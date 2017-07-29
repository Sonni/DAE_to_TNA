import org.lwjgl.util.vector.Matrix4f;


public class maths
{

	public static Matrix4f toMatrix(float[] ta)
	{
		Matrix4f m = new Matrix4f();
		m.m00 = ta[0]; m.m10 = ta[1]; m.m20 = ta[2]; m.m30 = ta[3];
		m.m01 = ta[8]; m.m11 = ta[9]; m.m21 = ta[10]; m.m31 = ta[7];
		m.m02 = ta[4]; m.m12 = ta[5]; m.m22 = ta[6]; m.m32 = ta[11];
		m.m03 = ta[12]; m.m13 = ta[13]; m.m23 = ta[14]; m.m33 = ta[15];

		return m;
	}

    public static Matrix4f rotate90Degrees(Matrix4f matrix) {


					Matrix4f translate = extractTranslationMatrix(matrix);
					double[] axisAngleRotated = matrixToRotatedAxisAngle(matrix);
					Matrix4f rotated = axisAngleToMatrix(axisAngleRotated);
					rotated.m30 = -translate.m30;
					rotated.m31 = translate.m32;
					rotated.m32 = -translate.m31;
					return rotated;
		}

		private static double[] matrixToRotatedAxisAngle(Matrix4f frameMatrix) {
			double[] axisAngle = new double[4];

			if ((Math.abs(frameMatrix.m01 + frameMatrix.m10) < 0.001)
					&& (Math.abs(frameMatrix.m20 + frameMatrix.m02) < 0.001)
					&& (Math.abs(frameMatrix.m12 + frameMatrix.m21) < 0.001)
					&& (Math.abs(frameMatrix.m00 + frameMatrix.m11 + frameMatrix.m22 - 3) < 0.001)) {
				return new double[] { 0, 1, 0, 0 };
			}

			axisAngle[0] = Math.acos((frameMatrix.m00 + frameMatrix.m11 + frameMatrix.m22 - 1) / 2);
			axisAngle[1] = -(frameMatrix.m12 - frameMatrix.m21)
					/ Math.sqrt(Math.pow((frameMatrix.m12 - frameMatrix.m21), 2)
							+ Math.pow((frameMatrix.m20 - frameMatrix.m02), 2)
							+ Math.pow((frameMatrix.m01 - frameMatrix.m10), 2));
			axisAngle[3] = ((frameMatrix.m20 - frameMatrix.m02) / Math.sqrt(Math.pow(
					(frameMatrix.m12 - frameMatrix.m21), 2)
					+ Math.pow((frameMatrix.m20 - frameMatrix.m02), 2)
					+ Math.pow((frameMatrix.m01 - frameMatrix.m10), 2)));
			axisAngle[2] = -(frameMatrix.m01 - frameMatrix.m10)
					/ Math.sqrt(Math.pow((frameMatrix.m12 - frameMatrix.m21), 2)
							+ Math.pow((frameMatrix.m20 - frameMatrix.m02), 2)
							+ Math.pow((frameMatrix.m01 - frameMatrix.m10), 2));
			return axisAngle;
		}

		private static Matrix4f axisAngleToMatrix(double[] axisAngle) {
			Matrix4f matrix = new Matrix4f();
			double c = Math.cos(axisAngle[0]);
			double s = Math.sin(axisAngle[0]);
			double t = 1 - c;
			double x = axisAngle[1];
			double y = axisAngle[2];
			double z = axisAngle[3];
			matrix.m00 = (float) (t * x * x + c);
			matrix.m11 = (float) (t * y * y + c);
			matrix.m22 = (float) (t * z * z + c);
			matrix.m10 = (float) (t * x * y - z * s);
			matrix.m20 = (float) (t * x * z + y * s);
			matrix.m01 = (float) (t * x * y + z * s);
			matrix.m02 = (float) (t * x * z - y * s);
			matrix.m21 = (float) (t * y * z - x * s);
			matrix.m12 = (float) (t * y * z + x * s);
			return matrix;
		}


		private static Matrix4f extractTranslationMatrix(Matrix4f matrix) {
			Matrix4f translation = new Matrix4f();
			translation.m30 = matrix.m30;
			translation.m31 = matrix.m31;
			translation.m32 = matrix.m32;
			return translation;
		}



	}

