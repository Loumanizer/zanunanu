package edu.cse523.npatel.learningorm;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by npatel on 11/15/17.
 */

@Table(name = "player")
public class Player extends Model {

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    public Player(){super();}
    public Player(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return getId() + ": " + firstName + ", " + lastName;
    }

    public static List<Player> getAllPlayers(){
        Select query = new Select();
        return query.from(Player.class).orderBy("first_name DESC").execute();
    }

    public String getPlayerbyId(int id){

        return  getId() + ": " + firstName + ", " + lastName;
    }
}
