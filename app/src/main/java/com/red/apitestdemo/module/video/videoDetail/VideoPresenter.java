package com.red.apitestdemo.module.video.videoDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.red.apitestdemo.network.auxiliary.ApiConstants;
import com.red.downlib.RxDownload;

import java.io.File;
import java.text.SimpleDateFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.red.apitestdemo.utils.ConstantUtil.SDPATH;
import static com.red.apitestdemo.utils.ConstantUtil.TEMP;


/**
 * Created by Red on 2017/6/17.
 */

public class VideoPresenter extends VideoContract.Presenter {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void playVideo() {





        //访问服务器获取数据
        mView.onLoading();

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

    @Override
    public void getVideoImg(Context context, String imgUrl) {
        mModel.getVideoImg(context, imgUrl)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .doOnNext(bitmap -> mView.onImgLoad(bitmap))
                .doOnError(throwable -> mView.onError("缩略图获取失败"))
                .subscribe();
        /*.subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mView.onImgLoad(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError("缩略图获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
//                    tvDownloadStatus.setText(tvDownloadStatus.getText() + "\n开始下载：" + sdf.format(new Date()));
                })
                .doOnNext(downloadStatus -> {
                    //此处更新下载进度
                    String formatStatusString = downloadStatus.getFormatStatusString();
//                    tvDownloadPrg.setText("下载进度：" + formatStatusString);
                })
                .doOnError(throwable -> {
                    //此处处理下载异常
//                    tvDownloadStatus.setText("下载失败:" + throwable.getMessage());
                })
                .doOnComplete(() -> {
                    //下载完成，解压或安装
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
//                    tvDownloadPrg.setText(tvDownloadPrg.getText() + "\n下载完成：" + sdf.format(new Date()));
                    File file = new File(savePath, saveName);
                    file.delete();
                })
                .subscribe();
    }

    //阵亡
    @Override
    public void viewOnDestroy() {
        compositeDisposable.dispose();
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
