package com.muhammet.downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText rfcNumberView;
    private TextView rfcContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rfcNumberView = findViewById(R.id.rfcNumber);
        rfcContent    = findViewById(R.id.rfcContent);

        rfcContent.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onFetchButtonClicked(View view) {
        String rfcNumber = rfcNumberView.getText().toString();

        new FetchRFCTask(this).execute(rfcNumber);
    }
}
