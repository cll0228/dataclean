package com.holyrobot.hbase;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


/**
 * hdfs文件操作
 * @author Administrator
 *
 */
public class HdfsClient {
	private static Logger logger = LoggerFactory.getLogger(HdfsClient.class);
	public static String HDFSUri = "";


	/**
	 * 获取文件系统
	 * 
	 * @return FileSystem
	 */
	public static FileSystem getFileSystem() {
	    //读取配置文件
	    Configuration conf = new Configuration();
        String path = HdfsClient.class.getClassLoader().getResource("").getPath();
        conf.addResource(new Path(path + "dev/core-site.xml"));
        conf.addResource(new Path(path + "dev/hdfs-site.xml"));
	    // 文件系统
	    FileSystem fs = null;
	    
	    String hdfsUri = HDFSUri;
	    if(StringUtils.isBlank(hdfsUri)){
	        // 返回默认文件系统  如果在 Hadoop集群下运行，使用此种方法可直接获取默认文件系统
	        try {
	            fs = FileSystem.get(conf);
	        } catch (IOException e) {
	            logger.error("", e);
	        }
	    }else{
	        // 返回指定的文件系统,如果在本地测试，需要使用此种方法获取文件系统
	        try {
	            URI uri = new URI(hdfsUri.trim());
	            fs = FileSystem.get(uri,conf);
	        } catch (Exception e) {
	            logger.error("", e);
	        }
	    }
	        
	    return fs;
	}
	//创建新文件
    public static void createFile(String dst , byte[] contents) throws IOException{

        FileSystem fs = getFileSystem();
        Path dstPath = new Path(dst); //目标路径
        //打开一个输出流
        FSDataOutputStream outputStream = fs.create(dstPath);
        outputStream.write(contents);
        outputStream.close();
        fs.close();
        System.out.println("文件创建成功！");
    }
    
    /**
     * 删除文件或者文件目录
     * 
     * @param path
     */
    public static void rmdir(String path) {
        try {
            // 返回FileSystem对象
            FileSystem fs = getFileSystem();
            String hdfsUri = HDFSUri;
            if(StringUtils.isNotBlank(hdfsUri)){
                path = hdfsUri + path;
            }            
            // 删除文件或者文件目录  delete(Path f) 此方法已经弃用
            fs.delete(new Path(path),true);
            // 释放资源
            fs.close();
        } catch (IllegalArgumentException | IOException e) {
            logger.error("", e);
        }
    }
    
    /**
     * 判断目录是否存在
     * 
     * @param srcPath
     * @param dstPath
     */
    public boolean existDir(String filePath, boolean create){
        boolean flag = false;
        
        if (StringUtils.isEmpty(filePath)){
            return flag;
        }
        
        try{
            Path path = new Path(filePath);
            // FileSystem对象
            FileSystem fs = getFileSystem();
            
            if (create){
                if (!fs.exists(path)){
                    fs.mkdirs(path);
                }
            }
            
            if (fs.isDirectory(path)){
                flag = true;
            }
        }catch (Exception e){
            logger.error("", e);
        }
        
        return flag;
    }
    /**
     * 获取1号店生鲜食品的分类id字符串
     * @param filePath
     * @return
     */
    public static String[] getFileByDirectory(String filePath) {
        // 遍历目录下的所有文件
        String result = "";
        String[] contents = null;
        try {
            FileSystem fs = getFileSystem();
            FileStatus[] status = fs.listStatus(new Path(filePath));
            contents = new String[status.length];
            BufferedReader br = null;
            int index = 0;
            for (FileStatus file : status) {
                result += file.getPath().getName() + ",";
                FSDataInputStream inputStream = fs.open(file.getPath());
                br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                String cc = "";
                while (null != (line = br.readLine())) {
                    cc += line + "\n";
                }// end of while
                contents[index] = cc;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return contents;
    }
    /**
     * 文件重命名
     * 
     * @param srcPath
     * @param dstPath
     */
    public boolean rename(String srcPath, String dstPath){
        boolean flag = false;
        try    {
            // 返回FileSystem对象
            FileSystem fs = getFileSystem();
            
            String hdfsUri = HDFSUri;
            if(StringUtils.isNotBlank(hdfsUri)){
                srcPath = hdfsUri + srcPath;
                dstPath = hdfsUri + dstPath;
            }
            
            flag = fs.rename(new Path(srcPath), new Path(dstPath));
        } catch (IOException e) {
            logger.error("{} rename to {} error.", srcPath, dstPath);
        }
        
        return flag;
    }
    
    /**
     * 查找某个文件在 HDFS集群的位置
     * 
     * @param filePath
     * @return BlockLocation[]
     */
    public static BlockLocation[] getFileBlockLocations(String filePath) {
        // 文件路径
        String hdfsUri = HDFSUri;
        if(StringUtils.isNotBlank(hdfsUri)){
            filePath = hdfsUri + filePath;
        }
        Path path = new Path(filePath);
        
        // 文件块位置列表
        BlockLocation[] blkLocations = new BlockLocation[0];
        try {
            // 返回FileSystem对象
            FileSystem fs = getFileSystem();
            // 获取文件目录 
            FileStatus filestatus = fs.getFileStatus(path);
            //获取文件块位置列表
            blkLocations = fs.getFileBlockLocations(filestatus, 0, filestatus.getLen());
        } catch (IOException e) {
            logger.error("", e);
        }
        return blkLocations;
    }
    
    /**
     * 获取 HDFS 集群节点信息
     * 
     * @return DatanodeInfo[]
     */
    public static DatanodeInfo[] getHDFSNodes() {
        // 获取所有节点
        DatanodeInfo[] dataNodeStats = new DatanodeInfo[0];
        
        try {
            // 返回FileSystem对象
            FileSystem fs = getFileSystem();
            // 获取分布式文件系统
            DistributedFileSystem hdfs = (DistributedFileSystem)fs;
            dataNodeStats = hdfs.getDataNodeStats();
        } catch (IOException e) {
            logger.error("", e);
        }
        return dataNodeStats;
    }
    
    /**
     * 从 HDFS 下载文件
     * 
     * @param srcFile
     * @param destPath
     */
    public static void getFile(String srcFile,String destPath) {
        // 源文件路径
        String hdfsUri = HDFSUri;
        if(StringUtils.isNotBlank(hdfsUri)){
            srcFile = hdfsUri + srcFile;
        }
        Path srcPath = new Path(srcFile);
        
        // 目的路径是Linux下的路径，如果在 windows 下测试，需要改写为Windows下的路径，比如D://hadoop/djt/
        Path dstPath = new Path(destPath);
        
        try {
            // 获取FileSystem对象
            FileSystem fs = getFileSystem();
            // 下载hdfs上的文件
            fs.copyToLocalFile(srcPath, dstPath);
            // 释放资源
            fs.close();
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    
    /**
     * 文件上传至 HDFS
     * 
     * @param delSrc
     * @param overwrite
     * @param srcFile
     * @param destPath
     */
    public static void copyFileToHDFS(boolean delSrc, boolean overwrite,String srcFile,String destPath) {
        // 源文件路径是Linux下的路径，如果在 windows 下测试，需要改写为Windows下的路径，比如D://hadoop/djt/weibo.txt
        Path srcPath = new Path(srcFile);
        
        // 目的路径
        String hdfsUri = HDFSUri;
        if(StringUtils.isNotBlank(hdfsUri)){
            destPath = hdfsUri + destPath;
        }
        Path dstPath = new Path(destPath);
        
        // 实现文件上传
        try {
            // 获取FileSystem对象
            FileSystem fs = getFileSystem();
            fs.copyFromLocalFile(srcPath, dstPath);
            fs.copyFromLocalFile(delSrc,overwrite,srcPath, dstPath);
            //释放资源
            fs.close();
        } catch (IOException e) {
            logger.error("", e);
        }
    }
    
    /**
     * 根据filter获取目录下的文件
     * 
     * @param path
     * @param pathFilter
     * @return String[]
     */
    public static String[] ListFile(String path,PathFilter pathFilter) {
        String[] files = new String[0];
        
        try {
            // 返回FileSystem对象
            FileSystem fs = getFileSystem();
            
            String hdfsUri = HDFSUri;
            if(StringUtils.isNotBlank(hdfsUri)){
                path = hdfsUri + path;
            }
            
            FileStatus[] status;
            if(pathFilter != null){
                // 根据filter列出目录内容
                status = fs.listStatus(new Path(path),pathFilter);
            }else{
                // 列出目录内容
                status = fs.listStatus(new Path(path));
            }
            
            // 获取目录下的所有文件路径
            Path[] listedPaths = FileUtil.stat2Paths(status);
            // 转换String[]
            if (listedPaths != null && listedPaths.length > 0){
                files = new String[listedPaths.length];
                for (int i = 0; i < files.length; i++){
                    files[i] = listedPaths[i].toString();
                }
            }
            // 释放资源
            fs.close();
        } catch (IllegalArgumentException | IOException e) {
            logger.error("", e);
        }
        
        return files;
    }
    
    /**
     * 创建文件目录
     * 
     * @param path
     */
    public static void mkdir(String path) {
        try {
            // 获取文件系统
            FileSystem fs = getFileSystem();
            
            String hdfsUri = HDFSUri;
            if(StringUtils.isNotBlank(hdfsUri)){
                path = hdfsUri + path;
            }
            
            // 创建目录
            fs.mkdirs(new Path(path));
            
            //释放资源
            fs.close();
        } catch (IllegalArgumentException | IOException e) {
            logger.error("", e);
        }
    }
    static int counter = 0;
    static int success = 0;
    public static void main(String[] args) throws IOException {
        rmdir("hdfs://node2:8020/user/hdfs/config/ctrip.xml");
        copyFileToHDFS(false,true,
                "D:\\worksource\\holyrobot-realtimeprocess\\streamingprocess\\src\\main\\resources\\config\\lmm.xml",
                "hdfs://node2:8020/user/hdfs/config/lmm.xml");
    }



}
