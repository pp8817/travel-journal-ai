package com.travel.domain.route.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class RouteResponse {
    private String status;
    private String message;
    private Integer insert_count;
    private List<String> primary_keys;
    private String group_id;
    private Integer image_count;
    private List<String> image_paths;
}
