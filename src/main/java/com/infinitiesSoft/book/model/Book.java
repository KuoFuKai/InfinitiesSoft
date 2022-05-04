package com.infinitiesSoft.book.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "author", nullable = false)
	private String author;
	@Column(name = "translator")
	private String translator;
	@Column(name = "isbn", nullable = false)
	private String isbn;
	@Column(name = "publisher", nullable = false)
	private String publisher;
	@Column(name = "publicationDate", nullable = false)
	private String publicationDate;
	@Column(name = "listPrice", nullable = false)
	private String listPrice;
}