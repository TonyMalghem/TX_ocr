package com.projet.tony.tx.LostLetter;

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
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.projet.tony.tx.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class choix_histoire extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_histoire);



        ///////////////////////
        // GESTION DU TITRE  //
        ///////////////////////

        TextView tv=(TextView) findViewById(R.id.titre);
        Typeface font = Typeface.createFromAsset(getAssets(), "KQ.ttf");
        tv.setTypeface(font);
        tv.setTextSize(20);
        addItemsOnSpinner();


        final Button monbutt = (Button) findViewById(R.id.button);
        monbutt.setTypeface(font);

        monbutt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                Toast.makeText(getApplicationContext(), "Vous avez selectionné "+spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                MyProperties.getInstance().jeu=spinner.getSelectedItem().toString();
                /*Intent intent = new Intent(choix_histoire.this, Page_menu.class);
                startActivity(intent);*/
                finish();
            }
        });

    }


    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Lost Letters");

        File rep = new File(Environment.getExternalStorageDirectory() + File.separator + "Lost_letters");
        if ( rep.isDirectory ( ) ) {
            File[] listrep = rep.listFiles();
            for(int i=0;i<listrep.length;i++)
            {
                if(listrep[i].getName().split("_")[0].compareTo("save")!=0) list.add(listrep[i].getName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choix_histoire, menu);
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
