package com.dsr.preprocessing;

import java.util.Vector;
import com.dsr.configuration.Phase1Config;
import com.dsr.instances.DocumentInfo;
import com.dsr.ocr.OCRReceivedData;
import com.dsr.ocr.OCRSendData;
import com.dsr.ocr.OCRService;
import com.dsr.util.OCRUtil;
import com.dsr.util.Trace;

public class DocumentsProcessing {
	private Vector<OCRSendData> ocrSendDataVec;
	private Vector<OCRReceivedData> ocrReceivedDataVec;

	public DocumentsProcessing() {
		ocrSendDataVec = null;
		ocrReceivedDataVec = null;
	}
	
	public DocumentsProcessing(Vector<OCRSendData> ocrSendDataVec) {
		this.ocrSendDataVec = ocrSendDataVec;
		ocrReceivedDataVec = null;
	}
	
	public void process() {
		convertDocToText();
	}
	
	private void convertDocToText() {
		ocrReceivedDataVec = new Vector<OCRReceivedData>();
		OCRService ocrService = new OCRService(Phase1Config.OCR_SERVICE_URL
				+ Phase1Config.OCR_SERVICE_SINGLE_FILE_FUNCTION);
		for (OCRSendData dataToSend : ocrSendDataVec) {
			OCRReceivedData receivedData = ocrService.sendRequest(dataToSend);
			ocrReceivedDataVec.add(receivedData);
			Trace.trace("\n\n" + receivedData);
		}
	}

	public Vector<DocumentInfo> getDocumentInfoVec() {
		return OCRUtil.convertOCRReceivedDataToDocumentInfo(ocrReceivedDataVec);
	}
}
