package com.example.testsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WinSudoku extends AppCompatActivity {
    private LinearLayout layoutDifficulty;
    private View opacityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_win_sudoku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_lvl = findViewById(R.id.tv_level);
        TextView tv_point_win = findViewById(R.id.tv_point_win);
        TextView tv_time_win = findViewById(R.id.tv_time);
        ImageView img_backHP = findViewById(R.id.img_backToHomePage);
        TextView tv_backHP = findViewById(R.id.tv_BackToHomePage);
        Button btn_newgame = findViewById(R.id.button_newgameSudoku);

        Intent i = getIntent();
        String level = i.getStringExtra("level");
        String point_win = i.getStringExtra("point_win");
        String time = i.getStringExtra("time");

        tv_lvl.setText(level);
        tv_point_win.setText(point_win);
        tv_time_win.setText(time);

        Intent iBack = new Intent(WinSudoku.this, SudokuMenu.class);
        iBack.putExtra("point_win", point_win);

        img_backHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iBack);
            }
        });
        tv_backHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iBack);
            }
        });
        layoutDifficulty = findViewById(R.id.layoutDifficulty);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        opacityView = findViewById(R.id.backgroundOverlay);
        opacityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutDifficulty.startAnimation(slideDown);  // Áp dụng animation
                layoutDifficulty.setVisibility(View.GONE);
                opacityView.setVisibility(View.INVISIBLE);
            }
        });
        Intent iNew = new Intent(WinSudoku.this, Sudoku.class);
        btn_newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDifficultyLayout();
                Button easy = findViewById(R.id.btnEasy);
                Button medium = findViewById(R.id.btnMedium);
                Button hard = findViewById(R.id.btnHard);
                easy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iNew.putExtra("count", "30");
                        startActivity(iNew);
                    }
                });
                medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iNew.putExtra("count", "40");
                        startActivity(iNew);
                    }
                });
                hard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iNew.putExtra("count", "50");
                        startActivity(iNew);
                    }
                });
            }
        });
    }
    private void showDifficultyLayout() {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        opacityView = findViewById(R.id.backgroundOverlay);
        layoutDifficulty.setVisibility(View.VISIBLE);  // Hiển thị layout
        layoutDifficulty.startAnimation(slideUp);
        opacityView.setVisibility(View.VISIBLE);// Áp dụng animation
    }
}