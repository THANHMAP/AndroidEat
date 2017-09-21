package company.phuminh.com.foodeat;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import company.phuminh.com.foodeat.Common.Common;
import company.phuminh.com.foodeat.Database.Database;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import company.phuminh.com.foodeat.Model.Order;
import company.phuminh.com.foodeat.Model.Request;
import company.phuminh.com.foodeat.ViewHoder.CartAdapter;
import info.hoang8f.widget.FButton;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference request;
    TextView txtTotalPrice;
    FButton btnPlace;
    List<Order> orderList = new ArrayList<>();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CartActivity.this);
        mBuilder.setTitle("One more Step");
        mBuilder.setMessage("Enter your address");

        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        edtAddress.setLayoutParams(layoutParams);
        mBuilder.setView(edtAddress);
        mBuilder.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request requests = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        orderList
                );

                request.child(String.valueOf(System.currentTimeMillis())).setValue(requests);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this, "Thank you ok", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.show();
    }

    private void loadListFood() {
        orderList = new Database(this).orderList();
        cartAdapter = new CartAdapter(orderList, this);
        recyclerView.setAdapter(cartAdapter);

        int total = 0;
        for(Order order:orderList){
            total+= Integer.parseInt(order.getPrice())* Integer.parseInt(order.getQuantity());
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotalPrice.setText(fmt.format(total));
        }
    }
}
