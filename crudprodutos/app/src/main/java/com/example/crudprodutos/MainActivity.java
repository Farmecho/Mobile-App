package com.example.crudprodutos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProdutoDAO produtoDAO;
    private ListView listViewProdutos;
    private Button btnAdicionar;
    private Button btnCadastro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);
        listViewProdutos = findViewById(R.id.listViewProdutos);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnCadastro = findViewById(R.id.clientes);

        // Carregar e exibir a lista de produtos
        carregarProdutos();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditProdutoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();  // Recarrega a lista de produtos quando a atividade é retomada
    }

    private void carregarProdutos() {
        List<Produto> produtos = produtoDAO.listarProdutos();
        ProdutoAdapter adapter = new ProdutoAdapter(this, produtos);
        listViewProdutos.setAdapter(adapter);
    }
}
