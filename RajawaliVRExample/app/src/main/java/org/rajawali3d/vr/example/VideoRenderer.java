package org.rajawali3d.vr.example;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.vr.renderer.RajawaliCardboardRenderer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dileep on 12/16/2015.
 */
public class VideoRenderer extends RajawaliCardboardRenderer {

    Context mContext;

    private MediaPlayer mMediaPlayer;
    private StreamingTexture mVideoTexture;
    private String path;
    private SeekBar seekbars;
    private double timeElapsed = 0, finalTime = 0;

    private Handler durationHandler = new Handler();

    public VideoRenderer(Context context,String mPath,SeekBar seekbar) {
        super(context);

        mContext = context;
        path=mPath;
        seekbars=seekbar;
      //  System.out.println("Path=" + path);
    }



    /**
     * Scene construction should happen here, not in onSurfaceCreated()
     */

    @Override
    protected void initScene() {
       // mMediaPlayer = MediaPlayer.create(getContext(),
             //   R.raw.montage);
        mMediaPlayer=new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(path);
           mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setLooping(true);

        mVideoTexture = new StreamingTexture("sintelTrailer", mMediaPlayer);
        Material material = new Material();
        material.setColorInfluence(0);
        try {
            material.addTexture(mVideoTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Sphere sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);
        sphere.setMaterial(material);

        getCurrentScene().addChild(sphere);

        getCurrentCamera().setPosition(Vector3.ZERO);

        getCurrentCamera().setFieldOfView(75);

        finalTime = mMediaPlayer.getDuration();

        mMediaPlayer.start();
        seekbars.setMax((int) finalTime);
        seekbars.setClickable(false);
        timeElapsed = mMediaPlayer.getCurrentPosition();
        seekbars.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);


    }
    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
        durationHandler.removeCallbacks(updateSeekBarTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaPlayer != null)
            mMediaPlayer.start();

    }

    public void VideoPause(){
        mMediaPlayer.pause();
    }
    public void VideoResume(){
        mMediaPlayer.start();
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }

    @Override
    public void onRenderSurfaceDestroyed(SurfaceTexture surfaceTexture) {
        super.onRenderSurfaceDestroyed(surfaceTexture);
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    //handler to change seekBarTime

    private Runnable updateSeekBarTime = new Runnable() {

        public void run() {
              //get current position
           timeElapsed = mMediaPlayer.getCurrentPosition();
            //set seekbar progress
            seekbars.setProgress((int) timeElapsed);
            //set time remaing
           // double timeRemaining = finalTime - timeElapsed;
            //duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            //repeat yourself that again in 100 miliseconds
           durationHandler.postDelayed(this, 100);

        }

    };



}
