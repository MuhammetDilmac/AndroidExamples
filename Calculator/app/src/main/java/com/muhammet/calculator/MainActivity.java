package com.muhammet.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    String lastAction = "";
    Double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void numberButtonClicked(View clickedButton) {
        if (lastAction.equals("=")) {
            editText.setText("");
            lastAction = "";
        }

        String clicked = clickedNumber(clickedButton);

        editText.append(clicked);
    }

    public void actionButtonClicked(View clickedAction) {
        Double screenNumber;
        Button clickedButton = (Button)clickedAction;
        String action = clickedButton.getText().toString();

        try {
            String content = editText.getText().toString();
            screenNumber   = Double.parseDouble(content);
        } catch ( NumberFormatException e ) {
            screenNumber = 0.0;
        }

        if ( action.equals("=") ) {
            calculate(lastAction, screenNumber);
            String equalsResult = Double.toString(result);
            editText.setText(equalsResult);
        } else if ( action.equals("C") ) {
            result = 0.0;
        }
        else {
            if ( !lastAction.equals("=") )
                calculate(action, screenNumber);
        }

        lastAction = action;

        if ( !action.equals("=") )
            editText.setText("");
    }

    private String clickedNumber(View clickedButton) {
        switch ( clickedButton.getId() ) {
            case R.id.buttonForOne:
                return "1";

            case R.id.buttonForTwo:
                return "2";

            case R.id.buttonForThree:
                return "3";

            case R.id.buttonForFour:
                return "4";

            case R.id.buttonForFive:
                return "5";

            case R.id.buttonForSix:
                return "6";

            case R.id.buttonForSeven:
                return "7";

            case R.id.buttonForEight:
                return "8";

            case R.id.buttonForNine:
                return "9";

            case R.id.buttonForZero:
                return "0";

            case R.id.buttonForPoint:
                return ".";
        }

        return null;
    }

    private void calculate(String action, Double screenNumber) {
        switch(action) {
            case "+":
                result = result + screenNumber;
                break;

            case "-":
                result = result - screenNumber;
                break;

            case "*":
                result = result * screenNumber;
                break;

            case "/":
                result = result / screenNumber;
                break;
        }
    }
}
