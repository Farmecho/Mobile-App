package com.example.crudprodutos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class activity_clientes extends AppCompatActivity {

    private ListView listViewClientes;
    private Button btnAdicionarCliente;
    private ClienteDAO clienteDAO;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> listaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_clientes);

        listViewClientes = findViewById(R.id.listViewClientes);
        btnAdicionarCliente = findViewById(R.id.btnAdicionar_cliente);

        clienteDAO = new ClienteDAO(this);

        // Configura botão para adicionar cliente
        btnAdicionarCliente.setOnClickListener(v -> {
            Intent intent = new Intent(activity_clientes.this, AddEditClientActivity.class);
            startActivity(intent);
        });

        // Recarrega os clientes ao abrir a activity
        carregarClientes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarClientes(); // Recarrega os clientes ao retornar para a activity
    }

    private void carregarClientes() {
        listaClientes = clienteDAO.listarClientes();

        if (listaClientes.isEmpty()) {
            Toast.makeText(this, "Nenhum cliente cadastrado", Toast.LENGTH_SHORT).show();
        }

        // Configura o ClienteAdapter
        clienteAdapter = new ClienteAdapter(this, listaClientes, clienteDAO);
        listViewClientes.setAdapter(clienteAdapter); // Configura o adapter na ListView
    }
}
