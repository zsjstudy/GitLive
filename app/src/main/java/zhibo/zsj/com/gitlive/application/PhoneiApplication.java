package zhibo.zsj.com.gitlive.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2017/2/20.
 */
public class PhoneiApplication extends Application{

    public void onCreate() {
        super.onCreate();
        //基本使用的初始化方法
        Fresco.initialize(this);
    }

}
