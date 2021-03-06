package edu.dkim2macalester.stepsequencer.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import edu.dkim2macalester.stepsequencer.R;


/**
 * Created by Benas on 3/4/2015.
 */
public class GridItemAdapter extends BaseAdapter{

    private Context mContext;

    private int gridWidth = 16;
    private int gridHeight = 16;

    private Integer[] values = new Integer[gridWidth*gridHeight];




    public GridItemAdapter(Context c) {
        this.mContext = c;
        initValues();
    }

    public void editDrawableID(int position, Integer drawableID){
        values[position] = drawableID;
    }

    // create a new View (Button) for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup arg2) {
        final View view;
        if (convertView==null)
        {
            view = new SquareImageView(mContext);
        }
        else
        {
            view = convertView;
        }
        view.setBackgroundResource(values[position]);
        return view;
    }


    public long getItemId(int position) {
        return 0;
    }


    public int getCount() {
        return values.length;
    }


    public Object getItem(int position) {
        return values[position];
    }


    public void initValues(){
        for (int i=0;i<gridHeight*gridWidth;i++){
            this.values[i]= R.drawable.white_frame;
        }
    }




}
