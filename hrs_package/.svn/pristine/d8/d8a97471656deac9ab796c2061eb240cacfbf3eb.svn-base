package com.hausontech.hrs.daoImpl.engine;

import java.sql.SQLException;

public class HrsStatement extends SQLStatement {

	// initialize all payment sql strings in a static bloc
	static {
		put("get_calulation_items", "{call hrs_core_item_result_api.get_item_contents(?,?,?,?,?)}", 5, 1);
		put("generate_fin_index", "{call hrs_core_item_result_api.generate_fin_index(?,?,?,?)}", 4, 1);
		// payment log
		put("insert_calculation_result", "INSERT INTO HRS_CORE_ITEM_RESULT(ITEM_CODE, RULE_CODE, FIN_ELEMENT, LEDGER_ID, ACTUAL_FLAG,"
							 + "CURRENCY_TYPE, CURRENCY_CODE, SEGMENT1, SEGMENT2, SEGMENT3, SEGMENT4, SEGMENT5, SEGMENT6, SEGMENT7,SEGMENT8,SEGMENT9,"
				             + "SEGMENT10,SEGMENT11,SEGMENT12,SEGMENT13,SEGMENT14,SEGMENT15,SEGMENT16,SEGMENT17,SEGMENT18,SEGMENT19,SEGMENT20,PERIOD_NAME,"
				             + "PERIOD_NUM,PERIOD_YEAR,BEGIN_BALANCE_DR,BEGIN_BALANCE_CR,PERIOD_DR,PERIOD_CR,END_BALANCE_DR,END_BALANCE_CR,"
				             + "REQUEST_INSTANCE_ID,"
				             + "CREATION_DATE, CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 41, 1);

	};

	public HrsStatement() throws SQLException {
		super();
	}

	public HrsStatement(String uname, String pwd) throws SQLException {
		super(uname, pwd);
	}

	public HrsStatement(String host, String port, String sid, String uname, String pwd) throws SQLException {
		super(host, port, sid, uname, pwd);
	}
	
}
