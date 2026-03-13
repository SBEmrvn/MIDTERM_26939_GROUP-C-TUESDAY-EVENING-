package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Check if tag with same name exists
    boolean existsByName(String name);
}