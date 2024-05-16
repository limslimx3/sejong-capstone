package com.sejong.capstone.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 사용자 업로드한 비디오 Form 데이터를 담기 위한 DTO
 */
@Data
public class VideoForm {
    private String title;
    private String content;
    private MultipartFile video;
    private MultipartFile thumbnail;
    private List<String> videoTags;
}
