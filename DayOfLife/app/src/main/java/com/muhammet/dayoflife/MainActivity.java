package com.muhammet.dayoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText dateDayInput, dateMonthInput, dateYearInput;
    Button calculateButton;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignVariablesToUI();
    }

    private void assignVariablesToUI() {
        dateDayInput    = findViewById(R.id.dateDayInput);
        dateMonthInput  = findViewById(R.id.dateMonthInput);
        dateYearInput   = findViewById(R.id.dateYearInput);
        calculateButton = findViewById(R.id.calculateButton);
        output          = findViewById(R.id.output);
    }

    public void onCalculateButtonClicked(View view) {
        int day          = Integer.parseInt(dateDayInput.getText().toString());
        int month        = Integer.parseInt(dateMonthInput.getText().toString());
        int year         = Integer.parseInt(dateYearInput.getText().toString());
        int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay   = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int totalDay = 0;

        for(; year < currentYear; year++) {
            if ( year % 4 == 0 )
                totalDay += 366;
            else
                totalDay += 365;
        }

        Integer[] bigMonths   = { 1, 3, 5, 7, 8, 10, 12 };
        Integer[] shortMonths = { 4, 6, 9, 11 };


        for(; month < currentMonth; month++) {
            if ( isInclude(bigMonths, month) )
                totalDay += 31;
            else if ( isInclude(shortMonths, month) )
                totalDay += 30;
            else
                if ( year % 4 == 0 )
                    totalDay += 29;
                else
                    totalDay += 28;
        }

        totalDay += currentDay;

        output.setText("Toplam Yaşadığınız Gün Sayısı: " + Integer.toString(totalDay));
    }

    private boolean isInclude(Integer[] array, int value) {
        Integer counter;

        for (counter = 0; counter < array.length; counter++) {
            if ( array[counter] == value )
                return true;
        }

        return false;
    }
}
