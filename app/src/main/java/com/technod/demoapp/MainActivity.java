package com.technod.demoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private RecyclerView col1;
    private RecyclerView col2;
    private Button generate;
    private EditText inputField;

    private List<Integer> inputItems = new ArrayList<Integer>();
    private List<Integer> randomItems = new ArrayList<Integer>();

    private ButtonAdapter column1Adapter;
    private ButtonAdapter column2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initialize();
    }

    private void initialize() {
        col1 = findViewById(R.id.rcv_ordered);
        col2 = findViewById(R.id.rcv_random);
        inputField = findViewById(R.id.edtCount);
        generate = findViewById(R.id.btn_generate);

        col1.setLayoutManager(new LinearLayoutManager(this));
        col2.setLayoutManager(new LinearLayoutManager(this));

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGenerateClick();
            }
        });
    }

    private void onGenerateClick() {
        if (inputField.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please input value between 0 to 50!", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt((inputField.getText().toString())) > 50) {
            Toast.makeText(MainActivity.this, "Please input value between 0 to 50!", Toast.LENGTH_SHORT).show();
        } else {
            setAdapters();
            generate.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.grey)));
            generate.setEnabled(false);
        }
    }

    private void setAdapters() {
        for (int i = 0; i < Integer.parseInt((inputField.getText().toString())); i++) {
            inputItems.add(i + 1);
        }
        column1Adapter = new ButtonAdapter(inputItems, MainActivity.this, new ButtonClickListener() {
            @Override
            public void onButtonClick(int randomList) {
                randomItems.add(randomList);
                column2Adapter = new ButtonAdapter(randomItems, MainActivity.this, null, false, null);
                col2.setAdapter(column2Adapter);
                col2.scrollToPosition(randomItems.size()-1);
            }
        }, true, randomItems);
        col1.setAdapter(column1Adapter);
    }
}