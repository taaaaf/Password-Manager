import java.io.Serializable;
import java.util.ArrayList;

public class UserData implements Serializable{

	
	private static final long serialVersionUID = 1L;
	public UserData(){
	;
		userRecords = new ArrayList<>();
	}
	
	public ArrayList<UserRecord> userRecords;
	
	
}
