import org.lwjgl.util.vector.Matrix4f;


public class Skinning {
	
	String[] joint;
	float[] weight;
	float[] invBindMatrix;
	int[] vcount;
	int[] v;
	
	public Skinning() {
		
	}
	
	
	
	public float[] getInvBindMatrix() {
		return invBindMatrix;
	}

	public void setInvBindMatrix(String mat) {
		String[] weights = mat.split(" ");
		this.invBindMatrix = new float[weights.length];
		for (int i = 0; i < weights.length; i++) this.invBindMatrix[i] = Float.valueOf(weights[i]);
		
		Matrix4f tmpMat = maths.toMatrix(this.invBindMatrix);
		Matrix4f m = maths.rotate90Degrees(tmpMat);
		//this.invBindMatrix = maths.toFloatArray(m);
	}
	
	public float[] getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		String[] weights = weight.split(" ");
		this.weight = new float[weights.length];
		for (int i = 0; i < weights.length; i++) this.weight[i] = Float.valueOf(weights[i]);
	}
	
	public String[] getJoint() {
		return joint;
	}
	
	public void setJoint(String joint) {
		String[] tmp = joint.split(" ");
		this.joint = new String[tmp.length];
		for(int i = 0; i < tmp.length; i++)
			this.joint[i] = tmp[i];
	}
	
	public int[] getvCount() {
		return vcount;
	}
	
	public void setvCount(String vcount) {
		String[] vcounts = vcount.split(" ");
		this.vcount = new int[vcounts.length];
		for (int i = 0; i < vcounts.length; i++) this.vcount[i] = Integer.valueOf(vcounts[i]);
	}
	
	public int[] getV() {
		return v;
	}
	
	public void setV(String v) {
		String[] vs = v.split(" ");
		this.v = new int[vs.length];
		for (int i = 0; i < vs.length; i++) this.v[i] = Integer.valueOf(vs[i]);
	}

}
