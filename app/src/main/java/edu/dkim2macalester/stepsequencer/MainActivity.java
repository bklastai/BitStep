package edu.dkim2macalester.stepsequencer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private GridView gridView;
    private SoundPool soundPool;
    private ArrayList<sound> mSounds = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);

        //Set up the gridview
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ButtonAdapter(this));
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        mSounds = new ArrayList<>();

        loadSounds();



        //Set up the gridview logic
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                v.setSelected(!v.isSelected());
                sound s = mSounds.get((position-position%16)/16);
                soundPool.play(s.getSoundResourceId(),1,1,1,0,1);




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