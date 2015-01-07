package com.example.niepewnosci.pomiarowe.files;

import android.content.Context;

import com.example.niepewnosci.pomiarowe.entity.Zestaw;

import org.apache.http.impl.entity.EntityDeserializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by mateusz on 06.01.15.
 */
public class FileRead {
    Context mCtx;
    File[] patchList;
    String[] fileNamesList;

    public FileRead(Context context) {
        mCtx = context;
    }

    public String[] loadFilesList(){
        File[] list = mCtx.getFilesDir().listFiles();
        String[] listString = new String[list.length];
        for(int i = 0;i<list.length;i++) {
            listString[i] = list[i].getName().toString();
        }
        this.patchList = list;
        this.fileNamesList = listString;
        return listString;
    }

    public Zestaw getZestawFile(int which) {
        Zestaw zestaw = null;
        FileInputStream fis;
        try {
            fis = mCtx.openFileInput(fileNamesList[which]);
            ObjectInputStream ois = new ObjectInputStream(fis);
            zestaw = (Zestaw) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return zestaw;
    }
}
