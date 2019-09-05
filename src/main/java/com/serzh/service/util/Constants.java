package com.serzh.service.util;

import lombok.experimental.UtilityClass;

import java.time.ZoneId;

@UtilityClass
public final class Constants {

    public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final ZoneId ZONE_ID_KIEV = ZoneId.of("Europe/Kiev");

    public static final String ITEM_WAS_NOT_FOUND_BY_ID = "Item was not found by id = %d";

}
