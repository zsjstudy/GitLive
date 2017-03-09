package zhibo.zsj.com.gitlive.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhibo.zsj.com.gitlive.R;

/**
 * Created by Administrator on 2017/3/8.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpage)
    ViewPager viewPage;
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    private FragmentPagerAdapter adapter;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.mainfragment, container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {

        JingXuanFragment jingXuanFragment = new JingXuanFragment();
        HotFragment hot = new HotFragment();

        mFragments.add(jingXuanFragment);
        mFragments.add(hot);


        FragmentManager fm = getActivity().getSupportFragmentManager();

        adapter = new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);
        viewPage.setOffscreenPageLimit(0);
        tabLayout.getTabAt(0).setText("精选");
        tabLayout.getTabAt(1).setText("热门");

    }
}
