package com.hausontech.hrs.daoImpl.engine;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Hashtable;

public class SQLStatement {

	// flag indicating whether to use local JDBC connection
	static boolean useJDBC = Boolean.getBoolean("useJDBC");

	public static final int UNKNOWN = -1;

	public static final int STATEMENT = 0;

	public static final int PREPARED = 1;

	public static final int CALLABLE = 2;

	private java.sql.Connection conn = null;
	private Statement stmt = null;
	private ResultSet rset = null;

	private int mode = UNKNOWN;
	private SQLInfo sqlInfo = null;
	private int paramCount = 0; // count how many params have been set

	static Hashtable oracleSQLInfos = new Hashtable();

	static public void put(String name, String sqlString, int numberOfParams, int paramStartIndex) {
		Object prev = oracleSQLInfos.put(name, new SQLInfo(sqlString, numberOfParams, paramStartIndex));
	}

	public SQLStatement() throws SQLException {
		this(null, null);
	}

	public void setAutoCommit(boolean flag) throws SQLException {
		if (conn != null)
			conn.setAutoCommit(flag);
	}

	public java.sql.Connection getConnection() {
		return conn;
	}

	public void setConnection(java.sql.Connection conn) {
		this.conn = conn;
	}

	public SQLStatement(String uname, String pwd) throws SQLException {

		conn = getConnection(uname, pwd);
	}

	public SQLStatement(String host, String port, String sid, String uname, String pwd) throws SQLException {

		conn = getConnection(host, port, sid, uname, pwd);
		//conn.setNetworkTimeout(executor, milliseconds);
	}

	private java.sql.Connection getConnectionByJDBC(String uname, String pwd) throws SQLException {
		String host = System.getProperty("host");
		if (host == null)
			throw new SQLException("To get a connection through local JDBC, you must specify db host by using -D option, i.e., java -Dhost=DB_HOST_NAME.");
		String port = System.getProperty("port");
		if (port == null)
			throw new SQLException("To get a connection through local JDBC, you must specify db port  by using -D option, i.e., java -Dport=DB_port.");
		if (uname == null) {
			uname = System.getProperty("uname");
			if (uname == null)
				throw new SQLException("To get a connection through local JDBC, you must specify user name  by using -D option, i.e., java -Duname=DB_USER_NAME.");

		}
		if (pwd == null) {
			pwd = System.getProperty("pwd");
			if (pwd == null)
				throw new SQLException("To get a connection through local JDBC, you must specify user password  by using -D option, i.e., java -Dpwd=DB_USER_PASSWORD.");

		}
		String sid = System.getProperty("sid");
		if (sid == null)
			throw new SQLException("To get an oracle connection through local JDBC, you must specify sid by using -D option, i.e., java -Dsid=ORACLE_SID.");
		String url = "jdbc:oracle:thin:@" + host + ":" + port + "/" + sid;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			return java.sql.DriverManager.getConnection(url, uname, pwd);
		} catch (ClassNotFoundException cnfe) {
			throw new SQLException("Failed to load driver class. " + cnfe);
		}
	}

	private java.sql.Connection getConnectionByJDBC(String host, String port, String sid, String uname, String pwd) throws SQLException {
		if (host == null)
			throw new SQLException("To get a connection through local JDBC, you must specify db host by using -D option, i.e., java -Dhost=DB_HOST_NAME.");
		if (port == null)
			throw new SQLException("To get a connection through local JDBC, you must specify db port  by using -D option, i.e., java -Dport=DB_port.");
		if (uname == null) {
			uname = System.getProperty("uname");
			if (uname == null)
				throw new SQLException("To get a connection through local JDBC, you must specify user name  by using -D option, i.e., java -Duname=DB_USER_NAME.");

		}
		if (pwd == null) {
			pwd = System.getProperty("pwd");
			if (pwd == null)
				throw new SQLException("To get a connection through local JDBC, you must specify user password  by using -D option, i.e., java -Dpwd=DB_USER_PASSWORD.");

		}
		if (sid == null)
			throw new SQLException("To get an oracle connection through local JDBC, you must specify sid by using -D option, i.e., java -Dsid=ORACLE_SID.");
		String url = "jdbc:oracle:thin:@" + host + ":" + port + "/" + sid;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			return java.sql.DriverManager.getConnection(url, uname, pwd);
		} catch (ClassNotFoundException cnfe) {
			throw new SQLException("Failed to load driver class. " + cnfe);
		}
	}

	private java.sql.Connection getConnection(String uname, String pwd) throws SQLException {
		if (conn != null)
			return conn;

		return getConnectionByJDBC(uname, pwd);
	}

	private java.sql.Connection getConnection(String host, String port, String sid, String uname, String pwd) throws SQLException {
		if (conn != null)
			return conn;

		return getConnectionByJDBC(host, port, sid, uname, pwd);
	}

	private void freeConnection(java.sql.Connection conn) throws SQLException {
		conn.close();
	}

	public boolean isDuplicateBillIdException(SQLException se) {
		if (se.getErrorCode() == 1 && se.getMessage() != null && se.getMessage().toUpperCase().indexOf("UK_BILL_ID") >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDuplicateKeyException(SQLException se) {
		if (se.getErrorCode() == 1)
			return true;
		else
			return false;
	}

	public Statement getStatement() {
		return stmt;
	}

	public int getStartParamIndex() {
		return sqlInfo.startIndex;
	}

	public int getSQLParamCount() {
		return sqlInfo.sqlParamCount;
	}

	private void getSqlInfo(String sqlName) throws SQLException {
		sqlInfo = (SQLInfo) oracleSQLInfos.get(sqlName);
		if (sqlInfo == null) {
			int i = 0;
			int count = 0;
			while ((i = sqlName.indexOf('?', i)) > 0) {
				count++;
				i++;
			}
			sqlInfo = new SQLInfo(sqlName, count, 1);
		}
	}

	public void setSQL(String sqlName, int md) throws SQLException {
		mode = md;
		paramCount = 0;

		subclose();

		getSqlInfo(sqlName);

		switch (mode) {
		case STATEMENT:
			stmt = conn.createStatement();
			break;

		case PREPARED:
			stmt = conn.prepareStatement(sqlInfo.sqlString);
			break;

		case CALLABLE:
			stmt = conn.prepareCall(sqlInfo.sqlString);
			break;

		default:
			throw new SQLException("invalid mode:" + mode);
		}
	}

	public ResultSet executeQuery() throws SQLException {
		if (rset != null)
			throw new SQLException("Result set should be closed before executeQuery()");
		if (mode == STATEMENT)
			rset = ((Statement) stmt).executeQuery(sqlInfo.sqlString);
		else
			rset = ((PreparedStatement) stmt).executeQuery();

		return rset;
	}

	public ResultSet executeSqlQuery() throws SQLException {
		if (mode == CALLABLE && paramCount < getSQLParamCount()) {
			int i = getStartParamIndex() == 1 ? getSQLParamCount() : 1;
			((CallableStatement) stmt).registerOutParameter(i, oracle.jdbc.OracleTypes.CURSOR);
			this.execute();
			rset = (ResultSet) getObject(i);
			return rset;
		} else
			return executeQuery();
	}

	public int executeUpdate() throws SQLException {
		if (mode == STATEMENT)
			return ((Statement) stmt).executeUpdate(sqlInfo.sqlString);
		else
			return ((PreparedStatement) stmt).executeUpdate();
	}

	public int executeSqlUpdate() throws SQLException {

		if (mode == CALLABLE && paramCount < getSQLParamCount()) {
			int i = getStartParamIndex() == 1 ? getSQLParamCount() : 1;
			((CallableStatement) stmt).registerOutParameter(i, Types.INTEGER);
			execute();
			int count = getInt(i);
			return count;
		}
		return executeUpdate();
	}

	public void decrementCurrentParamCount() {
		paramCount--;
	}

	public boolean execute() throws SQLException {
		if (mode == STATEMENT)
			return ((Statement) stmt).execute(sqlInfo.sqlString);
		else
			return ((PreparedStatement) stmt).execute();
	}

	public ResultSet getResultSet() throws SQLException {
		return stmt.getResultSet();
	}

	public Object executeSql(int type) throws SQLException {

		if (mode == CALLABLE && paramCount < getSQLParamCount()) {
			int i = getStartParamIndex() == 1 ? getSQLParamCount() : 1;
			((CallableStatement) stmt).registerOutParameter(i, type);
			execute();
			return getObject(i);
		} else {
			ResultSet res = executeQuery();
			if (res.next()) {
				return res.getObject(1);
			} else
				return null;
		}
	}

	public void setObject(int index, Object value) throws SQLException {
		paramCount++;
		if (value == null)
			((PreparedStatement) stmt).setNull(index, Types.VARCHAR);
		else
			((PreparedStatement) stmt).setObject(index, value);
	}

	public boolean wasNull() throws SQLException {
		return ((CallableStatement) stmt).wasNull();
	}

	public Object getObject(int index) throws SQLException {
		return ((CallableStatement) stmt).getObject(index);
	}

	public void setInt(int index, int value) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setInt(index, value);

	}

	public int getInt(int index) throws SQLException {
		return ((CallableStatement) stmt).getInt(index);
	}

	public void setLong(int index, long value) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setLong(index, value);
	}

	public long getLong(int index) throws SQLException {
		return ((CallableStatement) stmt).getLong(index);
	}

	public void setDouble(int index, double value) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setDouble(index, value);
	}

	public void setArray(int index, oracle.sql.ARRAY value) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setArray(index, value);
	}

	public double getDouble(int index) throws SQLException {
		return ((CallableStatement) stmt).getDouble(index);
	}

	public void setString(int index, String value) throws SQLException {
		paramCount++;
		if (value == null)
			((PreparedStatement) stmt).setNull(index, Types.VARCHAR);
		else
			((PreparedStatement) stmt).setString(index, value);
	}

	public String getString(int index) throws SQLException {
		return ((CallableStatement) stmt).getString(index);
	}

	public void setBytes(int index, byte[] value) throws SQLException {
		paramCount++;
		if (value == null)
			((PreparedStatement) stmt).setNull(index, Types.VARBINARY);
		else
			((PreparedStatement) stmt).setBytes(index, value);
	}

	public byte[] getBytes(int index) throws SQLException {
		return ((CallableStatement) stmt).getBytes(index);
	}

	public void setBoolean(int index, boolean value) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setBoolean(index, value);
	}

	public boolean getBoolean(int index) throws SQLException {
		return ((CallableStatement) stmt).getBoolean(index);
	}

	public void setNull(int index, int type) throws SQLException {
		paramCount++;
		((PreparedStatement) stmt).setNull(index, type);
	}

	private java.sql.Timestamp toSqlTimestamp(java.sql.Date d) {
		if (d == null)
			return null;
		else {

			Calendar cal = Calendar.getInstance();
			cal.setTime(new java.util.Date(d.getTime()));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.AM_PM, Calendar.AM);

			return new java.sql.Timestamp(cal.getTime().getTime());
		}
	}

	private java.sql.Timestamp toSqlTimestamp(java.sql.Time t) {
		if (t == null)
			return null;
		else {

			Calendar cal = Calendar.getInstance();
			cal.setTime(new java.util.Date(t.getTime()));
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 0);

			return new java.sql.Timestamp(cal.getTime().getTime());
		}
	}

	public void setDate(int index, java.sql.Date date) throws SQLException {
		paramCount++;
		if (date == null)
			((PreparedStatement) stmt).setNull(index, Types.DATE);
		else
			((PreparedStatement) stmt).setDate(index, date);
	}

	public java.sql.Date getDate(int index) throws SQLException {
		return ((CallableStatement) stmt).getDate(index);
	}

	public void setTimestamp(int index, java.sql.Timestamp date) throws SQLException {
		paramCount++;
		if (date == null)
			((PreparedStatement) stmt).setNull(index, Types.TIMESTAMP);
		else
			((PreparedStatement) stmt).setTimestamp(index, date);
	}

	public Timestamp getTimestamp(int index) throws SQLException {
		return ((CallableStatement) stmt).getTimestamp(index);
	}

	public void setTime(int index, java.sql.Time time) throws SQLException {
		paramCount++;
		if (time == null)
			((PreparedStatement) stmt).setNull(index, Types.TIME);
		else
			((PreparedStatement) stmt).setTime(index, time);
	}

	public Time getTime(int index) throws SQLException {
		return ((CallableStatement) stmt).getTime(index);
	}

	public void registerOutParameter(int index, int type) throws SQLException {
		paramCount++;
		((CallableStatement) stmt).registerOutParameter(index, type);
	}

	private void subclose() {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {

			}
		}
		rset = null;
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				;
			}
		}
		stmt = null;
	}

	protected void finalize() throws Throwable {
		if (conn != null) {
			close();
		}
	}

	public void close() {
		subclose();
		if (conn != null) {
			try {
				freeConnection(conn);
			} catch (SQLException e) {

			}
			conn = null;
		}
	}

	public oracle.sql.ARRAY getArrayObject(String arrayObjName, Object[] object) throws SQLException {
		oracle.sql.ARRAY array = null;
		if (this.conn != null) {
			oracle.sql.ArrayDescriptor desc = oracle.sql.ArrayDescriptor.createDescriptor(arrayObjName, conn);
			array = new oracle.sql.ARRAY(desc, conn, object);
		}
		return array;
	}

}
