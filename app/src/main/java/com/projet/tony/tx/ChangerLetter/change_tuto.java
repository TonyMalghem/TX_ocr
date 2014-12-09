package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.projet.tony.tx.R;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class change_tuto extends Activity {

    ImageView image;
    int etape=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_tuto);

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
                if(etape<5)
                {
                    etape++;
                    change_img();
                }
                if(etape==5)
                {
                    Intent intent = new Intent(change_tuto.this, MyActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void change_img()
    {
        if(etape==1)image.setImageResource(R.drawable.change_menu);
        if(etape==2)image.setImageResource(R.drawable.changhe_phrase);
        if(etape==3)image.setImageResource(R.drawable.change_jeu_1);
        if(etape==4)image.setImageResource(R.drawable.change_jeu_2);
        if(etape==5)image.setImageResource(R.drawable.appareil_photo);

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
