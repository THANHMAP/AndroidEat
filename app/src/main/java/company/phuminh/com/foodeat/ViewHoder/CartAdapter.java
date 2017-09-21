package company.phuminh.com.foodeat.ViewHoder;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import company.phuminh.com.foodeat.Interface.ItemClickListener;
import company.phuminh.com.foodeat.Model.Order;
import company.phuminh.com.foodeat.R;

/**
 * Created by user on 9/20/2017.
 */

class CartViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txt_cart_name, txt_price;
    public ImageView img_cart_count;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewholder(View itemView) {
        super(itemView);
        txt_cart_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_Price);
        img_cart_count = itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewholder>{

    private List<Order> orderList= new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public CartViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewholder(view);
    }

    @Override
    public void onBindViewHolder(CartViewholder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(""+orderList.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(textDrawable);

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = Integer.parseInt(orderList.get(position).getPrice())* Integer.parseInt(orderList.get(position).getQuantity());
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(orderList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
