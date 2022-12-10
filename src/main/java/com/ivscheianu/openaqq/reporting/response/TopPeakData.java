package com.ivscheianu.openaqq.reporting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopPeakData implements Serializable {
    private String city;
    private String parameter;
    private String unit;
    private String utcTime;
    private Double value;
}
