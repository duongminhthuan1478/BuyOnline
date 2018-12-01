package com.example.dell.buyonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.adapter.GioHangAdapter;
import com.example.dell.buyonline.model.GioHang;
import com.example.dell.buyonline.ultil.CheckConnection;
import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    private GioHangAdapter mGioHangAdapter;
    private RecyclerView mRecyclerViewGioHang;
    private TextView mTextThongBao;
    private static TextView mTextTongTien;
    private Button mButtonThanhToan, mButtonTiepTucMua;
    private Toolbar mToolbarGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        findID();
        actionToolbar();
        checkData();
        EvenUtil();
        EventButton();
    }

    private void EventButton() {
        mButtonTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mButtonThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mArrayGioHang.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
                    startActivity(intent);
                }
                else {
                    CheckConnection.showToast(getApplicationContext(), "Chọn sản phẩm để thanh toán");
                }
            }
        });
    }

    public static void EvenUtil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.mArrayGioHang.size(); i++) {
            tongTien = tongTien + MainActivity.mArrayGioHang.get(i).getGiaSanPham();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mTextTongTien.setText(decimalFormat.format(tongTien) + " VND");
    }

    private void checkData() {
        if (MainActivity.mArrayGioHang.size() <= 0) {
            // Nếu không có dữ liệu , hiện thông báo , ẩn list view
            mGioHangAdapter.notifyDataSetChanged();
            mTextThongBao.setVisibility(View.VISIBLE);
            mRecyclerViewGioHang.setVisibility(View.INVISIBLE);
        } else {
            mGioHangAdapter.notifyDataSetChanged();
            mTextThongBao.setVisibility(View.INVISIBLE);
            mRecyclerViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(mToolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findID() {
        mTextThongBao = (TextView) findViewById(R.id.text_thong_bao_gio_hang_rong);
        mButtonThanhToan = (Button) findViewById(R.id.button_thanh_toan_gio_hang);
        mButtonTiepTucMua = (Button) findViewById(R.id.button_tiep_tuc_mua_hang);
        mToolbarGioHang = (Toolbar) findViewById(R.id.toolbar_gio_hang);
        mTextTongTien = (TextView) findViewById(R.id.text_tong_gia_tien);

        mRecyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerview_gio_hang);
        mGioHangAdapter = new GioHangAdapter(getApplicationContext(), MainActivity.mArrayGioHang);
        mRecyclerViewGioHang.setHasFixedSize(true);
        mRecyclerViewGioHang.setAdapter(mGioHangAdapter);
        mRecyclerViewGioHang.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
