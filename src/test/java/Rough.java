
public class Rough {

	public static void main(String[] args) {
		System.out.println( System.getProperty("user.dir"));
		String text="Good Luck Steel  (-99.09%)";
	
		 String temp[] = text.split("\\(");
		 System.out.println(temp[0].trim());
		 System.out.println(temp[1].split("\\)")[0]);
		 
	
	
	

	}

}
