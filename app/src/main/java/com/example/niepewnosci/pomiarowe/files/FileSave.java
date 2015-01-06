package com.example.niepewnosci.pomiarowe.files;

import android.content.Context;
import android.text.Editable;

import com.example.niepewnosci.pomiarowe.entity.Zestaw;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;

/**
 * Created by mateusz on 06.01.15.
 */
public class FileSave {
        public static void save(String filename, Zestaw zestaw, Context ctx) {
            FileOutputStream fos;
            try {
                fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(zestaw);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
