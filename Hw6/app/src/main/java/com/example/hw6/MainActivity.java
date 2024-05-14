package com.example.hw6;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public ProgressBar progressh, progress;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        progressh = findViewById(R.id.progressh);
        progress = findViewById(R.id.progress);
        imageView = findViewById(R.id.imageView);

        progress.setVisibility(View.GONE);

        Button downloadButton = findViewById(R.id.buttonDownload);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaTache().execute();
            }
        });
    }

    private class MaTache extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            progressh.setProgress(0);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://media.licdn.com/dms/image/D4D03AQE-8j9HQ_S5iA/profile-displayphoto-shrink_800_800/0/1673363492883?e=2147483647&v=beta&t=taXNVN71zUcVIvQiiel0M422ILTCEXgaclhLxLtHgYs");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                int totalBytesRead = 0;
                int contentLength = connection.getContentLength();

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    totalBytesRead += bytesRead;
                    int progress = (int) ((totalBytesRead * 100) / contentLength);
                    publishProgress(progress);
                    // Simulate delay for demonstration
                    Thread.sleep(10);
                }

                inputStream.close();
                return "Download Complete";
            } catch (Exception e) {
                e.printStackTrace();
                return "Download Failed";
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressh.setProgress(values[0]);
            textView.setText(String.format("Download progress: %d%%", values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            textView.setText(s);
        }
    }
}
