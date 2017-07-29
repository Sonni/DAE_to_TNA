
public class Material {
	
	String name;
	String ambient;
	String diffuse;
	String specular;
	float transparent;
	float shine;
	float refract;
	String texPath;
	
	public Material() {
		name = null;
		ambient = null;
		diffuse = null;
		specular = null;
		transparent = 1;
		shine = 50;
		refract = 1;
		texPath = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmbient() {
		return ambient;
	}

	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}

	public String getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(String diffuse) {
		this.diffuse = diffuse;
	}

	public String getSpecular() {
		return specular;
	}

	public void setSpecular(String specular) {
		this.specular = specular;
	}

	public float getTransperant() {
		return transparent;
	}

	public void setTransparent(float transparent) {
		this.transparent = transparent;
	}

	public float getShine() {
		return shine;
	}

	public void setShine(float shine) {
		this.shine = shine;
	}

	public float getRefract() {
		return refract;
	}

	public void setRefract(float refract) {
		this.refract = refract;
	}

	public String getTexPath() {
		return texPath;
	}

	public void setTexPath(String texPath) {
		this.texPath = texPath;
	}

}
