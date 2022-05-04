package com.infinitiesSoft.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinitiesSoft.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByNameContainingAndAuthorContainingAndTranslatorContainingAndIsbnContainingAndPublisherContainingAndPublicationDateContainingAndListPriceContaining(String name, String author, String translator, String isbn, String publisher, String publicationDate, String listPrice);
}