package Modelo;

import java.io.Serializable;

/**
 * Created by irom on 14/11/2015.
 */
public class Contas implements Serializable {

    private long id;
    private String nome;
    private String login;
    private String senha;


    public Contas(long id, String nome, String login, String senha) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }


    public Contas() {
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome;
    }
}
