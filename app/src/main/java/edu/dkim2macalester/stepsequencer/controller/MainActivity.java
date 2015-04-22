package edu.dkim2macalester.stepsequencer.controller;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import edu.dkim2macalester.stepsequencer.R;
import edu.dkim2macalester.stepsequencer.model.BooleanGridModel;
import edu.dkim2macalester.stepsequencer.model.Song;
import edu.dkim2macalester.stepsequencer.model.Sound;
import edu.dkim2macalester.stepsequencer.view.GridItemAdapter;


public class MainActivity extends ActionBarActivity {

    private int size = 16;

    private GridView gridView;
    private GridItemAdapter adapter;

    private Song song = new Song();
    private BooleanGridModel BGM;

    private SoundPool soundPool;
    private ArrayList<Sound> mSounds = new ArrayList<>();

    private boolean isPlaying = false; //flag to see whether or not the app is currently playing sound


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        enableStrictMode(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);

        //Get model data
        BGM = song.getCurrentBGM();

        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new GridItemAdapter(this);
        gridView.setAdapter(adapter);

        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        loadSounds();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!BGM.isSelected(position)) {
                    v.setBackgroundResource(R.drawable.black_square);
                    adapter.editDrawableID(position, R.drawable.black_square);
                } else {
                    v.setBackgroundResource(R.drawable.empty_square);
                    adapter.editDrawableID(position, R.drawable.empty_square);
                }
                BGM = BGM.setSelected(position);
                song.setCurrentBGM(BGM); //updates song with new BGM
            }
        });

        final Button next = (Button) findViewById(R.id.next_grid);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGM = song.getNextBGM();
                updateGridItemAdapter(BGM);
            }
        });

        final Button previous = (Button) findViewById(R.id.previous_grid);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGM = song.getPreviousBGM();
                updateGridItemAdapter(BGM);
            }
        });

        final Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (BooleanGridModel bgm : song.getBGMList()){
                    BGM = bgm;
                    updateGridItemAdapter(BGM);
                    play(BGM);
                }
                updateGridItemAdapter(song.getCurrentBGM());
            }
        });

        final Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (BooleanGridModel bgm : song.getBGMList()){
                    BGM = new BooleanGridModel();
                    updateGridItemAdapter(BGM);
                    song.setCurrentBGM(BGM);
                }
                updateGridItemAdapter(song.getCurrentBGM());
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

    public void play(BooleanGridModel bgm){
        Runnable r = new PlayThread(bgm);
        if(!isPlaying) {
            new Thread(r).start();
        } else {
            isPlaying = !isPlaying;
        }
    }



    public void updateGridItemAdapter(BooleanGridModel bgm){
        for (int i = 0; i < bgm.getBGMSize(); i++){
            if (bgm.isSelected(i)){
                adapter.editDrawableID(i, R.drawable.black_square);
            }
            else {
                adapter.editDrawableID(i, R.drawable.empty_square);
            }
        }
        adapter.notifyDataSetChanged(); //only needed if we add new data..
        gridView.invalidateViews();
    }

    public void selectRow(int i, BooleanGridModel bgm) {
        for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
            if (!bgm.isSelected((j*size)+i)){
                adapter.editDrawableID((j*size)+i, R.drawable.grey_square);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void deselectRow(int i, BooleanGridModel bgm) {
        for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
            if(!bgm.isSelected((j*size)+i)){
                adapter.editDrawableID(j*size+i, R.drawable.empty_square);
            }
        }
        adapter.notifyDataSetChanged();
    }

    class PlayThread implements Runnable {
        private final BooleanGridModel bgm;

        public PlayThread (BooleanGridModel bgm) {
            this.bgm = bgm;
        }

        @Override
        public void run() {
            isPlaying = true;
            while (isPlaying) {
                //TODO: see if the thing android has here is actually important and necessary, because I have no idea what it does
                for (int i = 0; i < size; i++){ //looping through beats (aka timestamps/columns)
                    if (!isPlaying) { break; } //breaks play loop
                    final int rowToUpdate = i;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectRow(rowToUpdate, bgm);
                        }
                    });

                    for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)

                        if(bgm.isSelected((j*size)+i)){
                            Sound s = mSounds.get(bgm.getSample((j*size)+i));
                            soundPool.play(s.getSoundResourceId(),1,1,1,0,1);//(binary arguments) left speaker, right speaker, priority, looping, speed of playback
                        }
                    }
                    //if (!isPlaying) { break; }
                    try{
                        Thread.sleep(125);
                    } catch(InterruptedException e){
                        System.out.println("Interrupted");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            deselectRow(rowToUpdate, bgm);
                        }
                    });

                }
            }
        }
    }

//    public void onClickInstruments(View arg0){
//        Button instruments = (Button)findViewById(R.id.instruments);
//        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupInstrumList = layoutInflater.inflate(R.layout.popup, null);
//        final PopupWindow popupWindow = new PopupWindow( popupInstrumList,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.showAtLocation(findViewById(R.id.squareLayout),0, instruments.getWidth(), 0);
//    }

    //TODO: remove if possible
    public static void enableStrictMode(Context context) {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()
                        .penaltyLog()
                        .build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .penaltyLog()
                        .build());
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