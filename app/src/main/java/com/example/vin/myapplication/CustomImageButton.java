package com.example.vin.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by vin on 11/06/2017.
 */

public class CustomImageButton extends  android.support.v7.widget.AppCompatImageButton {

    @Override
    protected void drawableStateChanged() {
        Log.d("Button", "isPressed: " + isPressed() );
        if( isPressed() ){
            setBackgroundResource( android.R.color.white );
        }  else {
            setBackgroundResource( android.R.color.transparent );
        }
        super.drawableStateChanged();

    }

    public CustomImageButton( Context context ) {
        super( context );
    }

    public CustomImageButton( Context context, AttributeSet attrs ) {
        super( context, attrs );
    }

    public CustomImageButton( Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        // TODO Auto-generated constructor stub
    }



}