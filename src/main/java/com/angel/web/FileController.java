package com.angel.web;

import com.angel.common.Constant;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 陈明
 * @date 2019/9/3 18:07
 */
@RestController
public class FileController
{
	@Autowired

	private FastFileStorageClient storageClient;
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String uploadFile(MultipartFile file) throws IOException
	{
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);
		return Constant.STORE_HOST_NAME + storePath.getFullPath();
	}
	
	
}
