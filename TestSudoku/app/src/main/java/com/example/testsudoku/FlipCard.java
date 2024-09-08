package com.example.testsudoku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;


import java.util.ArrayList;
import java.util.Random;

public class FlipCard extends AppCompatActivity {

    // ------- Card class -------
    public class Card{
        private int id;
        private int idBg;
        private ImageView imgView;
        private boolean isRunning = true;

        public int getId() {
            return id;
        }
        public int getIdBg() {
            return idBg;
        }
        public void setIdBg(int idBgSet) {
            this.idBg = idBgSet;
        }
        public void setImgView(ImageView imgView) {
            this.imgView = imgView;
        }

        public ImageView getImgView() {
            return imgView;
        }

        public void setIDImg(int idSet) {
            this.id = idSet;
            this.imgView.setImageResource(idSet);
        }

        public boolean isRunning() {
            return isRunning;
        }

        public void setRunning(boolean running) {
            isRunning = running;
        }
    }

    // ------- Elements -------
    public TextView txtScore;
    public int score = 0;

    // ------- Controller -------
    public Card[][] cards;
    public int[][] checkArray;
    private ArrayList<Integer> imageList;
    public int[][] idCheckImg;

    public int countCorrect;
    public int amoutOpen = 0;

    // width height table
    int widthTable = 4;
    int heightTable = 4;
    int amountCard = widthTable * heightTable / 2;

    public Card preCard;
    public Card afterCard;

    public boolean CheckCard(){
        if (preCard.getId() == afterCard.getId()) return true;

        return false;
    }

    public void ResetCard(){
        ObjectAnimator animatorControl = ObjectAnimator.ofFloat(preCard.getImgView(), "rotationY", 0f, -90f);
        animatorControl.setDuration(250);
        animatorControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                preCard.getImgView().setImageResource(preCard.getIdBg());
                ObjectAnimator animatorControl = ObjectAnimator.ofFloat(preCard.getImgView(), "rotationY", -90f, 0f);
                animatorControl.setDuration(250);
                animatorControl.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        preCard.setRunning(false);
                    }
                });
                animatorControl.start();
            }
        });
        animatorControl.start();

        animatorControl = ObjectAnimator.ofFloat(afterCard.getImgView(), "rotationY", 0f, -90f);
        animatorControl.setDuration(250);
        animatorControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                afterCard.getImgView().setImageResource(afterCard.getIdBg());
                ObjectAnimator animatorControl = ObjectAnimator.ofFloat(afterCard.getImgView(), "rotationY", -90f, 0f);
                animatorControl.setDuration(250);
                animatorControl.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        afterCard.setRunning(false);
                    }
                });
                animatorControl.start();
            }
        });
        animatorControl.start();

        amoutOpen = 0;
    }

    public void CheckWin(int amountCard){
        if (countCorrect == amountCard){
            Log.e("Win:", "true");
        }else {
            Log.e("Win:", "false");
        }
    }

    public void CreateTable(){
        imageList = new ArrayList<>();
        imageList.add(R.drawable.vietvg_bgcard);
        imageList.add(R.drawable.vietvg_grape);
        imageList.add(R.drawable.vietvg_apple);
        imageList.add(R.drawable.vietvg_orange);
        imageList.add(R.drawable.vietvg_pinaapple);
        imageList.add(R.drawable.vietvg_apricot);
        imageList.add(R.drawable.vietvg_kiwi);
        imageList.add(R.drawable.vietvg_avocado);
        imageList.add(R.drawable.vietvg_mango);

        GridLayout gridCard = findViewById(R.id.gridLayoutCard);

        gridCard.removeAllViews();

        int amountChoose = 0;

        gridCard.setColumnCount(heightTable);
        gridCard.setRowCount(widthTable);

        idCheckImg = new int[amountCard][2];

        for (int i = 0; i < amountCard; i++){
            idCheckImg[i][0] = -1;
        }
        for (int i = 0; i < amountCard; i++){
            idCheckImg[i][1] = 0;
        }

        cards = new Card[widthTable][heightTable];

        for (int i = 0; i < widthTable; i++){
            for (int j = 0; j < heightTable; j++){
                Card card = new Card();
                ImageView imgCard = new ImageView(FlipCard.this);

                card.setImgView(imgCard);

                Random random = new Random();
                int min = 1;
                int max = imageList.size();
                int randomNumber;
                int indexIdCheckImg = 0;
                boolean checkArr = false;
                int resultId = 0;
                do {
                    if (amountChoose < amountCard){
                        randomNumber = random.nextInt(max - min) + min;
                        checkArr = true;
                        for (int k = 0; k < amountCard; k++) {
                            if (idCheckImg[k][0] != randomNumber && idCheckImg[k][0] != -1){
                                continue;
                            }
                            else if (idCheckImg[k][0] != randomNumber && idCheckImg[k][1] == 2){
                                continue;
                            }else if (idCheckImg[k][0] == randomNumber && idCheckImg[k][1] == 2){
                                break;
                            }

                            if (randomNumber == idCheckImg[k][0]){
                                if (idCheckImg[k][1] < 2){
                                    checkArr = false;
                                }
                                else if (idCheckImg[k][1] == 2){
                                    break;
                                }
                            }
                            else if (randomNumber != idCheckImg[k][0]) {
                                amountChoose += 1;
                                checkArr = false;
                            }

                            if (!checkArr){
                                indexIdCheckImg = k;
                                resultId = randomNumber;
                                break;
                            }
                        }
                    }
                    else {
                        randomNumber = random.nextInt(amountCard);
                        int countIndex = idCheckImg[randomNumber][1];

                        if (countIndex < 2){
                            checkArr = false;
                            indexIdCheckImg = randomNumber;
                            resultId = idCheckImg[randomNumber][0];
                        }
                        else {
                            checkArr = true;
                        }
                    }
                }while (checkArr);

                card.setIDImg(imageList.get(resultId));
                card.setIdBg(imageList.get(0));

                idCheckImg[indexIdCheckImg][0] = resultId;
                idCheckImg[indexIdCheckImg][1] += 1;

                GridLayout.LayoutParams gridChild = new GridLayout.LayoutParams();

                gridChild.width = 200;
                gridChild.height = 200;

                int spacingRowColumn = 5;
                gridChild.setMargins(spacingRowColumn, spacingRowColumn, spacingRowColumn, spacingRowColumn);
                gridChild.rowSpec = GridLayout.spec(i, 1, 1);
                gridChild.columnSpec = GridLayout.spec(j, 1, 1);

                card.getImgView().setLayoutParams(gridChild);
                gridCard.addView(card.getImgView());

                cards[i][j] = card;
            }
        }
    }

    public void StartGame(){
        for (int i = 0; i < widthTable; i++) {
            for (int j = 0; j < heightTable; j++) {
                Card card = cards[i][j];

                ObjectAnimator animatorControl = ObjectAnimator.ofFloat(card.getImgView(), "rotationY", 0f, -90f);
                animatorControl.setDuration(250); // thời gian quay (1000 = 1s)
                animatorControl.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        card.getImgView().setImageResource(card.getIdBg());
                        ObjectAnimator animatorControl = ObjectAnimator.ofFloat(card.getImgView(), "rotationY", -90f, 0f);
                        animatorControl.setDuration(250);
                        animatorControl.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                card.setRunning(false);
                            }
                        });
                        animatorControl.start();
                    }
                });
                animatorControl.start();
            }
        }
    }

    public void AddListennerToCard() {
        for (int i = 0; i < widthTable; i++) {
            for (int j = 0; j < heightTable; j++) {
                Card card = cards[i][j];

                card.getImgView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (card.isRunning()) return;

                        card.setRunning(true);
                        ObjectAnimator animatorControl = ObjectAnimator.ofFloat(card.getImgView(), "rotationY", 0f, -90f);
                        animatorControl.setDuration(250); // thời gian quay (1000 = 1s)
                        animatorControl.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                card.getImgView().setImageResource(card.getId());
                                ObjectAnimator animatorControl = ObjectAnimator.ofFloat(card.getImgView(), "rotationY", -90f, 0f);
                                animatorControl.setDuration(250);
                                animatorControl.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if (amoutOpen == 0){
                                            preCard = card;
                                            amoutOpen += 1;
                                        }else if (amoutOpen == 1){
                                            afterCard = card;
                                            amoutOpen += 1;
                                        }

                                        if (amoutOpen == 2){
                                            if (CheckCard()){
                                                countCorrect += 1;

                                                score += 20;
                                                txtScore.setText(String.valueOf(score));

                                                CheckWin(amountCard);
                                                amoutOpen = 0;
                                            }
                                            else {
                                                ResetCard();
                                            }
                                        }
                                    }
                                });
                                animatorControl.start();
                            }
                        });
                        animatorControl.start();
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flip_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtScore = findViewById(R.id.txtScore);

        CreateTable();

        Handler handler = new Handler();

        // Thực hiện hàm sau 3 giây
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hàm sẽ được gọi sau 3 giây
                StartGame();
            }
        }, 1500);

        AddListennerToCard();
    }
}