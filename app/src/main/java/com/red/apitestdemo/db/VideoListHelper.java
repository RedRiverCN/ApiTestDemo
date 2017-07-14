package com.red.apitestdemo.db;

import com.red.apitestdemo.bean.dbentity.VideoList;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 跟实体类对应
 */
public class VideoListHelper extends BaseDbHelper<VideoList, Long> {
    private static VideoListHelper sVideoListHelper;

    public static VideoListHelper getInstance() {
        if (sVideoListHelper == null) {
            sVideoListHelper = new VideoListHelper(GreenDaoHelper.getDaoSession().getVideoListDao());
        }
        return sVideoListHelper;
    }

    private VideoListHelper(AbstractDao dao) {
        super(dao);
    }

    @Override
    public long save(VideoList item) {
        return super.save(item);
    }

    @Override
    public void save(VideoList... items) {
        super.save(items);
    }

    @Override
    public void save(List<VideoList> items) {
        super.save(items);
    }

    @Override
    public void saveOrUpdate(VideoList item) {
        super.saveOrUpdate(item);
    }

    @Override
    public void saveOrUpdate(VideoList... items) {
        super.saveOrUpdate(items);
    }

    @Override
    public void saveOrUpdate(List<VideoList> items) {
        super.saveOrUpdate(items);
    }

    @Override
    public void deleteByKey(Long key) {
        super.deleteByKey(key);
    }

    @Override
    public void delete(VideoList item) {
        super.delete(item);
    }

    @Override
    public void delete(VideoList... items) {
        super.delete(items);
    }

    @Override
    public void delete(List<VideoList> items) {
        super.delete(items);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public void update(VideoList item) {
        super.update(item);
    }

    @Override
    public void update(VideoList... items) {
        super.update(items);
    }

    @Override
    public void update(List<VideoList> items) {
        super.update(items);
    }

    @Override
    public VideoList query(Long key) {
        return super.query(key);
    }

    @Override
    public List<VideoList> queryAll() {
        return super.queryAll();
    }

    @Override
    public List<VideoList> query(String where, String... params) {
        return super.query(where, params);
    }

    @Override
    public QueryBuilder<VideoList> queryBuilder() {
        return super.queryBuilder();
    }

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public void refresh(VideoList item) {
        super.refresh(item);
    }

    @Override
    public void detach(VideoList item) {
        super.detach(item);
    }
}
