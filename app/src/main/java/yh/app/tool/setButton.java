package yh.app.tool;

import android.widget.Button;

public class setButton
{
	private Button button;
	
	public setButton(Button button)
	{
		this.button = button;
	}
	
	public Button addMessage(String text, int TextSize, int TextColor, int Width, int Height, int bg_id)
	{
		button.setText(text);
		button.setTextSize(TextSize);
		button.setTextColor(TextColor);
		button.setWidth(Width);
		button.setHeight(Height);
		button.setBackgroundResource(bg_id);
		return button;
	}
}
