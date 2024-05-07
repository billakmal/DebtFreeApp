package org.bilal.debtfree.Utility;

public class Identity {
    private static Identity instance;
    private int user_id;

    private Identity() {}

    public static Identity getInstance() {
        if (instance == null) {
            instance = new Identity();
        }
        return instance;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
}
