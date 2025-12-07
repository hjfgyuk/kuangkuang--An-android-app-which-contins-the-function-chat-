package com.example.kuangkuang.entity;

public class Group {
    //群聊唯一识别方式
    private int id;
    private String name;
    private String avatar;
    //未读的信息数
    private int messageUnread;
    //最近的时间戳
    private String lastTime;
    private long dragon;

    public long getDragon() {
        return dragon;
    }

    public void setDragon(long dragon) {
        this.dragon = dragon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    private String maxNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMessageUnread() {
        return messageUnread;
    }

    public void setMessageUnread(int messageUnread) {
        this.messageUnread = messageUnread;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
