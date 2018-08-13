package yh.app.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import yh.app.tool.PackAganmanger;

public class FileUtils {
	
//	public static Bitmap GetLocalOrNetBitmap(String url)
//	 { 
//	  Bitmap bitmap = null; 
//	  InputStream in = null; 
//	  BufferedOutputStream out = null; 
//	  try
//	  { 
//	   in = new BufferedInputStream(new URL(url).openStream(), Constant.IO_BUFFER_SIZE); 
//	   final ByteArrayOutputStream dataStream = new ByteArrayOutputStream(); 
//	   out = new BufferedOutputStream(dataStream, Constant.IO_BUFFER_SIZE); 
//	   copy(in, out); 
//	   out.flush(); 
//	   byte[] data = dataStream.toByteArray(); 
//	   bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); 
//	   data = null; 
//	   return bitmap; 
//	  } 
//	  catch (IOException e) 
//	  { 
//	   e.printStackTrace(); 
//	   return null; 
//	  } 
//	 }
	
	/**
	 * 地址转Bitmap
	 * @param url
	 * @return
	 */
	
	public static Bitmap getBitmap(String url){
		Bitmap bitmap=null;
		InputStream inputStream=null;//输入流
		BufferedOutputStream outputStream=null;//输出流
		try {
			inputStream=new BufferedInputStream(new URL(url).openStream(),Constants.IO_BUFFER_SIZE);
			final ByteArrayOutputStream dataStream=new ByteArrayOutputStream();
			outputStream=new BufferedOutputStream(dataStream, Constants.IO_BUFFER_SIZE);
			outputStream.flush();
			byte[] data=dataStream.toByteArray();
			bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			data=null;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return bitmap;
		
	}

	/**
	 * 读取本地json文件
	 * */
	public static String readJsonFile(Context context, String fileName) {

		StringBuilder builder = new StringBuilder();
		int id = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
		InputStream inputStream = context.getResources().openRawResource(id);
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String content;
			while ((content = bufferedReader.readLine()) != null) {
				builder.append(content);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();

	}
}
