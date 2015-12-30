package Criptografar_Contas;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Criptografia_Hash.Criptografar_Descriptografar;
import Database.RepositositorioContasHelper;
import Modelo.Contas;

/**
 * Created by irom on 14/11/2015.
 */
public class Metodo_Descriptografar {


    public static String CHAVE_64Bits  = "Acnln23n12l25$¨4C$%bDDB$FS";
    public static String CHAVE_100Bits = "Aufnchflnvmcncvnldasjojdndasdllcnln23n12l25$";



    public Contas descriptografarContas(Contas contasCrip) {

        Contas contasDescriptografadas = null;             // vai setar a conta(quando for descriptografada)

      try {

           Criptografar_Descriptografar rc4_64 = new Criptografar_Descriptografar(CHAVE_64Bits);  // vai receber as chaves
           Criptografar_Descriptografar rc4_100 = new Criptografar_Descriptografar(CHAVE_100Bits);

               // vai descriptografar a 1 criptografia a de 64 bits
               char[] loginDescrip_64 = rc4_64.decriptografa(contasCrip.getLogin().toCharArray());
               char[] senhaDescrip_64 = rc4_64.decriptografa(contasCrip.getSenha().toCharArray());

               // vai descriptografar a 2 criptografia a de 100 bits
               char[] loginDescrip_100 = rc4_100.decriptografa(new String(loginDescrip_64).toCharArray());
               char[] senhaDescrip_100 = rc4_100.decriptografa(new String(senhaDescrip_64).toCharArray());

               contasDescriptografadas = new Contas();
               contasDescriptografadas.setId(contasCrip.getId());
               contasDescriptografadas.setNome(contasCrip.getNome());               // vai receber os dados descriptografados e setar no objeto
               contasDescriptografadas.setLogin(new String(loginDescrip_100));      // contasDescriptografadas
               contasDescriptografadas.setSenha(new String(senhaDescrip_100));


       }catch (InvalidKeyException e){e.printStackTrace();}

        return contasDescriptografadas;            // vai retornar a lista com as contas descriptografadas.
    }
}
