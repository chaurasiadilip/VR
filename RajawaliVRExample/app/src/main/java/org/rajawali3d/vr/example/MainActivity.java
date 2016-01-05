package org.rajawali3d.vr.example;

import android.os.Bundle;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.vr.RajawaliCardboardView;
import org.rajawali3d.vr.renderer.RajawaliCardboardRenderer;


public class MainActivity extends CardboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RajawaliCardboardView view = new RajawaliCardboardView(this);
        setContentView(view);
        setCardboardView(view);

        RajawaliCardboardRenderer renderer = new MyRenderer(this);
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);
    }
}
