package com.sousa.inventario.network;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sousa.inventario.AppModel;
import com.sousa.inventario.model.Artigo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joao on 08/06/2015.
 */
public class ArtigosRequest extends JsonObjectRequest {

    public String token;
    public String cookie;


    public ArtigosRequest(String host) {
        super(Request.Method.GET, host + "/sap/opu/odata/sap/ZMD_ARTIGOS_SRV/Artigos?&$format=json", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                List<Artigo> artigos = ArtigosRequest.parseResponse(jsonObject);
                AppModel.getInstance().setArtigosFromBackend(artigos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    NetworkResponse response = error.networkResponse;
                    Log.d("testerror", "" + statusCode + " " + response.data);
                }
            }
        });
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", "deloitte", "deloitte");
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
        params.put("Authorization", auth);
        params.put("X-CSRF-Token", "Fetch");
        return params;
    }

    //@Override
    /*protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    } */

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Map<String, String> responseHeaders = response.headers;
        this.token = responseHeaders.get("X-CSRF-Token");
        this.cookie = responseHeaders.get("set-cookie");
        return super.parseNetworkResponse(response);
    }

    private static List<Artigo> parseResponse(JSONObject response) {
        JSONArray array = null;
        List<Artigo> artigos = new ArrayList<>();
        try {
            array = response.getJSONObject("d").getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                Artigo artigo = new Artigo();
                JSONObject object = array.getJSONObject(i);
                artigo.setId(object.getString("ID"));
                artigo.setEAN(object.getString("EAN"));
                artigo.setDescription(object.getString("Descritivo"));
                artigo.setUnit(object.getString("Unidade"));
                artigos.add(artigo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return artigos;
    }
}
