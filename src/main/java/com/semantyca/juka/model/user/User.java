package com.semantyca.juka.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semantyca.yatt.model.DataEntity;
import com.semantyca.juka.model.IUser;
import org.jdbi.v3.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends DataEntity<Integer> implements IUser {
    private String login;
    private String pwd;
    private String email;
    private boolean authorized;
    private List<String> roles = new ArrayList();

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    @JsonIgnore
    public String getPwd() {
        return pwd;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @Override
    public String getName() {
        return login;
    }

    public Date getLastPasswordResetDate() {
        return new Date();
    }

    public List<String> getRoles() {
        return roles;
    }

    @Json
    @JsonIgnore
    public List<String> getRolesAsJSON() {
        return roles;
    }


    public void setRoles(List<String> roles) {
        this.roles = roles;
    }



}
