package sa.kfupm.hw10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private EditText inputNumber, inputX, inputY;
    private TextView resultView;
    private Spinner operationSpinner;
    private Button tripleButton, calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        inputNumber = findViewById(R.id.inputNumber);
        inputX = findViewById(R.id.inputX);
        inputY = findViewById(R.id.inputY);
        resultView = findViewById(R.id.resultView);
        operationSpinner = findViewById(R.id.operationSpinner);
        tripleButton = findViewById(R.id.tripleButton);
        calculateButton = findViewById(R.id.calculateButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationSpinner.setAdapter(adapter);

        tripleButton.setOnClickListener(v -> {
            int number = Integer.parseInt(inputNumber.getText().toString());
            new Thread(() -> {
                final String result = fetchTriple(number);
                runOnUiThread(() -> resultView.setText(result));
            }).start();
        });

        calculateButton.setOnClickListener(v -> {
            int x = Integer.parseInt(inputX.getText().toString());
            int y = Integer.parseInt(inputY.getText().toString());
            int opIndex = operationSpinner.getSelectedItemPosition();
            new OperationTask(x, y, opIndex).execute();
        });
    }

    private String fetchTriple(int num) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://bounceur.com/prg/triple.php?x=" + num);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode(); // Get the response code
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return "Failed to connect: HTTP error code : " + responseCode;
            }
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
            in.close(); // Close InputStream after use
            return result;
        } catch (Exception e) {
            return "Error: " + e.toString();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private class OperationTask extends AsyncTask<Void, Void, String> {
        private int x, y;
        private String operation;

        OperationTask(int x, int y, int opIndex) {
            this.x = x;
            this.y = y;
            this.operation = String.valueOf(opIndex);
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://bounceur.com/prg/service.php?x=" + this.x + "&y=" + this.y + "&c=" + this.operation);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return "Failed to connect: HTTP error code : " + responseCode;
                }
                InputStream in = new BufferedInputStream(conn.getInputStream());
                String result = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
                in.close(); // Close InputStream after use
                return result;
            } catch (Exception e) {
                return "Error: " + e.toString();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            resultView.setText(result);
        }
    }

}
