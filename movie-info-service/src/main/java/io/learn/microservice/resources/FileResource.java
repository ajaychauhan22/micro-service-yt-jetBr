package io.learn.microservice.resources;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class FileResource {

	@GetMapping(value = "/pdf-d", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> getPdf(@RequestParam("template") String template) throws Exception {
		System.out.println("getPdf" + template);

		ClassPathResource pdfFile = new ClassPathResource("emailer-desktop_account opening.pdf");
		InputStreamResource inputStreamResource = new InputStreamResource(pdfFile.getInputStream());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Abcd.pdf");

		return ResponseEntity.ok().headers(headers).body(inputStreamResource);
	}

}
