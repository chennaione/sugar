package com.example.models;

import com.orm.SugarRecord;
import com.orm.dsl.Relationship;
import java.util.List;

public class Page extends SugarRecord {
    private String text;
    private int pageNumber;

    @Relationship(joinTable = "BOOK_PAGE", objectIdName = "PAGE_ID", refObjectIdName = "BOOK_ID")
    private Book book;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Book getBook() {

        //Lazy Loading
        if(book == null) {
            try {
                Relationship relationship = getClass().getField("book").getAnnotation(Relationship.class);

                List<Book> books = Book.findByRelationship(Book.class, relationship, "PAGE_ID = " + getId().toString(), null, null, null);
                book = books == null || books.isEmpty() ? null : books.get(0);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
