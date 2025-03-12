package com.example.memoapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setRadioButtonColor();
        listImageButton();
        showDatePickerDialog();

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

    @Override
    public void saveDate(Calendar selectedDate) {
        TextView textChangedDate = findViewById(R.id.textChangedDate);
        textChangedDate.setText(DateFormat.format("MM/dd/yyyy", selectedDate.getTime()));
    }

    public void showDatePickerDialog() {
        Button dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }
}
