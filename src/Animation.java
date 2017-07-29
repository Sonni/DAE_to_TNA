public class Animation {
	
	private float[] time;
	private String name;
	private float[] animation;
	
	public Animation() { }

	public float[] getTime() {
		return time;
	}

	public void setTime(String time) {
		String[] times = time.split(" ");
		this.time = new float[times.length];
		for (int i = 0; i < times.length; i++) this.time[i] = Float.valueOf(times[i]);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float[] getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		String[] animations = animation.split(" ");
		this.animation = new float[animations.length];
		for (int i = 0; i < animations.length; i++) this.animation[i] = Float.valueOf(animations[i]);
		
	}
	
	

}
