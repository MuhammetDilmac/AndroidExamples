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

    Integer[] bigMonths   = { 1, 3, 5, 7, 8, 10, 12 };
    Integer[] shortMonths = { 4, 6, 9, 11 };

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
        output          = findViewById(R.id.output  );
    }

    public void onCalculateButtonClicked(View view) {
        int day          = Integer.parseInt(dateDayInput.getText().toString());
        int month        = Integer.parseInt(dateMonthInput.getText().toString());
        int year         = Integer.parseInt(dateYearInput.getText().toString());
        int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDay   = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int totalDay = 0;

        for(; year < currentYear; year++){
            if ( year % 4 == 0 )
                totalDay += 366;
            else
                totalDay += 365;
        }

        if ( isInclude(bigMonths, month) ) {
            totalDay += currentDay + (31 - day);
        }
        else if ( isInclude(shortMonths, month) ) {
            totalDay += currentDay + (30 - day);
        }
        else {
            if ( year % 4 == 0 ) {
                totalDay += currentDay + (29 - day);
            }
            else
                totalDay += currentDay + (28 - day);
        }

        if(month < currentMonth){
            totalDay = monthCalculate(month,currentMonth,totalDay,year,1);
        }
        else{
            totalDay = monthCalculate(currentMonth,month,totalDay,year,-1);
        }

        output.setText("Toplam Yaşadığınız Gün Sayısı: " + Integer.toString(totalDay));
    }

    private int monthCalculate(int A_Month,int B_Month,int totalDay,int year,int bigOrShortControl){

        for(; A_Month < B_Month; A_Month++){
            if ( isInclude(bigMonths, A_Month) ) {
                totalDay += 31*bigOrShortControl;
            }
            else if ( isInclude(shortMonths, A_Month) ) {
                totalDay += 30*bigOrShortControl;
            }
            else
            if ( year % 4 == 0 ) {
                totalDay += 29*bigOrShortControl;
            }
            else
                totalDay += 28*bigOrShortControl;
        }
        return totalDay;
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
