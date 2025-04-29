public class UserData {
    private Integer id;
    private boolean isAdmin;
    private String user;
    private String password;


    public UserData(Integer id, boolean isAdmin, String user, String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.user = user;
        this.password = password;
    }

    //Getters
    public int getId() {
        return id;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }


    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
