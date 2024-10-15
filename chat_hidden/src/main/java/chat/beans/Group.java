package chat.beans;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Group implements Serializable {
    private String name;
    private List<User> members;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public List<User> getMembers() {
        return new ArrayList<>(members);
    }

    public boolean hasMember(User user) {
        return members.contains(user);
    }
}