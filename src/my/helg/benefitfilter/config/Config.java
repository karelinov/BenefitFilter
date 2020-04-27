package my.helg.benefitfilter.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	private static Properties AppConfig = null;

	public static Properties getAppConfig() {
		
		if (AppConfig == null) {
			AppConfig = new Properties();
			try {
				String currentClassPath = new File(Config.class.getResource("Config.class").getPath()).getParent();
				AppConfig.load(new FileInputStream(currentClassPath.toString() + "\\app.config"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return AppConfig;
	}

}
