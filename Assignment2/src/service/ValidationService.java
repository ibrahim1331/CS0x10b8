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
	
	public boolean isInteger(String value){
		String regEx = "\\d+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(value);
		
		return matcher.matches();
	}
	
	public String removeScripts(String inputString){
		String string = inputString;
		System.out.println(string);
		string = string.replaceAll("(?im)<script\\b[^<]*(?:(?!<\\/.*>)[^<]*)*<\\/\\w+>", "");
		string = string.replaceAll("(?im)on\\w+[^<]=\\W*?[\"\"\'\']\\b[^>]*[\"\"\'\']", "");
		string = string.replaceAll("(?im)href\\W*=\\W*[\'\'\"\"]\\W*javascript\\b[^>]*[\'\'\"\"]", "");
		return string;
	}
}
