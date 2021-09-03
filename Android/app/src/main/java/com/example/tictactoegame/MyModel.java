package com.example.tictactoegame;
import android.widget.TextView;

import java.util.*;

enum State {
    ACTIVE, INACTIVE
}

class UpdateInfo {
    State state;
    Integer imageId;
    Integer imageId2;
    boolean playerMoveDone;
    String statement = "";

}

public class MyModel extends Observable{
    boolean playerFirst = true; // true is user; false is comp

    public boolean getPlayerFirst(){
        return playerFirst;
    }

    public void switchPlayerFirst(){
        playerFirst = !playerFirst;
    }

    boolean gameActive = true;

    public void setGameActive(boolean active){
        gameActive = active;
    }

    public boolean getGameActive(){
        return gameActive;
    }

    public void callReset(){
        reset();
    }


    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    private void reset() {
        setGameActive(true);
        counter = 0;
        switchPlayerFirst();
        for (int i = 0; i < 9; i++) {
            gameState[i] = 2;
        }
    }

    public void updateGameState(int index, int player){
        // if the tapped image is empty
        //if (gameState[index] == 2) {
        counter++;
        gameState[index] = player;
        //}
    }

    public void processMoveDone(int playerMove, int compMove, boolean playerMoveDone, String endStatement){
        UpdateInfo updateInfo = new UpdateInfo();
        if (endStatement.equals("compMove1")){ // EXCEPTION FOR COMPUTER 1ST MOVE
            updateInfo.imageId = playerMove;
            updateInfo.statement = "compMove1";
            setChanged();
            notifyObservers(updateInfo);
            return;
        }

        if (endStatement.equals("")){
            //updateInfo.state = State.ACTIVE;
            updateInfo.imageId = playerMove;
            updateInfo.imageId2 = compMove;
            setChanged();
            notifyObservers(updateInfo);
            return;
        }
        if (playerMoveDone == true){
            //updateInfo.state = State.INACTIVE;
            updateInfo.imageId = playerMove;
            updateInfo.playerMoveDone = true;
            updateInfo.statement = endStatement;
            setChanged();
            notifyObservers(updateInfo);
        } else {
            //updateInfo.state = State.INACTIVE;
            updateInfo.imageId = playerMove;
            updateInfo.imageId2 = compMove;
            updateInfo.playerMoveDone = false;
            updateInfo.statement = endStatement;
            setChanged();
            notifyObservers(updateInfo);
        }
    }

    public int getGameState(int i){
        return gameState[i];
    }

    // State meanings:
    // 0 - X
    // 1 - O
    // 2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    int counter = 0;

    public int[][] getWinPositions(){
        return winPositions;
    }

    public void increaseCounter(){
        counter++;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int n){
        counter = n;
    }

    int playerScore = 0;
    int compScore = 0;

    public int getplayerScore(){
        return playerScore;
    }

    public int getCompScore(){
        return compScore;
    }

    public void playerScoreUp(){
        playerScore++;
    }

    public void compScoreUp(){
        compScore++;
    }

    public void setPlayerScore(int n){
        playerScore = n;
    }

    public void setCompScore(int n){
        compScore = n;
    }
}
