import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

public class Bone {
	
	String id;
	String name;
	float[] value;
	String parentId;
	public ArrayList<String> childrenId = new ArrayList<String>();
	
	public Bone() 
	{
		
	}
	
	public float[] flipMatrix(float[] ta)
	{
		float tmp4 = ta[8];
		float tmp8 = ta[4];
		float tmp5 = ta[9];
		float tmp9 = ta[5];
		float tmp6 = ta[10];
		float tmp10 = ta[6];
		float tmp7 = ta[11];
		float tmp11 = ta[7];
		
		ta[4] = tmp4;
		ta[8] = tmp8;
		ta[5] = tmp5;
		ta[9] = tmp9;
		ta[6] = tmp6;
		ta[10] = tmp10;
		ta[7] = tmp7;
		ta[11] = tmp11;
		
		return ta;
	}
	
	public ArrayList<String> getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(String id) {
		
		childrenId.add(id);
	}
	
	public float[] getValue() {
		return value;
	}

	public void setValue(String value) {
		String[] values = value.split(" ");
		this.value = new float[values.length];
		for (int i = 0; i < values.length; i++) this.value[i] = Float.valueOf(values[i]);
		
		Matrix4f mat = maths.toMatrix(this.value);
		Matrix4f m = maths.rotate90Degrees(mat);

		Matrix4f t = new Matrix4f();
		t.m00 = 0.9276494f; t.m10 = -0.2813291f; t.m20 = -0.2456022f; t.m30 = 5.96046e-8f;
		t.m01 = -0.2204689f; t.m11 = 0.118272f; t.m21 = -0.968197f; t.m31 = 2.704464f;
		t.m02 = 0.3014298f; t.m12 =  0.9522951f; t.m22 = 0.04769054f; t.m32 = 0;
		t.m03 = 0; t.m13 = 0; t.m23 = 0; t.m33 = 1;
		Matrix4f ttmp = maths.rotate90Degrees(t);
		System.out.println(ttmp);
		
		 

	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*public void mulBindShape(float[] bindShape)
	{
		Matrix4f bindShapeMat = toMatrix(bindShape);
		Matrix4f jointMatrix = toMatrix(value);
		
		Matrix4f result = Matrix4f.mul(bindShapeMat, jointMatrix, null);
		float[] newValue = toFloatArray(result);
		value = newValue;
	}
*/
}
