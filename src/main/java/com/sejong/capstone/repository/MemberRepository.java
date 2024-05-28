package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);

    @Query("select m from Member m join fetch m.channel c where m.id = :id")
    Optional<Member> findMemberWithChannelById(@Param("id") Long id);
}
