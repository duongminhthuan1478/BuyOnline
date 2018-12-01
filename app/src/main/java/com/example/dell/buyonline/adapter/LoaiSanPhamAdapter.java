package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.LoaiSanPham;
import com.example.dell.buyonline.ultil.OnItemClickListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Dell on 03-Mar-18.
 */

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.ViewHolder> {

    private ArrayList<LoaiSanPham> mArrayLoaiSp = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;


    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> arrayLoaiSp) {
        mArrayLoaiSp = arrayLoaiSp;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loai_san_pham_item, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mArrayLoaiSp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextTenLoaisp;
        ImageView mImageLoaisp;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextTenLoaisp = itemView.findViewById(R.id.text_ten_loai_sp);
            mImageLoaisp = itemView.findViewById(R.id.imageview_loaisp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void setData(int position){
            LoaiSanPham loaisp = mArrayLoaiSp.get(position);
            mTextTenLoaisp.setText(loaisp.getTenLoaiSp());
            Picasso.with(mContext).load(loaisp.getHinhAnhLoaiSp())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(mImageLoaisp);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
