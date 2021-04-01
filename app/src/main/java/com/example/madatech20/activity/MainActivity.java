package com.example.madatech20.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.madatech20.R;
import com.example.madatech20.adapter.UsuarioAdapter;
import com.example.madatech20.helper.DbHelper;
import com.example.madatech20.helper.RecyclerItemClickListener;
import com.example.madatech20.helper.UsuarioDAO;
import com.example.madatech20.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private Usuario usuarioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //config a lista
        recyclerView = findViewById(R.id.recyclerViewUsuarios);



        //Adicionar evento de click
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Recuperando Usuario para Edição
                Usuario usuarioSelecionado = listaUsuarios.get(position);

                //Exportando Usuario para Edição
                Intent intent = new Intent(MainActivity.this, AdicionarUsuarioActivity.class);
                intent.putExtra("usuarioSelecionado", usuarioSelecionado);

                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                //Recuperar o usuario para deletar
                usuarioSelecionado = listaUsuarios.get(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                //Configurar o Dialog
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Deseja excluir o Usuario: " + usuarioSelecionado.getNomeUsuario() + "?" );

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
                        if (usuarioDAO.deletar(usuarioSelecionado)){

                            carregarListaUsuarios();
                            Toast.makeText(getApplicationContext(),
                                    "Usuario excluido com Sucesso!",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao excluir Usuario!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.setNegativeButton("Não", null);

                //Exibir dialog
                dialog.create();
                dialog.show();

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdicionarUsuarioActivity.class);
                startActivity(intent);

            }
        });
    }

    public void carregarListaUsuarios() {

        //Listar Usuarios
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
        listaUsuarios = usuarioDAO.listar();

        //Exibir na lista Recycle


        //Config Adapter
        usuarioAdapter = new UsuarioAdapter(listaUsuarios);

        //Config Recycle
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(usuarioAdapter);

    }

    @Override
    protected void onStart() {

        carregarListaUsuarios();
        super.onStart();
    }

}