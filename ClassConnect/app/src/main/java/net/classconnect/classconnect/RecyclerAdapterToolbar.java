package net.classconnect.classconnect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cindy on 1/16/2016.
 */
public class RecyclerAdapterToolbar extends RecyclerView.Adapter<RecyclerAdapterToolbar.CustomViewHolder> {

    public List<CardItemModel> cardItems;

    public RecyclerAdapterToolbar(List<CardItemModel> cardItems){
        this.cardItems = cardItems;
    }

    @Override
    public RecyclerAdapterToolbar.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.title.setText(cardItems.get(position).title);
        holder.content.setText(cardItems.get(position).content);
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.card_title);
            this.content = (TextView)itemView.findViewById(R.id.card_content);
        }
    }
}
