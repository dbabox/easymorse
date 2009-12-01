package com.easymorse;
import android.app.Activity;
import android.os.Bundle;
public class ChessBoard extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this));

    }
}