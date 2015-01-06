package com.example.niepewnosci.pomiarowe.files;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mateusz on 06.01.15.
 */
public class FileRead {
    Context mCtx;
    public FileRead(Context context) {
        mCtx = context;
    }

    public String[] loadFilesList(){
        File[] list = mCtx.getFilesDir().listFiles();
        String[] listString = new String[list.length];
        for(int i = 0;i<list.length;i++) {
            listString[i] = list[i].getName().toString();
        }
        return listString;
    }

    public void loadFile(int which) {
        System.out.print(which);
    }
}
