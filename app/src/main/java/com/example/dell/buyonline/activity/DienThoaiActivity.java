package com.example.dell.buyonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.adapter.DienThoaiAdapter;
import com.example.dell.buyonline.model.GioHang;
import com.example.dell.buyonline.model.SanPham;
import com.example.dell.buyonline.ultil.CheckConnection;
import com.example.dell.buyonline.ultil.EndlessScrollListener;
import com.example.dell.buyonline.ultil.OnItemClickListener;
import com.example.dell.buyonline.ultil.Server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DienThoaiActivity extends AppCompatActivity {

    private Toolbar mToolbarDienThoai;
    RecyclerView mRecyclerView;
    DienThoaiAdapter mDienThoaiAdapter;
    ArrayList<SanPham> mArrayDienThoai = new ArrayList<>();
    int idDienThoai = 0;
    private int mPage = 1;
    private EndlessScrollListener mScrollListener;
    private OnItemClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            findID();
            getIDLoaiSp();
            actionToolBar();
            getData(mPage);
            getMoreData();

        } else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra kết nối!");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_gio_hang:
                // Nếu click vào icon giỏ hàng -> chuyển đến activity giỏ hàng
                Intent intent =  new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMoreData() {
        // Xử lý click sang màn hình chi tiết
        mDienThoaiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                // Gửi từng đối tượng của mảng điện thoại ,
                // sử dụng Seriable khai báo bên class Sản phẩm
                intent.putExtra("thongtinsanpham", mArrayDienThoai.get(position));
                startActivity(intent);
            }
        });

        // Xử lý load nhiều sản phẩm hơn , 1 lần load 1 page
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        mScrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // mPage = mPage + 1;
                        getData(++mPage);
                        mDienThoaiAdapter.notifyDataSetChanged();
                    }
                }, 1500);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    private void actionToolBar() {
        setSupportActionBar(mToolbarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Server.LINK_DIEN_THOAI + String.valueOf(page);
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String namePhone = jsonObject.getString("tensp");
                                    int pricePhone = jsonObject.getInt("giasp");
                                    String imagePhone = jsonObject.getString("hinhanhsp");
                                    String descripPhone = jsonObject.getString("motasp");
                                    int idsp = jsonObject.getInt("idsanpham");
                                    mArrayDienThoai.add(
                                            new SanPham(id, namePhone, pricePhone, imagePhone,
                                                    descripPhone, idsp));
                                    mDienThoaiAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("idsanpham", String.valueOf(idDienThoai));
                        return param;
                    }
                };
        requestQueue.add(stringRequest);
    }

    private void getIDLoaiSp() {
        //Truyền vào kiểu int -> get kiểu int, nếu giá trị không tìm thấy trả về -1
        idDienThoai = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idDienThoai + "");
    }

    private void findID() {
        mToolbarDienThoai = (Toolbar) findViewById(R.id.toolbar_phone);
        mDienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), mArrayDienThoai);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_phone);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mDienThoaiAdapter);
    }
}
