/*package com.hausontech.hrs.daoImpl.engine;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;

import com.hausontech.hrs.api.engine.ICalculationDao;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.engine.CalculationProcessorBean;
import com.hausontech.hrs.daoImpl.BaseDaoImpl;
import com.hausontech.hrs.utils.DateUtil;

import oracle.jdbc.OracleTypes;

public class CalculationDaoImpl extends BaseDaoImpl implements ICalculationDao {

	//静态批量值
	private static final int BATCH_SIZE = 5000;
	
	@SuppressWarnings("unchecked")
	public void generateFinIndex(RequestInstanceBean requestBean) throws SQLException {
		HrsStatement stmt = null;
		String sqlString = "generate_fin_index";
		try {
			//this.jdbcTemplate.execute("{call hrs_core_item_result_api.generate_fin_index(?,?,?,?)}", action)			
			String sql = "{call hrs_core_item_result_api.generate_fin_index(?,?,?,?,?)}";
			jdbcTemplate.execute(sql,
					new CallableStatementCallback() {
			            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
			                cs.setString(1, requestBean.getFinElementType()); 
			                cs.setLong(2,  requestBean.getLedgerId()); 
			                cs.setString(3,requestBean.getPeriodFrom()); 
			                cs.setString(4,requestBean.getPeriodTo()); 
			                cs.setLong(5, requestBean.getInstanceId()); 
			                cs.execute();
			                //close db statement
			                try {
			                	if (cs != null) {
			                		cs.close();
			                	}
			                } catch (Exception e) {		                	
			                }
			                return 0; 
			            }
			        }); 
		} finally {
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<CalculationProcessorBean>> getItemContents(RequestInstanceBean requestBean) throws SQLException {
		Map<String, List<CalculationProcessorBean>> processResult = null;
		try {
			processResult = (Map<String, List<CalculationProcessorBean>>) jdbcTemplate.execute(
					new CallableStatementCreator() {
						    public CallableStatement createCallableStatement(Connection con) throws SQLException {
						    	String storedProc = "{call hrs_core_item_result_api.get_item_contents(?,?,?,?,?,?)}";
						    	int i = 1;
						    	CallableStatement cs = con.prepareCall(storedProc);
								// set element
						    	cs.setString(i++, requestBean.getFinElementType());
								// set ledger id
						    	cs.setLong(i++, requestBean.getLedgerId());
								// set period from
						    	cs.setString(i++, requestBean.getPeriodFrom());
								// set period to 
						    	cs.setString(i++, requestBean.getPeriodFrom());
								//set request instance Id
						    	cs.setLong(i++, requestBean.getInstanceId());
						    	//register ouput parameter
						    	cs.registerOutParameter(i++,  OracleTypes.CURSOR);// 注册输出参数的类型
						    	return cs;
	                        }
					}, 
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
							Map<String, List<CalculationProcessorBean>> result = 
									new HashMap<String, List<CalculationProcessorBean>>();
							cs.execute();
							ResultSet rs = (ResultSet) cs.getObject(6);
							int groupNumber = 1;
							CalculationProcessorBean currentCalBean = null;
							CalculationProcessorBean previousCalBean = null;
							List<CalculationProcessorBean> innerList = null;
							boolean isConstantExsit = false;
							while (rs.next()) {
								// construct calculate bean object
								currentCalBean = createCalProcessorBean(rs);
								// if record is not constant
								if (!"CONSTANT".equals(currentCalBean.getCalItemCode())) {
									// if it is the first record
									if (previousCalBean == null) {
										// set previous record to current one
										previousCalBean = currentCalBean;
										// initialize inner list
										if (innerList == null) {
											innerList = new ArrayList<CalculationProcessorBean>();
											innerList.add(currentCalBean);
										}
									} else {
										// if previous record and current has same conditions, put them in the same list
										if (previousCalBean.equals(currentCalBean)) {
											// initialize inner list
											if (innerList == null) {
												innerList = new ArrayList<CalculationProcessorBean>();
											}
											innerList.add(currentCalBean);
										} else {
											// if previous and current are not in same
											// conditions
											result.put(String.valueOf(groupNumber), innerList);
											groupNumber++;
											// re-initialize inner list
											innerList = new ArrayList<CalculationProcessorBean>();
											// add current record
											innerList.add(currentCalBean);
										}
										// reset previous record to current one
										previousCalBean = currentCalBean;
									}
								} else { // if record is constant, need to add to each group
									isConstantExsit = true;
									//if 
									if (0 < innerList.size()) {
										result.put(String.valueOf(groupNumber), innerList);
										// re-initialize inner list
										innerList = new ArrayList<CalculationProcessorBean>();
									}
									if (0 < result.size()) {
										// loop to add constant record to the group
										for (List<CalculationProcessorBean> value : result.values()) {
											addConstantInGroup(value, currentCalBean);
										}
									}
								}
							}
							if (!isConstantExsit && innerList != null && 0 < innerList.size()) {
								result.put(String.valueOf(groupNumber), innerList);
							}
							
							
							try {
								rs.close();
								cs.close();
							} catch (Exception e) {
								
							}
							return result;
				}
			});
			
		} finally {			
		}
		return processResult;
	}
	
	
	private void addConstantInGroup(List<CalculationProcessorBean> targetList, CalculationProcessorBean constantBean) {
		if (targetList != null && 0 < targetList.size()) {
			CalculationProcessorBean contentRecord = targetList.get(0);
			if (constantBean.isContantBelongSameGroup(contentRecord)) {
				targetList.add(constantBean);
			}
		}
	}

	public int insertCalculationItems(List<CalculationProcessorBean> calculationResultList, String createdUser, long instanceId) throws SQLException {
		String sqlString = "INSERT INTO HRS_CORE_ITEM_RESULT(ITEM_CODE, RULE_CODE, FIN_ELEMENT, LEDGER_ID, ACTUAL_FLAG,"
				 + "CURRENCY_TYPE, CURRENCY_CODE, SEGMENT1, SEGMENT2, SEGMENT3, SEGMENT4, SEGMENT5, SEGMENT6, SEGMENT7,SEGMENT8,SEGMENT9,"
	             + "SEGMENT10,SEGMENT11,SEGMENT12,SEGMENT13,SEGMENT14,SEGMENT15,SEGMENT16,SEGMENT17,SEGMENT18,SEGMENT19,SEGMENT20,PERIOD_NAME,"
	             + "PERIOD_NUM,PERIOD_YEAR,BEGIN_BALANCE_DR,BEGIN_BALANCE_CR,PERIOD_DR,PERIOD_CR,END_BALANCE_DR,END_BALANCE_CR,"
	             + "REQUEST_INSTANCE_ID,"
	             + "CREATION_DATE, CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		try {
			List<CalculationProcessorBean> batchList = new ArrayList<CalculationProcessorBean>();
			int counter = 0;
			for (CalculationProcessorBean calBean : calculationResultList) {
				counter++;
				calBean.setCreatedBy(createdUser);
				calBean.setUpdatedBy(createdUser);
				calBean.setRequestInstanceId(instanceId);
				batchList.add(calBean);
				if (counter == BATCH_SIZE) {
					this.jdbcTemplate.batchUpdate(sqlString, new HrsBatchPreparedStatementSetter(batchList));
					counter = 0;
					batchList = new ArrayList<CalculationProcessorBean>();
				}
			}

			if (counter != 0) {
				try {
					this.jdbcTemplate.batchUpdate(sqlString, new HrsBatchPreparedStatementSetter(batchList));
				} catch (Exception e) {
					throw new SQLException(e.toString());
				}
			}
			return 0;
		} catch (DataAccessException de ) {
			throw new SQLException (de);
	    } finally {

		}
	}

	private CalculationProcessorBean createCalProcessorBean(ResultSet rs) throws SQLException {
		CalculationProcessorBean result = new CalculationProcessorBean();
		//
		int col = 1;
		// set item rule code
		result.setRuleCode(rs.getString(col++));
		// set report item code
		result.setItemCode(rs.getString(col++));
		// set seq number
		result.setLineNum(rs.getInt(col++));
		// set operator
		result.setSign(rs.getString(col++));
		// set op iterm code
		result.setCalItemCode(rs.getString(col++));
		// set legerId
		result.setLedgerId(rs.getLong(col++));
		// set actual flag
		result.setActualFlag(rs.getString(col++));
		// set currency code
		result.setCurrencyCode(rs.getString(col++));
		// set currency type
		result.setCurrencyType(rs.getString(col++));
		// set element
		result.setFinElement(rs.getString(col++));
		// set period name
		result.setPeriodName(rs.getString(col++));
		// set period year
		result.setPeriodYear(rs.getString(col++));
		// set period number
		result.setPeriodNumber(rs.getString(col++));	
		// set segment 1
		result.setSegment1(rs.getString(col++));
		// set segment 2
		result.setSegment2(rs.getString(col++));
		// set segment 3
		result.setSegment3(rs.getString(col++));
		// set segment 4
		result.setSegment4(rs.getString(col++));
		// set segment 5
		result.setSegment5(rs.getString(col++));
		// set segment 6
		result.setSegment6(rs.getString(col++));
		// set segment 7
		result.setSegment7(rs.getString(col++));
		// set segment 8
		result.setSegment8(rs.getString(col++));
		// set segment 9
		result.setSegment9(rs.getString(col++));
		// set segment 10
		result.setSegment10(rs.getString(col++));
		// set segment 11
		result.setSegment11(rs.getString(col++));
		// set segment 12
		result.setSegment12(rs.getString(col++));
		// set segment 13
		result.setSegment13(rs.getString(col++));
		// set segment 14
		result.setSegment14(rs.getString(col++));
		// set segment 15
		result.setSegment15(rs.getString(col++));
		// set segment 16
		result.setSegment16(rs.getString(col++));
		// set segment 17
		result.setSegment17(rs.getString(col++));
		// set segment 18
		result.setSegment18(rs.getString(col++));
		// set segment 19
		result.setSegment19(rs.getString(col++));
		// set segment 20
		result.setSegment20(rs.getString(col++));

		// set begin balance dr
		result.setBeginBalanceDR(rs.getDouble(col++));
		// set begin balance cr
		result.setBeginBalanceCR(rs.getDouble(col++));
		// set period net dr
		result.setPeriodDR(rs.getDouble(col++));
		// set period net cr
		result.setPeriodCR(rs.getDouble(col++));
		// set end balance dr
		result.setEndBalanceDR(rs.getDouble(col++));
		// set end balance cr
		result.setEndBalanceCR(rs.getDouble(col++));

		return result;
	}

	private void setInsertParameters(HrsStatement stmt, CalculationProcessorBean calBean) throws SQLException {
		int i = stmt.getStartParamIndex();
		// set items code
		stmt.setString(i++, calBean.getItemCode());
		// set item group code
		stmt.setString(i++, calBean.getRuleCode());
		// set item financial element
		stmt.setString(i++, calBean.getFinElement());
		// set ledger id
		stmt.setLong(i++, calBean.getLedgerId());
		// set actual flag
		stmt.setString(i++, calBean.getActualFlag());
		// set currency type
		stmt.setString(i++, calBean.getCurrencyType());
		// set currency code
		stmt.setString(i++, calBean.getCurrencyCode());
		// set segment 1
		stmt.setString(i++, calBean.getSegment1());
		// set segment 2
		stmt.setString(i++, calBean.getSegment2());
		// set segment 3
		stmt.setString(i++, calBean.getSegment3());
		// set segment 4
		stmt.setString(i++, calBean.getSegment4());
		// set segment 5
		stmt.setString(i++, calBean.getSegment5());
		// set segment 6
		stmt.setString(i++, calBean.getSegment6());
		// set segment 7
		stmt.setString(i++, calBean.getSegment7());
		// set segment 8
		stmt.setString(i++, calBean.getSegment8());
		// set segment 9
		stmt.setString(i++, calBean.getSegment9());
		// set segment 10
		stmt.setString(i++, calBean.getSegment10());
		// set segment 11
		stmt.setString(i++, calBean.getSegment11());
		// set segment 12
		stmt.setString(i++, calBean.getSegment12());
		// set segment 13
		stmt.setString(i++, calBean.getSegment13());
		// set segment 14
		stmt.setString(i++, calBean.getSegment14());
		// set segment 15
		stmt.setString(i++, calBean.getSegment15());
		// set segment 16
		stmt.setString(i++, calBean.getSegment16());
		// set segment 17
		stmt.setString(i++, calBean.getSegment17());
		// set segment 18
		stmt.setString(i++, calBean.getSegment18());
		// set segment 19
		stmt.setString(i++, calBean.getSegment19());
		// set segment 20
		stmt.setString(i++, calBean.getSegment20());
		// set period name
		stmt.setString(i++, calBean.getPeriodName());
		// set period number
		stmt.setString(i++, calBean.getPeriodNumber());
		// set period year
		stmt.setString(i++, calBean.getPeriodYear());
		// set begin balance dr
		stmt.setDouble(i++, new BigDecimal(calBean.getBeginBalanceDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set begin balance cr
		stmt.setDouble(i++, new BigDecimal(calBean.getBeginBalanceCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_DR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set PERIOD_NET_CR
		stmt.setDouble(i++, new BigDecimal(calBean.getPeriodCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance dr
		stmt.setDouble(i++, new BigDecimal(calBean.getEndBalanceDR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set end balance cr
		stmt.setDouble(i++,new BigDecimal(calBean.getEndBalanceCR()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		// set request instance Id
		stmt.setLong(i++, calBean.getRequestInstanceId());
		// set create date
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set created by user
		stmt.setString(i++, calBean.getCreatedBy());
		// set update
		stmt.setDate(i++, DateUtil.toSqlDate(new Date()));
		// set updated by user
		stmt.setString(i++, calBean.getUpdatedBy());
	}



	@Override
	public void generateFinIndexAlloc(AllocRequestInstanceBean requestBean) throws SQLException {
		// TODO Auto-generated method stub
		HrsStatement stmt = null;
		String sqlString = "generate_fin_index";
		try {
			//this.jdbcTemplate.execute("{call hrs_core_item_result_api.generate_fin_index(?,?,?,?)}", action)			
			//String sql = "{call hae_alloc_engine_test.get_alloc_source_amount(?)}";
			String sql = "{? = call PACK_XXX.GET_USER_ID_BY_TOKEN(?)}";
			jdbcTemplate.execute(sql,
					new CallableStatementCallback() {
			            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
			                cs.setLong(1, requestBean.getInstanceId()); 
			                cs.execute();
			                //close db statement
			                try {
			                	if (cs != null) {
			                		cs.close();
			                	}
			                } catch (Exception e) {		                	
			                }
			                return 0; 
			            }
			        }); 
		} finally {
		}
	}
	@SuppressWarnings("unchecked")
	public int getItemContentsAlloc(AllocRequestInstanceBean requestBean) throws SQLException {
		int processResult = 0;
		try {
			processResult = (int) jdbcTemplate.execute(
					new CallableStatementCreator() {
						    public CallableStatement createCallableStatement(Connection con) throws SQLException {
						    	String storedProc =  "{? = call hae_alloc_engine_test.get_alloc_source_amount(?)}";
						    	int i = 1;
						    	CallableStatement cs = con.prepareCall(storedProc);
						    	//register ouput parameter
						    	cs.registerOutParameter(i++,  OracleTypes.INTEGER);// 注册输出参数的类型
						    	// set element
						    	cs.setLong(i++, requestBean.getInstanceId());

						    	return cs;
	                        }
					}, 
					new CallableStatementCallback() { 
						public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
							int result = 0;
							cs.execute();
							result = cs.getInt(1);
							try {
								//rs.close();
								cs.close();
							} catch (Exception e) {
								
							}			
							return result;
				}
			});
			
		} finally {	

		}
		return processResult;
	}
	public String getItemContentsAlloc(AllocRequestInstanceHistoryRecord requestBean) throws SQLException {
		String processResult = "";
		try {
			processResult = (String) jdbcTemplate.execute(
					new CallableStatementCreator() {
						    public CallableStatement createCallableStatement(Connection con) throws SQLException {
						    	String storedProc =  "{? = call hae_alloc_engine_api.run_alloc(?)}";
						    	int i = 1;
						    	CallableStatement cs = con.prepareCall(storedProc);
						    	//register ouput parameter
						    	cs.registerOutParameter(i++,  OracleTypes.NVARCHAR);// 注册输出参数的类型
						    	// set element
						    	cs.setLong(i++, requestBean.getHistoryId());
						    	return cs;
	                        }
					}, 
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
							String result ="";
							cs.execute();
							result = cs.getString(1);
							try {
								//rs.close();
								cs.close();
							} catch (Exception e) {
								
							}			
							return result;
				}
			});
			
		} finally {	

		}
		return processResult;
	}

	@Override
	public String getRollbackItemContentsAlloc(AllocRequestInstanceHistoryRecord requestHistoryBean)
			throws SQLException {
		String processResult = "";
		try {
			processResult = (String) jdbcTemplate.execute(
					new CallableStatementCreator() {
						    public CallableStatement createCallableStatement(Connection con) throws SQLException {
						    	String storedProc =  "{? = call hae_alloc_engine_api.rollback_alloc(?)}";
						    	int i = 1;
						    	CallableStatement cs = con.prepareCall(storedProc);
						    	//register ouput parameter
						    	cs.registerOutParameter(i++,  OracleTypes.NVARCHAR);// 注册输出参数的类型
						    	// set element
						    	cs.setLong(i++, requestHistoryBean.getHistoryId());
						    	return cs;
	                        }
					}, 
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
							String result ="";
							cs.execute();
							result = cs.getString(1);
							try {
								//rs.close();
								cs.close();
							} catch (Exception e) {
								
							}			
							return result;
				}
			});
			
		} finally {	

		}
		return processResult;
	}
	
}

*/