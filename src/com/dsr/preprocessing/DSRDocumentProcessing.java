package com.dsr.preprocessing;

//DSR --> Document System Recognition

public class DSRDocumentProcessing {
	public static void main(String[] args) {
		DocumentsProcessing docProc = new DocumentsProcessing();
		docProc.process();
		/*
		 * Trace.trace(DBQuery.getDocumentInfobyName(DBConnection.connect(),
		 * "CZ25409891_2012.07.04_Pruef.pdf"));
		 */
		/*
		 * Gson gson = new Gson(); String json =
		 * gson.toJson("drücken Sie auf Login. Sie erhalten ein kostenloses");
		 * String res = gson.fromJson(json, String.class); String jsonResponse =
		 * ""; try { URL url = new
		 * URL("http://localhost:2289/OCRService.svc/test"); HttpURLConnection
		 * conn = (HttpURLConnection) url.openConnection();
		 * conn.setDoOutput(true); conn.setRequestMethod("GET");
		 * conn.setRequestProperty("Content-Type", "application/json");
		 * 
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream(),"UTF8")); String output;
		 * while ((output = br.readLine()) != null) { jsonResponse += output; }
		 * 
		 * conn.disconnect();
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); } System.out.println(jsonResponse); String
		 * receivedData = gson.fromJson(jsonResponse, String.class);
		 * System.out.println(receivedData);
		 */
	}
}
