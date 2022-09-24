package smartinternz.challenge.customercareregistry;

public class MyComplaintsModel {
private String title;
private String desc;
private boolean isResolved;
    public MyComplaintsModel(String title, String desc,boolean isResolved) {
        this.title = title;
        this.desc = desc;
        this.isResolved=isResolved;

    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
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
}
