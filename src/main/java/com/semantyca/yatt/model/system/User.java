package com.semantyca.yatt.model.system;

import com.semantyca.yatt.model.AppEntity;
import com.semantyca.yatt.model.IUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends AppEntity<Integer> implements IUser {
    private String login;
    private String email;
    private boolean authorized;
    private List<Role> roles = new ArrayList();

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
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
    public void setEditable(boolean b) {

    }

    @Override
    public String getName() {
        return login;
    }

    public Date getLastPasswordResetDate() {
        return new Date();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}