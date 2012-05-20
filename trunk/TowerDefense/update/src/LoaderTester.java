import java.util.ArrayList;


public class LoaderTester {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ArrayList<String> names = LevelLoader.loadLevel(1);
		String classpath = "";
		
		for(String i : names) {
			try {
				//wherever out classpath is       vvv
				Class monsterClass  = Class.forName(classpath + i + ".class");
				//this will error until we decide ^^^
				Object ojb = monsterClass.newInstance(); //the monster has been created (thank you reflection)
				ojb = monsterClass.cast(ojb);            //make it its corect type
			} catch (ClassNotFoundException e) {
				System.out.println("class not found.");
				e.printStackTrace();
				
			} catch (InstantiationException e) {
				System.out.println("monster takes arguements.");
				e.printStackTrace();
				
			} catch (IllegalAccessException e) {
				System.out.println("Y U no have prems?!?!"); //this better not ever happen
				e.printStackTrace();
			}
		    
		}
		
		
	}

}
