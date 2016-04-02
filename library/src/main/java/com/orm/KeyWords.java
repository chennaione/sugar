package com.orm;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 29/03/16.
 */
public class KeyWords {
    private static Set<String> reservedWords;
    static{
        reservedWords = new HashSet<>();
        reservedWords.add("");
        reservedWords.add("ABORT");
        reservedWords.add("ACTION");
        reservedWords.add("ADD");
        reservedWords.add("AFTER");
        reservedWords.add("ALTER");
        reservedWords.add("ANALYZE");
        reservedWords.add("AND");
        reservedWords.add("AS");
        reservedWords.add("ASC");
        reservedWords.add("ATTACH");
        reservedWords.add("AUTOINCREMENT");
        reservedWords.add("BEFORE");
        reservedWords.add("BEGIN");
        reservedWords.add("BETWEEN");
        reservedWords.add("BY");
        reservedWords.add("CASCADE");
        reservedWords.add("CASE");
        reservedWords.add("CAST");
        reservedWords.add("CHECK");
        reservedWords.add("COLLATE");
        reservedWords.add("COLUMN");
        reservedWords.add("COMMIT");
        reservedWords.add("CONFLICT");
        reservedWords.add("CONSTRAINT");
        reservedWords.add("CREATE");
        reservedWords.add("CROSS");
        reservedWords.add("CURRENT_DATE");
        reservedWords.add("CURRENT_TIME");
        reservedWords.add("CURRENT_TIMESTAMP");
        reservedWords.add("DATABASE");
        reservedWords.add("DEFAULT");
        reservedWords.add("DEFERRABLE");
        reservedWords.add("DEFERRED");
        reservedWords.add("DELETE");
        reservedWords.add("DESC");
        reservedWords.add("DETACH");
        reservedWords.add("DISTINCT");
        reservedWords.add("DROP");
        reservedWords.add("EACH");
        reservedWords.add("ELSE");
        reservedWords.add("END");
        reservedWords.add("ESCAPE");
        reservedWords.add("EXCEPT");
        reservedWords.add("EXCLUSIVE");
        reservedWords.add("EXISTS");
        reservedWords.add("EXPLAIN");
        reservedWords.add("FAIL");
        reservedWords.add("FOR");
        reservedWords.add("FOREIGN");
        reservedWords.add("FROM");
        reservedWords.add("FULL");
        reservedWords.add("GLOB");
        reservedWords.add("GROUP");
        reservedWords.add("HAVING");
        reservedWords.add("IF");
        reservedWords.add("IGNORE");
        reservedWords.add("IMMEDIATE");
        reservedWords.add("IN");
        reservedWords.add("INDEX");
        reservedWords.add("INDEXED");
        reservedWords.add("INITIALLY");
        reservedWords.add("INNER");
        reservedWords.add("INSERT");
        reservedWords.add("INSTEAD");
        reservedWords.add("INTERSECT");
        reservedWords.add("INTO");
        reservedWords.add("IS");
        reservedWords.add("ISNULL");
        reservedWords.add("JOIN");
        reservedWords.add("KEY");
        reservedWords.add("LEFT");
        reservedWords.add("LIKE");
        reservedWords.add("LIMIT");
        reservedWords.add("MATCH");
        reservedWords.add("NATURAL");
        reservedWords.add("NO");
        reservedWords.add("NOT");
        reservedWords.add("NOTNULL");
        reservedWords.add("NULL");
        reservedWords.add("OF");
        reservedWords.add("OFFSET");
        reservedWords.add("ON");
        reservedWords.add("OR");
        reservedWords.add("ORDER");
        reservedWords.add("OUTER");
        reservedWords.add("PLAN");
        reservedWords.add("PRAGMA");
        reservedWords.add("PRIMARY");
        reservedWords.add("QUERY");
        reservedWords.add("RAISE");
        reservedWords.add("RECURSIVE");
        reservedWords.add("REFERENCES");
        reservedWords.add("REGEXP");
        reservedWords.add("REINDEX");
        reservedWords.add("RELEASE");
        reservedWords.add("RENAME");
        reservedWords.add("REPLACE");
        reservedWords.add("RESTRICT");
        reservedWords.add("RIGHT");
        reservedWords.add("ROLLBACK");
        reservedWords.add("ROW");
        reservedWords.add("SAVEPOINT");
        reservedWords.add("SELECT");
        reservedWords.add("SET");
        reservedWords.add("TABLE");
        reservedWords.add("TEMP");
        reservedWords.add("TEMPORARY");
        reservedWords.add("THEN");
        reservedWords.add("TO");
        reservedWords.add("TRANSACTION");
        reservedWords.add("TRIGGER");
        reservedWords.add("UNION");
        reservedWords.add("UNIQUE");
        reservedWords.add("UPDATE");
        reservedWords.add("USING");
        reservedWords.add("VACUUM");
        reservedWords.add("VALUES");
        reservedWords.add("VIEW");
        reservedWords.add("VIRTUAL");
        reservedWords.add("WHEN");
        reservedWords.add("WHERE");
        reservedWords.add("WITH");
        reservedWords.add("WITHOUT");}

    public boolean isaReservedWords(String word){
        return reservedWords.contains(word);
    }
}

