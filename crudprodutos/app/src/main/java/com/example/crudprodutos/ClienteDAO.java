package com.example.crudprodutos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "clientes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLIENTE = "clientes";
    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_CNPJ = "cnpj";
    private static final String COL_DESCRICAO = "descricao";
    private static final String COL_ENDERECO = "endereco";

    public ClienteDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CLIENTE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOME + " TEXT NOT NULL, " +
                COL_CNPJ + " TEXT NOT NULL, " +
                COL_DESCRICAO + " TEXT, " +
                COL_ENDERECO + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
        onCreate(db);
    }

    public boolean inserirCliente(String nome, String cnpj, String descricao, String endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, nome);
        values.put(COL_CNPJ, cnpj);
        values.put(COL_DESCRICAO, descricao);
        values.put(COL_ENDERECO, endereco);

        long result = db.insert(TABLE_CLIENTE, null, values);
        db.close();
        return result != -1; // Retorna true se inserção foi bem-sucedida
    }

    public boolean atualizarCliente(int id, String nome, String cnpj, String descricao, String endereco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOME, nome);
        values.put(COL_CNPJ, cnpj);
        values.put(COL_DESCRICAO, descricao);
        values.put(COL_ENDERECO, endereco);

        int rowsAffected = db.update(TABLE_CLIENTE, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0; // Retorna true se atualização foi bem-sucedida
    }

    public boolean excluirCliente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CLIENTE, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0; // Retorna true se exclusão foi bem-sucedida
    }

    public List<Cliente> listarClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_CLIENTE, new String[]{COL_ID, COL_NOME, COL_CNPJ, COL_DESCRICAO, COL_ENDERECO},
                    null, null, null, null, COL_NOME + " ASC"); // Ordena por nome

            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_CNPJ)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRICAO)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDERECO))
                    );
                    listaClientes.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro ao listar clientes", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return listaClientes;
    }

    public Cliente buscarClientePorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Cliente cliente = null;

        try {
            cursor = db.query(TABLE_CLIENTE, new String[]{COL_ID, COL_NOME, COL_CNPJ, COL_DESCRICAO, COL_ENDERECO},
                    COL_ID + "=?", new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                cliente = new Cliente(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CNPJ)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRICAO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ENDERECO))
                );
            }
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro ao buscar cliente por ID", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return cliente;
    }

    public void deletarCliente(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Deletar o cliente pelo ID
        db.delete("clientes", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }




}
