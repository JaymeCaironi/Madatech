package com.example.madatech20.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madatech20.R;
import com.example.madatech20.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.MyViewHolder> {

    private List<Usuario> listaUsuarios;

    public UsuarioAdapter(List<Usuario> lista) {
        this.listaUsuarios = lista;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_usuario_adapter, parent, false);


        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario usuario = listaUsuarios.get(position);
        holder.nomeUsuario.setText(usuario.getNomeUsuario());
        holder.cpfUsuario.setText(usuario.getCpfUsuario());
        holder.telefoneUsuario.setText(usuario.getTelefoneUsuario());

    }

    @Override
    public int getItemCount() {
        return this.listaUsuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeUsuario;
        TextView cpfUsuario;
        TextView telefoneUsuario;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeUsuario = itemView.findViewById(R.id.textViewNome);
            cpfUsuario = itemView.findViewById(R.id.textViewCpf);
            telefoneUsuario = itemView.findViewById(R.id.textViewTel);

        }
    }

}
