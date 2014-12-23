package com.projet.tony.tx.ChangerLetter;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.projet.tony.tx.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyActivity4 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity4);
        TextView text = (TextView)findViewById(R.id.aff);
        String essai = Uri.parse("sdcard/OCR/images/saves.txt").toString();
        Typeface font2 = Typeface.createFromAsset(getAssets(), "PRC.ttf");
        text.setTypeface(font2);
        text.setTextSize(20);
        //text.setText(Environment.getExternalStorageDirectory().getAbsolutePath() + "/saves.txt");
        try {
            text.setText(retirer_accents(readFileAsString("sdcard/OCR/images/saves.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String readFileAsString(String filePath)
            throws java.io.IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            fileData.append(buf, 0, numRead);
        }
        reader.close();
        return fileData.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_my_activity4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
