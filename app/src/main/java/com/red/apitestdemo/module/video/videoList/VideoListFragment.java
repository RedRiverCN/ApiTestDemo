package com.red.apitestdemo.module.video.videoList;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.red.apitestdemo.R;
import com.red.apitestdemo.bean.dbentity.VideoList;
import com.red.apitestdemo.bean.VideoListDao;
import com.red.apitestdemo.db.VideoListHelper;
import com.red.apitestdemo.module.video.videoList.data.VideoContent;
import com.red.apitestdemo.module.video.videoList.data.VideoItem;
import com.red.apitestdemo.network.RetrofitHelper;
import com.red.apitestdemo.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class VideoListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_VIDEO_CATEGORY = "video-category";
    private static final String ARG_VIDEO_STAGE = "video-stage";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private String mCategory;
    private String mStage;

    private MyItemRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private VideoListHelper mHelper;
    private List<VideoList> dbVideoLists;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VideoListFragment newInstance(int columnCount, String category, String stage) {
        VideoListFragment fragment = new VideoListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_VIDEO_CATEGORY, category);
        args.putSerializable(ARG_VIDEO_STAGE, stage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mCategory = (String) getArguments().getSerializable(ARG_VIDEO_CATEGORY);
            mStage = (String) getArguments().getSerializable(ARG_VIDEO_STAGE);
        }

        loadCache();//检查是否有缓存
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            //mRecyclerView = (RecyclerView) view.findViewById(R.id.list);整个view就是recyclerview
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            updateUI();//通知adapter更新数据
        }
        return view;
    }

    private void loadCache() {
        mHelper = VideoListHelper.getInstance();

        //读取指定条件视频数据
        dbVideoLists = mHelper.queryBuilder()
                .where(VideoListDao.Properties.MemberCategory.eq(mCategory))
                .where(VideoListDao.Properties.MemberStage.eq(mStage))
                .list();

        //有缓存
        if (dbVideoLists.size() > 0) {
            List<VideoItem> VideoItems = new ArrayList<>();;

            //加载数据库的数据
            for (VideoList s : dbVideoLists) {
                int videoId = s.getMemberId();
                String title = s.getMemberTitle();
                String time = s.getMemberTime();
                Uri thumbUri = Uri.parse(ConstantUtil.SDPATH + ConstantUtil.THUMB_TEMP_DIR + videoId);


                VideoItem item = new VideoItem(videoId, thumbUri, title, time);

                Log.d("db: ", "ITEM: "+ videoId + ", " + thumbUri + ", " + title + ", " + time);

                VideoItems.add(item);
            }
            VideoContent.getInstance(getActivity()).setItems(VideoItems);

        } else {
            toRefreshDataFromNetwork();
        }
    }

    private void toRefreshDataFromNetwork() {
        RetrofitHelper.getInstance()
                .getVideoAPI()
                .getVideo(id)
                .compose(RetrofitHelper.getInstance().rxSchedulerHelper());
        mModel.getVideoInfo(mView.getId())
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .doOnNext(videoBean -> {
                    //判断本地有无该视频的缓存,文件名为视频id
                    File file = new File(SDPATH + TEMP + mView.getId());
                    String videoUrl;
                    if (file.canRead()) {
                        videoUrl = file.getAbsolutePath();
                    } else {
                        videoUrl = ApiConstants.VIDEO_BASE_URL + "demo/" + videoBean.getMp4url();
                    }
                    //加载缩略图
                    String imgUrl = ApiConstants.VIDEO_BASE_URL + "demo/" + videoBean.getImgurl();//修正服务器URL
                    getVideoImg(mContext,imgUrl);

                    mView.onSuccess(videoUrl, videoBean.getTitle());
                })
                .doOnError(throwable -> mView.onError("网络请求出错"))
                .doOnComplete(() -> mView.onFinish())
                .subscribe();
    }

    private void updateUI() {
        List<VideoItem> items = VideoContent.getInstance(getActivity()).getItems();

        if (mAdapter == null) {
            mAdapter = new MyItemRecyclerViewAdapter(items);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(items);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("sdcard dir========", "onViewCreated: "+ ConstantUtil.SDPATH);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    /**
     * {@link RecyclerView.Adapter} that can display a {@link VideoItem}
     * TODO: Replace the implementation with code for your data type.
     */
    private class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<VideoListFragment.ViewHolder> {
        private List<VideoItem> mValues;

        MyItemRecyclerViewAdapter(List<VideoItem> items) {
            mValues = items;
        }

        public void setItems(List<VideoItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_video_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.bindItem(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SimpleDraweeView mDraweeView;
        private TextView mTitleView;
        private VideoItem mItem;

        ViewHolder(View view) {
            super(view);
            mDraweeView = (SimpleDraweeView) view.findViewById(R.id.course_image_view);
            mTitleView = (TextView) view.findViewById(R.id.tv_course_title);
        }

        public void bindItem(VideoItem item) {
            mItem = item;
            mDraweeView.setImageURI(mItem.getThumbUri());
            mTitleView.setText(mItem.getTitle());
        }

        @Override
        public void onClick(View v) {
//            //指定视频id，有无缓存
//            Intent intent = VideoDetailsActivity.newIntent(getActivity(), mItem.getVideoId(), hasVideoCache);
//            startActivity(intent);
        }
    }
}
