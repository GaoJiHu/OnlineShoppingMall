package com.whalesj.common.utils;



import java.io.IOException;
import java.util.Iterator;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient storageClient = null;
	public FastDFSClient(String conf) throws Exception{
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	public String uploadFile(String local_filename,String file_ext_name) throws Exception{
		String[] strings = storageClient.upload_file(local_filename, file_ext_name, null);
		String result = "";
		result = strings[0]+"/"+strings[1];
		return result;
	}
	public String uploadFile(byte[] file_buff,String file_ext_name) throws Exception{
		String[] strings = storageClient.upload_file(file_buff, file_ext_name, null);
		String result = "";
		result = strings[0]+"/"+strings[1];

		return result;
	}
}
