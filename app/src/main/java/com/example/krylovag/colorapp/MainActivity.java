package com.example.krylovag.colorapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Spinner spinner;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COLOR = "Color";
    private SharedPreferences mSettings;
    private SharedPreferences.Editor prefEditor;
    int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textview_description);
        Button button = (Button) findViewById(R.id.button_show_description);
        button.setOnClickListener(onClickListener);

        spinner = (Spinner) findViewById(R.id.spinner_for_color);
        if (mSettings.contains(APP_PREFERENCES_COLOR)) {
            c = mSettings.getInt(APP_PREFERENCES_COLOR, 0);
        } else {
            c = 0;
        }
        spinner.setSelection(c);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.button_show_description) {
                String result = ColorSpec.getEffect(MainActivity.this, spinner.getSelectedItemPosition());
                textView.setText(result);
                if (spinner.getSelectedItemPosition() == 0) {
                    textView.setBackgroundResource(R.color.red);
                } else if (spinner.getSelectedItemPosition() == 1) {
                    textView.setBackgroundResource(R.color.orange);
                } else if (spinner.getSelectedItemPosition() == 2) {
                    textView.setBackgroundResource(R.color.yellow);
                } else if (spinner.getSelectedItemPosition() == 3) {
                    textView.setBackgroundResource(R.color.green);
                } else if (spinner.getSelectedItemPosition() == 4) {
                    textView.setBackgroundResource(R.color.light_blue);
                } else if (spinner.getSelectedItemPosition() == 5) {
                    textView.setBackgroundResource(R.color.blue);
                } else if (spinner.getSelectedItemPosition() == 6) {
                    textView.setBackgroundResource(R.color.purple);
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        int save_color = spinner.getSelectedItemPosition();
        prefEditor = mSettings.edit();
        prefEditor.putInt(APP_PREFERENCES_COLOR, save_color);
        prefEditor.apply();
    }
}
