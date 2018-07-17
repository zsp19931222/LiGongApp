package yh.app.uiengine;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.MediaColumns;
import yh.app.activitytool.ActivityPortrait;
import 云华.智慧校园.自定义控件.CutView;import yh.app.appstart.lg.R;

public class ChoiceAndCutPictrue extends ActivityPortrait
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_and_cut_pictrue);
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);
	}

	public String getRealPathFromURI(Uri contentUri)
	{
		String res = null;
		String[] proj =
		{
				MediaColumns.DATA
		};
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		if (cursor.moveToFirst())
		{
			;
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			Uri mUri = data.getData();
			Bitmap tmpBitmap = BitmapFactory.decodeFile(getRealPathFromURI(mUri));
			CutView myCutView = (CutView) this.findViewById(R.id.myCutView);
			myCutView.setBitmap(tmpBitmap);
			myCutView.setHandler(new Handler());
			//Uri mUri = data.getData();
			// if (null == mUri)
			// return;
			// Intent intent = new Intent();
			// intent.setAction("com.android.camera.action.CROP");
			// intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri
			// intent.putExtra("crop", "true");
			// intent.putExtra("aspectX", 1);// 裁剪框比例
			// intent.putExtra("aspectY", 1);
			// intent.putExtra("outputX", 150);// 输出图片大小
			// intent.putExtra("outputY", 150);
			// intent.putExtra("return-data", true);
			// startActivityForResult(intent, 200);
			// ContentResolver cr = this.getContentResolver();
			// try
			// {
			// Bitmap bitmap =
			// BitmapFactory.decodeStream(cr.openInputStream(uri));
			// ImageView imageView = (ImageView) findViewById(R.id.img);
			// /* 将Bitmap设定到ImageView */
			// imageView.setBackground(new BitmapDrawable(this.getResources(),
			// bitmap));
			// file = FileTools.saveFile(bitmap, Constants.number + "tx.png",
			// "tx");
			// } catch (FileNotFoundException e)
			// {
			// } catch (Exception e)
			// {
			// e.printStackTrace();
			// }
		}
		// Uri mUri = queryUriForVideo();

	}
}
