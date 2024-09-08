package com.example.testsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SudokuMenu extends AppCompatActivity {
    private LinearLayout layoutDifficulty;
    private View opacityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sudoku_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button newGame = findViewById(R.id.button_newgameSudoku);

        // Tham chiếu đến layout chọn độ khó
        layoutDifficulty = findViewById(R.id.layoutDifficulty);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDifficultyLayout();
                Button easy = findViewById(R.id.btnEasy);
                Button medium = findViewById(R.id.btnMedium);
                Button hard = findViewById(R.id.btnHard);
                easy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SudokuMenu.this, Sudoku.class);
                        i.putExtra("count", "30");
                        startActivity(i);
                    }
                });
                medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SudokuMenu.this, Sudoku.class);
                        i.putExtra("count", "40");
                        startActivity(i);
                    }
                });
                hard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SudokuMenu.this, Sudoku.class);
                        i.putExtra("count", "50");
                        startActivity(i);
                    }
                });
            }
        });
        ConstraintLayout constraintLayout = findViewById(R.id.main);
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
    }
    private void showDifficultyLayout() {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        opacityView = findViewById(R.id.backgroundOverlay);
        layoutDifficulty.setVisibility(View.VISIBLE);  // Hiển thị layout
        layoutDifficulty.startAnimation(slideUp);
        opacityView.setVisibility(View.VISIBLE);// Áp dụng animation
    }

}