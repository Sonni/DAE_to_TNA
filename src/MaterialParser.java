import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MaterialParser {

	Document doc;

	public ArrayList<Material> material = new ArrayList<Material>();

	public MaterialParser(Document doc) {
		this.doc = doc;
	}

	public void start() {
		NodeList nameId = doc.getElementsByTagName("effect");

		for (int i = 0; i < nameId.getLength(); ++i) {
			Element labTest = (Element) nameId.item(i);
			String materialName = labTest.getAttribute("id");

			String[] firstName = materialName.split("-");

			material.add(new Material());
			material.get(i).setName(firstName[0]);
		}

		int numCheck = 0;
		NodeList materialNode = doc.getElementsByTagName("color");
		NodeList textureNode = doc.getElementsByTagName("color");

		for (int i = 0; i < materialNode.getLength(); ++i) {
		
			Element materialElement = (Element) materialNode.item(i);
			String material = materialElement.getAttribute("sid");
			
			
			
			if (material.equals("diffuse")) this.material.get(numCheck).setDiffuse(materialElement.getTextContent());
			if (material.equals("ambient")) this.material.get(numCheck).setAmbient(materialElement.getTextContent());
			if (material.equals("specular"))  {
				this.material.get(numCheck).setSpecular(materialElement.getTextContent());
				numCheck++;
			}
			if (material.equals("shininess")) this.material.get(numCheck - 1).setShine(Float.valueOf(materialElement.getTextContent()));
			if (material.equals("index_of_refraction")) this.material.get(numCheck - 1).setRefract(Float.valueOf(materialElement.getTextContent()));
			if (material.equals("transparency")) this.material.get(numCheck - 1).setTransparent(Float.valueOf(materialElement.getTextContent()));
		}
		
		parse();
	}
	
	public void parse() {
		BufferedWriter bw = null;
		try {

			FileWriter fstream = new FileWriter("res/material.txt", true); //true tells to append data.
		    bw = new BufferedWriter(fstream);
		    
		    for (int i = 0; i < material.size(); i++) {
			bw.newLine();
			bw.newLine();
			
			    bw.write("newmtl " + material.get(i).getName());
				bw.newLine();
				bw.write("Ns " + material.get(i).getShine());
				bw.newLine();
				bw.write("Ka " + material.get(i).getAmbient());
				bw.newLine();
				bw.write("Kd " + material.get(i).getDiffuse());
				bw.newLine();
				bw.write("Ks " + material.get(i).getSpecular());
				bw.newLine();
				bw.write("Ni " + material.get(i).getRefract());
				bw.newLine();
				bw.write("d " + material.get(i).getTransperant());
				bw.newLine();
				bw.write("illum " + 2);
	
		    }
				
				bw.close();
	     } catch (IOException e) {
				e.printStackTrace();
				System.out.println("Couldn't parse material");
		}
	}

}
