package com.webservice;

import javax.xml.ws.Endpoint;

public class ServicePublish {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9898/Eval", new DCESAPI());
		 
        System.out.println("HelloWeb service is ready");
	}
}
