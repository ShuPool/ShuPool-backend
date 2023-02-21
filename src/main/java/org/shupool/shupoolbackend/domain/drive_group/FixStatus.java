package org.shupool.shupoolbackend.domain.drive_group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FixStatus {

    DRIVE("START", "출발 시간 고정"),
    BEFORE("ARRIVE", "도착 시간 고정");

    private final String key;
    private final String status;
}
