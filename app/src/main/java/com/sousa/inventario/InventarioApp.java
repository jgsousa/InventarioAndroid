package com.sousa.inventario;

import android.app.Application;

/**
 * Created by Joao on 06/06/2015.
 */
public class InventarioApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();
    }

    public void initSingletons(){
        AppModel.initInstance();
    }
}
