package org.shupool.shupoolbackend.domain.drive_group.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.shupool.shupoolbackend.domain.drive_group.FixStatus;
import org.shupool.shupoolbackend.domain.drive_group.GroupStatus;
import org.shupool.shupoolbackend.domain.path.dto.PathDto;

import java.util.List;

@Getter
@Builder @AllArgsConstructor
public class DriveGroupDto {

    private GroupStatus groupStatus;

    private FixStatus fixStatus;

    private List<PathDto> paths;

}
