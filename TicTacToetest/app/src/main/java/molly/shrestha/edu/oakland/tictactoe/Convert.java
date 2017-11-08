package molly.shrestha.edu.oakland.tictactoe;

public class Convert {
    private String XYvaluet;

    Convert() {
    }

    public void getXYvalue(int _value) {
        int remainder = _value % 4;
        this.XYvaluet = "(" + (_value / 4) + ", " + remainder + ")";
    }

    public int singleValue(String _xyValue) {
        int x_loc = Integer.parseInt(String.valueOf(_xyValue.charAt(1)));
        return (x_loc * 4) + Integer.parseInt(String.valueOf(_xyValue.charAt(4)));
    }

    public String toSting() {
        return this.XYvaluet;
    }
}
