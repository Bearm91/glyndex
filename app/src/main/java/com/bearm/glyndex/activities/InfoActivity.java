package com.bearm.glyndex.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bearm.glyndex.R;
import com.bearm.glyndex.helpers.Constants;


public class InfoActivity extends AppCompatActivity {

    TextView digitalSource;
    TextView pdfSource;
    TextView mainSource;
    TextView giSource1;
    TextView onBoardingTitle;
    Button closeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        closeButton = findViewById(R.id.btn_close);
        onBoardingTitle = findViewById(R.id.gi_title_onboarding);

        closeButton.setOnClickListener((View v) -> {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_ONBOARDING_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.SHARED_PREFERENCES_ONBOARDING_SHOWINFO, false);
            editor.apply();

            finish();
        });

        loadInfo();
    }

    private void loadInfo() {
        mainSource = findViewById(R.id.gi_source);
        mainSource.setMovementMethod(LinkMovementMethod.getInstance());
        mainSource.setText(Html.fromHtml(getString(R.string.info_source_main)));

        giSource1 = findViewById(R.id.gi_source_gi);
        giSource1.setMovementMethod(LinkMovementMethod.getInstance());
        giSource1.setText(Html.fromHtml(getString(R.string.info_source_gi_links)));

        digitalSource = findViewById(R.id.gi_source_digital_link);
        digitalSource.setMovementMethod(LinkMovementMethod.getInstance());
        digitalSource.setText(Html.fromHtml(getString(R.string.info_source_digital)));

        pdfSource = findViewById(R.id.gi_source_pdf);
        pdfSource.setMovementMethod(LinkMovementMethod.getInstance());
        pdfSource.setText(Html.fromHtml(getString(R.string.info_source_pdf)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        boolean isOnBoarding = bundle.getBoolean(Constants.SHARED_PREFERENCES_ONBOARDING_KEY, false);
        if (isOnBoarding) {
            closeButton.setVisibility(View.VISIBLE);
            onBoardingTitle.setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name_2);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            closeButton.setVisibility(View.GONE);
            onBoardingTitle.setVisibility(View.GONE);
        }
    }
}
