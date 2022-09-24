package smartinternz.challenge.customercareregistry;

import android.widget.Switch;

public class PendingModel {
    private String title;
    private String desc;
    private String username;
    private String emailId;
    private Switch isResolved;


    public PendingModel(String title, String desc, String username, String emailId) {
        this.title = title;
        this.desc = desc;
        this.username = username;
        this.emailId = emailId;
        this.isResolved=isResolved;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
