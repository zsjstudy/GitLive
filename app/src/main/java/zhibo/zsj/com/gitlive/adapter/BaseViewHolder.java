package zhibo.zsj.com.gitlive.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/22.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private SparseArray<View> views;
    private BaseAdapter.OnItemClickListener onItemClickListener;
    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.views = new SparseArray<>();
        this.onItemClickListener = listener;
    }

    private <V extends View>V retrieveView(int viewId){
        View view = views.get(viewId);
        if(view==null){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (V)view;
    }

    public View getView(int viewId){
        return retrieveView(viewId);
    }

    public TextView getTextView(int viewId){
        return retrieveView(viewId);
    }
    public ImageView getImageView(int viewId){
        return retrieveView(viewId);
    }
    public Button getButton(int viewId){
        return retrieveView(viewId);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
