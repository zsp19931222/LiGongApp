package yh.app.uiengine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import yh.app.activitytool.ActivityPortrait;
import com.yhkj.cqgyxy.R;
public class VideoActivity extends ActivityPortrait
{
	private VideoView videoView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_video_layout);
		
		
		videoView = (VideoView) findViewById(R.id.videoView);
		
		
		String url = getIntent().getStringExtra("url");
		
		
		videoView.setVideoURI(Uri.parse(url));
	}
}