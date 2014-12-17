package com.projet.tony.tx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.projet.tony.tx.ChangerLetter.MyActivity;
import com.projet.tony.tx.LostLetter.Page_menu;
import com.projet.tony.tx.R;
import com.projet.tony.tx.background.Affichage;
import com.projet.tony.tx.creation_mode.creation;

import java.io.File;


public class Main_page extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // creation du dossier

        File dossier = new File(Environment.getExternalStorageDirectory() + File.separator + "Lost_letters");

        if (!dossier.exists()) {
            dossier.mkdir();
        }





        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(40);
        tv.setText("Lost Letters");



        ///////////////////////////
        // GESTION DES BOUTONS
        ///////////////////////////

        // LosteLetters
        final Button monbutt = (Button) findViewById(R.id.button);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        monbutt.setTypeface(font2);

        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Page_menu.this, Partie.class);
                Intent intent = new Intent(Main_page.this, Page_menu.class);
                startActivity(intent);
            }
        });

        // creation

        final Button monbutt2 = (Button) findViewById(R.id.button2);

        monbutt2.setTypeface(font2);

        monbutt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_page.this, MyActivity.class);
                startActivity(intent);
            }
        });




        // fond
        Activity me =this;
        LinearLayout rl = (LinearLayout)me.findViewById(R.id.counterLayout);
        Affichage aff=new Affichage(me,rl);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_menu, menu);
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



