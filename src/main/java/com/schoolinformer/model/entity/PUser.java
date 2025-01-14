package com.schoolinformer.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "puser")
public class PUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany
    @JoinColumn(name = "userId")
    private List<Address> addresses;

    public PUser() {
    }

    public PUser(final String username, final List<Address> addresses) {
        this.username = username;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<Address> addresses) {
        this.addresses = addresses;
    }
}
