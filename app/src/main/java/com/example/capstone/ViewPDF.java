package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewPDF extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);

        pdfView = findViewById(R.id.pdfView);
        String  file = UI_ActionMenu.pdf.getPDFname();
        pdfView.fromAsset(file).load();

    }
}