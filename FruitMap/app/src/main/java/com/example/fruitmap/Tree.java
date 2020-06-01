package com.example.fruitmap;

public class Tree {
    String comentario;
    String tipo;
    Float acesso;
    Float quant;
    Float quali;
    Double lLat;
    Double lLong;
    public Tree (String comentario,String tipo,  Float acesso,Float quant,Float quali, Double lLat, Double lLong){
        this.comentario = comentario;
        this.tipo =  tipo;
        this.acesso = acesso;
        this.quant = quant;
        this.quali = quali;
        this.lLat = lLat;
        this.lLong = lLong;

    }
}
