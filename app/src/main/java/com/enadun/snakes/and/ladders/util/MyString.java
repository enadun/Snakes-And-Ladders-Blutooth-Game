package com.enadun.snakes.and.ladders.util;

import com.enadun.snakes.and.ladders.GameActivity;

public class MyString {
	public static String getStringById(int id){
		return GameActivity.activity.getResources().getString(id);
	}
	
	public static String getStringById(int id, String msg){
		return GameActivity.activity.getResources().getString(id, msg);
	}
	
	public static String getStringById(int id, int value){
		return GameActivity.activity.getResources().getString(id, value);
	}
}
