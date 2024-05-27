package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 특정 사용자의 Note 정보들을 담기 위한 DTO
 */
@Data
@AllArgsConstructor
public class NoteTotalResponse {
    private List<NoteInnerResponse> noteList;
}
