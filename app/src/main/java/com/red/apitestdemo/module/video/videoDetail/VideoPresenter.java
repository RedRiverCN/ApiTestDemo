package com.red.apitestdemo.module.video.videoDetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.red.apitestdemo.R;
import com.red.apitestdemo.bean.VideoDetailsInfo;
import com.red.apitestdemo.bean.VideoListDao;
import com.red.apitestdemo.bean.dbentity.VideoList;
import com.red.apitestdemo.db.VideoListHelper;
import com.red.apitestdemo.network.auxiliary.ApiConstants;
import com.red.downlib.RxDownload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.red.apitestdemo.utils.ConstantUtil.SDPATH;
import static com.red.apitestdemo.utils.ConstantUtil.TEMP;
import static com.red.apitestdemo.utils.ConstantUtil.VIDEO_TEMP_DIR;


/**
 * Created by Red on 2017/6/17.
 */

public class VideoPresenter extends VideoContract.Presenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private VideoListHelper mHelper;
    private VideoList videoList;

    private String videoUrl;
    private String imgUrl;
    private String videoTitle;
    private String category;//所属类别
    private String stage;//阶段

    @Override
    public void playVideo() {

        mView.onLoading();

        mHelper = VideoListHelper.getInstance();
        //读取指定条件视频数据
        List<VideoList> dbVideoLists = mHelper.queryBuilder()
                .where(VideoListDao.Properties.MemberId.eq(mView.getId()))
                .list();

        //有记录
        if (dbVideoLists.size() > 0) {

            videoList = dbVideoLists.get(0);
            if (videoList.getHasVideoCache() == 1) {
                //判断本地有无该视频的磁盘缓存,文件名为视频id
                File file = new File(SDPATH + VIDEO_TEMP_DIR + mView.getId()+ ".mp4");
                if (file.canRead()) {
                    videoUrl = file.getAbsolutePath();
                    mView.toast("播放本地视频");
                } else {
                    videoUrl = videoList.getMemberVideo();
                    videoList.setHasVideoCache(0);
                    mHelper.update(videoList);
                    mView.toast("播放网络视频");
                }

                imgUrl = videoList.getMemberThumb();
                videoTitle= videoList.getMemberTitle();
                category= videoList.getMemberCategory();
                stage= videoList.getMemberStage();

                finishTask();
            }else {
                toRefreshDataFromNetwork();
            }
        }
        else {
            toRefreshDataFromNetwork();
            //建立记录
            videoList = new VideoList();
            videoList.setId(mHelper.count()+1);
            videoList.setMemberId(mView.getId());
            videoList.setMemberThumb(imgUrl);
            videoList.setMemberVideo(videoUrl);
            videoList.setMemberTitle(videoTitle);
            videoList.setMemberTitle(category);
            videoList.setMemberTitle(stage);
            videoList.setHasVideoCache(0);
            mHelper.save(videoList);
        }

    }

    private void toRefreshDataFromNetwork() {
        //访问服务器获取数据
        mModel.getVideoInfo(mView.getId())
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .doOnNext(videoDetailsInfo -> {
                    VideoDetailsInfo.VideoBean videoBean = videoDetailsInfo.getVideo();

                    videoUrl = ApiConstants.VIDEO_BASE_URL + "demo/" + videoBean.getMp4url();
                    imgUrl = ApiConstants.VIDEO_BASE_URL + "demo/" + videoBean.getImgurl();//修正服务器URL
                    category= videoBean.getCategory();
                    stage= videoBean.getStage();

                    finishTask();
                })
                .doOnError(throwable -> mView.onError("网络请求出错"))
                .doOnComplete(() -> mView.onFinish())
                .subscribe();
    }

    private void finishTask() {
        if (TextUtils.isEmpty(imgUrl)) {
            Glide.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.loading) //占位符 也就是加载中的图片，可放个gif
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                //.bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0, RoundedCornersTransformation.CornerType.ALL))//转成圆角
                .crossFade(800)//交叉淡入
                .into(mView.getThumbViewHolder());
        }

        mView.onSuccess(videoUrl, videoTitle);
    }

/*
    @Override
    public void getVideoImg(Context context, String imgUrl) {
        mModel.getVideoImg(context, imgUrl)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .doOnNext(bitmap -> mView.onImgLoad(bitmap))
                .doOnError(throwable -> mView.onError("缩略图获取失败"))
                .subscribe();
//        subscribe(new Observer<Bitmap>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Bitmap bitmap) {
//                        mView.onImgLoad(bitmap);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onError("缩略图获取失败");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }*/

    @Override
    public void playImmediately() {
        mView.playImmediately();
    }

    /**
     * 下载文件
     * @param url 下载链接
     * @param saveName 保存文件名
     * @param savePath 保存路径
     */
    @Override
    public void downloadFiles(@NonNull String url, @NonNull String saveName, @Nullable String savePath) {
        RxDownload.getInstance().context(mContext)
                .maxThread(4)
                .maxRetryCount(3)
                .download(url, saveName, savePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    compositeDisposable.add(disposable);
                    mView.toast("开始下载");
                })
                .doOnNext(downloadStatus -> {
                    //此处更新下载进度
                    mView.setProgressBar((int)(Float.valueOf(downloadStatus.getPercent())*100));
                    //tvDownloadPrg.setText("下载进度：" + downloadStatus.getFormatStatusString());
                })
                .doOnError(throwable -> {
                    //此处处理下载异常
                    //tvDownloadStatus.setText("下载失败:" + throwable.getMessage());
                    mView.toast("下载失败：" + throwable.getMessage());
                })
                .doOnComplete(() -> {
                    //下载完成，解压或安装
                    mView.toast("下载完成");

                    File file = new File(savePath, saveName);
                })
                .subscribe();
    }

    //阵亡
    @Override
    public void viewOnDestroy() {
        compositeDisposable.dispose();
    }

    @Override
    public void startDownload() {
        if (videoList != null && videoList.getHasVideoCache() == 0) {
            //判断本地有无该视频的磁盘缓存,文件名为视频id
            File file = new File(SDPATH + VIDEO_TEMP_DIR + mView.getId() + ".mp4");
            if (file.canRead()) {
                videoList.setHasVideoCache(1);
                mHelper.update(videoList);
                mView.toast("视频已存在");
            } else {

                videoList.setHasVideoCache(0);
                mHelper.update(videoList);
                mView.toast("开始缓存视频");

                downloadFiles(videoUrl, String.valueOf(mView.getId() + ".mp4"), SDPATH + VIDEO_TEMP_DIR);
            }
        }
    }


    //<editor-fold desc="这段代码是用另一个库实现的下载">
    /**
     * 全局初始化
     * 如果你需要注册你的定制组件，你需要在Application#onCreate中
     * 调用FileDownloader.setupOnApplicationOnCreate(application):InitCustomMaker,
     * 否则你只需要在使用FileDownloader之前的任意时候调用FileDownloader.setup(Context)即可。
     */
    //启动单任务下载任务 隐式下载
    //@Override
//    public BaseDownloadTask createDownloadTask(final String downloadUrl, String savePath) {
//        boolean isDir = false;
//        if (savePath == null){
//            savePath = (FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "tmp");
//        }
//
//        return FileDownloader.getImpl().create(downloadUrl)
//                .setPath(savePath, isDir)
//                .setCallbackProgressTimes(300)
//                .setMinIntervalUpdateSpeed(400)
//                //.setTag(tag)
//                .setListener(new FileDownloadListener() {
//                    //等待，已经进入下载队列
//                    @Override
//                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//                        //((ViewHolder) task.getTag()).updatePending(task);
//                    }
//                    //下载进度回调
//                    @Override
//                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                        ((ViewHolder) task.getTag()).updateProgress(soFarBytes, totalBytes, task.getSpeed());
//                    }
//                    //下载出现错误
//                    @Override
//                    protected void error(BaseDownloadTask task, Throwable e) {
////                        ((ViewHolder) task.getTag()).updateError(e, task.getSpeed());
//                    }
//                    //已经连接上
//                    @Override
//                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
////                        ((ViewHolder) task.getTag()).updateConnected(etag, task.getFilename());
//                    }
//                    //暂停下载
//                    @Override
//                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                        ((ViewHolder) task.getTag()).updatePaused(task.getSpeed());
//                    }
//                    //完成整个下载过程
//                    @Override
//                    protected void completed(BaseDownloadTask task) {
////                        ((ViewHolder) task.getTag()).updateCompleted(task);
//                    }
//                    //在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务
//                    @Override
//                    protected void warn(BaseDownloadTask task) {
////                        ((ViewHolder) task.getTag()).updateWarn();
//                    }
//                });
//    }
    //</editor-fold>


}
