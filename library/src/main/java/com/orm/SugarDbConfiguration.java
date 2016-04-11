package com.orm;

import java.util.Locale;

/**
 * @author jonatan.salas
 */
public class SugarDbConfiguration {

    /**
     * Tells SQLite which is the database default locale
     */
    private Locale databaseLocale;

    /**
     * Tells SQLite how much it can grow
     */
    private Long maxSize;

    /**
     * Tells SQLite the page size that have
     */
    private Long pageSize;

    public SugarDbConfiguration() { }

    public Locale getDatabaseLocale() {
        return databaseLocale;
    }

    public SugarDbConfiguration setDatabaseLocale(Locale databaseLocale) {
        this.databaseLocale = databaseLocale;
        return this;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public SugarDbConfiguration setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public SugarDbConfiguration setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "SugarDbConfiguration{" +
                ", databaseLocale=" + databaseLocale +
                ", maxSize=" + maxSize +
                ", pageSize=" + pageSize +
                '}';
    }
}
