package com.dces.util.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DCESInfoXML {
	private String iD;
	private String className;
	/**
	 * Map<String, String[]>: First String is classifier option. Second String[]
	 * contains the values for this option ex, ("k",[20,50,100]);
	 */
	private Map<String, String[]> parameters;
	private ArrayList<String> cutPercentagesList;
	private ArrayList<String> evaluatorSearchMethodsIDList;

	public DCESInfoXML() {

	}

	public String getID() {
		return iD;
	}

	public void setID(String id) {
		this.iD = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public ArrayList<String> getEvaluatorSearchMethodsIDList() {
		return evaluatorSearchMethodsIDList;
	}

	public void setEvaluatorSearchMethodsIDList(ArrayList<String> evaluatorSearchMethodsIDList) {
		this.evaluatorSearchMethodsIDList = evaluatorSearchMethodsIDList;
	}

	public ArrayList<String> getCutPercentagesList() {
		return cutPercentagesList;
	}

	public void setCutPercentagesList(ArrayList<String> cutPercentagesList) {
		this.cutPercentagesList = cutPercentagesList;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DCESInfoXML))
			return false;
		DCESInfoXML infoXml = (DCESInfoXML) obj;
		if (infoXml.getID().equals(getID()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		String res = "Xml Info Object:\n\tID: " + iD + "\n\tClassName: " + className
				+ "\n\tparameters: [";
		for (String key : parameters.keySet()) {
			res += " " + key + ":" + Arrays.toString(parameters.get(key));
		}
		res += " ]\n\t" + "SearchMethodsIDList: " + evaluatorSearchMethodsIDList + "\n";
		return res;
	}
}
