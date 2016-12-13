package client.yalantis.com.foldingtabbarandroid;

import android.support.annotation.DrawableRes;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ChatModel {
    private String userName;
    private String lastMessage;
    private int chatImageRes;
    private String time;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getChatImageRes() {
        return chatImageRes;
    }

    public void setChatImageRes(int chatImageRes) {
        this.chatImageRes = chatImageRes;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ChatModel(String userName, String lastMessage, @DrawableRes int chatImageRes, String time) {
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.chatImageRes = chatImageRes;
        this.time = time;
    }
}
