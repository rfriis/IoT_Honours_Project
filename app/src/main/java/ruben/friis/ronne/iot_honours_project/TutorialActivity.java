package ruben.friis.ronne.iot_honours_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private com.tbuonomo.viewpagerdotsindicator.DotsIndicator dotsIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String tutorial = intent.getStringExtra("tutorial");    // get which tutorial to launch
        setContentView(R.layout.view_pager);

        // initialise the ViewPager2 object
        // and the dots indicator
        viewPager2 = findViewById(R.id.viewPager);
        dotsIndicator = findViewById(R.id.dotsIndicator);
        // initialise the viewpager adapter
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}
