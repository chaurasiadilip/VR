package org.rajawali3d.vr.example;

import android.content.Context;

import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.vr.renderer.RajawaliCardboardRenderer;
/**
 * Created by Dileep on 12/16/2015.
 */
public class MyRenderer extends RajawaliCardboardRenderer {

    public MyRenderer(Context context) {
        super(context);
    }

    @Override
    protected void initScene() {

        Sphere sphere = createPhotoSphereWithTexture(new Texture("photo", R.drawable.panorama));

        getCurrentScene().addChild(sphere);

        getCurrentCamera().setPosition(Vector3.ZERO);
        getCurrentCamera().setFieldOfView(75);
    }

    private static Sphere createPhotoSphereWithTexture(ATexture texture) {

        Material material = new Material();
        material.setColor(0);

        try {
            material.addTexture(texture);
        } catch (ATexture.TextureException e) {
            throw new RuntimeException(e);
        }

        Sphere sphere = new Sphere(50, 64, 32);
        sphere.setScaleX(-1);
        sphere.setMaterial(material);

        return sphere;
    }
    /* public void changeImage()
    {
       Log.d("debug1", "" + getCurrentScene().getNumChildren());
        ArrayList<Object3D> objectList = getCurrentScene().getChildrenCopy();
        Material material = objectList.get(0).getMaterial();
        for (ATexture texture : material.getTextureList())
        {
            material.removeTexture(texture);
            texture = null;
        }

        Texture t = new Texture("sphereTexture",R.drawable.newImage);
        t.shouldRecycle(true);
              try {
                  material.addTexture(t);
              }
              catch (Exception e){e.printStackTrace();}

    }
     */
}
