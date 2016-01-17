package net.classconnect.classconnect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.widget.JoinAppGroupDialog;

import java.util.List;

/**
 * Created by Cindy on 1/16/2016.
 */
public class RecyclerAdapterToolbar extends RecyclerView.Adapter<RecyclerAdapterToolbar.CustomViewHolder> {

    public List<CardItemModel> cardItems;
    private CourseListActivity activity;

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
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final String className = cardItems.get(position).title;
        holder.title.setText(className);
        holder.content.setText(cardItems.get(position).content);
        final boolean needToCreate = cardItems.get(position).empty;
        if(needToCreate) {
            holder.join.setText("create");
            holder.content.setVisibility(View.GONE);
        }
        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(needToCreate){
                    activity.createGroup(className, "A group for " + className + ".");
                }else{
                    activity.addUser(className);
                }
            }
        });
    }

    public void setActivity(CourseListActivity activity){
        this.activity = activity;

    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        Button join;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.card_title);
            this.content = (TextView)itemView.findViewById(R.id.card_content);
            this.join = (Button) itemView.findViewById(R.id.add_facebook_group);
        }
    }
}
