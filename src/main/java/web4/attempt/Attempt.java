package web4.attempt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table(name="attempts")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Attempt implements Serializable {
    @Id
    private Long id;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private Point point = new Point(0,0,0);
    private boolean result;
    private String start;
    @Column(value="worktime")
    private long workTime;
    private String username;
}
