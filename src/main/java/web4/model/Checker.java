package web4.model;

public class Checker {
    public boolean check(Attempt attempt) {
        Point point = attempt.getPoint();
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return r >= 0 ? checkBoxWithPositiveR(x, y, r) : checkBoxWithNegativeR(x, y, r);
    }

    public boolean checkBoxWithPositiveR(double x, double y, double r) {
        if (x >= 0 && y >= 0) {
            return x*x + y*y <= r*r;
        }
        if (x >= 0 && y < 0) {
            return y >= 2 * x - r;
        }
        if (x < 0 && y <= 0) {
            return x >= -r && y >= -r;
        }
        return false;
    }

    public boolean checkBoxWithNegativeR(double x, double y, double r) {
        if (x <= 0 && y <= 0) {
            return x*x + y*y <= r*r;
        }
        if (x <= 0 && y > 0) {
            return y <= 2 * x - r;
        }
        if (x > 0 && y >= 0) {
            return x <= -r && y <= -r;
        }
        return false;
    }
}
