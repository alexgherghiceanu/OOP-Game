package gameboard;

public class ImpossibleMove extends RuntimeException {
    public ImpossibleMove(String message) {
        super(message);
    }
}
