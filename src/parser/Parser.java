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
	Map<String,String> paramsMap;
	
	public static void main(String[] args) throws IOException {
		
		Parser p = new Parser();
		
		p.load("e:\\MyDocuments\\My\\tm\\test.ini");
		
		
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

	}
	
	public void load(String fileName) throws IOException { 
		text = new LinkedList<String>();
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
		String s = "";

		while ((s = r.readLine()) != null) {
			text.add(s);
		}
		
		paramsMap = parseParams(text);
		
		r.close();
	}
	
	
	public void append(String fileName) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
		String s = "";
		List<String> textToAppend = new LinkedList<String>();
		while ((s = r.readLine()) != null) {
			textToAppend.add(s);
		}
		paramsMap.putAll(parseParams(text));
		
//	m.b. here'e better to do so: (no extra List will be created)
		
//		while ((s = r.readLine()) != null) {
//		text.add(s);
//	}
//	
//	paramsMap = parseParams(text);
		
		r.close();
	}
	
	public String get(String name){
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
				throw new IllegalArgumentException("Invalid parameter found: " + s);
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
