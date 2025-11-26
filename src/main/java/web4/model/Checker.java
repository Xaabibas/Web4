package web4.model;

import org.springframework.stereotype.Service;

@Service
public class Checker {
    public boolean check(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return r >= 0 ? checkBoxWithPositiveR(x, y, r) : checkBoxWithNegativeR(x, y, r);
    }

    public boolean checkBoxWithPositiveR(double x, double y, double r) {
        if (x >= 0 && y >= 0) {
            return x*x + y*y <= r*r / 4;
        }
        if (x >= 0 && y < 0) {
            return x <= r/2 && y >= -r;
        }
        if (x < 0 && y >= 0) {
            return y <= x + r;
        }
        return false;
    }

    public boolean checkBoxWithNegativeR(double x, double y, double r) {
        if (x <= 0 && y <= 0) {
            return x*x + y*y <= r*r / 4;
        }
        if (x <= 0 && y > 0) {
            return x >= -r / 2 && y <= -r;
        }
        if (x > 0 && y <= 0) {
            return y >= x + r;
        }
        return false;
    }
}
