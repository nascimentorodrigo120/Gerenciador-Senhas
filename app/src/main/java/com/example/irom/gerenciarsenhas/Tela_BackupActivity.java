package com.example.irom.gerenciarsenhas;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import Database.RepositositorioContasHelper;
import Modelo.Contas;

public class Tela_BackupActivity extends AppCompatActivity {

    List<Contas> lista;
    TextView txtCaminhoBackup;
    EditText edtNomeArquivo;

    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__backup);
        setTitle("Backup");

        edtNomeArquivo = (EditText) findViewById(R.id.editTextNomeArquivo);
        lista = new RepositositorioContasHelper(this).carregar();
        txtCaminhoBackup = (TextView)findViewById(R.id.textViewCaminhoBackup);

        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Contas.rln");
    }



    public void botaoExportar(View view) throws IOException {

        Document doc = new Document();

        Element root = new Element("xml_contas");

        for(Contas conta : lista) {

            Element pessoa = new Element("conta");

            Element nome = new Element("nome");
            nome.setText(conta.getNome());
            pessoa.addContent(nome);

            Element id = new Element("id");
            id.setText(""+conta.getId());
            pessoa.addContent(id);

            Element login = new Element("login");
            login.setText(conta.getLogin());
            pessoa.addContent(login);

            Element senha = new Element("senha");
            senha.setText(conta.getSenha());
            pessoa.addContent(senha);

            root.addContent(pessoa);
        }

        doc.setRootElement(root);

        XMLOutputter xout = new XMLOutputter();
        xout.output(doc, System.out);

        XMLOutputter xxout = new XMLOutputter();     // gerar um arquivo em no celular
        OutputStream out = new FileOutputStream(file);
        xout.output(doc, out);


        Toast.makeText(this,"Backup criado com sucesso.",Toast.LENGTH_LONG).show();
        txtCaminhoBackup.setText(file.getAbsolutePath());
    }



    public void botaoImportar(View view) throws JDOMException{

        String nomeArquivo = edtNomeArquivo.getText().toString();
        File f = new File(String.valueOf(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nomeArquivo)));

        SAXBuilder builder = new SAXBuilder();

        Document doc = null;
        try { doc = builder.build(f);} catch (IOException e) {Toast.makeText(this,"Arquivo nao Exist!.",Toast.LENGTH_SHORT).show();}

        Element root = (Element) doc.getRootElement();

        List pessoas = root.getChildren();

        Iterator i = pessoas.iterator();

        while (i.hasNext()) {

            Element conta = (Element) i.next();
            String nome = conta.getChildText("nome");
            String id = conta.getChildText("id");
            String login = conta.getChildText("login");
            String senha = conta.getChildText("senha");

            Contas contaBackup = new Contas();
            contaBackup.setNome(nome);
            contaBackup.setLogin(login);
            contaBackup.setSenha(senha);

            new RepositositorioContasHelper(this).cadastrar(contaBackup);
            Toast.makeText(this,"Por questao de seguranca apague o Backup:\n na Pasta Download.",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resu = item.getItemId();

        switch (resu){
            case R.id.action_enviar_email:
                dialogEnviar_EmailBackup();
            break;

            case R.id.action_enviar_dropbox:
                Toast.makeText(this,"Em BREVE!!",Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void dialogEnviar_EmailBackup() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog_backup);     //carregando o layout do dialog do xml
        dialog.setTitle("Enviar Backup");                         //titulo do dialog

        Button enviar = (Button) dialog.findViewById(R.id.buttonEnviarEmail);//componentes do layout do dialog.
        Button cancelar = (Button) dialog.findViewById(R.id.buttonCancelarEmail);
        final EditText edtMensagem = (EditText) dialog.findViewById(R.id.editTextMensagem);
        final EditText edtDestino = (EditText) dialog.findViewById(R.id.editText2Destino);
        final EditText edtAnexo = (EditText) dialog.findViewById(R.id.editText3Anexo);
        edtAnexo.setText(file.getAbsolutePath());

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent itEmail = new Intent(Intent.ACTION_SENDTO);
                itEmail.putExtra(Intent.EXTRA_SUBJECT, "Backup");
                itEmail.putExtra(Intent.EXTRA_TEXT, edtMensagem.getText().toString());
                itEmail.putExtra(Intent.EXTRA_EMAIL, edtDestino.getText().toString());

                itEmail.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath())); // local do anexo
                itEmail.setType("application/rln");    // a extenssao do anexo.
                startActivity(Intent.createChooser(itEmail, "Escolha a App para envio do e-mail..."));
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }




}
