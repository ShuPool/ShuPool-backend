package org.shupool.shupoolbackend.domain.path;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shupool.shupoolbackend.domain.path.dto.PathDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Path {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long id;

    private String displayName;
    private String address;

    private PathType pathType;

    public static Path dtoToEntity(PathDto pathDto) {
        return Path.builder()
                .displayName(pathDto.getDisplayName())
                .address(pathDto.getAddress())
                .pathType(pathDto.getPathType())
                .build();
    }

    public static List<Path> initPaths(List<PathDto> pathDtos) {
        return pathDtos.stream().map(Path::dtoToEntity).collect(Collectors.toList());
    }
}
