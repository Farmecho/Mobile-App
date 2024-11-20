package com.example.crudprodutos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    private Context context;
    private List<Produto> listaProdutos;
    private ProdutoDAO produtoDAO;

    public ProdutoAdapter(Context context, List<Produto> listaProdutos) {
        this.context = context;
        this.listaProdutos = listaProdutos;
        this.produtoDAO = new ProdutoDAO(context);
    }

    @Override
    public int getCount() {
        return listaProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaProdutos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_produto, parent, false);
        }

        // Vincular as views
        TextView nomeProdutoTextView = convertView.findViewById(R.id.nomeProduto);
        TextView precoProdutoTextView = convertView.findViewById(R.id.precoProduto);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluir);

        Produto produto = listaProdutos.get(position);

        nomeProdutoTextView.setText(produto.getNome());
        precoProdutoTextView.setText(String.valueOf(produto.getPreco()));

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditProdutoActivity.class);
                intent.putExtra("produtoId", produto.getId());
                context.startActivity(intent);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtoDAO.deletarProduto(produto.getId());
                listaProdutos.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
