package zhibo.zsj.com.gitlive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhibo.zsj.com.gitlive.fragment.MainFragment;
import zhibo.zsj.com.gitlive.fragment.MineFragment;
import zhibo.zsj.com.gitlive.widget.PhoenixToolbar;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.toolbar)
    PhoenixToolbar toolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fm = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);
        initToolbar();
        changeFragment(new MainFragment(),false);
    }

    public void initToolbar(){
        toolbar.setTitle("映客直播");
        toolbar.showTextTitle();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.mianfragment:
                    changeFragment(new MainFragment(),false);
                    break;
                case R.id.minefragment:
                    changeFragment(new MineFragment(),false);
                    break;
            }
    }

    //切换不同的fragment
    public void changeFragment(Fragment fragment,boolean isInit){
        //开启事务
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl,fragment);
        if(!isInit){
            //添加到回退栈   避免多个碎片重叠的效果
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
