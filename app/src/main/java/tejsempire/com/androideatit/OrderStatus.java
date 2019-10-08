package tejsempire.com.androideatit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tejsempire.com.androideatit.Common.Common;
import tejsempire.com.androideatit.Model.Request;
import tejsempire.com.androideatit.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());

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

    private void loadOrders(String phone) {
        FirebaseRecyclerOptions<Request> options=
                new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(requests.orderByChild("phone").equalTo(phone),Request.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options){
            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout,parent,false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull Request model) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStaus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
            }

//            @Override
//            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
//                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
//                viewHolder.txtOrderStaus.setText(convertCodeToStatus(model.getStatus()));
//                viewHolder.txtOrderAddress.setText(model.getAddress());
//                viewHolder.txtOrderPhone.setText(model.getPhone());
        //}
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On My Way";
        else
            return "Shipped";
    }
}
