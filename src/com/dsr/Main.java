package com.dsr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;
import com.dsr.core.DSRCoreEngine;
import com.dsr.core.DSRTrainingFiles;
import com.dsr.core.DSRUnClassifiedFiles;
import com.dsr.instances.DocumentInfo;

public class Main {
	public static void main(String[] args) {
		prototype();
	}

	public static void prototype() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Vector<DocumentInfo> classifiedDocInfo = new Vector<DocumentInfo>();
			DSRCoreEngine engine = new DSRCoreEngine();
			while (engine.isConnected()) {
				System.out
						.println("\n========================\n\nSelect one of the following actions: ");
				System.out.println("1. Classify new File");
				System.out.println("2. Train System");
				System.out.println("3. Update Classified Document");
				System.out.println("4. Print Document Content");
				System.out.println("5. Exit");
				System.out.println("100. Drop Database\n");
				System.out.print("# ");
				String input = br.readLine();
				if (input.equals("1")) {
					System.out.println("Unclassified files directory: (Defualt -> 'C:\\Files\\Unclassified'");
					System.out.print("# ");
					input = br.readLine();
					if(input.equals("")) input = "C:\\Files\\Unclassified";
					DSRUnClassifiedFiles unclassifiedFiles = new DSRUnClassifiedFiles(input);
					System.out.println(unclassifiedFiles.getPreprocessingVector());
					classifiedDocInfo = engine.classifyFiles(unclassifiedFiles);
					System.out.println(classifiedDocInfo);
					System.out.println("\n" + "Press Enter Key to Continue ...");
					br.readLine();
				} else if (input.equals("2")) {
					System.out.println("Training data directory: (Defualt -> 'C:\\Files\\Classified'");
					System.out.println("# ");
					input = br.readLine();
					if(input.equals("")) input = "C:\\Files\\Classified";
					DSRTrainingFiles trainingFiles = new DSRTrainingFiles(input);
					engine.updateClassifier(trainingFiles);
				} else if (input.equals("3")) {
					Vector<DocumentInfo> docInfoVec = engine.getAllDocumentsInfo();
					printList(docInfoVec);
					System.out
							.println("Select document ID to update or press 'Enter' to continue:: ");
					System.out.print("# ");
					input = br.readLine();
					if (input.equals(""))
						continue;
					int id = Integer.parseInt(input);
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
					System.out
							.println("Select document ID to print or press 'Enter' to continue: ");
					System.out.print("# ");
					input = br.readLine();
					if (input.equals(""))
						continue;
					int id = Integer.parseInt(input);
					for (DocumentInfo docInfo : docInfoVec)
						if (docInfo.getDocID() == id) {
							System.out.println("\n***********************************");
							System.out.println(docInfo.getContent());
							System.out.println("\n***********************************\n");
						}
				} else if (input.equals("100")) {
					engine.clear();
					break;
				} else
					break;
			}
		} catch (Exception e) {

		}
		System.out.println("\n\n Application Terminated ....");
	}

	public static void printList(Vector<DocumentInfo> docInfoVec) {
		for (DocumentInfo docInfo : docInfoVec)
			System.out.println(docInfo.getDocID() + ". |Name: " + docInfo.getName()
					+ " |Category: " + docInfo.getCategory());
		System.out.println();
	}
}