package com.sousa.inventario.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sousa.inventario.AppModel;
import com.sousa.inventario.activity.ListaContagens;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.activity.DetalheContagem;
import com.sousa.inventario.R;

import java.text.DateFormat;

/**
 * Created by Joao on 06/06/2015.
 */
public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.InventarioItem> {

    public static final int NAVIGATE = 90;
    private LayoutInflater inflater;
    private Context context;


    public InventarioAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public InventarioItem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View current = inflater.inflate(R.layout.item_lista, viewGroup, false);
        Contagem c = AppModel.getInstance().getContagens().get(i);
        InventarioItem item = new InventarioItem(current, c);
        return item;
    }

    @Override
    public void onBindViewHolder(InventarioItem inventarioItem, int i) {
        Contagem c = AppModel.getInstance().getContagens().get(i);
        inventarioItem.centro.setText(c.getCentro() + " - " + c.getDescritivo());
        inventarioItem.data.setText(DateFormat.getDateInstance().format(c.getData()));
        if(c.isReleased()) {
            inventarioItem.synch.setVisibility(View.VISIBLE);
        }
        else{
            inventarioItem.synch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return AppModel.getInstance().getContagens().size();
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
            int pos = getAdapterPosition();
            Contagem c = AppModel.getInstance().getContagens().get(pos);
            Intent i = new Intent(context, DetalheContagem.class);
            i.putExtra("id", c.getId());

            ListaContagens actContagens = (ListaContagens) context;
            actContagens.startActivityForResult(i,NAVIGATE);
        }
    }
}
