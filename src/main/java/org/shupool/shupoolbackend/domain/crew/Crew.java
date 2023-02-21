package org.shupool.shupoolbackend.domain.crew;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.shupool.shupoolbackend.domain.drive_group.DriveGroup;
import org.shupool.shupoolbackend.domain.path.Path;
import org.shupool.shupoolbackend.domain.member.Member;

@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_group_id")
    private DriveGroup driveGroup;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    private boolean isAllowed;

    public boolean checkAllow() {
        return isAllowed;
    }
}
