package com.example.wordmaker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class helpView_page extends Activity {

	EditText messageET;
	Button sendBtn;
	String message, number;
	TextView text1, text2, text3, text4, text5, text6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);
		text6 = (TextView) findViewById(R.id.text6);
		
		String fontPath = "fonts/Second Grader.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        text1.setTypeface(tf);
        text2.setTypeface(tf);
        text3.setTypeface(tf);
        text4.setTypeface(tf);
        text5.setTypeface(tf);
        text6.setTypeface(tf);
        
	}
	
	public void onBtnClick(View v){
		Intent myIntent = new Intent(v.getContext(), howAppWorks.class);
        startActivityForResult(myIntent, 0);
	}
}
