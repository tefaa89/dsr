package com.dsr.test;

import com.dsr.core.DSRUnClassifiedFiles;
import com.dsr.preprocessing.DocumentsProcessing;

public class DocumentProcessingTest {
	public static void main(String[] args) {
		String s = "C:\\temp\\unclassified";
		DSRUnClassifiedFiles unclassFiles = new DSRUnClassifiedFiles(s);
		DocumentsProcessing docProcessing = new DocumentsProcessing(
				unclassFiles.getPreprocessingVector());
		docProcessing.process();
		System.out.println(docProcessing.getDocumentInfoVec());
	}
}
