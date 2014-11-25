package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projet.tony.tx.R;
import com.projet.tony.tx.background.Affichage;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(40);
        tv.setText("Change Letters");


        Button bouton = (Button) findViewById(R.id.changement);
        Button bouton2 = (Button) findViewById(R.id.apropos);
        Button bouton3 = (Button) findViewById(R.id.scan);



        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        bouton.setTypeface(font2);
        bouton2.setTypeface(font2);
        bouton3.setTypeface(font2);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                startActivity(intent);

            }
        });
        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, MyActivity3.class);
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
       // getMenuInflater().inflate(R.menu, menu);
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