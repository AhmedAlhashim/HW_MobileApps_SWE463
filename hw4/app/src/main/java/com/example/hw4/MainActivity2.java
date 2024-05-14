package com.example.hw4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw4.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double number1 = getIntent().getDoubleExtra("number1", 0);

        binding.addButton.setOnClickListener(v -> launchMainActivity3(number1, '+'));
        binding.subtractButton.setOnClickListener(v -> launchMainActivity3(number1, '-'));
        binding.multiplyButton.setOnClickListener(v -> launchMainActivity3(number1, '*'));
        binding.divideButton.setOnClickListener(v -> launchMainActivity3(number1, '/'));
    }

    private void launchMainActivity3(double number1, char operation) {
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("number1", number1);
        intent.putExtra("operation", operation);
        startActivityForResult(intent, operation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            double result = data.getDoubleExtra("result", 0);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", result);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }
}
