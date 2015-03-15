package edu.dkim2macalester.stepsequencer;

import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class Main_Activity extends ActionBarActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);

        //Set up the gridview
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ButtonAdapter(this));

        //Set up the gridview logic
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                v.setSelected(!v.isSelected());
            }
        });


    }

//    public void play(Button v){
//
//    }

    //PLAY button should take a method call from this place
    // it should call gridView.getAdapter().
    // The adapter of gridView is ButtonAdapter, a class in which we have methods for gridView length
    // getItem, etc. Those should be the main items needed to figure out the
    // buttons pressed by reiterating over the entire gridView length.


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