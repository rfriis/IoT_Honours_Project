package ruben.friis.ronne.iot_honours_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SensorConnectionDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Get arguments from Bundle
        String sensor = getArguments().getString("tutorial");

        return new AlertDialog.Builder(requireContext())
                .setMessage("Checking " + sensor + " sensor connection")
                .setPositiveButton(getString(R.string.finish), (dialog, which) -> {Log.d("dialogDebug", "Dialog Finish button pressed"); })
                .create();
    }

    public static String TAG = "SensorConnectionDialog";
}
