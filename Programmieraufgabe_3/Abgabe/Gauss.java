

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		int n = b.length;
		double[] x = new double[n];

		int counter = 0;
		for(int i=n-1;i>=0;i--)	{
			double acc = 0.0;
			for (int j=n-1;j>n-counter;j--)	{
				acc += x[j]*R[i][j];
			}
			x[i] = (b[i] -acc)/R[i][i];
			counter++;
		}
		
		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		int n = b.length;
		//Matrix A unud Vektor b in form A|b bringen -> neue Matrix Ab
		double[][] Ab = new double[n][n+1];
		for (int i=0;i<n;i++)	{
			Ab[i][n] = b[i];
		}
		
		//Pivotsuche und Spaltentausch
		for (int i=0;i<n;i++)	{
			double maxValue = 0.0;
			int maxIndex = 0;
			for (int j=i;j<n;j++)	{
				double possibleMax = Math.abs(Ab[i][j]);
				if (possibleMax > maxValue)	{
					maxValue = possibleMax;
					maxIndex = j;
				}
			}
			//Spalten tauschen
			if (i!=maxIndex)	{
				double[] tmp = A[maxIndex];
				A[maxIndex] = A[i];
				A[i] = tmp;
			}
			
			//Dreiecksform 
			//Multiplikator aik/akk
			for (int k=i+1;k<n;k++)	{
				double multiplicator = Ab[i][k]/Ab[k][k];
				for (int l=k;l<n;l++)	{
					Ab[i][l] = Ab[i][l] - multiplicator*Ab[i][l];
				}
			}
		
			
		}
		//Matrix Ab wieder in Matri A und Vektor b zerlegen
		double[][] Anew = new double[n][n];
		double[] Bnew = new double[n];
		
		for (int i=0;i<n;i++)	{
			for (int j=0;j<n;j++)	{
				Anew[i][j]=Ab[i][j];
				b[i] = Ab[i][n];
			}
		}		
		return backSubst(Anew,Bnew);
	}

	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * 
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung 
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
		//TODO: Diese Methode ist zu implementieren

		return new double[2];
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Gauss-Loesung
	 */
	public static double[] matrixVectorMult(double[][] A, double[] x) {
		int n = A.length;
		int m = x.length;

		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = 0;
			for (int j = 0; j < m; j++) {
				y[i] += A[i][j] * x[j];
			}
		}

		return y;
	}
}
