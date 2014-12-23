package com.projet.tony.tx.LostLetter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.tony.tx.ChangerLetter.MyActivity;
import com.projet.tony.tx.R;

public class lost_tuto extends Activity {

    ImageView image;
    int etape=1;
    String texte="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_tuto);

        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(25);
        tv.setText("Tutoriel");



        image = (ImageView) findViewById(R.id.imageView);
        change_img();


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
                if(etape<=6)
                {
                    etape++;
                    change_img();
                }
                if(etape>6)
                {
                    /*Intent intent = new Intent(lost_tuto.this, Page_menu.class);
                    startActivity(intent);*/
                    finish();
                }

            }
        });

    }

    public void change_txt() {
        if(etape==1)texte="Ceci est le menu principal, voici la liste des actions possibles : " +
                "\n - Jouer : vous donne accès au jeu (Histoire \"Lost letters\" par défaut)" +
                "\n - Mode création : vous permet de créer votre propre histoire avec vos propres questions " +
                "\n - Choix histoire : vous permet d'accéder aux différentes histoires disponibles sur votre téléphone";
        if(etape==2)texte="Dans ce menu vous pouvez choisir une histoire." +
                "\n Pour ajouter de nouvelles histoires (seul \"Lost Letters\" est disponible par défaut), vous pouvez utiliser le mode création, ou encore ajouter des fichiers d'histoire dans le dossier Lost_Letters sur votre carte SD.";
        if(etape==3)texte="Après avoir cliqué sur le bouton 'Jouer' vous arrivez sur cette page. S'affiche alors l'histoire que vous avez sélectionnée. Un boutton en bas de page vous permet d'accéder à la suite.";
        if(etape==4)texte="Vous êtes maintenant dans la partie principale de l'application : le jeu." +
                "\n Deux boutons s'offrent à vous : " +
                "\n  - Ouvrir la caméra : ouvre l'application gérant la caméra de votre appareil. Vous devrez alors prendre en photo un texte (non manuscrit et assez lisible) contenant au moins un mot répondant à la question posée." +
                "\n - Valider le résultat : Après avoir utilisé votre caméra pour lire un texte, utilisez ce bouton pour valider votre réponse. Si celle-ci est fausse, rien de grave, recommencez !";
        if(etape==5)texte="Le mode création vous permet de créer vos propres histoires. Vous pourrez partager en allant chercher votre fichier dans le dossier Lost_Letters de votre carte SD." +
                "\n Vous devez donc préciser un titre et le contenu de votre histoire avant de valider le tout.";
        if(etape==6) {
            texte = "Dans cette partie vous allez gérer la création des questions pour votre histoire. Pour cela tapez votre question ainsi que les résponses correspondantes séparées par des ';' " +
                    " Une fois cela fait, il ne vous reste plus qu'à valider votre question et à continuer tant que vous le souhaitez.";
        Button bt=(Button) findViewById(R.id.next);
            bt.setText("Retour menu");
        }
        TextView tv=(TextView) findViewById(R.id.textView);
        tv.setText(texte);

    }



    public void change_img()
    {
        if(etape==1)image.setImageResource(R.drawable.lost1);
        if(etape==2)image.setImageResource(R.drawable.lost2);
        if(etape==3)image.setImageResource(R.drawable.lost3);
        if(etape==4)image.setImageResource(R.drawable.lost4);
        if(etape==5)image.setImageResource(R.drawable.lost5);
        if(etape==6)image.setImageResource(R.drawable.lost6);

        change_txt();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lost_tuto, menu);
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
