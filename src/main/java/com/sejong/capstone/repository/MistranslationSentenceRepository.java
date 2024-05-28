package com.sejong.capstone.repository;

import com.sejong.capstone.domain.MistranslationSentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MistranslationSentenceRepository extends JpaRepository<MistranslationSentence, Long> {
}
