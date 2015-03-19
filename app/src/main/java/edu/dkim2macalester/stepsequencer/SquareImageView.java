package edu.dkim2macalester.stepsequencer;

import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Benas on 3/15/2015.
 */
public class SquareImageView extends ImageView{

    public SquareImageView(Context context){
        super(context);
    }
    public SquareImageView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public SquareImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

}
