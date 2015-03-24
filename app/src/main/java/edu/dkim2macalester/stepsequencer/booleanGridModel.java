package edu.dkim2macalester.stepsequencer;

/**
 * Created by mju on 3/23/2015.
 */
public class booleanGridModel extends Object {
    int size = 16;
    private boolean[] selections = new boolean[size*size];


    public void initSelection(){
        for (int i = 0; i < selections.length; i++){
            selections[i]= false;
        }
    }


    public void setSelected(int position){
        selections[position] = !selections[position];
    }

    public int getSample(int position) { return (position - position % size) / size; }

    public int getTimestamp(int position) { return position % size; }

    public boolean isSelected(int position) {
        return selections[position];
    }

    public int getSizeGridModel() {
        return selections.length;
    }
}
