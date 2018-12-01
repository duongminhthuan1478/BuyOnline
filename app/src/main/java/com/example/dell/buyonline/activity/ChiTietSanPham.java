package com.example.dell.buyonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.GioHang;
import com.example.dell.buyonline.model.SanPham;
import com.example.dell.buyonline.ultil.CheckConnection;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity {

    private Toolbar mToolbarChiTiet;
    private ImageView mImageChitiet;
    private TextView mTextTenSp, mTextGiaSp, mTextMoTaSp;
    private Spinner mSpinnerSoLuong;
    private Button mButtonThemGioHang;

    private int mId, mIdLoaiSanPhamChiTiet;
    private String mTenSanPhamChiTiet, mImageSanPhamChiTiet, mMotaSanPhamChiTiet;
    private int mGiaSanPhamChiTiet;

    boolean exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        findID();
        actionToolbar();
        getInformation();
        eventForSpinner();
        eventForButtonThemGioHang();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_gio_hang:
                // Nếu click vào icon giỏ hàng -> chuyển đến activity giỏ hàng
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventForButtonThemGioHang() {
        mButtonThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mArrayGioHang.size() > 0) {
                    // Nếu mảng đã có dữ liệu , update
                    int soLuongSp = Integer.parseInt(mSpinnerSoLuong.getSelectedItem().toString());

                    for (int viTri = 0; viTri < MainActivity.mArrayGioHang.size(); viTri++) {
                        if (MainActivity.mArrayGioHang.get(viTri).getId() == mId) {
                            // Kiểm tra id của mảng giỏ hàng = id của Chi tiết activity
                            // -> cập nhập số lượng
                            MainActivity.mArrayGioHang.get(viTri)
                                    .setSoLuongSanPham(MainActivity.mArrayGioHang.get(viTri)
                                            .getSoLuongSanPham() + soLuongSp);
                            // Nếu giỏ hàng hơn 10 sản phẩm -> reset về 10
                            if (MainActivity.mArrayGioHang.get(viTri).getSoLuongSanPham() >= 10) {
                                MainActivity.mArrayGioHang.get(viTri).setSoLuongSanPham(10);
                            }
                            // Cập nhập tổng giá
                            MainActivity.mArrayGioHang.get(viTri)
                                    .setGiaSanPham(MainActivity.mArrayGioHang.get(viTri)
                                            .getSoLuongSanPham() * mGiaSanPhamChiTiet);
                            exist = true;
                        }
                    }
                    if (exist == false) {
                        // Nếu dữ liệu chưa xuất hiện
                        int soLuong =
                                Integer.parseInt(mSpinnerSoLuong.getSelectedItem().toString());
                        int giaTongSoLuong = soLuong * mGiaSanPhamChiTiet;

                        MainActivity.mArrayGioHang.add(
                                new GioHang(mId, mTenSanPhamChiTiet, giaTongSoLuong,
                                        mImageSanPhamChiTiet, soLuong));
                    }
                } else {
                    // Nếu chưa có dữ liệu
                    int soLuong = Integer.parseInt(mSpinnerSoLuong.getSelectedItem().toString());
                    int giaTongSoLuong = soLuong * mGiaSanPhamChiTiet;

                    MainActivity.mArrayGioHang.add(
                            new GioHang(mId, mTenSanPhamChiTiet, giaTongSoLuong,
                                    mImageSanPhamChiTiet, soLuong));
                }

                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventForSpinner() {
        Integer [] soLuong = new Integer[] {1,2,3,4,5,6, 7, 8, 9, 10};
//        ArrayList<Integer> soLuong = new ArrayList<>();
//        for (int i = 0; i <= 10; i++) {
//            //Cho mảng số lượng chạy từ 1 -> 10
//            if (i > 0) {
//                soLuong.add(i);
//            }
//        }
        ArrayAdapter<Integer> arrayAdapter =
                new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item,
                        soLuong);
        mSpinnerSoLuong.setAdapter(arrayAdapter);
    }

    private void getInformation() {
        // intent.putExtra("thongtinsanpham", mArrayDienThoai.get(position));
        // Lấy đối tượng sản phẩm từ  intent với name = thongtinsanpham
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        mId = sanPham.getID();
        mTenSanPhamChiTiet = sanPham.getTenSanPham();
        mGiaSanPhamChiTiet = sanPham.getGiaSanPham();
        mImageSanPhamChiTiet = sanPham.getHinhAnhSanPham();
        mMotaSanPhamChiTiet = sanPham.getMotaSanPham();
        mIdLoaiSanPhamChiTiet = sanPham.getIdLoaiSanPham();

        // Đưa dữ liệu lên layout
        mTextTenSp.setText(mTenSanPhamChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mTextGiaSp.setText("Giá : " + decimalFormat.format(mGiaSanPhamChiTiet) + " VND");
        mTextMoTaSp.setText(mMotaSanPhamChiTiet);
        // Mảng trả về link url -> đọc hình từ picasso
        Picasso.with(getApplicationContext())
                .load(mImageSanPhamChiTiet)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(mImageChitiet);
    }

    private void actionToolbar() {
        setSupportActionBar(mToolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findID() {
        mToolbarChiTiet = (Toolbar) findViewById(R.id.toolbar_chi_tiet);
        mImageChitiet = (ImageView) findViewById(R.id.image_chi_tiet_san_pham);
        mTextTenSp = (TextView) findViewById(R.id.text_ten_sp_chi_tiet);
        mTextGiaSp = (TextView) findViewById(R.id.text_gia_sp_chi_tiet);
        mTextMoTaSp = (TextView) findViewById(R.id.text_mo_ta_sp_chi_tiet);
        mSpinnerSoLuong = (Spinner) findViewById(R.id.spiner_sp_chi_tiet);
        mButtonThemGioHang = (Button) findViewById(R.id.button_them_gio_hang);
    }
}
