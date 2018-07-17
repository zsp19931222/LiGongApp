//package yh.app.calendar;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.androidpn.push.Constants;
//
//import  com.yhkj.cqswzyxy.R;
//import yh.app.tool.weekToDate;
//import yh.app.calendar.CalendarActivity;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
// 
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//
///**
// * 
// * 包 名:yh.app.calendar 
// * 类 名:CalendarView.java 
// * 功 能:继承View控件,校历View
// * 
// * @author 云华科技
// * @version 1.0
// * @date 2015-7-29
// */
//public class CalendarView extends View implements View.OnTouchListener {
//	private final static String TAG = "anCalendar";
//	private Date selectedStartDate;
//	private Date selectedEndDate;
//	private Date curDate;
//	private Date today;
//	private static Date downDate = new Date(System.currentTimeMillis()); // 按下的是哪一天
//	private Date 		showFirstDate, showLastDate;
//	private int downIndex;
//	weekToDate weektodate;
//	private Calendar calendar;
//	private Surface surface;
//	private int[] date = new int[42];
//	private int curStartIndex, curEndIndex;
//	private boolean completed = false;
//	private boolean isSelectMore = false;
//	private OnItemClickListener onItemClickListener;
//
//	public CalendarView(Context context) {
//		super(context);
//		init();
//	}
//
//	public CalendarView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init();
//	}
//
//	public static Date getDownDate() {
//		return downDate;
//	}
//
//	private void init() {
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor result = db.rawQuery("select * from term where userid='"
//				+ Constants.number + "' and ifnow='true'", null);
//		result.moveToFirst();
//		while (!result.isAfterLast()) {
//			weektodate = new weekToDate(result.getString(4),
//					result.getString(5));
//			result.moveToNext();
//		}
//		db.close();
//		curDate = selectedStartDate = selectedEndDate = today = new Date();
//		calendar = Calendar.getInstance();
//		calendar.setTime(curDate);
//		surface = new Surface();
//		surface.density = getResources().getDisplayMetrics().density;
//		setBackgroundColor(surface.bgColor);
//		setOnTouchListener(this);
//	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		surface.width = getResources().getDisplayMetrics().widthPixels;
//		int i = getResources().getDisplayMetrics().heightPixels * 2 / 6;
//		surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 3 / 6) - 13;  //初始2
//		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
//				View.MeasureSpec.EXACTLY);
//		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
//				View.MeasureSpec.EXACTLY);
//		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}
//
//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//		Log.d(TAG, "[onLayout] changed:"
//				+ (changed ? "new size" : "not change") + " left:" + left
//				+ " top:" + top + " right:" + right + " bottom:" + bottom);
//		if (changed) {
//			surface.init();
//		}
//		super.onLayout(changed, left, top, right, bottom);
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas) {
//		Log.d(TAG, "onDraw");
//		canvas.drawPath(surface.boxPath, surface.borderPaint);
//		float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
//		for (int i = 0; i < surface.weekText.length; i++) {
//			float weekTextX = i
//					* surface.cellWidth
//					+ (surface.cellWidth - surface.weekPaint
//							.measureText(surface.weekText[i])) / 2f;
//			canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
//					surface.weekPaint);
//		}
//		calculateDate();
//
//		int todayIndex = -1;
//		calendar.setTime(curDate);
//		String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
//				+ calendar.get(Calendar.MONTH);
//		calendar.setTime(today);
//		String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
//				+ calendar.get(Calendar.MONTH);
//		if (curYearAndMonth.equals(todayYearAndMonth)) {
//			int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
//			todayIndex = curStartIndex + todayNumber - 1;
//			if (downIndex == 0) {
//				downIndex = (todayIndex / 7) * 8 + todayIndex % 7 + 1;
//			}
//		}
//		drawDownOrSelectedBg(canvas);
//		for (int i = 0; i < 48; i++) {
//			String Ndate = getYearAndmonth();
//			int iscurrent = 0;
//			int color = surface.textColor;
//			if (isLastMonth((i / 8) * 7 + i % 8 - 1)) {
//				iscurrent = -1;
//				color = surface.borderColor;
//			} else if (isNextMonth((i / 8) * 7 + i % 8 - 1)) {
//				iscurrent = 1;
//				color = surface.borderColor;
//			}
//			if (todayIndex != -1 && ((i / 8) * 7 + i % 8 - 1) == todayIndex) {
//				color = surface.todayNumberColor;
//			}
//			if (i % 8 == 0) {
//				Ndate += "-" + date[((i / 8) * 7 + i % 8 - 1) + 1];
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				Date nowdate = null;
//				try {
//					nowdate = sdf.parse(Ndate);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				int week = weekToDate.getCurrentWeekNum(nowdate);
//				if (iscurrent == 1) {
//					drawCellText(canvas, i, " ", color);
//				} else if (iscurrent == -1) {
//					drawCellText(canvas, i, " ", color);
//				} else {
//					if (week > 20 || week <= 0) {
//						drawCellText(canvas, i, "放假", color);
//					} else {
//						drawCellText(canvas, i, week + "周", color);
//					}
//				}
//			} else {
//				drawCellText(canvas, i, date[(i / 8) * 7 + i % 8 - 1] + "",
//						color);
//			}
//		}
//		super.onDraw(canvas);
//	}
//
//	private void calculateDate() {
//		calendar.setTime(curDate);
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
//		Log.d(TAG, "day in week:" + dayInWeek);
//		int monthStart = dayInWeek;
//		if (monthStart == 1) {
//			monthStart = 8;
//		}
//		monthStart -= 1;
//		curStartIndex = monthStart;
//		date[monthStart] = 1;
//		// last month
//		if (monthStart > 0) {
//			calendar.set(Calendar.DAY_OF_MONTH, 0);
//			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
//			for (int i = monthStart - 1; i >= 0; i--) {
//				date[i] = dayInmonth;
//				dayInmonth--;
//			}
//			calendar.set(Calendar.DAY_OF_MONTH, date[0]);
//		}
//		showFirstDate = calendar.getTime();
//		// this month
//		calendar.setTime(curDate);
//		calendar.add(Calendar.MONTH, 1);
//		calendar.set(Calendar.DAY_OF_MONTH, 0);
//		// Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
//		// calendar.get(Calendar.DAY_OF_MONTH));
//		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
//		for (int i = 1; i < monthDay; i++) {
//			date[monthStart + i] = i + 1;
//		}
//		curEndIndex = monthStart + monthDay;
//		// next month
//		for (int i = monthStart + monthDay; i < 42; i++) {
//			date[i] = i - (monthStart + monthDay) + 1;
//		}
//		if (curEndIndex < 42) {
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
//		}
//		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
//		showLastDate = calendar.getTime();
//	}
//
//	/**
//	 * 
//	 * @param canvas
//	 * @param index
//	 * @param text
//	 */
//	private void drawCellText(Canvas canvas, int index, String text, int color) {
//		int x = getXByIndex(index);
//		int y = getYByIndex(index);
//		surface.datePaint.setColor(color);
//		float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
//				* surface.cellHeight + surface.cellHeight * 3 / 4f;
//		float cellX = (surface.cellWidth * (x - 1))
//				+ (surface.cellWidth - surface.datePaint.measureText(text))
//				/ 2f;
//		canvas.drawText(text, cellX, cellY, surface.datePaint);
//	}
//
//	/**
//	 * 
//	 * @param canvas
//	 * @param index
//	 * @param color
//	 */
//	private void drawCellBg(Canvas canvas, int index, int color) {
//		int x = getXByIndex(index);
//		int y = getYByIndex(index);
//		surface.cellBgPaint.setColor(color);
//		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
//		float top = surface.monthHeight + surface.weekHeight + (y - 1)
//				* surface.cellHeight + surface.borderWidth;
//		canvas.drawRect(left, top, left + surface.cellWidth
//				- surface.borderWidth, top + surface.cellHeight
//				- surface.borderWidth, surface.cellBgPaint);
//	}
//
//	private void drawDownOrSelectedBg(Canvas canvas) {
//		// down and not up
//		if (downDate != null) {
//			drawCellBg(canvas, downIndex, surface.cellDownColor);
//		}
//		// selected bg color
//		if (!selectedEndDate.before(showFirstDate)
//				&& !selectedStartDate.after(showLastDate)) {
//			int[] section = new int[] { -1, -1 };
//			calendar.setTime(curDate);
//			calendar.add(Calendar.MONTH, -1);
//			findSelectedIndex(0, curStartIndex, calendar, section);
//			if (section[1] == -1) {
//				calendar.setTime(curDate);
//				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
//			}
//			if (section[1] == -1) {
//				calendar.setTime(curDate);
//				calendar.add(Calendar.MONTH, 1);
//				findSelectedIndex(curEndIndex, 48, calendar, section);
//			}
//			if (section[0] == -1) {
//				section[0] = 0;
//			}
//			if (section[1] == -1) {
//				section[1] = 47;
//			}
//			for (int i = section[0]; i <= section[1]; i++) {
//				drawCellBg(canvas, downIndex, surface.cellSelectedColor);
//			}
//		}
//	}
//
//	private void findSelectedIndex(int startIndex, int endIndex,
//			Calendar calendar, int[] section) {
//		for (int i = startIndex; i < endIndex; i++) {
//			calendar.set(Calendar.DAY_OF_MONTH, date[i]);
//			Date temp = calendar.getTime();
//			// Log.d(TAG, "temp:" + temp.toLocaleString());
//			if (temp.compareTo(selectedStartDate) == 0) {
//				section[0] = i;
//			}
//			if (temp.compareTo(selectedEndDate) == 0) {
//				section[1] = i;
//				return;
//			}
//		}
//	}
//
//	public Date getSelectedStartDate() {
//		return selectedStartDate;
//	}
//
//	public Date getSelectedEndDate() {
//		return selectedEndDate;
//	}
//
//	private boolean isLastMonth(int i) {
//		if (i < curStartIndex) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean isNextMonth(int i) {
//		if (i >= curEndIndex) {
//			return true;
//		}
//		return false;
//	}
//
//	private int getXByIndex(int i) {
//		return i % 8 + 1; // 1 2 3 4 5 6 7
//	}
//
//	private int getYByIndex(int i) {
//		return i / 8 + 1; // 1 2 3 4 5 6
//	}
//
//	// ��õ�ǰӦ����ʾ������
//	public String getYearAndmonth() {
//		calendar.setTime(curDate);
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		return year + "-" + month;
//	}
//
//	// ��һ��
//	public String clickLeftMonth() {
//		calendar.setTime(curDate);
//		calendar.add(Calendar.MONTH, -1);
//		curDate = calendar.getTime();
//		invalidate();
//		return getYearAndmonth();
//	}
//
//	// ��һ��
//	public String clickRightMonth() {
//		calendar.setTime(curDate);
//		calendar.add(Calendar.MONTH, 1);
//		curDate = calendar.getTime();
//		invalidate();
//		return getYearAndmonth();
//	}
//
//	// ��������ʱ��
//	public void setCalendarData(Date date) {
//		calendar.setTime(date);
//		invalidate();
//	}
//
//	// ��ȡ����ʱ��
//	public void getCalendatData() {
//		calendar.getTime();
//	}
//
//	// �����Ƿ��ѡ
//	public boolean isSelectMore() {
//		return isSelectMore;
//	}
//
//	public void setSelectMore(boolean isSelectMore) {
//		this.isSelectMore = isSelectMore;
//	}
//
//	private void setSelectedDateByCoor(float x, float y) {
//		if (y > surface.monthHeight + surface.weekHeight) {
//			// 获取按下的是哪一格
//			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
//			int n = (int) (Math
//					.floor((y - (surface.monthHeight + surface.weekHeight))
//							/ Float.valueOf(surface.cellHeight)) + 1);
//			downIndex = (n - 1) * 8 + m - 1; // 按下的是第几格(从头开始数)
//			int dateindex = 0;
//			if (m != 1 || n != 1) {
//				dateindex = (n - 1) * 7 + m - 2; // 按下坐标与数据坐标
//			}
//
//			calendar.setTime(curDate);
//			if (isLastMonth(dateindex)) {
//				calendar.add(Calendar.MONTH, -1);
//			} else if (isNextMonth(dateindex)) {
//				calendar.add(Calendar.MONTH, 1);
//			}
//			calendar.set(Calendar.DAY_OF_MONTH, date[dateindex]);
//			downDate = calendar.getTime();
//		}
//		invalidate();
//	}
//
//	ListView diaryList111;
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			setSelectedDateByCoor(event.getX(), event.getY());
//			CalendarActivity.showDiary();
//			break;
//		case MotionEvent.ACTION_UP:
//			if (downDate != null) {
//				if (isSelectMore) {
//					if (!completed) {
//						if (downDate.before(selectedStartDate)) {
//							selectedEndDate = selectedStartDate;
//							selectedStartDate = downDate;
//						} else {
//							selectedEndDate = downDate;
//						}
//						completed = true;
//						// ��Ӧ�����¼�
//						onItemClickListener.OnItemClick(selectedStartDate,
//								selectedEndDate, downDate);
//					} else {
//						selectedStartDate = selectedEndDate = downDate;
//						completed = false;
//					}
//				} else {
//					selectedStartDate = selectedEndDate = downDate;
//					// ��Ӧ�����¼�
//					onItemClickListener.OnItemClick(selectedStartDate,
//							selectedEndDate, downDate);
//				}
//				invalidate();
//			}
//
//			break;
//		}
//		return true;
//	}
//	
////	private List<Map<String, Object>> getData(Date downDate) {
////		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
////		Map<String, Object> map = new HashMap<String, Object>();
////		SQLiteDatabase db = new SqliteHelper().getWrite();
////		Cursor myDiary = db
////				.query("myDiary", null, null, null, null, null, null);
////		while (myDiary.moveToNext()) {
////			map = new HashMap<String, Object>();
////			String str = "";
////			try {
////				str = new SimpleDateFormat("yyyy-MM-dd").format( new SimpleDateFormat("yyyy-MM-dd").parse(myDiary.getString(0)));
////			} catch (ParseException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			String s = new SimpleDateFormat("yyyy-MM-dd").format(downDate);
////			if(str.equals(s)){
////				map.put("title", myDiary.getString(1));
////				map.put("content", myDiary.getString(2));
////				map.put("date", str);
////				list.add(map);
////			}
////		}
////		db.close();
////		return list;
////	} 
//
//	// ��ؼ����ü����¼�
//	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//		this.onItemClickListener = onItemClickListener;
//	}
//
//	// ����ӿ�
//	public interface OnItemClickListener {
//		void OnItemClick(Date selectedStartDate, Date selectedEndDate,
//				Date downDate);
//	}
//
//	/**
//	 * 
//	 * 1. ���ֳߴ� 2. ������ɫ����С 3. ��ǰ���ڵ���ɫ��ѡ���������ɫ
//	 */
//	private class Surface {
//		public float density;
//		public int width;
//		public int height;
//		public float monthHeight;
//		// public float monthChangeWidth;
//		public float weekHeight;
//		public float cellWidth;
//		public float cellHeight;
//		public float borderWidth;
//		public int bgColor = Color.parseColor("#FFFFFF");
//		private int textColor = Color.BLACK;
//		// private int textColorUnimportant = Color.parseColor("#666666");
//		private int btnColor = Color.parseColor("#666666");
//		private int borderColor = Color.parseColor("#CCCCCC");
//		public int todayNumberColor = Color.RED;
//		public int cellDownColor = Color.parseColor("#CCFFFF");
//		public int cellSelectedColor = Color.parseColor("#99CCFF");
//		public Paint borderPaint;
//		public Paint monthPaint;
//		public Paint weekPaint;
//		public Paint datePaint;
//		public Paint monthChangeBtnPaint;
//		public Paint cellBgPaint;
//		public Path boxPath;
//		// public Path preMonthBtnPath;
//		// public Path nextMonthBtnPath;
//		public String[] weekText = { "学周", "日", "一", "二", "三", "四", "五", "六" };
//
//		// public String[] monthText =
//		// {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//
//		public void init() {
//			float temp = 213;/*height / 7f;*/
//			monthHeight = 0;// (float) ((temp + temp * 0.3f) * 0.6);
//			// monthChangeWidth = monthHeight * 1.5f;
//			weekHeight = /*(float) ((temp + temp * 0.3f) * 0.7);*/50;
//			cellHeight = width / 8f/*(height - monthHeight - weekHeight) / 6f*/;// 单元格高度
//			cellWidth = width / 8f; // 单元格宽度
//			borderPaint = new Paint();
////			borderPaint.setColor(borderColor);
////			borderPaint.setStyle(Paint.Style.STROKE);
//			borderWidth = (float) (0.5 * density);
//			// Log.d(TAG, "borderwidth:" + borderWidth);
//			borderWidth = borderWidth < 1 ? 1 : borderWidth;
////			borderPaint.setStrokeWidth(borderWidth);
//			monthPaint = new Paint();
//			monthPaint.setColor(textColor);
//			monthPaint.setAntiAlias(true);
//			float textSize = cellHeight * 0.4f;
//			Log.d(TAG, "text size:" + textSize);
//			monthPaint.setTextSize(textSize);
//			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
//			weekPaint = new Paint();
//			weekPaint.setColor(textColor);
//			weekPaint.setAntiAlias(true);
//			float weekTextSize = weekHeight * 0.6f;
//			weekPaint.setTextSize(weekTextSize);
//			weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
//			datePaint = new Paint();
//			datePaint.setColor(textColor);
//			datePaint.setAntiAlias(true);
//			float cellTextSize = cellHeight * 0.5f;
//			datePaint.setTextSize(cellTextSize);
//			datePaint.setTypeface(Typeface.DEFAULT_BOLD);
//			boxPath = new Path();
//			boxPath.rLineTo(width, 0);
//			boxPath.moveTo(0, monthHeight + weekHeight);
//			boxPath.rLineTo(width, 0);
//			for (int i = 1; i < 7; i++) {
//				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
//				boxPath.rLineTo(width, 0);
//				boxPath.moveTo(i * cellWidth, monthHeight);
//				boxPath.rLineTo(0, height - monthHeight);
//			}
//			boxPath.moveTo(7 * cellWidth, monthHeight);
//			boxPath.rLineTo(0, height - monthHeight);
//			monthChangeBtnPaint = new Paint();
//			monthChangeBtnPaint.setAntiAlias(true);
//			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//			monthChangeBtnPaint.setColor(btnColor);
//			cellBgPaint = new Paint();
//			cellBgPaint.setAntiAlias(true);
//			cellBgPaint.setStyle(Paint.Style.FILL);
//			cellBgPaint.setColor(cellSelectedColor);
//		}
//	}
//
//
//}
