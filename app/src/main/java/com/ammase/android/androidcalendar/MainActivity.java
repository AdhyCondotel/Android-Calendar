package com.ammase.android.androidcalendar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int mCheck = 1;
    private RelativeLayout checkIn, checkOut;
    private LinearLayout colorIn, colorOut;
    private TextView mIn, mOut;
    private String CekIn, CekOut = null;

    private CaldroidFragment caldroidFragment;

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        // cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.colorCalender));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.colorAccent, blueDate);
            caldroidFragment.setTextColorForDate(R.color.colorAccent, greenDate);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorIn = (LinearLayout)findViewById(R.id.colorIn);
        colorOut = (LinearLayout)findViewById(R.id.colorOut);
        checkIn = (RelativeLayout)findViewById(R.id.tombolCheckIn);
        checkOut = (RelativeLayout) findViewById(R.id.tombolCheckOut);
        checkIn.setOnClickListener(this);
        checkOut.setOnClickListener(this);
        colorIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        colorOut.setBackgroundColor(getResources().getColor(R.color.colorCalender));
        mIn = (TextView) findViewById(R.id.textViewCheckIn);
        mOut = (TextView)findViewById(R.id.textViewCheckOut);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        caldroidFragment = new CaldroidFragment();

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }
        setCustomResourceForDates();
        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // Toast.makeText(getApplicationContext(), formatter.format(date),
                //         Toast.LENGTH_SHORT).show();

                if (mCheck == 1 ){
                    mIn.setText(formatter.format(date));
                    CekIn = (formatter.format(date));
                    mCheck = 2;
                    colorOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    colorIn.setBackgroundColor(getResources().getColor(R.color.colorCalender));
                }else {
                    mOut.setText(formatter.format(date));
                    CekOut = (formatter.format(date));
                    mCheck = 1;
                    colorIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    colorOut.setBackgroundColor(getResources().getColor(R.color.colorCalender));
                }

            }

            @Override
            public void onChangeMonth(int month, int year ) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                // Toast.makeText(getApplicationContext(),
                //        "Long click " + formatter.format(date),
                //         Toast.LENGTH_SHORT).show();
            }


        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

    }

    @Override
    public void onClick(View view) {
        if (view == checkIn){
            mCheck = 1;
            colorIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            colorOut.setBackgroundColor(getResources().getColor(R.color.colorCalender));
        } else if (view == checkOut){
            mCheck = 2;
            colorOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            colorIn.setBackgroundColor(getResources().getColor(R.color.colorCalender));
        }
    }

    public void Search(View view) {
        if (CekIn == null ){
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Tanggal Check In ",
                    Toast.LENGTH_SHORT).show();

        } else if (CekOut == null){
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Tanggal Check Out ",
                    Toast.LENGTH_SHORT).show();
        }else {
            Intent book = new Intent(getApplicationContext(), Main2Activity.class);
            book.putExtra("cekIn", CekIn);
            book.putExtra("cekOut", CekOut);
            startActivity(book);

        }
    }
}
