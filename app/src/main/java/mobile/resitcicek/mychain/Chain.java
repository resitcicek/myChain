package mobile.resitcicek.mychain;

public class Chain {
    private int ID;
    private String name;



    private String description;
    private int reminder;
    private int priv;
    private String category;
    private String duration;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
