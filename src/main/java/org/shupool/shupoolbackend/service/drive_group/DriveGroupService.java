package org.shupool.shupoolbackend.service.drive_group;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.shupool.shupoolbackend.config.security.jwt.JwtProvider;
import org.shupool.shupoolbackend.domain.drive_group.DriveGroup;
import org.shupool.shupoolbackend.domain.drive_group.DriveGroupRepository;
import org.shupool.shupoolbackend.domain.drive_group.dto.DriveGroupDto;
import org.shupool.shupoolbackend.domain.member.Member;
import org.shupool.shupoolbackend.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DriveGroupService {

    private final DriveGroupRepository driveGroupRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public DriveGroupDto createDriveGroup(DriveGroupDto driveGroupDto, HttpServletRequest request) {

        Member driver = memberRepository.findBySocialId(jwtProvider.socialIdFromRequest(request)).get();
        DriveGroup driveGroup = DriveGroup.initDriveGroup(driveGroupDto, driver);
        DriveGroupDto saveDriveDto = DriveGroupDto.entityToDto(driveGroupRepository.save(driveGroup));

        return saveDriveDto;
    }
}
