package org.shupool.shupoolbackend.domain.drive_group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shupool.shupoolbackend.domain.drive_group.dto.DriveGroupDto;
import org.shupool.shupoolbackend.domain.member.Member;
import org.shupool.shupoolbackend.domain.path.Path;

import javax.persistence.*;
import java.util.List;

@Builder @AllArgsConstructor
@Entity @NoArgsConstructor @Getter
public class DriveGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @Enumerated(EnumType.STRING)
    private FixStatus fixStatus;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Member driver;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "path_id")
    private List<Path> paths;

    public static DriveGroup initDriveGroup(DriveGroupDto driveGroupDto, Member driver) {
        List<Path> paths = Path.initPaths(driveGroupDto.getPaths());
        return DriveGroup.builder()
                .groupStatus(driveGroupDto.getGroupStatus())
                .fixStatus(driveGroupDto.getFixStatus())
                .paths(paths)
                .driver(driver)
                .build();
    }
}
