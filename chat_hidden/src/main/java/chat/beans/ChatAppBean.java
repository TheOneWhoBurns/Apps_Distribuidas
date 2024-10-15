package chat.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.*;

@Named
@ApplicationScoped
public class ChatAppBean implements Serializable {

    @Inject
    @Push(channel = "push")
    private PushContext push;

    @Inject
    Login login;

    private String message;
    private Map<String, List<String>> groupMessages = new HashMap<>();
    private Map<String, Group> groups = new HashMap<>();
    private String currentGroup = "todos";
    private String newGroupName;

    public ChatAppBean() {
        // Initialize "todos" group
        groups.put("todos", new Group("todos"));
        groupMessages.put("todos", new ArrayList<>());
    }

    public void updateTable() {
        if (message != null && !message.trim().isEmpty()) {
            String formattedMessage = login.getName() + ": " + message;
            groupMessages.get(currentGroup).add(formattedMessage);
            this.message = "";
            push.send("updateMessages");
        }
    }

    public List<String> getMessages() {
        return groupMessages.getOrDefault(currentGroup, new ArrayList<>());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public String getName() {
        return login.getName();
    }

    public String getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String group) {
        if (group != null && !group.trim().isEmpty()) {
            this.currentGroup = group;
            if (!groupMessages.containsKey(group)) {
                groupMessages.put(group, new ArrayList<>());
            }
            push.send("updateMessages");
        }
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public void createGroup() {
        if (newGroupName != null && !newGroupName.trim().isEmpty() && !groups.containsKey(newGroupName)) {
            groups.put(newGroupName, new Group(newGroupName));
            groupMessages.put(newGroupName, new ArrayList<>());
            setCurrentGroup(newGroupName);  // Automatically switch to the new group
            newGroupName = "";  // Clear the input field
        }
    }

    public List<String> getGroupNames() {
        return new ArrayList<>(groups.keySet());
    }

    public String endSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "loggedOut.xhtml";
    }
}