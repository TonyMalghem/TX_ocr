package com.projet.tony.tx;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*TODO
* intégration de la caméra dans l'appli*/

public class Activity_JSON extends ActionBarActivity {
    private TextView texte = null;
    private JSONObject parser=null;
    private JSONObject save=null;
    private int Id_Question=1; // question actuelle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__json);
        parser = getJSONObject("test.JSON");
        save = getJSONObject("save.JSON");


        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(get_question(Id_Question));



        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                final EditText editText = (EditText) findViewById(R.id.editText);
                String rep=editText.getText().toString();
                if(res(parser,rep,Id_Question)) editText.setText("True !");
                else editText.setText("False !");
                question_done(Id_Question);
                saveXP();
                changer_question();

                if(Id_Question!=-1) {
                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(get_question(Id_Question));
                }
                else
                {
                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText("Plus de question !");
                }

                // lecture du fichier save
                // a utiliser au démarage (faire un test pour savoir s'il existe dans le cas contraire ouvir le fichier save.JSON de l'asset)
                /*

                String ret = "";
                try {
                    InputStream inputStream = openFileInput("save.txt");

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();

                        while ( (receiveString = bufferedReader.readLine()) != null ) {
                            stringBuilder.append(receiveString);
                        }

                        inputStream.close();
                        ret = stringBuilder.toString();
                        final TextView textView2 = (TextView) findViewById(R.id.textView2);
                        textView2.setText(ret);
                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }
                */
            }

        });

    }

    // change de question pour une question pas encore répondue
public void changer_question()
{
    int i=0;
    String done="y";
    JSONArray enigme = null;
    try {
        enigme = save.getJSONArray("enigme");
        JSONObject ret=enigme.getJSONObject(i);
        while(done.compareTo("y")==0 && i<enigme.length())
        {
            ret = enigme.getJSONObject(i);
            done=ret.getString("Done");
            i++;
        }
        // si on a pas trouvé alors il n'y a plus de question sinon on met l'id réel de la question
        if(ret.getString("Done").compareTo("y")==0)i=-1;
    } catch (JSONException e) {
        e.printStackTrace();
    }





    Id_Question=i;

}


    public void saveXP()
    {
        File file = getFileStreamPath("save.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream writer = null;
        try {
            writer = openFileOutput(file.getName(), Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            writer.write(save.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSONObject getJSONObject(String Fichier)
    {
        BufferedReader reader = null;
        JSONObject parser=null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(Fichier)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json;
        StringBuffer buff = new StringBuffer();

        try {
            while ((json = reader.readLine()) != null) {
                buff.append(json + "\n");

                try {
                    parser = new JSONObject(buff.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parser;
    }

    public void question_done(int id)
    {
        try {
            id=id-1;
            JSONArray enigme = save.getJSONArray("enigme");
            JSONObject ret = enigme.getJSONObject(id);
            ret.put("Done","y");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean res(JSONObject parser,String rep,int id) {
        String resp = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("test.JSON")));
            String json;
            StringBuffer buff = new StringBuffer();

            try {
                JSONArray enigme = parser.getJSONArray("enigme");
                int i=id-1;
                JSONObject ret = enigme.getJSONObject(i);
                Log.d("JSON", "id : " + ret.getString("id"));
                Log.d("JSON", "question : " + ret.getString("question"));
                JSONArray reponses = ret.getJSONArray("reponse");
                for (int j = 0; j < reponses.length(); j++) {
                    Log.d("JSON", "reponse " + j + " : " + reponses.get(j));
                    if(rep.compareTo((reponses.get(j).toString()))==0) return true;
                }

            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        } catch (IOException io) {
            Log.e("JSON", io.toString());
        }

        return false;

    }


    public String get_question(int id)
    {
        String quest="";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("test.JSON")));
            String json;
            StringBuffer buff = new StringBuffer();

            try {
                JSONArray enigme = parser.getJSONArray("enigme");
                int i=id-1;
                JSONObject ret = enigme.getJSONObject(i);
                quest=ret.getString("question");


            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        } catch (IOException io) {
            Log.e("JSON", io.toString());
        }
        return quest;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__json, menu);
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
