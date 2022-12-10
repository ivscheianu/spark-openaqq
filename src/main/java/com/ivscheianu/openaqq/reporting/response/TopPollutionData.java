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
public class TopPollutionData implements Serializable {
    private String city;
    private Double latitude;
    private Double longitude;
}
