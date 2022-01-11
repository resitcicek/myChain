package mobile.resitcicek.mychain;

public class User {
    private String username;
    private int ID;
    private String bio;
    private String password;
    private String insta;
    private String twitter;
    private int chainNum = 0;



    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    private String email;
    private String[] followList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getFollowList() {
        return followList;
    }

    public void setFollowList(String[] followList) {
        this.followList = followList;
    }

    public int getChainNum() {
        return chainNum;
    }

    public void setChainNum(int cNum) {
        this.chainNum = cNum;
    }

    public void addChainNum() {
        this.chainNum += 1;
    }
}
