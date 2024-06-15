package com.example.olx_clone;

import android.content.Context;
import android.widget.Toast;

public class Utilis {
    public static void toast(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static long timeTamp(){
        return System.currentTimeMillis();
    }
}
