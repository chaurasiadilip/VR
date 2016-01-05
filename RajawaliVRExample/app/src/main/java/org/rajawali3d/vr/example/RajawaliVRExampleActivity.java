package org.rajawali3d.vr.example;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.util.RajLog;
import org.rajawali3d.vr.RajawaliCardboardView;
import org.rajawali3d.vr.renderer.RajawaliCardboardRenderer;

public class RajawaliVRExampleActivity extends CardboardActivity implements OnClickListener {
   // private RajawaliVRExampleRenderer mRenderer;
    private  RajawaliCardboardRenderer renderer;
    private String path="";
    private SeekBar seekbar;
    private Button playpause;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        path=getIntent().getStringExtra("paths");
       // System.out.println("RajawaliVRExampleActivity="+path);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);
        RelativeLayout rl=(RelativeLayout)findViewById(R.id.place);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        playpause=(Button)findViewById(R.id.button);
        RajawaliCardboardView view = new RajawaliCardboardView(this);
        rl.addView(view,0);
        //setContentView(view);
        setCardboardView(view);

         renderer = new VideoRenderer(this,path,seekbar);
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);
        // mRenderer = new RajawaliVRExampleRenderer(this);
        // setRenderer(mRenderer);
        playpause.setOnClickListener(this);
        setConvertTapIntoTrigger(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    /**
     * Called when the Cardboard trigger is pulled.
     */
    @Override
    public void onCardboardTrigger() {
        RajLog.i("onCardboardTrigger");
    }

    @Override
    protected void onPause() {
        super.onPause();
        renderer.onPause();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(playpause.getText().equals("Pause")){
            ((VideoRenderer)renderer).VideoPause();
            playpause.setText("Play");
        }else{
            ((VideoRenderer)renderer).VideoResume();
            playpause.setText("Pause");
        }
    }
}
