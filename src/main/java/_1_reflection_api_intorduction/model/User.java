package _1_reflection_api_intorduction.model;

import java.io.Serializable;

public class User extends Person implements Serializable, Comparable<User> {

    String name;

    @Override
    public int compareTo(User o) {
        return 0;
    }

    public User(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
