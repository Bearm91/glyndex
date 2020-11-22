package com.bearm.glyndex.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bearm.glyndex.R;


public class InfoActivity extends AppCompatActivity {

    TextView digitalSource;
    TextView pdfSource;
    TextView mainSource;
    TextView giSource1;
    TextView giSource2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainSource = findViewById(R.id.gi_source_link);
        mainSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fundaciondiabetes.org"));
                startActivity(browserIntent);
            }
        });

        giSource1 = findViewById(R.id.gi_source_gi_link_1);
        giSource1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fundaciondiabetes.org/general/articulo/47/el-indice-glucemico-de-los-alimentos"));
                startActivity(browserIntent);
            }
        });

        giSource2 = findViewById(R.id.gi_source_gi_link_2);
        giSource2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fundaciondiabetes.org/general/noticia/14444/que-es-el-indice-glucemico-de-los-alimentos"));
                startActivity(browserIntent);
            }
        });

        digitalSource = findViewById(R.id.gi_source_digital_link);
        digitalSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fundaciondiabetes.org/sabercomer/tabla_de_raciones_de_hidratos_de_carbono"));
                startActivity(browserIntent);
            }
        });

        pdfSource = findViewById(R.id.gi_source_pdf_link);
        pdfSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fundaciondiabetes.org/upload/hidratos_carbono_textos/1/tablas_hidratos2.pdf"));
                startActivity(browserIntent);
            }
        });
    }
}
