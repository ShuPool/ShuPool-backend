package org.shupool.shupoolbackend.domain.drive_group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.shupool.shupoolbackend.domain.drive_group.dto.DriveGroupDto;
import org.shupool.shupoolbackend.domain.member.Member;
import org.shupool.shupoolbackend.domain.path.Path;

import javax.persistence.*;
import java.util.List;

@Builder @AllArgsConstructor
@Entity @NoArgsConstructor
public class DriveGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_group_id")
    private Long id;

    private GroupStatus groupStatus;
    private FixStatus fixStatus;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Member driver;

    @OneToMany
    @Column(name = "path_id")
    private List<Path> paths;

    public static DriveGroup initDriveGroup(DriveGroupDto driveGroupDto) {
        List<Path> paths = Path.initPaths(driveGroupDto.getPaths());
        return DriveGroup.builder()
                .groupStatus(driveGroupDto.getGroupStatus())
                .fixStatus(driveGroupDto.getFixStatus())
                .paths(paths)
                .build();
    }
}
