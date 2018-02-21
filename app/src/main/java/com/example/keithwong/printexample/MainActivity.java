package com.example.keithwong.printexample;

import android.accounts.Account;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.*;
import com.clover.sdk.v1.ServiceConnector;
import com.clover.sdk.v1.printer.job.PrintJobsConnector;
import com.clover.sdk.v1.printer.job.ViewPrintJob;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnector.OnServiceConnectedListener {

    protected View receipt;
    protected ViewGroup vg;
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
        vg = (ViewGroup) findViewById(R.id.printArea);
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.print_template, vg, true);
        receipt = findViewById(R.id.receipt);
        buildReceipt();

        //Hides the view on screen
        receipt.setVisibility(View.INVISIBLE);
    }

    protected void buildReceipt() {
        //Use LinearLayout to add on views to the receipt
        LinearLayout receiptTree = (LinearLayout) findViewById(R.id.receipt);

        //Use TextViews to add on custom text
        TextView heading = new TextView(this);
        heading.setText("Example Merchant");
        heading.setGravity(Gravity.CENTER);
        //Use the dimens.xml to specify font sizes for different printer sizes
        heading.setTextSize(getResources().getDimension(R.dimen.headingTextSize));

        //Add text element to the receipt
        receiptTree.addView(heading);

        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Pizza", "100.00");
        addLineItem(receiptTree, "Crackers", "10.00");
        addLineItem(receiptTree, "Cards", "5.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");
        addLineItem(receiptTree, "Bottled Water", "20.00");

    }

    private void addLineItem(LinearLayout tree, String itemName, String Price) {
        //Use ViewGroups to have text elements side-by-side
        RelativeLayout lineItem = new RelativeLayout(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView item = new TextView(this);
        item.setText(itemName);
        item.setId(1);
        item.setTextSize(getResources().getDimension(R.dimen.lineItem));
        lineItem.addView(item);

        TextView price = new TextView(this);
        price.setText(Price);
        item.setId(2);
        price.setTextSize(getResources().getDimension(R.dimen.lineItemPrice));
        price.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        lp.addRule(RelativeLayout.ALIGN_PARENT_END);
        lp.addRule(RelativeLayout.RIGHT_OF, item.getId());
        lp.addRule(RelativeLayout.ALIGN_BOTTOM, item.getId());
        lineItem.addView(price, lp);

        tree.addView(lineItem);
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
