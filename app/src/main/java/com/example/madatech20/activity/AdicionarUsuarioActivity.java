package com.example.madatech20.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.madatech20.R;
import com.example.madatech20.helper.UsuarioDAO;
import com.example.madatech20.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarUsuarioActivity extends AppCompatActivity {

    private TextInputEditText editNomeUsuario;
    private TextInputEditText editCpfUsuario;
    private TextInputEditText editTelefoneUsuario;
    private Usuario usuarioAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_usuario);

        editNomeUsuario = findViewById(R.id.textNome);
        editCpfUsuario = findViewById(R.id.textCpf);
        editTelefoneUsuario = findViewById(R.id.textTelefone);

        //Recuperar dados Usuario para Edição
        usuarioAtual = (Usuario) getIntent().getSerializableExtra("usuarioSelecionado");

        //Configurar Usuario nas caixas de texto
        if(usuarioAtual != null) {

            editNomeUsuario.setText(usuarioAtual.getNomeUsuario());
            editCpfUsuario.setText(usuarioAtual.getCpfUsuario());
            editTelefoneUsuario.setText(usuarioAtual.getTelefoneUsuario());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_save:
                //Salvar o Usuario
                UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

                if (usuarioAtual != null) { //Editar

                    String nomeUsuario = editNomeUsuario.getText().toString();
                    String cpfUsuario = editCpfUsuario.getText().toString();
                    String telefoneUsuario = editTelefoneUsuario.getText().toString();

                    if (!nomeUsuario.isEmpty()){
                        if (!cpfUsuario.isEmpty()){
                            if ((!telefoneUsuario.isEmpty())){

                                Usuario usuario = new Usuario();
                                usuario.setId(usuarioAtual.getId());
                                usuario.setNomeUsuario(nomeUsuario);
                                usuario.setCpfUsuario(cpfUsuario);
                                usuario.setTelefoneUsuario(telefoneUsuario);

                                //atualizar banco de dados
                                if(usuarioDAO.atualizar(usuario)) {
                                    finish();
                                    Toast.makeText(getApplicationContext(),
                                            "Usuario Modificado com Sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao Modificar Usuario!",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }

                }else { //Salvar

                    String nomeUsuario = editNomeUsuario.getText().toString();
                    String cpfUsuario = editCpfUsuario.getText().toString();
                    String telefoneUsuario = editTelefoneUsuario.getText().toString();

                    if (!nomeUsuario.isEmpty()){
                        if (!cpfUsuario.isEmpty()) {
                            if ((!telefoneUsuario.isEmpty())){

                                Usuario usuario = new Usuario();
                                usuario.setNomeUsuario(nomeUsuario);
                                usuario.setCpfUsuario(cpfUsuario);
                                usuario.setTelefoneUsuario(telefoneUsuario);

                                if (usuarioDAO.salvar(usuario)){
                                    finish();
                                    Toast.makeText(getApplicationContext(),
                                            "Usuario Salvo com Sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao Salvar Usuario!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

}