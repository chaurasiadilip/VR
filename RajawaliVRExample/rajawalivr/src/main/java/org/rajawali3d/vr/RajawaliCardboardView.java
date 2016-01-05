package org.rajawali3d.vr;

import android.content.Context;
import android.util.AttributeSet;

import com.google.vrtoolkit.cardboard.CardboardView;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.IRajawaliSurfaceRenderer;
/**
 * Created by Dileep on 12/16/2015.
 */
public class RajawaliCardboardView extends CardboardView implements IRajawaliSurface{
    private IRajawaliSurfaceRenderer renderer;

    public RajawaliCardboardView(Context context) {
        super(context);
    }

    public RajawaliCardboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the target frame rate in frames per second.
     *
     * @param rate {@code double} The target rate.
     */
    @Override
    public void setFrameRate(double rate) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onResume();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        renderer.onRenderSurfaceDestroyed(null);
    }

    /**
     * Called to enable/disable multisampling on this surface.
     * Must be called before {@link #setSurfaceRenderer(IRajawaliSurfaceRenderer)}.
     *
     * @param config {@link ANTI_ALIASING_CONFIG} The desired anti aliasing configuration.
     */
    @Override
    public void setAntiAliasingMode(ANTI_ALIASING_CONFIG config) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets the sample count to use. Only applies if multisample antialiasing is active.
     *
     * @param count {@code int} The sample count.
     */
    @Override
    public void setSampleCount(int count) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Called to set the {@link IRajawaliSurfaceRenderer} which will render on this surface.
     *
     * @param renderer {@link IRajawaliSurfaceRenderer} instance.
     * @throws IllegalStateException Thrown if a renderer has already been set.
     */
    @Override
    public void setSurfaceRenderer(IRajawaliSurfaceRenderer renderer) throws IllegalStateException {
        if (this.renderer != null)
            throw new IllegalStateException("A renderer has already been set for this view.");

        renderer.setRenderSurface(this);

        this.renderer = renderer;

        onPause(); // We want to halt the surface view until we are ready
    }

    /**
     * Called when a render request should be made.
     */
    @Override
    public void requestRenderUpdate() {
        requestRender();
    }
}
