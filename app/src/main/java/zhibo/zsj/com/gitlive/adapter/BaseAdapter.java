package zhibo.zsj.com.gitlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected final Context mContext;
    protected List<T> mDatas;
    protected int mLayoutResId;
    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public BaseAdapter(Context context, int layoutResId, List<T> datas){
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResId,parent,false);
        return new BaseViewHolder(view,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H)holder,item);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    //提供抽象方法
    protected abstract void convert(H holder,T item);

    public T getItem(int positon) {
        return positon>=mDatas.size() ? null :mDatas.get(positon);
    }

    //用于添加数据
    public void addData(List<T> datas){
        addData(0,datas);
    }
    //用于添加数据
    public void addData(int position,List<T> datas){
        if(datas!=null&&datas.size()>0){
            mDatas.addAll(datas);
            notifyItemRangeChanged(0,mDatas.size());//先刷新
        }
    }
    //清空数据
    public void clearData(){
        mDatas.clear();
        notifyItemRangeRemoved(0,mDatas.size());
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }


    public void refreshData(List<T> list){
        if(list!=null&&list.size()>0){
            clearData();
            int size = list.size();
            for(int i = 0; i <size ; i++) {
                mDatas.add(i,list.get(i));
                notifyItemInserted(i);
            }
        }
    }

    public void loadMoreData(List<T> list){
        if(list!=null&&list.size()>0){
            int size = list.size();
            int begin = mDatas.size();
            for(int i = 0; i <size; i++) {
                mDatas.add(list.get(i));
                notifyItemInserted(i+begin);
            }
        }
    }
}
