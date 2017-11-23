package dem2k.model;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DBName {
	
	Map<String, String> dbnodes = new TreeMap<>();
	
	public DBName(File f) {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("/config/db");
			
			NodeList nldb = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i=0 ; i< nldb.getLength();i++) {
				Node db = nldb.item(i);
				dbnodes.put(db.getAttributes().getNamedItem("stxt").getNodeValue(), db.getAttributes().getNamedItem("name").getNodeValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getForName(String stxt) {
		return dbnodes.get(stxt);
	}

	public static void main(String[] args) {
		// TODO : relativer pfad : "../.."/...STXT.xml"
		File f = new File("D:/Projekte/GinsterRM_entw/GisterRM_Java-x003222/GisterRM_Java/GinsterRM_DAS/bin/src/generator/STXT.xml");
		
		DBName db = new DBName(f);
		db.getForName("20113");
	}

}
