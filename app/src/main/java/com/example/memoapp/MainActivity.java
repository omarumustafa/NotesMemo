package com.example.memoapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRadioButtonColor();
        listImageButton();
        autoFillDate();

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

    private void listImageButton() {
        ImageButton ibList = findViewById(R.id.listImageButton);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoList.class);
                startActivity(intent);
            }
        });
    }

    private void autoFillDate() {
        EditText editDate = findViewById(R.id.editDate);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());

        editDate.setText(date);
    }
}


