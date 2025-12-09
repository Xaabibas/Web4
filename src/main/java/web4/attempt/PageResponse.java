package web4.attempt;

import java.util.List;

public record PageResponse(List<?> content, long totalElements) {
}
