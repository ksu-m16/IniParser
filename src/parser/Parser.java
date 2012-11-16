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

	private Map<String, String> paramsMap;

	public static void main(String[] args) throws IOException {

		Parser p = new Parser();

		p.load("e:\\MyDocuments\\My\\tm\\test.ini");
		p.append("e:\\MyDocuments\\My\\tm\\test1.ini");

		for (Map.Entry<String, String> e : p.paramsMap.entrySet()) {
			System.out.println(e.getKey() + "&" + e.getValue());
		}

		String s = p.get("parameter1");
		System.out.println("result");
		System.out.println(s);

		// Формат ini:
		//
		// parameter1=value1
		// parameter2=value2 ;comment
		// ;comment
		//
		// parameter3=val=ue3
		//

	}

	public void load(String fileName) throws IOException {

		paramsMap = new TreeMap<String, String>();

		append(fileName);
	}

	public void append(String fileName) throws IOException {
		BufferedReader r = null;

		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(fileName))));

			String s = "";
			List<String> text = new LinkedList<String>();
			while ((s = r.readLine()) != null) {
				text.add(s);
			}
			parseParams(text);
		}

		finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public String get(String name) {
		return paramsMap.get(name);
	}

	private void parseParams(List<String> text) {

		for (String s : text) {
			s = cutComments(s);

			if (s.trim().length() == 0) {
				continue;
			}

			int separatorIndex = s.indexOf('=');
			if (separatorIndex == -1) {
				throw new IllegalArgumentException("Invalid parameter found: "
						+ s);
			}

			paramsMap.put(s.substring(0, separatorIndex).trim(),
					s.substring(separatorIndex + 1).trim());

		}
	}

	private String cutComments(String s) {
		if (s.indexOf(';') == -1) {
			return s;
		}
		return s.substring(0, s.indexOf(';'));

	}

}
