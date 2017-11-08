package molly.shrestha.edu.oakland.tictactoe;

public class TimeTracker {
    long startTime = 0;
    long[] totalTime = new long[]{0, 0};

    TimeTracker() {
    }

    void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    void setStopTime(int _id) {
        this.totalTime[_id] = (this.totalTime[_id] + System.currentTimeMillis()) - this.startTime;
    }

    long getTotalTime(int _id) {
        return this.totalTime[_id];
    }
}
