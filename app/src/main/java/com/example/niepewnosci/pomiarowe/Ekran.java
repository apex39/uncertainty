package com.example.niepewnosci.pomiarowe;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

public class Ekran extends ActionBarActivity {
	List<Float> data = new ArrayList<Float>();
    Float deviceUncertainty =0f;
    private MojAdapter adapter;
    EditText dataEditText;
    EditText bEditText;
    private Zestaw zestaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekran);

        adapter = new MojAdapter(this, data);
        ListView lista = (ListView)findViewById(R.id.lista);
        lista.setAdapter(adapter);

        dataEditText = (EditText)findViewById(R.id.dataEditText);
        dataEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(v.getText().toString())) {
                    data.add(Float.valueOf(v.getText().toString()));
                    adapter.notifyDataSetChanged();
                    v.setText(null);
                    return true;
                }
                return false;
            }

        });
        bEditText = (EditText)findViewById(R.id.bEditText);
        bEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(v.getText().toString())) {
                    deviceUncertainty= Float.valueOf(v.getText().toString());
                    return false;
                }
                return false;
            }

        });
    }

    public boolean sprawdz_zawartosc(){
    	if(data.size() == 0){
    		AlertDialog alertDialog = new AlertDialog.Builder(
                    Ekran.this).create();
            alertDialog.setTitle("Błąd");
            alertDialog.setMessage("Wpisz dane pomiarowe");
            alertDialog.show();
            return false;
        }
        return true;
    }

    public void usuwanie_elementu (View v){

        Float itemDelete = adapter.getItem((int)v.getTag());
        adapter.remove(itemDelete);
        /*MojAdapter.ViewHolder holder = (MojAdapter.ViewHolder) v.getTag();
        Float itemDelete = adapter.getItem((int)holder.element_position);*/
        data.remove(itemDelete);

        adapter.notifyDataSetChanged();
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void sendZestaw(View view){
    if(sprawdz_zawartosc()) {
        Zestaw zestaw = new Zestaw(data);
        zestaw.setDeviceUncertainty(deviceUncertainty);
        Intent wysylka = new Intent(this, Wyniki.class);
        wysylka.putExtra("Zestaw", zestaw);
        startActivity(wysylka);}
    }


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
                Zestaw receivedZestaw = fileRead.getZestawFile(which);
                loadZestaw(receivedZestaw);
                dialog.dismiss();
            }
        });
         AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadZestaw(Zestaw receivedZestaw) {

        data = receivedZestaw.getDane();
        deviceUncertainty = receivedZestaw.getDeviceUncertainty();

        bEditText.setText(deviceUncertainty.toString());
        adapter.clear();
        adapter.addAll(data);
        //adapter.notifyDataSetChanged();
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
