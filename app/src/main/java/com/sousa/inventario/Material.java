package com.sousa.inventario;

/**
 * Created by Joao on 05/06/2015.
 */
public class Material {

    public String id;
    public String EAN;
    public String description;
    public String unit;

    public Material(String mat, String ean, String unit, String desc){
        this.id = mat;
        this.EAN = ean;
        this.unit = unit;
        this.description = desc;
    }
}
