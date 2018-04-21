package sample;

public class PointEvent {
    double pointX, pointY;
    private boolean onGraph;
    private int counter;

    public PointEvent(double pointX, double pointY, boolean onGraph, int counter) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.onGraph = onGraph;
        this.counter = counter;
    }

    public double getPointX() {
        return pointX;
    }

    public double getPointY() {
        return pointY;
    }

    public boolean isOnGraph() {
        return onGraph;
    }

    public int getCounter() {
        return counter;
    }
}