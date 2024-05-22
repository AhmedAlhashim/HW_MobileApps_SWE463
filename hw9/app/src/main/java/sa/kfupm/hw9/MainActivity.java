package sa.kfupm.hw9;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        textViewMessage = findViewById(R.id.textViewMessage);
        setupMessageListener();
    }

    public void modify(View view) {
        DatabaseReference msgRef = mDatabase.child("message");
        msgRef.setValue("Hello World !").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Field modified", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to modify field", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupMessageListener() {
        mDatabase.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                if (message != null) {
                    textViewMessage.setText(message);
                } else {
                    textViewMessage.setText("No message received");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to read value: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
