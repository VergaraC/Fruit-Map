package com.example.fruitmap;

import android.content.Intent;

public class Item {
    private int mImageResourece;
    private String titulo;
    private String clicked;

    public Item(int mImageResourece,String titulo ){

        this.mImageResourece = mImageResourece;
        this.titulo = titulo;
    }
    public String changeclicked (String text){
        clicked = text;
        System.out.println("Corno");
        //this.titulo = clicked;
        return this.titulo;

    }
    public String getTitulo() {
        return titulo;
    }
    public int getmImageResourece() {
        return mImageResourece;
    }

}
