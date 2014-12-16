package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.projet.tony.tx.LostLetter.MyProperties;
import com.projet.tony.tx.LostLetter.fin;
import com.projet.tony.tx.R;

public class photo_change extends ActionBarActivity {
    private TextView question = null;
    private JSONObject parser=null;
    private JSONObject save=null;
    private int Id_Question=1; // question actuelle
    private File OCRDir = new File(Environment.getExternalStorageDirectory().getPath()+"/OCR/");
    private File tessDir = new File(OCRDir.getPath()+"/tessdata/");
    private File imagesDir = new File(OCRDir.getPath()+"/images/");
    private File engTrainedData = new File(tessDir.getPath()+"/eng.traineddata");
    private File fraTrainedData = new File(tessDir.getPath()+"/fra.traineddata");
    private String fichier= "LostLetters.JSON";
    public final static int REQUEST_CAM = 42;
    private String fichier_save;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__photo_change);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl);

        final Button button = (Button) relativeLayout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView resOCR = (TextView) findViewById(R.id.field);

                globalvar_change.getInstance().phrase= resOCR.getText().toString();

                Intent intent = new Intent(photo_change.this, MyActivity.class);
                startActivity(intent);
            }
        });
    }


    private void copyFromAsset(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            int read=0;
            while((read=in.read(buffer))!=-1) {
                out.write(buffer,0,read);
            }
            in.close();
            in=null;
            out.flush();
            out.close();
            out=null;
        }
        catch (IOException e) {
            Log.d("OCR", e.toString());
        }
    }



    public void openCamera(View view) {
        //Intent intent = new Intent(this, CameraActivity.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();
        String game = MyProperties.getInstance().jeu;
        File image;
        File dir;

            dir = new File(imagesDir+"/"+game);
            dir.mkdir();
            image = new File(dir,"image_" + simpleDateFormat.format(date) + ".jpg");

        imgUri = Uri.fromFile(image);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imgUri);
        startActivityForResult(intent, REQUEST_CAM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAM:
                if(resultCode == Activity.RESULT_OK) {
                    ocr(imgUri.getPath());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__json, menu);
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

    // OCR

    String LOG_TAG="error_ocr";
    String LANG="fra";
    String DATA_PATH= Environment.getExternalStorageDirectory().getPath()+"/OCR/";
    //String IMAGE_PATH=DATA_PATH+"ici-parle-francais.jpg";

    public void ocr(String pathToImage) {

        //File imagesDirectory = new File(DATA_PATH + "images/");
        /*File[] imagesInDirectory = imagesDirectory.listFiles();
        Arrays.sort(imagesInDirectory);*/

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(pathToImage, options);
        Bitmap mutableBitmap;

        try {
            ExifInterface exif = new ExifInterface(pathToImage);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Log.v(LOG_TAG, "Orient: " + exifOrientation);

            int rotate = 0;
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }


            Log.v(LOG_TAG, "Rotation: " + rotate);

            if (rotate != 0) {

                // Getting width & height of the given image.
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
                // tesseract req. ARGB_8888
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            }

            mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);

            for(int px = 0; px<mutableBitmap.getWidth();px++) {
                for(int py = 0; py<mutableBitmap.getHeight();py++) {
                    int pColor = mutableBitmap.getPixel(px,py);

                    if((Color.red(pColor)<105) && (Color.blue(pColor)<120) && (Color.green(pColor)<110)) {
                        mutableBitmap.setPixel(px,py,Color.argb(255,0,0,0));
                    }
                    else {
                        mutableBitmap.setPixel(px,py,Color.argb(255,255,255,255));
                    }


                }
            }
            /*Pix pix = Convert.convertTo8(ReadFile.readBitmap(mutableBitmap));
            pix = Binarize.otsuAdaptiveThreshold(pix,Binarize.OTSU_SIZE_X,Binarize.OTSU_SIZE_Y,Binarize.OTSU_SMOOTH_X,Binarize.OTSU_SMOOTH_Y,0.05f);
            mutableBitmap = WriteFile.writeBitmap(pix);*/
            ImageView iv = (ImageView) findViewById(R.id.image);
            iv.setImageBitmap(mutableBitmap);
            iv.setVisibility(View.VISIBLE);

            Log.v(LOG_TAG, "Before baseApi");

            TessBaseAPI baseApi = new TessBaseAPI();
            baseApi.setDebug(true);
            baseApi.init(DATA_PATH, LANG);
            baseApi.setImage(mutableBitmap);
            String recognizedText = baseApi.getUTF8Text();
            baseApi.end();

            Log.v(LOG_TAG, "OCR Result: " + recognizedText);

            // clean up and show
            if (LANG.equalsIgnoreCase("eng")) {
                recognizedText = recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
            }
            if (recognizedText.length() != 0) {
                TextView resOCR = (TextView) findViewById(R.id.field);
                resOCR.setText(recognizedText.trim());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Rotate or conversion failed: " + e.toString());
        }
    }
}
