package com.example.crudprodutos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditClientActivity extends AppCompatActivity {

    private EditText etNomeCliente, etCNPJ, etDescricaoCliente, etEndereco;
    private Button btnSalvar, btnCancelar;
    private ClienteDAO clienteDAO;
    private int clienteId = -1; // Valor padrão para novo cliente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_cliente);

        // Vincular componentes do layout
        etNomeCliente = findViewById(R.id.etNomeCliente);
        etCNPJ = findViewById(R.id.etCNPJ);
        etDescricaoCliente = findViewById(R.id.etDescricaoCliente);
        etEndereco = findViewById(R.id.etEndereco);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);

        clienteDAO = new ClienteDAO(this);

        // Verifica se é edição
        clienteId = getIntent().getIntExtra("clienteId", -1);
        if (clienteId != -1) {
            Cliente cliente = clienteDAO.buscarClientePorId(clienteId);
            if (cliente != null) {
                // Preenche os campos com os dados do cliente
                etNomeCliente.setText(cliente.getNome());
                etCNPJ.setText(cliente.getCnpj());
                etDescricaoCliente.setText(cliente.getDescricao());
                etEndereco.setText(cliente.getEndereco());
            }
        }

        // Configura o botão "Salvar"
        btnSalvar.setOnClickListener(v -> salvarCliente());

        // Configura o botão "Cancelar"
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void salvarCliente() {
        String nome = etNomeCliente.getText().toString().trim();
        String cnpj = etCNPJ.getText().toString().trim();
        String descricao = etDescricaoCliente.getText().toString().trim();
        String endereco = etEndereco.getText().toString().trim();

        if (nome.isEmpty() || cnpj.isEmpty()) {
            Toast.makeText(this, "Nome e CNPJ são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso;
        if (clienteId == -1) {
            // Novo cliente
            sucesso = clienteDAO.inserirCliente(nome, cnpj, descricao, endereco);
        } else {
            // Atualizar cliente existente
            sucesso = clienteDAO.atualizarCliente(clienteId, nome, cnpj, descricao, endereco);
        }

        if (sucesso) {
            Toast.makeText(this, "Cliente salvo com sucesso", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a activity
        } else {
            Toast.makeText(this, "Erro ao salvar cliente", Toast.LENGTH_SHORT).show();
        }
    }
}
