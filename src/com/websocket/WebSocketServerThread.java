package com.websocket;

import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;
import com.esda.Main;

public class WebSocketServerThread implements WebSocketServerTokenListener {
	private static TokenServer tokenServer;

	public TokenServer getTokenServer() {
		return tokenServer;
	}

	public void init() {
		try {
			JWebSocketFactory.printCopyrightToConsole();
			JWebSocketConfig.initForConsoleApp(null);
			JWebSocketFactory.start();
			tokenServer = (TokenServer) JWebSocketFactory.getServer("ts0");
			if (tokenServer != null) {
				System.out.println("Server was found");
				tokenServer.addListener(this);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void processClosed(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processOpened(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processPacket(WebSocketServerEvent arg0, WebSocketPacket arg1) {
		System.out.println("Packet Received: " + arg1.getString());
		try {
			System.out.println(arg1.isComplete());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processToken(WebSocketServerTokenEvent arg0, Token arg1) {
		// TODO Auto-generated method stub
		System.out.println("#################  " + arg1.getString("type"));
		String messageType = arg1.getString("type");
		if(messageType.equals("evaluate"))
		{
			Main.dsrEngine.evaluate();
		}
	}

}