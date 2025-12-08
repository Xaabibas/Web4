package web4;

import java.util.List;

public record PageResponse(List<?> content, long totalElements) {
}
