package com.whalesj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whalesj.common.pojo.PictureResult;
import com.whalesj.service.PictureService;


/**
 * 图片上传Controller
 * @author wushijia
 *
 */
@Controller
public class PictureController {
	@Autowired
	PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PictureResult uploadFile(MultipartFile uploadFile){
		PictureResult result = pictureService.uploadPic(uploadFile);
		return result;
	}
}
