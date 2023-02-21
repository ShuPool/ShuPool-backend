package org.shupool.shupoolbackend.domain.path.dto;

import lombok.Builder;
import lombok.Getter;
import org.shupool.shupoolbackend.domain.path.Path;
import org.shupool.shupoolbackend.domain.path.PathType;

@Builder @Getter
public class PathDto {
    private String displayName;
    private String address;

    private PathType pathType;

    private Long longitude;
    private Long latitude;

    public static PathDto entityToDto(Path path) {
        return PathDto.builder()
                .displayName(path.getDisplayName())
                .address(path.getAddress())
                .pathType(path.getPathType())
                .longitude(path.getLongitude())
                .latitude(path.getLatitude())
                .build();
    }
}
