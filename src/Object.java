import org.lwjgl.util.vector.Matrix4f;


public class Object {
	
	String name;
	float[] vertex;
	float[] normal;
	float[] vTex;
	int[] face;
	float[] translation;
	String material;
	int stackNum;
	
	public Object() {
		stackNum = 0;
		name = null;
		vertex = null;
		normal = null;
		vTex = null;
		face = null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getStackNum() {
		return stackNum;
	}
	
	public void setStackNum(int stackNum) {
		this.stackNum = stackNum;
	}
	
	public float[] getVertex() {
		return vertex;
	}
	
	public void setVertex(int index, float value) {
		vertex[index] = value;
	}

	public void setVertex(String vertex) {
		String[] vertexs = vertex.split(" ");
		this.vertex = new float[vertexs.length];
		for (int i = 0; i < vertexs.length; i++) this.vertex[i] = Float.valueOf(vertexs[i]);
	}

	public float[] getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		String[] normals = normal.split(" ");
		this.normal = new float[normals.length];
		for (int i = 0; i < normals.length; i++) this.normal[i] = Float.valueOf(normals[i]);
	}

	public float[] getvTex() {
		return vTex;
	}

	public void setvTex(String vTex) {
		String[] vTexs = vTex.split(" ");
		this.vTex = new float[vTexs.length];
		for (int i = 0; i < vTexs.length; i++) this.vTex[i] = Float.valueOf(vTexs[i]);
	}

	public int[] getFace() {
		return face;
	}

	public void setFace(String face) {
		String[] faces = face.split(" ");
		int newFaces[] = new int[faces.length];
		for (int i = 0; i < faces.length; i++) newFaces[i] = Integer.valueOf(faces[i]);
		
		if(this.face != null)
		{
		int[] oldFaces = this.face;
		int[] result = new int[newFaces.length + oldFaces.length];
		
		for(int i = 0; i < oldFaces.length; i++)
			result[i] = oldFaces[i];
		
		for(int i = 0; i < newFaces.length; i++)
			result[i+oldFaces.length] = newFaces[i];
		
		this.face = result;
		} else {
			this.face = newFaces;
		}
	}
	
	public float[] getTranslation() {
		return translation;
	}
	
	public void setTranslation(String translation) {
		String[] translations = translation.split(" ");
		this.translation = new float[translations.length];
		for (int i = 0; i < translations.length; i++) this.translation[i] = Float.valueOf(translations[i]);
		
		Matrix4f tmpMat = maths.toMatrix(this.translation);
		Matrix4f m = maths.rotate90Degrees(tmpMat);
		//this.translation = maths.toFloatArray(m);
	}
	
	public String getMaterial() {
		return material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
}
