package org.shupool.shupoolbackend.domain.path;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PathType {
    START("START", "출발지"),
    ARRIVE("ARRIVE", "도착지"),
    WAYPOINT("WAYPOINT", "경유지");

    private final String key;
    private final String status;

}
