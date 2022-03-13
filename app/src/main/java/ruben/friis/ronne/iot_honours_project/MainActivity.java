package ruben.friis.ronne.iot_honours_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
        temperatureTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
                myIntent.putExtra("tutorial", "temperature");
                MainActivity.this.startActivity(myIntent);
            }
        });

        // when click on light tutorial button, start new activity and send "light" as extra
        lightTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
                myIntent.putExtra("tutorial", "light");
                MainActivity.this.startActivity(myIntent);
            }
        });

        // when click on moisture tutorial button, start new activity and send "moisture" as extra
        moistureTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, TutorialActivity.class);
                myIntent.putExtra("tutorial", "moisture");
                MainActivity.this.startActivity(myIntent);
            }
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