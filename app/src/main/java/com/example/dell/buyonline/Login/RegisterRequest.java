package com.example.dell.buyonline.Login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    
    private static final String REGISTER_REQUEST_URL = "https://thuanduong.000webhostapp.com/server/register.php";
    private Map<String, String> param;

    public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        param = new HashMap<>();
        param.put("name", name);
        param.put("username", username);
        param.put("password", password);
        param.put("email", email);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return param;
    }
}
