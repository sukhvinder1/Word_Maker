package com.example.wordmaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
//import android.telephony.SmsManager;
//import android.telephony.gsm.*;
//import android.text.Editable;
//import android.text.TextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main_Page extends Activity {

    SeekBar seekBar;
    TextView seekBarText;
    EditText inputText;
    String line = null, dictinaryLines = "", dictStringLettes = "";
    String inputWord = "", output = "Sukh";
    String[] dictLinesArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        inputText = (EditText) findViewById(R.id.inputText);
        seekBarText = (TextView) findViewById(R.id.seekbarLabel);
        TextView wordLabelText = (TextView) findViewById(R.id.wordsLable);
        TextView seekLabel2 = (TextView) findViewById(R.id.seekLabel);

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                for (int i = s.length(); i > 0; i--) {
                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");

                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {


            }

        });

        String fontPath = "fonts/Annifont.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        String fontPath2 = "fonts/Second Grader.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);

        wordLabelText.setTypeface(tf);
        seekLabel2.setTypeface(tf);
        seekBarText.setTypeface(tf2);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int finalprogress = (progress / 100) + 1;
                seekBarText.setText("" + finalprogress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        loadingDictionary();
    }

    private void loadingDictionary() {

        AssetManager mgr;

        try {
            mgr = getAssets();
            InputStream is = mgr.open("dictinary.txt");


            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String ls = ",";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            dictinaryLines = stringBuilder.toString();

            dictLinesArray = dictinaryLines.split(ls);
        } catch (IOException e1) {

        }

    }

    public void SearchWord(View v) throws IOException {

        inputWord = inputText.getText().toString();
        int length = Integer.parseInt(seekBarText.getText().toString());

        if (inputWord.length() == 0) {

            new AlertDialog.Builder(this).setIcon(R.drawable.alert)
                    .setTitle("Please entre some text to proceed")
                    .setCancelable(true).setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    }).show();
        } else if (inputWord.length() > 8) {
            new AlertDialog.Builder(this)
                    .setMessage("Sorry, right now we are supporting upto 8 letters").setIcon(R.drawable.alert).setCancelable(true).
                    setNeutralButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).show();
        } else {
            String finalWord = inputWord.toUpperCase();


            switch (length) {
                case 1:
                    for1Letter();
                    break;
                case 2:
                    for2to3LettesOutput(finalWord, length, v);
                    break;
                case 3:
                    for2to3LettesOutput(finalWord, length, v);
                    break;
                case 4:
                    for2to3LettesOutput(finalWord, length, v);
                    break;
                case 5:
                    for2to3LettesOutput(finalWord, length, v);
                    break;
                case 6:
                    for2to3LettesOutput(finalWord, length, v);
                    break;
                default:
                    break;
            }


        }

    }

    private void for1Letter() {

        new AlertDialog.Builder(this)
                .setMessage("Sorry, Please select length more then 1 !").setIcon(R.drawable.alert)
                .setTitle("Error")
                .setCancelable(true).setNeutralButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    private void for2to3LettesOutput(String finalWord, int length, View v) throws IOException {


        StringBuilder letterStringBuilder2 = new StringBuilder();
        StringBuilder letterStringBuilder3 = new StringBuilder();
        StringBuilder letterStringBuilder4 = new StringBuilder();
        StringBuilder letterStringBuilder5 = new StringBuilder();
        StringBuilder letterStringBuilder6 = new StringBuilder();
        int wordLen = finalWord.length();
        String[] array = finalWord.split("");
        for (int i = 1; i <= wordLen; i++)
            for (int j = 1; j <= wordLen; j++) {
                letterStringBuilder2.append(array[i]).append(array[j]).append(",");
                if (length >= 3)
                    for (int K = 1; K <= wordLen; K++) {
                        letterStringBuilder3.append(array[i]).append(array[j]).
                                append(array[K]).append(",");
                        if (length >= 4) {
                            for (int l = 1; l <= wordLen; l++) {
                                letterStringBuilder4.append(array[i]).append(array[j]).
                                        append(array[K]).append(array[l]).append(",");
                                if (length >= 5) {
                                    for (int m = 1; m <= wordLen; m++) {
                                        letterStringBuilder5.append(array[i]).append(array[j]).append(array[K]).append(array[l]).append(array[m]).append(",");
                                        if (length >= 6) {
                                            for (int n = 1; n <= wordLen; n++)
                                                letterStringBuilder6.append(array[i]).append(array[j]).append(array[K]).append(array[l]).append(array[m]).append(array[n]).append(",");
                                        }
                                    }
                                }
                            }
                        }
                    }
            }


        String[] arrayLetters;
        if (length == 2) {
            arrayLetters = letterStringBuilder2.toString().split(",");
        } else if (length == 3) {
            arrayLetters = letterStringBuilder3.toString().split(",");
        } else if (length == 4) {
            arrayLetters = letterStringBuilder4.toString().split(",");
        } else if (length == 5) {
            arrayLetters = letterStringBuilder5.toString().split(",");
        } else {
            arrayLetters = letterStringBuilder6.toString().split(",");
        }


        searching(arrayLetters, v);
    }

    private void searching(String[] arrayLetters, View v) {
        int a = arrayLetters.length;
        Set<String> dictLinesSet = new HashSet<String>(Arrays.asList(dictLinesArray));
        for (int i = 0; i < a; i++) {
            if (dictLinesSet.contains(arrayLetters[i])) {
                dictStringLettes += arrayLetters[i] + ",";
            }
        }
        output = dictStringLettes;
        inputWord = "";
        dictStringLettes = "";

        Intent myIntent = new Intent(v.getContext(), Page2.class);
        myIntent.putExtra("sukh", output);
        startActivityForResult(myIntent, 0);
    }

    public void recommend(View v) {
        Intent myIntent = new Intent(v.getContext(), recommendation_page.class);
        startActivityForResult(myIntent, 0);
    }

    public void onBtnClick(View v) {
        Intent myIntent = new Intent(v.getContext(), savedWord_page.class);
        startActivityForResult(myIntent, 0);
    }

    public void clearBtn(View v) {

        seekBarText.setText("1");
        inputText.setText("");
        seekBar.setProgress(0);
    }

    public void helpBtn(View v) {

        Intent myIntent = new Intent(v.getContext(), helpView_page.class);
        startActivityForResult(myIntent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main__page, menu);
        return true;
    }


}