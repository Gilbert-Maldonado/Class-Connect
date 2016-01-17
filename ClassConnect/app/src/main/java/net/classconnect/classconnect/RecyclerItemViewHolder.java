package net.classconnect.classconnect;

/**
 * Created by Cindy on 1/16/2016.
 */
import android.view.View;
import android.widget.TextView;

import co.dift.ui.SwipeToAction;

public class RecyclerItemViewHolder extends  SwipeToAction.ViewHolder<String> {

    private final TextView mItemTextView;
    public String data;



    public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
        super(parent);
        mItemTextView = itemTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        return new RecyclerItemViewHolder(parent, itemTextView);
    }

    public void setItemText(CharSequence text) {
        data = text.toString();
        mItemTextView.setText(text);
    }

    public String getItemData(){
        return data;
    }

}