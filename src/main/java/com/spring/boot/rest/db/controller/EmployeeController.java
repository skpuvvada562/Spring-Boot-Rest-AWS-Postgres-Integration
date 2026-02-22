package com.spring.boot.rest.db.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.rest.db.config.AwsS3Config;
import com.spring.boot.rest.db.pojo.EmployeeDto;
import com.spring.boot.rest.db.service.AwsS3Service;
import com.spring.boot.rest.db.service.EmployeeService;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Value("${s3.bucket.name}")
	private String bucketName;

    private final AwsS3Config awsS3Config;

	@Autowired
	EmployeeService empService;

	@Autowired
	AwsS3Service s3Service;
	
	@Autowired
	AwsS3Config s3Config;
	

    EmployeeController(AwsS3Config awsS3Config) {
        this.awsS3Config = awsS3Config;
    }

	@PostMapping("/save")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto empDto) {

		EmployeeDto dto = empService.saveEmployeeService(empDto);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {

		List<EmployeeDto> empList = empService.getAllEmployeesService();

		return ResponseEntity.ok(empList);
	}

	@GetMapping("/getId/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id) {

		EmployeeDto emp = empService.getEmployeeByIdService(id);

		return ResponseEntity.ok(emp);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {

		String fileName = s3Service.uploadFile(file);
		return ResponseEntity.ok("Uploaded successfully: " + fileName);
	}

	@GetMapping("/listFiles")
	public ResponseEntity<List<String>> listFiles() {
		List<String> files = s3Service.listOfFilesinS3();
		return ResponseEntity.ok(files);
	}

	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
		s3Service.deleteFileFromS3(fileName);
		return ResponseEntity.ok("Deleted successfully: " + fileName);
	}

	@GetMapping("/downloadFile")
	public ResponseEntity<byte[]> downloadFile(@RequestParam String fileName) {
		byte[] data = s3Service.downloadFileFromS3(fileName);
		GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
		ResponseInputStream<GetObjectResponse> response=awsS3Config.getS3Client().getObject(request);
		System.out.println(response.response().contentType());
		HttpHeaders headers = new HttpHeaders();
		if("application/pdf".equals(response.response().contentType())) {
			headers.setContentType(MediaType.APPLICATION_PDF); // or detect dynamically
		}
		headers.setContentType(MediaType.APPLICATION_PDF); //this is working for document even above if is failed
	    headers.setContentDisposition(ContentDisposition
	            .attachment()
	            .filename(fileName)
	            .build());
		return new ResponseEntity<>(data, headers, HttpStatus.OK);
	}

}
