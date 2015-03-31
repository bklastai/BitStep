package edu.dkim2macalester.stepsequencer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import edu.dkim2macalester.stepsequencer.R;


/**
 * Created by Benas on 3/14/2015.
 */
public class MySquareLayout extends LinearLayout {
    private LinearLayout squareLayout;

    public MySquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySquareLayout(Context context) {
        super(context);
    }

    public MySquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.main_activity_layout,  this);
        squareLayout = (LinearLayout) findViewById(R.id.squareLayout);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }






}