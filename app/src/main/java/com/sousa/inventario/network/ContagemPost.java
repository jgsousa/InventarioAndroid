package com.sousa.inventario.network;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sousa.inventario.model.Contagem;
import com.sousa.inventario.utils.Contagens;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 10/06/2015.
 */
public class ContagemPost extends JsonObjectRequest {
    public String token;
    public String cookie;
    public String contagemId;

    public ContagemPost(String host, JSONObject requestBody) {
        super(Method.POST, host + "/sap/opu/odata/sap/ZMD_ARTIGOS_SRV/Contagens", requestBody,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                handleResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String creds = String.format("%s:%s", "deloitte", "deloitte");
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
        params.put("Authorization", auth);
        params.put("X-CSRF-Token", token);
        params.put("Cookie", cookie);
        params.put("Content-Type", "application/json");
        return params;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        byte[] responseData = new byte[0];
        try {
            JSONObject js = new JSONObject();
            js.put("statusCode", response.statusCode);
            js.put("id", this.contagemId);
            responseData = js.toString().getBytes("UTF8");
            response = new NetworkResponse(response.statusCode, responseData, response.headers, response.notModified);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.parseNetworkResponse(response);
    }

    private static void handleResponse(JSONObject object){
        String statusCode = null;
        String contagemId = null;
        try {
            statusCode = object.getString("statusCode");
            contagemId = object.getString("id");
            if(statusCode.equals("201") && contagemId != null){ //Created
                Contagens.setSynched(contagemId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
