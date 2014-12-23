package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.projet.tony.tx.R;
import com.projet.tony.tx.background.Affichage;


public class MyActivity3 extends Activity {
    String txt = "Cette application permet de scanner des phrases de votre environnement puis de jouer avec les mots de celles-ci." +
            "\n\n Ce travail à été réalisé par :" +
            "\n Zins Pierre" +
            "\n Tchandjou Adrien" +
            "\n Smalbeen Tristan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        txt=retirer_accents(txt);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity3);
        TextView texte = (TextView) findViewById(R.id.propos);

         texte.setText(txt);

        TextView tv=(TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(40);
        tv.setText("A propos");

        TextView tv2=(TextView) findViewById(R.id.propos);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        tv2.setTypeface(font2);
        tv2.setTextSize(25);


        // fond
        Activity me =this;
        LinearLayout rl = (LinearLayout)me.findViewById(R.id.counterLayout);
        Affichage aff=new Affichage(me,rl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.my_activity3, menu);
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


    public String retirer_accents(String text)
    {

        String source	= text;

        source = source.replaceAll("[èéêë]","e");
        source = source.replaceAll("[àáâãäå]","a");
        source = source.replaceAll("[òóôõöø]","o");
        source = source.replaceAll("[ìíîï]","i");
        source = source.replaceAll("[ùúûü]","u");
        source = source.replaceAll("[ÿ]","y");
        source = source.replaceAll("[ç]","c");
        source = source.replaceAll("[Ç]","C");
        source = source.replaceAll("[°]","-");
        source = source.replaceAll("[Ñ]","N");
        source = source.replaceAll("[ÙÚÛÜ]","U");
        source = source.replaceAll("[ÌÍÎÏ]","I");
        source = source.replaceAll("[ÈÉÊË]","E");
        source = source.replaceAll("[ÒÓÔÕÖØ]","O");
        source = source.replaceAll("[ÀÁÂÃÄÅ]","A");
        return source;
    }
}
