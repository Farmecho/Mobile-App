package com.example.crudprodutos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "produtos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUTO = "produtos";

    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_DESCRICAO = "descricao";
    private static final String COL_PRECO = "preco";

    public ProdutoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUTO + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NOME + " TEXT," +
                    COL_DESCRICAO + " TEXT," +
                    COL_PRECO + " REAL" + ")";
            db.execSQL(CREATE_TABLE);
            Log.d("ProdutoDAO", "Tabela produtos criada com sucesso.");
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao criar a tabela produtos", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);
        onCreate(db);
    }

    public boolean adicionarProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, produto.getNome());
        values.put(COL_DESCRICAO, produto.getDescricao());
        values.put(COL_PRECO, produto.getPreco());

        try {
            long result = db.insert(TABLE_PRODUTO, null, values);
            if (result == -1) {
                Log.e("ProdutoDAO", "Erro ao inserir produto no banco de dados");
                return false;
            }
            Log.d("ProdutoDAO", "Produto inserido com sucesso");
            return true;
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao adicionar produto", e);
            return false;
        } finally {
            db.close();
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> listaProdutos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_PRODUTO, new String[]{COL_ID, COL_NOME, COL_DESCRICAO, COL_PRECO},
                    null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Produto produto = new Produto(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getDouble(3));
                    listaProdutos.add(produto);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao listar produtos", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return listaProdutos;
    }

    public boolean atualizarProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, produto.getNome());
        values.put(COL_DESCRICAO, produto.getDescricao());
        values.put(COL_PRECO, produto.getPreco());

        try {
            int rows = db.update(TABLE_PRODUTO, values, COL_ID + "=?", new String[]{String.valueOf(produto.getId())});
            return rows > 0;
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao atualizar produto", e);
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deletarProduto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rows = db.delete(TABLE_PRODUTO, COL_ID + "=?", new String[]{String.valueOf(id)});
            return rows > 0;
        } catch (SQLException e) {
            Log.e("ProdutoDAO", "Erro ao deletar produto", e);
            return false;
        } finally {
            db.close();
        }
    }

    

}
