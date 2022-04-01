package com.digitalstork.developmentbooks.repository;

import com.digitalstork.developmentbooks.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByExternalCode(String externalCode);

}
