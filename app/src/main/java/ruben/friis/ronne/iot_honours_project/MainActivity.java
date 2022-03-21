package ruben.friis.ronne.iot_honours_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button temperatureTutorialButton;
    Button lightTutorialButton;
    Button moistureTutorialButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference mainSetReference;

    private TextView moistureTextView;
    private TextView tempTextView;
    private TextView lightTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise tutorial buttons
        temperatureTutorialButton = findViewById(R.id.temperatureButton);
        lightTutorialButton = findViewById(R.id.lightButton);
        moistureTutorialButton = findViewById(R.id.moistureButton);

        // when click on temp tutorial button, start new activity and send "temperature" as extra
        temperatureTutorialButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
            myIntent.putExtra("tutorial", "Temperature");
            MainActivity.this.startActivity(myIntent);
        });

        // when click on light tutorial button, start new activity and send "light" as extra
        lightTutorialButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
            myIntent.putExtra("tutorial", "Light");
            MainActivity.this.startActivity(myIntent);
        });

        // when click on moisture tutorial button, start new activity and send "moisture" as extra
        moistureTutorialButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
            myIntent.putExtra("tutorial", "Moisture");
            MainActivity.this.startActivity(myIntent);
        });

        // get firebase database instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get database references
        // need child reference of main database
        databaseReference = firebaseDatabase.getReference("Data");
        mainSetReference = databaseReference.child("Current");

        // initialise TextView's
        moistureTextView = findViewById(R.id.textViewMoisture);
        tempTextView = findViewById(R.id.textViewTemperature);
        lightTextView = findViewById(R.id.textViewLight);

        getFirebaseData();
    }

    private void getFirebaseData() {
        mainSetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String moisture = snapshot.child("moisture").getValue().toString();
                String temp = snapshot.child("temperature").getValue().toString();
                String light = snapshot.child("light").getValue().toString();
                moistureTextView.setText(moisture);
                tempTextView.setText(temp + " °C");
                lightTextView.setText(light + " lux");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", error.toException());
            }
        });
    }
}