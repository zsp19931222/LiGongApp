package yh.app.acticity;

import android.content.Intent;
import android.os.Bundle;
import yh.app.activitytool.ActivityPortrait;
import yh.app.appstart.AppStart;

public class OtherJumpActivity extends ActivityPortrait
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	Intent intent = new Intent(this, AppStart.class);
	startActivity(intent);
	finish();
    }
}
