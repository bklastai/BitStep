package edu.dkim2macalester.stepsequencer.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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


public class MainActivity extends Activity {

    Context context = this;
    private int size = 16;
    private int tempo = 40;
    private int tempTempo;
    private int tempoMin = 80; //Needed for UI (Seekbar minimum value is always 0)
    private GridView gridView;
    private GridItemAdapter adapter;
    private Instrument instrument = new Instrument();
    private Song song = new Song();
    private BooleanGridModel BGM;
    private static int drumsetGridcellColor;
    //DO NOT EVER EVER USE THIS VARIABLE. instead use methods isPlaying and setPlaying - for threadsafety reasons
    private boolean isPlaying = false; //flag to see whether or not the app is currently playing sound
    private boolean muteActivated = false;
    private static final String PREFS = "MainActivityPrefs";




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);
        drumsetGridcellColor = R.drawable.white_square_pink_filling;

        //Get model data
        BGM = song.getCurrentBGM();

        //Load gridview and drum sounds (...via SoundPool)
        instrument.initiateSound();
        instrument.loadDeepHouseKit(this);

        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new GridItemAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!BGM.isSelected(position)) {
                    v.setBackgroundResource(drumsetGridcellColor);
                    adapter.editDrawableID(position, drumsetGridcellColor);
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
    }


    //HELPER METHODS


    public void loadSong(View v) {
//        SharedPreferences settings = context.getSharedPreferences(PREFS, MODE_PRIVATE);
//
//        int songSize = settings.getInt(PREFS + "_bgm_list_size", 1);
//        boolean[] tempArray = new boolean[size*size];
//        song = new Song();
//        for(int i=0; i<songSize; i++){
//            for(int j=0 ; j<tempArray.length; j++){
//                tempArray[j] = settings.getBoolean(PREFS + "_beat_" + i + "_" + j, false);
//            }
//            if (i!=0) {
//                song.addBGM(new BooleanGridModel());
//            }
//            song.setCurrentBGM(new BooleanGridModel(tempArray));
//            BGM = song.getCurrentBGM();
//            updateAdapter_oneBGM(BGM);
//        }
//        switchInstrument(settings.getInt(PREFS + "_instrument", 2));//default: deep house kit
//        fixNavIcons();
//        fixBGMIndexIndicator();
    }


    public void saveSong(View v){
//        SharedPreferences settings = context.getSharedPreferences(PREFS, MODE_APPEND);
//        SharedPreferences.Editor editor = settings.edit();
//
//        editor.putInt(PREFS + "_bgm_list_size", song.getBGMListSize());
//        boolean[] bgmArray;
//        for (int i = 0; i < song.getBGMListSize()-1; i++) {
//            bgmArray = song.getBGMByIndex(i).getBooleanArray();
//            for (int j = 0; j < bgmArray.length; j++){
//                editor.putBoolean(PREFS + "_beat_" + i + "_" + j, bgmArray[j]);
//            }
//        }
//        editor.putInt(PREFS + "_instrument", instrument.getInstrumentSelection());
//        editor.commit();
    }


    protected void switchInstrument(int instrumentSelection) {
        switch (instrumentSelection){
            case 0:
                drumsetGridcellColor = R.drawable.white_square_blue_filling;
                instrument.load808Kit(MainActivity.this);
                break;
            case 1:
                drumsetGridcellColor = R.drawable.white_square_green_filling;
                instrument.loadKcKit(MainActivity.this);
                break;
            case 2:
                drumsetGridcellColor = R.drawable.white_square_pink_filling;
                instrument.loadDeepHouseKit(MainActivity.this);
                break;
        }
    }


    public void fixBGMIndexIndicator(){
        TextView indexView = (TextView) findViewById(R.id.BGMIndex);
        indexView.setText((song.getCurrentBGMIndex() + 1) + "/" + song.getBGMListSize());
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


    public void updateAdapter_allBGMs(){
        for (int i = 0; i < song.getBGMListSize(); i++) {
            updateAdapter_oneBGM(song.getBGMByIndex(i));
        }
    }


    public void updateAdapter_oneBGM(BooleanGridModel bgm){
        for (int i = 0; i < bgm.getBGMSize(); i++){
            if (bgm.isSelected(i)){
                adapter.editDrawableID(i, drumsetGridcellColor);
            }
            else {
                adapter.editDrawableID(i, R.drawable.white_frame);
            }
        }
        adapter.notifyDataSetChanged();
    }


    private synchronized boolean isPlaying(){
        return isPlaying;
    }


    private synchronized void setPlaying(boolean state){
        if (state){
            findViewById(R.id.play).setBackgroundResource(R.drawable.pause1);
        } else {
            findViewById(R.id.play).setBackgroundResource(R.drawable.play1);
        }
        isPlaying = state;
    }


    //onClick Methods


    public void onClickPlay(View v) {
        if (!isPlaying()) {
            Runnable r = new PlayThread();
            new Thread(r).start();
            v.setBackgroundResource(R.drawable.pause1);
        } else {
            v.setBackgroundResource(R.drawable.play1);
            setPlaying(false);
        }
    }


    public void onClickInstruments(View v){
        setPlaying(false);
        Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setTitle("Select a Drum Kit")
                .setItems(new String[]{"808 Kit", "Kc Kit","Deep House Kit"}, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dlg, int position) {
                        if (position == 0) {
                            drumsetGridcellColor = R.drawable.white_square_blue_filling;
                            instrument.load808Kit(MainActivity.this);
                        } else if (position == 1) {
                            drumsetGridcellColor = R.drawable.white_square_green_filling;
                            instrument.loadKcKit(MainActivity.this);
                        } else if (position == 2) {
                            drumsetGridcellColor = R.drawable.white_square_pink_filling;
                            instrument.loadDeepHouseKit(MainActivity.this);
                        }
                        updateAdapter_allBGMs();
                    }
                })
                .create();
        d.show();
    }


    public void onClickMusicSettings(View v) {
        setPlaying(false);
        final Dialog yourDialog = new Dialog(context);
        yourDialog.setContentView(R.layout.tempo_dialog);
        yourDialog.setTitle("Music Settings");
        final TextView tempoView = (TextView) yourDialog.findViewById(R.id.currentTempo);
        tempoView.setText(Integer.toString(tempo + tempoMin));
        final SeekBar tempoSetter = (SeekBar)yourDialog.findViewById(R.id.tempo_seek_bar);
        tempoSetter.setMax(80);//max num of increments along the bar
        tempoSetter.setProgress(tempo);
        tempoSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStopTrackingTouch(SeekBar arg0){}
            @Override
            public void onStartTrackingTouch(SeekBar arg0){}
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2){
                tempoView.setText(Integer.toString(arg1 + tempoMin));
                tempTempo = arg1;
            }
        });
        Button savedSettings = (Button) yourDialog.findViewById(R.id.apply_tempo_changed);
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


    public void onClickMute(View v) {
        muteActivated = !muteActivated;
        if (muteActivated) {
            v.setBackgroundResource(R.drawable.sound_inactive1);
        } else {
            v.setBackgroundResource(R.drawable.sound_active1);
        }
    }


    public void onClickClear(View v) {
        if (!song.isEmpty()){
            setPlaying(false);
            Dialog d = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                    .setTitle("Clear Contents...")
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
                                }
                                else {
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
                    .setNegativeButton(R.string.cancel, null)
                    .create();
            d.show();
        }
    }


    public void onClickAdd(View v) {
        if (isPlaying()){
            setPlaying(false);
        }
        if (song.getBGMListSize() < 5) {
            song.addBGM(new BooleanGridModel());
            BGM = song.getNextBGM();
            updateAdapter_oneBGM(BGM);
            fixNavIcons();
            fixBGMIndexIndicator();
        }
    }


    public void onClickPrevious(View v) {
        if (isPlaying()){
            setPlaying(false);
        }
        BGM = song.getPreviousBGM();
        updateAdapter_oneBGM(BGM);
        fixNavIcons();
        fixBGMIndexIndicator();
    }


    public void onClickNext(View v) {
        if (isPlaying()){
            setPlaying(false);
        }
        BGM = song.getNextBGM();
        updateAdapter_oneBGM(BGM);
        fixNavIcons();
        fixBGMIndexIndicator();
    }


    //Runnable Thread


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


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present (it's not).
//        getMenuInflater().inflate(R.menu.menu_grid__layout, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}