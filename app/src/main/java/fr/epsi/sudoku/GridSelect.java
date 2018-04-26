package fr.epsi.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridSelect extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_select);

        Intent intent = getIntent();
        final int level = intent.getIntExtra("Niveau",0);

        ListView gridList = (ListView) findViewById(R.id.GridList);
        List<Grid> myGrid = genererDoGrilles(level);

        GridAdapter adapter = new GridAdapter(GridSelect.this, myGrid);
        gridList.setAdapter(adapter);

        gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GridSelect.this, Sudoku.class);
                intent.putExtra("Niveau", level);
                intent.putExtra("Grille",i);
                startActivity(intent);
            }
        });
    }

    private List<Grid> genererDoGrilles(int lvl){
        List<Grid> doGrilles = new ArrayList<Grid>();
        for(int i = 0 ; i < 100; i++) {
            doGrilles.add(new Grid(lvl, i+1));
        }
        return doGrilles;
    }
}
