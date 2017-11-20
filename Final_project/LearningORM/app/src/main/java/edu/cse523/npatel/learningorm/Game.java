package edu.cse523.npatel.learningorm;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by npatel on 11/15/17.
 */

@Table(name = "game")
public class Game extends Model {

    @Column(name = "game_name")
    public String gameName;

    @Column(name = "weight")
    public double weight;

    public Game(){super();}
    public Game(String gameName, double weight) {
        super();
        this.gameName = gameName;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return getId() + ": " + gameName + ", " + weight;
    }

    public static List<Game> getAllGames(){
        Select query = new Select();
        return query.from(Game.class).orderBy("weight ASC").execute();
    }
}
