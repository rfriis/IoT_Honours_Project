package ruben.friis.ronne.iot_honours_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    private int[] images = {R.drawable.step_1_temp, R.drawable.step_2_temp};
    private int[] instructionTitles = {R.string.step_1, R.string.step_2};
    private int[] instructionDescriptions = {R.string.temp_description_step_1, R.string.temp_description_step_2};


    private Context context;

    ViewPager2Adapter(Context context) {
        this.context = context;
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
