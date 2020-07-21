package personal.wrui.test.test_fillters;

import java.util.UUID;

import personal.wrui.test.test_fillters.utils.ThreadLocalUtils;


public abstract class BaseFilter{

	public static final String  KEY_SEQID="filters.seq.key";
	public static final String  KEY_PROJECTANDMODULENAME="filters.seq.projectandmodulename";
	
	public static final String getNewSqId() {
		String sqlId=UUID.randomUUID().toString();
		ThreadLocalUtils.setSequenceId(sqlId);
		return sqlId;
	}
	public static final String getProjectAndModuleName() {
		String projectModuleName=null;
		try{
			String projectName = System.getenv("AE_PROJECT");
			 if (projectName == null) {
	             projectName = System.getProperty("ae.project", "unknown");
	         }
	         String moduleName = System.getenv("AE_MODULE");
	         if (moduleName == null) {
	             moduleName = System.getProperty("ae.module", "unknown");
	         }
	         projectModuleName = "projectName:"+projectName+";moduleName:"+moduleName;
		}catch (Exception e) {
		}
		return projectModuleName;
	}
	
}
