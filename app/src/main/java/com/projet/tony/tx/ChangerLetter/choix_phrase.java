package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projet.tony.tx.R;

public class choix_phrase extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_phrase);



        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(30);
        tv.setText("Change Words");

        TextView tv2=(TextView) findViewById(R.id.field);
        tv2.setText(globalvar_change.getInstance().phrase);


        Button bouton = (Button) findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tv= (EditText) findViewById(R.id.field);
                globalvar_change.getInstance().phrase=tv.getText().toString();
                /*Intent intent = new Intent(choix_phrase.this, MyActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choix_phrase, menu);
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
