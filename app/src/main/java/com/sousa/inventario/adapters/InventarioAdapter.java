package com.sousa.inventario.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sousa.inventario.Contagem;
import com.sousa.inventario.activity.DetalheContagem;
import com.sousa.inventario.R;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joao on 06/06/2015.
 */
public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.InventarioItem> {

    private List<Contagem> contagens = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;


    public InventarioAdapter(Context context, List<Contagem> contagens){
        this.contagens = contagens;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public InventarioItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View current = inflater.inflate(R.layout.item_lista, viewGroup, false);
        Contagem c = this.contagens.get(i);
        InventarioItem item = new InventarioItem(current, c);
        return item;
    }

    @Override
    public void onBindViewHolder(InventarioItem inventarioItem, int i) {
        Contagem c = contagens.get(i);
        inventarioItem.centro.setText(c.centro);
        inventarioItem.data.setText(DateFormat.getDateInstance().format(c.data));
        if(c.synched) {
            inventarioItem.synch.setVisibility(View.VISIBLE);
        }
        else{
            inventarioItem.synch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return contagens.size();
    }

    public class InventarioItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView centro;
        TextView data;
        ImageView synch;

        public InventarioItem(View itemView, Contagem c ) {
            super(itemView);
            centro = (TextView) itemView.findViewById(R.id.textView5);
            data = (TextView) itemView.findViewById(R.id.textView6);
            synch = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, DetalheContagem.class);
            TextView pl = (TextView) view.findViewById(R.id.textView5);
            CharSequence s = pl.getText();
            i.putExtra("id", String.valueOf(s));

            context.startActivity(i);
        }
    }
}
