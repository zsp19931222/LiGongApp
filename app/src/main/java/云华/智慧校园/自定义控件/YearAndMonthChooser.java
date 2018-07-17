package 云华.智慧校园.自定义控件;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * 重写datePicker 1.只显示 年-月 2.title 只显示 年-月
 * 
 * @author lmw
 */
public class YearAndMonthChooser extends DatePickerDialog
{
	public YearAndMonthChooser(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
	{
		super(context, AlertDialog.THEME_HOLO_LIGHT, callBack, year, monthOfYear, 1);
		this.setTitle(year + "年" + (monthOfYear + 1) + "月");
		((ViewGroup)((ViewGroup)this.getDatePicker().getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day)
	{
		// super.onDateChanged(view, year, month, day);
		this.setTitle(year + "年" + (month + 1) + "月");
	}

	@Override
	public void updateDate(int year, int monthOfYear, int dayOfMonth)
	{
		// TODO Auto-generated method stub
//		super.updateDate(year, monthOfYear, dayOfMonth);
	}

}
