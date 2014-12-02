package com.projet.tony.tx.ChangerLetter;

/**
 * Created by Tristan on 02/12/2014.
 */
public class globalvar_change {
    private static globalvar_change mInstance= null;

    public String phrase="la balle roule sur la ligne";

    protected globalvar_change(){}

    public static synchronized globalvar_change getInstance(){
        if(null == mInstance){
            mInstance = new globalvar_change();
        }
        return mInstance;
    }
}