package zhibo.zsj.com.gitlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8.
 */
public abstract class BaseFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater,container,savedInstanceState);
        ButterKnife.bind(this,view);
        initToolbar();
        init();
        return view;
    }

    private void initToolbar() {

    }

    public abstract View createView(LayoutInflater layoutInflater
            ,ViewGroup viewGroup,Bundle savedInstanceState);
    public abstract void init();
}
