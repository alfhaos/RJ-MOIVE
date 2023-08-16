package dev.jpa.movie.user.repository;

import dev.jpa.movie.user.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String userId);
}
