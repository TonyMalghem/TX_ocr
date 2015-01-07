package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.projet.tony.tx.R;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class change_tuto extends Activity {

    ImageView image;
    int etape=1;
    String texte="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_tuto);

        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(25);
        tv.setText("Tutoriel");

        change_txt();

        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.change_menu);

        Button bouton = (Button) findViewById(R.id.prev);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(etape>1)
             {
                 etape--;
                 change_img();

             }

            }
        });

        Button bouton2 = (Button) findViewById(R.id.next);
        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etape<=5)
                {
                    etape++;
                    change_img();
                }
                if(etape>5)
                {
                    /*Intent intent = new Intent(change_tuto.this, MyActivity.class);
                    startActivity(intent);*/
                    finish();
                }

            }
        });

    }

    public void change_txt() {
        if(etape==1)texte="Ceci est le menu principal, voici la liste des actions possibles : " +
                "\n - Scanner : vous permet d'utiliser la caméra du smartphone afin de scanner une phrase et l'utiliser plus tard" +
                "\n - Taper une phrase : vous permet de taper une nouvelle phrase ou de modifier celle scannée " +
                "\n - Changer mots : vous permet d'accéder à la partie principale de l'application (Suite)";
        if(etape==2)texte="Si vous voulez scanner un mot ou une phrase, celle-ci devra être assez courte (actuellement 5-6 mots pour cette version de l'application)." +
                " La caméra de votre téléphone se lance automatiquement, il ne vous reste plus qu'à prendre votre photo.";
        if(etape==3)texte="Dans \"Taper une phrase\", vous pouvez taper vous-même votre propre phrase ou encore modifier celle prise par la caméra." +
                " et en validant grâce au boutton prévu à cet effet.";
        if(etape==4) {
            texte = "Dans la partie \"Changer mots\", votre phrase s'affiche et il est possible de faire un clic sur n'importe quel mot (hormis les mots grisés qui ne sont pas pris en charge par l'application)." +
                    "Le mot est remplacé par un de ses synonymes.";
            Button bt=(Button) findViewById(R.id.next);
            bt.setText("Suivant");
        }
        if(etape==5) {
            texte = "A tout momment vous avez un aperçu des dernières phrases que vous avez générées." +
                    "\n Le boutton Reset vous permet de tout recommencer depuis le début.";
            Button bt=(Button) findViewById(R.id.next);
            bt.setText("Retour menu");
        }

        TextView tv=(TextView) findViewById(R.id.textView);
        tv.setText(texte);

    }



    public void change_img()
    {
        if(etape==1)image.setImageResource(R.drawable.change_menu);
        if(etape==2)image.setImageResource(R.drawable.appareil_photo);
        if(etape==3)image.setImageResource(R.drawable.changhe_phrase);
        if(etape==4)image.setImageResource(R.drawable.change_jeu_1);
        if(etape==5)image.setImageResource(R.drawable.change_jeu_2);

        change_txt();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_tuto, menu);
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
