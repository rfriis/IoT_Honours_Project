package ruben.friis.ronne.iot_honours_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class TutorialActivity extends AppCompatActivity {

    Button nextButton;
    Button backButton;
    Button finishButton;
    private ViewPager2 viewPager2;
    private com.tbuonomo.viewpagerdotsindicator.DotsIndicator dotsIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String tutorial = intent.getStringExtra("tutorial");    // get which tutorial to launch
        setContentView(R.layout.view_pager);

        // initialise the ViewPager2 object
        // and the dots indicator and the buttons;
        viewPager2 = findViewById(R.id.viewPager);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        nextButton = findViewById(R.id.tutorialNextButton);
        backButton = findViewById(R.id.tutorialBackButton);
        finishButton = findViewById(R.id.tutorialFinishButton);

        // initialise the viewpager adapter
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, tutorial);

        // add adapter to viewPager2
        viewPager2.setAdapter(viewPager2Adapter);

        // add viewPager to dotsIndicator
        dotsIndicator.setViewPager2(viewPager2);

        // make swipe event on viewpager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Integer totalPageCount = viewPager2Adapter.getItemCount();
                if (totalPageCount.equals(position + 1)) {
                    // If on the last page, hide the Next button, show the Finish button
                    nextButton.setVisibility(View.GONE);
                    finishButton.setVisibility(View.VISIBLE);
                } else {
                    // If NOT last page, show the Next button, hide the Finish button
                    nextButton.setVisibility(View.VISIBLE);
                    finishButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // Set NEXT button onClick listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
            }
        });

        // Set BACK button onClick listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true);
            }
        });
    }
}
