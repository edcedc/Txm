package com.fanwang.txm.event;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CameraInEvent {

    public static final int WORK_CAMEAR = 0;//择相册
    public static final int WORK_PHOTO = 1;//打开相机
    public static final int SET_IMAGE_CAMEAR = 2;//择相册
    public static final int SET_IMAGE_PHOTO = 3;//打开相机
    public static final int SET_LOGO_CAMEAR = 4;//择相册
    public static final int SET_LOGO_PHOTO = 5;//打开相机

    private int request;

    private Object object;

    public int getRequest() {
        return request;
    }

    public Object getObject() {
        return object;
    }

    public CameraInEvent(int request, Object object) {
        this.request = request;
        this.object = object;
    }
}
