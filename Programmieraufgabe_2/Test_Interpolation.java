import java.util.Arrays;

public class Test_Interpolation extends NewtonPolynom{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testNewton();
		//testSplines();
	}

	private static void testNewton() {
		/*double[] x = {0,1};
		double[] y = {3,0};
		NewtonPolynom p = new NewtonPolynom(x, y);
		p.computeCoefficients(y);
		p.addSamplingPoint(2, 1);
		p.addSamplingPoint(1.5,0);*/
		double[] x = { -1, 1, 3 };
		double[] y = { -3, 1, -3 };
//		double[] x = { 0, 1, 2 };
//		double[] y = { 3, 0, 1 };
		NewtonPolynom p = new NewtonPolynom(x, y);
		double[] coefs = p.getCoefficients();
		for(int i=0;i<coefs.length;i++)	{
			System.out.println(coefs[i]+" ");

		}

		System.out.println(p.evaluate(0) + " sollte sein: 0.0");
		System.out.println("-------------------------------");
	}

	public static void testSplines() {
		CubicSpline spl = new CubicSpline();
		double[] y = { 2, 0, 2, 3 };
		spl.init(-1, 2, 3, y);
		spl.setBoundaryConditions(9, 0);
		System.out.println(Arrays.toString(spl.getDerivatives())
				+ " sollte sein: [9.0, -3.0, 3.0, 0.0].");
	}
}