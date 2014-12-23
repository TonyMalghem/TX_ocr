package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.projet.tony.tx.R;


public class Apropos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);


        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(40);


        TextView tv2=(TextView) findViewById(R.id.content);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        tv2.setTypeface(font2);
        String txt = "Cette application permet de scanner des mots de votre environnement puis de les utiliser afin de répondre à différentes énigmes." +
                "\n\n Ce travail à été réalisé par :" +
                "\n Malghem Tony" +
                "\n Smalbeen Tristan";
        tv2.setText(txt);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apropos, menu);
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
