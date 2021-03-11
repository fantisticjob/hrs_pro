package com.hausontech.hrs.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	private static DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	private static final long MSECONDS_OF_ONE_DAY = 60 * 60 * 1000 * 24;

	private static final Calendar cal = Calendar.getInstance();
	

	  private Vector holidays = new Vector();

	  /** A date points to "01/01/1970 00:00:00".
	    */
	  public static Date MIN_DATE = getMinDate();

	  /** A date points to "01/01/3000 00:00:00".
	    */
	  public static Date MAX_DATE = getMaxDate();

	  /** This method contains a fixed date string "19700101" to create the MinDate.
	    * @return a Date type, with the date of "01/01/1970".
	    */
	  private static Date getMinDate()
	  {
		  try {
		    return dayStart(parse("yyyyMMdd", "19700101"));
		  }
		  catch (Exception e ) {
		    throw new RuntimeException(e.toString());
		  }
	  }

	  /** This method contains a fixed date string "30000101" to create the MaxDate.
	    * @return a Date type, with the date of "01/01/3000".
	    */
	  private static Date getMaxDate()
	  {
		  try {
		    return dayStart(parse("yyyyMMdd", "30000101"));
		  }
		  catch (Throwable e ) {
		    throw new RuntimeException(e.toString());
	    }
	  }

	    /** Constructor. It will calculate the federal holidays in the past year
	      * and the future year.
	      */
	    public DateUtil()
	    {
	        //Let's store current year +/- 1 worth of holidays
	        //
	        setDefaultHolidays(1);
	    }

	    /** Return a list of holidays maintained by the object/
	      * @return a String
	      */
	    public String toString()
	    {
	        StringBuffer buf = new StringBuffer("DateUtil: holiday list-->");

	        for(int i=0; i < holidays.size(); i++) {
	            buf.append("\n");
	            buf.append(holidays.get(i));
	        }
	        buf.append("\n");

	        return new String(buf);
	    }

	    /** default to US federal holidays, follow rules in 5 U.S.C. 6103
	      * regulation in determining where they fall (see www.opm.gov/fedhol/2000.htm
	      * for details). Set schedule for current year plus number of years both forward
	      * and backward as given by the argument.
	      * @param numYears, numYears >= 0 to the past and to the future.
	      */
	    private void setDefaultHolidays(int numYears)
	    {
	        Date curdt = dayStart(new Date());
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(curdt);
	        int year = cal.get(Calendar.YEAR);

	        for (int curYear = year - numYears; curYear <= year + numYears; curYear++) {

	            //New Years Day
	            cal.set(curYear, Calendar.JANUARY, 1);
	            holidays.add(rollHoliday(cal.getTime()));

	            //Martin Luther King Birthday (3rd monday in january)
	            cal.set(curYear, Calendar.JANUARY, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
	            holidays.add(cal.getTime());

	            //Presidents' Day (3rd monday in february)
	            cal.set(curYear, Calendar.FEBRUARY, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
	            holidays.add(cal.getTime());

	            //Memorial Day (last monday in may)
	            cal.set(curYear, Calendar.MAY, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
	            holidays.add(cal.getTime());

	            //Independence Day
	            cal.set(curYear, Calendar.JULY, 4);
	            holidays.add(rollHoliday(cal.getTime()));

	            //Labor Day (1st monday in september)
	            cal.set(curYear, Calendar.SEPTEMBER, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
	            holidays.add(cal.getTime());

	            //Columbus Day (2nd monday in october)
	            cal.set(curYear, Calendar.OCTOBER, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
	            holidays.add(cal.getTime());

	            //Veterans' Day
	            cal.set(curYear, Calendar.NOVEMBER, 11);
	            holidays.add(rollHoliday(cal.getTime()));

	            //Thanksgiving (4th thursday in november)
	            cal.set(curYear, Calendar.NOVEMBER, 1);
	            cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	            cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
	            holidays.add(cal.getTime());

	            //Christmas Day
	            cal.set(curYear, Calendar.DECEMBER, 25);
	            holidays.add(rollHoliday(cal.getTime()));
	        }
	    }

	    /** Parse a string which is in the format of "yyyyMMddHHmmssSSS" into a
	      * java.util.Date object. Note, this method is not efficient if it
	      * is used to parse a lot of date string since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a String indicate a date.
	      * @return a Date object indicated by val.
	      * @exception ParseException, java defined exception for parsing errors.
	      */
	    public static Date parse(String val) throws java.text.ParseException
	    {
	      if(val == null || val.trim().length() == 0) return null;
	      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
	      return formatter.parse(val);
	    }

	    /** Parse a String val according to format string format. Format is defined
	      * by java.text.SimpleDateFormat. Note, this method is not efficient if it
	      * is used to parse a lot of date string since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a String represent date.
	      * @param format, a String user provided format.
	      * @return a Date object indicated by val.
	      * @exception ParseException, java defined exception for parsing errors.
	      */
	    public static Date parse(String format, String val) throws java.text.ParseException
	    {
	      if(val == null || val.trim().length() == 0) return null;
	      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
	      return formatter.parse(val);
	    }

	    /** Parse a String val according to format string format and timezone.
	      * Format is defined by java.text.SimpleDateFormat Note, this method is not efficient if it
	      * is used to parse a lot of date string since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a String represent date.
	      * @param format, a String user provided date format.
	      * @param timezone, a String user provided a timezone info, internally convert to TimeZone obj.
	      * @return a Date object indicated by val.
	      * @exception ParseException, java defined exception for parsing errors.
	      */
	    public static Date parse(String format, String val, String timezone) throws java.text.ParseException
	    {
	      if(val == null || val.trim().length() == 0) return null;
	      TimeZone tz = null;
	      if(timezone == null || timezone.length() == 0 )
	      {
	        tz = TimeZone.getDefault();
	      }else{
	        tz = TimeZone.getTimeZone(timezone);
	      }
	      return parse(format, val, tz);
	    }

	    /** Parse a String val according to format string format and timezone.
	      * Format is defined by java.text.SimpleDateFormat
	      * Note, this method is not efficient if it
	      * is used to parse a lot of date string since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a String represent date.
	      * @param format, a String user provided date format.
	      * @param timezone, a TimeZone object.
	      * @return a Date object indicated by val.
	      */
	    public static Date parse(String format, String val, TimeZone timezone)
	     throws java.text.ParseException
	    {
	      if(val == null || val.trim().length() == 0) return null;
	      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
	      formatter.setTimeZone(timezone);
	      return formatter.parse(val);
	    }

	    /** Format a java.util.Date val into a string with format as "yyyyMMddHHmmssSSS".
	      * Note, this method is not efficient if it
	      * is used to format a lot of dates since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a Date object be converted to String.
	      * @return a String, date.
	      */
	    public static String format(Date val)
	    {
	      if(val == null ) return null;
	      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
	      return formatter.format(val);
	    }

	    /** Format a java.util.Date val into a string with format specified by
	      * string format. See java.text.SimpleDateFormat for available formats
	      * Note, this method is not efficient if it
	      * is used to format a lot of dates since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a Date object.
	      * @param format, a String user provided date format.
	      * @return a String, date.
	      */
	    public static String format(String format, Date val)
	    {
	      if(val == null) return null;
	      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
	      return formatter.format(val);
	    }

	    /** Format a java.util.Date val into a string with format specified by
	      * string format and timezone. See java.text.SimpleDateFormat
	      * for available formats. Note, this method is not efficient if it
	      * is used to format a lot of dates since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a Date object.
	      * @param format, a String user provided date format.
	      * @param timezone, a String user provided a timezone info, internally convert to TimeZone obj.
	      * @return a String, date.
	      */
	    public static String format(String format, Date val, String timezone)
	    {
	      if(val == null) return null;
	      TimeZone tz = null;
	      if(timezone == null || timezone.length() == 0 )
	      {
	        tz = TimeZone.getDefault();
	      }else{
	        tz = TimeZone.getTimeZone(timezone);
	      }
	      return format(format, val, tz);
	    }

	    /** Format a java.util.Date val into a string with format specified by
	      * string format and timezone. See java.text.SimpleDateFormat
	      * for available formats. Note, this method is not efficient if it
	      * is used to format a lot of dates since a SimpleDateFormat
	      * is created each time the method is called. In this case,
	      * use your own SimpleDateFormat.
	      * @param val, a Date object.
	      * @param format, a String user provided date format.
	      * @param timezone, a String user provided a timezone info, internally convert to TimeZone obj.
	      * @return a String, date.
	      */
	    public static String format(String format, Date val, TimeZone timezone)
	    {
	      if(val == null) return null;
	      java.text.SimpleDateFormat formatter =
		new java.text.SimpleDateFormat(format);
	      formatter.setTimeZone(timezone);
	      return formatter.format(val);
	    }

	    /** set a java.util.Date dt to the end of the date, that is, 11:59:59:999pm
	      * @param dt, the any given date, Date object.
	      * @return the new Date, on the same day but last hour, last minute, last second.
	      */
	    public static Date dayEnd(Date dt)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.set(Calendar.HOUR_OF_DAY, 23);
	        cal.set(Calendar.MINUTE, 59);
	        cal.set(Calendar.SECOND, 59);
	        cal.set(Calendar.MILLISECOND, 999);
	        cal.set(Calendar.AM_PM, Calendar.PM);

	        return cal.getTime();
	    }

	    /** Set a list of user provided holidays, overwrites the default holiday lists
	      * @param datesVec new holidays to be set.
	      */
	    public void setHolidays(Vector datesVec)
	    {
	        holidays = datesVec;
	    }

	    /** Add a new date to the holiday list
	      * @param dt, a Date object, new holiday.
	      */
	    public void addHoliday(Date dt)
	    {
	      Date newDate = dayStart(dt);
	        for(int i = 0; i < holidays.size(); i++) {
	            Date oldDate = dayStart((Date)holidays.get(i));
	            if(newDate.compareTo(oldDate) == 0) {
	                return;
	            }
	        }
	        holidays.add(dt);
	    }

	    /** Check whether a day is federal holiday
	      * @param dt, a Date object to be checked
	      * @return boolean true if dt is a holiday, false if not.
	      */
	    public boolean isHoliday(Date dt)
	    {
	        boolean found = false;
	        Date newDate = dayStart(dt);
	        for(int i = 0; i < holidays.size(); i++) {
	            if(newDate.compareTo(dayStart((Date)holidays.get(i))) == 0) {
	                found = true;
	                break;
	            }
	        }

	        return found;
	    }

	    /** Check whether a date is Saturday
	      * @param dt the Date object to be checked.
	      * @return boolean true if dt is a Saturday, false if not.
	      */
	    public static boolean isSaturday(Date dt)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt); int day = cal.get(Calendar.DAY_OF_WEEK);

	        return (day == Calendar.SATURDAY) ? true : false;
	    }


	    /** Check whether a date is Sunday
	      * @param dt, a Date object in question.
	      * @return simple boolean if found "true", not found "false".
	      */
	    public static boolean isSunday(Date dt)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        int day = cal.get(Calendar.DAY_OF_WEEK);

	        return (day == Calendar.SUNDAY) ? true : false;
	    }

	    /** Roll up a day that is fall on a Sunday.
	      * @param dt, a Date Object, if on Sunday it will be rolled forward one day.
	      * @return a Date, a new Date if did roll up; keeps dt when not.
	      */
	    private Date rollHoliday(Date dt)
	    {
	        Date rolled;

	        //banks will open on Friday, no need to roll
	        //if(isSaturday(dt)) {
	        //    rolled = addDays(dt, -1);
	        //}
	        if(isSunday(dt)) {
	            rolled = addDays(dt, 1);
	        }
	        else {
	            rolled = dt;
	        }

	        return rolled;
	    }

	    /** Check whether a date is on weekend, Saturday and Sunday.
	      * @param dt the Date object to be checked
	      * @return boolean true if is weekend, false if not.
	      */
	    public static boolean isWeekend(Date dt)
	    {
	        return isSaturday(dt) || isSunday(dt);
	    }

	    /** Check whether a date is not a business day. Saturday and Sunday are not
	      * business dates. Federal holiday is a little tricky:<br>
	      * a. If the holiday falls on a weekday, then it is a non-business date.
	      * b. If the holiday falls on Sunday, then the holiday is moved to next Monday
	      *    and that Monday is considered a non-business date.
	      * c. If the holiday falls on Saturday, then the holiday is moved to previous
	      *    Friday. However, this Friday is <b>not</b> considered a
	      *    non-business date.
	      * @param dt, a Date object in question.
	      * @return simple boolean if found "true", not found "false".
	      */
	    public boolean isNonBusinessDay(Date dt)
	    {
	        return isHoliday(dt) || isWeekend(dt);
	    }

	    /** Add number of full business days(numDays) to Date dt.
	      * For example, if Monday and Tuesday are business days and you add
	     * 1 to Monday, then the result will be Tuesday. Things get tricky
	     * if date dt is a non business day. In this case, it will be
	     * "rounded" to its nearest business day first before add numDays of bussiness
	     * days. For example, we have 06/13(Thu), 06/14(Fri), 06/15(Sat), 06/16(Sun), 06/17(Mon) and 06/18(Tue)
	     * and non of them are holidays. Assume the date dt is 06/15(Sat) and numDays
	     * is 1. Because numDays >0, date dt is rounded to the least business day ahead, 06/17
	     * and then add one, the result becomes 06/18. If numDays is -1 and because it < 0,
	     * date dt is rounded to the biggest business day before, 06/14 and then minus one, the
	     * result is 06/13.
	      * @param dt, the base Date to be added with number of business days.
	      * @param numDays, the number of days to be added to the base date.
	     *   Can be positive or negative. If zero, date stays the same even if it is a non business day.
	      * @return a Date object with numDays of business days added.
	      */
	    public Date addBusinessDays(Date dt, int numDays)
	    {
	        //temporary solution: numDays==0 will make the for
	        //loop indefinite,
	        if(numDays == 0) {
	          return dt;
	        }
	        int direction = (numDays >= 0) ? 1 : -1;
	        numDays = (numDays >= 0 ) ? numDays : -1 * numDays;

	        Date curdt = dt;

	        //first round the date
	        while(isNonBusinessDay(curdt)){
	          curdt =  addDays(curdt, direction);
	        }
	        //now add business days
	        int count = 0;
	        while(count < numDays)
	        {
	            //loop to find next business date
	            do{
	                curdt = addDays(curdt, direction);
	            }while(isNonBusinessDay(curdt));
	            //find next business date
	            count++;//increate business date count
	        }
	        //for(int days = 0; days < numDays; ) {
	        //    if(!isNonBusinessDay(curdt)) {
	        //        System.out.print(""+curdt+" is business date, add one");
	        //        days += direction;
	        //    }

	            //if(days == numDays) {
	            //    break;
	            //}
	        //    curdt = addDays(curdt, direction);
	        //}
	        return curdt;
	    }

	    /** Return the first non-federal holiday after this date
	      * if this date is a federal holiday. Return the same date if the date is not
	      * a federal holiday
	      * @param dt, a Date to be examined.
	      * @return a Date object.
	      */
	    public Date getFirstNonHolidayAhead(Date dt)
	    {
	      while(isHoliday(dt)){
	        dt = addDays(dt, 1);
	      }
	      return dt;
	    }

	   /** Return the first business date after this date if this date is a non-business date.
	     * Return the same date if the date is a business date
	     * @param dt, a Date to be examined.
	     * @return a Date object.
	     */
	   public Date getFirstBusinessDayAhead(Date dt)
	   {
	     while(isHoliday(dt) || isWeekend(dt)){
	       dt = addDays(dt, 1);
	     }
	     return dt;
	   }


	    /** same as addBusinessDays(dayStart(dt), -days). It will first set
	      * date dt to the start of day and then it is moved to the past by
	      * numOfBuzDays business days. For example, if today is 12/17/2001,
	      * which is a Monday and not a holiday and numOfBuzDays is 2, then
	      * the result will be
	      *@param numOfBuzDays number of business days ahead
	      */
	    public Date getFullBusinessDaysPrior(Date dt, int numOfBuzDays)
	    {
	        return addBusinessDays(dayStart(dt), - numOfBuzDays);
	    }

	    /** same as addBusinessDays(dayEnd(dt), days)
	     */
	    public Date getFullBusinessDaysAhead(Date dt, int days)
	    {
	        return addBusinessDays(dayEnd(dt), days);
	    }

	    /** Change the date to the start of the date, with hour, minute, second and milliseconds
	      * are all zeros(AM)
	      * @param dt, to be striped of hour, minutes and seconds.
	      * @return a Date object on the same day as dt.
	      */
	    public static Date dayStart(Date dt)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.set(Calendar.AM_PM, Calendar.AM);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime();
	    }

	    /** Get the next start date of Date dt. For example, if the Date is
	      * 10/19/1967 12:44:56:138 PM (format is MM/dd/yyyy HH:mm:ss:SSS), then this method
	      * will change it to 10/20/1967 12:00:00:000 AM.
	      * @param dt, to be added by one day then striped of hour, minutes and seconds.
	      * @return a Date object.
	      */
	    public static Date nextDayStart(Date dt)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.DATE, 1);
	        cal.set(Calendar.AM_PM, Calendar.AM);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime();
	    }

	    /** add number of days into current Date
	      * Note the passed in data is not changed, the returned date is changed
	      * @param dt, the target Date.
	      * @param days, the increment of days, can be negative.
	      * @return a new Date object.
	      */
	    public static Date addDays(Date dt, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.DATE, days);

	        return cal.getTime();
	    }

	    /** add number of hours into current Date
	      * Note the passed in data is not changed, the returned date is changed
	      * @param dt, the target Date.
	      * @param hours, the increment of hours, can be negative.
	      * @return a new Date object.
	      */
	    public static Date addHours(Date dt, int hours)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.HOUR, hours);

	        return cal.getTime();
	    }

	    /** add number of minutes into current Date
	      * Note the passed in data is not changed, the returned date is changed
	      * @param dt, the target Date.
	      * @param minutes, the increment of minutes, can be negative.
	      * @return a new Date object.
	      */
	    public static Date addMinutes(Date dt, int minutes)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.MINUTE, minutes);

	        return cal.getTime();
	    }


	    /**Set year of the date. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setYear() method
	      *@param date Date whose year to be set
	      *@param year the year to be set, four digits
	      *@return a new Date whose year is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setYear(Date date, int year)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.YEAR, year);

	       return cal.getTime();
	    }

	    /**Set month of the date. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setMonth() method
	      *@param date Date whose date field to be set
	      *@param month the month to be set
	      *@return a new Date whose month is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setMonth(Date date, int month)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.MONTH, month);

	       return cal.getTime();
	    }

	    /**Set date of the Date object. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setDate() method
	      *@param date Date whose date field to be set
	      *@param dt the date to be set
	      *@return a new Date whose date is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setDate(Date date, int dt)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.DATE, dt);

	       return cal.getTime();
	    }

	    /**Set hour of the date. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setHour() method
	      *@param date Date whose hour to be set
	      *@param hour the hour to be set
	      *@return a new Date whose hour is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setHour(Date date, int hour)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.HOUR, hour);

	       return cal.getTime();
	    }

	    /**Set minute of the date. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setMinute() method
	      *@param date Date whose minute to be set
	      *@param minute the minute to be set
	      *@return a new Date whose minute is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setMinute(Date date, int minute)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.MINUTE, minute);

	       return cal.getTime();
	    }

	    /**Set second of the date. Use the non-deprecated Calendar.set() method instead
	      *of the deprecated Date.setSecond() method
	      *@param date Date whose second to be set
	      *@param second the second to be set
	      *@return a new Date whose second is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setSecond(Date date, int second)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.SECOND, second);

	       return cal.getTime();
	    }

	    /**Set milli-second of the date. Use the Calendar.set() method
	      *@param date Date whose milli-second to be set
	      *@param millisecond the milli-second to be set
	      *@return a new Date whose milli-second is set. Note, the passed in param, Date date
	      *is not changed
	      */
	    public static Date setMilliSecond(Date date, int millisecond)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(date);
	       cal.set(Calendar.MILLISECOND, millisecond);

	       return cal.getTime();
	    }

	    /**Convert a date to calendar. A lot of operations on Date have been deprecated
	      *, you can use this method to convert a Date to Calendar so that you
	      *can use non-deprecated date operations.
	      *@param dt the date to be converted
	      *@return Calendar the calendar which is equivalent to the param dt
	      */
	    public static Calendar toCalendar(Date dt)
	    {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(dt);
	       return cal;
	    }

	    /**Convert a calendar back to date.
	      *@param cal the calendar
	      *@return Date which is equivalent to the calendar
	      */
	    public static Date toDate(Calendar cal)
	    {
	      return cal.getTime();
	    }


	    /** add number of Months into current Date
	      * Note the passed in data is not changes, the returned date is changed
	      * @param dt, the target Date.
	      * @param mons, the increment of months.
	      * @return a new Date object.
	      */
	    public static Date addMonths(Date dt, int mons)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.MONTH, mons);
	        return cal.getTime();
	    }

	    /** add number of years into current Date
	      * Note the passed in data is not changes, the returned date is changed
	      * @param dt, the target Date.
	      * @param years, the increment of years.
	      * @return a new Date object.
	      */
	    public static Date addYears(Date dt, int years)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.add(Calendar.YEAR, years);
	        return cal.getTime();
	    }

	    /** same as addDays(dayStart(dt), days). param days should <= 0
	      */
	    public static Date getFullDaysPrior(Date dt, int days)
	    {
	        days = (days <= 0) ? days : 0;
	        return addDays(dayStart(dt), days);
	    }

	    /** same as addDays(dayEnd(dt), days), param days should >= 0
	      */
	    public static Date getFullDaysAhead(Date dt, int days)
	    {
	        days = (days >= 0) ? days : 0;
	        return addDays(dayEnd(dt), days);
	    }

	  /** Convert a java.util.Date to java.sql.Date, which only include
	   * year/month/day information.
	    * java.util.Date could not be used for JDBC access.
	    * @param d, a java.util.Date object.
	    * @return a java.sql.Date object.
	    */
	  public static java.sql.Date toSqlDate(java.util.Date d){
	    if(d == null)
	      return null;
	    else
	      return new java.sql.Date(d.getTime());
	  }

	  /** Convert a java.sql.Date to java.util.Date
	    * @param d, a java.sql.Date object.
	    * @return a java.util.Date object.
	    */
	  public static java.util.Date toUtilDate(java.sql.Date d){
	    if(d == null )
	      return null;
	    else
	      return new java.util.Date(d.getTime());
	  }


	  /** Take a java.sql.Date and convert it into a java.sql.Timestamp
	    * @param d, a java.sql.Date.
	    * @return a java.sql.Timestamp object.
	    */
	  public static java.sql.Timestamp toSqlTimestamp(java.sql.Date d){
	    if(d == null)
	      return null;
	    else {

	      Calendar cal = Calendar.getInstance();
	      cal.setTime(DateUtil.toUtilDate(d));
	      cal.set(Calendar.AM_PM, Calendar.AM);
	      cal.set(Calendar.HOUR_OF_DAY, 0);
	      cal.set(Calendar.MINUTE, 0);
	      cal.set(Calendar.SECOND, 0);
	      cal.set(Calendar.MILLISECOND, 0);

	      return new java.sql.Timestamp(cal.getTime().getTime());
	    }
	  }

	  /** Take a java.util.Date and convert it into java.sql.Timestamp
	    * Note, the nano seconds of the timestamp will be set to 0.
	    * @param d, a java.util.Date object.
	    * @return a java.sql.TimeStamp object. null if the passed in java.util.Date
	    * is null.
	    */
	  public static java.sql.Timestamp toSqlTimestamp(java.util.Date d){
	    if(d == null)
	      return null;
	    else {
	      java.sql.Timestamp ts = new java.sql.Timestamp(d.getTime());
	      //Calendar cal = Calendar.getInstance();
	      //cal.setTime(d);
	      //System.out.println("milli = "  + cal.get(Calendar.MILLISECOND));
	      //ts.setNanos(cal.get(Calendar.MILLISECOND)*1000000);
	      //System.out.println("to sql timestamp="+ts.getTime()+",nanos="+ts.getNanos());
	      ts.setNanos(0);
	      return ts;
	    }
	  }

	  /** Convert a java.sql.TimeStamp into java.util.Date.
	    * All hour, minute and seconds remains.
	    * @param d, a java.sql.TimeStamp object.
	    * @return a java.util.Date object.
	    */
	  public static java.util.Date toUtilDate(java.sql.Timestamp d){

	    if(d == null )
	      return null;
	    else {
	       //System.out.println("from timestamp="+d.getTime()+",nanos="+d.getNanos());
	      return new java.util.Date(d.getTime()+d.getNanos()/1000000);
	    }
	  }
	  
		public static Date getCurrentDate() {
			return new Date();
		}
		
		
		public final static Date getCurrentTime() {
			return new Date(System.currentTimeMillis());
		}
		
		public final static String getCurrentDateStr(){
			return dateFormatter.format(System.currentTimeMillis());
		}
		
		public final static String getCurrentDateTimeStr(){
			return dateTimeFormatter.format(System.currentTimeMillis());
		}
		
		public final static String getCurrentTimeStr(String format){
			SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
			return simpledateformat.format(System.currentTimeMillis());
		}

		public static String getDateString(Date date) {
			if(date==null){
				return "";
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(date);
		}

		public static String getDateString(Date date, String format) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		}

		public static String getDateTimeString(Date date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		}

		public static String getDateTimeString(Date date, String format) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		}

		public static Date parseDate(String date){
			if(StringUtils.isEmpty(date.trim())){
				return null;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static Date parseDate(String date, String format)
				throws ParseException {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(date);
		}

		public static Date getDateTimeFormtDate(Date date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate =  df.format(date);
			return parseDate(strDate);
			
		}
		
		public static Date parseDateTime(String date) throws ParseException {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(date);
		}

		public static Date parseDateTime(String date, String format)
				throws ParseException {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(date);
		}
		
		/**
		 * @param date
		 * @return
		 * @author Administrator
		 * @date 2012-6-18
		 * @see
		 */
		public static Date getFirstDayOfMonth(Date date) {
			String year = getYear(date);
			String month = getMonth(date);
			month = month.substring(4, month.length());
			return getFirstDayOfMonth(Integer.parseInt(year), 
					Integer.parseInt(month));
		}

		//
		public static Date getFirstDayOfMonth(int year, int month) {
			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.YEAR, year);
			cl.set(Calendar.MONTH, month - 1);
			cl.set(Calendar.DAY_OF_MONTH, 1);
			return cl.getTime();
		}

		public static Date getLastDayOfMonth(int year, int month) {
			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.YEAR, year);
			cl.set(Calendar.MONTH, month - 1);
			cl.set(Calendar.DAY_OF_MONTH,
					cl.getActualMaximum(Calendar.DAY_OF_MONTH));
			return cl.getTime();
		}
		
		
		public static int getFirstDateOfMonth(int year, int month) {

			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.YEAR, year);
			cl.set(Calendar.MONDAY, month - 1);
			return cl.getActualMinimum(Calendar.DAY_OF_MONTH);
		}

		
		public static int getLastDateOfMont(int year, int month) {
			Calendar cl = Calendar.getInstance();
			cl.set(Calendar.YEAR, year);
			cl.set(Calendar.MONDAY, month - 1);
			return cl.getActualMaximum(Calendar.DAY_OF_MONTH);
		}


		public static int getDays(Date fromDate, Date endDate) {

			long from = fromDate.getTime();
			long end = endDate.getTime();

			return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
		}

		public static String getTakeTime(Date startDate, Date endDate) {
			int minute = 0;
			try {
				minute = (int) (endDate.getTime() - startDate.getTime())
						/ (1000 * 60);
				return String.valueOf(minute);
			} catch (Exception e) {
				return "";
			}

		}

		

		public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date) {
			if (date == null)
				return null;
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
			return jd;
		}

		public static java.sql.Date convertObjToSQLDate(Object obj) {
			if (obj == null || "".equals(obj.toString().trim()))
				return null;

			Calendar cl = Calendar.getInstance();
			cl.setTime((java.util.Date) obj);
			java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
			return jd;
		}

		public static java.sql.Timestamp convertUtilDateToSQLDateWithTime(
				java.util.Date date) {
			if (date != null) {
				return new java.sql.Timestamp(date.getTime());
			} else {
				return null;
			}
		}

		public static java.sql.Date convertStringToSQLDate(String dateString) {
			return (convertObjToSQLDate(dateString));
		}

		public static java.sql.Date convertToSQLDateWithoutTime(java.util.Date date) {
			String dateString = dateFormatter.format(date);
			return convertStringToSQLDate(dateString);
		}

		

		/**
		 * get day index of a week for the specific date
		 * 
		 * @param date
		 *            the specific date
		 * @return day index of a week,Mon. is 1,Tues. is 2,Wed. is 3,Thurs. is
		 *         4,Fri. is 5,Sat. is 6,Sun. is 7
		 * @throws ParseException
		 */
		public static int getDayOfWeek(Date date) {
			if (date == null)
				return 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (result == 0)
				result = 7;
			return result;
		}

		/**
		 * get day index of a week for the specific date
		 * 
		 * @param date
		 *            the specific date
		 * @return day index of a week,Sun. is 1,Mon. is 2,Tues. is 3,Wed. is
		 *         4,Thurs. is 5,Fri. is 6,Sat. is 7
		 * @throws ParseException
		 */
		public static int getDayOfWeek2(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_WEEK);
		}

		/**
		 * add days to the specific date
		 * 
		 * @param SourceDate
		 *            the specific date
		 * @param days
		 *            day count to be added
		 * @return java.util.Date object after add days
		 * @throws ParseException
		 */
		public static Date addDate(Date sourceDate, int days) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sourceDate);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}

		/**
		 * add days to the specific date
		 * 
		 * @param SourceDate
		 *            the specific date
		 * @param days
		 *            day count to be added
		 * @return java.util.Date object after add days
		 * @throws ParseException
		 */
		public static Date addDate(String stringDate, int days)  throws ParseException{
			Date sourceDate = parseDate(stringDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sourceDate);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}

		/**
		 * @param stringDate
		 * @return
		 */
		public static String addOneDay(Date sourceDate) {
			Date newDate = addDate(sourceDate, 1);
			return getDateTimeString(newDate);

		}

		/**
		 * 
		 * @param from
		 * @param to
		 * @return
		 * @throws ParseException
		 */
		public static long subDate(Date from, Date to) throws ParseException {
			long value = Math.abs(to.getTime() - from.getTime());
			return value / MSECONDS_OF_ONE_DAY;
		}

		/**
		 * 
		 * @param from
		 * @param to
		 * @return
		 * @throws ParseException
		 */
		public static long subDate(String from, String to) throws ParseException {
			return subDate(parseDateTime(from), parseDateTime(to));
		}


		public static int compareDate(Date firstDate, Date secondDate) {
			return firstDate.compareTo(secondDate);
		}

		

		

		
		public static final String getYear(Date date) {
			cal.setTime(date);
			return String.valueOf(cal.get(1));
		}

		
		public static final String getMonth(Date date) {
			String s = "";
			cal.setTime(date);
			if (cal.get(2) < 9)
				s = "0";
			return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1);
		}

		/**
		 *
		 * 
		 * @param date
		 * @return
		 */
		public static final String getDay(Date date) {
			String s = "";
			String s1 = "";
			cal.setTime(date);
			if (cal.get(2) < 9)
				s = "0";
			if (cal.get(5) < 10)
				s1 = "0";
			return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1)
					+ s1 + String.valueOf(cal.get(5));
		}

		/**
		 * 
		 * 
		 * @param date
		 * @return
		 */
		public static final String getWeek(Date date) {
			String s = "";
			cal.setTime(date);
			if (cal.get(3) < 10)
				s = "0";
			return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(3));
		}

		/**
		 * 
		 * 
		 * @param date
		 * @return
		 */
		public static final String getSeason(Date date) {
			cal.setTime(date);
			int i = cal.get(2);
			byte byte0 = 1;
			if (i >= 3 && i <= 5)
				byte0 = 2;
			if (i >= 6 && i <= 8)
				byte0 = 3;
			if (i >= 9 && i <= 11)
				byte0 = 4;
			return String.valueOf(cal.get(1)) + "0" + String.valueOf(byte0);
		}


		/**
		 *
		 */
		public static Date getNDayFromCur(Date oriDate, int n) {
			Date date = new Date(oriDate.getTime() + 24 * 60 * 60 * 1000 * n);
			return date;
		}

		

		/**
		
		 * @param date
		 * @return
		 */
		public static String getWekByDate(String dateStr) {
			return getWekByDate(dateStr);
		}

		public static String getWekByDate(Date date) {
			int week_no = getDayOfWeek(date);
			String wek = null;
			if (week_no == 1) {
				wek = "Mon";
			} else if (week_no == 2) {
				wek = "Tues";
			} else if (week_no == 3) {
				wek = "Wed";
			} else if (week_no == 4) {
				wek = "Thurs";
			} else if (week_no == 5) {
				wek = "Fri";
			} else if (week_no == 6) {
				wek = "Sat";
			} else if (week_no == 7) {
				wek = "Sun";
			} else {
				wek = "";
			}
			return wek;

		}

		public static void main(String[] args) throws Exception {
			// String str = parseDateTime("2007-4-10 12:30:01","yyyy-MM-dd
			// HH:mm:ss");
			//System.out.println(parseDate("20070605", "YYYYMMDD"));
//			System.out.println(getFirstDayOfMonth(new Date()));
			//TODO
			//System.out.println(convertS2S("Mon Jul 09 00:00:00 CST 2012"));
			//System.out.println(convertStr2Date("Mon Jul 09 00:00:00 CST 2012"));
		}

		
		public static Date get0Point(Date startDate) {
			cal.setTime(startDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return cal.getTime();
		}

		
		public static Date get24Point(Date endDate) {
			cal.setTime(endDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			System.out.println(cal.getTime());
			return cal.getTime();
		}
		
		/**
		 * "EEE MMM dd HH:mm:ss zzz yyyy" String绫诲瀿----> String绫诲瀿 (yyyy-MM-dd)
		 */
		public static String convertS2S(String date,String format) throws ParseException{
			Date d  = convertStr2Date(date);
			return getDateString(d, format);
		}
		
		
		
		/**
		 * "EEE MMM dd HH:mm:ss zzz yyyy" String绫诲瀿----> Date绫诲瀿 
		 * @throws ParseException 
		 */
		public static Date  convertStr2Date(String date) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			return sdf.parse(date);
		}

		public static String getLastYear(String year){
			if(StringUtils.isEmpty(year)){
				return null;
			}
			Integer yearInt = Integer.parseInt(year);
			Integer lastYearInt = yearInt - 1;
			return String.valueOf(lastYearInt);
		}


}
