package molly.shrestha.edu.oakland.tictactoe;

public interface Observable {
    void notifyListeners();

    void registerObserver(Observer observer);
}
