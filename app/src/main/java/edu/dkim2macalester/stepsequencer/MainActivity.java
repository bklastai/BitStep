package edu.dkim2macalester.stepsequencer;

<<<<<<< HEAD
import android.content.Context;
=======
import android.media.AudioManager;
import android.media.SoundPool;
>>>>>>> origin/master
import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.widget.ListAdapter;

import java.util.ArrayList;
>>>>>>> origin/master

public class MainActivity extends ActionBarActivity {

    private GridView gridView;
<<<<<<< HEAD
    private int numberOfGrids = 1; //change this value when adding new gridviews (to the right of the screen)
    private boolean[] selection = new boolean[numberOfGrids*256];
=======
    private SoundPool soundPool;
    private ArrayList<sound> mSounds = null;

>>>>>>> origin/master

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set selection to false for all grid cells
        initSelection();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);


        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
<<<<<<< HEAD
        gridView.setAdapter(new GridItemAdapter(this));

<<<<<<< HEAD
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
=======
        //set up the binary array
        binArray = new boolean[256];
        for (int i = 0; i<binArray.length;i++){
            binArray[i]=false;
        }

        //set up the soundPool
=======
        gridView.setAdapter(new ButtonAdapter(this));
>>>>>>> parent of bfbcaa4... Sound plays when play button is pressed
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        mSounds = new ArrayList<>();

        loadSounds();



        //Set up the gridview logic
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                v.setSelected(!v.isSelected());
                sound s = mSounds.get((position-position%16)/16);
                soundPool.play(s.getSoundResourceId(),1,1,1,0,1);




<<<<<<< HEAD
                binArray[position]=!binArray[position];
>>>>>>> origin/master
=======
>>>>>>> parent of bfbcaa4... Sound plays when play button is pressed
            }
        });


    }

    private void loadSounds() {
        sound s = new sound();
        s.setDescription("Kick");
        s.setSoundResourceId(soundPool.load(this,R.raw.kick,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("clap");
        s.setSoundResourceId(soundPool.load(this,R.raw.clap,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(this,R.raw.snare,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("rim");
        s.setSoundResourceId(soundPool.load(this,R.raw.rim,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("cowbell");
        s.setSoundResourceId(soundPool.load(this,R.raw.cowbell,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("hh closed");
        s.setSoundResourceId(soundPool.load(this,R.raw.hhclosed,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("hh open");
        s.setSoundResourceId(soundPool.load(this,R.raw.hhopen,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("conga low");
        s.setSoundResourceId(soundPool.load(this,R.raw.congalo,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("conga med");
        s.setSoundResourceId(soundPool.load(this,R.raw.congamed,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("conga hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.congahi,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("tom lo");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomlo,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("tom med");
        s.setSoundResourceId(soundPool.load(this,R.raw.tommed,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("tom hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomhi,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("cymbal 1");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal1,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("cymbal 2");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal2,1));
        mSounds.add(s);

        s = new sound();
        s.setDescription("cymbal 3");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal3,1));
        mSounds.add(s);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======

    public void play(View v){
//        for (int i = 0; i<16; i++){
//
//        }
        for (int i=0;i<binArray.length;i++){
            if (binArray[i]){
                sound s = mSounds.get(15-((i-i%16)/16));
                soundPool.play(s.getSoundResourceId(),1,1,1,0,1);
            }
        }
    }
>>>>>>> origin/master

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

=======
//    public void play(Button v){
//
//    }
>>>>>>> parent of bfbcaa4... Sound plays when play button is pressed


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