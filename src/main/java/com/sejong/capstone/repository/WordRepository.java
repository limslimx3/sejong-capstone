package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    @Query("select w from Word w where w.subtitleWord.korWord = :wordName")
    List<Word> findByWordName(@Param("wordName") String wordName);
}
