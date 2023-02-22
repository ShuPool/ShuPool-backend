package org.shupool.shupoolbackend.domain.path.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.shupool.shupoolbackend.domain.path.Path;
import org.shupool.shupoolbackend.domain.path.PathType;

@Builder @Getter
public class PathDto {
    private String displayName;
    private String address;

    private PathType pathType;

    public static PathDto entityToDto(Path path) {
        return PathDto.builder()
                .displayName(path.getDisplayName())
                .address(path.getAddress())
                .pathType(path.getPathType())
                .build();
    }

    public static List<PathDto> entitiesToDtos(List<Path> paths) {
        return paths.stream()
            .map(PathDto::entityToDto)
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
