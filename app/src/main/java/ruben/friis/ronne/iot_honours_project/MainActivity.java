package ruben.friis.ronne.iot_honours_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button temperatureTutorialButton;
    Button lightTutorialButton;
    Button moistureTutorialButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference mainSetReference;
    DatabaseReference plantTypesReference;

    private TextView moistureTextView;
    private TextView tempTextView;
    private TextView lightTextView;
    private Spinner plantTypeSpinner;
    private ArrayList<String> plantNames;
    private ArrayList<Plant> plants;


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

        // Initialise Plant Type Spinner
        plantTypeSpinner = findViewById(R.id.plantTypeSpinner);
        plantTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlant = parent.getSelectedItem().toString();
                for (Plant plant : plants) {
                    if (plant.getName() != null && plant.getName().contains(selectedPlant)) {
                        Log.d("spinner", "Plant match : " + plant.getName() + " tempMin : " + plant.getTempMin());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Get Plant Types from Firebase
        getPlants();

        // Get live Environment data from Firebase
        getFirebaseData();
    }

    // Get data from sensors
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

    private void getPlants() {
        plantTypesReference = firebaseDatabase.getReference("Plants");
        // initialise plantTypes list
        plantNames = new ArrayList<>();
        plants = new ArrayList<>();
        plantTypesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    HashMap plantValues = (HashMap) child.getValue();
                    Plant plant = new Plant(child.getKey(), ((Number)plantValues.get("tempMin")).floatValue(), (String) plantValues.get("lightMax"), (String) plantValues.get("lightMin"));
                    plants.add(plant);
                    plantNames.add(plant.getName());
                }
                // Populate Plant spinner
                String[] items = plantNames.toArray(new String[plantNames.size()]);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_spinner_item, plantNames);
                plantTypeSpinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", error.toException());
            }
        });
    }

}