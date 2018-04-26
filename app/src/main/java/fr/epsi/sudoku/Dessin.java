package fr.epsi.sudoku;

import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by mcroteau on 25/04/2018.
 */

public class Dessin extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public float tailleGrille;
    public float tailleCellule;
    List<Point> enable = new ArrayList();


    public Dessin(Context context, AttributeSet attribute) {
        super(context, attribute);
    }

    @Override
    protected  void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        tailleGrille = w;
        tailleCellule = w/9f;
    }

    @Override
    public void onDraw(Canvas canvas) {

        paint.setTextSize(65);
        for (int i=0; i<=9; i++) {
            if (i%3 == 0){
                paint.setStrokeWidth(8);
            } else {
                paint.setStrokeWidth(5);
            }
            canvas.drawLine(i*tailleCellule, 0, i*tailleCellule, tailleCellule*9, paint);
            canvas.drawLine(0, i*tailleCellule, tailleCellule*9, i*tailleCellule, paint);
        }

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(Sudoku.monSudo[i][j] != 0){
                    String s = "" + Sudoku.monSudo[i][j];
                    canvas.drawText(s, (j*tailleCellule)+25, ((i+1) * tailleCellule)-18, paint);
                }else{
                    enable.add(new Point(i,j));
                }

                if(Sudoku.monSudo[i][j] != 0 && enable.contains(new Point(i,j)) ){
                    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint2.setTextSize(65);
                    if(!Sudoku.check(i,j,Sudoku.monSudo)){
                        paint2.setColor(Color.RED);
                    }else {
                        paint2.setColor(Color.GREEN);
                    }
                 canvas.drawText(String.valueOf(Sudoku.monSudo[i][j]), (j*tailleCellule)+25, ((i+1) * tailleCellule)-18, paint2);

                }
            }
        }
        if(allGreen(Sudoku.monSudo)){
            Sudoku.win.setText("Bravo ! Vous avez gagné");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!allGreen(Sudoku.monSudo)){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int column = (int)(event.getX() / tailleCellule);
                int row = (int)(event.getY() / tailleCellule);
                int valeur = Sudoku.selectvaleur;

                if(!enable.contains(new Point(row,column))){
                    Log.e("Interdit", valueOf(row));
                }else if(valeur != 0){
                    Sudoku.monSudo[row][column] = valeur;
                }

                // Log.e("COLUMN", String.valueOf(column));
                // Log.e("ROW", String.valueOf(row));

                invalidate();
            }
            Log.d("if",String.valueOf(notComplete(Sudoku.monSudo) && !allGreen(Sudoku.monSudo)));
            Log.d("Complet ", String.valueOf(notComplete(Sudoku.monSudo)));
            Log.d("Green ", String.valueOf(allGreen(Sudoku.monSudo)));

        } return true;
    }

    public static boolean notComplete(int[][] grid){
        for(int i = 0; i<9;i++){
            for(int j = 0; j<9; j++){
                if (grid[i][j] == 0){
                    return true;
                }
            }
        } return false;
    }

    public static boolean allGreen(int[][] grid){
        if(!notComplete(grid)){
            for(int i = 0; i<9;i++){
                for(int j = 0; j<9; j++){
                    if (!Sudoku.check(i,j,grid)){
                        return false;
                    }
                }
            }   return true;
        }else{
            return false;
        }
    }

}
