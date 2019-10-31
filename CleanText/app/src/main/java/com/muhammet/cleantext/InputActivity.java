package com.muhammet.cleantext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputActivity extends AppCompatActivity {

    Button clearText;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        clearText = findViewById(R.id.clearText);
        inputText = findViewById(R.id.inputText);
    }

    public void onClickClearText(View view) {
        String cleanText = "";
        String input     = inputText.getText().toString();
        Pattern pattern  = Pattern.compile("[A-Za-zİıĞğÜüŞşÖöÇç]");
        Matcher matcher  = pattern.matcher(input);

        int totalInputSize = input.length();

        while( matcher.find() )
            cleanText = cleanText.concat(matcher.group(0));

        int cleanTextSize = cleanText.length();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("cleanText", cleanText);
        resultIntent.putExtra("invalidSize", totalInputSize - cleanTextSize);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
