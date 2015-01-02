package com.projet.tony.tx.creation_mode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projet.tony.tx.R;

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

public class Ajout_question extends Activity {

    protected JSONObject jsonfile=null;
    protected String fichier;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_question);

        //////////////////////
        // RECUP VAR
        /////////////////////
        Intent intent = getIntent();
        fichier = intent.getStringExtra("fichier");

        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        jsonfile=getJSONObject(fichier+".txt");

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(25);
        tv.setText("Ajout de question");





        ////////////////////////
        // GESTION DES BOUTTONS
        ///////////////////////

        final Button monbutt = (Button) findViewById(R.id.button);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font2);

        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Valider();


                jsonfile=getJSONObject(fichier+".txt");
                Log.d("ajout : ","ok");
            }
        });


        final Button monbutt2 = (Button) findViewById(R.id.button2);

        monbutt2.setTypeface(font2);

        monbutt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Valider();
                /*Intent intent = new Intent(Ajout_question.this, Page_menu.class);
                startActivity(intent);*/
                finish();

            }
        });

        final Button monbutt3 = (Button) findViewById(R.id.button3);

        monbutt3.setTypeface(font2);

        monbutt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Ajout_question.this, Page_menu.class);
                startActivity(intent);*/
                finish();
            }
        });






    }
    protected Integer id=0;

    public void Valider()
    {

        JSONArray enigmes= null;
        //nouvealle enigme
        JSONObject enigme = new JSONObject();
        // recup les enigmes
        try {
            enigmes = jsonfile.getJSONArray("enigme");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray reponses=new JSONArray();

        final EditText monedit =(EditText)findViewById(R.id.field);
        final EditText monedit2 =(EditText)findViewById(R.id.editText2);

        try {
            id++;
            enigme.put("id",id);
            enigme.put("question",monedit.getText().toString());


            // tableau de réponses
            String reps[]=monedit2.getText().toString().split(";");
            for(int i=0;i<reps.length;i++)reponses.put(reps[i]);


            enigme.put("reponse",reponses);
            enigmes.put(enigme);


            // inscription dans le fichier

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +"Lost_letters/"+ fichier+".txt");

            BufferedOutputStream writer = null;
            try {
                writer = new BufferedOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Log.d("JSON",jsonfile.toString());
                writer.write(jsonfile.toString().getBytes());
               monedit.setText("");
                monedit2.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ajout_question, menu);
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


    public JSONObject getJSONObject(String Fichier)
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
}
