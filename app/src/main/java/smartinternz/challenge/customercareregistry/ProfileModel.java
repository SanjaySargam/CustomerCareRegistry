package smartinternz.challenge.customercareregistry;

public class ProfileModel {
    private String name;
    private String email;
    private String mobNo;
    private int complaintsRaised;
    private int complaintsResolved;
    private int totalComp;


    public ProfileModel(String name, String email, String mobNo, int complaintsRaised, int complaintsResolved,int totalComp) {
        this.name = name;
        this.email = email;
        this.mobNo = mobNo;
        this.complaintsRaised = complaintsRaised;
        this.complaintsResolved = complaintsResolved;
        this.totalComp=totalComp;
    }

    public int getTotalComp() {
        return FireBaseData.complaints.size();
    }

    public void setTotalComp(int totalComp) {
        this.totalComp = totalComp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public int getComplaintsRaised() {
        return complaintsRaised;
    }

    public void setComplaintsRaised(int complaintsRaised) {
        this.complaintsRaised = complaintsRaised;
    }

    public int getComplaintsResolved() {
        return complaintsResolved;
    }

    public void setComplaintsResolved(int complaintsResolved) {
        this.complaintsResolved = complaintsResolved;
    }
}
