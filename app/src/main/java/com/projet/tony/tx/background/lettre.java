package com.projet.tony.tx.background;



import static java.lang.Math.sin;

/**
 * Created by Tristan on 13/10/2014.
 */
public class lettre {
    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    int font;
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public lettre(int x, int y, String lettre, int an, int font) {
        this.anim=an;
        this.x = x;
        this.y = y;
        this.lettre = lettre;
        this.font =font;
    }

    int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLettre() {
        return lettre;
    }

    int y;

    public int getAnim() {
        return anim;
    }

    public void setAnim(int anim) {
        this.anim = anim;
    }

    int anim;
    String lettre;


    public void move()
    {
        if(anim==1){x+=2;y+=2;}
        if(anim==2){x-=2;y+=2;}
        if(anim==3){x-=2;y-=2;}
        if(anim==4){x+=2;y-=2;}
        if(anim==5){x+=2;y+=(int)(sin(x/10)*5)+2;}
        if(anim==6){x+=2;y-=(int)(sin(x/10)*5)+2;}
        if(anim==7){x+=(int)(sin(y/10)*5)+2;y+=2;}
        if(anim==8){x+=(int)(sin(y/10)*5)+2;y-=2;}
    }

    public boolean is_in(int pox,int poy)
    {
        if(x<pox && y<poy && x>0 && y>0) return true;
        else return false;
    }

}
