package com.example.wordmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Page2 extends Activity implements OnInitListener {
    String[] output, outputFinal, dsf;
    ListView lv;
    String selectedword = "";
    Button speechBtn, searchBtn, saveBtn, deleteBtn;
    TextToSpeech talker;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        talker = new TextToSpeech(this, this);
        text = (TextView) findViewById(R.id.text1);

        talker.setSpeechRate((float) 0.6);


        speechBtn = (Button) findViewById(R.id.speech);
        searchBtn = (Button) findViewById(R.id.Find);
        saveBtn = (Button) findViewById(R.id.DeleteorSave);
        //deleteBtn = (Button) findViewById(R.id.);

        Intent mI = getIntent();
        String search = mI.getStringExtra("sukh");

        output = search.split(",");
        List<String> wordList = Arrays.asList(output);
        Set myset = new HashSet(wordList);
        List<String> list = new ArrayList<String>(myset);

        dsf = new String[list.size()];
        list.toArray(dsf);

        String fontPath = "fonts/Calligrapher Regular.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Log.d("findMe* - ", output.length + "");
        Log.d("findMe* - ", "Hello " + output[0]);
        if (output[0].isEmpty()) {
            text.setText("Nothing Found");
            Log.d("findMe* - ", "2-" + output.length + "");
        } else {
            text.setText(list.size() + " words found");
            Log.d("findMe* - ", output.length + "");
        }


        search.replaceAll("\\s", "");
        if (search.equalsIgnoreCase(null)) {
            text.setText("no space");
        }

        text.setTypeface(tf);
        sortStringBubble(dsf);

        lv = (ListView) findViewById(R.id.mylist);
        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, dsf);

        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setAdapter(myarrayAdapter);

        lv.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        selectedword = dsf[position];
                        speechBtn.setClickable(true);
                        searchBtn.setClickable(true);
                        saveBtn.setClickable(true);

                    }
                }

        );

    }

    private void sortStringBubble(String[] x) {

        int j;
        boolean flag = true;  // will determine when the sort is finished
        String temp;

        while (flag) {
            flag = false;
            for (j = 0; j < x.length - 1; j++) {
                if (x[j].compareToIgnoreCase(x[j + 1]) > 0) {                                             // ascending sort
                    temp = x[j];
                    x[j] = x[j + 1];     // swapping
                    x[j + 1] = temp;
                    flag = true;
                }
            }
        }

    }

    public void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.speech:
                talker.speak(selectedword, TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.DeleteorSave:
                boolean didItWork = true;
                try {
                    DatabaseHelper entry = new DatabaseHelper(Page2.this);
                    entry.open();
                    entry.saveEntry(selectedword);
                    entry.close();
                } catch (Exception e) {
                    didItWork = false;
                    String error = e.toString();
                    Toast.makeText(Page2.this, error, Toast.LENGTH_LONG).show();
                } finally {
                    didItWork = true;
                    Toast.makeText(Page2.this, selectedword + " has been saved.", Toast.LENGTH_SHORT).show();
                    saveBtn.setClickable(false);
                }
                break;
            case R.id.Find:
                Intent webSearch = new Intent(v.getContext(), WebSearch.class);
                Log.d("word", "selected word At page 2 - " + selectedword);
                webSearch.putExtra("word", selectedword);
                startActivity(webSearch);
                break;

            case R.id.button1:
                Intent myIntent = new Intent(v.getContext(), Main_Page.class);
                startActivityForResult(myIntent, 0);
                break;
        }
    }


    @Override
    public void onInit(int status) {

        //say("Hello World");

    }

    public void onDestroy() {
        if (talker != null) {
            talker.stop();
            talker.shutdown();
        }
        super.onDestroy();

    }

}
