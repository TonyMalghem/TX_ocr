package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projet.tony.tx.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class fin extends Activity {

    private final static File IMG_DIR = new File(Environment.getExternalStorageDirectory().getPath()+"/OCR/images");
    private RelativeLayout relativeLayout;

    private int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private static File baseDir;
    private static File saveFile;
    private static String game;
    private ImageView imageView;
    private LinearLayout globalLayout=null;

    public void res(){onResume();}
    @Override
    protected void onResume() {
        super.onResume();
        relativeLayout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_fin, null);
        globalLayout = (LinearLayout) relativeLayout.findViewById(R.id.linearLayoutFin);
        JSONObject jsonHistoire;


        game = MyProperties.getInstance().jeu;
        if(game.equals("Lost Letters")) {
            baseDir = new File(IMG_DIR.getPath()+"/lost_letters");
            saveFile = new File(Environment.getExternalStorageDirectory() + "/Lost_letters/save_Lost Letters.txt");
        }
        else {
            baseDir = new File(IMG_DIR.getPath()+"/"+game);
            saveFile = new File(Environment.getExternalStorageDirectory() + "/Lost_letters/save_" + game);
        }

        final File[] imgs = baseDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        int nbOfImages = (imgs!=null) ? imgs.length : 0;

        try {
            if(game.equals("Lost Letters")) {
                jsonHistoire = parseHistoire(getAssets().open("LostLetters.JSON"));
            }
            else {
                jsonHistoire = parseHistoire(new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/Lost_letters/"+game));
            }

            TextView tv=(TextView) relativeLayout.findViewById(R.id.titre);
            Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
            tv.setTypeface(font);
            tv.setTextSize(12);
           // tv.setText("Fin ! Voici votre histoire...");
            TextView title=(TextView) relativeLayout.findViewById(R.id.titleHist);
            title.setTypeface(font);
            title.setTextSize(12);
            title.setText(jsonHistoire.get("titre").toString());
            //TextView intro=(TextView) relativeLayout.findViewById(R.id.introHist);
            //intro.setTypeface(font);
            //intro.setTextSize(10);
           // intro.setText(jsonHistoire.get("histoire").toString());


            final Button buttonReset = (Button) relativeLayout.findViewById(R.id.next);
            //buttonReset.setTypeface(font2);
            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                pos++;
                res();
                    }
            });



            String questionTab = jsonHistoire.get("listQuestions").toString();
            questionTab = questionTab.replace("[","");
            questionTab = questionTab.replace("]","");
            String[] arrayQuestions = questionTab.split(",");
            for(int i=0;i<arrayQuestions.length;i++) {

                if (pos == i && i < imgs.length)
                {

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                //params.addRule(RelativeLayout.BELOW, R.id.introHist);
                globalLayout.setLayoutParams(params);
                TextView textView = new TextView(this);
                textView.setPadding(0, 0, 0, 0);
                textView.setTypeface(font);
                textView.setTextSize(10);
                textView.setText(arrayQuestions[i]);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, -10, 0, 0);
                textView.setLayoutParams(layoutParams);
                imageView = new ImageView(this);
                imageView.setPadding(0, 0, 0, 0);
                Bitmap bmp = BitmapFactory.decodeFile(baseDir + "/" + imgs[i].getName());

                if (i < imgs.length) imageView.setImageBitmap(bmp);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                globalLayout.addView(textView);
                globalLayout.addView(imageView);
            }
            }
        }
        catch (Exception ioe) {
            ioe.printStackTrace();
            Log.e("blbl","error");
        }

        final Button monbutt = (Button) relativeLayout.findViewById(R.id.button);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font2);

        try {


            monbutt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fin.this, Page_menu.class);
                    startActivity(intent);
                    finish();
                }
            });

            final Button buttonReset = (Button) relativeLayout.findViewById(R.id.buttonReset);
            buttonReset.setTypeface(font2);
            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imgs!=null)for (File f : imgs) {
                        f.delete();
                    }
                    saveFile.delete();
                    Intent intent = new Intent(fin.this, Page_menu.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        catch (Exception e){Log.d("n","bug bug");}
        setContentView(relativeLayout);
    }

    public JSONObject parseHistoire(InputStream inputStream) throws IOException, JSONException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        try {
            return readHistory(jsonReader);
        }
        finally {
            jsonReader.close();
        }
    }

    public JSONObject readHistory(JsonReader jsonReader) throws IOException, JSONException {
        JSONObject returnObject = new JSONObject();
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if(name.equals("titre") && jsonReader.peek()!= JsonToken.NULL) {
                returnObject.put("titre",jsonReader.nextString());
            }
            else if(name.equals("histoire") && jsonReader.peek()!= JsonToken.NULL) {
                returnObject.put("histoire", jsonReader.nextString());
            }
            else if(name.equals("enigme")) {
                returnObject.put("listQuestions",readEnigmes(jsonReader));
            }
            else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return returnObject;
    }

    public ArrayList<String> readEnigmes(JsonReader jsonReader) throws IOException, JSONException {
        ArrayList<String> retEnigmes = new ArrayList<String>();
        jsonReader.beginArray();
        while(jsonReader.hasNext()) {
            retEnigmes.add(readEnigme(jsonReader));
        }
        jsonReader.endArray();
        return retEnigmes;
    }

    public String readEnigme(JsonReader jsonReader) throws IOException {
        String ret = null;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if(name.equals("question") && jsonReader.peek() != JsonToken.NULL) {
                ret = jsonReader.nextString();
            }
            else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return ret;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fin, menu);
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
}
