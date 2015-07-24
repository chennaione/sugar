package com.example.models;

import com.orm.SugarRecord;
import com.orm.dsl.Relationship;
import java.util.List;

public class Author extends SugarRecord {
    private String name;

    @Relationship(joinTable = "AUTHOR_BOOK", objectIdName = "AUTHOR_ID", refObjectIdName = "BOOK_ID")
    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {

        //Lazy Loading
        if(books == null) {
            try {
                Relationship relationship = getClass().getField("books").getAnnotation(Relationship.class);

                books = Book.findByRelationship(Book.class, relationship, "AUTHOR_ID = " + getId().toString(), null, null, null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
