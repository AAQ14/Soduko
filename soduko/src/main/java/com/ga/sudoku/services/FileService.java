package com.ga.sudoku.services;

import com.ga.sudoku.exceptions.InvalidFileException;
import com.ga.sudoku.exceptions.SudokuFileNotFoundException;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        if(!ALLOWED_EXTENSIONS.contains(getExtension(file.getOriginalFilename())))
            throw new InvalidFileException("Only .txt files are allowed");

    }

    private String getExtension(String fileName){
        if(fileName==null) return "";
        int dotIndex = fileName.lastIndexOf(".");
        if( dotIndex == -1) return "";
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    public String saveSolution(String originalFileName, String solutionText){
        if(originalFileName == null || originalFileName.isBlank()) {
            throw new InvalidFileException("File name is required. ");
        }
        String cleanName = Path.of(originalFileName).getFileName().toString();

        int dotIndex = cleanName.lastIndexOf(".");
        String baseName = (dotIndex == -1) ? cleanName : cleanName.substring(0,dotIndex);
        String solutionName = baseName + ".solution.txt";

        Path folderPath = Path.of(solutionsDir).toAbsolutePath().normalize();
        Path filePath = folderPath.resolve(solutionName).normalize();

        if(!filePath.startsWith(folderPath))
            throw new InvalidFileException("Invalid file name");

        try{
            Files.createDirectories(folderPath);
            Files.writeString(filePath, solutionText);
            return filePath.toString();
        }catch(IOException ex){
            throw new RuntimeException("Could not save the solution file", ex);
        }
    }


}
