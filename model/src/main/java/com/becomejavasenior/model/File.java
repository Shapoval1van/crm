package com.becomejavasenior.model;

import java.io.Serializable;
import java.util.Date;

public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String path;
    private Contact contact;
    private byte[] data;
    private User createdByUser;
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
