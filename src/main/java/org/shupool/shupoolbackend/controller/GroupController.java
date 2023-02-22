package org.shupool.shupoolbackend.controller;

import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.domain.drive_group.dto.DriveGroupDto;
import org.shupool.shupoolbackend.service.drive_group.DriveGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@RestController
public class GroupController {

    private final DriveGroupService driveGroupService;

    @PostMapping(value = "/group")
    public DriveGroupDto createGroup(DriveGroupDto driveGroupDto) {
        return driveGroupService.createDriveGroup(driveGroupDto);
    }
}
