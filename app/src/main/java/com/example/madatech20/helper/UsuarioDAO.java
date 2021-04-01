package com.example.madatech20.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.madatech20.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements iUsuarioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public UsuarioDAO(Context context) {

        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }

    @Override
    public boolean salvar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put("nome", usuario.getNomeUsuario());
        cv.put("cpf", usuario.getCpfUsuario());
        cv.put("telefone", usuario.getTelefoneUsuario());

        try{
            escreve.insert(DbHelper.TABELA_USUARIOS, null, cv);
            Log.e("INFO", "Usuario salvo ");
        }catch (Exception e) {
            Log.e("INFO", "Erro ao salvar Usuario "+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put("nome", usuario.getNomeUsuario());
        cv.put("cpf", usuario.getCpfUsuario());
        cv.put("telefone", usuario.getTelefoneUsuario());

        try{
            String[] args = {usuario.getId().toString()};
            escreve.update(DbHelper.TABELA_USUARIOS, cv, "id=?", args);
            Log.e("INFO", "Usuario alterado com Sucesso!");
        }catch (Exception e) {
            Log.e("INFO", "Erro ao alterar Usuario!"+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Usuario usuario) {

        try{
            String[] args = {usuario.getId().toString()};
            escreve.delete(DbHelper.TABELA_USUARIOS, "id=?", args);
            Log.e("INFO", "Usuario removido com Sucesso!");
        }catch (Exception e) {
            Log.e("INFO", "Erro ao excluir Usuario!"+ e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIOS + " ;";
        Cursor cursor = ler.rawQuery(sql, null);

        while (cursor.moveToNext()){

            Usuario usuario = new Usuario();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeUsuario = cursor.getString(cursor.getColumnIndex("nome"));
            String cpfUsuario = cursor.getString(cursor.getColumnIndex("cpf"));
            String telefoneUsuario = cursor.getString(cursor.getColumnIndex("telefone"));

            usuario.setId(id);
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setCpfUsuario(cpfUsuario);
            usuario.setTelefoneUsuario(telefoneUsuario);

            usuarios.add(usuario);

        }

        return usuarios;

    }
}
