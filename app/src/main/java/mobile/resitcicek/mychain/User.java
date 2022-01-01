package mobile.resitcicek.mychain;

public class User {
    private String username;
    private int ID;
    private String password;
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
}
