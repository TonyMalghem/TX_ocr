package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projet.tony.tx.R;
import com.projet.tony.tx.background.Affichage;
import android.app.ProgressDialog;


public class MyActivity extends Activity {
    protected ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Log.d("pb","ok");

        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(30);
        tv.setText("Change Words");


        Button bouton = (Button) findViewById(R.id.changement);
        Button bouton2 = (Button) findViewById(R.id.apropos);
        Button bouton3 = (Button) findViewById(R.id.scan);
        Button bouton4 = (Button) findViewById(R.id.liremot);
        Button bouton5 = (Button) findViewById(R.id.tuto);
        Button bouton6 = (Button) findViewById(R.id.save);


        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        bouton.setTypeface(font2);
        bouton2.setTypeface(font2);
        bouton3.setTypeface(font2);
        bouton4.setTypeface(font2);
        bouton5.setTypeface(font2);
        bouton6.setTypeface(font2);



        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //compute();

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
        bouton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, photo_change.class);
                startActivity(intent);

            }
        });

        bouton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, choix_phrase.class);
                startActivity(intent);

            }
        });


        bouton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, change_tuto.class);
                startActivity(intent);

            }
        });

        bouton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, MyActivity4.class);
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

/*
    private void compute() {
        mProgressDialog = ProgressDialog.show(this, "En attente",
                "Cette opération est assez longue...", true);

        new Thread((new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MyActivity.this, MyActivity2.class);
                startActivity(intent);

                mProgressDialog.dismiss();
            }
        })).start();

    }
    */
}
