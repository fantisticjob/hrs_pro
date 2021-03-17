package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hausontech.hrs.bean.reportSetting.ItemContentBean2;
import com.hausontech.hrs.daoImpl.BaseRowMapper;

public class ItemContentRowMapper extends BaseRowMapper implements RowMapper<ItemContentBean2> {

	@Override
	public ItemContentBean2 mapRow(ResultSet rs, int arg1) throws SQLException {
		ItemContentBean2 itemConetentBean = new ItemContentBean2();
		
		itemConetentBean.setItemContentId(rs.getInt("ITEM_CONTENT_ID"));
		itemConetentBean.setItemHeaderId(rs.getInt("ITEM_HEADER_ID"));
		itemConetentBean.setItemCode(rs.getString("ITEM_CODE"));
		itemConetentBean.setLineNum(rs.getInt("LINE_NUM"));
		itemConetentBean.setSign(rs.getString("SIGN"));
		
		itemConetentBean.setSegment1Low(rs.getString("SEGMENT1_LOW"));
		itemConetentBean.setSegment1Hign(rs.getString("SEGMENT1_HIGH"));
		itemConetentBean.setSegment1Type(rs.getString("SEGMENT1_TYPE"));
		
		itemConetentBean.setSegment2Low(rs.getString("SEGMENT2_LOW"));
		itemConetentBean.setSegment2Hign(rs.getString("SEGMENT2_HIGH"));
		itemConetentBean.setSegment2Type(rs.getString("SEGMENT2_TYPE"));
		
		itemConetentBean.setSegment3Low(rs.getString("SEGMENT3_LOW"));
		itemConetentBean.setSegment3Hign(rs.getString("SEGMENT3_HIGH"));
		itemConetentBean.setSegment3Type(rs.getString("SEGMENT3_TYPE"));
		
		itemConetentBean.setSegment4Low(rs.getString("SEGMENT4_LOW"));
		itemConetentBean.setSegment4Hign(rs.getString("SEGMENT4_HIGH"));
		itemConetentBean.setSegment4Type(rs.getString("SEGMENT4_TYPE"));
		
		itemConetentBean.setSegment5Low(rs.getString("SEGMENT5_LOW"));
		itemConetentBean.setSegment5Hign(rs.getString("SEGMENT5_HIGH"));
		itemConetentBean.setSegment5Type(rs.getString("SEGMENT5_TYPE"));
		
		itemConetentBean.setSegment6Low(rs.getString("SEGMENT6_LOW"));
		itemConetentBean.setSegment6Hign(rs.getString("SEGMENT6_HIGH"));
		itemConetentBean.setSegment6Type(rs.getString("SEGMENT6_TYPE"));
		
		itemConetentBean.setSegment7Low(rs.getString("SEGMENT7_LOW"));
		itemConetentBean.setSegment7Hign(rs.getString("SEGMENT7_HIGH"));
		itemConetentBean.setSegment7Type(rs.getString("SEGMENT7_TYPE"));
		
		itemConetentBean.setSegment8Low(rs.getString("SEGMENT8_LOW"));
		itemConetentBean.setSegment8Hign(rs.getString("SEGMENT8_HIGH"));
		itemConetentBean.setSegment8Type(rs.getString("SEGMENT8_TYPE"));
		
		itemConetentBean.setSegment9Low(rs.getString("SEGMENT9_LOW"));
		itemConetentBean.setSegment9Hign(rs.getString("SEGMENT9_HIGH"));
		itemConetentBean.setSegment9Type(rs.getString("SEGMENT9_TYPE"));
		
		itemConetentBean.setSegment10Low(rs.getString("SEGMENT10_LOW"));
		itemConetentBean.setSegment10Hign(rs.getString("SEGMENT10_HIGH"));
		itemConetentBean.setSegment10Type(rs.getString("SEGMENT10_TYPE"));
		
		itemConetentBean.setSegment11Low(rs.getString("SEGMENT11_LOW"));
		itemConetentBean.setSegment11Hign(rs.getString("SEGMENT11_HIGH"));
		itemConetentBean.setSegment11Type(rs.getString("SEGMENT11_TYPE"));
		
		itemConetentBean.setSegment12Low(rs.getString("SEGMENT12_LOW"));
		itemConetentBean.setSegment12Hign(rs.getString("SEGMENT12_HIGH"));
		itemConetentBean.setSegment12Type(rs.getString("SEGMENT12_TYPE"));
		
		itemConetentBean.setSegment13Low(rs.getString("SEGMENT13_LOW"));
		itemConetentBean.setSegment13Hign(rs.getString("SEGMENT13_HIGH"));
		itemConetentBean.setSegment13Type(rs.getString("SEGMENT13_TYPE"));
		
		itemConetentBean.setSegment14Low(rs.getString("SEGMENT14_LOW"));
		itemConetentBean.setSegment14Hign(rs.getString("SEGMENT14_HIGH"));
		itemConetentBean.setSegment14Type(rs.getString("SEGMENT14_TYPE"));
		
		itemConetentBean.setSegment15Low(rs.getString("SEGMENT15_LOW"));
		itemConetentBean.setSegment15Hign(rs.getString("SEGMENT15_HIGH"));
		itemConetentBean.setSegment15Type(rs.getString("SEGMENT15_TYPE"));
		
		itemConetentBean.setSegment16Low(rs.getString("SEGMENT16_LOW"));
		itemConetentBean.setSegment16Hign(rs.getString("SEGMENT16_HIGH"));
		itemConetentBean.setSegment16Type(rs.getString("SEGMENT16_TYPE"));
		
		itemConetentBean.setSegment17Low(rs.getString("SEGMENT17_LOW"));
		itemConetentBean.setSegment17Hign(rs.getString("SEGMENT17_HIGH"));
		itemConetentBean.setSegment17Type(rs.getString("SEGMENT17_TYPE"));
		
		itemConetentBean.setSegment18Low(rs.getString("SEGMENT18_LOW"));
		itemConetentBean.setSegment18Hign(rs.getString("SEGMENT18_HIGH"));
		itemConetentBean.setSegment18Type(rs.getString("SEGMENT18_TYPE"));
		
		itemConetentBean.setSegment19Low(rs.getString("SEGMENT19_LOW"));
		itemConetentBean.setSegment19Hign(rs.getString("SEGMENT19_HIGH"));
		itemConetentBean.setSegment19Type(rs.getString("SEGMENT19_TYPE"));
		
		itemConetentBean.setSegment20Low(rs.getString("SEGMENT20_LOW"));
		itemConetentBean.setSegment20Hign(rs.getString("SEGMENT20_HIGH"));
		itemConetentBean.setSegment20Type(rs.getString("SEGMENT20_TYPE"));
		
		this.mapAuditColumns(rs, itemConetentBean);
		// TODO Auto-generated method stub
		return itemConetentBean;
	}

}
