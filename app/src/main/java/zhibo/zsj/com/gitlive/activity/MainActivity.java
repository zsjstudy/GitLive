package zhibo.zsj.com.gitlive.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhibo.zsj.com.gitlive.R;
import zhibo.zsj.com.gitlive.fragment.MainFragment;
import zhibo.zsj.com.gitlive.fragment.MineFragment;
import zhibo.zsj.com.gitlive.widget.PhoenixToolbar;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.toolbar)
    PhoenixToolbar toolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.vp)
    ViewPager viewPage;
    @BindView(R.id.mianfragment)
    RadioButton mainButton;
    @BindView(R.id.minefragment)
    RadioButton mineButton;

    private FragmentPagerAdapter adapter;
    private List<Fragment> mFragments = new ArrayList<>();

    private FragmentManager fm;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //fm = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);
        initView();
        initToolbar();
        //changeFragment(new MainFragment(),false);
    }

    private void initView() {
        mFragments.add(MainFragment.newInstance());
        mFragments.add(MineFragment.newInstance());

        adapter = new FragmentPagerAdapter(this.getSupportFragmentManager()) {
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
    }


    public void initToolbar(){
        toolbar.setTitle("映客直播");
        toolbar.showTextTitle();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.mianfragment:
                viewPage.setCurrentItem(0);
                break;
            case R.id.minefragment:
                viewPage.setCurrentItem(1);
                break;
        }
    }



//    //切换不同的fragment
//    public void changeFragment(Fragment fragment,boolean isInit){
//        //开启事务
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fl,fragment);
//        if(!isInit){
//            //添加到回退栈   避免多个碎片重叠的效果
//            ft.addToBackStack(null);
//        }
//        ft.commit();
//    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
