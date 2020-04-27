package my.helg.benefitfilter.contracts;


/*
 * Вспомогательный класс для возврата реузльтата операций
 */
public class ExecResult<T> {
	public int ErrorCode = 0;
	public String ErrorMessage;
    public T ResultValue;

    public ExecResult(T ResultValue) {
    	this.ResultValue = ResultValue;
    }

    public ExecResult() {
    	this.ResultValue = null;
    }
    
}
