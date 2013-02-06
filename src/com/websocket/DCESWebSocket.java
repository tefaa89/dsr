package com.websocket;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.kit.RawPacket;
import org.jwebsocket.token.JSONToken;
import com.dces.evaluation.EvaluationInfo;
import com.google.gson.Gson;

public class DCESWebSocket {
	public static WebSocketServerThread wst = new WebSocketServerThread();

	public static void updateClassifierAcc(EvaluationInfo evalInfo) {
		if (!isConnected())
			return;
		JSONToken token = new JSONToken();
		JSONObject jsonObj;
		try {
			Gson json = new Gson();
			jsonObj = new JSONObject(json.toJson(evalInfo));
			jsonObj.put("type", "evalInfo");
			token.setJSONObject(jsonObj);
			sendToken(token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void sendToken(JSONToken token) {
		Map<String, WebSocketConnector> f = wst.getTokenServer().getAllConnectors();
		for (WebSocketConnector sc : f.values()) {
			WebSocketPacket packet = new RawPacket(token.getJSONObject().toString());
			sc.sendPacket(packet);
		}
	}

	private static boolean isConnected() {
		if (wst != null)
			if (wst.getTokenServer() != null)
				return true;
		return false;
	}
}
