package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.projet.tony.tx.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class fin extends Activity {

    private final static File IMG_DIR = new File(Environment.getExternalStorageDirectory().getPath()+"/OCR/images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewFin);
        LinearLayout globalLayout = (LinearLayout) findViewById(R.id.linearLayoutFin);

        try {
            JSONObject jsonHistoire = parseHistoire(getAssets().open("LostLetters.JSON"));
            TextView tv=(TextView) findViewById(R.id.titre);
            Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
            tv.setTypeface(font);
            tv.setTextSize(25);
            tv.setText("Fin ! Voici votre histoire...");
            TextView title=(TextView) findViewById(R.id.titleHist);
            title.setTypeface(font);
            title.setTextSize(18);
            title.setText(jsonHistoire.get("titre").toString());
            TextView intro=(TextView) findViewById(R.id.introHist);
            intro.setTypeface(font);
            intro.setTextSize(14);
            intro.setText(jsonHistoire.get("histoire").toString());

            int nbOfImages = IMG_DIR.listFiles().length;
            String questionTab = jsonHistoire.get("listQuestions").toString();
            questionTab = questionTab.replace("[","");
            questionTab = questionTab.replace("]","");
            String[] arrayQuestions = questionTab.split(",");
            for(int i=0;i<nbOfImages;i++) {
                TextView textView = new TextView(this);
                textView.setTypeface(font);
                textView.setTextSize(14);
                textView.setText(arrayQuestions[i]);
                textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                globalLayout.addView(textView,i);
            }
        }
        catch (Exception ioe) {
            ioe.printStackTrace();
        }


        final Button monbutt = (Button) findViewById(R.id.button);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font2);

        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fin.this, Page_menu.class);
                startActivity(intent);
            }
        });
        setContentView(scrollView);
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
