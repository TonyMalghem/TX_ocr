package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projet.tony.tx.R;
import com.projet.tony.tx.background.Affichage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Partie extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);


        // jouer
        final Button monbutt = (Button) findViewById(R.id.button);
        Typeface font3 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font3);

        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Page_menu.this, Partie.class);
                //Intent intent = new Intent(Page_menu.this, Activity_JSON.class);
                Intent intent = new Intent(Partie.this, Activity_JSON.class);
                startActivity(intent);
                finish();
            }
        });


        // nom du jeu si ce n'est pas Lost Letters on ira le chercher dans le dossier sinon on le rajoute dans le dossier
        //String jeu="Lost Letters";
        String jeu= MyProperties.getInstance().jeu;
        JSONObject jeu_content=null;


            if(jeu=="Lost Letters")
            {
                jeu_content=getJSONObjectdefaut("LostLetters.JSON");
            }
            else
            {
                jeu_content=getJSONObjectautre(jeu.replaceAll(" ", ""));
            }

            ///////////////////////
            // GESTION DU TITRE  //
            ///////////////////////

            TextView tv=(TextView) findViewById(R.id.titre);
            Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
            tv.setTypeface(font);
            tv.setTextSize(40);
            try {
                tv.setText(jeu_content.get("titre").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        // histoire

        TextView hist=(TextView) findViewById(R.id.histoire);
        Typeface font2 = Typeface.DEFAULT;

        try {
            hist.setText(jeu_content.get("histoire").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


            // fond
            Activity me =this;
            LinearLayout rl = (LinearLayout)me.findViewById(R.id.counterLayout);
            Affichage aff=new Affichage(me,rl);



        }




    public JSONObject getJSONObjectdefaut(String Fichier)
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

    public JSONObject getJSONObjectautre(String Fichier)
    {
        BufferedReader reader = null;
        JSONObject parser=null;
        try {
            reader = new BufferedReader(new FileReader((Environment.getExternalStorageDirectory() + File.separator +"Lost_letters/"+Fichier)));
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.partie, menu);
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
