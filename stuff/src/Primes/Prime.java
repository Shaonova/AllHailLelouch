
import java.util.Arrays;


public class Prime {
	private int limit;
	private int list[];
	private int count;
	private int testNum;
	private int sq;

	public Prime(int lim) {
	setDefault(lim);
	}
	public void setDefault(int lim){
		limit = lim;
		list = new int[lim];
		list[0] = 2;
		list[1] = 3;
		count = 2;
		testNum = 5;
		sq = (int) Math.sqrt(5);
	}

	public void generate() {
		while (count < limit) {
			if (isPrime()) {
				list[count] = testNum;
				count++;
			}
			testNum += 2;
			sq = (int) Math.sqrt(testNum);
		}
		
	}


	private boolean isPrime() {
		for (int y = 1; y < count && list[y] <= sq; y++)
			if (testNum % list[y] == 0)
				return false;
		return true;
	}

	public static void main(String[] args) {
		
		Prime d = new Prime(1000000);
		//long[] f = new long[15];
		//long b = 2000000000;
		//long e = 0;
		//long avg=0;
		//for(int i=0;i<15;i++){
		System.out.println("Calculating...");
		long a = System.currentTimeMillis();
		d.generate();
		a = System.currentTimeMillis() - a;
		//if (a<b)
		//	b=a;
		//avg+=a;
		//if(a>e)
		//	e=a;
		//f[i]=a;
		System.out.println("It took " + a + " milliseconds to calcuate");
		//d.setDefault(105097565);}
		//System.out.println(Arrays.toString(f));
		//System.out.println("min milliseconds was "+b);
		//System.out.println("max milliseconds was "+e);
		//System.out.println("avg milliseconds was "+avg/15);
		//System.out.println("It took " + ((double) c) / a
			//	+ " times as long to actually print -_-");
	}

}
