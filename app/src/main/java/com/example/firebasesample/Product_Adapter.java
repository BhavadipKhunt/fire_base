package com.example.firebasesample;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Product_Adapter extends FirebaseRecyclerAdapter<product_data,Product_Adapter.ProductHolder>
{

    public Product_Adapter(@NonNull FirebaseRecyclerOptions<product_data> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull Product_Adapter.ProductHolder holder, int position, @NonNull product_data model) {
        holder.pName.setText(""+model.getName());
        holder.pPrice.setText(""+model.getPrice());
        holder.pDes.setText(""+model.getCategory());
        Log.d("TTT", "onBindViewHolder: "+model.getName());
    }

    @NonNull
    @Override
    public Product_Adapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_show_layout,parent,false);
        ProductHolder holder=new ProductHolder(view);
        return holder;
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        TextView pName,pPrice,pDes;
        ImageView imageView;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            pName=itemView.findViewById(R.id.product_show_name);
            pPrice=itemView.findViewById(R.id.product_show_price);
            pDes=itemView.findViewById(R.id.product_show_desc);
            imageView=itemView.findViewById(R.id.product_show_image);

        }
    }
}
