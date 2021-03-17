package com.hausontech.hrs.bean.allocationManager;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class DimFilterLineBean
  extends AuditBean2
{
  private int filterLineId;
  private int filterHeaderId;
  private String valueLow;
  private String valueHigh;
  private String incExcIndicator;
  private String description;
  
  public int getFilterLineId()
  {
    return this.filterLineId;
  }
  
  public void setFilterLineId(int filterLineId)
  {
    this.filterLineId = filterLineId;
  }
  
  public int getFilterHeaderId()
  {
    return this.filterHeaderId;
  }
  
  public void setFilterHeaderId(int filterHeaderId)
  {
    this.filterHeaderId = filterHeaderId;
  }
  
  public String getValueLow()
  {
    return this.valueLow;
  }
  
  public void setValueLow(String valueLow)
  {
    this.valueLow = valueLow;
  }
  
  public String getValueHigh()
  {
    return this.valueHigh;
  }
  
  public void setValueHigh(String valueHigh)
  {
    this.valueHigh = valueHigh;
  }
  
  public String getIncExcIndicator()
  {
    return this.incExcIndicator;
  }
  
  public void setIncExcIndicator(String incExcIndicator)
  {
    this.incExcIndicator = incExcIndicator;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public boolean isEmpty()
  {
    if ((super.isEmpty()) || (StringUtils.isBlank(this.valueLow)) || (StringUtils.isBlank(this.valueHigh)) || (StringUtils.isBlank(this.incExcIndicator)) || (this.filterHeaderId == 0) || (this.filterLineId == 0)) {
      return true;
    }
    return false;
  }
}
