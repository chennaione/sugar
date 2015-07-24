package com.example.models;

import com.orm.SugarRecord;
import com.orm.dsl.Relationship;
import java.util.List;

public class Book extends SugarRecord {
    private String title;

    @Relationship(joinTable = "AUTHOR_BOOK", objectIdName = "BOOK_ID", refObjectIdName = "AUTHOR_ID")
    private Author author;

    @Relationship(joinTable = "BOOK_PAGE", objectIdName = "BOOK_ID", refObjectIdName = "PAGE_ID")
    private List<Page> pages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {

        //Lazy Loading
        if(author == null) {
            try {
                Relationship relationship = getClass().getField("author").getAnnotation(Relationship.class);

                List<Author> authors = Author.findByRelationship(Author.class, relationship, "BOOK_ID = " + getId().toString(), null, null, null);
                author = authors == null || authors.isEmpty() ? null : authors.get(0);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Page> getPages() {

        //Lazy Loading
        if(pages == null) {
            try {
                Relationship relationship = getClass().getField("pages").getAnnotation(Relationship.class);

                pages = Page.findByRelationship(Page.class, relationship, "BOOK_ID = " + getId().toString(), null, null, null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
