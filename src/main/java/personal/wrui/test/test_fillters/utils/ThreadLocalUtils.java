package personal.wrui.test.test_fillters.utils;


/**
 * 线程本地变量工具
 * 
 * @author wch
 *
 */
public class ThreadLocalUtils {


	/**
	 * 存储流水号的线程本地变量
	 */
	private final static ThreadLocal<String> SEQUENCE_ID_THREAD_LOCAL = new ThreadLocal<String>();
	private final static ThreadLocal<String> PROJECTANDMODULENAME_THREAD_LOCAL = new ThreadLocal<String>();

	public static void clear() {
		SEQUENCE_ID_THREAD_LOCAL.remove();
		PROJECTANDMODULENAME_THREAD_LOCAL.remove();
	}

	/**
	 * 本地线程变量设置流水号
	 * 
	 * @param sequenceId
	 *            流水号
	 */
	public static void setSequenceId(String sequenceId) {
		SEQUENCE_ID_THREAD_LOCAL.set(sequenceId);
	}

	/**
	 * 获取当前请求线程的流水号
	 */
	public static String getSequenceId() {
		return SEQUENCE_ID_THREAD_LOCAL.get();
	}
	
	/**
	 * 本地线程变量设置ProjectAndModuleName
	 * 
	 * @param projectandmodulename
	 *            流水号
	 */
	public static void setProjectAndModuleName(String projectandmodulename) {
		PROJECTANDMODULENAME_THREAD_LOCAL.set(projectandmodulename);
	}

	/**
	 * 获取当前请求线程的ProjectAndModuleName
	 */
	public static String getProjectAndModuleName() {
		return PROJECTANDMODULENAME_THREAD_LOCAL.get();
	}
	public static void clearProjectAndModuleName() {
		PROJECTANDMODULENAME_THREAD_LOCAL.remove();
	}
}
