package com.angel.web;

import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@Autowired
	private FdfsWebServer webServer;
	
	/**
	 * 文件上传
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String uploadFile(MultipartFile file) throws IOException
	{
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);
		return webServer.getWebServerUrl() + storePath.getFullPath();
	}
	
	/**
	 * 文件删除
	 * @param fileUrl
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public Boolean deleteFile(String fileUrl)
	{
		if ( StringUtils.isEmpty(fileUrl) )
		{
			return Boolean.FALSE;
		}
		StorePath storePath = StorePath.praseFromUrl(fileUrl);
		storageClient.deleteFile(storePath.getGroup(),storePath.getPath());
		return Boolean.TRUE;
	}
	
	@RequestMapping(value = "/downFile",method = RequestMethod.GET)
	public ResponseEntity<byte[]> downFile(String fileUrl)
	{
		if ( StringUtils.isEmpty(fileUrl) )
		{
			return null;
		}
		StorePath storePath = StorePath.praseFromUrl(fileUrl);
		DownloadByteArray downloadByteArray = new DownloadByteArray();
		byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
		//设置响应头
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+"test.mp4");
		headers.set(HttpHeaders.CONTENT_LENGTH,String.valueOf(bytes.length) );
		//设置响应编码
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(bytes, headers, statusCode);
		return response;
	}
	
}
