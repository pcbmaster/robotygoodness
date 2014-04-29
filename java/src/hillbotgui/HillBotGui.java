package hillbotgui;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HillBotGui extends JFrame{
	public final int port = 7877;

	private String ip = "192.168.80.3";
	//private String ip = "";

	public static final int POLLING_INTERVAL_MS = 100;

	private JLabel tempLabel;

	private InfoGetter infoGetter;

	private JPanel mainPanel;

	private boolean keepLooping = true;

	private String values = null;
	//private String values = "[cputemp,50]";

	private HashMap<String, String> dictionary;

	public HillBotGui(){
		setLayout(new FlowLayout());

		dictionary = new HashMap<String, String>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initAndAddComponents();

		pack();

		infoGetter = new InfoGetter(ip, port);
		
		/*addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt){
            	System.out.println("Closing connection");
                closeConnection();
            }
        });*/
	}

	public HashMap<String, String> getDictionary(){
		return dictionary;
	}

	public void start(){
		setVisible(true);
	}

	public void connect() throws IOException{
		infoGetter.connect();
	}

	public void startListeningLoop(){
		(new Thread(new getStuffRunnable())).start();
	}

	public void stopListeningLoop(){
		keepLooping = false;
	}

	public void closeConnection(){
		stopListeningLoop();
		infoGetter.close();
	}

	public void updateDictionary(){
		String[] elements = null;
		String[] keyvalarr = null;
		boolean endsWithSemicolon = false;

		if(values != null){
			endsWithSemicolon = values.contains(";");
			

			if(endsWithSemicolon){
				System.out.println("Got message!");
				System.out.println(values);
				
				elements = sanitizeAndSplit(values);


				for(String keyval : elements){
					keyvalarr = keyval.split(":");
					dictionary.put(keyvalarr[0], keyvalarr[1]);
				}
			}
		}
	}

	private void initAndAddComponents(){
		mainPanel = new JPanel();

		mainPanel.setLayout(new FlowLayout());

		tempLabel = new JLabel("Temp");

		mainPanel.add(tempLabel);

		getContentPane().add(mainPanel);
	}
	
	public String[] sanitizeAndSplit(String values){
		values = values.split(";")[0];
		
		values = values.replace("{", "");
		values = values.replace("'", "");
		values = values.replace("\"", "");
		values = values.replace("\\n", "");
		values = values.replace(" ", "");
		return values.split("}");
	}
	
	private Double sanitizeTemp(String temp){
		temp = temp.split("=")[1];
		temp = temp.split("C")[0];
		
		return Double.valueOf(temp);
	}

	class getStuffRunnable implements Runnable{

		@Override
		public void run() {
			while(keepLooping){
				try {
					Thread.sleep(POLLING_INTERVAL_MS);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					values = infoGetter.getInfo();
					updateDictionary();

					if(dictionary.containsKey("cputemp")){
						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								tempLabel.setText(sanitizeTemp(dictionary.get("cputemp")).toString());
							}
						});
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
