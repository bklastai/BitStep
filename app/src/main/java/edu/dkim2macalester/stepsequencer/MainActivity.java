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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private GridView gridView;

    private int size = 16;
    private int numberOfGrids = 1; //change this value when adding new gridviews (to the right of the screen)
    //private boolean[] selection = new boolean[numberOfGrids*256];
    booleanGridModel gridModel = new booleanGridModel();

    private SoundPool soundPool;
    private ArrayList<Sound> mSounds = null;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main_activity_layout);


        //Set up the gridview adapter
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridItemAdapter(this));


        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
        mSounds = new ArrayList<>();
        loadSounds();

        final Button instruments = (Button)findViewById(R.id.instruments);

        instruments.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0){
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupInstrumList = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow( popupInstrumList,
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(findViewById(R.id.squareLayout),0, instruments.getWidth(), 0);
            }});


        //Set up the OnClickListener; change BackgroundResource and set notes 'selection' accordingly
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!gridModel.isSelected(position)) {
                    v.setBackgroundResource(R.drawable.black_square);
                } else {
                    v.setBackgroundResource(R.drawable.empty_square);
                }
                gridModel.setSelected(position);

                //Sound s = mSounds.get((position - position % 16) / 16);
                //soundPool.play(s.getSoundResourceId(), 1, 1, 1, 0, 1);
            }
        });

    }

    private void loadSounds() {
        Sound s = new Sound();
        s.setDescription("Kick");
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
        s.setDescription("hh closed");
        s.setSoundResourceId(soundPool.load(this,R.raw.hhc,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh open");
        s.setSoundResourceId(soundPool.load(this,R.raw.hho,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga low");
        s.setSoundResourceId(soundPool.load(this,R.raw.congal,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga med");
        s.setSoundResourceId(soundPool.load(this,R.raw.congam,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.congah,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom lo");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomlo,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom med");
        s.setSoundResourceId(soundPool.load(this,R.raw.tommed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom hi");
        s.setSoundResourceId(soundPool.load(this,R.raw.tomhi,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal 1");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal1,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal 2");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal2,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal 3");
        s.setSoundResourceId(soundPool.load(this,R.raw.cymbal3,1));
        mSounds.add(s);
    }



    public void play(View v){
        //TODO: put into nested for loops to make correct pausing.
        for (int i = 0; i < size; i++){ //looping through beats/timestamps/columns

            for (int j = 0; j < size; j++) { //looping through samples/scale
                if(gridModel.isSelected((j*size)+i)){
                    Sound s = mSounds.get(gridModel.getSample((j*size)+i));
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


//    public void playMusic(View v){
//        String selectedItems = "";
//        for (int i = 0; i<selection.length; i++){
//            if (selection[i]){
//                selectedItems = selectedItems + i + ", ";
//            }
//        }
//
//        Toast toast = Toast.makeText(getApplicationContext(), "Grid Items Selected: " + selectedItems, Toast.LENGTH_SHORT);
//        toast.show();
//    }


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