package com.dsr.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.dsr.classifier.DocumentClassifer;
import com.dsr.classifier.IBKDocumentClassifier;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstance;
import com.dsr.util.Trace;
import com.dsr.util.enumu.ClassifiersEnum;
import com.dsr.util.enumu.NGramEnum;
import com.mysql.jdbc.Statement;

public class DBQuery {
	private static Map<String, Boolean> dbTables;

	public static boolean isDBExists(Connection conn) {
		try {
			Trace.trace("Database: Checking if 'document_system_recognition' database exists or not ...");
			String queryString = "SELECT SCHEMA_NAME ";
			queryString += "FROM INFORMATION_SCHEMA.SCHEMATA ";
			queryString += "WHERE SCHEMA_NAME = 'document_system_recognition'";
			PreparedStatement query = conn.prepareStatement(queryString);
			ResultSet res = query.executeQuery();
			if (res.next()) {
				Trace.trace("Database: Database exists ...");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Trace.trace("Database: Database does not exist ...");
		return false;
	}

	public static void dropDatabase(Connection conn)
	{
		try {
			Trace.trace("Database: Droping Database ...");
			String queryString = "DROP Database `document_system_recognition`;";
			PreparedStatement query = conn.prepareStatement(queryString);
			query.executeUpdate();
			Trace.trace("Database has been dropped ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean tablesExists(Connection conn) {
		try {
			Trace.trace("Database: Checking if required tables exists in database or not ...");
			String queryString = "SELECT * ";
			queryString += "FROM information_schema.tables ";
			queryString += "WHERE table_schema = 'document_system_recognition' AND (";
			queryString += "table_name = 'documents_info' OR ";
			queryString += "table_name = 'classifiers_backups' OR ";
			queryString += "table_name = 'documents_instances' OR ";
			queryString += "table_name = 'documents_ngrams')";
			PreparedStatement query = conn.prepareStatement(queryString);
			ResultSet res = query.executeQuery();
			dbTables = new HashMap<String, Boolean>();
			dbTables.put("documents_info", false);
			dbTables.put("classifiers_backups", false);
			dbTables.put("documents_instances", false);
			dbTables.put("documents_ngrams", false);
			int counter = 0;
			for (; res.next(); counter++)
				dbTables.put(res.getString("table_name"), true);
			if (counter >= 4) {
				Trace.trace("Database: Requried tables exists ...");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Trace.trace("Database: Some tables does not exists ...");
		return false;
	}

	public static void createDB(Connection conn) {
		try {
			Trace.trace("Database: Creating 'document_system_recognition' Database");
			String queryString = "CREATE DATABASE IF NOT EXISTS document_system_recognition ";
			queryString += "CHARACTER SET utf8 ";
			queryString += "COLLATE utf8_general_ci;";
			PreparedStatement query = conn.prepareStatement(queryString);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createTables(Connection conn) {
		try {
			Trace.trace("Database: Creating missing tables in 'document_system_recognition' database ...");
			for (String key : dbTables.keySet()) {
				if (!dbTables.get(key)) {
					String queryString = "";
					if (key.equals("documents_info")) {
						queryString = "CREATE TABLE `documents_info`";
						queryString += "(`ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
								+ "`NAME` varchar(100) COLLATE 'utf8_general_ci' NOT NULL,"
								+ "`CATEGORY` varchar(100) COLLATE 'utf8_general_ci' NULL,"
								+ "`CONTENT` longtext COLLATE 'utf8_general_ci' NULL) ";
						queryString += "CHARACTER SET utf8 ";
						queryString += "COLLATE utf8_general_ci;";
						PreparedStatement query = conn.prepareStatement(queryString);
						query.executeUpdate();
					} else if (key.equals("documents_ngrams")) {
						queryString = "CREATE TABLE `documents_ngrams`";
						queryString += "(`DOC_ID` int(11) NOT NULL,"
								+ "`NGRAM_TYPE` varchar(100) COLLATE 'utf8_general_ci' NOT NULL,"
								+ "`NGRAM_VEC` longblob NOT NULL,"
								+ "FOREIGN KEY (`DOC_ID`) REFERENCES `documents_info` (`ID`)) ";
						queryString += "CHARACTER SET utf8 ";
						queryString += "COLLATE utf8_general_ci;";
						PreparedStatement query = conn.prepareStatement(queryString);
						query.executeUpdate();
					} else if (key.equals("documents_instances")) {
						queryString = "CREATE TABLE `documents_instances`";
						queryString += "(`DOC_ID` int(11) NOT NULL,"
								+ "`EFFICTIVE_INSTANCE` tinyint NOT NULL,"
								+ "`FEATURES_VALUES_VEC` longblob NOT NULL,"
								+ "FOREIGN KEY (`DOC_ID`) REFERENCES `documents_info` (`ID`)) ";
						queryString += "CHARACTER SET utf8 ";
						queryString += "COLLATE utf8_general_ci;";
						PreparedStatement query = conn.prepareStatement(queryString);
						query.executeUpdate();
					} else if (key.equals("classifiers_backups")) {
						queryString = "CREATE TABLE `classifiers_backups`";
						queryString += "(`ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
								+ "`TYPE` varchar(100) COLLATE 'utf8_general_ci' NOT NULL,"
								+ "`CLASSIFIER` longblob NULL)";
						queryString += "CHARACTER SET utf8 ";
						queryString += "COLLATE utf8_general_ci;";
						PreparedStatement query = conn.prepareStatement(queryString);
						query.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Vector<DocumentInfo> getAllDocuments(Connection conn) {
		Trace.trace("Database: " + "Retrieving All Documents");
		Vector<DocumentInfo> dInfoVec = new Vector<DocumentInfo>();
		try {
			PreparedStatement query = conn.prepareStatement("select * from documents_info");
			ResultSet res = query.executeQuery();
			while (res.next()) {
				DocumentInfo dInfo = new DocumentInfo(res.getInt("ID"), res.getString("NAME"),
						res.getString("CATEGORY"), res.getString("CONTENT"));
				dInfoVec.add(dInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dInfoVec;
	}

	public static DocumentInfo getDocumentInfobyName(Connection conn, String docName) {
		Trace.trace("Database: " + "Retrieving Document with Name: " + docName);
		DocumentInfo dInfo = null;
		try {
			PreparedStatement query = conn
					.prepareStatement("select * from documents_info where name='" + docName + "'");
			ResultSet res = query.executeQuery();
			res.next();
			if (res.isLast())
				dInfo = new DocumentInfo(res.getString("name"), res.getString("category"),
						res.getString("content"));
			else
				throw new Exception("Error: Multiple Entries with same file Name");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dInfo;
	}

	public static void storeDocumentInfo(Connection conn, DocumentInfo docInfo) {
		Vector<DocumentInfo> docInfoVec = new Vector<DocumentInfo>();
		docInfoVec.add(docInfo);
		DBQuery.storeMultipleDocumentsInfo(conn, docInfoVec);
	}

	public static void storeMultipleDocumentsInfo(Connection conn, Vector<DocumentInfo> docInfoVec) {
		try {
			Trace.trace("Database: " + "Storing " + docInfoVec.size() + " Documents Information");
			String queryString = "INSERT INTO ";
			queryString += "`document_system_recognition`.`documents_info`";
			queryString += "(`NAME` ,`CATEGORY` ,`CONTENT`) ";
			queryString += "VALUES(?,?,?);";
			PreparedStatement query = conn.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);
			for (DocumentInfo docInfo : docInfoVec) {
				String name = docInfo.getName();
				String category = docInfo.getCategory();
				String content = docInfo.getContent();
				query.setString(1, name);
				query.setString(2, category);
				query.setString(3, content);
				query.executeUpdate();
				ResultSet idsSet = query.getGeneratedKeys();
				idsSet.next();
				docInfo.setDocID(idsSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static Vector<DocumentInstance> getEffictiveDocumentInstaces(Connection conn) {
		Trace.trace("Database: " + "Retrieving Effective Document Instances");
		Vector<DocumentInstance> docInstanceVec = new Vector<DocumentInstance>();
		try {
			String queryString = "SELECT dInfo.ID, dInfo.NAME, dInfo.CATEGORY, dInfo.CONTENT, dngram.NGRAM_TYPE, dngram.NGRAM_VEC, dInst.FEATURES_VALUES_VEC ";
			queryString += "FROM documents_info dInfo, documents_ngrams dngram, documents_instances dInst ";
			queryString += "WHERE dInst.DOC_ID = dInfo.ID AND dngram.DOC_ID = dInfo.ID AND dInst.EFFICTIVE_INSTANCE = true";
			PreparedStatement ps = conn.prepareStatement(queryString);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				DocumentInstance dInst = new DocumentInstance();
				dInst.getDocNGram().setDocID(resultSet.getInt("ID"));
				dInst.getDocNGram().setName(resultSet.getString("NAME"));
				dInst.getDocNGram().setCategory(resultSet.getString("CATEGORY"));
				dInst.getDocNGram().setContent(resultSet.getString("CONTENT"));
				dInst.getDocNGram().setnGramType(
						NGramEnum.valueOf(resultSet.getString("NGRAM_TYPE")));

				InputStream is = resultSet.getBlob("NGRAM_VEC").getBinaryStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				dInst.getDocNGram().setnGramVec((Vector<String>) ois.readObject());

				is = resultSet.getBlob("FEATURES_VALUES_VEC").getBinaryStream();
				ois = new ObjectInputStream(is);
				dInst.setFeaturesValues((Vector<Double>) ois.readObject());
				dInst.setEffective(true);
				docInstanceVec.add(dInst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docInstanceVec;

	}

	public static void updateCategories(Connection conn, Vector<DocumentInfo> docInfoVec) {
		try {
			Trace.trace("Database: " + "Updating Classified Categories");
			String queryStr = "UPDATE `documents_info` ";
			queryStr += "SET `CATEGORY` = ? ";
			queryStr += "WHERE ID = ?";

			PreparedStatement ps = conn.prepareStatement(queryStr);
			for (DocumentInfo docInfo : docInfoVec) {
				ps.setString(1, docInfo.getCategory());
				ps.setInt(2, docInfo.getDocID());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateInstanceEffectiveness(Connection conn, Vector<DocumentInfo> docInfoVec) {
		try {
			Trace.trace("Database: " + "Updating Classified Instances Effectiveness");
			String queryStr = "UPDATE `documents_instances` ";
			queryStr += "SET `EFFICTIVE_INSTANCE` = ? ";
			queryStr += "WHERE DOC_ID = ?";

			PreparedStatement ps = conn.prepareStatement(queryStr);
			for (DocumentInfo docInfo : docInfoVec) {
				ps.setBoolean(1, true);
				ps.setInt(2, docInfo.getDocID());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void storeTrainingDocumentInstaces(Connection conn,
			Vector<DocumentInstance> docInstanceVec) {
		Trace.trace("Database: " + "Storing Training Instances in 2 Steps");
		storeNGramsVec(conn, docInstanceVec);
		storeInstancesVec(conn, docInstanceVec);
	}

	public static void backupClassifier(Connection conn, DocumentClassifer classifier) {
		try {
			Trace.trace("Database: " + "Storing Document Classifier");
			String queryStr = "INSERT INTO ";
			queryStr += "`document_system_recognition`.`classifiers_backups`";
			queryStr += "(`TYPE`)";
			queryStr += "VALUES(?);";

			PreparedStatement ps = conn.prepareStatement(queryStr, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, classifier.getClassifierType() + "");
			ps.executeUpdate();
			ResultSet idSet = ps.getGeneratedKeys();
			idSet.next();
			classifier.setClassifierID(idSet.getInt(1));

			queryStr = "UPDATE `classifiers_backups` ";
			queryStr += "SET `CLASSIFIER` = ?";
			ps = conn.prepareStatement(queryStr);
			byte[] classifierByteArr = getByteArrayFromObject(classifier);
			ByteArrayInputStream classifierIS = new ByteArrayInputStream(classifierByteArr);
			ps.setBinaryStream(1, classifierIS, classifierByteArr.length);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DocumentClassifer getLatestClassifier(Connection conn) {
		Trace.trace("Database: " + "Retrieving Latest Document Classifier");
		DocumentClassifer classifier = null;
		String queryString = "SELECT MAX(cb.ID) as lastID, cb.TYPE, cb.CLASSIFIER ";
		queryString += "FROM classifiers_backups cb ";
		queryString += "HAVING lastID IS NOT NULL;";
		try {
			PreparedStatement ps = conn.prepareStatement(queryString);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("lastID");
				Trace.trace("Classifier ID: " + id);
				ClassifiersEnum cType = ClassifiersEnum.valueOf(resultSet.getString("TYPE"));
				InputStream is = resultSet.getBlob("CLASSIFIER").getBinaryStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				if (cType.equals(ClassifiersEnum.IBK))
					classifier = (IBKDocumentClassifier) ois.readObject();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return classifier;
	}

	private static void storeNGramsVec(Connection conn, Vector<DocumentInstance> docInstanceVec) {
		try {
			Trace.trace("Database: " + "#STEP1# Storing NGrams Vector");
			String ngramsQueryStr = "INSERT INTO ";
			ngramsQueryStr += "`document_system_recognition`.`documents_ngrams`";
			ngramsQueryStr += "(`DOC_ID`,`NGRAM_TYPE`,`NGRAM_VEC`) ";
			ngramsQueryStr += "VALUES(?,?,?);";
			PreparedStatement ngramsQuery = conn.prepareStatement(ngramsQueryStr);
			for (DocumentInstance docInst : docInstanceVec) {
				byte[] ngramVecArr = getByteArrayFromObject(docInst.getDocNGram().getnGramVec());
				ByteArrayInputStream ngramVecStream = new ByteArrayInputStream(ngramVecArr);
				ngramsQuery.setInt(1, docInst.getDocNGram().getDocID());
				ngramsQuery.setString(2, docInst.getDocNGram().getnGramType() + "");
				ngramsQuery.setBinaryStream(3, ngramVecStream, ngramVecArr.length);
				ngramsQuery.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void storeInstancesVec(Connection conn, Vector<DocumentInstance> docInstanceVec) {
		try {
			Trace.trace("Database: " + "#STEP2# Storing Instances Vector");
			String instancesQueryStr = "INSERT INTO ";
			instancesQueryStr += "`document_system_recognition`.`documents_instances`";
			instancesQueryStr += "(`DOC_ID`,`EFFICTIVE_INSTANCE`,`FEATURES_VALUES_VEC`) ";
			instancesQueryStr += "VALUES(?,?,?);";
			PreparedStatement instancesQuery = conn.prepareStatement(instancesQueryStr);
			for (DocumentInstance docInst : docInstanceVec) {
				byte[] featureValuesVecArr = getByteArrayFromObject(docInst.getFeaturesValues());
				ByteArrayInputStream featureValuesVecStream = new ByteArrayInputStream(
						featureValuesVecArr);
				instancesQuery.setInt(1, docInst.getDocNGram().getDocID());
				instancesQuery.setBoolean(2, docInst.isEffective());
				instancesQuery.setBinaryStream(3, featureValuesVecStream,
						featureValuesVecArr.length);
				instancesQuery.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[] getByteArrayFromObject(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
