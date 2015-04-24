package edu.dkim2macalester.stepsequencer.model;

import java.util.ArrayList;

/**
 * Created by Benas on 3/27/2015.
 */
public class Song extends Object {


    private ArrayList<BooleanGridModel> BGMList = new ArrayList<>();
//    private BooleanGridModel[] BGMArray = (BooleanGridModel[]) BGMList.toArray();
    private int currentBGMIndex = 0;


    public Song(){
        init();
    }

    public void addBGM(){
        BooleanGridModel bgm = new BooleanGridModel();
        BGMList.add(bgm);
        BGMList.set(BGMList.size() - 1, bgm);
    }


    public BooleanGridModel getNextBGM(){
        if (BGMList.size() - 1 > currentBGMIndex){ //if there is a next grid, then return it!
            currentBGMIndex++;
            return BGMList.get(currentBGMIndex);
        } else if (getBGMListSize() < 5) {  //if there isn't, make a new one - but creates no more than five grids total
            addBGM();
            return getNextBGM();
        } else { //if there are already five, not creating a new one, returns the one you're on
            return getCurrentBGM();
        }
    }

    public BooleanGridModel getPreviousBGM(){
        if (currentBGMIndex > 0){
            currentBGMIndex--;
            return BGMList.get(currentBGMIndex);
        }
        else {
            return getCurrentBGM();
        }
    }


    public void init(){
        BooleanGridModel bgm = new BooleanGridModel();
        BGMList.add(bgm);
        BGMList.set(0, bgm);
    }

    public BooleanGridModel getCurrentBGM(){
        return BGMList.get(currentBGMIndex);
    }

    public BooleanGridModel getBGMFromIndex(int k){
        return BGMList.get(k);
    }

    //for updating, needed because the BGMs are immutable, we create a new one for each change.
    //called from whenever a button is touched, similar to updating the view.
    public void setCurrentBGM(BooleanGridModel BGM){
        BGMList.set(currentBGMIndex, BGM);
    }

    public int getBGMListSize(){
        return BGMList.size();
    }

    public ArrayList<BooleanGridModel> getBGMList(){
        return this.BGMList;
    }

}
