package br.com.igor.subredesapp.model;

/**
 * Created by Aluno on 15/09/2017.
 */

public class Subrede {

    private String nome;
    private String endRede;
    private String firstHost;
    private String lastHost;
    private String broadcast;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndRede() {
        return endRede;
    }

    public void setEndRede(String endRede) {
        this.endRede = endRede;
    }

    public String getFirstHost() {
        return firstHost;
    }

    public void setFirstHost(String firstHost) {
        this.firstHost = firstHost;
    }

    public String getLastHost() {
        return lastHost;
    }

    public void setLastHost(String lastHost) {
        this.lastHost = lastHost;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }
}
