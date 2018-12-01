package com.example.dell.buyonline.Login;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://thuanduong.000webhostapp.com/server/login.php";
    private Map<String, String> param;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return param;
    }
}
