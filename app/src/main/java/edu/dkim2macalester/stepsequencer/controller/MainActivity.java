package edu.dkim2macalester.stepsequencer.controller;



import android.content.pm.ActivityInfo;
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



import edu.dkim2macalester.stepsequencer.R;
import edu.dkim2macalester.stepsequencer.model.BooleanGridModel;
import edu.dkim2macalester.stepsequencer.model.Instrument;
import edu.dkim2macalester.stepsequencer.model.Song;
import edu.dkim2macalester.stepsequencer.model.Sound;
import edu.dkim2macalester.stepsequencer.view.GridItemAdapter;


public class MainActivity extends ActionBarActivity {

    private int size = 16;
    private int tempo = 120;

    private GridView gridView;
    private GridItemAdapter adapter;

    private Instrument instrument = new Instrument();
    private Song song = new Song();
    private BooleanGridModel BGM;

//    public SoundPool soundPool;
//    public ArrayList<Sound> mSounds = new ArrayList<>();

    //DO NOT EVER EVER USE THIS VARIABLE. instead use methods checkPlaying and setPlaying - for threadsafety reasons
    private boolean isPlaying = false; //flag to see whether or not the app is currently playing sound

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);

        //Get model data
        BGM = song.getCurrentBGM();

        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new GridItemAdapter(this);
        gridView.setAdapter(adapter);

//        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        instrument.initiateSound();
//        loadSounds();
        instrument.loadDeepHouseKit(this);

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

                //for playing sound on touch
                Sound s = (Sound) instrument.accessSoundArray().get(BGM.getSample(position));
//              Sound s = mSounds.get(bgm.getSample((j*size)+i));
                instrument.accessSoundPool().play(s.getSoundResourceId(),1,1,3,0,1);
//                Sound s = mSounds.get(BGM.getSample(position));
//                soundPool.play(s.getSoundResourceId(),1,1,1,0,1);//(binary arguments) left speaker, right speaker, priority, looping, speed of playback

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
                play();
            }
        });

        final Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGM = new BooleanGridModel();
                updateGridItemAdapter(BGM);
                song.setCurrentBGM(BGM);
                updateGridItemAdapter(song.getCurrentBGM());
            }
        });
    }

    //TODO delete all?
//    private void loadSounds() {
//        Sound s = new Sound();
//        s.setDescription("kick");
//        s.setSoundResourceId(soundPool.load(this,R.raw.kick,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("clap");
//        s.setSoundResourceId(soundPool.load(this,R.raw.clap,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("snare");
//        s.setSoundResourceId(soundPool.load(this,R.raw.snare,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("rim");
//        s.setSoundResourceId(soundPool.load(this,R.raw.rim,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("cowbell");
//        s.setSoundResourceId(soundPool.load(this,R.raw.cowbell,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("hh_closed");
//        s.setSoundResourceId(soundPool.load(this,R.raw.hhclosed,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("hh_open");
//        s.setSoundResourceId(soundPool.load(this,R.raw.hhopen,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("conga_low");
//        s.setSoundResourceId(soundPool.load(this,R.raw.congalo,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("conga_med");
//        s.setSoundResourceId(soundPool.load(this,R.raw.congamed,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("conga_hi");
//        s.setSoundResourceId(soundPool.load(this,R.raw.congahi,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("tom_lo");
//        s.setSoundResourceId(soundPool.load(this,R.raw.tomlo,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("tom_med");
//        s.setSoundResourceId(soundPool.load(this,R.raw.tommed,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("tom_hi");
//        s.setSoundResourceId(soundPool.load(this,R.raw.tomhi,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("cymbal_1");
//        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal1,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("cymbal_2");
//        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal2,1));
//        mSounds.add(s);
//
//        s = new Sound();
//        s.setDescription("cymbal_3");
//        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal3,1));
//        mSounds.add(s);
//    }

    public void play(){
        Runnable r = new PlayThread();
        if(!checkPlaying()) {
            new Thread(r).start();
        } else {
            setPlaying(false);
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

    public void colorRow(int i, BooleanGridModel bgm, boolean selected) {
        for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
            if (!bgm.isSelected((j*size)+i)){
                if (selected) {
                    adapter.editDrawableID((j*size)+i, R.drawable.grey_square);
                } else { adapter.editDrawableID(j*size+i, R.drawable.empty_square);}
            }
        }
        adapter.notifyDataSetChanged();
    }

    class PlayThread implements Runnable {
        private BooleanGridModel bgm;

        @Override
        public void run() {
            setPlaying(true);

            while (checkPlaying()) {
                if (playSong()) break;
            }

        }

        private boolean playSong() {
            for (int k = 0; k < song.getBGMListSize(); k++) { //TODO WORRIED about threadsafety of this code wrt Song
                bgm = song.getBGMFromIndex(k); //get the kth grid
                song.setCurrentBGMByIndex(k);

                switchUiNextGrid();

                if (playGrid(k)) return true;
                //bgm = song.getNextBGM();
            }
            return false;
        }

        private void switchUiNextGrid() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateGridItemAdapter(bgm);
                }
            });
        }

        private boolean playGrid(final int gridNum) {
            for (int i = 0; i < size; i++){ //looping through beats (aka timestamps/columns)
                if (!checkPlaying()) { //breaks play loop if the user has pressed pause
                    return true;
                }

                bgm = song.getBGMFromIndex(gridNum); //pulls updated grid if changes have been made
                playBeat(gridNum, i);
            }
            return false;
        }

        private void playBeat(int gridNum, int beatNum) {
            highlightBeat(gridNum, beatNum, true); //true means highlight the beat

            for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
                if(bgm.isSelected((j * size) + beatNum)){
                    Sound s = (Sound) instrument.accessSoundArray().get(bgm.getSample((j * size) + beatNum));
//                                Sound s = mSounds.get(bgm.getSample((j*size)+i));
                    instrument.accessSoundPool().play(s.getSoundResourceId(),1,1,3,0,1); //(binary arguments) left speaker, right speaker, priority, looping, speed of playback
//                                soundPool.play(s.getSoundResourceId(),1,1,1,0,1);
                }
            }

            try{
                Thread.sleep(60000 / (tempo * 4)); //tempo (bpm) is converted into milliseconds
            } catch(InterruptedException e){
                System.out.println("Interrupted");
            }

            highlightBeat(gridNum, beatNum, false); //false means unhighlight the beat
        }

        private void highlightBeat(final int gridNum, final int beatNum, final boolean highlighted) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (song.getCurrentBGMIndex() == gridNum) {
                        colorRow(beatNum, bgm, highlighted);
                    }
                }
            });
        }
    }

    private synchronized boolean checkPlaying(){
        return isPlaying;
    }

    private synchronized void setPlaying(boolean state){
      isPlaying = state;
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