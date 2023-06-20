package com.ivscheianu.openaqq.preprocessing.daily;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CountryRecords implements Serializable {
    private String country;
    private long records;
}
