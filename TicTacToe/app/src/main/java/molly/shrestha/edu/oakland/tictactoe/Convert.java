package molly.shrestha.edu.oakland.tictactoe;

public class Convert {
    private String XYvaluet;

    Convert() {
    }

    public void getXYvalue(int _value) {
        int remainder = _value % 3;
        this.XYvaluet = "(" + (_value / 3) + ", " + remainder + ")";
    }

    public int singleValue(String _xyValue) {
        int x_loc = Integer.parseInt(String.valueOf(_xyValue.charAt(1)));
        int y_loc = Integer.parseInt(String.valueOf(_xyValue.charAt(4)));
        return (x_loc * 3) + y_loc;
    }

    public String toSting() {
        return this.XYvaluet;
    }
}
