import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document doc;
	private File file;

	public MaterialParser materialParser;
	
	private ArrayList<Object> object = new ArrayList<Object>();
	public ArrayList<Material> materialt = new ArrayList<Material>();
	private ArrayList<String> geoName = new ArrayList<String>();
	private ArrayList<Bone> bone = new ArrayList<Bone>();
	private ArrayList<Skinning> skinning = new ArrayList<Skinning>();
	private ArrayList<Animation> animation = new ArrayList<Animation>();

	private boolean has_animation = true;

	private int id = 0;
	
	public static void main(String[] args) 
	{
		Main main = new Main();
		main.start();
	}

	public Main() { }

	private void init() {
		String path = "res/plane.dae";
		try {
			file = new File(path);
			if (file.exists()) {
				factory = DocumentBuilderFactory.newInstance();
				builder = factory.newDocumentBuilder();
				doc = builder.parse(path);
			} else {
				System.out.println("File at location '" + file
						+ "' was not found.");
				System.exit(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void start()
	{
		init();
		loadObjectName();
		
		loadObject();
		translate();
		loadBones();
		loadSkinning();
		loadAnimation();
		has_animation = false;
		if (has_animation)
			Parser.ParseObject(object, bone, skinning, animation);
		else
			Parser.ParseObject(object);
	}


	private void loadObject()
	{
		NodeList float_array = doc.getElementsByTagName("float_array");

		for (int k = 0; k < geoName.size(); k++) 
		{
			String end = geoName.get(k) + "-mesh-positions-array";

			for (int i = 0; i < float_array.getLength(); ++i) //Loading name, vertices, normals and UVs
			{
				Element element = (Element) float_array.item(i);
				String float_array_id = element.getAttribute("id");

				if (float_array_id.equals(end)) 
				{
					object.add(new Object());
					object.get(k).setName(geoName.get(k));
				}

				if (float_array_id.equals(end))
					object.get(k).setVertex(element.getTextContent());

				if (float_array_id.equals(geoName.get(k) + "-mesh-normals-array")) 
					object.get(k).setNormal(element.getTextContent());
				
				if (float_array_id.equals(geoName.get(k) + "-mesh-map-0-array")) 
					object.get(k).setvTex(element.getTextContent());		
			}
		}
		
		NodeList faces = doc.getElementsByTagName("p");

		for (int i = 0; i < faces.getLength(); i++)  //Loading faces
		{
			Element face = (Element) faces.item(i);
			object.get(0).setFace(face.getTextContent());
		}
		
	}

	private void translate()
	{
		NodeList node = doc.getElementsByTagName("node");
		NodeList translate = doc.getElementsByTagName("translate");
		int check = 0;
		loadTransformFromBindShape();
		/*for (int i = 0; i < node.getLength(); ++i) 
		{
			Element nodes = (Element) node.item(i);
			String names = nodes.getAttribute("name");
			
			for (int j = 0; j < object.size(); j++) 
			{
				if (names.equals(object.get(j).getName())) 
				{				
					Element translation = (Element) node.item(i).getChildNodes().item(1);
					String transContent = translation.getTextContent();
					object.get(j).setTranslation(transContent);
					
					if(object.get(j).getTranslation().length < 10)
					{
						loadTransformFromBindShape();
					}
				}
			}
		}*/
	}
	
	private void loadTransformFromBindShape()
	{
		
		NodeList node = doc.getElementsByTagName("bind_shape_matrix");
		if (node.getLength() == 0)
		{
			has_animation = false;
			return;
		}
		Element transform = (Element) node.item(0);
		for (int j = 0; j < object.size(); j++) 
		{
			String transformationMatrix = transform.getTextContent();
			object.get(j).setTranslation(transformationMatrix);
		}
	}
	
	
	private void loadBones() {
		NodeList skeleton = doc.getElementsByTagName("skeleton");
		NodeList nodes = doc.getElementsByTagName("node");
		String  root = "";
		
		for (int i = 0; i < skeleton.getLength(); i++) 
		{
			Element skeletons = (Element) skeleton.item(i);
			root = skeletons.getTextContent().substring(1);
		}
		String name = "";
		
		for (int j = 0; j < nodes.getLength(); j++) 
		{
			Element node = (Element) nodes.item(j);
			
			if (root.equals(node.getAttribute("id"))) 
			{
				NodeList rootChild = node.getChildNodes();
				int index = 0;
				for (int k = 0; k < rootChild.getLength(); k++) 
				{
					
					Node rootChilds = rootChild.item(k);
					
					if (rootChilds.getNodeType() == Node.ELEMENT_NODE) 
					{
						Element tmpElement = (Element) node;
						name = tmpElement.getAttribute("id");
						
						Element ja = (Element) rootChild.item(k);
						
						
						
						if (!ja.getAttribute("sid").equals("transform")) name = ja.getAttribute("id");
						if (ja.getAttribute("sid").equals("transform")) 
						{
							Element tmp = (Element) ja.getParentNode();
							name = tmp.getAttribute("name");
							
							bone.add(new Bone());
							bone.get(bone.size() - 1).setValue(ja.getTextContent());
							bone.get(bone.size() - 1).setId(name);
							bone.get(bone.size() - 1).setName(root);
							bone.get(bone.size() - 1).setParentId(name);
							id++;
							index = bone.size() - 1;
						} 
						else
						{
							bone.get(index).setChildrenId(ja.getAttribute("id"));
							
							loadChild(rootChilds, bone.get(index).getId());
						}
						
					}
				}		
			}
		}
	}
	
	private void loadChild(Node parent, String parentId)
	{
		NodeList children = parent.getChildNodes();
		if (children.getLength() != 0) 
		{
			int index = 0;
			for (int i = 0; i < children.getLength(); i++) 
			{
				Node child = children.item(i);
				String name = "";
				if (child.getNodeType() == Node.ELEMENT_NODE) 
				{
					
					Element childElement = (Element) children.item(i);
					Element tmpElement = (Element) parent;
					name = tmpElement.getAttribute("id");
					
					
					if (childElement.getAttribute("sid").equals("transform")) 
					{
						Element parentEle = (Element) parent;
						bone.add(new Bone());
						bone.get(bone.size() - 1).setValue(childElement.getTextContent());
						bone.get(bone.size() - 1).setId(name);
						bone.get(bone.size() - 1).setName(parentEle.getAttribute("id"));
						bone.get(bone.size() - 1).setParentId(parentId);
						index = bone.size() - 1;
						id++;
						
					} 
					else 
					{
						bone.get(index).setChildrenId(childElement.getAttribute("id"));
						
						loadChild(child, name);
					}
				}
			}
		}
	}

	private void loadSkinning()
	{
		if (!has_animation)
			return;

		NodeList controller = doc.getElementsByTagName("controller");
		NodeList Name_array = doc.getElementsByTagName("Name_array");
		NodeList float_array = doc.getElementsByTagName("float_array");
		NodeList joints_array = doc.getElementsByTagName("joints");
		Element control = (Element) controller.item(0);
		String name = control.getAttribute("id");
		
		//Loading bone names
		for (int i = 0; i < Name_array.getLength(); i++) 
		{
			Element name_array = (Element) Name_array.item(i);
			if ((name + "-joints-array").equals(name_array.getAttribute("id"))) 
			{
				skinning.add(new Skinning());
				skinning.get(skinning.size() - 1).setJoint(name_array.getTextContent());
			}
		}
		
		//Getting the source for inv bind matrix
		String invBindSource = "";
		for(int i = 0; i < joints_array.getLength(); i++)
		{
			Element childrenElement = (Element) joints_array.item(i);
			NodeList children = childrenElement.getChildNodes();
			
			for(int j = 0; j < children.getLength(); j++)
			{
				Node childNode = children.item(j);
				if(childNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element child = (Element) childNode;
					
				
					if(child.getAttribute("semantic").equals("INV_BIND_MATRIX")) 
						invBindSource = child.getAttribute("source").substring(1);
				}
			}
		}
		
		//Loading weights and inv bind matrix
		for (int i = 0; i < float_array.getLength(); i++) 
		{
			Element floats = (Element) float_array.item(i);
			if (((name + "-weights-array").equals(floats.getAttribute("id")))) 
				skinning.get(skinning.size() - 1).setWeight(floats.getTextContent());
			
			if(floats.getAttribute("id").equals(invBindSource+"-array"))	
				skinning.get(skinning.size() - 1).setInvBindMatrix(floats.getTextContent());
		}
		
		NodeList v = doc.getElementsByTagName("v");
		for (int i = 0; i < v.getLength(); i++) //Loading v (this works becasue it's the last <v> in the document
		{
			Element vs = (Element) v.item(i);
			skinning.get(skinning.size() - 1).setV(vs.getTextContent());
		}
		
		NodeList vertex_weights = doc.getElementsByTagName("vertex_weights");
	
		for (int i = 0; i < vertex_weights.getLength(); i++)  //loading vcount
		{
			Element vertex_weight = (Element) vertex_weights.item(i);
			NodeList children = vertex_weight.getChildNodes();
			
			for (int j = 0; j < children.getLength(); j++) 
			{
				Node child = children.item(j);
				
				if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals("vcount")) 
				{
					Element childEle = (Element) children.item(j);
					skinning.get(skinning.size() - 1).setvCount(childEle.getTextContent());
				}
			}
		}
	}
	
	
	private void loadAnimation()
	{
		NodeList sampler = doc.getElementsByTagName("sampler");
		for (int i = 0; i < sampler.getLength(); ++i) 
		{
			animation.add(new Animation());
			NodeList samplerChildren = sampler.item(i).getChildNodes();

			for(int j = 0; j < samplerChildren.getLength(); j++)
			{
				if (samplerChildren.item(j).getNodeType() == Node.ELEMENT_NODE)
				{
					Element childElement = (Element) samplerChildren.item(j);
					String samplerSource = childElement.getAttribute("source").substring(1);
					String data = "";
					
					NodeList float_array = doc.getElementsByTagName("float_array");
					
					for(int f = 0; f < float_array.getLength(); f++)
					{
						Element float_arrayElement = (Element) float_array.item(f);
						if(float_arrayElement.getAttribute("id").equals(samplerSource + "-array"))
						{
							data = float_arrayElement.getTextContent();
						}
					}
					
					if(childElement.getAttribute("semantic").equals("INPUT")) //If time
					{
						String boneName = childElement.getAttribute("source").substring(10).replace("_pose_matrix-input", "");
						animation.get(animation.size() - 1).setName(boneName);
						animation.get(animation.size() - 1).setTime(data);
					}
					else if(childElement.getAttribute("semantic").equals("OUTPUT")) //If animation
					{
						animation.get(animation.size() - 1).setAnimation(data);
					}
				}
			}
		}
	}
	
	private void loadObjectName()
	{
		NodeList geoNames = doc.getElementsByTagName("geometry");

		for (int i = 0; i < geoNames.getLength(); ++i) 
		{
			Element element = (Element) geoNames.item(i);
			String[] name = element.getAttribute("id").split("-");
			geoName.add(name[0]);
		}
	}
	
}