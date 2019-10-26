package com.example.androidlabs;

public class Messenger {

    private String content;
    private Boolean isSend;
    private Long messageID;

    public Messenger(String content, boolean isSend, long messageID) {

        this.content = content;
        this.isSend = isSend;
        this.messageID = messageID;
    }

    public Messenger() {
    }

    public String getContent() {
        return content;

    }

    public void setContent(String content) {
        this.content = content;

    }

    public Boolean isSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }
}


