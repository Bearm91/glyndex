package com.bearm.glyndex.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bearm.glyndex.R;


public class InfoActivity extends AppCompatActivity {

    TextView digitalSource;
    TextView pdfSource;
    TextView mainSource;
    TextView giSource1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


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
}
