package org.shupool.shupoolbackend.domain.drive_group.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.shupool.shupoolbackend.domain.drive_group.DriveGroup;
import org.shupool.shupoolbackend.domain.drive_group.FixStatus;
import org.shupool.shupoolbackend.domain.drive_group.GroupStatus;
import org.shupool.shupoolbackend.domain.member.dto.MemberDto;
import org.shupool.shupoolbackend.domain.path.dto.PathDto;

import java.util.List;

@Getter
@Builder @AllArgsConstructor
public class DriveGroupDto {

    private GroupStatus groupStatus;

    private FixStatus fixStatus;

    private List<PathDto> paths;

    private MemberDto memberDto;

    public static DriveGroupDto entityToDto(DriveGroup driveGroup) {
        return DriveGroupDto.builder()
                .groupStatus(driveGroup.getGroupStatus())
                .fixStatus(driveGroup.getFixStatus())
                .paths(PathDto.entitiesToDtos(driveGroup.getPaths()))
                .memberDto(MemberDto.entityToDto(driveGroup.getDriver().getNickname()))
                .build();
    }
}
