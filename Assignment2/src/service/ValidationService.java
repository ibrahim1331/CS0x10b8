package service;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ValidationService {
	
	public boolean validateEmail(String email){
		String emailRegEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailRegEx);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
