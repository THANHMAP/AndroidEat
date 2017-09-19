package company.phuminh.com.foodeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import company.phuminh.com.foodeat.Interface.ItemClickListener;
import company.phuminh.com.foodeat.Model.Category;
import company.phuminh.com.foodeat.Model.Food;
import company.phuminh.com.foodeat.ViewHoder.FoodViewHolder;
import company.phuminh.com.foodeat.ViewHoder.MenuViewHolder;

public class FoodListActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference foodList;
    RecyclerView recyclerView;
    String catrgoryID = "";
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_foodlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            catrgoryID = getIntent().getStringExtra("CategoryID");
        }
        if(!catrgoryID.isEmpty() && catrgoryID != null){
            loadListFood(catrgoryID);
        }
    }

    private void loadListFood(String catrgoryID) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(catrgoryID) // select form where MenuId = ""
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.food_image);
                final Food food = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, Boolean isClick) {
                        Toast.makeText(FoodListActivity.this, "" + food.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getRequestedOrientation() {
        return super.getRequestedOrientation();
    }
}
