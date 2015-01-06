package com.example.niepewnosci.pomiarowe;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.niepewnosci.pomiarowe.entity.Zestaw;
import com.example.niepewnosci.pomiarowe.files.FileSave;

import java.util.GregorianCalendar;

public class Wyniki extends ActionBarActivity {
	//Deklaracja zmiennych do wyświetlania  
	public String Swartosci_x = "";
    public String Swartosci_epsilon = "";
    public String Swartosci_epsilon_pow = "";
    public String Swyniki_finalne = "";
    public String Ua = "";
    public String average = "";
    public String e_pot = "";
    public String niepB = "";
    public String niepC = "";
    private Zestaw zestaw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyniki);
        //Przydzielenie zmiennym wartości

        Intent odbior = getIntent();
        zestaw = (Zestaw) odbior.getSerializableExtra("Zestaw");
        zestaw.run();
        Swartosci_x = zestaw.getDaneAsString();
        Ua = zestaw.getaUncertainty();
        average = zestaw.getAverageAsString();
        e_pot = zestaw.getSumDifferencesFromAveragePower();
        niepB = zestaw.getbUncertainty();
        niepC = zestaw.getcUncertainty();
        Swartosci_epsilon= zestaw.getDifferencesFromAverage();
        Swartosci_epsilon_pow=zestaw.getDifferencesFromAveragePower();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wyniki, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_wyniki,
                    container, false);
            TextView wartosci_x = (TextView) rootView.findViewById(R.id.wartosci_x);
          	wartosci_x.setText(((Wyniki)getActivity()).Swartosci_x);
          	//
          	TextView wartosci_epsilonx = (TextView) rootView.findViewById(R.id.wartosci_epsilon);
          	wartosci_epsilonx.setText(((Wyniki)getActivity()).Swartosci_epsilon);
          	//
          	TextView wyniki = (TextView) rootView.findViewById(R.id.wyniki_finalne);
          	wyniki.setSingleLine(false);
          	wyniki.setText("x̄  = "+((Wyniki)getActivity()).average+"\nΣε² = "+
          			((Wyniki)getActivity()).e_pot+"\nUa = "+((Wyniki)getActivity()).Ua+"\nUb = "+((Wyniki)getActivity()).niepB
          			+"\nUc = "+((Wyniki)getActivity()).niepC);
          	//
          	TextView wartosci_epsilon_pow = (TextView) rootView.findViewById(R.id.wartosci_epsilon_pow);
          	wartosci_epsilon_pow.setText(((Wyniki)getActivity()).Swartosci_epsilon_pow);
            return rootView;
        }
    }

    public void save() {
        final Zestaw zestaw = this.zestaw;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Podaj nazwę pliku:");
        final EditText input = new EditText(this);
        input.setText(GregorianCalendar.getInstance().getTime().toString());
        builder.setView(input);
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileSave.save(input.getText().toString(), zestaw, getApplicationContext());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
            }

}