package ruben.friis.ronne.iot_honours_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    private int[] images = {};
    private int[] instructionTitles = {};
    private int[] instructionDescriptions = {};

    // Images for temperature tutorial
    private int[] temperatureImages = {
            R.drawable.step_1_temp,
            R.drawable.step_2_temp,
            R.drawable.step_3_temp,
            R.drawable.step_4_temp,
            R.drawable.step_5_temp,
            R.drawable.step_6_temp
    };

    // Step titles for temperature tutorial
    private int[] temperatureInstructionTitles = {
            R.string.step_1,
            R.string.step_2,
            R.string.step_3,
            R.string.step_4,
            R.string.step_5,
            R.string.step_6
    };

    // Instructions for temperature tutorial
    private int[] temperatureInstructionDescriptions = {
            R.string.temp_description_step_1,
            R.string.temp_description_step_2,
            R.string.temp_description_step_3,
            R.string.temp_description_step_4,
            R.string.temp_description_step_5,
            R.string.temp_description_step_6
    };

    // Images for light tutorial
    private int[] lightImages = {
            R.drawable.step_1_light,
            R.drawable.step_2_light,
            R.drawable.step_3_light,
            R.drawable.step_4_light,
            R.drawable.step_5_light
    };

    // Step titles for light tutorial
    private int[] lightInstructionTitles = {
            R.string.step_1,
            R.string.step_2,
            R.string.step_3,
            R.string.step_4,
            R.string.step_5
    };

    // Instructions for light tutorial
    private int[] lightInstructionDescriptions = {
            R.string.light_description_step_1,
            R.string.light_description_step_2,
            R.string.light_description_step_3,
            R.string.light_description_step_4,
            R.string.light_description_step_5
    };

    // Images for moisture tutorial
    private int[] moistureImages = {
            R.drawable.step_1_moisture
    };

    // Step titles for moisture tutorial
    private int[] moistureInstructionTitles = {
            R.string.step_1
    };

    // Instructions for moisture tutorial
    private int[] moistureInstructionDescription = {
            R.string.moisture_description_step_1
    };


    private Context context;

    ViewPager2Adapter(Context context, String tutorial) {
        this.context = context;
        if (tutorial.equals("temperature")) {           // Temperature button pressed, set to Temperature tutorial
            images = temperatureImages;
            instructionTitles = temperatureInstructionTitles;
            instructionDescriptions = temperatureInstructionDescriptions;
        } else if (tutorial.equals("light")) {          // Light button pressed, set to Light tutorial
            images = lightImages;
            instructionTitles = lightInstructionTitles;
            instructionDescriptions = lightInstructionDescriptions;
        } else if (tutorial.equals("moisture")) {       // Moisture button pressed, set to Moisture tutorial
            images = moistureImages;
            instructionTitles = moistureInstructionTitles;
            instructionDescriptions = moistureInstructionDescription;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tutorial_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.instructionImage.setImageResource(images[position]);
        holder.instructionTitle.setText(instructionTitles[position]);
        holder.instructionDescription.setText(instructionDescriptions[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView instructionImage;
        TextView instructionTitle;
        TextView instructionDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instructionImage = itemView.findViewById(R.id.instructionImage);
            instructionTitle = itemView.findViewById(R.id.instructionTitleText);
            instructionDescription = itemView.findViewById(R.id.instructionDescriptionText);
        }
    }
}
