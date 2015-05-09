 package edu.dkim2macalester.stepsequencer.model;

/**
 * Created by mju on 3/23/2015.
 */

public final class BooleanGridModel {

    private final int width = 16;
    private final int height = 16;
    private final boolean[] selections;

    public BooleanGridModel(){ //if no model passed in, inits to all-0 grid
        boolean[] temp = new boolean[width*height];
        for (int i = 0; i < temp.length; i++){
            temp[i]= false;
        }
        selections = temp;
    }

    public BooleanGridModel(boolean[] temp){//constructs a class with the given boolean array
        selections = temp;
    }

    //constructs a class with the given boolean
    // array AND selects the grid at the
    // specified position (Song's current BGM is updated
    // to this one when grid cells are clicked)
    public BooleanGridModel(boolean[] temp, int position){
        selections = temp;
        selections[position] = !isSelected(position);
    }


//    public BooleanGridModel setSelected(int position){
//        //new model to reflect changes
//        boolean[] temp = new boolean[width*height];
//
//        //setting identical to old BGM model aside from desired change
//        for (int i = 0; i < temp.length; i++){
//                temp[i]= this.isSelected(i);
//        }
//
//        //making the change
//        selections[position] = !this.isSelected(position);
//
//        BooleanGridModel newBGM = new BooleanGridModel(temp);
//        return newBGM;
//    }

    public int getSample(int position) { return (position - position % width) / height; }

    public int getTimestamp(int position) { return position % width; }

    public boolean isSelected(int position) {
        return selections[position];
    }

    public int getBGMSize() {
        return selections.length;
    }

    public boolean[] getBooleanArray(){
        return selections;
    }

    public boolean isEmpty(){
        for (int i=0; i<selections.length; i++){
            if (isSelected(i)){
                return false;
            }
        }
        return true;
    }

}
