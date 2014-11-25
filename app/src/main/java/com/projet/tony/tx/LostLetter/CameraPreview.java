package com.projet.tony.tx.LostLetter;

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
        }
        catch (IOException io) {
            Log.e("CAM",io.toString());
        }
    }


    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizesPreview = parameters.getSupportedPreviewSizes();
            List<Camera.Size> sizesPicture = parameters.getSupportedPictureSizes();

            for (int i = 0; i < sizesPicture.size(); i++) {
                if (sizesPreview.contains(sizesPicture.get(i))) {
                    Log.d("CAM", "w = " + sizesPicture.get(i).width + " h = " + sizesPicture.get(i).height);
                    parameters.setPreviewSize(sizesPicture.get(i).width, sizesPicture.get(i).height);
                    Log.d("CAM", "" + parameters.getPreviewSize().width + " " + parameters.getPreviewSize().height);
                    break;
                }
            }
            try {
                mCamera.setParameters(parameters);
            } catch (RuntimeException e) {
                Log.d("CAM", e.toString());
            }
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException io) {
            Log.e("CAM", io.toString());
        }
    }
}

