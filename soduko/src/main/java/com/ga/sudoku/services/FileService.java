package com.ga.sudoku.services;

import com.ga.sudoku.exceptions.InvalidFileException;
import com.ga.sudoku.exceptions.SudokuFileNotFoundException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileService {
    private final static Set<String> ALLOWED_FILE_TYPES = Set.of("text/plain");
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("txt");

    private static final long MAX_FILE_SIZE = 10 * 1024;

    @Value("${solutions-dir}")
    private String solutionsDir;


    public void validate(MultipartFile file){
        if(file == null || file.isEmpty())
            throw new InvalidFileException("File is required");

        if(file.getSize() > MAX_FILE_SIZE)
            throw new InvalidFileException("File is too large (maximum 10 KB)");

        String contentType = file.getContentType();
        if(contentType == null || !ALLOWED_FILE_TYPES.contains(contentType))
            throw new InvalidFileException("Only .txt files are allowed");

    }
}
