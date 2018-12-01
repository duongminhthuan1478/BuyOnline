package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.activity.GioHangActivity;
import com.example.dell.buyonline.activity.MainActivity;
import com.example.dell.buyonline.model.GioHang;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<GioHang> mArrayGioHang = new ArrayList<>();

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayGioHang) {
        mContext = context;
        mArrayGioHang = arrayGioHang;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gio_hang_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);


    }

    @Override
    public int getItemCount() {
        return mArrayGioHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTenSpGioHang, textGiaSpGioHang;
        ImageView imageSpGioHang;
        Button buttonCong, buttonTru, buttonValue;

        public ViewHolder(View itemView) {
            super(itemView);
            textTenSpGioHang = itemView.findViewById(R.id.text_ten_sp_gio_hang);
            textGiaSpGioHang = itemView.findViewById(R.id.text_gia_sp_gio_hang);
            imageSpGioHang = itemView.findViewById(R.id.image_gio_hang);
            buttonCong = itemView.findViewById(R.id.button_add);
            buttonTru = itemView.findViewById(R.id.button_minus);
            buttonValue = itemView.findViewById(R.id.button_value);
        }

        public void setData(final int position) {
            GioHang gioHang = mArrayGioHang.get(position);
            textTenSpGioHang.setText(gioHang.getTenSanPham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            textGiaSpGioHang.setText(decimalFormat.format(gioHang.getGiaSanPham()) + " VND");
            Picasso.with(mContext).load(gioHang.getImageSanPham())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(imageSpGioHang);
            // Cast ve dang chuoi
            buttonValue.setText(String.valueOf(gioHang.getSoLuongSanPham()));

            int soluong = Integer.parseInt(buttonValue.getText().toString());
            if(soluong >= 10) {
                buttonCong.setVisibility(View.INVISIBLE);
                buttonTru.setVisibility(View.VISIBLE);
            } else if (soluong <= 1) {
                buttonTru.setVisibility(View.INVISIBLE);
                buttonCong.setVisibility(View.VISIBLE);
            } else {
                buttonCong.setVisibility(View.VISIBLE);
                buttonTru.setVisibility(View.VISIBLE);
            }

            buttonCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongMoiNhat = Integer.parseInt(buttonValue.getText().toString()) + 1;
                    int soLuongHienTai = MainActivity.mArrayGioHang.get(position).getSoLuongSanPham();
                    int giaHienTai = MainActivity.mArrayGioHang.get(position).getGiaSanPham();

                    MainActivity.mArrayGioHang.get(position).setSoLuongSanPham(soLuongMoiNhat);
                    int giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                    MainActivity.mArrayGioHang.get(position).setGiaSanPham(giaMoiNhat);

                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    textGiaSpGioHang.setText(decimalFormat.format(giaMoiNhat) + " VND");

                    //Update lai tổng tiền
                    GioHangActivity.EvenUtil();

                    if(soLuongMoiNhat > 9) {
                        buttonCong.setVisibility(View.INVISIBLE);
                        buttonTru.setVisibility(View.VISIBLE);
                        buttonValue.setText(String.valueOf(soLuongMoiNhat));
                    } else {
                        buttonCong.setVisibility(View.VISIBLE);
                        buttonTru.setVisibility(View.VISIBLE);
                        buttonValue.setText(String.valueOf(soLuongMoiNhat));
                    }
                }
            });
            buttonTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongMoiNhat = Integer.parseInt(buttonValue.getText().toString()) -1;
                    int soLuongHienTai = MainActivity.mArrayGioHang.get(position).getSoLuongSanPham();
                    int giaHienTai = MainActivity.mArrayGioHang.get(position).getGiaSanPham();

                    MainActivity.mArrayGioHang.get(position).setSoLuongSanPham(soLuongMoiNhat);
                    int giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                    MainActivity.mArrayGioHang.get(position).setGiaSanPham(giaMoiNhat);

                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    textGiaSpGioHang.setText(decimalFormat.format(giaMoiNhat) + " VND");

                    //Update lai tổng tiền
                    GioHangActivity.EvenUtil();

                    if(soLuongMoiNhat < 2) {
                        buttonCong.setVisibility(View.VISIBLE);
                        buttonTru.setVisibility(View.INVISIBLE);
                        buttonValue.setText(String.valueOf(soLuongMoiNhat));
                    } else {
                        buttonCong.setVisibility(View.VISIBLE);
                        buttonTru.setVisibility(View.VISIBLE);
                        buttonValue.setText(String.valueOf(soLuongMoiNhat));
                    }
                }
            });

        }
    }
}
