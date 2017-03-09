package zhibo.zsj.com.gitlive.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhibo.zsj.com.gitlive.R;

/**
 * Created by Administrator on 2017/3/8.
 */
public class HotFragment extends BaseFragment{


    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.hotfragment, container,false);
        return view;
    }

}
