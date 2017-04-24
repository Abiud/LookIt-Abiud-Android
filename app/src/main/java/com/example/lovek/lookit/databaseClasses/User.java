package com.example.lovek.lookit.databaseClasses;

/**
 * Created by abiud on 4/22/2017.
 */


//Object passed to the database stored like:
    //DATABASE ROOT
    //      |
    //    users
    //      |
    //      |-User.name
    //            |
    //            |-User.name
    //            |-User.points
public class User {
    public String name;
    public Double points;

    public User(){
    }

   public User(String name, Double points){
       this.name = name;
       this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
