/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.chl.core.util.properties;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author Administrator
 *
 */
public class DateUtils {

	/**
	 * DEFAULT_TIME_PATTERN
	 */
	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * DEFAULT_DATE_PATTERN
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 8位的日期格式
	 */
	public static final String EIGHT_BIT_DATE_PATTERN = "yyyyMMdd";

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 毫秒
	 */
	public static final String DEFAULT_SECONDTIME_PATTERN = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 获取Date
	 * 
	 * @param year
	 *            year
	 * @param month
	 *            month
	 * @param day
	 *            day
	 * @return Date
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取Date
	 * 
	 * @param dateStr
	 *            dateStr
	 * @param pattern
	 *            pattern
	 * @return Date
	 * @throws ParseException
	 *             ParseException
	 */
	public static Date getDate(String dateStr, String pattern)
			throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.parse(dateStr);
	}

	/**
	 * 获取Date
	 * 
	 * @param timeStr
	 *            timeStr
	 * @return Date
	 */
	public static Date getTime(String timeStr) {
		if (null == timeStr || timeStr.equals("")) {
			return null;
		}

		int length = timeStr.length();
		if (7 == length) {
			timeStr = timeStr + "-01 00:00:00";
		}
		if (10 == length) {
			timeStr = timeStr + " 00:00:00";
		}
		if (length > 7 && length < 10) {
			String[] str = timeStr.split("-");
			if (null == str || str.length != 3) {
				return null;
			}
			if (1 == str[1].length()) {
				str[1] = "0" + str[1];
			}
			if (1 == str[2].length()) {
				str[2] = "0" + str[2];
			}
			timeStr = str[0] + "-" + str[1] + "-" + str[2] + " 00:00:00";
		}

		String regex = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
		Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(timeStr).matches()) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
		try {
			return format.parse(timeStr);
		} catch (Exception e) {
			System.out
					.println("--------- DateUtils.getTime() 出现异常! -----------");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过指定的一种格式, 返回Date时间类型
	 * 
	 * @param dateStr
	 *            时间格式为 yyyy/MM/dd 或者 yyyy MM dd 或者 yyyy-MM-dd格式的数据类型.
	 *            2003/12/15
	 * @return Date
	 */
	public static Date getDate(String dateStr) {
		if (null == dateStr || dateStr.equals("")) {
			return null;
		}
		dateStr = dateStr.trim();
		char c = 0;
		for (int i = 0; i < dateStr.length(); i++) {
			c = dateStr.charAt(i);
			if (!StringUtils.isNumber(c)) {
				break;
			}
		}
		if (StringUtils.isNumber(c)) {
			return null;
		}
		StringTokenizer tokenYmd = new StringTokenizer(dateStr,
				Character.toString(c));
		int year = Integer.parseInt(tokenYmd.nextToken().trim());
		int month = Integer.parseInt(tokenYmd.nextToken().trim());
		String dayStr = tokenYmd.nextToken().trim();
		int index;
		for (index = 0; index < dayStr.length(); index++) {
			c = dayStr.charAt(index);
			if (!StringUtils.isNumber(c)) {
				break;
			}
		}
		int day = Integer.parseInt(dayStr.substring(0, index));
		// added by wallace
		int hour = 0;
		int min = 0;
		int sec = 0;

		if (dateStr.length() > 10) {
			String hms = dateStr.substring(10);
			StringTokenizer tokenHms = new StringTokenizer(hms, ":");
			hour = Integer.parseInt(tokenHms.nextToken().trim());
			min = Integer.parseInt(tokenHms.nextToken().trim());
			sec = Integer.parseInt(tokenHms.nextToken().trim());
		}
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hour, min, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 通过指定的一种格式, 返回Date时间类型
	 * 
	 * @param str
	 *            时间格式为 yyyyMMdd 或者 yyyyMM 或者 yyyy格式的数据类型. 20031215
	 * @return Date
	 */
	public static Date getDateYMD(String str) {
		// 设置年月日
		int year = 0, mm = 0, dd = 1;
		Calendar cal = Calendar.getInstance();

		if (str == null || "".equals(str) || str.length() < 4) {
			return null;
		}

		// 设置年月日
		try {
			year = Integer.parseInt(str.substring(0, 4));
			mm = Integer.parseInt(str.substring(4, 6)) - 1;
			dd = Integer.parseInt(str.substring(6, 8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.set(year, mm, dd, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * Date转字符串
	 * 
	 * @param date
	 *            date
	 * @param pattern
	 *            pattern
	 * @return String
	 */
	public static String toString(Date date, String pattern) {
		if (null == date) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * toTimeString
	 * 
	 * @param date
	 *            date
	 * @return String
	 */
	public static String toTimeString(Date date) {
		return toString(date, DEFAULT_TIME_PATTERN);
	}

	/**
	 * toDateString
	 * 
	 * @param time
	 *            long
	 * @return String
	 */
	public static String toDateString(long time) {
		return toDateString(new Date(time));
	}

	/**
	 * toTimeString
	 * 
	 * @param time
	 *            long
	 * @return String
	 */
	public static String toTimeString(long time) {
		return toTimeString(new Date(time));
	}

	/**
	 * @param date
	 *            date
	 * @return String
	 */
	public static String toDateString(Date date) {
		return toString(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * getDayRange
	 * 
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return int
	 */
	public static int getDayRange(Date beginDate, Date endDate) {
		// double result = (endDate.getTime() - beginDate.getTime()) / (1000 *
		// 3600 * 24.0) + 1;
		// if (result < 0) {
		// result += -1;
		// }
		// return (int) result;
		int flag = 1;
		Calendar start = getCalendarAtDateBegin(beginDate);
		Calendar end = getCalendarAtDateBegin(endDate);
		if (start.after(end)) {
			flag = -1;
			Calendar temp = start;
			start = end;
			end = temp;
		}
		int dayRange = 0;
		while (start.before(end)) {
			start.add(Calendar.DAY_OF_MONTH, 1);
			dayRange++;
		}
		return dayRange * flag;
	}

	/**
	 * 格式化日期,返回格式:"yyyy-MM-dd"
	 * 
	 * @param date
	 *            date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			DateFormat simpleDateFormat = new SimpleDateFormat(
					DEFAULT_DATE_PATTERN);
			String newDate = simpleDateFormat.format(date);
			return newDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化日期,返回格式:"yyyy-MM-dd hh:mm:ss"
	 * 
	 * @param date
	 *            date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (null == date) {
			return null;
		}
		try {
			DateFormat simpleDateFormat = new SimpleDateFormat(
					DEFAULT_TIME_PATTERN);
			String newDate = simpleDateFormat.format(date);
			return newDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 月数，不足一月加1 getMonthRange
	 * 
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return int
	 */
	public static int getRoundUpMonthRange(Date beginDate, Date endDate) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(beginDate);
		// int beginYear = calendar.get(Calendar.YEAR);
		// int beginMonth = calendar.get(Calendar.MONTH);
		// int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
		// calendar.setTime(endDate);
		// int endYear = calendar.get(Calendar.YEAR);
		// int endMonth = calendar.get(Calendar.MONTH);
		// int endDay = calendar.get(Calendar.DAY_OF_MONTH);
		// int monthRange = (endYear - beginYear) * 12 + (endMonth -
		// beginMonth);
		// if (endDay > beginDay) {
		// return monthRange + 1;
		// }
		// return monthRange;
		int flag = 1;
		Calendar start = getCalendarAtDateBegin(beginDate);
		Calendar end = getCalendarAtDateBegin(endDate);
		if (start.after(end)) {
			flag = -1;
			Calendar temp = start;
			start = end;
			end = temp;
		}
		int monthRange = 0;
		while (start.before(end)) {
			start.add(Calendar.MONTH, 1);
			monthRange++;
		}
		return monthRange * flag;
	}

	/**
	 * 月数，不足一月不计
	 * 
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return int
	 */
	public static int getMonthRange(java.util.Date beginDate,
			java.util.Date endDate) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(beginDate);
		// int beginYear = calendar.get(Calendar.YEAR);
		// int beginMonth = calendar.get(Calendar.MONTH);
		// int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
		// calendar.setTime(endDate);
		// int endYear = calendar.get(Calendar.YEAR);
		// int endMonth = calendar.get(Calendar.MONTH);
		// int endDay = calendar.get(Calendar.DAY_OF_MONTH);
		// int monthRange = (endYear - beginYear) * 12 + (endMonth -
		// beginMonth);
		// if (endDay < beginDay) {
		// return monthRange - 1;
		// }
		// return monthRange;

		int flag = 1;
		Calendar start = getCalendarAtDateBegin(beginDate);
		Calendar end = getCalendarAtDateBegin(endDate);
		if (start.after(end)) {
			flag = -1;
			Calendar temp = start;
			start = end;
			end = temp;
		}
		int monthRange = 0;
		while (start.before(end)) {
			start.add(Calendar.MONTH, 1);
			monthRange++;
		}
		if (start.after(end)) {
			monthRange--;
		}
		return monthRange * flag;
	}

	/**
	 * 月数，不足一月不计（兼容用，以后用上面的两个）
	 * 
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return int
	 */
	public static int getDepreciationMonthRange(java.util.Date beginDate,
			java.util.Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		int beginYear = calendar.get(Calendar.YEAR);
		int beginMonth = calendar.get(Calendar.MONTH);
		int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(endDate);
		int endYear = calendar.get(Calendar.YEAR);
		int endMonth = calendar.get(Calendar.MONTH);
		int endDay = calendar.get(Calendar.DAY_OF_MONTH);
		int monthRange = (endYear - beginYear) * 12 + (endMonth - beginMonth);
		if (endDay < beginDay) {
			return monthRange - 1;
		}
		return monthRange;
	}

	/**
	 * 以天为单位，如果两种是同一天就返回0 date1小于date2就返回-1 date1大于date2就返回1
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @return int
	 */
	public static int compareDate(Date date1, Date date2) {

		Calendar cal1 = getCalendarAtDateBegin(date1);
		Calendar cal2 = getCalendarAtDateBegin(date2);
		if (cal1.before(cal2)) {
			return -1;
		}
		if (cal1.after(cal2)) {
			return 1;
		}
		return 0;
	}

	/**
	 * 得到这天的最开始时间 ０点０分０秒
	 * 
	 * @param date
	 *            date
	 * @return Calendar
	 */
	public static Calendar getCalendarAtDateBegin(Date date) {
		Calendar cal = getCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * Date得到Calendar
	 * 
	 * @param date
	 *            date
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * getYearRange 不足一年算一年
	 * 
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return int
	 */
	public static int getYearRange(Date beginDate, Date endDate) {
		Calendar startCalendar = getCalendar(beginDate);
		Calendar endCalendar = getCalendar(endDate);
		int yearRange = 0;
		while (compareDate(startCalendar.getTime(), endCalendar.getTime()) <= 0) {
			startCalendar.add(Calendar.YEAR, 1);
			yearRange++;
		}
		return yearRange;
	}

	/**
	 * Date 增加几天
	 * 
	 * @param date
	 *            日期
	 * @param day
	 *            增加的天数
	 * @return Date
	 */
	public static Date addDay(Date date, int day) {
		if (date == null || day == 0) {
			return date;
		}
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * Date 增加几个月
	 * 
	 * @param date
	 *            日期
	 * @param month
	 *            增加的月数
	 * @return Date
	 */
	public static Date addMonth(Date date, int month) {
		if (date == null || month == 0) {
			return date;
		}
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间的日期
	 * 
	 * @return 日期
	 */
	public static Date getDateNow() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前的日期时间
	 * 
	 * @return 时间
	 */
	public static Date getDateTimeNow() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获取年
	 * 
	 * @param date
	 *            日期
	 * @return 年
	 */
	public static int getYear(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取日
	 * 
	 * @param date
	 *            日期
	 * @return 日
	 */
	public static int getDay(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * 根据提供的日期查找该日期的上一个还款日
	 * 
	 * @return
	 * @author chl
	 * @date 2014年8月27日
	 */
	public static Date getPlanDate(Date date) {
		if (date == null) {
			return date;
		}
		int year = getYear(date);
		int month = getMonth(date);
		int day = getDay(date);

		if (month == 1) {
			if (day < 10) {
				return getDate(year - 1, 12, 30);
			} else if (10 <= day && day < 20) {
				return getDate(year, month, 10);
			} else if (20 <= day && day < 30) {
				return getDate(year, month, 20);
			} else if (30 <= day) {
				return getDate(year, month, 30);
			}
		} else if (month == 3) {
			if ((year % 4 != 0) || ((year % 100 == 0) && (year % 400 != 0))) {
				if (day < 10) {
					return getDate(year, month - 1, 28);
				} else if (10 <= day && day < 20) {
					return getDate(year, month, 10);
				} else if (20 <= day && day < 30) {
					return getDate(year, month, 20);
				} else if (30 <= day) {
					return getDate(year, month, 30);
				}
			} else {
				if (day < 10) {
					return getDate(year, month - 1, 29);
				} else if (10 <= day && day < 20) {
					return getDate(year, month, 10);
				} else if (20 <= day && day < 30) {
					return getDate(year, month, 20);
				} else if (30 <= day) {
					return getDate(year, month, 30);
				}
			}

		} else if (month == 2) {
			if ((year % 4 != 0) || ((year % 100 == 0) && (year % 400 != 0))) {
				if (day < 10) {
					return getDate(year, month - 1, 30);
				} else if (10 <= day && day < 20) {
					return getDate(year, month, 10);
				} else if (20 <= day && day < 28) {
					return getDate(year, month, 20);
				} else if (28 <= day) {
					return getDate(year, month, 28);
				}
			} else {
				if (day < 10) {
					return getDate(year, month - 1, 30);
				} else if (10 <= day && day < 20) {
					return getDate(year, month, 10);
				} else if (20 <= day && day < 29) {
					return getDate(year, month, 20);
				} else if (29 <= day) {
					return getDate(year, month, 29);
				}
			}

		} else {
			if (day < 10) {
				return getDate(year, month - 1, 30);
			} else if (10 <= day && day < 20) {
				return getDate(year, month, 10);
			} else if (20 <= day && day < 30) {
				return getDate(year, month, 20);
			} else if (30 <= day) {
				return getDate(year, month, 30);
			}

		}

		return date;
	}

	/**
	 * 
	 * 根据提供的日期查找该日期的下一个还款日
	 * 
	 * @return
	 * @author chl
	 * @date 2014年8月27日
	 */
	public static Date getNextPlanDate(Date date) {
		if (date == null) {
			return date;
		}
		int year = getYear(date);
		int month = getMonth(date);
		int day = getDay(date);
		if (month == 2) {
			if ((year % 4 != 0) || ((year % 100 == 0) && (year % 400 != 0))) {
				if (day <= 10) {
					return getDate(year, month, 10);
				} else if (10 < day && day <= 20) {
					return getDate(year, month, 20);
				} else if (20 < day && day <= 28) {
					return getDate(year, month, 28);
				}
			} else {
				if (day <= 10) {
					return getDate(year, month, 10);
				} else if (10 < day && day <= 20) {
					return getDate(year, month, 20);
				} else if (20 < day && day <= 29) {
					return getDate(year, month, 29);
				}
			}

		} else if (month == 12) {

			if (day <= 10) {
				return getDate(year, month, 10);
			} else if (10 < day && day <= 20) {
				return getDate(year, month, 20);
			} else if (20 < day && day <= 30) {
				return getDate(year, month, 30);
			} else if (day > 30) {
				return getDate(year + 1, 1, 10);
			}

		} else {
			if (day <= 10 || day > 30) {
				return getDate(year, month, 10);
			} else if (10 < day && day <= 20) {
				return getDate(year, month, 20);
			} else if (20 < day && day <= 30) {
				return getDate(year, month, 30);
			} else if (day > 30) {
				return getDate(year, month + 1, 10);
			}

		}

		return date;
	}

	/**
	 * 
	 * 根据月份查询该月的两个还款日（10和20）和上月最后一次还款日（30或28）
	 * 
	 * @param date
	 * @return
	 * @author chl
	 * @date 2014年11月21日
	 */
	public static List<Date> getPlanDates(Date date) {
		List<Date> dates = new ArrayList<Date>(0);
		if (date == null) {
			return dates;
		}
		int year = getYear(date);
		int month = getMonth(date);

		if (month == 1) {
			Date d1 = getDate(year - 1, 12, 30);
			Date d2 = getDate(year, 1, 10);
			Date d3 = getDate(year, 1, 20);
			dates.add(d1);
			dates.add(d2);
			dates.add(d3);
		} else if (month == 3) {
			if ((year % 4 != 0) || ((year % 100 == 0) && (year % 400 != 0))) {
				Date d1 = getDate(year, 2, 28);
				Date d2 = getDate(year, 3, 10);
				Date d3 = getDate(year, 3, 20);
				dates.add(d1);
				dates.add(d2);
				dates.add(d3);
			} else {
				Date d1 = getDate(year, 2, 29);
				Date d2 = getDate(year, 3, 10);
				Date d3 = getDate(year, 3, 20);
				dates.add(d1);
				dates.add(d2);
				dates.add(d3);
			}

		} else {
			Date d1 = getDate(year, month - 1, 30);
			Date d2 = getDate(year, month, 10);
			Date d3 = getDate(year, month, 20);
			dates.add(d1);
			dates.add(d2);
			dates.add(d3);

		}

		return dates;
	}

	/**
	 * 时间差，用毫秒度量 date1<date2
	 * 
	 * @param date1
	 * @param date2
	 * @return date2-date1
	 */
	public static long getDeltaMilliseconds(Date date1, Date date2) {
		return date2.getTime() - date1.getTime();
	}

	/**
	 * 时间差，用天度量 date1<date2
	 * 
	 * @param date1
	 * @param date2
	 * @return date2-date1
	 */
	public static int getDeltaDays(Date date1, Date date2) {
		return (int) (getDeltaMilliseconds(date1, date2) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 获取月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMinMonthDate(Date d) throws ParseException {

		String date = DateUtils.toString(d, DEFAULT_DATE_PATTERN);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateUtils.getDate(dateFormat.format(calendar.getTime()),
				DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMaxMonthDate(Date d) throws ParseException {
		String date = DateUtils.toString(d, DEFAULT_DATE_PATTERN);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return DateUtils.getDate(dateFormat.format(calendar.getTime()),
				"yyyy-MM-dd");
	}

	/**
	 * zmy add
	 * 
	 * @param commDate
	 * @return 返回日期格式：yyyy-MM-dd HH:mm:ss
	 */

	public static Date getRealCommDate(String commDate) {
		// String returnTime="YYYY-MM-DD HH:mm:ss";
		try {
			if (!StringUtils.isNullOrEmpty(commDate)) {
				Calendar calendar = Calendar.getInstance();
				Long timeL = Long.valueOf(commDate);
				calendar.setTimeInMillis(timeL);
				return DateUtils.getDate(calendar.getTime().toLocaleString(),
						DateUtils.DEFAULT_TIME_PATTERN);

			} else {
				return DateUtils.getDate(Calendar.getInstance().toString(),
						DateUtils.DEFAULT_TIME_PATTERN);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * zmy add
	 * 
	 * @param commDate
	 * @return 毫秒日期
	 */

	public static String getRealCommSecondsDate(String commDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			if (!StringUtils.isNullOrEmpty(commDate)) {
				Calendar calendar = Calendar.getInstance();
				Long timeL = Long.valueOf(commDate);
				calendar.setTimeInMillis(timeL);
				System.err.println(sdf.format(calendar.getTimeInMillis()));
				return sdf.format(calendar.getTimeInMillis());

			} else {
				return sdf.format(Calendar.getInstance().getTimeInMillis());
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getWeekDay(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 
	 * 获取两个日期之间差几个工作日（工作日为周一到周五） 差值包含当天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author chl
	 * @date 2015年4月8日
	 */
	public static int getDutyDays(Date startDate, Date endDate) {
		int result = 0;
		while (startDate.compareTo(endDate) <= 0) {
			if (getWeekDay(startDate) != 7 && getWeekDay(startDate) != 1) {
				result++;
			}
			startDate = addDay(startDate, 1);
		}
		return result;
	}

}
