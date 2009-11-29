package easymorse.com;

import java.text.MessageFormat;
import java.util.Date;

public class DealDate {

	public String deatTheDate(Date date){
		return MessageFormat.format("{0,date,yyyy年MM月dd日}", date);
		
	}
}
