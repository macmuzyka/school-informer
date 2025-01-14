package com.schoolinformer.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String street;
    private String zipCode;
    private String city;

    public Address() { }

    public Address(final Long userId, final String street, final String zipCode, final String city) {
        this.userId = userId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address(final String street, final String zipCode, final String city) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
}
