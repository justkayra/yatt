package com.semantyca.juka.model.user;


public class AnonymousUser extends SystemUser {
    public final static String USER_NAME = "anonymous";
    public final static int ID = 0;

    @Override
    public Integer getId() {
        return ID;
    }

    @Override
    public String getLogin() {
        return USER_NAME;
    }


    @Override
    public String getName() {
        return USER_NAME;
    }


}
