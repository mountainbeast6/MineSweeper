package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameBoard {
    private Texture fixed = new Texture("EmptyTile.png");
    private boolean firstclick = true;
    private Texture emptyTile;
    private Texture questionTile;
    private Texture bombTile;
    private Texture numberTile;
    private Texture flaggedTile;
    private int mouseX;
    private int mouseY;
    private static final int BOMB=9, EMPTYTILE=1,FLAGGEDTILE=2,QUESTIONTILE=3;
    private int [] [] board;
    public GameBoard(){
        board = new int[16][30];
        initEmptyBoard();
        emptyTile= new Texture("BlankNull.png");
        bombTile=new Texture("Bomb.png");
    }
    public boolean isValidLoc(int row, int col){
        return row>=0&&row<board.length&&col>=0&&col<board[0].length;
    }
    private void placeBombs(int rowClicked, int colClicked){
        int bombCount = 0;
        while(bombCount<99){
            int randomRow=(int)(Math.random()* board.length);
            int randomCol=(int)(Math.random()*board.length);
            if(randomRow!=rowClicked&&randomCol!=colClicked){
                if(board[randomRow][randomCol]%10!=9){
                    board[randomRow][randomCol]=19;
                    bombCount++;
                }
            }
        }
    }
    private int[] getNeighbors(int row, int col){
        int[]storage= new int[8];
        int counter = 0;
        for(int i =-1; i<2;i++){
            for(int j=-1; j<2;j++){
                if(isValidLoc(row+i,col+j)){
                    storage[counter]=board[row+i][row+j];
                    counter++;
                }
            }
        }
        return storage;
    }
    public void handleClick(int x, int y) {
        //change windows (x,y) coordinate to 2D array loc
        int rowClicked = (y-10)/20;
        int colClicked = (x-10)/20;

        if(firstclick) {
            if (isValidLoc(rowClicked, colClicked)) {
                board[rowClicked][colClicked] = board[rowClicked][colClicked]%10;
            }
            placeBombs(rowClicked,colClicked);
            firstclick=false;
        }
        if (isValidLoc(rowClicked, colClicked)) {
            board[rowClicked][colClicked] = board[rowClicked][colClicked]%10;
        }
    }
    private Texture getTexture(int val){
        int cover=val/10;
        int number=val%10;
        if(cover==EMPTYTILE){
            return emptyTile;
        }
        if (cover==FLAGGEDTILE){
            return emptyTile;
        }
        if (cover==QUESTIONTILE){
            return emptyTile;
        }
        if (number==9){
            return bombTile;
        }
        return fixed;
    }
    public void draw(SpriteBatch spriteBatch){
        for(int row=0; row < board.length; row++) {
            for(int col=0; col < board[row].length; col++) {
                spriteBatch.draw(getTexture(board[row][col]), (10) + (col*20),(600-35) - (row * 20));
            }
        }
    }
    private void initEmptyBoard(){
        for(int i=0;i< board.length;i++){
            for(int j=0;j<board[i].length;j++){
                board[i][j]=10;
            }
        }
    }
}