package com.infinitiesSoft.book.controller;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinitiesSoft.book.model.Book;
import com.infinitiesSoft.book.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	BookRepository bookRepository;
	
	@PostMapping()
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		try {
			return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping()
	public ResponseEntity<List<Book>> findBook(@RequestParam Map<String, String> paramsMap) {
		try {
			List<Book> book = null;
			if (paramsMap.isEmpty())
				book = bookRepository.findAll();
			else
				book = bookRepository.findByNameContainingAndAuthorContainingAndTranslatorContainingAndIsbnContainingAndPublisherContainingAndPublicationDateContainingAndListPriceContaining(paramsMap.getOrDefault("name", ""), paramsMap.getOrDefault("author", ""), paramsMap.getOrDefault("translator", ""), paramsMap.getOrDefault("isbn", ""), paramsMap.getOrDefault("publisher", ""), paramsMap.getOrDefault("publicationDate", ""), paramsMap.getOrDefault("listPrice", ""));
			if (book.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(book, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> findBook(@PathVariable("id") long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent())
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		else
 			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
		try {
			Optional<Book> oribook = bookRepository.findById(id);
			if (oribook.isPresent()) {
				book.setId(oribook.get().getId());
				BeanUtils.copyProperties(book, oribook.get());
				return new ResponseEntity<>(bookRepository.save(oribook.get()), HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<HttpStatus> deleteBook() {
		try {
			bookRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
		try {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}