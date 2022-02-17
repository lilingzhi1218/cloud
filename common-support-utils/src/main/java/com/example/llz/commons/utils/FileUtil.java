package com.example.llz.commons.utils;


import com.example.llz.commons.utils.enums.FilePathTypeEnum;
import com.example.llz.commons.utils.exception.ServiceException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件处理类。
 * <ul>接口方法修改记录：
 *   <li>2021-6-13 dennis inspectMacroPath改名为inspectPath；safeJspFileName改名为safeWhiteFileName；
 *           getFileName改名为getFilePathName</li>
 * </ul>
 */
public final class FileUtil {

	/**
	 * 私有化工具类构造函数
	 */
	private FileUtil() {
	}

	/**
	 * 协议名与服务器地址的分隔符(://)最大可能位置，查找://的位置如果超过此常量值，
	 * 则表示是查询参数中的内容，不算是URL地址中的协议名分隔符。
	 */
	public static final int SCHEME_SPLIT_MAXPOS = 12;

	/**
	 * 图片后缀类型
	 */
	private static final Set<String> IMAGE_TYPE = new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));

	/**
	 * 默认的扩展名黑名单
	 */
	public static final String[] defaultBlackFileExt=new String[] {".jsp",".jspx",".jspf",".sh",".html",".htm",".shtml",".js",".htaccess"};
//
//	/**
//	 * 宏路径包含字符默认黑名单
//	 */
//	public static final String[] FILTER_PATH = new String[]{"../", "/..", ".." + java.io.File.separator, java.io.File.separator + ".."};

	/**
	 * 获取文件名的扩展名部分，转换为小写字母，包含点(.)。
	 * 如传入文件名：“test.jpg”，返回“.jpg”。
	 * @param sFileName
	 * @return 带点号的扩展名，如果没有扩展名，则返回""
	 */
	public static String getExtend(String sFileName) {
		if (CheckUtil.isNullorEmpty(sFileName)) return "";
		int iPos = sFileName.lastIndexOf('.');
		if (iPos < 0) return "";
		return lastIndexOfSeparator(sFileName) > iPos ? "" : (sFileName.substring(iPos).toLowerCase());
	}

	/**
	 * 获取文件路径名（排除扩展名部分），如果有路径部分，保留路径。
	 * 如传入文件名：“test.jpg”，返回“test”；“%%jobf%/2019/test.jpg”返回“%%jobf%/2019/test”。
	 * @param sFileName
	 * @return
	 */
	public static String getFilePathName(String sFileName) {
		if (CheckUtil.isNullorEmpty(sFileName)) return "";
		int iPos = sFileName.lastIndexOf('.');
		if (iPos < 0) return sFileName;
		return lastIndexOfSeparator(sFileName) > iPos ? sFileName : (sFileName.substring(0, iPos));
	}

	/**
	 * 获取文件名的最后一部分的名称，即不包含路径(支持/或\分隔）。
	 * 如传入文件名：“test.jpg”，返回“test”；“%%jobf%/2019/test.jpg”返回“test”。
	 * @param sFileName
	 * @return
	 */
	public static String getOnlyFileName(String sFileName) {
		return getOnlyFileName(sFileName, true);
	}

	/**
	 * 获取文件名的最后一部分的名称，即不包含路径(支持/或\分隔）。
	 * 如传入文件名：“test.jpg”，若removeExt=true，返回“test”，removeExt=false，返回test.jpg；<br>
	 * “%%jobf%/2019/test.jpg”，若removeExt=true，返回“test”，removeExt=false，返回test.jpg。
	 * @param sFileName
	 * @param removeExt 是否移除扩展名
	 * @return
	 */
	public static String getOnlyFileName(String sFileName, boolean removeExt) {
		if (CheckUtil.isNullorEmpty(sFileName)) return "";

		int pathPos = lastIndexOfSeparator(sFileName);
		if (removeExt) {
			int iPos = sFileName.lastIndexOf('.');
			if (iPos >= 0 && iPos > pathPos) sFileName = sFileName.substring(0, iPos);
		}

		if (pathPos < 0) return sFileName;
		return sFileName.substring(pathPos + 1);
	}

	/**
	 * 获取指定文件路径中最后一个分隔符位置，可用于获取排除文件名的目录
	 * @param filePath
	 * @return
	 */
	public static int lastIndexOfSeparator(String filePath) {
		int iPos = filePath.lastIndexOf('/');
		int temp = filePath.lastIndexOf('\\');
		if (temp > iPos) iPos = temp;

		return iPos;
	}

	/**
	 * 判断扩展名是否为图片类型的扩展名(.png,.jpg,.jpeg,.gif,.bmp)
	 * @param ext 要判断的扩展名
	 * @return
	 */
	public static boolean isImageExtName(String ext) {
		if (!CheckUtil.isNullorEmpty(ext) && ext.length() < 8) {
			if (ext.charAt(0) == '.') {
				ext = ext.substring(1);
			}
			return IMAGE_TYPE.contains(ext.toLowerCase());
		}
		return false;
	}

	/**
	 * 创建目录
	 * LXY
	 * @param destDirName
	 * @return
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists() && dir.isDirectory()) {// 判断目录是否存在
			return true;
		}
		return dir.mkdirs();
	}

	/**
	 * 基于扩展名获得mime类型串（如：image/jpeg）
	 * @param sExt
	 * @return mime类型串，如果sExt为空，返回“application/octet-stream”；如果无法识别，返回"application/octet-stream"；
	 */
	public static String getMimeFromExt(String sExt) {
		String sMime = "application/octet-stream";
		if (CheckUtil.isNullorEmpty(sExt)) {
			return sMime;
		}

		sExt = sExt.toLowerCase();
		if (sExt.charAt(0) == '.') sExt = sExt.substring(1);

		switch (sExt) {
			case "jpg":
			case "jpe":
				sMime = "image/jpeg";
				break;
			case "png":
			case "gif":
				sMime = "image/" + sExt;
				break;
			case "txt":
				sMime = "text/plain;charset=utf-8";
				break;
			case "mp3":
				sMime = "audio/mpeg";
				break;
			case "ogg":
			case "wav":
				sMime = "audio/" + sExt;
				break;
			case "mp4":
			case "webm":
				sMime = "video/" + sExt;
				break;
			case "ogv":
				sMime = "video/ogg";
				break;
			case "doc":
			case "docx":
				sMime = "application/msword";
				break;
			case "xls":
			case "xlsx":
				sMime = "application/msexcel";
				break;
			case "ppt":
			case "pptx":
				sMime = "application/mspowerpoint";
				break;
			default:
				break;
		}
		return sMime;
	}

	/**
	 * 判断并返回绝对地址的://分隔符位置。如果不是绝对地址，返回-1。
	 * 考虑了地址中参数有://分隔符的情况。
	 * @param url 不能为null
	 * @return
	 */
	public static int absoluteUrlSplitIndex(String url)
	{
		return url.lastIndexOf("://", SCHEME_SPLIT_MAXPOS);
	}

	/**
	 * 判断地址是否为网络地址。以ftp://、http://或https://开头则返回非0值
	 * @param path
	 * @return path为空返回-1。
	 * 以http://开头返回1，以https://开头返回2。
	 * 以ftp:// 开头返回10。
	 * 以mongo://开头返回20
	 * 其它值返回0
	 */
	public static int getFilePathType(String path) {
		if (CheckUtil.isNullorEmpty(path)) {
			return FilePathTypeEnum.EMPTY.getCode();
		}
		int iPos = absoluteUrlSplitIndex(path);
		if (iPos < 0) {
			return FilePathTypeEnum.LOCALDISK.getCode();
		}

		String prefix = path.substring(0, iPos).toLowerCase();
		return FilePathTypeEnum.getCodeByType(prefix);
	}

	/**
	 * 查找URL地址的路径开始位置。
	 * 如果为null或Empty，返回-1；
	 * 如果不包含服务器地址，返回0；
	 * 否则返回除服务器地址后的第一个路径位置(/)，如果仅服务器地址且无查询参数(?)，返回字符串长度，如果有?则返回?位置。<br/>
	 * 比如以下情况都返回23（即服务器地址后的第一个字符位置）：<br/>
	 * http://192.168.1.1:8080/mainWeb/work<br/>
	 * http://192.168.1.1:8080<br/>
	 * http://192.168.1.1:8080?name=test
	 * @param url
	 * @return
	 */
	public static int indexOfPath(String url) {
		if (CheckUtil.isNullorEmpty(url)) return -1;
		int iPos = absoluteUrlSplitIndex(url);
		if (iPos < 0) return 0;
		int iquery = url.indexOf('?');
		int inx = url.indexOf('/', iPos + 3);
		if (inx < 0) inx = iquery;
		if (inx < 0) inx = url.length();
		return inx;
	}

	/**
	 * 返回端口号所在开始位置（端口号前的:所在位置），如果没有端口号，返回-1。
	 * @param host 要查找的主机名，兼容有或没有scheme，但不能有路径或查询参数部分。以下格式有效：<br>
	 * 192.168.1.10:8000、http://192.168.1.10、[240e:e0::abcd]、https://[240e:e0::abcd]:8080等。<br>
	 * 但如果传入有路径或查询参数部分的地址时，会返回不正确的结果。<br>
	 * 如传入：http://192.168.1.10?callback=http://192.168.1.10:80<br>
	 * 将会返回：33，即callback=http后面的位置
	 * @return
	 */
	public static int indexOfPort(String host)
	{
		int ipEnd=host.indexOf(']')+1; //兼容ipv6格式的地址：[240e:e0::abcd]:8080
		if(ipEnd<1)
			ipEnd = absoluteUrlSplitIndex(host)+1;
		return host.indexOf(':',ipEnd);
	}

	/**
	 * 拼接路径，会自动处理连接处的 / 或 \ 分隔符。
	 * 注：不管内容是 /  还是 \，在拼接时总使用 / （因为/ 兼容性更好），但原内容中有的分隔符不处理。<br>
	 * 如果前一个字符串尾和下一个字符串头都有分隔符，则只保留后一个字符串的分隔符。<br>
	 * 如：["c:\\", "test/", "\\files", "good.txt"] 拼接为：c:\test\files/good.txt<br>
	 * ["http://cas/cas/", "/login"] 拼接为：http://cas/cas/login<br>
	 * ["http://cas/cas", "/login"] 拼接为：http://cas/cas/login<br>
	 * ["http://cas/cas", "login"] 拼接为：http://cas/cas/login<br>
	 * @param paths 要拼接的路径
	 * @return
	 */
	public static String combinePath(String... paths)
	{
		StringBuilder fullPath=new StringBuilder(1024);
		if(paths!=null) {
			char chEnd=0;
			for(String pt : paths) {
				if(CheckUtil.isNullorEmpty(pt)) continue;
				char chx=pt.charAt(0);
				if(chx=='/' || chx=='\\') {
					if(chEnd=='/' || chEnd=='\\') {
						fullPath.deleteCharAt(fullPath.length()-1);
					}
				}else if(chEnd!=0 && chEnd!='/' && chEnd!='\\') {
					fullPath.append('/');
				}
				fullPath.append(pt);

				chEnd=fullPath.charAt(fullPath.length()-1);
			}
		}

		return fullPath.toString();
	}

	/**
	 * 以utf-8的编码写入数据到指定目录的指定名称的文件
	 * @param writeFile 指定写入的文件，若目录不存在则自动创建
	 * @param writeData 需要写入的数据
	 * @throws IOException
	 * @deprecated 使用org.apache.commons.io.FileUtils#write(file, data, chartset)代替
	 */
	@Deprecated
	public static void writeFileToPath(String writeFile, String writeData) throws IOException {
		int lastIndex = lastIndexOfSeparator(writeFile);
		createDir(writeFile.substring(0, lastIndex));

		File file = new File(writeFile);
		BufferedOutputStream buff = null;
		try {
			buff = new BufferedOutputStream(new FileOutputStream(file));
			buff.write(writeData.getBytes(StandardCharsets.UTF_8));
			buff.flush();
		} finally {
			tryCloseStream(buff);
		}
	}

	/**
	 * 重构字符流文件的读文件，并根据对应的编码转换为对应的字符串。
	 * @param filePath 文件路径
	 * @param encodeType 编码类型（空，默认为utf-8）
	 * @return
	 * @throws IOException
	 * @deprecated 使用org.apache.commons.io.FileUtils#readFileToString(file, chartset)代替
	 * */
	@Deprecated
	public static String file2String(String filePath, String encodeType) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}

		BufferedReader br = null;
		StringBuilder result = new StringBuilder();
		try {
			if (CheckUtil.isNullorEmpty(encodeType)) {
				encodeType = "UTF-8";
			}
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encodeType));
			int chr = 0;
			while ((chr = br.read()) >= 0) {
				result.append((char) chr);
			}
		} finally {
			FileUtil.tryCloseStream(br);
		}
		return result.toString();
	}

	/**
	 * 从指定路径的文件中以utf-8编码方式读取内容
	 * @param filePath 指定路径下的文件
	 * @return 指定路径的文件内容
	 * @throws IOException
	 * @deprecated 使用org.apache.commons.io.FileUtils#readFileToString(file, chartset)代替
	 */
	@Deprecated
	public static String file2String(String filePath) throws IOException {
		return file2String(filePath, "UTF-8");
	}

	/**
	 * 将文件(夹)名字转换成安全的名字，{@code /\<>:?*|}这些特殊符会转换为＠符。
	 * 名字后面的.都会移除。最后，如果名字变为空，则返回＠＠
	 * @param fname 不能为空
	 * @return
	 */
	public static String safeFileName(String fname) {
		fname=fname.replaceAll("[/\\\\<>:?*|]", "@");
		int end=fname.length()-1;
		//移除最后的.
		while(end>=0 && fname.charAt(end)=='.') end--;
		if(end<0) return "@@";
		else if(end<fname.length()-1) fname=fname.substring(0,end+1);
		return fname;
	}
	/**
	 * 将文件路径名字转换成安全的名字，{@code <>:?*|} 这些特殊符会转换为＠符。
	 * 名字后面和开头的.都会移除（且以.开头，在移除后，如果变成以/开头时，/也移除）、名字中间的/../也会替换为/。
	 * （其中，路径分隔符\视为/）
	 * 最后，如果名字变为空，则返回＠＠
	 * @param fname 不能为空
	 * @return
	 */
	public static String safeFilePath(String fname) {
		fname=fname.replaceAll("[<>:?*|]", "@")
			.replaceAll("([/\\\\]+\\.*)+[/\\\\]+", "/");

		int inx=fname.length()-1;
		//移除最后的.
		while(inx>=0 && fname.charAt(inx)=='.') inx--;
		if(inx<0) return "@@";
		else if(inx<fname.length()-1) fname=fname.substring(0,inx+1);

		inx=0;
		if(fname.charAt(inx)=='.'){
			char chx;
			while(inx<fname.length() && ((chx=fname.charAt(inx))=='.' || chx=='/' || chx=='\\')){
				inx++;
			}
		}
		if(inx>=fname.length()) return "@@";
		else if(inx>0) fname=fname.substring(inx);
		return fname;
	}
	/**
	 * 判断文件扩展名是否属于指定白名单允许（如果不指定，则不是黑名单内禁止）的扩展名，<br>
	 * 如果是，抛出错误代码为CMM_FILE_UNSAFE_TYPE的错误。<br>
	 * 注意：如果指定了白名单，只判断满足白名单条件（并不会判断黑名单）；
	 * 只有未指定白名单时，才会判断黑名单。<br>
	 * 因此，如果白名单指定了黑名单相同的扩展名，将会判断为允许，不会抛出错误。
	 * @param filePath 包含扩展名的文件名，或直接是扩展名（包含.）
	 * @param whiteFileExt 判断的扩展名白名单，如果为空，则使用默认的黑名单
	 * @return 是白名单扩展名（未设置时，需不是黑名单扩展名），原样返回，否则抛出错误。
	 * @throws ServiceException ErrorType.code为"CMM_FILE_UNSAFE_TYPE"时，说明文件不允许上传。
	 */
	public static String safeWhiteFileName(String filePath,String[] whiteFileExt)
	{
		String sExt=FileUtil.getExtend(filePath).toLowerCase();
		if(sExt.isEmpty())
			sExt="."+filePath;

		boolean rejectFile=false;
		if(CheckUtil.isNullorEmpty(whiteFileExt)) {//默认的黑名单
			rejectFile=CheckUtil.contains(defaultBlackFileExt, sExt);
		}else {//传入的白名单
			rejectFile=!CheckUtil.contains(whiteFileExt, sExt);
		}

		//ExceptionAssertEnum.ServiceError.throwIsTrue(rejectFile, "不允许上传此种类型文件："+filePath);
		if(rejectFile) {
			ServiceException ex= new ServiceException("CMM_FILE_UNSAFE_TYPE", "不允许上传此种类型文件："+sExt, null);

			throw ex;
		}
//	if (rejectFile) {
//		return filePath + "0";
//	}
		return filePath;
	}

	/**
	 * 在最后需要关闭流对象时，调用此方法关闭（内部会屏蔽关闭时产生的异常）
	 * @param handle
	 */
	public static <T extends AutoCloseable> void tryCloseStream(T handle) {
		try {
			if (handle != null) handle.close();
		} catch (Throwable e) {
			//关闭流，如果出错，不做任何处理
		}
	}

	/**
	 * 以锁文件的方式实现多线程同步（最多尝试50次，每次之间间隔500ms）。
	 * 如果在尝试指定次数还未能获取到锁，则直接输出错误码为“"FPA_LOCKFILE_FAIL"”的ServiceException异常
	 * @see #tryLock(FileChannel, int, int)
	 * @param fc 文件对象，由文件流对象(RandomAccessFile/FileOutputStream等)的getChannel得到
	 */
	public static void tryLock(FileChannel fc)
	{
		tryLock(fc,50,500);
	}

	/**
	 * 以锁文件的方式实现多线程同步
	 * @param fc 文件对象，由文件流对象(RandomAccessFile/FileOutputStream等)的getChannel得到
	 * @param count 最多尝试次数，如果在尝试指定次数还未能获取到锁，则直接输出错误码为“"FPA_LOCKFILE_FAIL"”的ServiceException异常
	 * @param delay 每次获取锁失败后，等待下次获取锁的时长(ms)
	 */
	public static void tryLock(FileChannel fc, int count, int delay)
	{
		int tryCount=count;
		FileLock flock=null;
		while (true) { //直到拿到锁为止
			try {
				flock = fc.tryLock();
			}catch(Exception ex){
				flock=null;
			}
			if(flock!=null)
				break;
			if(tryCount-- < 0) {
				throw new ServiceException("FPA_LOCKFILE_FAIL", "获取文件锁尝试"+count+"次后失败，请确认文件是否一直被占用", null);
			}
			try {
				Thread.sleep(delay);
			}catch (Exception ex){
			}
		}
	}

	/**
	 * 删除文件夹及文件内容
	 * @param path 文件路径
	 */
	public static void deleteSource(String path) throws IOException {
		Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * 检查路径里是否包含访问上级目录（../）的部分
	 * @param path 宏路径（或一般路径）
	 * @return 包含：true 否则false
	 */
	public static boolean inspectPath(String path) {
		if (CheckUtil.isNullorEmpty(path)) {
			return false;
		}
		//“../”开头 或中间“/../” 或 “/..”结尾 或 至少两个且全为.
		return path.matches("(^|.*[/\\\\])\\.{2,}($|[/\\\\].*)");
	}

}
