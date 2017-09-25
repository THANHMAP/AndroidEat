package company.phuminh.com.foodeat.ViewHoder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import company.phuminh.com.foodeat.Interface.ItemClickListener;
import company.phuminh.com.foodeat.R;

/**
 * Created by user on 9/25/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_orderId, txtOrderStatus, txtOrderPhone, txtOrderAddres;
    private ItemClickListener itemClickListener;
    public OrderViewHolder(View itemView) {
        super(itemView);
        txt_orderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderAddres = itemView.findViewById(R.id.order_addres);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

    }
}
