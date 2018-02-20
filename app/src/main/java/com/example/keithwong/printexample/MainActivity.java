package com.example.keithwong.printexample;

import android.accounts.Account;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.*;
import com.clover.sdk.v1.ServiceConnector;
import com.clover.sdk.v1.printer.job.PrintJobsConnector;
import com.clover.sdk.v1.printer.job.ViewPrintJob;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnector.OnServiceConnectedListener {

    protected View receipt;
    protected PrinterConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Account account = CloverAccount.getAccount(this);
        connector = new PrinterConnector(this, account, this);
        connector.connect();



        createPrintLayout();
        print();
    }

    @Override
    protected void onStop() {
        super.onStop();
        connector.disconnect();
    }

    protected void print() {
        new AsyncTask<Void, Void, String> () {
            @Override
            protected String doInBackground(Void... Voids) {
                try {

                    Printer printer = getPrinter();

                    ViewPrintJob.Builder builder = new ViewPrintJob.Builder();
                    builder.view(receipt);
                    builder.printToAny(true);
                    ViewPrintJob job = builder.build();

                    return new PrintJobsConnector(MainActivity.this).print(printer, job);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i("AsyncTask", "Finished printing");
            }
        }.execute();
    }

    protected void createPrintLayout() {
        ViewGroup vg = (ViewGroup) findViewById(R.id.printArea);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.print_template, vg, true);
        receipt = findViewById(R.id.receipt);
    }

    //Selects the default receipt printer, which can be set on the Clover device
    private Printer getPrinter() {
        try {
            List<Printer> printers = connector.getPrinters(Category.RECEIPT);
            return printers.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onServiceConnected(ServiceConnector connector) {
        Log.i("ServiceConnector", "service connected: " + connector);
    }

    @Override
    public void onServiceDisconnected(ServiceConnector connector) {
        Log.i("ServiceConnector", "service disconnected: " + connector);
    }


}
