package com.projet.tony.tx.LostLetter;

/**
 * Created by Tristan on 26/11/2014.
 */
public class MyProperties {
    private static MyProperties mInstance= null;

    public String jeu="Lost Letters";

    protected MyProperties(){}

    public static synchronized MyProperties getInstance(){
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}