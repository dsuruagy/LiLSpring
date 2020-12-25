package com.frankmoley.security.app.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH_USER_GROUP")
public class AuthGroup {
    @Id
    @Column(name = "AUTH_USER_GROUP_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "AUTH_GROUP")
    private String groupname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
