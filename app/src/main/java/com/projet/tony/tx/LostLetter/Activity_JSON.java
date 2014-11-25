package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.projet.tony.tx.R;

public class Activity_JSON extends ActionBarActivity {
    private TextView question = null;
    private JSONObject parser=null;
    private JSONObject save=null;
    private int Id_Question=1; // question actuelle
    private File OCRDir = new File(Environment.getExternalStorageDirectory().getPath()+"/OCR/");
    private File tessDir = new File(OCRDir.getPath()+"/tessdata/");
    private File imagesDir = new File(OCRDir.getPath()+"/images/");
    private File engTrainedData = new File(tessDir.getPath()+"/eng.traineddata");
    private File fraTrainedData = new File(tessDir.getPath()+"/fra.traineddata");
    public final static int REQUEST_CAM = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__json);
        parser = getJSONObject("test.JSON");
        save = getJSONObject("save.JSON");

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl);


        question = (TextView) relativeLayout.findViewById(R.id.textView);
        question.setText(get_question(Id_Question));


        final Button button = (Button) relativeLayout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                EditText editText = (EditText) relativeLayout.findViewById(R.id.editText);
                String rep=editText.getText().toString();
                if(res(parser,rep,Id_Question)) Toast.makeText(getApplicationContext(),"Bonne réponse!",Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(),"Mauvaise réponse!",Toast.LENGTH_LONG).show();
                editText.setText("");
                question_done(Id_Question);
                saveXP();
                changer_question();

                if(Id_Question!=-1) {
                    question = (TextView) relativeLayout.findViewById(R.id.textView);
                    question.setText(get_question(Id_Question));
                }
                else
                {
                    question = (TextView) relativeLayout.findViewById(R.id.textView);
                    question.setText("Plus de question !");
                }
            }

        });

        if(!OCRDir.exists()) {
            if(!OCRDir.mkdir()) {
                Log.d("OCR","erreur creation dossier OCR");
            }
        }
        if(!tessDir.exists()) {
            if(!tessDir.mkdir()) {
                Log.d("OCR","erreur creation dossier tessdata");
            }
        }
        if(!imagesDir.exists()) {
            if(!imagesDir.mkdir()) {
                Log.d("OCR","erreur creation dossier tessdata");
            }
        }
        if(!engTrainedData.exists() || !fraTrainedData.exists()) {
            //chargement de eng.traineddata depuis assets dans /OCR
            try {
                AssetManager assetManager = getAssets();
                String[] files = assetManager.list("traineddata");
                InputStream in = null;
                OutputStream out = null;
                for (String f : files) {
                    Log.d("OCR", f);
                    in = assetManager.open("traineddata/" + f);
                    out = new FileOutputStream(tessDir + "/" + f);
                    copyFromAsset(in, out);
                }
            } catch (IOException e) {
                Log.d("OCR", e.toString());
            }
        }

        setContentView(relativeLayout);

        /*if(!imageTestEng.exists() ||!imageTestFra.exists()) {
            //chargement de eng.traineddata depuis assets dans /OCR
            try {
                AssetManager assetManager = getAssets();
                String[] files = assetManager.list("imagesTest");
                InputStream in = null;
                OutputStream out = null;
                for (String f : files) {
                    Log.d("OCR", f);
                    in = assetManager.open("imagesTest/" + f);
                    out = new FileOutputStream(OCRDir + "/" + f);
                    copyFromAsset(in, out);
                }
            } catch (IOException e) {
                Log.d("OCR", e.toString());
            }
        }*/
    }

    /*    @Override
        public void onResume() {
            super.onResume();
            if(OCRDir.exists() && tessDir.exists() && engTrainedData.exists()) {
                ocr();
            }
        }
    */
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

    // change de question pour une question pas encore répondue
    public void changer_question()
    {
        int i=0;
        String done="y";
        JSONArray enigme = null;
        try {
            enigme = save.getJSONArray("enigme");
            JSONObject ret=enigme.getJSONObject(i);
            while(done.compareTo("y")==0 && i<enigme.length())
            {
                ret = enigme.getJSONObject(i);
                done=ret.getString("Done");
                i++;
            }
            // si on a pas trouvé alors il n'y a plus de question sinon on met l'id réel de la question
            if(ret.getString("Done").compareTo("y")==0)i=-1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Id_Question=i;
    }


    public void saveXP()
    {
        File file = getFileStreamPath("save.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream writer = null;
        try {
            writer = openFileOutput(file.getName(), Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            writer.write(save.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSONObject getJSONObject(String Fichier)
    {
        BufferedReader reader = null;
        JSONObject parser=null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(Fichier)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json;
        StringBuffer buff = new StringBuffer();

        try {
            while ((json = reader.readLine()) != null) {
                buff.append(json + "\n");

                try {
                    parser = new JSONObject(buff.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parser;
    }

    public void question_done(int id)
    {
        try {
            id=id-1;
            JSONArray enigme = save.getJSONArray("enigme");
            JSONObject ret = enigme.getJSONObject(id);
            ret.put("Done","y");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean res(JSONObject parser,String rep,int id) {
        String resp = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("test.JSON")));
            String json;
            StringBuffer buff = new StringBuffer();

            try {
                JSONArray enigme = parser.getJSONArray("enigme");
                int i=id-1;
                JSONObject ret = enigme.getJSONObject(i);
                Log.d("JSON", "id : " + ret.getString("id"));
                Log.d("JSON", "question : " + ret.getString("question"));
                JSONArray reponses = ret.getJSONArray("reponse");
                for (int j = 0; j < reponses.length(); j++) {
                    Log.d("JSON", "reponse " + j + " : " + reponses.get(j));
                    if(rep.compareTo((reponses.get(j).toString()))==0) return true;
                }

            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        } catch (IOException io) {
            Log.e("JSON", io.toString());
        }

        return false;

    }


    public String get_question(int id)
    {
        String quest="";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("test.JSON")));
            String json;
            StringBuffer buff = new StringBuffer();

            try {
                JSONArray enigme = parser.getJSONArray("enigme");
                int i=id-1;
                JSONObject ret = enigme.getJSONObject(i);
                quest=ret.getString("question");


            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        } catch (IOException io) {
            Log.e("JSON", io.toString());
        }
        return quest;
    }

    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent,REQUEST_CAM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAM:
                if(resultCode == Activity.RESULT_OK) {
                    ocr(data.getStringExtra("picFile"));
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
                    /*if(py<101) {
                        Log.d("bla", "pixel(" + px + "," + py + "): red: " + Color.red(pColor) + " blue: " + Color.blue(pColor) + " green: " + Color.green(pColor));
                    }*/
                    /*
                    if((Color.red(pColor)>89 && Color.red(pColor)<105) && (Color.blue(pColor)>95 && Color.blue(pColor)<120) && (Color.green(pColor)>100 && Color.green(pColor)<110)) {
                        mutableBitmap.setPixel(px,py,Color.argb(0,0,0,0));
                    }
                    else if(Color.red(pColor)>=105 && Color.blue(pColor)>=120 && Color.green(pColor)>=110) {
                        mutableBitmap.setPixel(px,py,Color.argb(0,255,255,255));
                    }*/



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
            Log.e(LOG_TAG, "Rotate or coversion failed: " + e.toString());
        }
    }
}
