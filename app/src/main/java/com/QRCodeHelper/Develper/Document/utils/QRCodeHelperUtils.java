package com.QRCodeHelper.Develper.Document.utils;

import android.app.Activity;
import android.content.Intent;

public class QRCodeHelperUtils
{
	//类型
	public static String TYPE_TEXT="TYPE_TEXT";//文本
	public static String TYPE_URL="TYPE_URL";//完整链接（附带有网络协议）
	public static String TYPE_URL_MISSING="TYPE_URL_MISS";//链接（缺少网络协议）
	public static String TYPE_TEL="TYPE_TEL";//电话，真的只是座机
	public static String TYPE_MOBILE_TEL="TYPE_MOBILE_TEL";//手机号码
	public static String TYPE_EMAIL="TYPE_EMAIL";//电子邮箱
	public static String TYPE_VCARD="TYPE_VCARD";//就是一个包含有 名片卡 信息的VCard
	public static String TYPE_WIFI="TYPE_WIFI";//WIFI卡片
	public static String TYPE_MARKET_SEARCH="TYPE_MARKET_SEARCH";//应用市场搜索的二维码
	public static String TYPE_MARKET_DETAILED="TYPE_MARKET_DETAILED";//应用市场详细二维码
	
	public static int RESULT_CODE_QRCODE=1303;
	//从外部调用 二维码 扫描
	public static String startQRCodeScan(Activity a) throws Exception{
		Intent i = new Intent();
		//i.setAction("com.QRCode.helper.SCAN");
		i.setClassName("com.QRCode.helper","com.QRCode.helper.qrcode.CaptureActivity");
		i.putExtra("API",true);
		try {
			a.startActivityForResult(i,RESULT_CODE_QRCODE);
		} catch (Exception e){
			return e.toString();
		}
		return null;
	}
	
	
	
	
	
	
}
