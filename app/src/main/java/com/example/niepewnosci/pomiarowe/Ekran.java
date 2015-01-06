package com.example.niepewnosci.pomiarowe;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.niepewnosci.pomiarowe.entity.Zestaw;
import com.example.niepewnosci.pomiarowe.files.FileRead;
import com.example.niepewnosci.pomiarowe.files.FileSave;

public class Ekran extends ActionBarActivity {
	List<Float> data = new ArrayList<Float>();
    private MojAdapter adapter;

    private Zestaw zestaw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekran);
        final EditText actv= (EditText)findViewById(R.id.wejscie); 
        instalujAdapter();
       
        actv.setOnEditorActionListener(new OnEditorActionListener() {                     
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE ) {
            		data.add(Float.valueOf(actv.getText().toString()));
            		adapter.notifyDataSetChanged();
                    return true;
                	}
                return false;
            }
            	        
        });
    }
    	        

                
    public void sprawdz_zawartosc(View view){
    	/*EditText wejscie_B = (EditText) findViewById(R.id.wejscie_B);
    	 String dane_wejscie_B = wejscie_B.getText().toString();*/
    	if(data.size() == 0){
    		AlertDialog alertDialog = new AlertDialog.Builder(
                    Ekran.this).create();
    alertDialog.setTitle("Błąd");
    alertDialog.setMessage("Wpisz dane pomiarowe.");
    alertDialog.show();
    	}
    	else srednia();
    }
    public void usuwanie_elementu (View v){
              Ziarenko element_do_usuniecia = (Ziarenko)v.getTag();
              adapter.remove(element_do_usuniecia);
        adapter.notifyDataSetChanged();
    }
                
    private void instalujAdapter(){
    	adapter = new MojAdapter(this, data);
    	ListView lista = (ListView)findViewById(R.id.lista);
    	lista.setAdapter(adapter);
    }
    public void srednia (){

       EditText wejscie_B = (EditText) findViewById(R.id.wejscie_B);
        String dane_wejscie_B = wejscie_B.getText().toString();
        float deviceUncertainty;
        if(dane_wejscie_B.length()<=0) {
            deviceUncertainty=0;
        } else {
            deviceUncertainty = (float) Float.valueOf(dane_wejscie_B);
        }


    Zestaw zestaw = new Zestaw(data);
    zestaw.setDeviceUncertainty(deviceUncertainty);
    Intent wysylka = new Intent(this,Wyniki.class);
    wysylka.putExtra("Zestaw", zestaw);
    startActivity(wysylka);}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ekran, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_load) {
            openFilesList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFilesList() {
        final FileRead fileRead = new FileRead(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wybierz zestaw");
        String[] fileList = fileRead.loadFilesList();
        builder.setItems(fileList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fileRead.loadFile(which);
            }
        });
         AlertDialog dialog = builder.create();
        dialog.show();
    }
}
