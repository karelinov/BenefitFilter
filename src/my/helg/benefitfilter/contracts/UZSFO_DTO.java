package my.helg.benefitfilter.contracts;

/*
 * Класс данных с фактом ЮЗСФО ФЛ
 */
public class UZSFO_DTO {
	public int uszfo_pk;		// ЮЗСФО
	public boolean uszfo_state;	// Результат
	public String uszfo_value;	// Данные

	public UZSFO_DTO(int uszfo_pk, boolean uszfo_state, String uszfo_value) {
		this.uszfo_pk = uszfo_pk;
		this.uszfo_state = uszfo_state;
		this.uszfo_value = uszfo_value; 
	}
}

