package Criptografar_Contas;

import java.security.InvalidKeyException;

import Criptografia_Hash.Criptografar_Descriptografar;
import Modelo.Contas;

/**
 * Created by irom on 14/11/2015.
 */
public class Metodo_Criptografar {

    public static final String CHAVE_64Bits  = "Acnln23n12l25$¨4C$%bDDB$FS";
    public static final String CHAVE_100Bits = "Aufnchflnvmcncvnldasjojdndasdllcnln23n12l25$¨4";




    public Contas criptografarDados(Contas conta){

        Contas novaConta = null;

        try {                  // criptografia geral
            Criptografar_Descriptografar rc4_100 = new Criptografar_Descriptografar(CHAVE_100Bits);
            char[] emailCriptografado_100 = rc4_100.criptografa(conta.getLogin().toCharArray());
            char[] senhaCriptografada_100 = rc4_100.criptografa(conta.getSenha().toCharArray());

            // criptografia interna
            Criptografar_Descriptografar rc4_64 = new Criptografar_Descriptografar(CHAVE_64Bits);
            char[] emailCriptografado_64 = rc4_64.criptografa(new String(emailCriptografado_100).toCharArray());
            char[] senhaCriptografada_64 = rc4_64.criptografa(new String(senhaCriptografada_100).toCharArray());


            novaConta = new Contas();
            novaConta.setId(conta.getId());
            novaConta.setNome(conta.getNome());
            novaConta.setLogin(new String(emailCriptografado_64));
            novaConta.setSenha(new String(senhaCriptografada_64));



        } catch (InvalidKeyException e) {
           e.printStackTrace();
        }

        return novaConta;
    }
}
