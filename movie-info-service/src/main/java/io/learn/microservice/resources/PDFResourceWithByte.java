package io.learn.microservice.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class PDFResourceWithByte {

	@GetMapping(value = "/pdf-byte", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPdf() throws Exception {
		System.out.println("getPdf");

		File file = new File(
				"C:\\Users\\ajaychauhan01\\Downloads\\sms_email_pdf_documents\\account opening\\emailer-desktop_account opening.pdf");
		try (InputStream in = new FileInputStream(file)){

			byte[] buff = new byte[8000];
			int bytesRead = 0;

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			while ((bytesRead = in.read(buff)) != -1) {
				bao.write(buff, 0, bytesRead);
			}

			byte[] data = bao.toByteArray();
			var headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

			return ResponseEntity.ok().headers(headers).body(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
}
