package br.com.igor.subredesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.igor.subredesapp.model.Subrede;

/**
 * Created by Aluno on 15/09/2017.
 */

public class ListViewTableAdapter extends BaseAdapter {

    private List<Subrede> subRedes;
    private Context context;

    public ListViewTableAdapter(List<Subrede> subRedes, Context context) {
        this.subRedes = subRedes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return subRedes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            view = vi.inflate(R.layout.listview_table, null);
        }

        Subrede subrede = subRedes.get(position);

        if(subrede != null){
            TextView textViewNome = (TextView) view.findViewById(R.id.textViewNome);
            TextView textViewRedeIP = (TextView) view.findViewById(R.id.textViewRedeIP);
            TextView textViewFistIP = (TextView) view.findViewById(R.id.textViewPrimeiroHostIP);
            TextView textViewLastIP = (TextView) view.findViewById(R.id.textViewUltimoHostIP);
            TextView textViewBroadcastIP = (TextView) view.findViewById(R.id.textViewBroadcastIP);

            TextView textViewRede = (TextView) view.findViewById(R.id.textViewRede);
            TextView textViewFist = (TextView) view.findViewById(R.id.textViewPrimeiroHost);
            TextView textViewLast = (TextView) view.findViewById(R.id.textViewUltimoHost);
            TextView textViewBroadcast = (TextView) view.findViewById(R.id.textViewBroadcast);

            textViewNome.setText(subrede.getNome());
            textViewRedeIP.setText(subrede.getEndRede());
            textViewFistIP.setText(subrede.getFirstHost());
            textViewLastIP.setText(subrede.getLastHost());
            textViewBroadcastIP.setText(subrede.getBroadcast());

            textViewRede.setText("Rede: ");
            textViewFist.setText("Primeiro IP: ");
            textViewLast.setText("Ultimo IP: ");
            textViewBroadcast.setText("Broadcast");

        }

        return view;
    }
}
