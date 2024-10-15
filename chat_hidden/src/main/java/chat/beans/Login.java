package chat.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("user") // or @ManagedBean(name="user")
@SessionScoped
public class Login implements Serializable {
    private long id;
    boolean alreadyAddedThisUse = false;
    @Inject
    ChatAppBean loggedIn;
    private String name;
    private String password;
    private User user;

    @PostConstruct
    public void init() {
        // This method is called when the bean is first created (when the session starts)
    /*   setUser();
        loggedIn.addUser(this);*/

    }

    public void setUser(){
        user = new User();
        User newUser = new User();
        newUser.setUserName(name + ":   ");
    }

    public User getUser(){
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newValue) {
        password = newValue;
    }

    public String includeUser() {
        return "chat.xhtml";
    }


}
