package com.subsolr.entityprocessors.datasources;

import java.io.Reader;
/**
 * POJO for File Data sources
 * @author vamsiy-mac aditya
 */
public abstract class FileDataSource implements DataSource {

    public abstract Reader getFileReader(String fileName);

}
