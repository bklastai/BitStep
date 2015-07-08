package edu.dkim2macalester.stepsequencer.model;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

import edu.dkim2macalester.stepsequencer.R;

/**
 * Created by aronthomas on 4/30/15.
 */
public class Instrument {

    private SoundPool soundPool;
    private ArrayList<Sound> mSounds = new ArrayList<>();
    private int instrumentSelection;

    public void initiateSound (){
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);
    }

    public SoundPool accessSoundPool(){
       return soundPool;
    }

    public ArrayList accessSoundArray (){
        return mSounds;
    }

    private void reset() {
        mSounds.clear();
        soundPool.release();
        initiateSound();
    }

    public void load808Kit(Context context) {
//        samples taken from http://hiphopmakers.com/free-808-drum-kit-227-samples
        reset();
        instrumentSelection = 0;

        Sound s = new Sound();
        s.setDescription("kick");
        s.setSoundResourceId(soundPool.load(context, R.raw.kick, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clap");
        s.setSoundResourceId(soundPool.load(context, R.raw.clap, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(context, R.raw.snare, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("rim");
        s.setSoundResourceId(soundPool.load(context, R.raw.rim, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cowbell");
        s.setSoundResourceId(soundPool.load(context, R.raw.cowbell, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_closed");
        s.setSoundResourceId(soundPool.load(context, R.raw.hhclosed, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hh_open");
        s.setSoundResourceId(soundPool.load(context, R.raw.hhopen, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_low");
        s.setSoundResourceId(soundPool.load(context, R.raw.congalo, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_med");
        s.setSoundResourceId(soundPool.load(context, R.raw.congamed, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("conga_hi");
        s.setSoundResourceId(soundPool.load(context, R.raw.congahi, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_lo");
        s.setSoundResourceId(soundPool.load(context, R.raw.tomlo, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_med");
        s.setSoundResourceId(soundPool.load(context, R.raw.tommed, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("tom_hi");
        s.setSoundResourceId(soundPool.load(context, R.raw.tomhi, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_1");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal1, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_2");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal2, 3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("cymbal_3");
        s.setSoundResourceId(soundPool.load(context, R.raw.cymbal3, 3));
        mSounds.add(s);
    }

    public void loadKcKit (Context context) {
//        Samples taken from http://www.freesound.org/people/NoiseCollector/packs/161/
        reset();
        instrumentSelection = 1;

        Sound s = new Sound();
        s.setDescription("kick");
        s.setSoundResourceId(soundPool.load(context,R.raw.kckick,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("double kick");
        s.setSoundResourceId(soundPool.load(context,R.raw.kckickdouble,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("kick 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.kckick2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcsnare,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcsnare2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare 3");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcsnare3,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hi hat");
        s.setSoundResourceId(soundPool.load(context, R.raw.kchihat,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("toms");
        s.setSoundResourceId(soundPool.load(context, R.raw.kctoms,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("toms 2");
        s.setSoundResourceId(soundPool.load(context, R.raw.kctoms2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("stick");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcstick,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("ro to");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcroto,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("ro to rim");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcrotorim,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("ride");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcride,3));
        mSounds.add(s);


        s = new Sound();
        s.setDescription("ride2");
        s.setSoundResourceId(soundPool.load(context,R.raw.kcride2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("crash");
        s.setSoundResourceId(soundPool.load(context,R.raw.kccrash,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("crash 2");
        s.setSoundResourceId(soundPool.load(context, R.raw.kccrash2,3));
        mSounds.add(s);
    }

    public void loadDeepHouseKit (Context context) {
//        Samples taken from https://www.freesound.org/people/DWSD/packs/11575/
        reset();
        instrumentSelection = 2;

        Sound s = new Sound();
        s.setDescription("kick");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhkick,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("kick 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhkick2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhsnare,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("snare 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhsnare2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clap");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhclap,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clap 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhclap2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hi hat");
        s.setSoundResourceId(soundPool.load(context, R.raw.dhhat,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("hi hat 2");
        s.setSoundResourceId(soundPool.load(context, R.raw.dhhat2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("toms");
        s.setSoundResourceId(soundPool.load(context, R.raw.dhtom,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("toms 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhtom2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("clav");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhclav,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("perc");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhperc,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("perc 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhperc2,3));
        mSounds.add(s);


        s = new Sound();
        s.setDescription("rim");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhrim,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("rim 2");
        s.setSoundResourceId(soundPool.load(context,R.raw.dhrim2,3));
        mSounds.add(s);

        s = new Sound();
        s.setDescription("ride");
        s.setSoundResourceId(soundPool.load(context, R.raw.dhride,3));
        mSounds.add(s);
    }

    public int getInstrumentSelection() {
        return instrumentSelection;
    }
}
