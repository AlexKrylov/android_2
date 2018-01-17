package com.example.krylovag.colorapp;

import android.content.Context;

public class ColorSpec {
    static String getEffect (Context context, int position) {
        String [] strings  = context.getResources().getStringArray(R.array.effects);
        String effects = strings[position];
        return effects;
    }
}
