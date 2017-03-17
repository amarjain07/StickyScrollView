package com.amar.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private StickyScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (StickyScrollView) findViewById(R.id.scrollView);
        findViewById(R.id.buy).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.title).setOnClickListener(this);
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
        }
    }
}
