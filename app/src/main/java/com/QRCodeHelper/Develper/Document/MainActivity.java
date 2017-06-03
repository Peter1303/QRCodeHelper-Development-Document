package com.QRCodeHelper.Develper.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.QRCodeHelper.Develper.Document.MainActivity;
import com.QRCodeHelper.Develper.Document.utils.QRCodeHelperUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity 
{
	
	private Button bn, bn2;
	private TextView tv;
	private ImageView img;
	
	private Context con = this;
	
	//private String assetsFile = "file:///android_asset/QRCode.jpg";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		bn = (Button) findViewById(R.id.main_Button);
		bn2 = (Button) findViewById(R.id.main_Button2);
		img = (ImageView) findViewById(R.id.main_ImageView);
		tv = (TextView) findViewById(R.id.main_TextView);
		
		final Bitmap b = getBitmap();
		img.setImageBitmap(b);
		bn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					try {
						QRCodeHelperUtils.startQRCodeScan(MainActivity.this);
					} catch (Exception e){
						showError(e.toString());
					}
				}
			});
		bn2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					File f = new File(getAppStorageDir()+"/QRCode.jpg");
					if (f.exists()){
						scanPicQRCode(f);
						//setTip(f +" 已存在");
					} else {
						//setTip("输出 " + f);
						boolean mBoolean = saveBitmap(b, f);
						if (mBoolean) {
							scanPicQRCode(f);
						} else {
							showError("无法保存图片");
						}
						
					}
					
				}
			});
    }
	
	private void scanPicQRCode(File f){
		try {
			QRCodeHelperUtils.startQRCodeScan(MainActivity.this,f);
		} catch (Exception e){
			showError(e.toString());
		}
	}
	
	private void showError(String s)
	{
		new AlertDialog.Builder(con)
			.setMessage("发生错误：\n"+s)
			.setPositiveButton(android.R.string.ok, null)
			.show();
	}
	
	private File getAppStorageDir()
	{
		File file = new File(getExternalFilesDir(null) + File.separator + "/");
        if (!file.exists()){
			file.mkdirs();
		}
        return file;
	}
	
	private Bitmap getBitmap()
	{
		return BitmapFactory.decodeResource(getResources(), R.drawable.qrc);
	}  
	
	private boolean saveBitmap(Bitmap mBitmap ,File f)
	{
		try {
			if (f.exists()){
				f.delete();
			} else {
				f.createNewFile();
			}
		} catch (IOException e) {
			showError(e.toString());
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			showError(e.toString());
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
		try {
			fileOut.flush();
		} catch (IOException e) {
			showError(e.toString());
		}
		try {
			fileOut.close();
		} catch (IOException e) {
			showError(e.toString());
		}
		if(f.exists()){
			return true;
		} else{
			return false;
		}
	}
	
	private String result = "";
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		/*
		这里返回两个值：
		
		RESULT：扫描结果（不会为空）
		 TYPE：扫描结果类型（请看QRCodeHelperUtils）
		*/
		if (requestCode==QRCodeHelperUtils.RESULT_CODE_QRCODE &&
			data != null &&
			data.hasExtra("RESULT")) {
			result = "●结果：\n" + data.getStringExtra("RESULT");
			result += "\n\n●类型：\n" + data.getStringExtra("TYPE");
			setTip(result);
		} else {
			setTip("发生错误或没有结果");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setTip (String s)
	{
		tv.setText(s);
	}
	
	
}
