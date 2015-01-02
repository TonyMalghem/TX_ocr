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
import android.widget.TextView;
import android.widget.EditText;

import com.projet.tony.tx.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class creation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv = (TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(30);
        tv.setText("Mode creation");


        final Button monbutt = (Button) findViewById(R.id.button);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font2);
        final EditText monedit = (EditText) findViewById(R.id.field);
        final EditText monedit2 = (EditText) findViewById(R.id.editText2);
        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String fichier = monedit.getText().toString().replaceAll(" ", "");
                create_file(fichier, monedit2.getText().toString());
                Intent intent = new Intent(creation.this, Ajout_question.class);
                intent.putExtra("fichier", fichier);
                startActivity(intent);
                finish();
            }
        });
    }


    public void create_file(String name, String histoire) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Lost_letters/" + name + ".txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedOutputStream writer = null;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        JSONObject base = new JSONObject();
        JSONArray enigmes = new JSONArray();
        try {
            base.put("titre", name);
            base.put("histoire", histoire);
            base.put("enigme", enigmes);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            writer.write(base.toString().getBytes());
            Log.d("creer : ", base.toString());

        } catch (IOException e) {

            e.printStackTrace();
        }


        try {
            writer.close();
            Log.d("save : ", "ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.creation, menu);
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
