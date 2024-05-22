package sa.kfupm.hw7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progress, progressh;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your layout file name

        textView = findViewById(R.id.textView);
        progress = findViewById(R.id.progress);
        progressh = findViewById(R.id.progressh);
        imageView = findViewById(R.id.imageView);

        Button buttonDownload = findViewById(R.id.buttonDownload);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadTask().execute("https://media.licdn.com/dms/image/D4D03AQE-8j9HQ_S5iA/profile-displayphoto-shrink_800_800/0/1673363492883?e=2147483647&v=beta&t=taXNVN71zUcVIvQiiel0M422ILTCEXgaclhLxLtHgYs");
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            progressh.setProgress(0);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Log headers
                int i = 0;
                while (connection.getHeaderFieldKey(i) != null) {
                    Log.i("Header", connection.getHeaderFieldKey(i) + " : " + connection.getHeaderField(i));
                    i++;
                }

                int fileLength = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream());
                byte[] data = new byte[4096];
                long total = 0;
                int count;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) {
                        publishProgress((int) (total * 100 / fileLength));
                    }
                    output.write(data, 0, count);
                }
                bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(output.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressh.setProgress(values[0]);
            textView.setText("Download progress: " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            progress.setVisibility(View.GONE);
        }
    }
}
