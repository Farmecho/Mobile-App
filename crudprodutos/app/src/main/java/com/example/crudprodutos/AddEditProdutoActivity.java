package com.example.crudprodutos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditProdutoActivity extends AppCompatActivity {

    private EditText etNomeProduto, etDescricaoProduto, etPrecoProduto;
    private Button btnSalvar, btnCancelar;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_produto);

        produtoDAO = new ProdutoDAO(this);
        etNomeProduto = findViewById(R.id.etNomeProduto);
        etDescricaoProduto = findViewById(R.id.etDescricaoProduto);
        etPrecoProduto = findViewById(R.id.etPrecoProduto);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeProduto.getText().toString();
                String descricao = etDescricaoProduto.getText().toString();
                double preco = Double.parseDouble(etPrecoProduto.getText().toString());

                Produto produto = new Produto(0, nome, descricao, preco);
                if (produtoDAO.adicionarProduto(produto)) {
                    setResult(RESULT_OK); // Indica que o produto foi adicionado com sucesso
                    finish(); // Fecha a atividade
                } else {
                    // Lidar com erro de adição (opcional)
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha a atividade sem adicionar produto
            }
        });
    }
}

