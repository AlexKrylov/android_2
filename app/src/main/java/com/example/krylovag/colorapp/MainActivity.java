package com.example.krylovag.colorapp;

import android.content.Context;
import android.content.Intent;
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
    private Intent intent;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COLOR = "Color";
    private SharedPreferences mSettings;
    private SharedPreferences.Editor prefEditor;
    int saved_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textview_description);
        Button button = (Button) findViewById(R.id.button_show_description);
        button.setOnClickListener(onClickListener);
        Button send = findViewById(R.id.share_button);
        send.setOnClickListener(shareWithAFriend);

        spinner = (Spinner) findViewById(R.id.spinner_for_color);
        if (mSettings.contains(APP_PREFERENCES_COLOR)) {
            saved_color = mSettings.getInt(APP_PREFERENCES_COLOR, 0);
        } else {
            saved_color = 0;
        }
        spinner.setSelection(saved_color);
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
        int save_color = spinner.getSelectedItemPosition();
        prefEditor = mSettings.edit();
        prefEditor.putInt(APP_PREFERENCES_COLOR, save_color);
        prefEditor.apply();
    }
}
