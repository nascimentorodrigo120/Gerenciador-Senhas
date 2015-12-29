package com.example.irom.gerenciarsenhas;

import android.app.Dialog;
import android.content.Context;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by irom on 17/11/2015.
 * Layout do dialog que vai copiar item(como login ou senha)
 */
public class AlertDialog_CopiarItem {


    public void dialog_CopiarItem(final Context context,String nome,String login, String senha){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_copia_conta);//carregando o layout do dialog do xml
        dialog.setTitle("Dados da Conta:");//titulo do dialog

        Button ok = (Button) dialog.findViewById(R.id.buttonOk);//componentes do layout do dialog.
        ImageButton copyLogin = (ImageButton)dialog.findViewById(R.id.imageButtonCopyLogin);
        ImageButton copySenha = (ImageButton)dialog.findViewById(R.id.imageButtonCopySenha);
        final EditText edtNome = (EditText) dialog.findViewById(R.id.editTextNomeConta);
        final EditText edtLogin = (EditText) dialog.findViewById(R.id.editText2LoginConta);
        final EditText edtSenha = (EditText) dialog.findViewById(R.id.editTextSenhaConta);

        edtNome.setText(nome);
        edtLogin.setText(login);
        edtSenha.setText(senha);

        copyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(edtLogin.getText().toString());
                Toast.makeText(context,"Login Copiado.",Toast.LENGTH_SHORT).show();
            }
        });

        copySenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(edtSenha.getText().toString());
                Toast.makeText(context,"Senha Copiada.",Toast.LENGTH_SHORT).show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
