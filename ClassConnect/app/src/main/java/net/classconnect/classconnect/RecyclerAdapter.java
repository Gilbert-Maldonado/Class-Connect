package net.classconnect.classconnect;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;

    public RecyclerAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.class_list_layout, parent, false);
        return RecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        String itemText = mItemList.get(position);
        holder.setItemText(itemText);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public boolean addItem(String s) {
        mItemList.add(s);
        notifyItemInserted(mItemList.size()-1);
        return true;
    }

    public boolean addItem(int pos, String s) {
        mItemList.add(pos, s);
        notifyItemInserted(pos);
        return true;
    }

    public int removeItem(String s){
        int pos = mItemList.indexOf(s);
        return removeItem(pos);
    }
    public int removeItem(int pos) {
        if (pos < 0 || pos >= mItemList.size())
            return pos;
        mItemList.remove(pos);
        notifyItemRemoved(pos);
        return pos;
    }

    public ArrayList<String> getFinalData(){
        return new ArrayList<String>(mItemList);
    }
}

