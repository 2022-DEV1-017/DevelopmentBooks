package com.digitalstork.bookskata.repository;

import com.digitalstork.bookskata.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByExternalCode(String externalCode);

}
