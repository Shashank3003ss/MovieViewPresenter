package com.newproject.mvptmdb.moviefilter;


import static com.newproject.mvptmdb.utils.Constants.KEY_RELEASE_FROM;
import static com.newproject.mvptmdb.utils.Constants.KEY_RELEASE_TO;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.newproject.mvptmdb.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MovieFilterActivity extends AppCompatActivity {

    private TextView tvFromReleaseDate;
    private TextView tvToReleaseDate;
    private TextView tvClerAll;

    private String fromDate = "";
    private String toDate = "";

    private RelativeLayout rlMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUI();
        setListeners();

        Intent mIntent = getIntent();
        fromDate = mIntent.getStringExtra(KEY_RELEASE_FROM);
        toDate = mIntent.getStringExtra(KEY_RELEASE_TO);

        // If the data from import is not empty then set it release from and release to values
        if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
            tvFromReleaseDate.setText(fromDate);
            tvToReleaseDate.setText(toDate);
        }
    }

    /**
     * This method will initialize the UI components
     */
    private void initUI() {
        tvFromReleaseDate = findViewById(R.id.tv_from_date);
        tvToReleaseDate = findViewById(R.id.tv_to_date);
        tvClerAll = findViewById(R.id.tv_clear_all);
        rlMainLayout = findViewById(R.id.rl_main_layout);
    }

    /**
     * This method will handle the listeners for the UI components
     */
    private void setListeners() {
        tvFromReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                DatePickerDialog dpd = new DatePickerDialog(
                        MovieFilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                                fromDate = date;
                                tvFromReleaseDate.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show();
            }
        });

        tvToReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                DatePickerDialog dpd = new DatePickerDialog(
                        MovieFilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth);
                                toDate = date;
                                tvToReleaseDate.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show();
            }
        });

        tvClerAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDate = "";
                toDate = "";
                tvFromReleaseDate.setText(getString(R.string.from));
                tvToReleaseDate.setText(getString(R.string.to));
            }
        });

        tvToReleaseDate.addTextChangedListener(twDates);
        tvFromReleaseDate.addTextChangedListener(twDates);
    }

    /**
     * Handling the text change listeners on dates to hide/show clear all button.
     */
    private TextWatcher twDates = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (fromDate != null && toDate != null && (!fromDate.isEmpty() || !toDate.isEmpty())) {
                tvClerAll.setVisibility(View.VISIBLE);
            } else {
                tvClerAll.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_done) {
            if ((fromDate.isEmpty() && !toDate.isEmpty()) || (!fromDate.isEmpty() && toDate.isEmpty())) {
                Snackbar snackbar = Snackbar
                        .make(rlMainLayout, getString(R.string.error_date_filter), Snackbar.LENGTH_LONG);
                snackbar.show();
                return true;
            }

            // Sending the return intent
            Intent returnIntent = new Intent();
            returnIntent.putExtra(KEY_RELEASE_FROM, fromDate);
            returnIntent.putExtra(KEY_RELEASE_TO, toDate);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}