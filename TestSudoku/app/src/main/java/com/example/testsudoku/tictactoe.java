package com.example.testsudoku;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tictactoe extends AppCompatActivity {

    TextView tv;
    GridLayout gridLayout;
    Button btnPlay;
    Button[][] buttons;
    boolean player1Turn = true;
    boolean playAgainstAI;
    int getTile;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tictactoe);

        tv = findViewById(R.id.tvResult);
        gridLayout = findViewById(R.id.gridLayout);
        btnPlay = findViewById(R.id.btnPlayagain);
        gridLayout.setBackgroundResource(R.drawable.border);

        getTile = getIntent().getIntExtra("tile", 3);
        if (getTile <= 0) {
            getTile = 3;
        }

        buttons = new Button[getTile][getTile];
        playAgainstAI = getIntent().getBooleanExtra("playAI", false);

        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                Button button = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 80;
                params.height = 80;
                params.rowSpec = GridLayout.spec(i, 1, 1f);
                params.columnSpec = GridLayout.spec(j, 1, 1f);
                button.setLayoutParams(params);
                button.setTextSize(16);
                button.setBackgroundResource(R.drawable.border);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClicked(button);
                    }
                });
                gridLayout.addView(button);
                buttons[i][j] = button;
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
                btnPlay.setVisibility(View.GONE);
            }
        });
    }

    private void onButtonClicked(Button button) {
        if (button.getText().toString().isEmpty()) {
            if (!playAgainstAI) {
                if (player1Turn) {
                    button.setTextColor(Color.RED);
                    button.setText("X");
                } else {
                    button.setTextColor(Color.BLUE);
                    button.setText("O");
                }
                player1Turn = !player1Turn;

                checkWinner();
            } else {
                if (player1Turn) {
                    button.setTextColor(Color.RED);
                    button.setText("X");
                    player1Turn = false;
                    checkWinner();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aiMove();
                        }
                    }, 1500);
                }
            }
        }
    }

    private void aiMove() {
        int[] bestMove = minimax(0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (bestMove.length == 2) {
            int row = bestMove[0];
            int col = bestMove[1];
            if (row != -1 && col != -1) {
                buttons[row][col].setTextColor(Color.BLUE);
                buttons[row][col].setText("O");
                player1Turn = true;
                checkWinner();
            }
        }
    }

    private int[] minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        String winner = getWinner();
        if (winner != null) {
            if (winner.equals("O")) return new int[]{getTile - depth, 0};
            if (winner.equals("X")) return new int[]{depth - getTile, 0};
            return new int[]{0, 0};
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    buttons[i][j].setText(isMaximizing ? "O" : "X");

                    int[] score = minimax(depth + 1, !isMaximizing, alpha, beta);
                    if (isMaximizing) {
                        if (score[0] > bestScore) {
                            bestScore = score[0];
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (score[0] < bestScore) {
                            bestScore = score[0];
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                        beta = Math.min(beta, bestScore);
                    }

                    buttons[i][j].setText("");
                    if (beta <= alpha) break; // Cắt tỉa alpha-beta
                }
            }
            if (beta <= alpha) break;
        }

        return depth == 0 ? bestMove : new int[]{bestScore, 0};
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
        String symbol = buttons[row][col].getText().toString();
        if (symbol.isEmpty()) return false;

        int count = 0;
        for (int i = 0; i < getTile; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r < 0 || r >= getTile || c < 0 || c >= getTile || !buttons[r][c].getText().toString().equals(symbol)) {
                return false;
            }
            count++;
        }
        return count == getTile;
    }

    private String getWinner() {
        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                if (checkDirection(i, j, 1, 0) || checkDirection(i, j, 0, 1) || checkDirection(i, j, 1, 1) || checkDirection(i, j, 1, -1)) {
                    return buttons[i][j].getText().toString();
                }
            }
        }

        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return null; // Không có người thắng và còn ô trống
                }
            }
        }

        return "Hòa"; // Hòa
    }

    private void checkWinner() {
        String winner = getWinner();
        if (winner != null) {
            if (winner.equals("Hòa")) {
                tv.setText(String.format("%s!", winner));
            } else {
                tv.setText(String.format("%s thắng!", winner));
            }

            if (btnPlay.getVisibility() == View.GONE) {
                btnPlay.setVisibility(View.VISIBLE);
            }
            stop();
        }
    }

    private void stop() {
        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < getTile; i++) {
            for (int j = 0; j < getTile; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        tv.setText("");
        player1Turn = true;
    }
}