package com.example.wordmaker;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class recommendation_page extends Activity {

	EditText messageET;
	TextView text;
	Button sendBtn;
	String message, number;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommendationPage);
		messageET = (EditText) findViewById(R.id.editText1);
		text = (TextView) findViewById(R.id.text1);
		
		String fontPath = "fonts/Annifont.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        text.setTypeface(tf);
		
	}
	
	public void sendBtn(View v){
		
		number = messageET.getText().toString();
		
		if(number.length() == 10){

			  String sms = "Hey this is just project demo :) ";
	
			  try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(number, null, sms, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
			  } catch (Exception e) {
				Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
				e.printStackTrace();
			  }
		}
		else{
			alertBox("Please Entre valid Phone Number");
			messageET.setText(number.length() + " " + number);
		}
		
		messageET.setText("");
	}
		

	

	
	private void alertBox(String message) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setIcon(R.drawable.alert)
	      .setTitle(message)
	      .setCancelable(true).setNeutralButton(android.R.string.ok,
	         new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton){}
	      }).show();
	}
	      
	


}