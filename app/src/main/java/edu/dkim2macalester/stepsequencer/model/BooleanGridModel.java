package edu.dkim2macalester.stepsequencer.model;

/**
 * Created by mju on 3/23/2015.
 */
public class BooleanGridModel extends Object {

    int width = 16;
    int height = 16;
    private boolean[] selections = new boolean[width*height];


    public void initSelection(){
        for (int i = 0; i < selections.length; i++){
            selections[i]= false;
        }
    }

    public void setSelected(int position){
        selections[position] = !selections[position];
    }

    public int getSample(int position) { return (position - position % width) / height; }

    public int getTimestamp(int position) { return position % width; }

    public boolean isSelected(int position) {
        return selections[position];
    }

    public int getSizeGridModel() {
        return selections.length;
    }

    public boolean[] getSelections(){
        return this.selections;
    }
}
