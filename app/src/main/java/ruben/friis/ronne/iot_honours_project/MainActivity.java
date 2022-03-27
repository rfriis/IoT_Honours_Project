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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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
    private String selectedPlant;
    private float lightMin;
    private float lightMax;

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


        // Get selected Plant type from Shared Preferences if exits
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.plant_preference_file_key), Context.MODE_PRIVATE);
        String defaultValue = getString(R.string.default_plant_value);
        selectedPlant = sharedPreferences.getString(getString(R.string.plant_reference_key), defaultValue);
        Log.d("plant", "Shared Preferences: " + selectedPlant);

        // Initialise Plant Type Spinner
        plantTypeSpinner = findViewById(R.id.plantTypeSpinner);
        plantTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlant = parent.getSelectedItem().toString();
                for (Plant plant : plants) {
                    if (plant.getName() != null && plant.getName().contains(selectedPlant)) {
                        Log.d("spinner", "Plant match : " + plant.getName() + " tempMin : " + plant.getTempMin());
                        setPlantData(plant);
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


    // Get plant types from Firebase
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
                    Plant plant = new Plant(child.getKey(), ((Number)plantValues.get("tempMin")).floatValue(), (String) plantValues.get("light"));
                    plants.add(plant);
                    plantNames.add(plant.getName());
                }
                // Populate Plant spinner
                String[] items = plantNames.toArray(new String[plantNames.size()]);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_spinner_item, plantNames);
                plantTypeSpinner.setAdapter(spinnerAdapter);
                int selectedPlantPosition = 0;
                if (!selectedPlant.equals(getString(R.string.default_plant_value))) {                   // Set selected plant in spinner according to SharedPreferences
                    selectedPlantPosition = plantNames.indexOf(selectedPlant);
                }
                plantTypeSpinner.setSelection(selectedPlantPosition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", error.toException());
            }
        });
    }


    // Set the selected Plant's variables
    private void setPlantData(Plant plant) {
        //TODO
        // - Extract data from Plant class
        // - Translate String data into numerical data
        // - Find the acceptable light level range
        // - Populate the TextView's with the new data

        // Set plant in Shared Preferences
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.plant_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.plant_reference_key), plant.getName());
        editor.apply();

        float temperatureMin = plant.getTempMin();
        String lightConditions = plant.getLight();
        if (lightConditions.equals(getResources().getString(R.string.full_sun))) {                                  // Full Sun
            lightMax = 100000000;
            lightMin = 2000;
        } else if (lightConditions.equals(getResources().getString(R.string.full_sun_to_partial_shade))) {          // Full Sun to Partial Shade
            lightMax = 2000;
            lightMin = 1250;
        } else if (lightConditions.equals(getResources().getString(R.string.partial_shade))) {                      // Partial Shade
            lightMax = 1250;
            lightMin = 500;
        } else if (lightConditions.equals(getResources().getString(R.string.partial_shade_to_full_shade))) {        // Partial Shade to Full Shade
            lightMax = 500;
            lightMin = 250;
        } else if (lightConditions.equals(getResources().getString(R.string.full_shade))) {                         // Full Shade
            lightMax = 250;
            lightMin = 0;
        }

        Log.d("plant", plant.getName() + "   lightMin: " + lightMin + "   lightMax: " + lightMax);
    }

}