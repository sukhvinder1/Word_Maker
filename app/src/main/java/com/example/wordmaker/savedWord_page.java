package com.example.wordmaker;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class savedWord_page extends Activity implements OnInitListener {
    TextView heading;
    ListView listView;
    String[] array, rowId;
    String selectedword, lRowId;
    Button speechBtn, searchBtn, deleteBtn;
    TextToSpeech talker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedwords);

        talker = new TextToSpeech(this, this);
        talker.setSpeechRate((float) 0.6);
        heading = (TextView) findViewById(R.id.wordsLable);
        speechBtn = (Button) findViewById(R.id.speech);
        searchBtn = (Button) findViewById(R.id.Find);
        deleteBtn = (Button) findViewById(R.id.DeleteorSave);


        DatabaseHelper info = new DatabaseHelper(this);
        info.open();

        Cursor c = info.getData();

        String result = "", row = "";

        int iROW = c.getColumnIndex("_id");
        int iNAME = c.getColumnIndex("word");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result += c.getString(iNAME) + ",";
            row += c.getString(iROW) + ",";
        }
        info.close();

        array = result.split(",");
        rowId = row.split(",");

        String fontPath = "fonts/Calligrapher Regular.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        listView = (ListView) findViewById(R.id.mySavedlist);
        heading = (TextView) findViewById(R.id.text1);

        List<String> wordList = Arrays.asList(array);

        if (array[0].isEmpty()) {
            heading.setText("Nothing Saved");
        } else {
            heading.setText("You have " + wordList.size() + " saved words");
        }


        //Log.w("length", array.length);
        heading.setTypeface(tf);

        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, array);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(myarrayAdapter);

        listView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        selectedword = array[position];
                        lRowId = rowId[position];
                        speechBtn.setClickable(true);
                        searchBtn.setClickable(true);
                        deleteBtn.setClickable(true);

                    }
                }

        );
    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.speech:
                talker.speak(selectedword, TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.DeleteorSave:
                try {
                    DatabaseHelper entry = new DatabaseHelper(savedWord_page.this);
                    entry.open();
                    long lRow = Long.parseLong(lRowId);
                    entry.deleteWord(lRow);
                    entry.close();
                } catch (Exception e) {
                    String error = e.toString();
                    Toast.makeText(savedWord_page.this, error, Toast.LENGTH_LONG).show();
                } finally {
                    finish();
                    startActivity(getIntent());
                }
                break;
            case R.id.Find:
                Intent webSearch = new Intent(v.getContext(), WebSearch.class);
                Log.d("word", "selected word At page 2- " + selectedword);
                webSearch.putExtra("word", selectedword);
                startActivity(webSearch);
                break;
        }
    }

    public void homeBtn(View v) {
        Intent myIntent = new Intent(v.getContext(), Main_Page.class);
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void onInit(int status) {

    }

    public void onDestroy() {
        if (talker != null) {
            talker.stop();
            talker.shutdown();
        }
        super.onDestroy();

    }


}
