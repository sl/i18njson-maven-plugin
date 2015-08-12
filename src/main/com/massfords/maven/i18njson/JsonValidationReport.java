package com.massfords.maven.i18njson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author slazarus
 */
public class JsonValidationReport {

    private int total = 0;

    private int invalid = 0;

    private int valid = 0;

    private List<String> checkedFiles = new ArrayList<String>();

    private String activeFile = null;

    private List<String> errors = new ArrayList<String>();

    protected JsonValidationReport addFile(String file) {
        this.checkedFiles.add(file);
        return this;
    }

    protected void success() {
        ++total;
        ++valid;
    }

    protected void failure(String message) {
        this.errors.add(message);
        ++total;
        ++invalid;
    }

    public int getValid() {
        return valid;
    }

    public int getInvalid() {
        return invalid;
    }

    public int getTotal() {
        return total;
    }

    protected void createReportFile(File file) throws MojoExecutionException {
        if (!file.getParentFile().isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, this);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to create a report file.", e);
        }
    }


}
