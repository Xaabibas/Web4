package web4.attempt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Point implements Serializable {
    private double x;
    private double y;
    private double r;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
