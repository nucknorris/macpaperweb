package de.htwkleipzig.mmdb.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utilities.class);

    public static final List<File> getAllFilesOfDirectory() {
        LOGGER.debug("files loading from {}", Utilities.getProperty("paper.directory"));
        File file = new File(Utilities.getProperty("paper.directory"));
        if (!file.exists()) {
            LOGGER.debug("file doesn't exists");
            boolean success = file.mkdir();
            LOGGER.debug("folder created? {}", success);
            return null;
        }
        if (file.listFiles() != null) {
            LOGGER.debug("files found and returned");
            return Arrays.asList(file.listFiles());
        } else {
            LOGGER.debug("No files found");
            return null;
        }
    }

    public static String getProperty(String property) {
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration("META-INF/spring/macpaper.properties");
            return properties.getString(property);
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
