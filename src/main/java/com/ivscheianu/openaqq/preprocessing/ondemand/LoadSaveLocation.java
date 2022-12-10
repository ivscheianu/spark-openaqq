package com.ivscheianu.openaqq.preprocessing.ondemand;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class LoadSaveLocation {
    private final String[] loadLocations;
    private final String saveLocation;
}
