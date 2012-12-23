package com.dsr.test;

import java.io.File;
import com.dsr.ocr.OCRSendData;
import com.dsr.ocr.OCRService;
import com.dsr.util.FileUtil;

public class OCRServiceTest {
	public static void main(String[] args) {
		OCRService ocr = new OCRService();
		File f = new File("c:\\Temp\\a\\a.pdf");
		OCRSendData ocrSendData = new OCRSendData("a.pdf", "a", FileUtil.getByteArrayForFile(f));
		ocr.sendRequest(ocrSendData);
	}
}
