package com.kramphub;

import com.kramphub.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class BookAlbumSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookAlbumSearchApplication.class, args);
	}
}
