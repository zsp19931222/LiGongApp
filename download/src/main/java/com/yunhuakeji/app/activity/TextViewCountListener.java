package com.yunhuakeji.app.activity;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TextViewCountListener implements TextWatcher
{
	private int MaxCount;
	private EditText text;
	private TextView textCount;

	public TextViewCountListener(EditText text, TextView textCount, int MaxCount)
	{
		this.MaxCount = MaxCount;
		this.text = text;
		this.textCount = textCount;
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		if (s != null && s.length() > MaxCount)
		{
			text.setText(s.toString().substring(0, MaxCount));
		}
		try
		{
			textCount.setHint(text.getText().length() + "/" + MaxCount);
		} catch (Exception e)
		{
			textCount.setHint(0 + "/" + MaxCount);
		}
	}

}
