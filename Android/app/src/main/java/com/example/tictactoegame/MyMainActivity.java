package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Observable;
import java.util.Observer;


import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public class MyMainActivity extends AppCompatActivity implements Observer{
    private MyModel myModel = new MyModel();  // Create model
    private MyController myController = new MyController(myModel);

    // this function will be called every time a
    // players tap in an empty box of the grid
    public void playerTap(View view) {
        if (myModel.getGameActive() == false){
            gameReset();
            myModel.callReset();
            if (myModel.getPlayerFirst() == false){
                int move = myController.computerMove();
                myModel.updateGameState(move, 1);
                myModel.processMoveDone(move, -1, true, "compMove1");
            }
            return;
        }
        ImageView img1 = (ImageView) view;
        int tappedImage = Integer.parseInt(img1.getTag().toString());

        // tell the model of user's move
        myController.processMove(tappedImage);
    }

    public void scoreReset(View view){
        TextView status = (TextView) view;
        myModel.setPlayerScore(0);
        myModel.setCompScore(0);
        status.setText("0 - 0");
    }

    public void updateScore(){
        TextView status = findViewById(R.id.score);
        String pScore = String.valueOf(myModel.getplayerScore());
        String cScore = String.valueOf(myModel.getCompScore());
        String score = pScore + " - " + cScore;
        status.setText(score);
    }

    // reset the game
    public void gameReset() {
        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("Tap grid to play (X)\nTap score to reset\nStarting player switches by round");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myModel.addObserver(this);
        myController.addObserver(this);
    }

    private @IdRes int getImageViewId(int id) {
        int res = R.id.imageView0;
        switch (id) {
            case 0: res = R.id.imageView0;
                break;
            case 1: res = R.id.imageView1;
                break;
            case 2: res = R.id.imageView2;
                break;
            case 3: res = R.id.imageView3;
                break;
            case 4: res = R.id.imageView4;
                break;
            case 5: res = R.id.imageView5;
                break;
            case 6: res = R.id.imageView6;
                break;
            case 7: res = R.id.imageView7;
                break;
            case 8: res = R.id.imageView8;
                break;
        }
        return res;
    }

    @Override
    public void update(Observable observable, Object o) {
        UpdateInfo updateInfo = (UpdateInfo) o;

        if (updateInfo.statement.equals("compMove1")){
            ImageView img1 = findViewById(getImageViewId(updateInfo.imageId));
            img1.setTranslationY(-1000f);
            img1.setImageResource(R.drawable.o);
            img1.animate().translationYBy(1000f).setDuration(300);
            return;
        }

        // check object whether the game is over, if so, update the UI
        if (updateInfo.playerMoveDone == true){
            ImageView img1 = findViewById(getImageViewId(updateInfo.imageId));
            img1.setTranslationY(-1000f);
            img1.setImageResource(R.drawable.x);
            img1.animate().translationYBy(1000f).setDuration(300);

            TextView status = findViewById(R.id.status);
            status.setText(updateInfo.statement);
        } else {
            ImageView img1 = findViewById(getImageViewId(updateInfo.imageId));
            img1.setTranslationY(-1000f);
            img1.setImageResource(R.drawable.x);
            img1.animate().translationYBy(1000f).setDuration(300);

            ImageView img2 = findViewById(getImageViewId(updateInfo.imageId2));
            img2.setTranslationY(-1000f);
            img2.setImageResource(R.drawable.o);
            img2.animate().translationYBy(1000f).setDuration(300);

            TextView status = findViewById(R.id.status);
            status.setText(updateInfo.statement);
        }

        updateScore();
    }
}