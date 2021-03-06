package com.whalesj.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.whalesj.common.pojo.PictureResult;
import com.whalesj.common.utils.FastDFSClient;
import com.whalesj.service.PictureService;

//图片上传Service
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${IMAGE_SERVER_BASE_URL}")
	private String basepath;
	@Override
	public PictureResult uploadPic(MultipartFile picfile) {
		PictureResult result = new PictureResult();
		//先判断图片是否为空
		if(picfile.isEmpty()){
			result.setError(1);//有错error为1，无措为0
			result.setMessage("图片为空");
			return result;
		}
		try {
			//取图片扩展名
			String originalFilename = picfile.getOriginalFilename();
			String extname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//扩展名不要" . "
			FastDFSClient client = new FastDFSClient("G:\\OnlineShoppingMall\\online-shoppingmall-manager\\online-shoppingmall-manager-web\\src\\main\\resources\\properties\\client.conf");
			String url = client.uploadFile(picfile.getBytes(), extname);

			//拼接图片服务器域名
			url = basepath+url;
			//将url响应个客户端
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);//有错error为1，无措为0
			result.setMessage("图片上传失败");
		}
		return result;
	}

}
