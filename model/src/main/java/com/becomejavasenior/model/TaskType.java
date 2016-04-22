package com.becomejavasenior.model;


import java.io.Serializable;

public class TaskType implements Serializable {
    private int id;
    private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TaskType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }

}
