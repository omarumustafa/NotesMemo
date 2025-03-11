package com.example.memoapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRadioButtonColor();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setRadioButtonColor() {
        RadioButton radioButton = findViewById(R.id.radioButtonLow);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

        radioButton = findViewById(R.id.radioButtonMedium);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

        radioButton = findViewById(R.id.radioButtonHigh);
        radioButton.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
    }
}