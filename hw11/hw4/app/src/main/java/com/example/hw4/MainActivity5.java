package com.example.hw4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw4.databinding.ActivityMain5Binding;

public class MainActivity5 extends AppCompatActivity {

    private ActivityMain5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double number = getIntent().getDoubleExtra("number1", 0);

        binding.logButton.setOnClickListener(view -> returnResult(Math.log10(number)));
        binding.expButton.setOnClickListener(view -> returnResult(Math.exp(number)));
        binding.cosButton.setOnClickListener(view -> returnResult(Math.cos(Math.toRadians(number))));
    }

    private void returnResult(double result) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", result);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
