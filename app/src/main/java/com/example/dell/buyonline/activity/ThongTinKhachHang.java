package com.example.dell.buyonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.ultil.CheckConnection;
import com.example.dell.buyonline.ultil.Server;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThongTinKhachHang extends AppCompatActivity {

    private EditText mEdtTenKhachHang, mEdtSoDienThoai, mEdtEmail;
    private Button  mBtnXacNhan, mBtnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        findId();

        mBtnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng màn hình
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        }
        else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối");
        }
    }

    private void EventButton() {
        mBtnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = mEdtTenKhachHang.getText().toString();
                final String sdt = mEdtSoDienThoai.getText().toString();
                final String email = mEdtEmail.getText().toString();

                if(ten.length()>0 && sdt.length()>0 && email.length()>0) {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server
                            .LINK_DON_HANG, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //ma don hang <=> id trong thong tin khach hang
                            Log.d("madonhang",response);
                            final int madonhang = Integer.parseInt(response);
                            if(madonhang > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST,
                                        Server.LINK_CHI_TIET_DON_HANG, new Response
                                        .Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")) {
                                            MainActivity.mArrayGioHang.clear();
                                            CheckConnection.showToast(getApplicationContext(),"Thêm dữ liệu giỏ hàng thành công");

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToast(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                        } else {
                                            CheckConnection.showToast(getApplicationContext(),"Error");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0; i <  MainActivity.mArrayGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            // Lấy key từ php
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.mArrayGioHang.get(i).getId());
                                                jsonObject.put("tensanpham", MainActivity.mArrayGioHang.get(i).getTenSanPham());
                                                jsonObject.put("giasanpham", MainActivity.mArrayGioHang.get(i).getGiaSanPham());
                                                jsonObject.put("soluongsanpham", MainActivity.mArrayGioHang.get(i).getSoLuongSanPham());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            // Đưa dữ liệu vào json
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            } else {
                                Log.d("thuan",response);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("tenkhachhang", ten);
                            map.put("sodienthoai", sdt);
                            map.put("email", email);
                            return map;
                        }
                    };
                    queue.add(stringRequest);

                } else {
                    CheckConnection.showToast(getApplicationContext(),"Kiểm tra lại thông tin");
                }
            }
        });
    }

    private void findId() {
        mEdtTenKhachHang = (EditText) findViewById(R.id.edit_ten_khach_hang);
        mEdtSoDienThoai = (EditText) findViewById(R.id.edit_sdt_khach_hang);
        mEdtEmail = (EditText) findViewById(R.id.edit_email_khach_hang);

        mBtnXacNhan = (Button) findViewById(R.id.button_xac_nhan);
        mBtnTroVe = (Button) findViewById(R.id.button_tro_ve);
    }
}
