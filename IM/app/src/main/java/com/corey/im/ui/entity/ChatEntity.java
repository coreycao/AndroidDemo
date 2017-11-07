package com.corey.im.ui.entity;

/**
 * Created by sycao on 2017/11/6.
 * 会话实体类
 */

public class ChatEntity {

    private String portrait;
    private String friendName;
    private String latestMsg;
    private String updateTime;

    public ChatEntity(String portrait, String friendName, String latestMsg, String updateTime) {
        this.portrait = portrait;
        this.friendName = friendName;
        this.latestMsg = latestMsg;
        this.updateTime = updateTime;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLatestMsg() {
        return latestMsg;
    }

    public void setLatestMsg(String latestMsg) {
        this.latestMsg = latestMsg;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
