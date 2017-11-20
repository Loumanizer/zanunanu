package edu.cse523.npatel.learningorm;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by molly on 11/19/17.
 */

@Table(name = "run")
public class Run extends Model {

    @Column(name = "player_id")
    public int playerId;

    @Column(name = "game_id")
    public int gameId;

    @Column(name = "run")
    public String run;


    public Run(){super();}
    public Run(int playerId, int gameId, String run ) {
        super();
        this.playerId = playerId;
        this.gameId = gameId;
        this.run = run;
    }

    @Override
    public String toString(){
        //return getId() + ":" + playerId + "," + gameId + "," + run;
        return getId() + ":" + playerId + "," + gameId + "," + run;
    }

    public static List<Run> getAllRuns(){
        Select query = new Select();
        return query.from(Run.class).orderBy("player_id").execute();
    }
}
