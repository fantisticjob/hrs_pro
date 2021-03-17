package com.hausontech.hrs.bean.allocationManager;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;

public class DimFilterHeaderBean
  extends AuditBean2
{
  private int filterHeaderId;
  private String filterHeaderName;
  private String dimensionSegment;
  private String type;
  private String description;
  
  public int getFilterHeaderId()
  {
    return this.filterHeaderId;
  }
  
  public void setFilterHeaderId(int filterHeaderId)
  {
    this.filterHeaderId = filterHeaderId;
  }
  
  public String getDimensionSegment()
  {
    return this.dimensionSegment;
  }
  
  public void setDimensionSegment(String dimensionSegment)
  {
    this.dimensionSegment = dimensionSegment;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
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
    if ((super.isEmpty()) || (StringUtils.isBlank(this.dimensionSegment)) || 
      (StringUtils.isBlank(this.type)) || (this.filterHeaderId == 0)) {
      return true;
    }
    return false;
  }
  
  public String getFilterHeaderName()
  {
    return this.filterHeaderName;
  }
  
  public void setFilterHeaderName(String filterHeaderName)
  {
    this.filterHeaderName = filterHeaderName;
  }
}
