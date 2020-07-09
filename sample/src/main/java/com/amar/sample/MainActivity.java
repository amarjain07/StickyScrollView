package com.amar.sample;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private StickyScrollView scrollView;
    private ImageView mainShoeView;
    private boolean redShoeVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (StickyScrollView) findViewById(R.id.scrollView);
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.title).setOnClickListener(this);
        findViewById(R.id.other_product).setOnClickListener(this);
        mainShoeView = findViewById(R.id.main_shoe_picture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy:
                Toast.makeText(this, scrollView.isFooterSticky() ? "Footer is Sticky" : "Footer is not sticky", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save:
                Toast.makeText(this, scrollView.isFooterSticky() ? "Footer is Sticky" : "Footer is not sticky", Toast.LENGTH_SHORT).show();
                break;
            case R.id.title:
                Toast.makeText(this, scrollView.isHeaderSticky() ? "Header is Sticky" : "Header is not sticky", Toast.LENGTH_SHORT).show();
                break;
            case R.id.other_product:
                switchShoeViews();
                break;
        }
    }

    private void switchShoeViews() {
        if (redShoeVisible) {
            redShoeVisible = false;
            mainShoeView.setImageResource(R.drawable.similar_1);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainShoeView.getLayoutParams();
            params.height = (int) dpToPixel(320);
            mainShoeView.setLayoutParams(params);
        } else {
            redShoeVisible = true;
            mainShoeView.setImageResource(R.drawable.nike);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainShoeView.getLayoutParams();
            params.height = (int) dpToPixel(420);
            mainShoeView.setLayoutParams(params);
        }
    }

    private float dpToPixel(int pixel) {
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, r.getDisplayMetrics());
    }
}
