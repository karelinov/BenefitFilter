package my.helg.benefitfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import my.helg.benefitfilter.contracts.ExecResult;
import my.helg.benefitfilter.contracts.UZSFO_DTO;

/*
 * Класс, собственно проверяющий набор фактов (ЮЗСФО) по списку условий 
 */
public class CheckExecutor {
	static Logger logger = LoggerFactory.getLogger(CheckExecutor.class);
	private CriteriaSet criteriaSet = new CriteriaSet(); 
	
	
	/***
	 * Загружает конфигурацию параметров
	 * @param CheckConfigFile = конфигурационный файл формата XSD_UZSFO.BEN.CRITERIA.xsd
	 * @return /
	 */
	public ExecResult<Void> LoadCheckConfiguration(String CheckConfigFile) {
		ExecResult<Void> result = new ExecResult<>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xmlConfig = dBuilder.parse(CheckConfigFile);			
			xmlConfig.getDocumentElement().normalize();
			
			XPath xPath =  XPathFactory.newInstance().newXPath();
			NamespaceContext namespaceContext = new NamespaceContext() { // создание и загрузка списка NS - ерунда какая-то - и без него работает, а "bc:" не понимает
				public String getNamespaceURI(String prefix) {
		        if (prefix == null) {
		            throw new IllegalArgumentException("No prefix provided!");
		        } else if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX) || prefix.equals("bc")) {
		            return "http://erl.msr.com/schemas/uzsfo/ben.criteria";
		        } else {
		            return XMLConstants.NULL_NS_URI;
		        }
				}
			    public Iterator getPrefixes(String val) {
			        return null;
			    }
			    public String getPrefix(String uri) {
			        return null;
			    }
				
			}; 
			xPath.setNamespaceContext(namespaceContext);

			// Отбираем группы
			NodeList criteriaGroupNodes = (NodeList) xPath.evaluate("CriteriaSetList/CriteriaSet/CriteriaGroup",xmlConfig, XPathConstants.NODESET);
			for (int nodeIndex = 0; nodeIndex < criteriaGroupNodes.getLength(); nodeIndex++) {
				Node criteriaGroupNode = criteriaGroupNodes.item(nodeIndex);
				Node commentNode =criteriaGroupNode.getAttributes().getNamedItem("comment");  
				System.out.println("Group read, comment= " + ((commentNode == null) ? "" : commentNode.getNodeValue()));
				 
				CriteriaGroup checkGroup = new CriteriaGroup(); // создаём группу
				checkGroup.Comment = criteriaGroupNode.getAttributes().getNamedItem("comment").toString();
				
				
				// Отбираем условия
				NodeList сriteriaNodes = (NodeList) criteriaGroupNode.getChildNodes();
				for (int childIndex = 0; childIndex < сriteriaNodes.getLength(); childIndex++) {
					Node criteriaNode = сriteriaNodes.item(childIndex);
					if (! (criteriaNode instanceof Element)) continue; // кроме элементов может отбраться всякое... это всякое пропускаем
					
					String criteriaNodeText = criteriaNode.getTextContent();
					String criteriaNodeName = ((Element)criteriaNode).getNodeName(); // getLocalName() почему то возвращает null
					ICheckCriteria checkCriteria;
					// Создаём объект условия нужного типа
					switch(criteriaNodeName) {
					  case "timespan":
						checkCriteria = new ICheckCriteria.CheckCriteria_timespan();
					    break;
					  case "number":
						checkCriteria = new ICheckCriteria.CheckCriteria_number(); 
					    break;
					  case "bool":
					    checkCriteria = new ICheckCriteria.CheckCriteria_boolean(); 
					    break;
					  case "CriteriaSubGroup":
						    continue; 
					default:
					    throw new Exception("Неизвестный тип критерия "+criteriaNodeName);
					}
					// Создаём объект условия и загружаем его свойства из элемента
				    ExecResult<Void> bcLoadResult = checkCriteria.Load(criteriaNode);
				    if (bcLoadResult.ErrorCode !=0)
				    	throw new Exception(bcLoadResult.ErrorMessage); // если не удалось загрузить - exception
				    // Добавляем условие к группе
				    checkGroup.CheckCriteriaList.add(checkCriteria); 
				
				}
				// Добавляем группу к набору 
				this.criteriaSet.CriteriaGroupList.add(checkGroup);
			}			
			
		}
		catch (Exception e) {
			result.ErrorCode = 1;
			result.ErrorMessage = e.getClass().getName()+" "+e.getMessage();
			logger.error("{}",e.getClass().getName()+" "+e.getMessage());			
		}
		
		
		return result;
	}
	
	/***
	 * Проверяет набор данных согл. конфигурации
	 * @param Data = данные (список ЮЗСФО одного ФЛ)
	 * @return /
	 */
	public ExecResult<Boolean> CheckData(HashMap<Integer,UZSFO_DTO> data) {
		ExecResult<Boolean> result = new ExecResult<>(false);
		
		try {
			
			
			
			
		}
		catch (Exception e) {
			result.ErrorCode = 1;
			result.ErrorMessage = e.getMessage();
			System.out.println(e.getMessage());			
		}
		
		
		return result;
	}
	
	
	/***
	 * Внутренний класс представляющий группу условий
	 */
	class CriteriaSet {
		public ArrayList<CriteriaGroup> CriteriaGroupList = new ArrayList<>();

	}	
	
	
}
	
	
	


