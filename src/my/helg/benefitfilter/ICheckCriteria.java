package my.helg.benefitfilter;

import java.util.HashMap;

import org.w3c.dom.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.helg.benefitfilter.contracts.ExecResult;
import my.helg.benefitfilter.contracts.UZSFO_DTO;


/***
 * Внутренний класс представляющий условие проверки
 */
interface ICheckCriteria {
	/***
	 * Функция загружает критерий из конфигурационного файла
	 * 
	 * @param criteriaNode
	 * @return
	 */
	public ExecResult<Void> Load(Node criteriaNode);

	/***
	 * Функция проверки условия
	 * 
	 * @param data = список ЮЗСФО
	 * @return
	 */
	public ExecResult<Boolean> Check(HashMap<Integer, UZSFO_DTO> data);
	
	
	/***
	 * Базовый класс реализации ICheckCriteria
	 * @author Helg
	 *
	 */
	public abstract class CheckCriteria_base implements ICheckCriteria {
		public String data_pk;
		public String data_set;
		
		public ExecResult<Void> Load(Node criteriaNode) {
			ExecResult<Void> result = new ExecResult<>();
			try {
				this.data_pk = criteriaNode.getAttributes().getNamedItem("data_pk").toString();
				this.data_set = criteriaNode.getAttributes().getNamedItem("data_set").toString();

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}
		
		public abstract ExecResult<Boolean> Check(HashMap<Integer, UZSFO_DTO> data);
		
	}
	

	/**
	 * Класс для проверки булевых данных
	 * @author Helg
	 *
	 */
	public class CheckCriteria_boolean extends CheckCriteria_base {
		static Logger logger = LoggerFactory.getLogger(CheckCriteria_boolean.class);
		public String compareoperator;
		public Boolean comparevalue;


		public ExecResult<Void> Load(Node criteriaNode) {
			ExecResult<Void> result = new ExecResult<>();
			try {
				ExecResult<Void> parentResult = super.Load(criteriaNode);
				if (parentResult.ErrorCode !=0) throw new Exception(parentResult.ErrorMessage);
				logger.info("super.Load Done, ErrorCode={}",parentResult.ErrorCode);
				
				this.compareoperator = criteriaNode.getAttributes().getNamedItem("compareoperator").toString();
				this.comparevalue = Boolean.parseBoolean(criteriaNode.getAttributes().getNamedItem("comparevalue").toString());
				
				
			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
				logger.error("Exception "+e.getMessage());
			}
			return result;
		}

		public ExecResult<Boolean> Check(HashMap<Integer, UZSFO_DTO> data) {
			ExecResult<Boolean> result = new ExecResult<>();
			try {

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}

	}

	/**
	 * Класс для проверки числовых данных
	 * @author Helg
	 *
	 */
	public class CheckCriteria_number extends CheckCriteria_base {
		public String compareoperator;
		public Boolean comparevalue;

		public ExecResult<Void> Load(Node criteriaNode) {
			ExecResult<Void> result = new ExecResult<>();
			try {
				ExecResult<Void> parentResult = super.Load(criteriaNode);
				if (parentResult.ErrorCode !=0) throw new Exception(parentResult.ErrorMessage);
				
				this.compareoperator = criteriaNode.getAttributes().getNamedItem("compareoperator").toString();
				this.comparevalue = Boolean.parseBoolean(criteriaNode.getAttributes().getNamedItem("comparevalue").toString());
				

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}

		public ExecResult<Boolean> Check(HashMap<Integer, UZSFO_DTO> data) {
			ExecResult<Boolean> result = new ExecResult<>();
			try {

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}

	}

	
	/**
	 * Класс для проверки периодов времени
	 * @author Helg
	 *
	 */
	public class CheckCriteria_timespan extends CheckCriteria_base {
		public String compareoperator;
		public Boolean comparevalue;
		
		
		public ExecResult<Void> Load(Node criteriaNode) {
			ExecResult<Void> result = new ExecResult<>();
			try {
				ExecResult<Void> parentResult = super.Load(criteriaNode);
				if (parentResult.ErrorCode !=0) throw new Exception(parentResult.ErrorMessage);
				
				this.compareoperator = criteriaNode.getAttributes().getNamedItem("compareoperator").toString();
				this.comparevalue = Boolean.parseBoolean(criteriaNode.getAttributes().getNamedItem("comparevalue").toString());
				

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}

		public ExecResult<Boolean> Check(HashMap<Integer, UZSFO_DTO> data) {
			ExecResult<Boolean> result = new ExecResult<>();
			try {

			} catch (Exception e) {
				result.ErrorCode = 1;
				result.ErrorMessage = e.getMessage();
			}
			return result;
		}
	}
}