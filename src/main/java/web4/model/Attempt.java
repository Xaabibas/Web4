package web4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Attempt implements Serializable {
    private Point point = new Point();
    private boolean result;
    private String start;
    private long workTime;
}
