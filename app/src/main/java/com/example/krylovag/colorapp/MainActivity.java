package com.example.krylovag.colorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";
    private TextView textView;
    private Spinner spinner;
    private Intent intent;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_POSITION = "Position";
    public static final String APP_PREFERENCES_DESCRIPTION = "Description";
    private SharedPreferences mSettings;
    private SharedPreferences.Editor prefEditor;
    int saved_description;
    String saved_text;
    int saved_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        textView = findViewById(R.id.textview_description);
        Button button = findViewById(R.id.button_show_description);
        button.setOnClickListener(onClickListener);
        Button send = findViewById(R.id.share_button);
        send.setOnClickListener(shareWithAFriend);

        spinner = findViewById(R.id.spinner_for_color);
        if (mSettings.contains(APP_PREFERENCES_POSITION)) {
            saved_position = mSettings.getInt(APP_PREFERENCES_POSITION, 0);
        } else {
            saved_position = 0;
        }
        spinner.setSelection(saved_position);

        if(mSettings.contains(APP_PREFERENCES_DESCRIPTION)) {
            saved_text = mSettings.getString(APP_PREFERENCES_DESCRIPTION, null);
        }
        textView.setText(saved_text);

        Log.i(TAG, "savedInstanceState " + savedInstanceState);
        if (savedInstanceState != null) {
            saved_position = savedInstanceState.getInt(APP_PREFERENCES_POSITION);
        }
     }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_show_description) {
                String result = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());
                textView.setText(result);
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        textView.setBackgroundResource(R.color.red);
                        break;
                    case 1:
                        textView.setBackgroundResource(R.color.orange);
                        break;
                    case 2:
                        textView.setBackgroundResource(R.color.yellow);
                        break;
                    case 3:
                        textView.setBackgroundResource(R.color.green);
                        break;
                    case 4:
                        textView.setBackgroundResource(R.color.light_blue);
                        break;
                    case 5:
                        textView.setBackgroundResource(R.color.blue);
                        break;
                    case 6:
                        textView.setBackgroundResource(R.color.purple);
                        break;
                }
            }
        }
    };

    private final View.OnClickListener shareWithAFriend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.share_button) {
                String messageText = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, messageText);
                String chooserTitle = getString(R.string.choose_title);
                Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
                startActivity(chosenIntent);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        int save_description = spinner.getSelectedItemPosition();
        prefEditor = mSettings.edit();
        prefEditor.putInt(APP_PREFERENCES_POSITION, save_description);
        String save_desc = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());
        prefEditor.putString(APP_PREFERENCES_DESCRIPTION, save_desc);
        prefEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(APP_PREFERENCES_POSITION, saved_position);
    }
}
