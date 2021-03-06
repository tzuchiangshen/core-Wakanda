/**
 * 
 */
package com.wakanda.qa.dataperm;

import static com.wakanda.qa.dataperm.Settings.*;

import java.util.Properties;

/**
 * @author ouissam.gouni@4d.com
 *
 */
public class ErrorCode {

	private static Properties errorCodesp = null;
	public static final int NO_PERM_EXEC = getErrorCode("noPermExec");
	public static final int NO_PERM_READ = getErrorCode("noPermRead");
	public static final int NO_PERM_DELETE = getErrorCode("noPerDelete");
	public static final int CANNOT_SAVE_ENTITY = getErrorCode("cannotSaveEntity");
	
	private static Properties getSettings() {
		if (errorCodesp == null) {
			errorCodesp = getProperties(ErrorCode.class, "errorcodes.properties");
		}
		return errorCodesp;
	}
	
	private static int getErrorCode(String name){
		return Integer.parseInt(getSettings().getProperty(name));
	}
	
}
