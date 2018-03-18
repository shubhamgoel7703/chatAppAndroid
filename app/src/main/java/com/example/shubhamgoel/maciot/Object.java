package com.example.shubhamgoel.maciot;

/**
 * Created by shubhamgoel on 09/03/18.
 */

public class Object {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String username,message;

    public Object(String username,String message)
    {
        this.username=username;
        this.message=message;
    }


}


