package com.muhammet.slider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView sliderImage;
    ImageButton arrowLeftButton, arrowRightButton;
    TextView cityArea, plateArea, descriptionArea;
    Resources resources;
    TypedArray cities;
    Integer cityIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accessToResources();
        assignVariablesToUI();
        readArrayToVariable();
        setArrowStatus();
        setCity(0);
    }

    private void assignVariablesToUI() {
        cityArea         = findViewById(R.id.cityArea);
        plateArea        = findViewById(R.id.plateArea);
        descriptionArea  = findViewById(R.id.descriptionArea);
        arrowLeftButton  = findViewById(R.id.arrowLeftButton);
        arrowRightButton = findViewById(R.id.arrowRightButton);
        sliderImage      = findViewById(R.id.sliderImage);
    }

    private void accessToResources() {
        resources = getResources();
    }

    private void readArrayToVariable() {
        cities = resources.obtainTypedArray(R.array.cities);
    }

    private void setArrowStatus() {
        if ( cityIndex == 0 ) {
            if ( arrowRightButton.getVisibility() == View.INVISIBLE )
                arrowRightButton.setVisibility(View.VISIBLE);

            if ( arrowLeftButton.getVisibility() == View.VISIBLE )
                arrowLeftButton.setVisibility(View.INVISIBLE);
        }
        else if ( cityIndex == cities.length() - 1 ) {
            if ( arrowLeftButton.getVisibility() == View.INVISIBLE )
                arrowLeftButton.setVisibility(View.VISIBLE);

            if ( arrowRightButton.getVisibility() == View.VISIBLE )
                arrowRightButton.setVisibility(View.INVISIBLE);
        }
        else {
            if ( arrowLeftButton.getVisibility() == View.INVISIBLE )
                arrowLeftButton.setVisibility(View.VISIBLE);

            if ( arrowRightButton.getVisibility() == View.INVISIBLE )
                arrowRightButton.setVisibility(View.VISIBLE);
        }
    }

    private void setCity(Integer index) {
        if ( index >= cities.length() )
            index = 0;

        Integer resourceId = cities.getResourceId(index, -1);
        if ( resourceId < 0 )
            return;

        TypedArray city = resources.obtainTypedArray(resourceId);

        cityArea.setText(city.getString(0));
        plateArea.setText(city.getString(1));
        descriptionArea.setText(city.getString(2));
        sliderImage.setImageDrawable(city.getDrawable(3));

        cityIndex = index;
        setArrowStatus();
    }

    public void arrowRightButtonClicked (View button) {
        if ( cityIndex >= cities.length() - 1 )
            return;

        Integer index = (cityIndex + 1) % (cities.length());
        setCity(index);
    }

    public void arrowLeftButtonClicked (View button) {
        if ( cityIndex == 0 )
            return;

        Integer index = (cityIndex - 1) % (cities.length());
        setCity(index);
    }
}
