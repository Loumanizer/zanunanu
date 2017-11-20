package edu.csi5230.sshresth.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Observable;

/**
 * Created by shova on 11/11/2017.
 */

public class TTTButton extends Button implements Observer {
    int index = -1;

    TTTButton(Context c) {
        super(c);
    }

    void setIndex(int _index) {
        this.index = _index;
    }

    public void update(int symbol) {
        setBackgroundResource(symbol);
    }
}
