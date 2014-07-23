package com.orm;

import java.util.ArrayList;

/**
 * Added by Mitja Dragman on 23.7.2014.
 * This is simple interface for objects which extend SugarRecord<T>
 * Fast way to write ArrayList of object extending SugarRecord
 * 
 */
public interface SimpleSugarInterface <T> {

    abstract ArrayList<T> getAllObjectFromDB();

    abstract void saveAllObjectToDB(ArrayList <T> t);
}
