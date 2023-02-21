package org.shupool.shupoolbackend.domain.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupStatus {

    DRIVE("DRIVE", "운전 중"),
    BEFORE("BEFORE", "출발 전");

    private final String key;
    private final String status;
}
