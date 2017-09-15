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

    private String IP;
    private String mascara;
    private Subrede subrede;
    private ListView listView;
    ListAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewTables);

        // get data from the table by the ListAdapter
        customAdapter = new ListAdapter(this, R.layout.listview_table, );

        listView.setAdapter(customAdapter);

    }

    public void calcular(View view) {

        EditText editText1 = (EditText) findViewById(R.id.editTextIP);
        IP = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editTextMascara);
        mascara = editText2.getText().toString();
    }
}
