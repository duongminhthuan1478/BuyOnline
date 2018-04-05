package com.example.dell.buyonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.adapter.LoaiSanPhamAdapter;
import com.example.dell.buyonline.adapter.SanPhamAdapter;
import com.example.dell.buyonline.model.LoaiSanPham;
import com.example.dell.buyonline.model.SanPham;
import com.example.dell.buyonline.ultil.CheckConnection;
import com.example.dell.buyonline.ultil.Server;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewFlipper mViewFlipper;
    private RecyclerView mHomeRecyclerView, mMenuRecyclerView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout; //Thanh menu
    private LoaiSanPhamAdapter mLoaispAdapter;
    private SanPhamAdapter mSanPhamAdapter;
    private ArrayList<LoaiSanPham> mArrayLoaisp = new ArrayList<>();
    private ArrayList<SanPham> mArraySanPham = new ArrayList<>();

    private int mID;
    private String mTenLoaiSanPham = "";
    private String mHinhAnhLoaiSanPham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
        // Kiểm tra kết nối internet trước khi tải dữ liệu
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFlipper();
            getDataLoaiSanPham();
            getDataSanPhamMoiNhat();
            clickItemListener();
        } else {
            CheckConnection.showToast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối");
            finish();
        }
    }

    private void clickItemListener() {
        mLoaispAdapter.setOnItemClickListener(new LoaiSanPhamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(),
                                    "Vui lòng kiểm tra kết nối");
                        }
                        // Đóng thanh menu sau khi click
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mArrayLoaisp.get(position).getID());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(),
                                    "Vui lòng kiểm tra kết nối");
                        }
                        // Đóng thanh menu sau khi click
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", mArrayLoaisp.get(position).getID());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast(getApplicationContext(),
                                    "Vui lòng kiểm tra kết nối");
                        }
                        // Đóng thanh menu sau khi click
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getDataSanPhamMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // thư viện Volley hỗ trợ tích hợp đọc dữ liệu Json nhanh gọn
        // qua method Json arrayrequest, object request
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Server.LINK_ITEM_NEW, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int ID = jsonObject.getInt("id");
                                    String tenSanPham = jsonObject.getString("tensp");
                                    Integer giaSanPham = jsonObject.getInt("giasp");
                                    String hinhAnhSp = jsonObject.getString("hinhanhsp");
                                    String moTaSp = jsonObject.getString("motasp");
                                    int idSanPham = jsonObject.getInt("idsanpham");

                                    mArraySanPham.add(
                                            new SanPham(ID, tenSanPham, giaSanPham, hinhAnhSp,
                                                    moTaSp, idSanPham));
                                    mSanPhamAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast(getApplicationContext(), error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Nhận dữ liệu của loại sản phẩm (thanh menu)
     */
    private void getDataLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // thư viện Volley hỗ trợ tích hợp đọc dữ liệu Json nhanh gọn
        // qua method Json arrayrequest, object request
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Server.LINK_TYPE_PRODUCT, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            mArrayLoaisp.add(0, new LoaiSanPham(0, "Home",
                                    "http://icons.iconarchive"
                                            + ".com/icons/graphicloads/colorful-long-shadow/48/Home-icon.png"));
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    mID = jsonObject.getInt("id");
                                    mTenLoaiSanPham = jsonObject.getString("tenloaisp");
                                    mHinhAnhLoaiSanPham = jsonObject.getString("hinhanhloaisp");

                                    mArrayLoaisp.add(new LoaiSanPham(mID, mTenLoaiSanPham,
                                            mHinhAnhLoaiSanPham));

                                    //update lai
                                    mLoaispAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast(getApplicationContext(), error.toString());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionViewFlipper() {
        // Mảng chứa link hình ảnh
        ArrayList<String> arrPromotion = new ArrayList<>();
        arrPromotion.add(
                "http://banhangmypham.vn/wp-content/uploads/2017/06/nen-chon-nguoi-mau-quang-cao"
                        + "-my-pham-theo-tieu-chi-nao-1.jpg");
        arrPromotion.add(
                "http://www.brandsvietnam.com/upload/forum2/2017/03/11813Oppo_1488816752.png");
        arrPromotion.add("http://img.news.zing.vn/img/330/t330036.jpg");
        arrPromotion.add("http://www.brandsvietnam.com/img/2012/aquafina.png");
        arrPromotion.add("http://matic.com.vn/media/news/0807_phn.jpg");

        // Vì View Flipper chứa hình ảnh chứ không phải link nên dùng thư viện Picasso tải hình ảnh
        for (int i = 0; i < arrPromotion.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrPromotion.get(i)).into(imageView);
            //Canh Image vừa ViewFlipper để không mất hình
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mViewFlipper.addView(imageView);
            // Bắt sự kiện tự chạy trong thời gian quy định 5000mili = 5s
            mViewFlipper.setFlipInterval(5000);
            mViewFlipper.setAutoStart(true);
            // Quảng cáo di chuyển
            Animation animationSlideIn =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
            Animation animationSlideOut =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
            mViewFlipper.setInAnimation(animationSlideIn);
            mViewFlipper.setOutAnimation(animationSlideOut);
        }
    }

    private void actionBar() {
        setSupportActionBar(mToolbar);

        // Hiển thị icon và set icon menu trong thư viện có sẵn android
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở thanh menu
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void findID() {
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);
        mHomeRecyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        //Adapter menu
        mMenuRecyclerView = (RecyclerView) findViewById(R.id.menu_recyclerview);
        mMenuRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLoaispAdapter = new LoaiSanPhamAdapter(getApplicationContext(), mArrayLoaisp);
        mMenuRecyclerView.setLayoutManager(layoutManager);
        mMenuRecyclerView.setAdapter(mLoaispAdapter);

        // Adapter cho san pham moi nhat
        mSanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mArraySanPham);
        mHomeRecyclerView.setHasFixedSize(true);
        mHomeRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mHomeRecyclerView.setAdapter(mSanPhamAdapter);
    }
}
