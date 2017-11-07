package com.corey.im.ui.entity;

/**
 * Created by sycao on 2017/11/6.
 * 文本消息实体类
 */

public class MessageEntity {

    public static final int MSG_TYPE_SEND = 0;

    public static final int MSG_TYPE_REC = 1;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息来源：
     * MSG_TYPE_SEND->自己发的消息
     * MSG_TYPE_REC->接收到的消息
     */
    private int messageType;

    public MessageEntity(String content, int messageType) {
        this.content = content;
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
