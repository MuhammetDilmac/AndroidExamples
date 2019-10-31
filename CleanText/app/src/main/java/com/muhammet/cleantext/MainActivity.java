package com.muhammet.cleantext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final Integer GOTO_INPUT_ACTIVITY_EVENT = 1001;

    Button gotoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoInput = findViewById(R.id.gotoInput);
    }

    public void onClickGotoInput(View view) {
        Intent inputActivity = new Intent(this, InputActivity.class);
        startActivityForResult(inputActivity, GOTO_INPUT_ACTIVITY_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == GOTO_INPUT_ACTIVITY_EVENT ) {
            if ( resultCode == Activity.RESULT_OK ) {
                String cleanText = data.getStringExtra("cleanText");
                String cleanTextMessage = String.format(getString(R.string.toast_clean_text), cleanText);
                Toast.makeText(getApplicationContext(), cleanTextMessage, Toast.LENGTH_LONG).show();

                int invalidSize = data.getIntExtra("invalidSize", 0);
                String invalidSizeMessage = String.format(getString(R.string.toast_invalid_length), invalidSize);
                Toast.makeText(getApplicationContext(), invalidSizeMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}
