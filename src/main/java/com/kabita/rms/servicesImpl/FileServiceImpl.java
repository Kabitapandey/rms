package com.kabita.rms.servicesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kabita.rms.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
//		getting the file name
		String name = file.getOriginalFilename();

		String filePath = path + File.separator + name;

		File f = new File(path);
//creating the file directory if it does not exist
		if (!f.exists()) {
			f.mkdir();
		}
//copying the input bytes to the destintated path
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
//		reading the input bytes and returning it
		InputStream is=new FileInputStream(fullPath);
		return is;
	}

}
