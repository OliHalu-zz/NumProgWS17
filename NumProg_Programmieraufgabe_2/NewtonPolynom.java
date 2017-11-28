import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 * 
 * @author braeckle
 * 
 */
public class NewtonPolynom implements InterpolationMethod {

	/** Stuetzstellen xi */
	double[] x;

	/**
	 * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
	 * a2*(x-x0)*(x-x1)+...
	 */
	double[] a;

	/**
	 * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
	 * fuer die Erweiterung der Stuetzstellen benoetigt.
	 */
	double[] f;

	/**
	 * leerer Konstruktore
	 */
	public NewtonPolynom() {
	};

	/**
	 * Konstruktor
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public NewtonPolynom(double[] x, double[] y) {
		this.init(x, y);
	}

	/**
	 * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
	 * Newton-Polynom berechnet.
	 */
	@Override
	public void init(double a, double b, int n, double[] y) {
		x = new double[n + 1];
		double h = (b - a) / n;

		for (int i = 0; i < n + 1; i++) {
			x[i] = a + i * h;
		}
		computeCoefficients(y);
	}

	/**
	 * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
	 * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
	 * ist leer" werden nicht beachtet.
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public void init(double[] x, double[] y) {
		this.x = Arrays.copyOf(x, x.length);
		computeCoefficients(y);
	}

	/**
	 * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
	 * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
	 * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
	 * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
	 * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
	 * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
	 * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
	 * der Erweiterung der Stuetzstellen verwendet werden.
	 * 
	 * Es gilt immer: x und y sind gleich lang.
	 */
	public void computeCoefficients(double[] y) {
		//initialisierung koeff, a und f
		double[] koeff = new double[x.length];
		this.a = new double[x.length];
		this.f = new double[x.length];
		
		//1. Spalte [x_i]f=f(x_i)
		for (int i=0;i<x.length;i++)	{
			koeff[i]=y[i];
		}
		
		//[x_0]f=f(x_0) ist a_0
		this.a[0]=koeff[0];
		this.f[0]=koeff[x.length-1];

		//1. Spalte bearbeitet -> bei i = 1 beginnen
		for (int i=1;i<x.length;i++)	{
			for (int k=0;k<x.length-i;k++)	{
				koeff[k]=(koeff[k+1]-koeff[k])/(this.x[k+i]-this.x[k]);
			}
			//a mit 1. Wert aus jeder Spalte füllen (a[0] schon befüllt)
			this.a[i] = koeff[0];
			this.f[i] = koeff[x.length-i-1];
		}
	}

	/**
	 * Gibt die Koeffizienten des Newton-Polynoms a zurueck
	 */
	public double[] getCoefficients() {
		return a;
	}

	/**
	 * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
	 * zurueck
	 */
	public double[] getDividedDifferences() {
		return f;
	}

	/**
	 * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
	 * hinzu. Daher werden die Membervariablen x, a und f vergoessert und
	 * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
	 * werden, da man den neuen Punkt unten anhaengen und das alte
	 * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
	 * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
	 * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
	 * die Stuetzstellen nicht erweitert.
	 * 
	 * @param x_new
	 *            neue Stuetzstelle
	 * @param y_new
	 *            neuer Stuetzwert
	 */
	public void addSamplingPoint(double x_new, double y_new) {
		//test ob Stürtzstelle bereits vorhanden
		for (int i=0; i<x.length;i++)	{
			if (x[i]==x_new)	{
				return;
			}
		}
		
		//Vergrößerung der Arrays x, a und f
	   /* double[] tmp_x = x.clone();
	    double[] tmp_a = a.clone();
	    double[] tmp_f = f.clone();
	    this.x = new double[tmp_x.length+1];
	    this.a = new double[tmp_a.length+1];
	    this.f = new double[tmp_f.length+1];
	    
	    for (int i=0;i<tmp_x.length;i++)	{
	    	this.x[i]=tmp_x[i];
	    	this.a[i]=tmp_a[i];
	    	this.f[i]=tmp_f[i];
	    }*/
		this.x = Arrays.copyOf(x, x.length+1);
		this.a = Arrays.copyOf(a, a.length+1);
		this.f = Arrays.copyOf(f, f.length+1);

		this.x[x.length-1] = x_new;
	    //y(x_new)=y_new ist neuer koeffizient
	    double tmp = y_new;
	    //f[0]=y_new;
	    for (int i=1;i<x.length;i++)	
	    {
	    	double new_f;
	    	if(i==1)	{
		    	new_f =(tmp - f[i-1])/(x_new-this.x[x.length-1-i]);
	    	} else {
		    	new_f =(f[i-1] - tmp)/(x_new-this.x[x.length-1-i]);

	    	}
	    	tmp = this.f[i];
	    	this.f[i] = new_f;

	    }
	    a[a.length-1]=f[f.length-1];
	    f[0]=y_new;
	}

	/**
	 * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
	 * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
	 * dass die Stuetzstellen nicht leer sind.
	 */
	//@Override
	public double evaluate(double z) {
		/* TODO: diese Methode ist zu implementieren */
		double faktor = this.a[x.length-1];
		double summand;
		for (int i=1;i<x.length;i++)	{
			summand = this.a[x.length-i-1];
			faktor = summand +  ((z-this.x[x.length-i-1])*faktor);

		}
		double result = faktor;
		return result;
	}
}
