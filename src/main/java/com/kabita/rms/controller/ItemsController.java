package com.kabita.rms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kabita.rms.payload.ApiResponse;
import com.kabita.rms.payload.ItemsDto;
import com.kabita.rms.services.FileService;
import com.kabita.rms.servicesImpl.ItemServicesImpl;

@RestController
@RequestMapping("/api")
public class ItemsController {
	@Autowired
	private ItemServicesImpl itemService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String filePath;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/category/{categoryId}/products")
	public ResponseEntity<ItemsDto> addProducts(@Valid @RequestBody ItemsDto itemDto,
			@PathVariable Integer categoryId) {
		return new ResponseEntity<ItemsDto>(this.itemService.addItems(itemDto, categoryId), HttpStatus.OK);
	}

	@GetMapping("/products/get")
	public ResponseEntity<List<ItemsDto>> getAllProducts() {
		return new ResponseEntity<List<ItemsDto>>(this.itemService.getAllItems(), HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ItemsDto> getSingleItem(@PathVariable Integer productId) {
		return new ResponseEntity<ItemsDto>(this.itemService.getSingleItem(productId), HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/products")
	public ResponseEntity<List<ItemsDto>> getProductByCategory(@PathVariable Integer categoryId) {
		return new ResponseEntity<List<ItemsDto>>(this.itemService.getItemByCategory(categoryId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/products/{productId}/category/{categoryId}")
	public ResponseEntity<ItemsDto> updateProduct(@Valid @RequestBody ItemsDto itemsDto,
			@PathVariable Integer productId, @PathVariable Integer categoryId) {
		return new ResponseEntity<ItemsDto>(this.itemService.updateItem(itemsDto, productId, categoryId),
				HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) {
		this.itemService.deleteItem(productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted successfully!", true), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("item/image/upload/{postId}")
	public ResponseEntity<ItemsDto> uploadPostImg(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		String fileName = this.fileService.uploadImage(filePath, image);
		ItemsDto itemsDto = this.itemService.getSingleItem(postId);
		itemsDto.setProductImg(fileName);

		ItemsDto updatedImg = this.itemService.updateItemImg(itemsDto, postId);

		return new ResponseEntity<ItemsDto>(updatedImg, HttpStatus.OK);
	}

	@GetMapping(value = "/items/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imgName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(filePath, imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
