package com.example.ex_xuxiaopeng002.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.ex_xuxiaopeng002.myapplication.R;
import com.example.ex_xuxiaopeng002.myapplication.view.HistogramView;
import com.example.ex_xuxiaopeng002.myapplication.view.LotteryView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistogramActivity extends AppCompatActivity {

    @BindView(R.id.my_view)
    HistogramView myView;
    @BindView(R.id.seekbara)
    SeekBar seekbara;
    @BindView(R.id.lottery)
    LotteryView lottery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        ButterKnife.bind(this);

        myView.start();
        seekbara.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.d("progress", progress + "");

                myView.setvoiceRate(progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbara.setProgress(80);
    }

    @OnClick(R.id.my_view)
    public void onClick() {
        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
//        myView.stop();

        lottery.startAnimation(15);
    }
}
