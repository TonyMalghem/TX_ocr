package com.projet.tony.tx.background;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;

/**
 * Created by Tristan on 30/10/2014.
 */
public class Affichage {

    private Handler handler = new Handler();
    protected static ArrayList<lettre> lts = new ArrayList<lettre>();
    protected Activity me =null;
    protected static int largeur;
    protected static int hauteur;



    protected LinearLayout rl;



    public Affichage(Activity A,LinearLayout ll) {
        me=A;


        SampleView SV=new SampleView(me);


        Display ecran = me.getWindowManager().getDefaultDisplay();
        largeur= ecran.getWidth();
        hauteur= ecran.getHeight();
        creer_lettres();


        //setContentView(SV);


        //rl = (LinearLayout)me.findViewById(R.id.counterLayout);
        rl=ll;
        rl.addView(SV);


        //setContentView(rl);




        handler.postDelayed(runnable, 100);



    }
    protected void creer_lettres()
    {
        //on vérifie qu'il n'y a pas trop de lettres à l'écran
        for(int i=0;i<lts.size();i++)
        {
            lettre l=lts.get(i);
            if(!l.is_in(largeur,hauteur)) lts.remove(l);
        }
        while(lts.size()<10)
        { int Min=0;
            int Max=largeur;
            int x = Min + (int) (Math.random() * ((Max - Min) + 1));
            Max=hauteur;

            int y = Min + (int) (Math.random() * ((Max - Min) + 1));


            Min=25;
            Max=100;
            int newlt = Min + (int) (Math.random() * ((Max - Min) + 1));

            Min=1;
            Max=8;
            int anim = Min + (int) (Math.random() * ((Max - Min) + 1));

            Min=1;
            Max=6;

            int font = Min + (int) (Math.random() * ((Max - Min) + 1));
            lts.add(new lettre(x,y,Character.toString((char)newlt),anim,font));
        }

    }
    private String texte="";
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
      /* do what you need to do */
            for(int i=0;i<lts.size();i++)
            {
                lettre l=lts.get(i);
                l.move();
            }


            SampleView SV=new SampleView(me);

            creer_lettres();

            rl.removeAllViews();
            rl.addView(SV);
            // setContentView(SV);



      /* and here comes the "trick" */
            handler.postDelayed(this, 100);
        }
    };

    private static class SampleView extends View {

        // CONSTRUCTOR
        public SampleView(Context context) {
            super(context);
            setFocusable(true);

        }

        protected Bitmap creerbmp(Canvas canvas, int pox, int poy, String text,int font) {

            Paint paint = new Paint();
            Bitmap b = Bitmap.createBitmap(50, 50, Bitmap.Config.ALPHA_8);
            Canvas c = new Canvas(b);
            c.drawRect(0, 0, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setTextSize(40);
            paint.setTextScaleX(1.f);
            paint.setAlpha(0);

            // gestion des fonts
            Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
            if(font==1)tf = Typeface.create("Helvetica",Typeface.BOLD);
            else if(font==2)tf = Typeface.create("Helvetica",Typeface.ITALIC);
            else if(font==3)tf = Typeface.create("Helvetica",Typeface.NORMAL);
            else if(font==4)tf = Typeface.create("serif",Typeface.NORMAL);
            else if(font==5)tf = Typeface.create("serif",Typeface.ITALIC);
            else if(font==6)tf = Typeface.create("serif",Typeface.BOLD);




            paint.setTypeface(tf);
            paint.setAntiAlias(true);
            c.drawText(text, 10, 40, paint);
            paint.setColor(Color.WHITE);

            canvas.drawBitmap(b, pox, poy, paint);
            return b;
        }



        protected void onDraw(Canvas canvas) {



            // creation du fond

            Paint paint = new Paint();
            Bitmap b = Bitmap.createBitmap(largeur, hauteur, Bitmap.Config.ALPHA_8);
            Canvas c = new Canvas(b);
            c.drawRect(0, 0, largeur, hauteur, paint);
            paint.setColor(Color.WHITE);

            canvas.drawBitmap(b, 0, 0, paint);

            // affichage des lettres
            for(int i=0;i<lts.size();i++)
            {
                lettre l=lts.get(i);
                b=creerbmp(canvas,l.getX(),l.getY(),l.getLettre(),l.getFont());
            }






        }

    }
}
