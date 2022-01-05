package com.karatascompany.pys3318.firebase_poco;

/**
 * Created by azizmahmud on 2.4.2018.
 */

public class Users  extends UserId{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
public Users(){

}
    public Users(String name) {
        this.name = name;
    }
}
