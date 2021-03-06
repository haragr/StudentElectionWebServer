package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ElectionConfig {

	public static void main(String[] args) {
		ElectionConfig ec = new ElectionConfig();
		ec.ReadXMLFile();
	}

	public void makeFile() {
		try {
			Element election = new Element("election");
			Document doc = new Document(election);

			doc.getRootElement().addContent(new Element("isVoting").setText("False"));

			Element positions = new Element("positions");
			Element pos = new Element("pos");
			pos.setAttribute("id", "1");
			pos.addContent(new Element("Name").setText("Name of Position"));
			pos.addContent(new Element("maxWinners").setText("1"));
			positions.addContent(pos);
			doc.getRootElement().addContent(positions);

			Element voters = new Element("voters");
			Element vot = new Element("vot");
			vot.setAttribute("id", "1");
			vot.addContent(new Element("prefix").setText("16ucs"));
			vot.addContent(new Element("sufStart").setText("1"));
			vot.addContent(new Element("sufEnd").setText("225"));
			voters.addContent(vot);
			doc.getRootElement().addContent(voters);

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("electionConfig.xml"));

			System.out.println("BlankFile Created!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	public void ReadXMLFile() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("electionConfig.xml");
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			System.out.println("isVoting : " + rootNode.getChildText("isVoting"));

			List votList = rootNode.getChild("voters").getChildren("vot");
			for (int i = 0; i < votList.size(); i++) {
				Element node = (Element) votList.get(i);
				System.out.println("Element " + i + " in voters");
				System.out.println("prefix : " + node.getChildText("prefix"));
				System.out.println("sufStart : " + node.getChildText("sufStart"));
				System.out.println("sufEnd : " + node.getChildText("sufEnd"));
			}

			List posList = rootNode.getChild("positions").getChildren("pos");
			for (int i = 0; i < posList.size(); i++) {
				Element node = (Element) posList.get(i);
				System.out.println("Element " + i + " in positions");
				System.out.println("Name : " + node.getChildText("Name"));
				System.out.println("maxWinners : " + node.getChildText("maxWinners"));
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

}
