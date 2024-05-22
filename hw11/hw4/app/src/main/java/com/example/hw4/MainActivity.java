package com.example.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText numberEditText;

    private static final int REQUEST_CODE_OPERATION = 100;
    private static final int REQUEST_CODE_EQUATION = 200;
    private static final int REQUEST_CODE_FUNCTION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberEditText = findViewById(R.id.numberEditText);
        Button operationButton = findViewById(R.id.operationButton);
        Button equationButton = findViewById(R.id.equationButton);
        Button functionButton = findViewById(R.id.functionButton);

        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchOperationActivity();
            }
        });

        equationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEquationActivity();
            }
        });

        functionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFunctionActivity();
            }
        });
    }

    private void launchOperationActivity() {
        if (validateInput()) {
            double number = Double.parseDouble(numberEditText.getText().toString());
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("number1", number);
            startActivityForResult(intent, REQUEST_CODE_OPERATION);
        }
    }

    private void launchEquationActivity() {
        if (validateInput()) {
            double number = Double.parseDouble(numberEditText.getText().toString());
            Intent intent = new Intent(MainActivity.this, MainActivity4.class);
            intent.putExtra("number1", number);
            startActivityForResult(intent, REQUEST_CODE_EQUATION);
        }
    }

    private void launchFunctionActivity() {
        if (validateInput()) {
            double number = Double.parseDouble(numberEditText.getText().toString());
            Intent intent = new Intent(MainActivity.this, MainActivity5.class);
            intent.putExtra("number1", number);
            startActivityForResult(intent, REQUEST_CODE_FUNCTION);
        }
    }

    private boolean validateInput() {
        if (numberEditText.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            double result = data.getDoubleExtra("result", 0);
            numberEditText.setText(String.valueOf(result));
        }
    }
}
