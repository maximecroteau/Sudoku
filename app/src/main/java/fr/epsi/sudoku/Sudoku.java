package fr.epsi.sudoku;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Sudoku extends AppCompatActivity {
    public static int[][] monSudo;
    public static int selectvaleur;
    public static TextView win;
    Sudoku context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        win = findViewById(R.id.win);

        Intent intent = getIntent();
        int level = intent.getIntExtra("Niveau", 0);
        int grille = intent.getIntExtra("Grille", 0);

        monSudo = getSudo(level, grille);
        context = this;

        for(int i = 1; i < 10; i++){
            int val = getResources().getIdentifier("val"+i, "id", getPackageName());
            TextView tv = (TextView)findViewById(val);
            tv.setOnClickListener(myClick);
        }


    }

    View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            TextView tv = (TextView)context.findViewById(v.getId());
            selectvaleur = Integer.parseInt(tv.getText().toString());

            for(int j = 1 ; j < 10 ; j++) {
                int val = context.getResources().getIdentifier("val" + j, "id", getPackageName());
                TextView tvw = (TextView)findViewById(val);
                tvw.setBackgroundColor(Color.TRANSPARENT);
            }
            Log.e("valeur :", String.valueOf(selectvaleur));
            tv.setBackgroundColor(Color.BLUE);
        }
    };

    private int[][] getSudo(int lvl, int num){
        int[][] Sudo = new int[9][9];
        String exemple = getLigne(lvl,num);
        String[] exempleArray = exemple.split("");
        int k = 1;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                Sudo[i][j] = Integer.parseInt(exempleArray[k]);
                k++;
            }
        }
        return Sudo;
    }

    private String getLigne(int lvl, int num) {
        int fileResourceId;
        if (lvl == 1){
            fileResourceId = R.raw.niveau1;
        } else {
            fileResourceId = R.raw.niveau2;
        }
        InputStream is = this.getResources().openRawResource(fileResourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str="";
        String ligne="";
        int i = 0;

        try {
            while ((str = reader.readLine()) != null) {
                if ( i == num){
                    ligne = str;
                }
                i++;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } return ligne;
    }

    public static boolean check(int i, int j, int[][] grid) {
        // Check whether grid[i][j] is valid at the i's row
        for (int column = 0; column < 9; column++)
            if (column != j && grid[i][column] == grid[i][j])
                return false;
        // Check whether grid[i][j] is valid at the j's column
        for (int row = 0; row < 9; row++)
            if (row != i && grid[row][j] == grid[i][j])
                return false;
        // Check whether grid[i][j] is valid in the 3 by 3 box
        for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++)
            for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++)
                if (row != i && col != j && grid[row][col] == grid[i][j])
                    return false;
        return true; // The current value at grid[i][j] is valid
    }

}
