package com.dsr.configuration;

public class DBConfig {
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "dsr";
	public static final String DB_CONNECTION = "jdbc:mysql://localhost/document_system_recognition";

	public static final String QUERY_INSERT_IN_TRAINING_DATA_TABLE = "INSERT INTO `document_system_recognition`.`training_instances`(`FILENAME`,`CATEGORY`,`INSTANCE`) VALUES(?,?,?);";
	public static final String QUERY_GET_TRAINING_DATA = "Select instance From training_instances";
}
