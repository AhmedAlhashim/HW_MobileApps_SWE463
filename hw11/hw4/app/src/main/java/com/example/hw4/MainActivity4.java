package com.example.hw4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw4.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {

    private ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double number = getIntent().getDoubleExtra("number1", 0);

        binding.squareButton.setOnClickListener(view -> returnResult(Math.pow(number, 2)));
        binding.squareRootButton.setOnClickListener(view -> returnResult(Math.sqrt(number)));
        binding.cubeButton.setOnClickListener(view -> returnResult(Math.pow(number, 3)));
    }

    private void returnResult(double result) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", result);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
