package com.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class DCESAPI {
	@WebMethod
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
