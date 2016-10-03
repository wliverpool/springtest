package com.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import org.apache.commons.lang.time.DateUtils;


/**
* 
* 功能描述:时间工具类
* @author 吴福明
*/
public class DateTime {
	
	public static final int YEAR = 1; //获取年;
	static final String YEAR_STR = "yyyy";
	public static final int MONTH = 2; //获取月;
	static final String MONTH_STR = "MM";
	public static final int DATE =3 ; //获取天;
	static final String DATE_STR = "dd";
	public static final int HOUR =4;//获取小时; 
	static final String HOUR_STR = "HH";
	public static final int MINUTE = 5;//获取分钟;
	static final String MINUTE_STR = "mm";
	public static final int SECOND = 6;//获取秒钟;
	static final String SECOND_STR = "ss";
    
	public static final int YEAR_MONTH_DATE = 7;//获取 年 - 月 - 日;
	public static final int YEAR_MONTH_DATE_HOUR = 8;//获取 年 - 月 - 日 小时;
	public static final int YEAR_MONTH_DATE_HOUR_MINUTE = 9;//获取 年 - 月 - 日 小时:分钟;
	
	public static final int HOUR_MINUTE = 10;//  小时:分钟;
	
	public static final int DATE_HOUR = 11;//日 小时;
	
	private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static final int FIRSTTEN = 1;
	public static final int MIDTEN = 2;
	public static final int LASTTEN = 3;

	public static final int FIRSTQUARTER = 1;
	public static final int SECONDQUARTER = 2;
	public static final int THIRDQUARTER = 3;
	public static final int FORTHQUARTER = 4;

	private static Pattern pattern = Pattern.compile("^[1-9]\\d{3}-[01]?\\d-[0|1|2|3]?\\d$"); // 2010-12-22
	
	//获取时间
    public static String getTime(int obj){
    	switch (obj) {
		case YEAR:
			return toTime(YEAR_STR);
		case MONTH:
			return toTime(MONTH_STR);
		case DATE:
			return toTime(DATE_STR);
		case HOUR:
			return toTime(HOUR_STR);
		case MINUTE:
			return toTime(MINUTE_STR);
		case SECOND:
			return toTime(SECOND_STR);
		case YEAR_MONTH_DATE:
			return toTime(YEAR_STR+"-"+MONTH_STR+"-"+DATE_STR);
		case YEAR_MONTH_DATE_HOUR:
			return toTime(YEAR_STR+"-"+MONTH_STR+"-"+DATE_STR+" "+HOUR_STR);
		case YEAR_MONTH_DATE_HOUR_MINUTE:
			return toTime(YEAR_STR+"-"+MONTH_STR+"-"+DATE_STR+" "+HOUR_STR+":"+MINUTE_STR);
		case HOUR_MINUTE:
			return toTime(HOUR_STR+":"+MINUTE_STR);
		case DATE_HOUR:
			return toTime(DATE_STR+" "+HOUR_STR);
		default:
			return toTime();
		}
    	 
    }
	 public static void main(String[] args) {
		 System.out.println(DateTime.isDateTime("2013-11-22 12:00:21"));
		 System.out.println(DateTime.parseDate("2013-9-15 00:00:00", "yyyy-MM-dd HH:mm:ss"));
	    System.out.println(DateTime.toTime("yyyyMMddHHmmss"));
	    Date start = DateTime.parseDate("2013-9-15", "yyyy-MM-dd");
		Date end = DateTime.parseDate("2013-9-11", "yyyy-MM-dd");
		if(start.getTime() >= end.getTime()){
			System.out.println("1>=2");
		}
		System.out.println(getDaysBetweenTwoTimes("2013-9-15","2013-9-15"));
	}
	 
	 /**
     * 检查字符串是否为日期
     */
    public static boolean isDate(Object value)
    {
        return isDateFormat(value, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
    }
    
    /**
     * 检查字符串是否为日期时间类型
     */
    public static boolean isDateTime(Object value)
    {
        return isDateFormat(value, "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    }
    
    /**
     * 检查字符串是否为指定的日期类型
     * @param fromatReg 格式正则表达式
     */
    public static boolean isDateFormat(Object value, String fromatReg)
    {
        Pattern pattern = Pattern.compile(fromatReg);
        if (Validation.isNULL(value) || !pattern.matcher(value.toString()).matches())
        	return false;
        else
        	return true;
    }
    
    /**
     * 解析日期类型，格式"yyyyMMdd"
     */
    public static String toDate(String  format){
    	if ( Validation.isNULL(format))
    		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	else
    		return new SimpleDateFormat(format).format(new Date());
    }
    
    public static String toDate(String  format,Date date){
    	if ( Validation.isNULL(format))
    		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    	else
    		return new SimpleDateFormat(format).format(date);
    }


    /**
     * 解析日期时间类型，格式"yyyyMMdd hh:mm:ss"
     * @author RASCAL
     */
    public static Date toDateTime(Object value)
    {
        return parseDate(value, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 解析日期类型
     * @param fromat 日期格式
     */
    public static Date parseDate(Object value, String fromat)
    {
        //解析日期格式
        try
        {
            DateFormat df =  new SimpleDateFormat(fromat);
            return df.parse(value.toString());
        }
        catch (Exception ex)
        {
//            logger.warn("字符串[{}]转日期格式[{}]转换错误!", str, fromat);
        }
        return null;
    }
    
    /**
     * 
     * @param format 日期格式
     * @return 当日日期
     */
    public static String toTime(String  format){
    	if (Validation.isNULL(format)){
    		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	}else{
    		return new SimpleDateFormat(format).format(new Date());
    	}
    }

    public static String toTime( ){
    	return toTime(null);
    }
    
    /**
     * 获取两个时间的间隔天数
     * @param dateFrom
     * @param dateEnd
     * @return int
     */
    public static int getDaysBetweenTwoTimes(Object dateFrom, Object dateEnd) {
        Date dtFrom = parseDate(dateFrom, "yyyy-MM-dd");
        Date dtEnd = parseDate(dateEnd, "yyyy-MM-dd");
        long begin = dtFrom.getTime();
        long end = dtEnd.getTime();
        long inter = end - begin;
        if (inter < 0) {
            inter = inter * (-1);
        }
        long dateMillSec = 24 * 60 * 60 * 1000;
        Long dateCnt =  inter / dateMillSec;
        long remainder = inter % dateMillSec;
        if (remainder != 0) {
            dateCnt++;
        }
        return dateCnt.intValue();
    }
    
    /**
     * Long转为日期，结果为String
     */
    public static String longToDate(Long value, String format){
    	 try{
    		 DateFormat df =  null;
    		 if (Validation.isNULL(format)){
    			 df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		 }else{
    			 df =  new SimpleDateFormat(format);
    		 }
    		 Date dt = new Date(value);  
             return df.format(dt);
         }catch (Exception ex){}
         return null;
    }
    
    /**
	 * 获取当前系统时间按天截取的时间
	 * 
	 * @return
	 */
	public static Date getSystemTranceDay() {
		return DateUtils.truncate(new Date(), Calendar.DATE);
	}

	/**
	 * 功能：根据指定时间获取当前天的开始和结束时间，以date数组返回 逻辑： 1、appointDate is null ,set default
	 * value sysdate 2、get date[]
	 * 
	 * @param appointDate
	 * @return
	 */
	public static Date[] getDateArrByDay(Date appointDate) {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		// 未完
		if (appointDate == null) {
			appointDate = new Date();
		}
		stime = DateUtils.truncate(appointDate, Calendar.DATE);
		etime = DateUtils.addSeconds(DateUtils.truncate(
				DateUtils.addDays(appointDate, 1), Calendar.DATE), -1);

		date[0] = stime;
		date[1] = etime;
		return date;
	}

	/**
	 * 功能：根据指定时间获取当前星期的开始和结束时间，以date数组返回
	 * 
	 * @param appointDate
	 * @return
	 */
	public static Date[] getDateArrByWeek(Date appointDate) {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		if (appointDate == null) {
			appointDate = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(appointDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek);

		calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + 2);

		stime = DateUtils.truncate(calendar.getTime(), Calendar.DATE);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		etime = DateUtils.addSeconds(
				DateUtils.truncate(calendar.getTime(), Calendar.DATE), -1);

		date[0] = stime;
		date[1] = etime;

		return date;
	}

	/**
	 * 功能：根据指定的时间和上中下旬的其中一个，获取开始时间和结束时间
	 * 
	 * @param appointDate
	 * @param appointIndex
	 * @return
	 */
	public static Date[] getDateArrByTenDays(Date appointDate, int appointIndex) {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		if (appointDate == null) {
			appointDate = new Date();
		}
		// init date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(appointDate);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int maxDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

		Date tempDate = DateUtils.truncate(
				DateUtils.addDays(appointDate, -dayOfMonth + 1), Calendar.DATE);

		if (appointIndex == FIRSTTEN) {
			stime = tempDate;
			etime = DateUtils.addSeconds(DateUtils.addDays(stime, 10), -1);
		}

		if (appointIndex == MIDTEN) {
			stime = DateUtils.addDays(tempDate, 10);
			etime = DateUtils.addSeconds(DateUtils.addDays(stime, 10), -1);
		}

		if (appointIndex == LASTTEN) {
			stime = DateUtils.addDays(tempDate, 20);
			etime = DateUtils.addSeconds(
					DateUtils.addDays(tempDate, maxDayOfMonth), -1);
		}

		date[0] = stime;
		date[1] = etime;
		return date;
	}

	/**
	 * 功能:根据指定时间获取相应月份的开始时间和结束时间
	 * 
	 * @param appointDate
	 * @return
	 */
	public static Date[] getDateArrByMonth(Date appointDate) {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		if (appointDate == null) {
			appointDate = new Date();
		}

		// init date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(appointDate);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int maxDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

		appointDate = DateUtils.truncate(appointDate, Calendar.DATE);

		stime = DateUtils.truncate(
				DateUtils.addDays(appointDate, -dayOfMonth + 1), Calendar.DATE);
		etime = DateUtils.addSeconds(DateUtils.addDays(stime, maxDayOfMonth),
				-1);

		date[0] = stime;
		date[1] = etime;

		return date;
	}

	/**
	 * 功能：根据指定时间所在的当前年，获取指定季度的开始时间和结束时间
	 * 
	 * @param appointDate
	 *            指定当前年
	 * @param appointIndex
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Date[] getDateArrByQuarter(Date appointDate, int appointIndex)throws IllegalArgumentException {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		if (appointDate == null) {
			appointDate = new Date();
		}
		Date tempDate = DateUtils.truncate(appointDate, Calendar.YEAR);
		if (appointIndex == FIRSTQUARTER) {
			stime = tempDate;
		} else if (appointIndex == SECONDQUARTER) {
			stime = DateUtils.addMonths(tempDate, 3);
		} else if (appointIndex == THIRDQUARTER) {
			stime = DateUtils.addMonths(tempDate, 6);
		} else if (appointIndex == FORTHQUARTER) {
			stime = DateUtils.addMonths(tempDate, 9);
		}
		etime = DateUtils.addSeconds(DateUtils.addMonths(stime, 3), -1);

		date[0] = stime;
		date[1] = etime;

		return date;
	}

	/**
	 * 功能：根据指定时间，获取年的开始时间和结束时间
	 * 
	 * @param appointDate
	 * @return
	 */
	public static Date[] getDateArrByYear(Date appointDate) {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];
		if (appointDate == null) {
			appointDate = new Date();
		}
		stime = DateUtils.truncate(appointDate, Calendar.YEAR);
		etime = DateUtils.addSeconds(DateUtils.addYears(stime, 1), -1);

		date[0] = stime;
		date[1] = etime;

		return date;
	}

	/**
	 * 逻辑： 1、检查startTime,endTime的有效性（是否为空，数据格式）， 异常处理: 1、两个参数都为空，抛出空指针异常
	 * 2、数据格式不对，直接抛出 3、一个参数为空，另一个参数格式正确的情况下，为空的参数采用系统时间，为了保证startTime <=
	 * endTime,工具类会做适当的调整 2、转换 3、返回值是个Date[2]数组，date[0] 保存startTime值,date[1]
	 * 保存startTime值，其中startTime <= endTime
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Date[] convertDateClass(String startTime, String endTime) throws NullPointerException, DataFormatException, ParseException {
		Date stime = null;
		Date etime = null;
		Date[] date = new Date[2];

		if (StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)) {
			throw new NullPointerException("两个参数不能同时为空");
		}

		if (StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
			// 先判断endTime格式是否正确
			Matcher matcher = pattern.matcher(endTime);
			if (matcher.matches()) {
				stime = DateUtils.truncate(new Date(), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27
																		// 11:31:30
																		// 这里stime的时间是2010-12-27
																		// 0:0:0
				etime = DateUtils.truncate(sDateFormat.parse(endTime),Calendar.DATE);
			} else {
				throw new DataFormatException(
						"参数endTime的格式不正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
			}
		}
		if (!StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)) {
			Matcher matcher = pattern.matcher(startTime);
			if (matcher.matches()) {
				// 提供转换
				etime = DateUtils.truncate(new Date(), Calendar.DATE); // 当天的开始时间，比如：当前时间为2010-12-27
																		// 11:31:30
																		// 这里stime的时间是2010-12-27
																		// 0:0:0
				stime = DateUtils.truncate(sDateFormat.parse(startTime),Calendar.DATE);
			} else {
				throw new DataFormatException("参数startTime的格式不正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
			}
		}

		if (!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
			Matcher sMatcher = pattern.matcher(startTime);
			Matcher eMatcher = pattern.matcher(endTime);
			if (sMatcher.matches() && eMatcher.matches()) {

				stime = DateUtils.truncate(sDateFormat.parse(startTime),
						Calendar.DATE);
				etime = DateUtils.truncate(sDateFormat.parse(endTime),
						Calendar.DATE);

			} else {
				throw new DataFormatException("请检查参数startTime、endTime的格式是否正确！正确的格式 yyyy-MM-dd 比如：2010-12-12！");
			}

		}

		if (!stime.before(etime)) {
			Date temp = stime;
			stime = etime;
			etime = temp;
			temp = null;
		}

		date[0] = stime;
		date[1] = etime;
		return date;
	}

	/**
	 * 生成随机时间
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date randomDate(String beginDate, String endDate) {

		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date start = format.parse(beginDate);// 构造开始日期

			Date end = format.parse(endDate);// 构造结束日期

			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

			if (start.getTime() >= end.getTime()) {

				return null;

			}

			long date = random(start.getTime(), end.getTime());

			return new Date(date);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	private static long random(long begin, long end) {

		long rtn = begin + (long) (Math.random() * (end - begin));

		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值

		if (rtn == begin || rtn == end) {

			return random(begin, end);

		}

		return rtn;

	}
}
