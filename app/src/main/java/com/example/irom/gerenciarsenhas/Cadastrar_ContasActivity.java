package com.example.irom.gerenciarsenhas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;

import Criptografar_Contas.Metodo_Criptografar;
import Criptografia_Hash.Criptografar_Descriptografar;
import Database.RepositositorioContasHelper;
import Modelo.Contas;

public class Cadastrar_ContasActivity extends AppCompatActivity {

    private EditText editNome,editLogin,editSenha;
    private Button button;
    private Contas conta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar__contas);
        setTitle("Cadastrar Conta");

        button = (Button) findViewById(R.id.button2);
        editNome  = (EditText)findViewById(R.id.editTextNome);
        editLogin = (EditText)findViewById(R.id.editTextLogin);
        editSenha = (EditText)findViewById(R.id.editTextSenha);

        conta = (Contas) getIntent().getSerializableExtra("conta");
        if (conta != null){
            editNome.setText(conta.getNome());
            editLogin.setText(conta.getLogin());
            editSenha.setText(conta.getSenha());
            setTitle("Atualizar Conta");
            button.setText("Atualizar");
        }
    }


    public void botaoCadastrar(View view){
       salvarContato();
    }



     private void salvarContato() {
         if (conta == null) {
            this.conta = new Contas();
         }
         this.conta.setNome(this.editNome.getText().toString());
         this.conta.setLogin(this.editLogin.getText().toString());
         this.conta.setSenha(this.editSenha.getText().toString());

         alertDialog_Cadastrar(conta);

          /*  if (conta.getId() > 0) {
                new RepositositorioContasHelper(this).atualizar(conta);
            } else {
                new RepositositorioContasHelper(this).cadastrar(conta);

            }
         setResult(RESULT_OK, null);
         finish();*/

     }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_cadastro, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resu = item.getItemId();

        switch (resu){
            case R.id.action_icones:
            Toast.makeText(this, " EM BREVE!", Toast.LENGTH_SHORT).show();
            break;

            case R.id.action_gerar_senhas:
            Toast.makeText(this," EM BREVE!",Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }






    public void alertDialog_Cadastrar(final Contas conta){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cadastrar_ContasActivity.this);
        alertDialog.setTitle("Confirme os Dados");
        alertDialog.setMessage("CONTA: \n" + conta.getNome() + "\n" + conta.getLogin() + "\n" + conta.getSenha());

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (conta.getId() > 0) {
                    new RepositositorioContasHelper(Cadastrar_ContasActivity.this).atualizar(conta);
                } else {
                    new RepositositorioContasHelper(Cadastrar_ContasActivity.this).cadastrar(conta);

                }
                Toast.makeText(Cadastrar_ContasActivity.this, "CONTA CADASTRADA.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                finish();

            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {// evento do botao cancelar
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }




















}
