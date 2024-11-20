package com.example.crudprodutos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ClienteAdapter extends BaseAdapter {

    private Context context;
    private List<Cliente> clientes;
    private ClienteDAO clienteDAO;

    // Construtor do adapter
    public ClienteAdapter(Context context, List<Cliente> clientes, ClienteDAO clienteDAO) {
        this.context = context;
        this.clientes = clientes;
        this.clienteDAO = clienteDAO;
    }

    @Override
    public int getCount() {
        return clientes.size(); // Retorna o número de itens
    }

    @Override
    public Cliente getItem(int position) {
        return clientes.get(position); // Retorna o cliente na posição especificada
    }

    @Override
    public long getItemId(int position) {
        return position; // ID da posição
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla o layout personalizado
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);
        }

        // Obtém o cliente atual
        Cliente cliente = getItem(position);

        // Vincula os componentes do layout
        TextView nomeCliente = convertView.findViewById(R.id.nomeCliente);
        TextView cnpjCliente = convertView.findViewById(R.id.cnpjCliente);
        TextView descricaoCliente = convertView.findViewById(R.id.descricaoCliente);
        TextView enderecoCliente = convertView.findViewById(R.id.enderecoCliente);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluir);

        // Preenche os dados do cliente
        nomeCliente.setText(cliente.getNome());
        cnpjCliente.setText(cliente.getCnpj());
        descricaoCliente.setText(cliente.getDescricao());
        enderecoCliente.setText(cliente.getEndereco());

        // Configura o botão "Editar"
        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditClientActivity.class);
            intent.putExtra("clienteId", cliente.getId()); // Envia o ID do cliente para a activity
            context.startActivity(intent);
        });

        // Configura o botão "Excluir"
        btnExcluir.setOnClickListener(v -> {
            if (clienteDAO.excluirCliente(cliente.getId())) {
                clientes.remove(position); // Remove o cliente da lista
                notifyDataSetChanged(); // Atualiza o ListView
                Toast.makeText(context, "Cliente excluído com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao excluir cliente", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
