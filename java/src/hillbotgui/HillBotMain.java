package hillbotgui;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HillBotMain {
	public static void main(String[] args){
		/*try {
			Process p = Runtime.getRuntime().exec("python /Users/mjohansen15/Desktop/pythoncrap/testserver.py");
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/

		HillBotGui window = (new HillBotGui());

		window.start();
		
		try {
			window.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		window.startListeningLoop();
	}
}
