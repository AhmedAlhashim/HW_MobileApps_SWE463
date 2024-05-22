package com.example.hw4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hw4.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {

    private ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double number1 = getIntent().getDoubleExtra("number1", 0);
        char operation = getIntent().getCharExtra("operation", '+');

        binding.validateButton.setOnClickListener(view -> {
            double number2;
            try {
                number2 = Double.parseDouble(binding.number2EditText.getText().toString());
            } catch (NumberFormatException e) {
                number2 = 0;
            }
            double result = performOperation(number1, number2, operation);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", result);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private double performOperation(double number1, double number2, char operation) {
        switch (operation) {
            case '+':
                return number1 + number2;
            case '-':
                return number1 - number2;
            case '*':
                return number1 * number2;
            case '/':
                return number2 != 0 ? number1 / number2 : Double.POSITIVE_INFINITY; // Avoid division by zero
            default:
                return 0;
        }
    }
}
