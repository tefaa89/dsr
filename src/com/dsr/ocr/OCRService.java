package com.dsr.ocr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.google.gson.Gson;

public class OCRService {
	private String jsonServiceURL;

	public OCRService(String jsonServiceURL) {
		this.jsonServiceURL = jsonServiceURL;
	}

	public OCRReceivedData sendRequest(Object objToSend) {
		Gson gson = new Gson();
		OCRReceivedData receivedData = null;
		String jsonResponse = "";
		try {
			OCRSendData ocrSendDataRaw = (OCRSendData) objToSend;
			if(ocrSendDataRaw.getFileName() != null)
				ocrSendDataRaw.setFileName(URLEncoder.encode(ocrSendDataRaw.getFileName(),"UTF-8"));
			if(ocrSendDataRaw.getCategory() != null)
				ocrSendDataRaw.setCategory(URLEncoder.encode(ocrSendDataRaw.getCategory(),"UTF-8"));

			String json = gson.toJson(ocrSendDataRaw);
			URL url = new URL(jsonServiceURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}

			conn.disconnect();
			receivedData = gson.fromJson(jsonResponse, OCRReceivedData.class);
			if(receivedData.getFileName() != null)
				receivedData.setFileName(URLDecoder.decode(receivedData.getFileName(), "UTF-8"));
			if(receivedData.getCategory() != null)
				receivedData.setCategory(URLDecoder.decode(receivedData.getCategory(), "UTF-8"));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return receivedData;
	}
}
