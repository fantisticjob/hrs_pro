package com.hausontech.hrs.daoImpl;

import org.springframework.stereotype.Repository;

@Repository
public interface IBaseDao2 {

	public long getAutoGeneratedPrimaryKey(String sequenceName);
	
}
