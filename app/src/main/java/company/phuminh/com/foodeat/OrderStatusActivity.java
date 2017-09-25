package company.phuminh.com.foodeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import company.phuminh.com.foodeat.Common.Common;
import company.phuminh.com.foodeat.Model.Request;
import company.phuminh.com.foodeat.ViewHoder.OrderViewHolder;

public class OrderStatusActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listorder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder(Common.currentUser.getPhone());
    }

    private void loadOrder(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(Request.class, R.layout.order_layout, OrderViewHolder.class, requests.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txt_orderId.setText(adapter.getRef(position).getKey());
                Toast.makeText(OrderStatusActivity.this, convertCodeStatus(model.getStatus()), Toast.LENGTH_SHORT).show();
                viewHolder.txtOrderStatus.setText(convertCodeStatus(model.getStatus()));
                viewHolder.txtOrderAddres.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeStatus(String status){
        if(status.equals("0")){
            return "Place";
        } else if(status.equals("1")){
            return "On my way";
        }
        else return "Shipped";
    }
}
