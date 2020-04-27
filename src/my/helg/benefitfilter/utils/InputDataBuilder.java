package my.helg.benefitfilter.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import my.helg.benefitfilter.contracts.ExecResult;
import my.helg.benefitfilter.contracts.UZSFO_DTO;
import my.helg.benefitfilter.contracts.UZSFO_IDATA;

/*
 * Вспомогательный класс для создания тестового набора данных из файла
 */
public class InputDataBuilder {
	public static ExecResult<UZSFO_IDATA> GetDataFromFile(String FileName) {
		ExecResult<UZSFO_IDATA> result = new ExecResult<>(new UZSFO_IDATA()); // возвращаемый объект результата
		
	    try {
	        String rawData;
	    	
			// Открываем файл данных
		    BufferedReader br = new BufferedReader(new FileReader(FileName));

			while ((rawData = br.readLine()) != null) { // читаем построчно
		        if (rawData.startsWith("//")) continue; // закомментированные строки пропускаем 
		        
		        // Парсим полученную строку, преобразовывая к требуемому формату объекта
		        String[] rawDataArray = rawData.split(",");
		        // создаём объект данных и добавляем к результату
		        UZSFO_DTO data = new UZSFO_DTO(Integer.parseInt(rawDataArray[1]),Boolean.parseBoolean(rawDataArray[2]),rawDataArray[3]);
		        if (!result.ResultValue.containsKey(rawDataArray[0])) { // добавляем элемент ФЛ если его ещё нет
		        	 result.ResultValue.put(rawDataArray[0],new HashMap<Integer,UZSFO_DTO>());	
		        }
		        result.ResultValue.get(rawDataArray[0]).put(data.uszfo_pk,data);
	        
		            
	        }
			br.close();
			
		} catch (Exception e) {
			result.ErrorCode = 1;
			result.ErrorMessage = e.getMessage();
			System.out.println(e.getMessage());
		}
	    
		return result;
	}
}
