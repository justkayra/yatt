package com.semantyca.yatt.model;


public class AnonymousUser extends SystemUser {
    public final static String USER_NAME = "anonymous";
    public final static long ID = 0;

    @Override
    public String getLogin() {
        return USER_NAME;
    }

    @Override
    public String getName() {
        return USER_NAME;
    }


}
