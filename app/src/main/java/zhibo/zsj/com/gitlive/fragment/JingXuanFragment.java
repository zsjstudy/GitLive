package zhibo.zsj.com.gitlive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhibo.zsj.com.gitlive.R;
import zhibo.zsj.com.gitlive.adapter.BaseAdapter;
import zhibo.zsj.com.gitlive.adapter.JXAdapter;
import zhibo.zsj.com.gitlive.adapter.decotation.DividerItemDecoration;
import zhibo.zsj.com.gitlive.bean.Jingxuan;
import zhibo.zsj.com.gitlive.bean.PageResult;
import zhibo.zsj.com.gitlive.http.Contants;
import zhibo.zsj.com.gitlive.utils.PageUtils;

/**
 * Created by Administrator on 2017/3/8.
 */
public class JingXuanFragment extends Fragment {


    @BindView(R.id.jingxuan_mrl)
    MaterialRefreshLayout mRefreshLayout;
    @BindView(R.id.jingxuan_rl)
    RecyclerView mRecyclerView;

    private JXAdapter jxAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jingxuanfragment, container,false);
        ButterKnife.bind(this,view);

        PageUtils pageUtils = PageUtils.newBuilder()
                .setUrl(Contants.API.BASE_URL)
                .setLoadMore(true)
                .setOnPageListener(new PageUtils.OnPageListener() {
                    @Override
                    public void load(List datas, int totalPage, int totalCount) {
                        jxAdapter = new JXAdapter(getActivity(),datas);
                        jxAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Toast.makeText(getContext(),"点击了position",Toast.LENGTH_SHORT).show();
//                                Jingxuan wares = jxAdapter.getItem(position);
//                                Intent intent = new Intent(getActivity(), WareDetailActivity.class);
//                                intent.putExtra(Contants.WARE,wares);//这样传值实体类一定得实现序列化接口
//                                startActivity(intent);

                            }
                        });
                        mRecyclerView.setAdapter(jxAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                                getActivity(),DividerItemDecoration.VERTICAL_LIST));
                    }

                    @Override
                    public void refresh(List datas, int totalPage, int totalCount) {
                        jxAdapter.clearData();//刷新是吧页面数据换掉从新加载新数据所以从头开始加载
                        jxAdapter.addData(datas);
                        mRecyclerView.scrollToPosition(0);//滚动到最前面
                    }

                    @Override
                    public void loadMore(List datas, int totalPage, int totalCount) {
                        //加载更多是从最后一条数据开始插入
                        jxAdapter.addData(jxAdapter.getItemCount(),datas);
                        mRecyclerView.scrollToPosition(jxAdapter.getItemCount());
                    }
                })
                .setPageSize(20)
                .setRefreshLayout(mRefreshLayout)
                .build(getActivity(),new TypeToken<PageResult<Jingxuan>>(){}.getType());
        pageUtils.request();
        return view;
    }
}
