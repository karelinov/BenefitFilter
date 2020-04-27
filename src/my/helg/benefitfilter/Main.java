package my.helg.benefitfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.helg.benefitfilter.ICheckCriteria.CheckCriteria_boolean;
import my.helg.benefitfilter.config.Config;
import my.helg.benefitfilter.contracts.ExecResult;
import my.helg.benefitfilter.contracts.UZSFO_IDATA;
import my.helg.benefitfilter.utils.InputDataBuilder;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		// задаём путь к файлу
		String inputDir = Config.getAppConfig().getProperty("inputdir");
		String inputFilePath = inputDir+"\\inputdata.txt";
		// Получаем данные для проверки
		ExecResult<UZSFO_IDATA> inputDataResult = InputDataBuilder.GetDataFromFile(inputFilePath);
		if (inputDataResult.ErrorCode !=0) {
			System.out.println("get data result ="+inputDataResult.ErrorCode+" "+inputDataResult.ErrorMessage);
			return;
		}
		logger.info("GetDataFromFile Done");
		
		// Создаём объект проверки данных
		CheckExecutor executor = new CheckExecutor();
		// Загружаем конфигурацию проверки
		ExecResult<Void> loadResult = executor.LoadCheckConfiguration(inputDir+"\\XML_UZSFO.BEN.CRITERIA.xml");
		if (loadResult.ErrorCode !=0) {
			logger.info("LoadCheckConfiguration failed {} , {}",inputDataResult.ErrorCode,inputDataResult.ErrorMessage);
			return;
		}
		logger.info("LoadCheckConfiguration Done");
		
		// Запускаем проверки
		for (String citizen_pk : inputDataResult.ResultValue.keySet()) {  
			ExecResult<Boolean> checkResult = executor.CheckData(inputDataResult.ResultValue.get(citizen_pk));
			if (checkResult.ErrorCode !=0) {
				logger.info("CheckData failed {} , {}",checkResult.ErrorCode,checkResult.ErrorMessage);
				return;
			}
			logger.info("CheckData for citizen_pk {} = {}",citizen_pk,checkResult.ResultValue);
			
		}
		logger.info("LoadCheckConfiguration Done");

		
	}
}
