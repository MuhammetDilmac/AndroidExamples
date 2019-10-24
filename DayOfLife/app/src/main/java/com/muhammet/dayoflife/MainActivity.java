package com.muhammet.dayoflife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText dateDayInput,
             dateMonthInput,
             dateYearInput;

    Button calculateButton;

    TextView output;

    int[] bigMonths   = { 1, 3, 5, 7, 8, 10, 12 },
          shortMonths = { 4, 6, 9, 11 };

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
        int daysCount  = howManyDaysLived();
        String message = String.format(getString(R.string.livedDaysCount), daysCount);


        output.setText(message);
    }

    private boolean isInclude(int[] array, int value) {
        for ( int item : array )
            if ( item == value )
                return true;

        return false;
    }

    private int howManyDaysLived() {
        Calendar calendar = Calendar.getInstance();

        int birthDay     = Integer.parseInt(dateDayInput.getText().toString()),
            birthMonth   = Integer.parseInt(dateMonthInput.getText().toString()),
            birthYear    = Integer.parseInt(dateYearInput.getText().toString()),
            currentYear  = calendar.get(Calendar.YEAR),
            currentMonth = calendar.get(Calendar.MONTH) + 1,
            currentDay   = calendar.get(Calendar.DAY_OF_MONTH),
            totalDay     = 0,
            temporaryCounter;

        totalDay -= birthDay;

        for ( temporaryCounter = 1; temporaryCounter < birthMonth; temporaryCounter++ )
            totalDay -= howManyDaysHaveMonth(temporaryCounter, birthYear);

        for ( temporaryCounter = birthYear; temporaryCounter < currentYear; temporaryCounter++ )
            if ( temporaryCounter % 4 == 0 )
                totalDay += 366;
            else
                totalDay += 365;

        for ( temporaryCounter = 1; temporaryCounter < currentMonth; temporaryCounter++ )
            totalDay += howManyDaysHaveMonth(temporaryCounter, currentYear);

        totalDay += currentDay;

        return totalDay;
    }

    private int howManyDaysHaveMonth(int month, int year) {
        if ( isInclude(bigMonths, month) )   return 31;
        if ( isInclude(shortMonths, month) ) return 30;
        if ( year % 4 == 0 )                 return 29;

        return 28;
    }
}
