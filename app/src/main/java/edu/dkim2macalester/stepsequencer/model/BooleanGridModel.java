 package edu.dkim2macalester.stepsequencer.model;

/**
 * Created by mju on 3/23/2015.
 */

public final class BooleanGridModel extends Object {

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

    public BooleanGridModel(boolean[] temp){//takes a
        selections = temp;
    }


    public BooleanGridModel setSelected(int position){
        //new model to reflect changes
        boolean[] temp = new boolean[width*height];

        //setting identical to old BGM model aside from desired change
        for (int i = 0; i < temp.length; i++){
                temp[i]= this.isSelected(i);
        }

        //making the change
        temp[position] = !this.isSelected(position);

        BooleanGridModel newBGM = new BooleanGridModel(temp);
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
