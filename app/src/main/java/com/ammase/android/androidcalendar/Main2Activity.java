package com.ammase.android.androidcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private String CekIn, CekOut;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        CekIn = getIntent().getExtras().getString("cekIn");
        CekOut = getIntent().getExtras().getString("cekOut");

        textView = (TextView)findViewById(R.id.textView);
        textView.setText("Check In :" +CekIn + "\nCheck Out :" +CekOut);
    }
}
