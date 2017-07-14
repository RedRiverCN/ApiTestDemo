package com.red.apitestdemo.bean.dbentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Red on 2017/7/10.
 */

@Entity
public class VideoList {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    @Property(nameInDb = "VIDEOID")
    private int memberId;//视频ID
    private String memberTitle;//标题
    private String memberTime;//视频时长
    private String memberThumb;//缩略图地址
    private String memberCategory;//所属类别
    private String memberStage;//阶段
    private String memberVideo;//视频地址
    private int hasVideoCache;//视频缓存状态
    private int hasThumbCache;//图片缓存状态
    private String memberDetailData;//详情数据
    @Generated(hash = 133385387)
    public VideoList(Long id, int memberId, String memberTitle, String memberTime,
            String memberThumb, String memberCategory, String memberStage,
            String memberVideo, int hasVideoCache, int hasThumbCache,
            String memberDetailData) {
        this.id = id;
        this.memberId = memberId;
        this.memberTitle = memberTitle;
        this.memberTime = memberTime;
        this.memberThumb = memberThumb;
        this.memberCategory = memberCategory;
        this.memberStage = memberStage;
        this.memberVideo = memberVideo;
        this.hasVideoCache = hasVideoCache;
        this.hasThumbCache = hasThumbCache;
        this.memberDetailData = memberDetailData;
    }
    @Generated(hash = 39621318)
    public VideoList() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMemberId() {
        return this.memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public String getMemberTitle() {
        return this.memberTitle;
    }
    public void setMemberTitle(String memberTitle) {
        this.memberTitle = memberTitle;
    }
    public String getMemberTime() {
        return this.memberTime;
    }
    public void setMemberTime(String memberTime) {
        this.memberTime = memberTime;
    }
    public String getMemberThumb() {
        return this.memberThumb;
    }
    public void setMemberThumb(String memberThumb) {
        this.memberThumb = memberThumb;
    }
    public String getMemberCategory() {
        return this.memberCategory;
    }
    public void setMemberCategory(String memberCategory) {
        this.memberCategory = memberCategory;
    }
    public String getMemberVideo() {
        return this.memberVideo;
    }
    public void setMemberVideo(String memberVideo) {
        this.memberVideo = memberVideo;
    }
    public int getHasVideoCache() {
        return this.hasVideoCache;
    }
    public void setHasVideoCache(int hasVideoCache) {
        this.hasVideoCache = hasVideoCache;
    }
    public int getHasThumbCache() {
        return this.hasThumbCache;
    }
    public void setHasThumbCache(int hasThumbCache) {
        this.hasThumbCache = hasThumbCache;
    }
    public String getMemberDetailData() {
        return this.memberDetailData;
    }
    public void setMemberDetailData(String memberDetailData) {
        this.memberDetailData = memberDetailData;
    }
    public String getMemberStage() {
        return this.memberStage;
    }
    public void setMemberStage(String memberStage) {
        this.memberStage = memberStage;
    }


}
