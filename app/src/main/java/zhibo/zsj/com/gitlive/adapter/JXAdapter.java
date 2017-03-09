package zhibo.zsj.com.gitlive.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import zhibo.zsj.com.gitlive.R;
import zhibo.zsj.com.gitlive.bean.Jingxuan;

/**
 * Created by Administrator on 2017/3/9.
 */
public class JXAdapter  extends SimpleAdapter<Jingxuan> {

    public JXAdapter(Context context, List<Jingxuan> datas) {
        super(context, R.layout.jinguxan_item, datas);
    }

    @Override
    protected void convert(BaseViewHolder holder, Jingxuan item) {
        SimpleDraweeView riv = (SimpleDraweeView) holder.getView(R.id.jingxuan_item_title_iv);
        riv.setImageURI(Uri.parse(item.getHeadIcon()));
        holder.getTextView(R.id.jingxuan_item_name_tv).setText(item.getName());
        holder.getTextView(R.id.jingxuan_item_where_tv).setText(item.getPlace());
        SimpleDraweeView iv = (SimpleDraweeView) holder.getView(R.id.jingxuan_item_people_iv);
        iv.setImageURI(Uri.parse(item.getInformationImage()));
        holder.getButton(R.id.jingxuan_item_state_tv).setText(item.getStatus());

    }
}
