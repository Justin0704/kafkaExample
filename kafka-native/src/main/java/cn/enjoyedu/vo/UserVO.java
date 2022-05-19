package cn.enjoyedu.vo;

import java.io.Serializable;

/**
 * 一个简单的用户实体类
 */
public class UserVO implements Serializable{
    private int id;
    private String name;

    public UserVO(int id) {
        this.id = id;
    }

    public UserVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
