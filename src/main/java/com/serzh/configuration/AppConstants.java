package com.serzh.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {

    // Spring profiles for development, test and production
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
}
