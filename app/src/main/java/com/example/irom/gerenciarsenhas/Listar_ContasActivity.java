package com.example.irom.gerenciarsenhas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.RepositositorioContasHelper;
import Modelo.Contas;

public class Listar_ContasActivity extends AppCompatActivity {

    private ListView lista;
    private List<Contas> listContas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar__contas);
        setTitle("Lista de Contas");

        lista = (ListView) findViewById(R.id.lista);
        lista.setEmptyView(findViewById(R.id.msg_lista_vazia));
        registerForContextMenu(this.lista);

        clickRapidoListView();


    }


    @Override
    protected void onResume() {
        super.onResume();
        carregarContatos();
    }

    private void carregarContatos() {
        listContas = new ArrayList<Contas>();
        listContas = new RepositositorioContasHelper(this).carregar();
        ArrayAdapter<Contas> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listContas);
        this.lista.setAdapter(adapter);
    }


    public void botaoCadastro(View view) {
        Intent it = new Intent(this, Cadastrar_ContasActivity.class);
        startActivityForResult(it, 1);
    }


    private void excluirContato(final Contas conta) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação");
        builder.setMessage("Confirma exclusão do contato?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

          public void onClick(DialogInterface dialog, int which) {

            new RepositositorioContasHelper(Listar_ContasActivity.this).excluir(conta);
            Toast.makeText(getApplicationContext(), conta.getNome() + " Excluida.", Toast.LENGTH_SHORT).show();
            carregarContatos();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setIcon(R.drawable.ic_excluir);
        builder.show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto_lista, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contas conta = (Contas) this.lista.getItemAtPosition(info.position);
        switch (item.getItemId()) {

            case R.id.menu_item_acessar:
                Toast.makeText(this, " EM BREVE!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_item_editar:
                chamarTelaAtualizar(conta);
                return true;

            case R.id.menu_item_excluir:
                excluirContato(conta);
                return true;
            default:
                return false;
        }
    }


    private void chamarTelaAtualizar(Contas conta) {
        Intent it = new Intent(this, Cadastrar_ContasActivity.class);
        it.putExtra("conta", conta);
        startActivityForResult(it, 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2) {
            if (resultCode == RESULT_OK) {
                carregarContatos();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_listar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resu = item.getItemId();

        switch (resu) {
            case R.id.action_backup:
                chamarTelaBackup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void chamarTelaBackup() {
        startActivity(new Intent(this, Tela_BackupActivity.class));
    }



    public void clickRapidoListView() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nome = listContas.get(position).getNome();
                String login = listContas.get(position).getLogin();
                String senha = listContas.get(position).getSenha();

                new AlertDialog_CopiarItem().dialog_CopiarItem(Listar_ContasActivity.this, nome, login, senha);
            }
        });
    }


}
