package company.phuminh.com.foodeat;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import company.phuminh.com.foodeat.Database.Database;
import company.phuminh.com.foodeat.Model.Food;
import company.phuminh.com.foodeat.Model.Order;

public class FoodDetailActivity extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btrCart;
    ElegantNumberButton numberButton;
    String food_id = "";
    FirebaseDatabase database;
    DatabaseReference food;
    Food food_curent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");

        numberButton = findViewById(R.id.number_button);
        btrCart = findViewById(R.id.btnCart);

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_description = findViewById(R.id.food_description);
        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpanderAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        
        if(getIntent() != null){
            food_id = getIntent().getStringExtra("FoodId");
        }
        
        if(!food_id.isEmpty()){
            getDetailFood(food_id);
        }

        btrCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(FoodDetailActivity.this).addTocart(new Order(
                        food_id,
                        food_curent.getName(),
                        numberButton.getNumber(),
                        food_curent.getPrice(),
                        food_curent.getDiscount()
                ));
            }
        });

    }

    private void getDetailFood(final String food_id) {
        food.child(food_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 food_curent = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext())
                        .load(food_curent.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(food_curent.getName());
                food_price.setText(food_curent.getPrice());
                food_name.setText(food_curent.getName());
                food_description.setText(food_curent.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
