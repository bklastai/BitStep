package edu.dkim2macalester.stepsequencer;

/**
 * Created by aronthomas on 3/18/15.
 */
public class sound {
    private String mDescription = "";
    private int mSoundResourceId = -1;
    public void setDescription(String description) { mDescription = description; }
    public String getDescription() { return mDescription; }
    public void setSoundResourceId(int id) { mSoundResourceId = id; }
    public int getSoundResourceId() { return mSoundResourceId; }
}