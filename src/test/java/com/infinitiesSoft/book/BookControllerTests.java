package com.infinitiesSoft.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinitiesSoft.book.controller.BookController;
import com.infinitiesSoft.book.model.Book;
import com.infinitiesSoft.book.repository.BookRepository;

@WebMvcTest(BookController.class)
@TestMethodOrder(OrderAnnotation.class)
class BookControllerTests {
	@MockBean
	BookRepository bookRepository;
	@Autowired
	MockMvc mockMvc; 
	@Autowired
	private ObjectMapper objectMapper;

	long id = 1L;
	
	@Test
	@Order(1)
	void createBook() throws Exception {
		mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new Book(1, "Java: 從入門到放棄1", "郭富凱", "吳育慧", "123", "虎林街", "2022-04-26", "5"))))
			.andExpect(status().isCreated())
			.andDo(print());
	}

	
	@Test
	@Order(2)
	void findBookWithId() throws Exception {
		Book book = new Book(id, "Java: 從入門到放棄1", "郭富凱", "吳育慧", "123", "虎林街", "2022-04-26", "5");
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		mockMvc.perform(get("/book/{id}", id)).andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value(book.getName()))
			.andExpect(jsonPath("$.author").value(book.getAuthor()))
			.andExpect(jsonPath("$.translator").value(book.getTranslator()))
			.andExpect(jsonPath("$.isbn").value(book.getIsbn()))
			.andExpect(jsonPath("$.publisher").value(book.getPublisher()))
			.andExpect(jsonPath("$.publicationDate").value(book.getPublicationDate()))
			.andExpect(jsonPath("$.listPrice").value(book.getListPrice()))
			.andDo(print());
	}
	
	@Test
	@Order(3)
	void findBookWithIdIsNotFound() throws Exception {
		when(bookRepository.findById(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/book/{id}", id))
			 .andExpect(status().isNotFound())
			 .andDo(print());
	}
	
	@Test
	@Order(4)
	void findBooks() throws Exception {
		List<Book> books = new ArrayList<>(
			Arrays.asList(new Book(1, "Java: 從入門到放棄1", "郭富凱", "吳育慧", "123", "虎林街", "2022-04-26", "5"),
						  new Book(2, "Java: 從入門到放棄2", "郭富凱", "吳育慧", "456", "文恩路", "2022-04-27", "50"),
						  new Book(3, "C#: 從入門到放棄1", "吳育慧", "郭富凱", "789", "虎林街", "2022-04-28", "500")));
		when(bookRepository.findAll()).thenReturn(books);
		mockMvc.perform(get("/book"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(books.size()))
			.andDo(print());
	}
	
	@Test
	@Order(5)
	void findBooksIsNotFound() throws Exception {
		List<Book> books = Collections.emptyList();
		when(bookRepository.findAll()).thenReturn(books);
		mockMvc.perform(get("/book"))
			.andExpect(status().isNoContent())
			.andDo(print());
	}
	
	@Test
	@Order(6)
	void findBooksWithParams() throws Exception {
		List<Book> books = new ArrayList<Book>(Arrays.asList(new Book(1, "Java: 從入門到放棄1", "郭富凱", "吳育慧", "123", "虎林街", "2022-04-26", "5"), 
															 new Book(2, "Java: 從入門到放棄2", "郭富凱", "吳育慧", "456", "文恩路", "2022-04-27", "50")));
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("name", "Java");
		paramsMap.add("author", "郭");
		when(bookRepository.findByNameContainingAndAuthorContainingAndTranslatorContainingAndIsbnContainingAndPublisherContainingAndPublicationDateContainingAndListPriceContaining(paramsMap.get("name").get(0), paramsMap.get("author").get(0), "", "", "", "", "")).thenReturn(books);
		mockMvc.perform(get("/book").params(paramsMap))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(books.size()))
			.andDo(print());
	}
	
	@Test
	@Order(7)
	void findBooksWithParamsIsNotFound() throws Exception {
		List<Book> books = Collections.emptyList();
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		when(bookRepository.findByNameContainingAndAuthorContainingAndTranslatorContainingAndIsbnContainingAndPublisherContainingAndPublicationDateContainingAndListPriceContaining("", "", "", "", "", "", "")).thenReturn(books);
		mockMvc.perform(get("/book").params(paramsMap))
			.andExpect(status().isNoContent())
			.andDo(print());
	}
	
	@Test
	@Order(8)
	void updateBook() throws Exception {
		Book updatedbook = new Book(id, "胃痛怎麼辦?", "吳育慧", "郭富凱", "ABC", "文恩路", "2022-04-29", "5000");
		Book book = new Book(id, "Java: 從入門到放棄1", "郭富凱", "吳育慧", "123", "虎林街", "2022-04-26", "5");
		when(bookRepository.findById(id)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(updatedbook);
		mockMvc.perform(put("/book/{id}", id).contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updatedbook)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(updatedbook.getName()))
			.andExpect(jsonPath("$.author").value(updatedbook.getAuthor()))
			.andExpect(jsonPath("$.translator").value(updatedbook.getTranslator()))
			.andExpect(jsonPath("$.isbn").value(updatedbook.getIsbn()))
			.andExpect(jsonPath("$.publisher").value(updatedbook.getPublisher()))
			.andExpect(jsonPath("$.publicationDate").value(updatedbook.getPublicationDate()))
			.andExpect(jsonPath("$.listPrice").value(updatedbook.getListPrice()))
			.andDo(print());
	}
	
	@Test
	@Order(9)
	void updateBookIsNotFound() throws Exception {
		Book updatedbook = new Book(id, "胃痛怎麼辦?", "吳育慧", "郭富凱", "ABC", "文恩路", "2022-04-29", "5000");
		when(bookRepository.findById(id)).thenReturn(Optional.empty());
		mockMvc.perform(put("/book/{id}", id).contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updatedbook)))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	@Order(10)
	void deleteBook() throws Exception {
		doNothing().when(bookRepository).deleteById(id);
		mockMvc.perform(delete("/book/{id}", id))
			 .andExpect(status().isOk())
			 .andDo(print());
	}

	@Test
	@Order(11)
	void deleteBooks() throws Exception {
		doNothing().when(bookRepository).deleteAll();
		mockMvc.perform(delete("/book"))
			 .andExpect(status().isOk())
			 .andDo(print());
	}
}