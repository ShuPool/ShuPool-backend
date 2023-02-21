package org.shupool.shupoolbackend.service.drive_group;

import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.domain.drive_group.DriveGroupRepository;
import org.shupool.shupoolbackend.domain.drive_group.dto.DriveGroupDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DriveGroupService {

    private DriveGroupRepository driveGroupRepository;

    public DriveGroupDto createDriveGroup() {
        return null;
    }
}
