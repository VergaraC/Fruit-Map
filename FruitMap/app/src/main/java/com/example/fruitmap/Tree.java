package com.example.fruitmap;

public class Tree {
    private String comentario;
    private String tipo;
    private double acesso;
    private double quant;
    private double quali;
    private double lat;
    private double longi;

    public Tree (String comentario, String tipo,  double acesso, double quant, double quali, double lat, double longi) {
        this.comentario = comentario;
        this.tipo =  tipo;
        this.acesso = acesso;
        this.quant = quant;
        this.quali = quali;
        this.lat = lat;
        this.longi = longi;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getAcesso() {
        return acesso;
    }

    public void setAcesso(double acesso) {
        this.acesso = acesso;
    }

    public double getQuant() {
        return quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public double getQuali() {
        return quali;
    }

    public void setQuali(double quali) {
        this.quali = quali;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }
}
