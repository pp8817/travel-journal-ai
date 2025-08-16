package com.travel.domain.route.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RouteRequest {
    private String group_id;
    private String place_name;
    private String place_category;
    private Integer place_open;
    private Integer place_close;
    private List<Integer> place_break;
    private Boolean has_parking;
    private Double latitude;
    private Double longitude;
    private List<String> image_base64;
}
