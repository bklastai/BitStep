package edu.dkim2macalester.stepsequencer.model;

import java.util.ArrayList;

/**
 * Created by Benas on 3/27/2015.
 */
public class Song {


    private ArrayList<BooleanGridModel> BGMList = new ArrayList<>();
    private int currentBGMIndex = 0;
    private BooleanGridModel emptyBGM = new BooleanGridModel();


    public Song(){
        init();
    }

    public BooleanGridModel addBGM(){
        if (!areRemainingBGMsEmpty(currentBGMIndex)){
            BGMList.add(new BooleanGridModel());
            currentBGMIndex = getCurrentBGMIndex()+1;
            return BGMList.get(getCurrentBGMIndex());
        }
        return getCurrentBGM();
    }

    public boolean areRemainingBGMsEmpty(int currentIndex){
        for (int i=currentIndex; i<getBGMListSize(); i++){
            boolean[] tempBGM = BGMList.get(i).getBooleanArray();
            for (int j=0; j<tempBGM.length; j++){
                if (!tempBGM[i]){
                    return false;
                }
            }
            if (i==BGMList.size()-1){
                return true;
            }
            else {
                return areRemainingBGMsEmpty(i+1);
            }
        }
        return false;
    }



    public BooleanGridModel getNextBGM(){
        if (currentBGMIndex < BGMList.size() - 1){ //if there is a next grid, then return it!
            currentBGMIndex++;
            return BGMList.get(currentBGMIndex);
        }
        else { //if there are already five, not creating a new one, returns the one you're on
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
    }

    public BooleanGridModel getCurrentBGM(){
        return BGMList.get(currentBGMIndex);
    }

    public BooleanGridModel getBGMByIndex(int k){
        return BGMList.get(k);
    }

    //for updating, needed because the BGMs are immutable, we create a new one for each change.
    //called from whenever a button is touched, similar to updating the view.
    public void setCurrentBGM(BooleanGridModel BGM){
        BGMList.set(currentBGMIndex, BGM);
    }

    public void setCurrentBGMIndex(int k){
        currentBGMIndex = k;
    }

    public int getBGMListSize(){
        return BGMList.size();
    }

    public void deleteCurrentBGM() {
        BGMList.remove(currentBGMIndex);
    }

    public int getCurrentBGMIndex (){
        return currentBGMIndex;
    }

    public ArrayList<BooleanGridModel> getBGMList(){
        return this.BGMList;
    }

}
