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
import java.util.Vector;
import com.dsr.configuration.DBConfig;
import com.dsr.instances.DocumentInfo;
import com.dsr.instances.DocumentInstance;
import com.dsr.util.Trace;

public class DBQuery {
	public static DocumentInfo[] getAllDocuments(Connection conn) {
		Vector<DocumentInfo> dInfoVec = new Vector<DocumentInfo>();
		try {
			PreparedStatement query = conn.prepareStatement("select * from documents_info");
			ResultSet res = query.executeQuery();
			while (res.next()) {
				DocumentInfo dInfo = new DocumentInfo(res.getString("name"),
						res.getString("category"), res.getString("content"));
				dInfoVec.add(dInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dInfoVec.toArray(new DocumentInfo[dInfoVec.size()]);
	}

	public static DocumentInfo getDocumentInfobyName(Connection conn, String docName) {
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
		DBQuery.storeMultipleDocumentsInfo(conn, new DocumentInfo[] { docInfo });
	}

	public static void storeMultipleDocumentsInfo(Connection conn, DocumentInfo[] docInfoArray) {
		try {
			String queryString = "INSERT INTO `document_system_recognition`.`documents_info`(`NAME` ,`CATEGORY` ,`CONTENT`) VALUES(?,?,?);";
			PreparedStatement query = conn.prepareStatement(queryString);
			for (int i = 0; i < docInfoArray.length; i++) {
				String name = docInfoArray[i].getName();
				String category = docInfoArray[i].getCategory();
				String content = docInfoArray[i].getContent();
				query.setString(1, name);
				query.setString(2, category);
				query.setString(3, content);
				query.addBatch();
				Trace.trace("Adding Entry " + i + " to DB");
				query.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Vector<DocumentInstance> getTrainingDocumentInstaces(Connection conn) {
		Vector<DocumentInstance> docInstanceVec = new Vector<DocumentInstance>();
		try {
			String queryString = DBConfig.QUERY_GET_TRAINING_DATA;
			PreparedStatement ps = conn.prepareStatement(queryString);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next())
			{
				InputStream is = resultSet.getBlob("instance").getBinaryStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				docInstanceVec.add((DocumentInstance)ois.readObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docInstanceVec;
	}

	public static void storeTrainingDocumentInstaces(Connection conn,
			Vector<DocumentInstance> docInstanceVec) {
		try {
			String queryString = DBConfig.QUERY_INSERT_IN_TRAINING_DATA_TABLE;
			PreparedStatement query = conn.prepareStatement(queryString);
			for (DocumentInstance docInst : docInstanceVec) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(docInst);
				byte[] objectBytesArr = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(objectBytesArr);
				query.setString(1, docInst.getDocNGram().getName());
				query.setString(2, docInst.getDocNGram().getCategory());
				query.setBinaryStream(3, bais, objectBytesArr.length);
				query.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
