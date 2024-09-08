package com.example.testsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu_level_FlipCard extends AppCompatActivity {

    public void AddListenToButton(){
        ImageView btnLv1 = findViewById(R.id.imgBtn1);
        ImageView btnLv2 = findViewById(R.id.imgBtn2);
        ImageView btnLv3 = findViewById(R.id.imgBtn3);
        ImageView btnLv4 = findViewById(R.id.imgBtn4);
        ImageView btnLv5 = findViewById(R.id.imgBtn5);
        ImageView btnLv6 = findViewById(R.id.imgBtn6);

        btnLv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("1"));
                startActivity(i);
            }
        });
        btnLv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("2"));
                startActivity(i);
            }
        });
        btnLv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("3"));
                startActivity(i);
            }
        });
        btnLv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("4"));
                startActivity(i);
            }
        });
        btnLv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("5"));
                startActivity(i);
            }
        });
        btnLv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, FlipCard.class);
                i.putExtra("level", String.valueOf("6"));
                startActivity(i);
            }
        });
    }

    public void SetEventButton(){
        ImageView btnBack = findViewById(R.id.btnBackToHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(menu_level_FlipCard.this, menu_FlipCard.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_level_flip_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SetEventButton();
        AddListenToButton();
    }
}