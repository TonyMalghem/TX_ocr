package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projet.tony.tx.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends ActionBarActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private LinearLayout layout;
    private Activity act= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }

    public void onClick(View view) {
        mCamera.takePicture(null,null,mPicture);
    }

    @Override
    //parait qu'il faut pas tout mettre dans le onStart mais dans le onResume, j'ai pas encore trop compris pourquoi cela dit ^^
    protected void onResume() {
        super.onResume();
        if(checkCamAvailability()) {
            mCamera = getCamInstance();
            mPreview = new CameraPreview(this, mCamera);
            layout = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_camera,null);
            FrameLayout preview = (FrameLayout) layout.findViewById(R.id.camera_preview);
            preview.addView(mPreview);
            setContentView(layout);
        }
    }

    //comme pour l'autre appli, permet de check si y a des cam sur l'appareil (sachant que c'est redondant avec ce qu'il y a dans le manifest, m'enfin)
    private boolean checkCamAvailability() {
        Log.d("CAM","y a une cam ? "+getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //récup une instance de la cam, comme son nom ne l'indique pas
    public static Camera getCamInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e) {
            Log.e("CAM",e.toString());
        }
        return c;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //quand on met l'appli en pause (arrière plan), on release l'instance de la cam qu'on avait pour libérer l'accès à la ressource
    protected void onPause() {
        super.onPause();
        releaseCam();
    }

    private void releaseCam() {
        if (mCamera!=null) {
            Log.d("CAM","releasing");
            mCamera.release();
            mCamera=null;
        }
    }

    //callback pour la prise de photo, set du dossier et enregistrement du bordel
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mCamera.startPreview();
            File imageFolder = new File(Environment.getExternalStorageDirectory(),"OCR/images/");
            Log.d("CAM","Creation du dossier " + imageFolder);
            if(!imageFolder.exists())
                imageFolder.mkdir();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            Date date = new Date();
            File image = new File(imageFolder,"image_" + simpleDateFormat.format(date) + ".jpg");
            if(image==null) {
                Log.e("CAM","erreur dans la creation du fichier");
            }
            try {
                FileOutputStream fos = new FileOutputStream(image);
                fos.write(data);
                fos.close();
                //indique au gars que sa photo est enregistrée, je t'explique tout à l'heure pourquoi il le faut
                Toast.makeText(getApplicationContext(),"Pic taken and recorded : " + image,Toast.LENGTH_LONG).show();
                Intent intent = getIntent();
                intent.putExtra("picFile",image.getAbsolutePath());
                setResult(RESULT_OK,intent);

                finish();
            }
            catch (FileNotFoundException e) {
                Log.e("CAM",e.toString());
            }
            catch (IOException io) {
                Log.e("CAM",io.toString());
            }
        }
    };
}
