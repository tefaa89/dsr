package com.dsr.test;

import java.io.File;
import com.dsr.configuration.Phase1Config;
import com.dsr.ocr.OCRSendData;
import com.dsr.ocr.OCRService;
import com.dsr.util.FileUtil;

public class OCRServiceTest {
	public static void main(String[] args) {
		OCRService ocr = new OCRService(Phase1Config.OCR_SERVICE_URL + Phase1Config.OCR_SERVICE_SINGLE_FILE_FUNCTION);
		File f = new File("c:\\Temp\\a\\a.pdf");
		OCRSendData ocrSendData = new OCRSendData("a.pdf", "a", FileUtil.getByteArrayForFile(f));
		ocr.sendRequest(ocrSendData);
	}
}
