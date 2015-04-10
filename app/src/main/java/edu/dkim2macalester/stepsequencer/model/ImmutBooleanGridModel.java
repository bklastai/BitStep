 package edu.dkim2macalester.stepsequencer.model;

/**
 * Created by mju on 3/23/2015.
 */

public final class ImmutBooleanGridModel extends Object {

    private final int width = 16;
    private final int height = 16;
    private final boolean[] selections;

    public ImmutBooleanGridModel(){ //if no model passed in, inits to all-0 grid
        boolean[] temp = new boolean[width*height];
        for (int i = 0; i < temp.length; i++){
            temp[i]= false;
        }
        selections = temp;
    }

    public ImmutBooleanGridModel(boolean[] temp){//takes a
        selections = temp;
    }


    public ImmutBooleanGridModel setSelected(int position, boolean NOTisSelected){
        //new model to reflect changes
        boolean[] temp = new boolean[width*height];

        //setting identical to old BGM model
        for (int i = 0; i < temp.length; i++){
            temp[i]= this.isSelected(i);
        }

        //making the change
        temp[position] = NOTisSelected;

        ImmutBooleanGridModel newBGM = new ImmutBooleanGridModel(temp);
        return newBGM;
    }

    public int getSample(int position) { return (position - position % width) / height; }

    public int getTimestamp(int position) { return position % width; }

    public boolean isSelected(int position) {
        return selections[position];
    }
    public int getBGMSize() {
        return selections.length;
    }

}
