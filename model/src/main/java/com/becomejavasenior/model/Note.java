package com.becomejavasenior.model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String text;
    private User createdByUser;
    private Date creationDate;
    private Company company;
    private Deal deal;

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    private Contact contact;


    public Company getCompany() {
        return company;
    }

    public Deal getDeal() {
        return deal;
    }

    public Contact getContact() {
        return contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
