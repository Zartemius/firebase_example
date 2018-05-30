package com.example.artem.firstappwithnotification.mainactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artem.firstappwithnotification.R;
import com.example.artem.firstappwithnotification.appdatabase.Order;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NewViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private Context mContext;
    private List<Order> mListOfOrders = new ArrayList<>();
    private OnItemClickListener mListener;

    public void setOnItemClickListner(OnItemClickListener listner){
        mListener = listner;
    }

    public Adapter(Context context, List<Order> listOfOrders){
        mContext = context;
        mListOfOrders = listOfOrders;
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{

        TextView numberOfOrder;

        public NewViewHolder(View viewItem,final OnItemClickListener listener){
            super(viewItem);

            numberOfOrder = itemView.findViewById(R.id.item_view_holder__numberOfOrder);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
        Order currentItem = mListOfOrders.get(position);
        String numberOfOrder = currentItem.getNumberOfOrder();
        holder.numberOfOrder.setText(numberOfOrder);
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_holder,parent,false);
        NewViewHolder newViewHolder = new NewViewHolder(view,mListener);
        return newViewHolder;
    }

    @Override
    public int getItemCount() {
        return mListOfOrders.size();
    }
}
