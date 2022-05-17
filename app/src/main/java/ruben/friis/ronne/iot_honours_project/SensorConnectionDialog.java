package ruben.friis.ronne.iot_honours_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SensorConnectionDialog extends DialogFragment {

    DatabaseReference databaseReference;
    String sensorConnection;
    String message;
    String additionalInfo;
    AlertDialog alertDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Get arguments from Bundle
        String sensor = getArguments().getString("tutorial");

        message = "Loading data...";
        additionalInfo = "";
        // Get connection data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getFirebaseData(sensor);

        return makeDialog(message, additionalInfo);
    }


    public static String TAG = "SensorConnectionDialog";

    // Function to create a Dialog, takes in a message parameter
    public Dialog makeDialog(String message, String additionalInfo) {
        alertDialog = new AlertDialog.Builder(requireContext())
                .setTitle(message)
                .setMessage(additionalInfo)
                .setPositiveButton(getString(R.string.finish), (dialog, which) -> {getActivity().finish();} )
                .create();
        return alertDialog;
    }



    private void getFirebaseData(String sensor) {
        switch (sensor) {
            case "Temperature":
                databaseReference = databaseReference.child("Connections").child("tempConnection");
                break;
            case "Light":
                databaseReference = databaseReference.child("Connections").child("lightConnection");
                break;
            case "Moisture":
                databaseReference = databaseReference.child("Connections").child("moistureConnection");
                break;
        }

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    alertDialog.setMessage("Error fetching connection data");
                } else {
                    Log.d("firebase", "connection: " + task.getResult().getValue());
                    sensorConnection = String.valueOf(task.getResult().getValue());
                    // check the value of the connection variable
                    if (sensorConnection != null) {
                        if (sensorConnection.equals("true")) {
                            if (!sensor.equals("Temperature")) {
                                message = sensor + " sensor looks good!";
                                additionalInfo = "Make sure the light on the sensor is on";
                            } else {
                                message = sensor + " sensor";
                                additionalInfo = "Sensor connection looks good!";
                            }
                        } else if (sensorConnection.equals("false")) {
                            message = sensor + " sensor not connected";
                            additionalInfo = "Try to go through the tutorial again";
                        }
                    }
                    alertDialog.setTitle(message);
                    if (!additionalInfo.equals("")) {
                        alertDialog.setMessage(additionalInfo);
                    }
                }
            }
        });
    }
}
