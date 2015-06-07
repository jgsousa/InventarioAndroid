package com.sousa.inventario.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sousa.inventario.AppModel;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.model.ItemContagem;
import com.sousa.inventario.R;

/**
 * Created by Joao on 06/06/2015.
 */
public class DetalheAdapter extends RecyclerView.Adapter<DetalheAdapter.VItemContagem> {

    private Context context;
    private Contagem contagem;
    private LayoutInflater inflater;

    public DetalheAdapter(Context context, Contagem contagem){
        this.context = context;
        this.contagem = contagem;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DetalheAdapter.VItemContagem onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = inflater.inflate(R.layout.item_contagem, viewGroup, false);
        VItemContagem novo = new VItemContagem(item);
        novo.material = (TextView) item.findViewById(R.id.artigo);
        novo.quantidade = (TextView) item.findViewById(R.id.quantidade);
        novo.ean = (TextView) item.findViewById(R.id.ean);
        return novo;
    }

    @Override
    public void onBindViewHolder(VItemContagem vitem, int i) {
        ItemContagem item = contagem.getItens().get(i);
        vitem.material.setText(item.getMaterial().getDescription());
        vitem.quantidade.setText(String.valueOf(item.getContado()) + " " + item.getUnidade());
        vitem.ean.setText(item.getMaterial().getEAN());
    }

    @Override
    public int getItemCount() {
        return contagem.getItens().size();
    }

    public class VItemContagem extends RecyclerView.ViewHolder{
        public TextView material;
        public TextView quantidade;
        public TextView ean;

        public VItemContagem(View itemView) {
            super(itemView);
        }
    }
}
