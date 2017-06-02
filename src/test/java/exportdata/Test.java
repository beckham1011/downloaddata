package exportdata;

public class Test {
	public static void main(String[] args) {

		String s = "shdfksahj$sjdjfad$weipr2378947$efu" ;
		
		String[] str = s.split("\\$") ;
		for(String sss : str){
			System.out.println(sss);
		}
		
	}
}
