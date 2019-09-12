package com.diol.widget.materialprogressbarexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diol.widget.materialprogressbar.MaterialPercentageProgressBar;
import com.diol.widget.materialprogressbar.Utils;

public class MainActivity extends AppCompatActivity {

    MaterialPercentageProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.mppbProgress);


        progress.fillTo(2);

        Utils.runAfter(3000, new Runnable() {
            @Override
            public void run() {
                progress.setSteps(2);

            }
        });

    }
}
