package edu.dkim2macalester.stepsequencer;

import android.content.Context;
import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private GridView gridView;
    private int numberOfGrids = 1; //change this value when adding new gridviews (to the right of the screen)
    private boolean[] selection = new boolean[numberOfGrids*256];

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set selection to false for all grid cells
        initSelection();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);


        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridItemAdapter(this));

        //Set up the OnClickListener; change BackgroundResource and set notes 'selection' accordingly
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!selection[position]) {
                    v.setBackgroundResource(R.drawable.black_square);
                    selection[position] = true;
                }
                else {
                    v.setBackgroundResource(R.drawable.empty_square);
                    selection[position] = false;
                }
            }
        });


    }


    public void playMusic(View v){
        String selectedItems = "";
        for (int i = 0; i<selection.length; i++){
            if (selection[i]){
                selectedItems = selectedItems + i + ", ";
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Grid Items Selected: " + selectedItems, Toast.LENGTH_SHORT);
        toast.show();
    }



    public void initSelection(){
        for (int i=0;i<selection.length;i++){
            selection[i]= false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid__layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}