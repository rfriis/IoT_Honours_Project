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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorConnectionDialog extends DialogFragment {

    DatabaseReference databaseReference;
    String sensorConnection;
    String message;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Get arguments from Bundle
        String sensor = getArguments().getString("tutorial");

        // Get connection data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getFirebaseData();

        Log.d("firebase", "sensorConnection = " + sensorConnection);

        if(sensorConnection != null) {
            if (sensorConnection.equals("true")) {
                message = "Sensor connected";
            } else {
                message = "Sensor not connected";
            }
        }

        return new AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(getString(R.string.finish), (dialog, which) -> {Log.d("dialogDebug", "Dialog Finish button pressed"); })
                .create();
    }

    public static String TAG = "SensorConnectionDialog";

    private void getFirebaseData() {
        databaseReference.child("Connections").child("tempConnection").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", "connection: " + String.valueOf(task.getResult().getValue()));
                    sensorConnection = String.valueOf(task.getResult().getValue());
                }
            }
        });
    }
}
