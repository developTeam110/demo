package com.demo.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Optional;

/** 
* REVIEW
* @Description: TODO
* @author jingkun.wang@baidao.com   
* @date 2016年8月5日 下午5:54:25 
*  
*/
public class DateUtil {

	protected static Log logger = LogFactory.getLog(DateUtil.class);

	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";

	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

	// 格式：年月日 小时分钟秒
	public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

	public static final String FORMAT_FOUR = "yyyy年MM月dd日 HH:mm";

	// 格式：年－月－日
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

	// 格式：年月日
	public static final String EIGHT_STYLE_DATE_FORMAT = "yyyyMMdd";

	// 格式：月－日
	public static final String SHORT_DATE_FORMAT = "MM-dd";

	// 格式：小时：分钟：秒
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";

	// 格式：小时：分钟
	public static final String SHORT_TIME_FORMAT = "HH:mm";

	// 格式：年-月
	public static final String MONTG_DATE_FORMAT = "yyyy-MM";

	// 格式：年月
	public static final String MONTG_DATE_FORMAT2 = "yyyyMM";

	// 年的加减
	public static final int SUB_YEAR = Calendar.YEAR;

	// 月加减
	public static final int SUB_MONTH = Calendar.MONTH;

	// 天的加减
	public static final int SUB_DAY = Calendar.DATE;

	// 小时的加减
	public static final int SUB_HOUR = Calendar.HOUR;

	// 分钟的加减
	public static final int SUB_MINUTE = Calendar.MINUTE;

	// 秒的加减
	public static final int SUB_SECOND = Calendar.SECOND;

	static final String DAYNAMES[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	@SuppressWarnings("unused")
	private static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat(FORMAT_ONE);

	public DateUtil() {
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */

	public static java.util.Date toDate(String dateStr) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	public static java.util.Date toDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	/**
	 * 把日期转换为字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateTime(java.util.Date date) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(FORMAT_ONE);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	public static String formatDateTime(java.util.Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			// log.error(e);
		}
		return result;
	}

	/**
	 * 获取当前时间的指定格式
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return formatDateTime(new Date(), format);
	}

	/**
	 *
	 * @param dateStr
	 * @param amount
	 * @return
	 */
	public static String dateSub(int dateKind, String dateStr, int amount) {
		Date date = toDate(dateStr, FORMAT_ONE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateKind, amount);
		return formatDateTime(calendar.getTime(), FORMAT_ONE);
	}

	/**
	 * 两个日期相减
	 *
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的秒数
	 */
	public static long timeSub(String firstTime, String secTime) {
		long first = toDate(firstTime, FORMAT_ONE).getTime();
		long second = toDate(secTime, FORMAT_ONE).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 获得某月的天数
	 *
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8") || month.equals("10") || month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) || Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获取某年某月的天数
	 *
	 * @param year
	 *            int
	 * @param month
	 *            int 月份[1-12]
	 * @return int
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	 *
	 * @return int
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 *
	 * @return int
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	 *
	 * @return int
	 */
	public static int getToYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的天
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回日期的年
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的月份，1-12
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 *
	 * @param date1
	 *            Date
	 * @param date2
	 *            Date
	 * @return long
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 比较两个日期的年差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiff(String before, String after) {
		Date beforeDay = toDate(before, LONG_DATE_FORMAT);
		Date afterDay = toDate(after, LONG_DATE_FORMAT);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较两个日期的天差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static long dayDiff(String before, String after) {
		Date beforeDay = toDate(before, LONG_DATE_FORMAT);
		Date afterDay = toDate(after, LONG_DATE_FORMAT);
		return (beforeDay.getTime() - afterDay.getTime()) / 86400000;
	}

	/**
	 * 比较指定日期与当前日期的差（年）
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiffCurr(String after) {
		Date beforeDay = new Date();
		Date afterDay = toDate(after, LONG_DATE_FORMAT);
		return getYear(beforeDay) - getYear(afterDay);
	}

	/**
	 * 比较指定日期与当前日期的差(天)
	 *
	 * @param before
	 * @return
	 * @author chenyz
	 */
	public static long dayDiffCurr(String before) {
		Date currDate = DateUtil.toDate(currDay(), LONG_DATE_FORMAT);
		Date beforeDate = toDate(before, LONG_DATE_FORMAT);
		return (currDate.getTime() - beforeDate.getTime()) / 86400000;

	}

	/**
	 * 比较指定日期与当前日期的差(秒)
	 *
	 * @param before
	 * @return
	 * @author chenyz
	 */
	public static long timeDiffCurr(String before) {
		Date beforeDay = new Date();
		long first = toDate(formatDateTime(beforeDay), FORMAT_ONE).getTime();
		long second = toDate(before, FORMAT_ONE).getTime();
		return (first - second) / 1000;
	}

	/**
	 * 获取每月的第一周
	 *
	 * @param year
	 * @param month
	 * @return
	 * @author chenyz
	 */
	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取每月的最后一周
	 *
	 * @param year
	 * @param month
	 * @return
	 * @author chenyz
	 */
	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
	 *
	 * @return
	 */
	public static String getNow() {
		Calendar today = Calendar.getInstance();
		return formatDateTime(today.getTime(), FORMAT_ONE);
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 *
	 * @param date
	 *            YYYY-mm-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
	 *
	 * @param date
	 *            日期 为null时表示当天
	 * @param month
	 *            相加(相减)的月数
	 */
	public static Date nextMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
	 *
	 * @param date
	 *            日期 为null时表示当天
	 * @param month
	 *            相加(相减)的月数
	 */
	public static Date nextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}

	/**
	 * 取得距离今天 day 日的日期
	 *
	 * @param day
	 * @param format
	 * @return
	 * @author chenyz
	 */
	public static String nextDay(int day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, day);
		return formatDateTime(cal.getTime(), format);
	}

	/**
	 * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
	 *
	 * @param date
	 *            日期 为null时表示当天
	 */
	public static Date nextWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}

	/**
	 * 获取当前的日期(yyyy-MM-dd)
	 */
	public static String currDay() {
		return DateUtil.formatDateTime(new Date(), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 获取昨天的日期
	 *
	 * @return
	 */
	public static String befoDay() {
		return befoDay(DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 根据时间类型获取昨天的日期
	 *
	 * @param format
	 * @return
	 * @author chenyz
	 */
	public static String befoDay(String format) {
		return DateUtil.formatDateTime(DateUtil.nextDay(new Date(), -1), format);
	}

	/**
	 * 获取明天的日期
	 */
	public static String afterDay() {
		return DateUtil.formatDateTime(DateUtil.nextDay(new Date(), 1), DateUtil.LONG_DATE_FORMAT);
	}

	/**
	 * 当前时间的前(后)几天
	 *
	 * @return
	 */
	public static Date getDateByOfNum(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);
		return c.getTime();
	}

	/**
	 * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
	 *
	 * @param day
	 * @return
	 */
	public static Date getDateByNum(int day) {
		GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
		Date date = gd.getTime();
		date = nextDay(date, day);
		return date;
	}

	/** 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd */
	public static String getYmdDateCN(String datestr) {
		if (datestr == null) {
			return "";
		}
		if (datestr.length() < 10) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7)).append(datestr.substring(8, 10));
		return buf.toString();
	}

	public static Date getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return getStartOfDay(cal.getTime());
	}

	public static String getFirstDayOfMonth(String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		return formatDateTime(cal.getTime(), format);
	}

	public static Date getFirstDayOfMonth(Date date, int addMonthNum) {
		Date nextMonth = nextMonth(date, addMonthNum);
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static String getFirstDayOfMonth(Date date, int addMonthNum, String format) {
		Date nextMonth = nextMonth(date, addMonthNum);
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatDateTime(cal.getTime(), format);
	}

	/**
	 * 获取本月最后一天
	 *
	 * @param format
	 * @return
	 */
	public static String getLastDayOfMonth(String format) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return formatDateTime(cal.getTime(), format);
	}

	/**
	 * REVIEW
	 * @Description:获取指定日期前(后)addWeekNum周的第一天
	 * @author xi.he@baidao.com
	 * @date 2013-2-26 上午09:44:42
	 * @param date
	 * @param addWeekNum
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date, int addWeekNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addWeekNum * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * REVIEW
	 * @Description:获取指定日期前(后)addWeekNum周的最后一天
	 * @author xi.he@baidao.com
	 * @date 2013-2-26 上午09:47:39
	 * @param date
	 * @param addWeekNum
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date, int addWeekNum) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, (addWeekNum + 1) * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
	}

	/**
	 * REVIEW
	 * @Description: 获取指定日期前(后)addMonthNum月的最后一天
	 * @author xi.he@baidao.com
	 * @date 2013-2-26 上午10:29:06
	 * @param date
	 * @param addMonthNum
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date, int addMonthNum) {
		Date nextMonth = nextMonth(date, addMonthNum + 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(nextMonth);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day) {
		return getStartOfDay(day, Calendar.getInstance());
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set to the last possible millisecond of the day, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfDay(Date day) {
		return getEndOfDay(day, Calendar.getInstance());
	}

	public static Date getEndOfDay(Date day, Calendar cal) {
		if (day == null) {
			day = new Date();
		}
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * 判断2个时间相差多少天<br>
	 * <br>
	 *
	 * @param pBeginTime
	 *            请假开始时间<br>
	 * @param pEndTime
	 *            请假结束时间<br>
	 * @return Long 计算结果<br>
	 * @throws ParseException
	 * @Exception 发生异常<br>
	 */
	public static Long timeDiffForDay(Date pBeginTime, Date pEndTime) throws ParseException {
		Long beginL = pBeginTime.getTime();
		Long endL = pEndTime.getTime();
		return (endL - beginL) / 86400000;
	}

	/**
	 * 判断2个时间相差多少小时<br>
	 * <br>
	 *
	 * @param pBeginTime
	 *            请假开始时间<br>
	 * @param pEndTime
	 *            请假结束时间<br>
	 * @return String 计算结果<br>
	 * @throws ParseException
	 * @Exception 发生异常<br>
	 */
	public static Long timeDiffForHour(Date pBeginTime, Date pEndTime) throws ParseException {
		Long beginL = pBeginTime.getTime();
		Long endL = pEndTime.getTime();
		return ((endL - beginL) % 86400000) / 3600000;
	}

	/**
	 * 判断2个时间相差多少分<br>
	 * <br>
	 *
	 * @param pBeginTime
	 *            请假开始时间<br>
	 * @param pEndTime
	 *            请假结束时间<br>
	 * @return String 计算结果<br>
	 * @throws ParseException
	 * @Exception 发生异常<br>
	 */
	public static Long timeDiffForMin(Date pBeginTime, Date pEndTime) throws ParseException {
		Long beginL = pBeginTime.getTime();
		Long endL = pEndTime.getTime();
		return ((endL - beginL) % 86400000 % 3600000) / 60000;
	}

	/**
	 * 返回指定时间与当前时间差多少天(小时, 分钟, 刚才)
	 * @param time 要比较的时间
	 * @return 多少天(小时, 分钟, 刚才)
	 */
	public static String getTime(Date time) {
		String result = null;
		Long temp = null;
		final Date currentDate = new Date();

		do {
			try {
				// 看差多少天
				temp = timeDiffForDay(time, currentDate);
				if (temp > 0l) {
					result = temp + "天前";
					break;
				}

				// 看差多少时
				temp = timeDiffForHour(time, currentDate);
				if (temp > 0l) {
					result = temp + "小时前";
					break;
				}

				// 看差多少分
				temp = timeDiffForMin(time, currentDate);
				if (temp > 0l) {
					result = temp + "分种前";
					break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (false);

		return result == null ? "刚才" : result;
	}

	/**
	 * 获取指定日期的星期号
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}

		return DAYNAMES[w];
	}

	/**
	 * 判断当前系统日期是否为星期一
	 * @return
	 */
	public static boolean isFirstDayOfWeek() {
		Date d = new Date();
		String day = getWeekOfDate(d);
		return DAYNAMES[1].equals(day);
	}

	public static Date parseDate(String time) {
		return parseDate(time, null);
	}

	public static Date parseDate(String time, String pattern) {
		if (!StringUtils.hasLength(time)) {
			return null;
		}
		time = time.trim();
		Date d = null;
		String defaultPattern = "yyyy-MM-dd HH:mm:ss";
		if (!StringUtils.hasLength(pattern)) {
			String[] splits = time.split("-");
			switch (splits.length) {
			case 2:
				pattern = "yyyy-MM";
				break;
			case 3:
				pattern = "yyyy-MM-dd";
				break;
			case 4:
				pattern = "yyyy-MM-dd-HH";
				break;
			case 5:
				pattern = "yyyy-MM-dd-HH-mm";
				break;
			case 6:
				pattern = "yyyy-MM-dd-HH-mm-ss";
				break;
			}

			try {
				d = new SimpleDateFormat(pattern).parse(time);
			} catch (ParseException e) {
				d = null;
				e.printStackTrace();
			}

			if (d == null) {
				try {
					d = new SimpleDateFormat(defaultPattern).parse(time);
				} catch (ParseException e) {
					d = null;
					e.printStackTrace();
				}
			}
		} else {
			try {
				d = new SimpleDateFormat(pattern).parse(time);
			} catch (ParseException e) {
				d = null;
				e.printStackTrace();
			}
		}
		return d;
	}

	public static Date parseGMTDate(String time) {
		//Thu, 07 Mar 2013 08:04:06 GMT
		if (!StringUtils.hasText(time)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String compareToday(Date date) {
		String[] dd = new String[] { "前天", "昨天", "今天" };
		Date today = new Date();
		int countInt = getCountofDates(date, today);
		countInt += 2;
		String dateStr = "";
		if (countInt >= 0 && countInt <= 2) {
			dateStr = dd[countInt] + " " + formatDateTime(date, "HH:mm:ss");
		} else {
			dateStr = formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
		}

		return dateStr;
	}

	public static int getCountofDates(Date srcDate, Date target) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = (Date) srcDate.clone();
		try {
			target = parse.parse(format.format(target));
			date = parse.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long mmsec = date.getTime() - target.getTime();
		long count = (mmsec / 3600000 / 24);
		return Integer.parseInt(count + "");
	}

	/**
	 * 获取去年的今天
	 * @return
	 */
	public static String getNowOfLastYear() {
		SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar aGregorianCalendar = new GregorianCalendar();
		aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar.get(Calendar.YEAR) - 1);
		return aSimpleDateFormat.format(aGregorianCalendar.getTime());
	}

	/**
	 * 获取上个月的今天
	 * @return
	 */
	public static String getNowOfLastMonth() {
		SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar aGregorianCalendar = new GregorianCalendar();
		aGregorianCalendar.set(Calendar.MONTH, aGregorianCalendar.get(Calendar.MONTH) - 1);
		return aSimpleDateFormat.format(aGregorianCalendar.getTime());
	}

	public static Date getTodayDate() throws ParseException {
		String strDate = currDay();
		DateFormat df = new SimpleDateFormat(DateUtil.LONG_DATE_FORMAT);
		return df.parse(strDate);
	}

	public static String dateDiff(String startTime, String endTime, String format) {
		String str = "";
		//		new DateUtil().dateDiff(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), "2013-5-28 12:11",
		//		"yyyy-MM-dd HH:mm");
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
		long nh = 1000 * 60 * 60;//一小时的毫秒数
		long nm = 1000 * 60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
			//获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;//计算差多少天
			long hour = diff % nd / nh;//计算差多少小时
			long min = diff % nd % nh / nm;//计算差多少分钟
			long sec = diff % nd % nh % nm / ns;//计算差多少秒
			//输出结果
			str = "" + day + " 天 " + hour + " 小时 " + min + " 分钟 " + sec + " 秒 ";
			//System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * REVIEW
	 * @Description:
	 * @author xi.he@baidao.com
	 * @date 2013-3-18 下午04:35:02
	 * @return
	 */
	public static Date yestoday() {
		return DateUtil.nextDay(new Date(), -1);
	}

	/**
	 * REVIEW
	 * @Description:前天
	 * @author xi.he@baidao.com
	 * @date 2013-3-27 上午10:51:59
	 * @return
	 */
	public static Date beforeLast() {
		return DateUtil.nextDay(new Date(), -2);
	}

	/**
	 * @Description:时间比较大小
	 * @author yi.xu@baidao.com
	 * @date 2013-10-15 上午10:51:59
	 * @return
	 */
	public static int compare_date(Date d1, Date d2) {
		try {
			if (d1.getTime() > d2.getTime()) {
				return 1;
			} else if (d1.getTime() < d2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 *
	 * REVIEW
	 * @Description: 获取，距离现在指定天数，某个时间的日期
	 * @author jingxin.lin@baidao.com
	 * @date 2013-4-23 下午02:51:24
	 * @param day
	 * @param hours
	 * @return
	 */
	public static Date getDate(int day, int hours) {
		Date date = DateUtil.nextDay(new Date(), day);
		date.setHours(hours);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	/**
	 * REVIEW
	 * @Description:
	 * @author xi.he@baidao.com
	 * @date 2013-8-26 下午03:33:28
	 * @param lastModified
	 * @return
	 */
	public static Date subMillisecond(Date date) {
		return new Date((date.getTime() / 1000) * 1000);
	}

	public static String getLastMonth(String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Date lastMonth = calendar.getTime();
		String lm = DateUtil.formatDateTime(lastMonth, format);
		return lm;
	}

	/** 
	 * REVIEW
	 * @Description: 是否是工作时间，周一至周五
	 * @return    
	 * @author huan.wang@baidao.com
	 * @date 2015-2-10 上午11:35:57 
	 */

	public static boolean isWorkingDay() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(week);
		return week > 1 && week < 7;
	}

	/**
	 * 根据date的 hh:mm来计算当周的yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param dayOfWeek
	 * @return
	 */
	public static Date getCurrentHHmmTime(Date date, Integer dayOfWeek) {
		Date now = new Date();

		Calendar nowCal = Calendar.getInstance();
		Calendar dateCal = Calendar.getInstance();

		nowCal.setTime(now);
		dateCal.setTime(date);

		int week = nowCal.get(Calendar.DAY_OF_WEEK);
		nowCal.add(Calendar.DATE, dayOfWeek - week);
		nowCal.set(Calendar.HOUR, dateCal.get(Calendar.HOUR));
		nowCal.set(Calendar.MINUTE, dateCal.get(Calendar.MINUTE));
		nowCal.set(Calendar.SECOND, dateCal.get(Calendar.SECOND));

		return nowCal.getTime();
	}

	private static SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");

	//	public static void main(String[] args) throws Exception {
	//		Date ext = hhmm.parse("20:30");
	//
	//		for (int i = 1; i <= 7; i++) {
	//			System.out.println(formatDateTime(getCurrentHHmmTime(ext, i), "yyyy-MM-dd HH:mm:ss"));
	//		}
	//	}

	/** 
	 * REVIEW
	 * @Description: 根据过期秒数计算过期日期
	 * @param ttl
	 * @return    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @date 2015年8月12日 下午6:48:36 
	 */
	public static Date getExpireDateBySeconds(long ttl) {
		return new Date(new Date().getTime() + ttl * 1000);
	}

	/**
	 * 
	 * REVIEW
	 * @Description:十位时间搓转13位 
	 * @param tenTime
	 * @return    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年4月9日 上午11:22:11
	 */
	public static Long tenCaseThirteen(Long tenTime) {
		String time = tenTime.toString();
		time = time + "000";
		return Long.parseLong(time);
	}

	/**
	 * 
	 * REVIEW
	 * @Description:十三位时间搓转十位 
	 * @param thirteenTime
	 * @return    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年4月9日 上午11:23:33
	 */
	public static Long thirteenCaseTen(Long thirteenTime) {
		String time = thirteenTime.toString();
		time = time.substring(0, 10);
		return Long.parseLong(time);
	}

	/** 
	* REVIEW
	* @Description: 获取本周开始时间
	* @param currentCalendar 当前日历对象
	* @return Date 本周开始时间
	* @author jingkun.wang@baidao.com
	* @date 2016年5月5日 上午10:19:55
	*/
	public static Date getStartOfWeek(Calendar currentCalendar) {
		currentCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return (Date) currentCalendar.getTime().clone();
	}

	/** 
	* REVIEW
	* @Description: 获取本周结束时间
	* @param currentCalendar 当前日历对象
	* @return Date 本周结束时间
	* @author jingkun.wang@baidao.com
	* @date 2016年5月5日 上午10:19:55
	*/
	public static Date getEndOfWeek(Calendar currentCalendar) {
		currentCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		currentCalendar.set(Calendar.HOUR_OF_DAY, 23);
		currentCalendar.set(Calendar.MINUTE, 59);
		currentCalendar.set(Calendar.SECOND, 59);
		currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return (Date) currentCalendar.getTime().clone();
	}

	/** 
	* REVIEW
	* @Description: 获取本周日期数组
	* @return Date[]本周日期数组
	* @author jingkun.wang@baidao.com
	* @date 2016年5月5日 上午10:40:36
	*/
	public static Date[] getCurrentWeek() {
		Calendar currentCalendar = new GregorianCalendar();
		return getCurrentWeek(currentCalendar);
	}

	/** 
	* {@link #getCurrentWeek()}
	*/
	public static Date[] getCurrentWeek(Calendar currentCalendar) {
		Calendar cloneCalender = (Calendar) currentCalendar.clone();
		cloneCalender.setFirstDayOfWeek(Calendar.MONDAY);
		cloneCalender.set(Calendar.HOUR_OF_DAY, 0);
		cloneCalender.set(Calendar.MINUTE, 0);
		cloneCalender.set(Calendar.SECOND, 0);
		cloneCalender.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Date[] week = new Date[7];
		for (int i = 0; i < week.length; i++) {
			week[i] = (Date) cloneCalender.getTime().clone();
			cloneCalender.add(SUB_DAY, 1);
		}

		return week;
	}

	/** 
	* REVIEW
	* @Description: 获取上一周日期数组
	* @return Date[] 上一周日期数组
	* @author jingkun.wang@baidao.com
	* @date 2016年5月5日 上午10:46:12
	*/
	public static Date[] getLastWeek() {
		Calendar currentCalendar = new GregorianCalendar();
		return getLastWeek(currentCalendar);
	}

	/** 
	* {@link #getLastWeek()}
	*/
	public static Date[] getLastWeek(Calendar currentCalendar) {
		Date[] currentWeek = getCurrentWeek(currentCalendar);
		for (int i = 0; i < currentWeek.length; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentWeek[i]);
			cal.add(Calendar.DATE, -7);
			currentWeek[i] = (Date) cal.getTime().clone();
		}
		return currentWeek;
	}

	/** 
	* REVIEW
	* @Description: 获取某一周周五时间
	* @param date 日期
	* @param addWeekNum 添加周数
	* @return 周五日期
	* @author jingkun.wang@baidao.com
	* @date 2016年5月16日 下午3:06:10
	*/
	public static Date getFridayOfWeek(Date date, int addWeekNum) {
		Date monday = DateUtil.getFirstDayOfWeek(date, addWeekNum);
		return DateUtil.nextDay(monday, 4);
	}

	/**
	 * REVIEW
	 * @Description: 获取在当天之前指定天数的日期（不包含周日）
	 * @param specificNum 指定天数
	 * @return 获取在当天之前指定天数的日期（不包含周日）
	 * @author bolong.zhai@baidao.com
	 * @date 2016-07-07 9:58
	 */
	public static Date getSpecificNumDayBeforeNowExcludeSunday(int specificNum) {
		Date specificDate = new Date();
		//排除周日
		Calendar calendar = Calendar.getInstance();
		while (specificNum > 0) {
			specificDate = DateUtil.nextDay(specificDate, -1);
			calendar.setTime(specificDate);
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			if (week != Calendar.SUNDAY) {
				specificNum--;
			}
		}
		//交易时间最早6点开始
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 *
	 * REVIEW
	 * @Description: 简单判断某时间是否为白银现货交易时间（没有考虑西方节日休市的情况，没有考虑美国夏令时、冬令时）
	 * @param date 待判断时间
	 * @return  是否是交易时间
	 * @author bolong.zhai@baidao.com
	 * @date 2016年5月31日 下午4:49:56
	 */
	public static boolean isSilverTradeTime(Date date) {
		Optional<Date> optional = Optional.of(date);
		if (optional.isPresent()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			//每一到周六6点到次日凌晨4点
			if (Calendar.SUNDAY == week) {
				return false;
			}
			//周一8点之前非交易时间
			if (Calendar.MONDAY == week && calendar.get(Calendar.HOUR_OF_DAY) < 8) {
				return false;
			}
			//周六4点之后非交易时间
			if (Calendar.SATURDAY == week && calendar.get(Calendar.HOUR_OF_DAY) > 4) {
				return false;
			}
			//每天04:00点到05:59点之间非交易时间,美国有夏令时、冬令时，中国木有啊，GMT时区不存在
			return !(calendar.get(Calendar.HOUR_OF_DAY) >= 4 && calendar.get(Calendar.HOUR_OF_DAY) < 6);
		}
		return false;
	}

	/**
	 * 判断是否在当天微盘的交易日
	 * @param date 待判断时间
	 * @return 是否在同一个微盘白银交易日内
	 */
	public static boolean isSameCurrentWpTradeDay(Date date) {

		if (!isSilverTradeTime(date)) {
			return false;
		}

		Calendar calendar = Calendar.getInstance();
		if (!isSilverTradeTime(calendar.getTime())) {
			return false;
		}

		int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		if (nowHour < 4) {
			calendar.set(Calendar.DAY_OF_MONTH, nowDay - 1);
		}

		Date startTime = calendar.getTime();
		calendar.add(Calendar.HOUR_OF_DAY, 22);

		Date endTime = calendar.getTime();
		return startTime.getTime() <= date.getTime() && date.getTime() < endTime.getTime();
	}

	/**
	 * {@link #isSilverTradeTime}
	 */
	public static boolean isSilverTradeTimeNow() {
		return isSilverTradeTime(new Date());
	}

	/** 
	* REVIEW
	* @Description: 移除时分秒
	* @return 
	* @author jingkun.wang@baidao.com
	* @date 2016年8月5日 下午2:55:49
	*/
	public static Date removeHms(Date date) {
		String todayDate = DateUtil.formatDateTime(date, DateUtil.LONG_DATE_FORMAT);
		return DateUtil.toDate(todayDate, DateUtil.LONG_DATE_FORMAT);
	}

	/** 
	* REVIEW
	* @Description: 获取交易时间开始、结束时间
	* @param date 以给定时间的零时为准
	* @return 0：开始时间，1：结束时间
	* @author jingkun.wang@baidao.com
	* @date 2016年8月5日 下午5:54:29
	*/
	public static Date[] getTradingDate(Date date) {
		Date[] tradingDate = new Date[2];
		date = DateUtil.removeHms(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 4);

		tradingDate[0] = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		tradingDate[1] = cal.getTime();
		return tradingDate;
	}

}
