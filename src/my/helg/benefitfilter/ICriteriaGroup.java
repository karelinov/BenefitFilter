package my.helg.benefitfilter;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.helg.benefitfilter.ICheckCriteria.CheckCriteria_boolean;
import my.helg.benefitfilter.contracts.ExecResult;
import my.helg.benefitfilter.contracts.PersonDataTable;
import my.helg.benefitfilter.contracts.UZSFO_DTO;

/***
 * Внутренний класс представляющий группу условий
 */
interface ICriteriaGroup {
	/**
	 * Опциональный комментарий
	 */
	public String Comment = null;
	/**
	 * Вложенный список объектов ICheckCriteria 
	 */
	public ArrayList<ICheckCriteria> CheckCriteriaList = new ArrayList<>();
	
	/***
	 * Функция загружает группу из конфигурационного файла
	 * 
	 * @param criteriaNode
	 * @return
	 */
	public ExecResult<Void> Load(Node criteriaGroupNode);

	/***
	 * Функция проверки условия
	 * 
	 * @param data = список ЮЗСФО
	 * @return
	 */
	public ExecResult<Boolean> Check(PersonDataTable personDataTable);


	
	
	public class CriteriaGroup implements ICriteriaGroup {
		static Logger logger = LoggerFactory.getLogger(CriteriaGroup.class); 

		public ExecResult<Void> Load(Node criteriaGroupNode) {
			ExecResult<Void> result = new ExecResult<>();
			try {

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
				logger.error("Exception " + e.getMessage());
			}
			return result;
		}		

		@Override
		public ExecResult<Boolean> Check(PersonDataTable personDataTable) {
			ExecResult<Boolean> result = new ExecResult<>();
			try {

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
				logger.error("Exception " + e.getMessage());
			}
			return result;

		}
		
	}
	
}

