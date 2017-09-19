package br.com.igor.subredesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.igor.subredesapp.model.Subrede;

public class MainActivity extends AppCompatActivity {

    private String ip;
    private String mascara;
    private Integer numeroIP;
    private Integer numeroMascara;
    private Integer numberOfIPs;
    private Integer firstIP;
    private Integer lastIP;
    private Integer rede;
    private Integer numeroRedes;
    private Integer broadcast;
    private Double x;
    private List<Subrede> subredes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
//    https://www.codeproject.com/Tips/850531/IP-Subnet-Calculator-Lib

    public void calcular(View view) {

        EditText editText1 = (EditText) findViewById(R.id.editTextIP);
        ip = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editTextMascara);
        mascara = editText2.getText().toString();

        // IP
        String[] st = ip.split("\\.");
        Integer i = 24;
        try {
            if (st.length == 4) {
                numeroIP = 0;

                for (int n = 0; n < st.length; n++) {

                    int value = Integer.parseInt(st[n]);

                    if (value != (value & 0xff)) {

                        Toast.makeText(this, R.string.ipInvalido + ip, Toast.LENGTH_LONG).show();
                    }

                    numeroIP += value << i;
                    i -= 8;
                }

                // Mascara
                st = mascara.split("\\.");

                if (st.length == 4) {

                    i = 24;
                    numeroMascara = 0;

                    if (Integer.parseInt(st[0]) < 255) {

                        Toast.makeText(this, R.string.fistByteMascara + "", Toast.LENGTH_SHORT).show();

                    }
                    for (int n = 0; n < st.length; n++) {

                        int value = Integer.parseInt(st[n]);

                        if (value != (value & 0xff)) {

                            Toast.makeText(this, R.string.mascaraInvalida + mascara, Toast.LENGTH_SHORT).show();
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
                            if (encounteredOne == true) {

                            }

//                                Toast.makeText(this, R.string.mascaraInvalida +
//                                        mascara + " (bit " + (i + 1) + ")", Toast.LENGTH_SHORT).show();
                        }

                        ourMaskBitPattern = ourMaskBitPattern << 1;
                    }


                    getHostAddressRange();
                    getNumberOfHosts();
                    getIP();
                    getAvailableIPs(numberOfIPs);

                    subredes = new ArrayList<>();
                    for (int y = 0; y < numeroRedes; y++) {
                        Subrede subrede = new Subrede();
                        subrede.setNome("Subrede " + (y + 1));
                        subrede.setFirstHost(convertNumericIpToSymbolic(firstIP));
                        subrede.setEndRede(convertNumericIpToSymbolic(rede));
                        subrede.setLastHost(convertNumericIpToSymbolic(lastIP));
                        subrede.setBroadcast(convertNumericIpToSymbolic(broadcast));

                        firstIP = firstIP + x.intValue();
                        rede = rede + x.intValue();
                        lastIP = lastIP + x.intValue();
                        broadcast = broadcast + x.intValue();

                        subredes.add(subrede);
                    }

                    ListView listView = (ListView) findViewById(R.id.listViewTables);
                    ListViewTableAdapter listViewTableAdapter = new ListViewTableAdapter(subredes, this);
                    listView.setAdapter(listViewTableAdapter);


                } else {
                    Toast.makeText(this, "Mascara Inválida! "
                            + mascara, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "IP Inválido!" + ip, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getIP() {
        return convertNumericIpToSymbolic(numeroIP);
    }

    private String convertNumericIpToSymbolic(Integer ip) {
        StringBuffer sb = new StringBuffer(15);

        for (int shift = 24; shift > 0; shift -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((ip >>> shift) & 0xff));

            sb.append('.');
        }
        sb.append(Integer.toString(ip & 0xff));

        Log.i("LOL", sb.toString());
        return sb.toString();
    }


    /**
     * Get an arry of all the IP addresses available for the IP and netmask/CIDR
     * given at initialization
     *
     * @return
     */
    public List<String> getAvailableIPs(Integer numberofIPs) {

        ArrayList<String> result = new ArrayList<String>();
        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((numeroMascara << numberOfBits) == 0)
                break;
        }
        Integer numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;
        }

        Integer baseIP = numeroIP & numeroMascara;

        for (int i = 1; i < (numberOfIPs) && i < numberofIPs; i++) {

            Integer ourIP = baseIP + i;

            String ip = convertNumericIpToSymbolic(ourIP);

            Log.i("LOL", ip + " avalible ips");

            result.add(ip);
        }
        return result;
    }

    /**
     * Range of hosts
     *
     * @return
     */
    private void getHostAddressRange() {

        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((numeroMascara << numberOfBits) == 0)
                break;
        }
        numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;
        }


        Integer baseIP = numeroIP & numeroMascara;
        Log.i("LOL", String.valueOf(baseIP));
        Log.i("LOL", String.valueOf(numberOfIPs));
        firstIP = baseIP + 1;
        rede = baseIP;
        lastIP = baseIP + numberOfIPs - 1;
        broadcast = baseIP + numberOfIPs;

    }

    /**
     * Returns number of hosts available in given range
     *
     * @return number of hosts
     */
    private void getNumberOfHosts() {
        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((numeroMascara << numberOfBits) == 0)
                break;
        }

        x = Math.pow(2, (32 - numberOfBits));

        if (x == -1)
            x = 1D;

        Log.i("LOL", String.valueOf(x.longValue()) + " número de hosts");

        numeroRedes = 256 / x.intValue();
    }

}
