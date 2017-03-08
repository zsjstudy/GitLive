package zhibo.zsj.com.gitlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhibo.zsj.com.gitlive.R;
import zhibo.zsj.com.gitlive.adapter.CustomPageAdapter;

/**
 * Created by Administrator on 2017/3/8.
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpage)
    ViewPager viewPage;
    ArrayList<View> mViewList;//保存viewpage
    ArrayList<String> mTabList;//保存标题

    LayoutInflater mLayoutInflater;
    View jingxuan;
    View hot;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.mainfragment, container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
    }
    private void initView() {
        //添加viewpage
        jingxuan = mLayoutInflater.inflate(R.layout.jingxuanfragment,null);
        hot = mLayoutInflater.inflate(R.layout.hotfragment,null);
        mViewList.add(jingxuan);
        mViewList.add(hot);
        //添加标题
        mTabList.add("精选");
        mTabList.add("发现");

        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置标签的模式,默认系统模式
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPage.setCurrentItem(tab.getPosition());//点击哪个就跳转哪个界面
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(0)));
                tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(1)));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        CustomPageAdapter myAdapter = new CustomPageAdapter(mViewList, mTabList);
        viewPage.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(viewPage);
        tabLayout.setTabsFromPagerAdapter(myAdapter);

    }

    @Override
    public void init() {
        mTabList = new ArrayList<String>();
        mViewList = new ArrayList<View>();
        mLayoutInflater = LayoutInflater.from(getActivity());
    }
}
