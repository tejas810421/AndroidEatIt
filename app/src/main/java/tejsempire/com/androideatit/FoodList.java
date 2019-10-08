package tejsempire.com.androideatit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import tejsempire.com.androideatit.Interface.ItemClickListener;
import tejsempire.com.androideatit.Model.Food;
import tejsempire.com.androideatit.ViewHolder.FoodViewHolder;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //firebase
        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if (getIntent() != null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null){
            loadListFood(categoryId);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void loadListFood(String categoryId) {
        FirebaseRecyclerOptions<Food> options=
                new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery( foodList.orderByChild("MenuId").equalTo(categoryId)/*like : select *from foods where menuId =*/,Food.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options){
            @Override
            public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext())
                        .inflate( R.layout.food_item,parent,false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int position, @NonNull Food model) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //start new activity
                        Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());//send food id to new activity
                        startActivity(foodDetail);
                    }
                });
            }
        };

        //set adapter
        recyclerView.setAdapter(adapter);
    }
}
