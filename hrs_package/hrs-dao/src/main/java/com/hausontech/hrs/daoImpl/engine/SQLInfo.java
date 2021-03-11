package com.hausontech.hrs.daoImpl.engine;

public class SQLInfo {
	String sqlString = null;
	int sqlParamCount = 0; // number of parameters
	int startIndex = 1; // start index to set parameter. should always 1 or 2

	public SQLInfo(String sqlString, int sqlParamCount, int startIndex) {
		this.sqlString = sqlString;
		this.sqlParamCount = sqlParamCount;
		this.startIndex = startIndex;
	}

	public String toString() {
		return "sqlString=" + sqlString + ", sqlParamCount=" + sqlParamCount + ", startIndex=" + startIndex;
	}
}