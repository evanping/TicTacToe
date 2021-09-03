package com.example.tictactoegame;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

// MAKE OPTION TO CHANGE STARTING PLAYER

public class MyController extends Observable {
    MyModel myModel;

    public MyController(MyModel m) {
        myModel = m;
    }

    public void processMove(int index){
        if (myModel.getGameState(index) != 2) {return;}
        myModel.updateGameState(index, 0);
        String winStatement = checkWin();
        if (!winStatement.equals("")){
            myModel.setGameActive(false);
            myModel.processMoveDone(index, -1, true, winStatement);
        } else {
            int move = computerMove();
            myModel.updateGameState(move, 1);
            winStatement = checkWin();
            myModel.processMoveDone(index, move, false, winStatement);
        }
    }

    public int computerMove() {
        int move = 0;
        boolean foundmove = false;
        for (int[] winPosition : myModel.getWinPositions()) { // Find winning position
            if (myModel.getGameState(winPosition[0]) == 1 && myModel.getGameState(winPosition[0]) == myModel.getGameState(winPosition[1]) && myModel.getGameState(winPosition[2]) == 2) {
                //choices.add(winPosition[2]);
                move = winPosition[2];
                foundmove = true;
                break;
            } else if (myModel.getGameState(winPosition[1]) == 1 && myModel.getGameState(winPosition[1]) == myModel.getGameState(winPosition[2]) && myModel.getGameState(winPosition[0]) == 2) {
                //choices.add(winPosition[0]);
                move = winPosition[0];
                foundmove = true;
                break;
            } else if (myModel.getGameState(winPosition[0]) == 1 && myModel.getGameState(winPosition[0]) == myModel.getGameState(winPosition[2]) && myModel.getGameState(winPosition[1]) == 2) {
                //choices.add(winPosition[1]);
                move = winPosition[1];
                foundmove = true;
                break;
            }
        }
        if (foundmove == false) { // Find defensive position
            for (int[] winPosition : myModel.getWinPositions()) { // Find winning position
                if (myModel.getGameState(winPosition[0]) == 0 && myModel.getGameState(winPosition[0]) == myModel.getGameState(winPosition[1]) && myModel.getGameState(winPosition[2]) == 2) {
                    //choices.add(winPosition[2]);
                    move = winPosition[2];
                    foundmove = true;
                    break;
                } else if (myModel.getGameState(winPosition[1]) == 0 && myModel.getGameState(winPosition[1]) == myModel.getGameState(winPosition[2]) && myModel.getGameState(winPosition[0]) == 2) {
                    //choices.add(winPosition[0]);
                    move = winPosition[0];
                    foundmove = true;
                    break;
                } else if (myModel.getGameState(winPosition[0]) == 0 && myModel.getGameState(winPosition[0]) == myModel.getGameState(winPosition[2]) && myModel.getGameState(winPosition[1]) == 2) {
                    //choices.add(winPosition[1]);
                    move = winPosition[1];
                    foundmove = true;
                    break;
                }
            }
        }
        if (foundmove == false) {
            while (true){
                int i = (int) (Math.random() * 9);
                if (myModel.getGameState(i) == 2) {
                    return i;
                }
            }
        }
        return move;
    }

    public String checkWin() {
        String winnerStr = "";
        // Check if any player has won
        for (int[] winPosition : myModel.getWinPositions()) {
            if (myModel.getGameState(winPosition[0]) == myModel.getGameState(winPosition[1]) &&
                    myModel.getGameState(winPosition[1]) == myModel.getGameState(winPosition[2]) &&
                    myModel.getGameState(winPosition[0]) != 2) {

                // game reset function be called
                myModel.setGameActive(false);
                if (myModel.getGameState(winPosition[0]) == 0) {
                    winnerStr = "X has won - Tap grid to replay";
                    myModel.playerScoreUp();
                } else {
                    winnerStr = "O has won - Tap grid to replay";
                    myModel.compScoreUp();
                }
                return winnerStr;
            }
        }

        // set the status if the match draw
        if (myModel.getCounter() == 9) {
            myModel.setGameActive(false);
            return "Draw - Tap grid to replay";
        }
        return winnerStr;
    }
}
