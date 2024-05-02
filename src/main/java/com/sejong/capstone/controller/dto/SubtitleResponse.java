package com.sejong.capstone.controller.dto;

import lombok.Data;

@Data
public class SubtitleResponse {
    private Long subtitleId;
    private float start;
    private float end;
    private String korText;
    private String engText;
}
