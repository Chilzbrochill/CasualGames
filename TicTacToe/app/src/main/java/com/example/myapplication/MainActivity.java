package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnAI, btnHuman, btn3, btn5;
    ImageButton btnClose;
    LinearLayout hiddenLayout;
    boolean playAI = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAI = findViewById(R.id.btnAI);
        btnHuman = findViewById(R.id.btnHuman);
        btn3 = findViewById(R.id.btn3x3);
        btn5 = findViewById(R.id.btn5x5);
        btnClose = findViewById(R.id.ibtnClose);
        hiddenLayout = findViewById(R.id.hidden_layout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenLayout.getVisibility() == View.GONE) {
                    hiddenLayout.setVisibility(View.VISIBLE);
//                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, hiddenLayout.getHeight());                // toYDelta
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    hiddenLayout.startAnimation(animate);
                } else {
                    hiddenLayout.setVisibility(View.GONE);
                }
            }
        });

        btnAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAI = true;
                if (hiddenLayout.getVisibility() == View.GONE) {
                    hiddenLayout.setVisibility(View.VISIBLE);
//                    TranslateAnimation animate = new TranslateAnimation(0, 0, hiddenLayout.getHeight(), 0);                // toYDelta
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    hiddenLayout.startAnimation(animate);
                } else {
                    hiddenLayout.setVisibility(View.GONE);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, tictactoe.class);
                if (playAI) {
                    intent.putExtra("playAI", true);
                    intent.putExtra("tile", 3);
                }
                else {
                    intent.putExtra("tile", 3);
                }
                startActivity(intent);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, tictactoe.class);
                if (playAI) {
                    intent.putExtra("playAI", true);
                    intent.putExtra("tile", 5);
                }
                else {
                    intent.putExtra("tile", 5);
                }
                startActivity(intent);
            }
        });
    }
}