package com.sousa.inventario;

import android.app.Application;

import com.sousa.inventario.network.AppRequestQueue;

/**
 * Created by Joao on 06/06/2015.
 */
public class InventarioApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();
    }

    public void initSingletons() {
        AppModel.initInstance(this);
        AppRequestQueue.initInstance(this);
    }
}
