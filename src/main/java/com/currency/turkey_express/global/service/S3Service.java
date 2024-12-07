package com.currency.turkey_express.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.currency.turkey_express.domain.store.repository.StoreRepository;
import com.currency.turkey_express.global.base.entity.Store;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3 amazonS3;
	private final StoreRepository storeRepository;

	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;

	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = generateUniqueFileName(file.getOriginalFilename());

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());

		amazonS3.putObject(bucketName,fileName,file.getInputStream(),metadata);

		return fileName;
	}

	public InputStream getImage(String key){
		S3Object object = amazonS3.getObject(bucketName,key);
		return object.getObjectContent();
	}

	//TODO 예외처리 수정
	public void deleteImageForStore(Long storeId){
		Store store = storeRepository.findById(storeId).orElseThrow(
			()-> new BusinessException(ExceptionType.STORE_NOT_FOUND)
		);
		String key = store.getImageKeyValue();
		if (key == null || key.isEmpty()){
			return;
		}

		try {
			amazonS3.deleteObject(bucketName,key);
		}catch (Exception e){
			throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
		}
	}

	public String encodingImageToBase64(byte[] image){
		return Base64.getEncoder().encodeToString(image);
	}

	private String generateUniqueFileName(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString() + extension;
	}
}
