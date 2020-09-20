package io.keeplearning.bookinfoservice.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "GoodreadsResponse")
public class GoodreadsResponse {

    private Book book;

    public static class Book {
        private String description;
        private String title;
        private String isbn;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
