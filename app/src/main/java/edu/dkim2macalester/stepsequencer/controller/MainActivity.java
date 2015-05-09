package edu.dkim2macalester.stepsequencer.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.dkim2macalester.stepsequencer.R;
import edu.dkim2macalester.stepsequencer.model.BooleanGridModel;
import edu.dkim2macalester.stepsequencer.model.Instrument;
import edu.dkim2macalester.stepsequencer.model.Song;
import edu.dkim2macalester.stepsequencer.model.Sound;
import edu.dkim2macalester.stepsequencer.view.GridItemAdapter;


public class MainActivity extends ActionBarActivity {

    Context context = this;
    private int size = 16;
    private int tempo = 120;
    private int tempTempo;
    private int tempoMin = 60; //Needed for UI (Seekbar minimum value is always 0)
    private GridView gridView;
    private GridItemAdapter adapter;

    private Instrument instrument = new Instrument();
    private Song song = new Song();
    private BooleanGridModel BGM;
    private static int drumsetColor;

    //DO NOT EVER EVER USE THIS VARIABLE. instead use methods isPlaying and setPlaying - for threadsafety reasons
    private boolean isPlaying = false; //flag to see whether or not the app is currently playing sound
    private boolean muteActivated = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);
        drumsetColor = R.drawable.white_square_pink_filling;

        //Get model data
        BGM = song.getCurrentBGM();

        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new GridItemAdapter(this);
        gridView.setAdapter(adapter);

        instrument.initiateSound();

        instrument.loadDeepHouseKit(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!BGM.isSelected(position)) {
                    v.setBackgroundResource(drumsetColor);
                    adapter.editDrawableID(position, drumsetColor);
                    //for playing sound on grid-cell-touch
                    if (!muteActivated) {
                        Sound s = (Sound) instrument.accessSoundArray().get(BGM.getSample(position));
                        instrument.accessSoundPool().play(s.getSoundResourceId(), 1, 1, 3, 0, 1);
                    }
                } else {
                    v.setBackgroundResource(R.drawable.white_frame);
                    adapter.editDrawableID(position, R.drawable.white_frame);
                }
                BGM = new BooleanGridModel(BGM.getBooleanArray(), position);
                song.setCurrentBGM(BGM); //updates song with new BGM

            }
        });

        final Button play = (Button) findViewById(R.id.play);
        play.setBackgroundResource(R.drawable.play1_no_border);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.setBackgroundResource(R.drawable.pause1_no_border);
                if (!isPlaying()) {
                    Runnable r = new PlayThread();
                    new Thread(r).start();
                    play.setBackgroundResource(R.drawable.pause1_no_border);
                } else {
                    play.setBackgroundResource(R.drawable.play1_no_border);
                    setPlaying(false);
                }
//                v.setBackgroundResource(R.drawable.play1_no_border);
            }
        });

        final Button next = (Button) findViewById(R.id.next_grid);
        next.setVisibility(View.GONE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying()) {
                    BGM = song.getNextBGM();
                    updateAdapter_oneBGM(BGM);
                    fixNavIcons();
                    fixBGMIndexIndicator();
                }
            }
        });



        final Button previous = (Button) findViewById(R.id.previous_grid);
        previous.setVisibility(View.INVISIBLE);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying()) {
                    BGM = song.getPreviousBGM();
                    updateAdapter_oneBGM(BGM);
                    fixNavIcons();
                    fixBGMIndexIndicator();
                }
            }
        });


        final Button add = (Button) findViewById(R.id.add_gridview);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying() && song.getBGMListSize() < 5) {
                    BGM = song.addBGM();
                    updateAdapter_oneBGM(BGM);
                    fixNavIcons();
                    fixBGMIndexIndicator();
                }
            }
        });


        final Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BGM = new BooleanGridModel();
//                song.setCurrentBGM(BGM);
//                updateAdapter_oneBGM(song.getCurrentBGM());
                if (!song.isEmpty()){
                    setPlaying(false);
                    play.setBackgroundResource(R.drawable.play1_no_border);
                    confirmClear(v);
                }
            }
        });


        final Button mute = (Button) findViewById(R.id.mute);
        mute.setBackgroundResource(R.drawable.sound_active1_no_border);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMuteConfig();
                if (muteActivated) {
                    v.setBackgroundResource(R.drawable.sound_inactive1_no_border);
                } else {
                    v.setBackgroundResource(R.drawable.sound_active1_no_border);
                }
            }
        });

        final Button instruments = (Button) findViewById(R.id.instruments);
        instruments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setPlaying(false);
                play.setBackgroundResource(R.drawable.play1_no_border);
                showInstruments(v);
            }
        });

        final Button settings = (Button) findViewById(R.id.music_options);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog yourDialog = new Dialog(context);
                yourDialog.setContentView(R.layout.seekerdialog);
                yourDialog.setTitle("Settings");


                //tempo seeker
                final TextView tempoView = (TextView) yourDialog.findViewById(R.id.currentTempo);
                tempoView.setText(Integer.toString(tempo + tempoMin));
                final SeekBar tempoSetter = (SeekBar)yourDialog.findViewById(R.id.temposeeker);
                tempoSetter.setMax(300);
                tempoSetter.setProgress(tempo + tempoMin);
//                //Maybe add a global tempo holder
                tempoSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onStopTrackingTouch(SeekBar arg0){}
                    @Override
                    public void onStartTrackingTouch(SeekBar arg0){}
                    @Override
                    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2){
                        tempoView.setText(Integer.toString(arg1 + tempoMin));
                        tempTempo = arg1;
                        //tempoSetter.setProgress(arg1 + tempoMin);
                    }
                });
//                SeekBar volume = (SeekBar) findViewById(R.id.volumeseeker);

                Button savedSettings = (Button) yourDialog.findViewById(R.id.setchanged);
                savedSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tempo = tempTempo;
                        yourDialog.dismiss();
                    }
                });
                Button cancelSettings = (Button) yourDialog.findViewById(R.id.setcancel);
                cancelSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yourDialog.dismiss();
                    }
                });
                yourDialog.show();
            }
        });


        final TextView BGMIndex = (TextView) findViewById(R.id.BGMIndex);
        BGMIndex.setText("1/1");


    }

    public void fixBGMIndexIndicator(){
        TextView indexView = (TextView) findViewById(R.id.BGMIndex);
        indexView.setText((song.getCurrentBGMIndex()+1)+"/"+song.getBGMListSize());
    }

    public void fixNavIcons(){
        if (song.getCurrentBGMIndex() == 0){
            if (song.getBGMListSize() == 1){
                findViewById(R.id.next_grid).setVisibility(View.GONE);
                findViewById(R.id.add_gridview).setVisibility(View.VISIBLE);
            }
            else {
                findViewById(R.id.next_grid).setVisibility(View.VISIBLE);
                findViewById(R.id.add_gridview).setVisibility(View.GONE);
            }
            findViewById(R.id.previous_grid).setVisibility(View.INVISIBLE);
        }
        else {
            if (song.getCurrentBGMIndex() == song.getBGMListSize() - 1 && song.getCurrentBGMIndex() != 4){
                findViewById(R.id.next_grid).setVisibility(View.GONE);
                findViewById(R.id.add_gridview).setVisibility(View.VISIBLE);
            }
            else if (song.getCurrentBGMIndex() == song.getBGMListSize()-1) {
                findViewById(R.id.next_grid).setVisibility(View.GONE);
                findViewById(R.id.add_gridview).setVisibility(View.GONE);
            }
            else {
                findViewById(R.id.next_grid).setVisibility(View.VISIBLE);
                findViewById(R.id.add_gridview).setVisibility(View.GONE);
            }
            findViewById(R.id.previous_grid).setVisibility(View.VISIBLE);
        }
    }

    public void switchMuteConfig(){
        muteActivated = !muteActivated;
    }


    public void showInstruments(View view){
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle("Select a Drum Kit")
                .setItems(new String[]{"808 Kit", "Kc Kit","Deep House Kit"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position) {
                        if (position == 0) {
                            drumsetColor = R.drawable.white_square_blue_filling;
                            instrument.load808Kit(MainActivity.this);
                        } else if (position == 1) {
                            drumsetColor = R.drawable.white_square_green_filling;
                            instrument.loadKcKit(MainActivity.this);
                        } else if (position == 2) {
                            drumsetColor = R.drawable.white_square_pink_filling;
                            instrument.loadDeepHouseKit(MainActivity.this);
                        }
                        updateAdapter_allBGMs();
                    }
                })
                .create();
        d.show();
    }

    public void confirmClear(View view){
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle("Clear")
                .setItems(new String[]{"Clear contents of current grid", "Delete this grid", "Delete all grids"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dlg, int position) {
                        if (position == 0) {
                            BGM = new BooleanGridModel();
                            song.setCurrentBGM(BGM);
                            updateAdapter_oneBGM(song.getCurrentBGM());
                        } else if (position == 1) {
                            if (song.getBGMListSize() > 1){
                                song.deleteCurrentBGM();
                                song.setCurrentBGMIndex(song.getCurrentBGMIndex() - 1);
                                BGM = song.getCurrentBGM();
                                updateAdapter_oneBGM(BGM);
                                fixNavIcons();
                                fixBGMIndexIndicator();
//                                song.fixBGMListOrderAfterDeletingElement(int position);
                            }
                            else {//this scneraio assumes people confuse "clear" and "delete" when they have one grid
                                BGM = new BooleanGridModel();
                                song.setCurrentBGM(BGM);
                                updateAdapter_oneBGM(song.getCurrentBGM());
                            }
                        } else if (position == 2) {
                            song = new Song();
                            BGM = new BooleanGridModel();
                            song.setCurrentBGM(BGM);
                            updateAdapter_oneBGM(song.getCurrentBGM());
                            fixNavIcons();
                            fixBGMIndexIndicator();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        d.show();
        //        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
//                .setTitle("Are you sure you want to delete your beat?")
//                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        BGM = new BooleanGridModel();
//                        song.setCurrentBGM(BGM);
//                        updateGridItemAdapter(song.getCurrentBGM());
//                    }
//                })
//                .setNegativeButton("No",null)
//                .create();
//        d.show();
    }


    public void updateAdapter_allBGMs(){
        for (int i = 0; i < song.getBGMListSize(); i++) {
            BGM = song.getBGMByIndex(i);
            updateAdapter_oneBGM(BGM);
        }
    }

    public void updateAdapter_oneBGM(BooleanGridModel bgm){
        for (int i = 0; i < bgm.getBGMSize(); i++){
            if (bgm.isSelected(i)){
                adapter.editDrawableID(i, drumsetColor);
            }
            else {
                adapter.editDrawableID(i, R.drawable.white_frame);
            }
        }
        adapter.notifyDataSetChanged();
    }


    class PlayThread implements Runnable {

        @Override
        public void run() {
            setPlaying(true);
            while (isPlaying()) {
                if (playSong()) break;
            }
        }

        private boolean playSong() {
            for (int k = 0; k < song.getBGMListSize(); k++) { //TODO WORRIED about threadsafety of this code wrt Song
                song.setCurrentBGMIndex(k);
                BGM = song.getCurrentBGM(); //get the kth grid

                switchUiNextGrid();//makes a runnable for updating gridview adapter
                switchUiNavIcons();
                switchUiBGMIndeces();

                if (playGrid(k)) return true; //breaks play loop if the user has pressed pause
            }
            return false;
        }

        private void switchUiBGMIndeces(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fixBGMIndexIndicator();
                }
            });
        }

        private void switchUiNavIcons() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fixNavIcons();
                }
            });
        }

        private void switchUiNextGrid() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateAdapter_oneBGM(BGM);
                }
            });
        }

        private boolean playGrid(final int gridNum) {
            for (int i = 0; i < size; i++){ //looping through beats (aka timestamps/columns)
                if (!isPlaying()) { //breaks play loop if the user has pressed pause
                    return true;
                }
                BGM = song.getBGMByIndex(gridNum); //pulls updated grid if changes have been made
                playBeat(gridNum, i);
            }
            return false;
        }

        private void playBeat(int gridNum, int beatNum) {
            highlightBeat(gridNum, beatNum, true); //true means highlight the beat
            for (int j = 0; j < size; j++) { //looping through samples (aka y-axis/scale)
                if(BGM.isSelected((j * size) + beatNum)){
                    Sound s = (Sound) instrument.accessSoundArray().get(BGM.getSample((j * size) + beatNum));
                    instrument.accessSoundPool().play(s.getSoundResourceId(),1,1,3,0,1); //(binary arguments) left speaker, right speaker, priority, looping, speed of playback
                }
            }
            try{
                Thread.sleep(60000 / ((tempo + tempoMin) * 4)); //tempo (bpm) is converted into milliseconds
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
                        for (int j = 0; j < size; j++) { //looping through samples (aka y-axis)
                            if (!BGM.isSelected((j*size)+beatNum)){
                                if (highlighted) {
                                    adapter.editDrawableID((j*size)+beatNum, R.drawable.grey_square_filled);
                                }
                                else {
                                    adapter.editDrawableID(j*size+beatNum, R.drawable.white_frame);}
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    private synchronized boolean isPlaying(){
        return isPlaying;
    }

    private synchronized void setPlaying(boolean state){
      isPlaying = state;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present (it's not).
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