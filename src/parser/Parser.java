package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Parser {
	
	List<String> text;
	
	
	public static void main(String[] args) {
		
		Parser p = new Parser();
		try {
			p.load("e:\\MyDocuments\\My\\tm\\test.ini");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s = p.get("parameter3");
		System.out.println("result");
		System.out.println(s);
		
		
//		Формат ini:
//
//		parameter1=value1
//		parameter2=value2 ;comment
//		;comment
//
//		parameter3=val=ue3
//
//		Комментарием считается часть строки после первой точки с запятой.
//		Пустые строки (или состоящие только из пробелов игнорируются)
//		Именем параметра считается часть строки от начала, до первого символа равно
//		Значением параметра считается часть строки после символа равно и до конца
//		строки или ;. Символы перевода строки и ; в значение параметра на входят.

		
		// TODO Auto-generated method stub

	}
	
	public void load(String fileName) throws IOException { //загружает ini
		text = new LinkedList<String>();
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
		String s = "";
		while ((s = r.readLine()) != null) {
			text.add(s);
		}
	}
	
	
	public void append(String fileName) throws IOException { //дополняет существующую конфигурация из другого ini
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
		String s = "";
		while ((s = r.readLine()) != null) {
			text.add(s);
		}
	}
	
	public String get(String name){ //возвращает значение параметра по его имени
		Map<String,String> paramsMap = parseParams(text);
		return paramsMap.get(name);
	}
	
	private Map<String,String> parseParams(List<String> text) {
		Map<String,String> paramsMap = new TreeMap<String,String>();
		for (String s: text) {
			s = cutComments(s);
			
			if (s.trim().length() == 0) {
				continue;
			}
			
			if (s.indexOf('=') == -1) {
				paramsMap.put(s.trim(), "");
				continue;
			}
			paramsMap.put(s.substring(0,s.indexOf('=')).trim(), 
					s.substring(s.indexOf('=') + 1).trim());
			
			
		}
	
		for (Map.Entry<String, String> e : paramsMap.entrySet()) {
			System.out.println(e.getKey() + "&" + e.getValue());
		}
		
		return paramsMap;
	} 
	
	private String cutComments(String s) {
		if (s.indexOf(';') == -1) {
			return s;
		}
		return s.substring(0, s.indexOf(';'));
		
	}
	
	

}
