package edu.dkim2macalester.stepsequencer;


import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import edu.dkim2macalester.stepsequencer.model.BooleanGridModel;
import edu.dkim2macalester.stepsequencer.model.Song;


public class MainActivity extends ActionBarActivity {

    private int size = 16;

    private GridView gridView;
    private GridItemAdapter adapter;

    private Song song = new Song();
    private BooleanGridModel BGM;

    private SoundPool soundPool;
    private ArrayList<Sound> mSounds = new ArrayList<>();



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);

        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new GridItemAdapter(this);

        BGM = song.getCurrentBGM();
        gridView.setAdapter(adapter);

        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        loadSounds();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!BGM.isSelected(position)) {
                    v.setBackgroundResource(R.drawable.white_square);
                } else {
                    v.setBackgroundResource(R.drawable.empty_square);
                }
                BGM.setSelected(position);
            }
        });

        final Button next = (Button) findViewById(R.id.next_grid);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGM = song.getNextBGM();
                updateGridview();
            }
        });

        final Button previous = (Button) findViewById(R.id.previous_grid);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGM = song.getPreviousBGM();
                updateGridview();
            }
        });
    }

    private void loadSounds() {
        Sound s = new Sound();
        s.setDescription("kick");
        s.setSoundResourceId(soundPool.load(this,R.raw.kick,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clap");
        s.setSoundResourceId(soundPool.load(this,R.raw.clap,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(this,R.raw.snare,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("rim");
        s.setSoundResourceId(soundPool.load(this,R.raw.rim,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cowbell");
        s.setSoundResourceId(soundPool.load(this,R.raw.cowbell,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_closed");
        s.setSoundResourceId(soundPool.load(this,R.raw.hhclosed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_open");
        s.setSoundResourceId(soundPool.load(this,R.raw.hhopen,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_low");
        s.setSoundResourceId(soundPool.load(this,R.raw.congalo,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_med");
        s.setSoundResourceId(soundPool.load(this,R.raw.congamed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.congahi,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_lo");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomlo,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_med");
        s.setSoundResourceId(soundPool.load(this,R.raw.tommed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomhi,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_1");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal1,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_2");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal2,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_3");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal3,1));
        mSounds.add(s);
    }

    public void play(View v){
        for (BooleanGridModel bgm : song.getBGMList()){
            BGM = bgm;
            for (int i = 0; i < size; i++){ //looping through beats (aka timestamps/columns)

                for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
                    if(BGM.isSelected((j*size)+i)){
                        Sound s = mSounds.get(BGM.getSample((j*size)+i));
                        soundPool.play(s.getSoundResourceId(),1,1,1,0,1);
                    }
                }
                try{
                    Thread.sleep(125);
                } catch(InterruptedException e){
                    System.out.println("Interrupted");
                }

            }
        }
        BGM = song.getCurrentBGM();
    }

    public void onClickInstruments(View arg0){
        Button instruments = (Button)findViewById(R.id.instruments);
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupInstrumList = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow( popupInstrumList,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(findViewById(R.id.squareLayout),0, instruments.getWidth(), 0);
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

    public void updateGridview(){
        for (int i=0; i<BGM.getBGMSize(); i++){
            if (BGM.isSelected(i)){
                adapter.editDrawableID(i, R.drawable.white_square);
            }
            else {
                adapter.editDrawableID(i, R.drawable.empty_square);
            }
        }
        adapter.notifyDataSetChanged();
    }

}