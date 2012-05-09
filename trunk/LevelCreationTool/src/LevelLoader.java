import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;


public class LevelLoader {

	private static final File levelLocation = new File("levels/");

	public static ArrayList<String> loadLevel(int number) {
		ArrayList<String> levelStack = new ArrayList<String>();
		try {
			String[] allLevels = levelLocation.list(new FilenameFilter() {
				@Override
				public boolean accept(File f, String s) {
					return true;
				}
			});
			
			File levelSelection = null;
			for(String name : allLevels) {
				if(Integer.parseInt(name.substring(6).replace(".level", ""))==number) {
					levelSelection = new File("levels/Level-"+number+".level");
					break;
				}
			}

			Scanner sc = new Scanner(levelSelection);
			while(sc.hasNextLine()) {
				levelStack.add(sc.nextLine().replace(".java", ""));
			}
			
			return levelStack;
		} catch (Exception e) {
			return null;
		}
	}

}
