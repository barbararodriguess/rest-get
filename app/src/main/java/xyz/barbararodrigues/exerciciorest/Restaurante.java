package xyz.barbararodrigues.exerciciorest;

import java.io.Serializable;

/**
 * Created by b_r_s on 28/02/2017.
 */

public class Restaurante implements Serializable {
    public String restaurante;
    public Restaurante(){

    }

    public Restaurante(String restaurante){
        this.restaurante = restaurante;
    }

    @Override
    public String toString(){
        return restaurante;
    }
}
