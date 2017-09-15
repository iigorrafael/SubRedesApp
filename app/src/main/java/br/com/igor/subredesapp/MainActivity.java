package br.com.igor.subredesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.igor.subredesapp.model.Subrede;

public class MainActivity extends AppCompatActivity {

    private String ip;
    private String mascara;
    private Integer numeroIP;
    private Integer numeroMascara;
    private Subrede subrede;
    private ListView listView;
    ListAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewTables);

        // get data from the table by the ListAdapter
//        customAdapter = new ListAdapter(this, R.layout.listview_table, );

        listView.setAdapter(customAdapter);

    }


//    https://www.codeproject.com/Tips/850531/IP-Subnet-Calculator-Lib

    public void calcular(View view) {

        EditText editText1 = (EditText) findViewById(R.id.editTextIP);
        ip = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editTextMascara);
        mascara = editText2.getText().toString();

        // IP
        String[] st = ip.split(".");

        if (st.length != 4)
            throw new NumberFormatException(R.string.ipInvalido + ip);

        Integer i = 24;
        numeroIP = 0;

        for (int n = 0; n < st.length; n++) {

            int value = Integer.parseInt(st[n]);

            if (value != (value & 0xff)) {

                throw new NumberFormatException(R.string.ipInvalido + ip);
            }

            numeroIP += value << i;
            i -= 8;
        }

        // Mascara

                /* Netmask */
        st = mascara.split(".");

        if (st.length != 4)
            throw new NumberFormatException(R.string.mascaraInvalida

                    + mascara);

        i = 24;
        numeroMascara = 0;

        if (Integer.parseInt(st[0]) < 255) {

            throw new NumberFormatException(
                    R.string.fistByteMascara + "");
        }
        for (int n = 0; n < st.length; n++) {

            int value = Integer.parseInt(st[n]);

            if (value != (value & 0xff)) {

                throw new NumberFormatException(R.string.mascaraInvalida + mascara);
            }

            numeroMascara += value << i;
            i -= 8;

        }
/*
* see if there are zeroes inside netmask, like: 1111111101111 This is
* illegal, throw exception if encountered. Netmask should always have
* only ones, then only zeroes, like: 11111111110000
*/
        boolean encounteredOne = false;
        int ourMaskBitPattern = 1;

        for (i = 0; i < 32; i++) {

            if ((numeroMascara & ourMaskBitPattern) != 0) {

                encounteredOne = true; // the bit is 1
            } else { // the bit is 0
                if (encounteredOne == true)

                    throw new NumberFormatException(R.string.mascaraInvalida +
                            mascara + " (bit " + (i + 1) + ")");
            }

            ourMaskBitPattern = ourMaskBitPattern << 1;
        }
    }
}
