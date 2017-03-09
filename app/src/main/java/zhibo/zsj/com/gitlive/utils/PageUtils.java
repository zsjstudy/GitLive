package zhibo.zsj.com.gitlive.utils;

import android.content.Context;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import zhibo.zsj.com.gitlive.bean.PageResult;
import zhibo.zsj.com.gitlive.http.OkHttpHelper;
import zhibo.zsj.com.gitlive.http.SpotsCallBack;

/**
 * Created by Administrator on 2017/2/28.
 */
public class PageUtils {
    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新
    private static final int STATE_MORE = 2;//加载更多
    private int state =STATE_NORMAL;//默认状态是正常状态

    private static Builder builder;
    private OkHttpHelper okHttpHelper;

    private PageUtils(){
        okHttpHelper = OkHttpHelper.getInstance();
        initRefreshLayout();
    }

    private void initRefreshLayout(){
        builder.refreshLayout.setLoadMore(builder.canLoadMore);

        builder.refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if(builder.pageIndex<=builder.totalPage){
                    loadMoreData();
                }else{
                    Toast.makeText(builder.context,"已经没有更多数据了",Toast.LENGTH_SHORT).show();
                    builder.refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    //刷新
    private void refreshData(){
        builder.pageIndex = 1;
        state = STATE_REFRESH;
        requestData();
    }
    //加载更多
    private void loadMoreData(){
        builder.pageIndex += 1;
        state = STATE_MORE;
        requestData();
    }

    //显示数据
    private <T> void showData(List<T> datas,int totalPage,int totalCount){
        switch (state){
            case STATE_NORMAL:
                if(builder.onPageListener!=null){
                    builder.onPageListener.load(datas,totalPage,totalCount);
                }
                break;
            case STATE_REFRESH:
                if(builder.onPageListener!=null){
                    builder.onPageListener.refresh(datas,totalPage,totalCount);
                }
                builder.refreshLayout.finishRefresh();//停止刷新
                break;
            case STATE_MORE:
                if(builder.onPageListener!=null){
                    builder.onPageListener.loadMore(datas,totalPage,totalCount);
                }
                builder.refreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    public interface OnPageListener<T>{
        void load(List<T> datas, int totalPage, int totalCount);
        void refresh(List<T> datas, int totalPage, int totalCount);
        void loadMore(List<T> datas, int totalPage, int totalCount);
    }

    //获取数据
    private void requestData(){
        okHttpHelper.get(buildUrl(), new RequestCallBack(builder.context));
    }

    //用于构建参数
    private String buildUrl(){
        return builder.url+ "?"+builderUrlParams();
    }

    private String builderUrlParams() {
        HashMap<String ,Object> map = builder.params;
        map.put("curPage",builder.pageIndex);
        map.put("pageSize",builder.pageSize);
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,Object> entry : map.entrySet()){
            sb.append(entry.getKey()+"="+entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if(s.endsWith("&")){
            s = s.substring(0,s.length()-1);
        }
        return s;
    }

    public static Builder newBuilder(){
        builder = new Builder();
        return  builder;
    }

    public void request(){
        requestData();
    }

    public void putParam(String key,Object value){
        builder.putParam(key,value);
    }

    //静态内部类
    public static class Builder{
        private Type tyep;

        private Context context;
        private String url;
        private MaterialRefreshLayout refreshLayout;
        private boolean canLoadMore;

        private int totalPage = 1;
        private int pageIndex = 1;//当前页
        private int pageSize = 10;

        private HashMap<String,Object> params = new HashMap<>(5);
        private  OnPageListener onPageListener;

        public Builder setOnPageListener(OnPageListener onPageListener){
            this.onPageListener = onPageListener;
            return builder;
        }

        public Builder setPageSize(int pageSize){
            this.pageSize = pageSize;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout){
            this.refreshLayout = refreshLayout;
            return builder;
        }

        public Builder setLoadMore(boolean loadMore){
            this.canLoadMore = loadMore;
            return builder;
        }

        public Builder setUrl(String url){
            this.url = url;
            return builder;
        }

        public Builder putParam(String key,Object value){
            params.put(key,value);
            return builder;
        }

        public PageUtils build(Context context,Type type){
            this.context = context;
            this.tyep = type;
            validate();
            return new PageUtils();
        }

        private void validate(){
            if(context==null){
                throw new RuntimeException("context can't be null");
            }
            if(this.url==null || "".equals(this.url)){
                throw new RuntimeException("url can't be null");
            }
            if(this.refreshLayout==null){
                throw new RuntimeException("refreshLayout can't be null");
            }

        }
    }

    class RequestCallBack<T> extends SpotsCallBack<PageResult<T>> {

        public RequestCallBack(Context context) {
            super(context);
            super.mType = builder.tyep;
        }

        @Override
        public void onSuccess(Response response, PageResult<T> PageResult) {
            builder.pageIndex = PageResult.getCurrentPage();
            builder.totalPage = PageResult.getTotalPage();
            showData(PageResult.getList(),builder.totalPage
                    ,PageResult.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {

        }
    }

}
