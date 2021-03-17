package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;



import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.engine.CalculationProcessorBean;

@Repository
public interface CalculationProcessorMapper {
	
	/*根据instanceId调用分摊规则运行函数*/
	public void getItemContentsAlloc(Map map)throws SQLException;
	public void generateFinIndex(RequestInstanceBean requestBean)throws SQLException;
	public int  insertCalculationItems(Map map)throws SQLException;
	public List<CalculationProcessorBean> getItemContents(Map<String, Object> param)throws SQLException;
}