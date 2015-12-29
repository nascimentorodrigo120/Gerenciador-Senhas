package com.example.irom.gerenciarsenhas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Criptografia_Hash.Hash_SHA2;
import Database.SenhaAcessoHelper;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView txtAcesso;
    EditText edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.button);
        txtAcesso = (TextView) findViewById(R.id.textViewAcesso);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);

        descobrirSeTemCadastro();


    }


    public void descobrirSeTemCadastro() {

        SenhaAcessoHelper helper = new SenhaAcessoHelper(this);
        String resu = helper.carregaSenha();            // vai retorna uma string com a senha(vem no formato de hash).
        helper.close();


        if (resu.equals("")) {                           // se caso vir "" eh porque nao tem nenhuma senha cadastrada
            button.setText("Cadastrar");                // entao o botao e o textView vai ser setado para cadastro.
            txtAcesso.setText("Cadastre sua Senha:".toUpperCase());
        }

        if (resu != "") {                                  // se caso for diferente de "" eh porque tem alguma senha cadastrada
            button.setText("Logar");                      // entao o botao e o textView vai ser setado para fazer o login.
            txtAcesso.setText("Digite a sua Senha de Acesso: ".toUpperCase());
        }
    }


    public void botaoCadastrar_Logar(View view) {

        String password = edtPassword.getText().toString();    // vai receber a senha q o usuario digitar no editText.

      if (password != "" && password.length() > 6) {

          if (button.getText().equals("Cadastrar")) {              // vai ver se o nome do botao eh cadastrar se for

              SenhaAcessoHelper helper = new SenhaAcessoHelper(MainActivity.this);
              helper.cadastra(new Hash_SHA2().calcularHash(password));     // vai transformar a senha digitada no calculo de hash(esse calculo que vai ser salvo no banco)
              helper.close();
              Toast.makeText(this, "Senha Cadastrada com Sucesso", Toast.LENGTH_LONG).show();

              button.setText("Logar");                             // e vai deixar pronto para LOGAR (mudando o nome do botao e textView...)
              txtAcesso.setText("Digite a sua Senha de Acesso:".toUpperCase());
              edtPassword.setText("");

          } else if (button.getText().equals("Logar")) {         // se houver uma senha cadastrada o nome do botao vai vir como Logar.(Aqui vai fazer o login)

              String senhaDigitada = new Hash_SHA2().calcularHash(password);  //vai pegar a senha digitada no editText e fazer o mesmo calculo de hash.

              SenhaAcessoHelper helper = new SenhaAcessoHelper(MainActivity.this);
              String senhaCadastrada = helper.carregaSenha();        // vai pegar a senha cadastrada no banco de dados(que vai vir na formula de hash).
              helper.close();


              if (senhaDigitada.equals(senhaCadastrada)) {          // vai comparar se os dois hash sao iguais(senha q foi digitada e senha q foi cadastrada).
                  startActivity(new Intent(MainActivity.this, Listar_ContasActivity.class));  // se for igual vai chamar a proxima tela.
              } else {
                  Toast.makeText(this, "Senha Incorreta!", Toast.LENGTH_LONG).show();   // se nao for igual vai mostrar uma mensagem.
                  edtPassword.setText("");
              }
          }

      }
      else {
        Toast.makeText(this, "Senha nao pode ser vazia \n ou menor que 6 caracteres", Toast.LENGTH_LONG).show();
      }
    }


    public void botaoExcluir(View view){

        String password = edtPassword.getText().toString();

        SenhaAcessoHelper helper = new SenhaAcessoHelper(MainActivity.this);
        helper.excluir(new Hash_SHA2().calcularHash(password));

        Toast.makeText(this, "Senha Excluida", Toast.LENGTH_SHORT).show();

    }




}












