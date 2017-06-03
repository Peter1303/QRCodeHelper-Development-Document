package com.QRCodeHelper.Develper.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.QRCodeHelper.Develper.Document.MainActivity;
import com.QRCodeHelper.Develper.Document.utils.QRCodeHelperUtils;
import android.content.Intent;

public class MainActivity extends Activity 
{
	
	private Button bn;
	private TextView tv;
	
	private Context con = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		bn = (Button) findViewById(R.id.main_Button);
		tv = (TextView) findViewById(R.id.main_TextView);
		
		bn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					try {
						QRCodeHelperUtils.startQRCodeScan(MainActivity.this);
					} catch (Exception e){
						new AlertDialog.Builder(con)
						.setMessage("发生错误：\n"+e.toString())
						.setPositiveButton(android.R.string.ok, null)
						.show();
					}
				}
			});
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
			result += "\n\n●类型：" + data.getStringExtra("TYPE");
			setTip(result);
		} else {
			setTip("发生错误或没有结果");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setTip (String s) {
		tv.setText(s);
	}
	
	
}
