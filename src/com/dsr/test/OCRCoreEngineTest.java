package com.dsr.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;
import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRTrainingFiles;
import com.dsr.core.DSRUnClassifiedFiles;
import com.dsr.instances.DocumentInfo;

public class OCRCoreEngineTest {
	public static void main(String[] args) {

		// Vector<DocumentInfo> docInfoVec = FileUtil
		// .loadObjectFromFile("resources\\obj\\documentsInfoTrainingData.obj");
		// engine.trainClassifierFromDB();
		prototype();
	}

	// TODO: When I get a new class I should remove all instances from the
	// Database and re-build them

	public static void testUnclassifiedFilesOnEmptyClassifier() {
		DSRCoreEngine engine = new DSRCoreEngine();
		String s = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(s);
		Vector<DocumentInfo> classes = engine.classifyFiles(unclassFiles);
		System.out.println(classes); // Should Return Null;
	}

	public static void testUpdateClassifiedFilesOnEmptyClassifier() {
		DSRCoreEngine engine = new DSRCoreEngine();

		String s = "C:\\temp\\classified";
		DSRTrainingFiles trainingFiles = new DSRTrainingFiles(s);
		engine.updateClassifier(trainingFiles);

		String s2 = "C:\\temp\\classified";
		DSRTrainingFiles trainingFiles2 = new DSRTrainingFiles(s2);
		engine.updateClassifier(trainingFiles2);

		String ss = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(ss);
		Vector<DocumentInfo> classifiedDoc = engine.classifyFiles(unclassFiles);
		System.out.println(classifiedDoc);
	}

	public static void testRealData() {
		DSRCoreEngine engine = new DSRCoreEngine();

		String s = "C:\\Users\\TeFa\\Desktop\\Data\\UID\\Classified";
		DSRTrainingFiles trainingFiles = new DSRTrainingFiles(s);
		engine.updateClassifier(trainingFiles);

		String ss = "C:\\Users\\TeFa\\Desktop\\Data\\UID\\UnClassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(ss);
		Vector<DocumentInfo> classifiedDoc = engine.classifyFiles(unclassFiles);
		System.out.println(classifiedDoc);

	}

	public static void testChangingAndRefreshing() {
		DSRCoreEngine engine = new DSRCoreEngine();

		DocumentInfo docInfo = new DocumentInfo(470, "My Doc.pdf", "Lelo", "Nothing Here aaaa");
		Vector<DocumentInfo> docInfoVec = new Vector<DocumentInfo>();
		docInfoVec.add(docInfo);

		engine.updateClassifier(docInfoVec);
	}

	public static void prototype() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Vector<DocumentInfo> classifiedDocInfo = new Vector<DocumentInfo>();
			DSRCoreEngine engine = new DSRCoreEngine();
			while (true) {
				System.out
						.println("\n========================\n\nSelect one of the following action: ");
				System.out.println("1. Classify new File");
				System.out.println("2. Train System");
				System.out.println("3. Update Existing Document");
				System.out.println("4. Print File Content");
				System.out.println("5. Exit\n");
				System.out.print("# ");
				String input = br.readLine();
				if (input.equals("1")) {
					System.out.println("Unclassified files directory:");
					System.out.print("# ");
					input = br.readLine();
					DSRUnClassifiedFiles unclassifiedFiles = new DSRUnClassifiedFiles(input);
					System.out.println(unclassifiedFiles.getPreprocessingVector());
					classifiedDocInfo = engine.classifyFiles(unclassifiedFiles);
					System.out.println(classifiedDocInfo);
					br.readLine();
				} else if (input.equals("2")) {
					System.out.println("Training data directory:");
					System.out.println("# ");
					input = br.readLine();
					DSRTrainingFiles trainingFiles = new DSRTrainingFiles(input);
					engine.updateClassifier(trainingFiles);
				} else if (input.equals("3")) {
					Vector<DocumentInfo> docInfoVec = engine.getAllDocumentsInfo();
					printList(docInfoVec);
					System.out.println("Select document ID to update: ");
					System.out.print("# ");
					int id = Integer.parseInt(br.readLine());
					System.out.println("Enter the category for document " + id);
					System.out.print("# ");
					String category = br.readLine();
					DocumentInfo docInfo = new DocumentInfo(id, "", category, "");
					Vector<DocumentInfo> updatedDocInfoVec = new Vector<DocumentInfo>();
					updatedDocInfoVec.add(docInfo);
					engine.updateClassifier(updatedDocInfoVec);
					docInfoVec = engine.getAllDocumentsInfo();
					printList(docInfoVec);
				} else if (input.equals("4")) {
					Vector<DocumentInfo> docInfoVec = engine.getAllDocumentsInfo();
					printList(docInfoVec);
					System.out.println("Select document ID to print: ");
					System.out.print("# ");
					int id = Integer.parseInt(br.readLine());
					for(DocumentInfo docInfo:docInfoVec)
						if(docInfo.getDocID() == id)
						{
							System.out.println("\n***********************************");
							System.out.println(docInfo.getContent());
							System.out.println("\n***********************************\n");
						}
				} else
					break;
			}
		} catch (Exception e) {

		}
	}

	public static void printList(Vector<DocumentInfo> docInfoVec) {
		for (DocumentInfo docInfo : docInfoVec)
			System.out.println(docInfo.getDocID() + ". |Name: " + docInfo.getName()
					+ " |Category: " + docInfo.getCategory());
		System.out.println();
	}
	/*
	 * - The classifier should be trained if there are effective instances in
	 * the database
	 *
	 *
	 */
}
