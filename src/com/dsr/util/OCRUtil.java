package com.dsr.util;

import java.util.Vector;
import com.dsr.instances.DocumentInfo;
import com.dsr.ocr.OCRReceivedData;
import com.dsr.ocr.OCRSendData;

public class OCRUtil {
	public static Vector<OCRSendData> convertDocumentInfoToOCRSendData(Vector<DocumentInfo> docInfoVec) {
		Vector<OCRSendData> ocrDataToSend = new Vector<OCRSendData>();
		for(DocumentInfo docInfo:docInfoVec)
			ocrDataToSend.add(new OCRSendData(docInfo.getName(), docInfo.getCategory(), docInfo.getContent()));
		return ocrDataToSend;
	}
	
	public static Vector<DocumentInfo> convertOCRReceivedDataToDocumentInfo(Vector<OCRReceivedData> ocrDataVec)
	{
		Vector<DocumentInfo> docInfoVec = new Vector<DocumentInfo>();
		for(OCRReceivedData ocrData:ocrDataVec)
			docInfoVec.add(new DocumentInfo(ocrData.getFileName(), ocrData.getCategory(), ocrData.getOcrString()));
		return docInfoVec;
	}
}
