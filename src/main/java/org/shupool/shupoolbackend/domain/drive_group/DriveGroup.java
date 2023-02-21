package org.shupool.shupoolbackend.domain.drive_group;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.shupool.shupoolbackend.domain.path.Path;

@Entity
public class DriveGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_group_id")
    private Long id;

    private GroupStatus groupStatus;
    private FixStatus fixStatus;

    @OneToMany
    @Column(name = "path_id")
    private List<Path> paths;
}
