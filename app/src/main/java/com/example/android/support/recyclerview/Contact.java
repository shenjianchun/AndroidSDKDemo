package com.example.android.support.recyclerview;

import java.util.ArrayList;

/**
 * Created by 14110105 on 2016-07-11.
 */
public class Contact {

    private String mName;

    private boolean mOnline;

    public Contact(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {

        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<Contact> creatContactsList(int numContacts) {

        ArrayList<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < numContacts; i++) {
            contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2));
        }
        return  contacts;
    }

}
