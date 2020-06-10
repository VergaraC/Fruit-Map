package com.example.fruitmap;

public class Item {
    private int mImageResourece;
    private String titulo;
    private String cliked;

    public Item(int mImageResourece,String titulo ){

        this.mImageResourece = mImageResourece;
        this.titulo = titulo;
    }
    public void changeclicked (String text){
        cliked = text;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getmImageResourece() {
        return mImageResourece;
    }

}
