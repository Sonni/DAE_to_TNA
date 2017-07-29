import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


public class Parser {
	
	public static void ParseObject(ArrayList<Object> object, ArrayList<Bone> bones, ArrayList<Skinning> skinning, ArrayList<Animation> animation)
	{
		try 
		{
			File file = new File("res/object.txt");
			if (!file.exists())
				file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < object.size(); i++)
			{
				
				boolean hasTransform = false;
				String space = " ";
				float[] ta = object.get(i).getTranslation();
				Matrix4f m = new Matrix4f();

				if(ta.length > 10)
				{
					hasTransform = true;

					m.m00 = ta[0]; m.m10 = ta[1]; m.m20 = ta[2]; m.m30 = ta[3];
					m.m01 = ta[4]; m.m11 = ta[5]; m.m21 = ta[6]; m.m31 = ta[7];
					m.m02 = ta[8]; m.m12 = ta[9]; m.m22 = ta[10]; m.m32 = ta[11];
					m.m03 = ta[12]; m.m13 = ta[13]; m.m23 = ta[14]; m.m33 = ta[15];
				}


				
				bw.write("v: "); //vertex
				float[] vert = object.get(i).getVertex();
				for (int j = 0; j < vert.length; j += 3) 
				{
					Vector4f v = new Vector4f(vert[j], vert[j + 1], vert[j + 2], 1);
					Vector4f r = new Vector4f(v.x, v.y, v.z, 1);
					if (hasTransform) r = Matrix4f.transform(m, v, null);

					if(j + 3 >= vert.length) space = "";
					bw.write(r.x + " " + r.y + " " + r.z  + space);
				}
				bw.newLine();	space = " ";


				bw.write("vt: "); //uvs
				float[] uv = object.get(i).getvTex();
				for (int j = 0; j < uv.length; j += 2) 
				{
					if(j + 2 >= uv.length) space = "";
					bw.write(uv[j] + " " + uv[j + 1] + space);
				}
				bw.newLine();	space = " ";
	

				bw.write("vn: "); // normals
				float[] normal = object.get(i).getNormal();
				for (int j = 0; j < normal.length; j += 3) 
				{
					if(j + 3 >= normal.length) space = "";
					
					bw.write(normal[j] + " " + normal[j + 2] + " " + -normal[j + 1] + space);
				}
				bw.newLine();	space = " ";
			
				bw.write("f: "); //Faces
				int[] face = object.get(i).getFace();
				for (int j = 0; j < face.length; j += 4)
				{
					if(j + 3 >= face.length) space = "";
					bw.write((face[j] + object.get(i).getStackNum()) + " " + face[j + 1] + " " + face[j + 2] + space);					
				}
				bw.newLine();	space = " ";
				
				String result = "";
				
				for (int k = 0; k < object.get(0).getTranslation().length; k++)
				{
					if(k + 1 >= object.get(0).getTranslation().length) space = "";
					result += object.get(0).getTranslation()[k] + space;
				}
				bw.write("bindShape: " + result);	bw.newLine();	space = " ";	result = "";

				//Loading bones
				for(int j = 0; j < bones.size(); j++)
				{
					String children = "";

					for(int n = 0; n < bones.get(j).getChildrenId().size(); n++)
					{
						children += bones.get(j).getChildrenId().get(n) + "/" ;
					}

					float[] values = bones.get(j).getValue();
					String value = ""; space = " ";
					for(int k = 0; k < values.length; k++)
					{
						if(k + 1 >= values.length) space = "";
						value += values[k] + space;
					}
					bw.write("bone: " + bones.get(j).getId() + "/" + bones.get(j).getParentId() + "/" + children + value);
					bw.newLine();	space = " ";
				}


				//Loading skinning
				for(int j = 0; j < skinning.size(); j++)
				{
					result = "";

					for (int k = 0; k < skinning.get(j).getJoint().length; k++)
					{
						if(k + 1 >= skinning.get(j).getJoint().length) space = "";
						result += skinning.get(j).getJoint()[k] + space;
					}
					bw.write("id: " + result);	bw.newLine();	space = " ";	result = "";

					for (int k = 0; k < skinning.get(j).getInvBindMatrix().length; k++)
					{
						if(k + 1 >= skinning.get(j).getInvBindMatrix().length) space = "";
						result += skinning.get(j).getInvBindMatrix()[k] + space;
					}
					bw.write("invBind: " + result);	bw.newLine();	space = " "; result = "";

					for (int k = 0; k < skinning.get(j).getWeight().length; k++)
					{
						if(k + 1 >= skinning.get(j).getWeight().length) space = "";
						result += skinning.get(j).getWeight()[k] + space;

					}
					bw.write("w: " + result);	bw.newLine();	space = " ";	result = "";

					for (int k = 0; k < skinning.get(j).getvCount().length; k++)
					{
						if(k + 1 >= skinning.get(j).getvCount().length) space = "";
						result += skinning.get(j).getvCount()[k] + space;

					}
					bw.write("jcount: " + result);	bw.newLine();	space = " ";

					result = "";
					for (int k = 0; k < skinning.get(j).getV().length; k++)
					{
						if(k + 1 >= skinning.get(j).getV().length) space = "";
						result += skinning.get(j).getV()[k] + space;

					}
					bw.write("j: " + result);	bw.newLine();	space = " ";
				}


				for(int j = 0; j < animation.size(); j++)
				{
					result = ""; float[] time = animation.get(j).getTime();
					for(int k = 0; k < time.length; k++)
					{
						if(k + 1 >= time.length) space = "";
						result += time[k] + space;
					}
					bw.write("t: " + animation.get(j).getName() + "/" + result);	bw.newLine();	space = " ";

					result = ""; float[] animations = animation.get(j).getAnimation();
					for(int k = 0; k < animations.length; k++)
					{
						if(k + 1 >= animations.length) space = "";
						result += animations[k] + space;
					}
					bw.write("a: " + result);	bw.newLine();	space = " ";
				}

			}
			
			
			bw.close();
			System.out.println("The data has been saved!");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("Couldn't save tile data");
		}
	}

    public static void ParseObject(ArrayList<Object> object)
    {
        try
        {
            File file = new File("res/object.txt");
            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < object.size(); i++)
            {

                String space = " ";


                bw.write("v: "); //vertex
                float[] vert = object.get(i).getVertex();
                for (int j = 0; j < vert.length; j += 3)
                {
                    Vector4f v = new Vector4f(vert[j], vert[j + 1], vert[j + 2], 1);
                    Vector4f r = new Vector4f(v.x, v.y, v.z, 1);

                    if(j + 3 >= vert.length) space = "";
                    bw.write(r.x + " " + r.y + " " + r.z  + space);
                }
                bw.newLine();	space = " ";


                bw.write("vt: "); //uvs
                float[] uv = object.get(i).getvTex();
                for (int j = 0; j < uv.length; j += 2)
                {
                    if(j + 2 >= uv.length) space = "";
                    bw.write(uv[j] + " " + uv[j + 1] + space);
                }
                bw.newLine();	space = " ";


                bw.write("vn: "); // normals
                float[] normal = object.get(i).getNormal();
                for (int j = 0; j < normal.length; j += 3)
                {
                    if(j + 3 >= normal.length) space = "";

                    bw.write(normal[j] + " " + normal[j + 2] + " " + -normal[j + 1] + space);
                }
                bw.newLine();	space = " ";

                bw.write("f: "); //Faces
                int[] face = object.get(i).getFace();
                for (int j = 0; j < face.length; j += 3)
                {
                    if(j + 3 >= face.length) space = "";
                    bw.write((face[j] + object.get(i).getStackNum()) + " " + face[j + 1] + " " + face[j + 2] + space);
                }
                bw.newLine();	space = " ";

				/*String result = "";

				for (int k = 0; k < object.get(0).getTranslation().length; k++)
				{
					if(k + 1 >= object.get(0).getTranslation().length) space = "";
					result += object.get(0).getTranslation()[k] + space;
				}
				bw.write("bindShape: " + result);	bw.newLine();	space = " ";	result = "";
				*/


            }


            bw.close();
            System.out.println("The data has been saved!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Couldn't save tile data");
        }
    }
	

}
