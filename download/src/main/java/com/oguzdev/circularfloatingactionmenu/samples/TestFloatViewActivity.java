package com.oguzdev.circularfloatingactionmenu.samples;

import com.yunhuakeji.app.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestFloatViewActivity extends Activity implements View.OnClickListener
{

	private String[] array;

	private static final int id1 = 1;

	private static final int id2 = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_with_fab);

		array = getResources().getStringArray(R.array.yh_strings_floatview_array);

		// Set up the white button on the lower right corner
		// more or less with default parameter
		final ImageView fabIconNew = new ImageView(this);
//		fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_new_light));
		fabIconNew.setBackgroundResource(R.drawable.ic_action_new_light);
		final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this).setContentView(fabIconNew).build();
		
		SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
		TextView rlIcon1 = new TextView(this);
		TextView rlIcon2 = new TextView(this);

//		rlIcon1.setBackgroundResource(R.drawable.ic_action_chat_light);
//		rlIcon2.setBackgroundResource(R.drawable.ic_action_camera_light);
		
		rlIcon1.setText(array[0]);
		rlIcon2.setText(array[1]);
		
		SubActionButton tcSub1 = rLSubBuilder.setContentView(rlIcon1).build();
		SubActionButton tcSub2 = rLSubBuilder.setContentView(rlIcon2).build();

		// Build the menu with default options: light theme, 90 degrees, 72dp radius.
		// Set 4 default SubActionButtons
		final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
				.addSubActionView(tcSub1)
				.addSubActionView(tcSub2)
				.attachTo(rightLowerButton)
				.setRadius(150)
				.build();

		// Listen menu open and close events to animate the button content view
		rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener()
		{
			@Override
			public void onMenuOpened(FloatingActionMenu menu)
			{
				// Rotate the icon of rightLowerButton 45 degrees clockwise
				fabIconNew.setRotation(0);
				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				animation.start();
			}

			@Override
			public void onMenuClosed(FloatingActionMenu menu)
			{
				// Rotate the icon of rightLowerButton 45 degrees counter-clockwise
				fabIconNew.setRotation(45);
				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				animation.start();
			}
		});
		tcSub1.setOnClickListener(this);
		tcSub2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO
	}
}