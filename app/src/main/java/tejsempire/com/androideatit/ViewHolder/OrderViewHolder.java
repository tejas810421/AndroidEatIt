package tejsempire.com.androideatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tejsempire.com.androideatit.Interface.ItemClickListener;
import tejsempire.com.androideatit.R;

/**
 * Created by Tejas on 2/8/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStaus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress=(TextView)itemView.findViewById(R.id.order_address);
        txtOrderId=(TextView)itemView.findViewById(R.id.order_id);
        txtOrderStaus=(TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone=(TextView)itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
