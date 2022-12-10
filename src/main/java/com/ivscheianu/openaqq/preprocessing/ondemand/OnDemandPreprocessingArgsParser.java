package com.ivscheianu.openaqq.preprocessing.ondemand;

import static com.ivscheianu.openaqq.common.Constants.COMMA;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.LF;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class OnDemandPreprocessingArgsParser {

    private static final String ARROW = "->";

    Set<LoadSaveLocation> parseArgs(final String[] args) {
        return Arrays
            .stream(args)
            .map(string -> string.replace(SPACE, EMPTY).replace(LF, EMPTY))
            .map(this::parseLoadSaveLocation)
            .collect(Collectors.toSet());
    }

    private LoadSaveLocation parseLoadSaveLocation(final String argument) {
        final String[] locations = argument.split(ARROW);
        final String[] loadLocations = locations[0].split(COMMA);
        final String saveLocation = locations[1];
        return LoadSaveLocation
            .builder()
            .loadLocations(loadLocations)
            .saveLocation(saveLocation)
            .build();
    }
}
