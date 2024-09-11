package com.example.testsudoku;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu_tictactoe extends AppCompatActivity {

    ImageView btnNewgame, btn2players, btnExit, btn3, btn5, btn8, btn10, btnCustom, btnHome, btnMusic;
    View overlay;
    ConstraintLayout hiddenLayout;
    MediaPlayer mediaPlayer;
    boolean speaker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_tictactoe);

        btnNewgame = findViewById(R.id.img_newgame);
        btn2players = findViewById(R.id.img_2players);
        btnExit = findViewById(R.id.img_exit);
        btn3 = findViewById(R.id.img3x3);
        btn5 = findViewById(R.id.img5x5);
        btn8 = findViewById(R.id.img8x8);
        btn10 = findViewById(R.id.img10x10);
        btnCustom = findViewById(R.id.img_custom);
        btnHome = findViewById(R.id.img_icon_home);
        btnMusic = findViewById(R.id.img_icon_music);
        overlay = findViewById(R.id.overlay_bg);
        hiddenLayout = findViewById(R.id.hidden_layout);
        mediaPlayer = MediaPlayer.create(this, R.raw.music_bg_tictactoe);
        mediaPlayer.setLooping(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnNewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, tictactoe.class);
                intent.putExtra("playAI", true);
                intent.putExtra("tile", 3);
                startActivity(intent);
            }
        });

        btn2players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenLayout.getVisibility() == View.GONE) {
                    hiddenLayout.setVisibility(View.VISIBLE);
                    overlay.setVisibility(View.VISIBLE);

                    Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                    hiddenLayout.startAnimation(slideUp);
                } else {
                    hiddenLayout.setVisibility(View.GONE);
                    overlay.setVisibility(View.GONE);
                }
            }
        });

        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
            }
        });

        hiddenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, tictactoe.class);
                intent.putExtra("tile", 3);
                startActivity(intent);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, tictactoe.class);
                intent.putExtra("tile", 5);
                startActivity(intent);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, tictactoe.class);
                intent.putExtra("tile", 8);
                startActivity(intent);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, tictactoe.class);
                intent.putExtra("tile", 10);
                startActivity(intent);
            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!speaker) {
                    btnMusic.setImageResource(R.drawable.an_music_on);
                    mediaPlayer.start();
                    speaker = true;
                } else {
                    btnMusic.setImageResource(R.drawable.an_music_off);
                    mediaPlayer.pause();
                    speaker = false;
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, Menu.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                Intent intent = new Intent(menu_tictactoe.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}