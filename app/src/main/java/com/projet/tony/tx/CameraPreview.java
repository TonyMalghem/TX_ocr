package com.projet.tony.tx;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

//permet d'afficher ce que l'utilisateur pointe avec sa camera, c'est une grosse dale avec l'affichage de la camera en fait
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch (IOException io) {
            Log.e("CAM",io.toString());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Log.d("CAM",""+sizes.get(0).height+"x" + sizes.get(0).width);
        Log.d("CAM",""+sizes.get(1).height+"x" + sizes.get(1).width);
        Log.d("CAM",""+sizes.get(2).height+"x" + sizes.get(2).width);
        parameters.setPreviewSize(240,360);
        requestLayout();
        mCamera.setParameters(parameters);

        if(mHolder.getSurface()==null) {
            return;
        }

        try {
            mCamera.stopPreview();
        }
        catch (Exception e) {
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch (IOException io) {
            Log.e("CAM",io.toString());
        }
    }
}

