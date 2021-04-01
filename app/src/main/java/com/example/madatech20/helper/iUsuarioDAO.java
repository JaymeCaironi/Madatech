package com.example.madatech20.helper;

import com.example.madatech20.model.Usuario;

import java.util.List;

public interface iUsuarioDAO {

    public boolean salvar(Usuario usuario);
    public boolean atualizar(Usuario usuario);
    public boolean deletar(Usuario usuario);
    public List<Usuario> listar();

}
