package edu.dkim2macalester.stepsequencer.model;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

import edu.dkim2macalester.stepsequencer.R;
import edu.dkim2macalester.stepsequencer.controller.MainActivity;

/**
 * Created by aronthomas on 4/30/15.
 */
public class Instrument {

    private SoundPool soundPool;
    private ArrayList<Sound> mSounds = new ArrayList<>();

    public void initiateSound (){
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
    }


    public void loadKitOne(Context context) {
        Sound s = new Sound();
        s.setDescription("kick");
        s.setSoundResourceId(soundPool.load(context,R.raw.kick,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clap");
        s.setSoundResourceId(soundPool.load(context,R.raw.clap,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(context,R.raw.snare,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("rim");
        s.setSoundResourceId(soundPool.load(context,R.raw.rim,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cowbell");
        s.setSoundResourceId(soundPool.load(context,R.raw.cowbell,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_closed");
        s.setSoundResourceId(soundPool.load(context,R.raw.hhclosed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_open");
        s.setSoundResourceId(soundPool.load(context,R.raw.hhopen,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_low");
        s.setSoundResourceId(soundPool.load(context,R.raw.congalo,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_med");
        s.setSoundResourceId(soundPool.load(context,R.raw.congamed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_hi");
        s.setSoundResourceId(soundPool.load(context,R.raw.congahi,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_lo");
        s.setSoundResourceId(soundPool.load(context,R.raw.tomlo,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_med");
        s.setSoundResourceId(soundPool.load(context,R.raw.tommed,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_hi");
        s.setSoundResourceId(soundPool.load(context, R.raw.tomhi,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_1");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal1,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_2");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal2,1));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_3");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal3,1));
        mSounds.add(s);
    }

    public SoundPool accessSoundPool(){
       return soundPool;
    }

    public ArrayList accessSoundArray (){
        return mSounds;
    }
}
