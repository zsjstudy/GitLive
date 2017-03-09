package zhibo.zsj.com.gitlive.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */
public class BaseBean  implements Serializable{

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
