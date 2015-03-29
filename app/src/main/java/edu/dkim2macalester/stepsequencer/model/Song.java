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
        BGMList.set(BGMList.size()-1, bgm);
    }


    public BooleanGridModel getNextBGM(){
        if (BGMList.size()-1>currentBGMIndex){
            currentBGMIndex++;
            return BGMList.get(currentBGMIndex);
        }
        else {
            addBGM();
            return getNextBGM();
        }
    }

    public BooleanGridModel getPreviousBGM(){
        if (currentBGMIndex>0){
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


    public int getBGMListSize(){
        return BGMList.size();
    }

    public ArrayList<BooleanGridModel> getBGMList(){
        return this.BGMList;
    }

}
