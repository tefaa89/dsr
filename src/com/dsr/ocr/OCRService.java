package com.dsr.ocr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class OCRService {
	private String jsonServiceURL;

	public OCRService(String jsonServiceURL) {
		this.jsonServiceURL = jsonServiceURL;
	}
	
	public OCRReceivedData sendRequest(Object objToSend) {
		Gson gson = new Gson();
		String json = gson.toJson(objToSend);
		String jsonResponse = "";
	//	System.out.println(json);
		try {
			URL url = new URL(jsonServiceURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			OutputStream os = conn.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF8"));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			
			conn.disconnect();

		} catch (Exception e) {

			e.printStackTrace();
		}
		OCRReceivedData receivedData = gson.fromJson(jsonResponse, OCRReceivedData.class);
		return receivedData;
	}
}
