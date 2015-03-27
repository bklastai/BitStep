package edu.dkim2macalester.stepsequencer.model;

import java.util.ArrayList;

/**
 * Created by Benas on 3/27/2015.
 */
public class Song extends Object {


    private ArrayList<BooleanGridModel> BGMList = new ArrayList<>();
    private int currentBGMIndex = 0;


    public void addBGM(){
        BGMList.add(new BooleanGridModel());
    }


    public BooleanGridModel getNextBGM(){
        if (BGMList.size()-1>currentBGMIndex){
            currentBGMIndex++;
            return BGMList.get(currentBGMIndex+1);
        }
        else {
            addBGM();
            return getNextBGM();
        }
    }

    public BooleanGridModel getPreviousBGM(){
        if (currentBGMIndex>0){
            currentBGMIndex--;
            return BGMList.get(currentBGMIndex-1);
        }
        else {
            return getCurrentBGM();
        }
    }



    public BooleanGridModel getCurrentBGM(){
        if (BGMList.size()>0){
            return BGMList.get(currentBGMIndex);
        }
        else {
            addBGM();
            return getCurrentBGM();
        }
    }



}
