package edu.dkim2macalester.stepsequencer.model;

/**
 * Created by aronthomas on 3/18/15.
 */
public class Sound {
    private String mDescription = "";
    private int mSoundResourceId = -1;


    public void setDescription(String description) { mDescription = description; }
    public String getDescription() { return mDescription; }
    public void setSoundResourceId(int id) { mSoundResourceId = id; }
    public int getSoundResourceId() { return mSoundResourceId; }
}