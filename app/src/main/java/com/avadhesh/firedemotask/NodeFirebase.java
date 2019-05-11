package com.avadhesh.firedemotask;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NodeFirebase {
    private static NodeFirebase ourInstance = new NodeFirebase();

    public static String NODE_USER = "UserDetail";
    public static String NODE_EVENT = "Event";

    public String NODE_ID1 = "";


    public static NodeFirebase getInstance() {
        return ourInstance;
    }

    public  String getEventList(String id) {
        this.NODE_ID1 = NODE_EVENT + "/"+id ;
        return this.NODE_ID1;
    }

    public  String setEventList(String id) {
        this.NODE_ID1 = NODE_EVENT + "/"+id ;
        return this.NODE_ID1;
    }
}
