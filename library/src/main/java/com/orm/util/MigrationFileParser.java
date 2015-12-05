package com.orm.util;

/**
 * Created by Nursultan Turdaliev on 12/4/15.
 */
public class MigrationFileParser {

    private String content;

    /**
     * @param content
     */
    public MigrationFileParser(String content){
        this.content = content.replaceAll("(\\/\\*([\\s\\S]*?)\\*\\/)|(--(.)*)|(\n)","");
    }

    public String[] getStatements(){
        return this.content.split(";");
    }

}
