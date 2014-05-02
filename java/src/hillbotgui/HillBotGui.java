package hillbotgui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HillBotGui extends JFrame{
	public final int port = 7877;

	private String ip = "192.168.80.3";
	//private String ip = "";

	private InfoGetter infoGetter;

	private JPanel mainPanel;
	
	private JPanel rawDataPanel;

	private GraphPanel graphsPanel;

	private boolean keepLooping = true;

	private String values = null;
	//private String values = "[cputemp,50]";

	private HashMap<String, String> dictionary;
	private HashMap<String, JLabel> panelDictionary;

	public HillBotGui(){
		setLayout(new FlowLayout());

		dictionary = new HashMap<String, String>();
		panelDictionary = new HashMap<String, JLabel>();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(600,600));

		initAndAddComponents();

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

		if(values != null){

			try {
				elements = sanitizeAndSplit(values);
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			for(String keyval : elements){
				keyvalarr = keyval.split(":");

				if(keyvalarr[0].equals("cputemp")){
					keyvalarr[1] = sanitizeTemp(keyvalarr[1]);
				}

				if(keyvalarr[0].equals("ser")){
					keyvalarr[1] = sanitizeDistanceVals(keyvalarr[1]);
				}


				dictionary.put(keyvalarr[0], keyvalarr[1]);
			}
		}
	}

	private void initAndAddComponents(){
		mainPanel = new JPanel();
		
		rawDataPanel = new JPanel();
		
		rawDataPanel.setLayout(new FlowLayout());

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		graphsPanel = new GraphPanel(200,200);
		
		mainPanel.add(rawDataPanel);

		mainPanel.add(graphsPanel);

		getContentPane().add(mainPanel);
	}

	public String[] sanitizeAndSplit(String values) throws StreamCorruptedException{

		int open = values.length() - values.replace("{", "").length();
		int close = values.length() - values.replace("}", "").length();

		if(!values.contains(";") || (open != close)){
			throw new StreamCorruptedException();
		}

		values = values.split(";")[0];

		values = values.replace("{", "");
		values = values.replace("'", "");
		values = values.replace("\"", "");
		values = values.replace("\\n", "");
		values = values.replace(" ", "");
		return values.split("}");
	}

	private String sanitizeTemp(String temp){
		temp = temp.split("=")[1];
		temp = temp.split("C")[0];

		return temp;
	}

	private String sanitizeDistanceVals(String vals){
		return vals.replace("\\r", "");
	}

	class getStuffRunnable implements Runnable{

		@Override
		public void run() {
			while(keepLooping){
				try {
					values = infoGetter.getInfo();
					updateDictionary();

					for(String loopKey : dictionary.keySet()){
						final String key = loopKey;
						if(!panelDictionary.containsKey(key)){
							panelDictionary.put(key, new JLabel());

							SwingUtilities.invokeLater(new Runnable(){
								public void run(){
									rawDataPanel.add(panelDictionary.get(key));
								}
							});
						}

						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								panelDictionary.get(key).setText(key + ": " + dictionary.get(key));
								
								if(key.equals("ser")){
									String[] vals = dictionary.get(key).split(",");
									int i = 0;
									
									for(String val : vals){
										try{
											graphsPanel.getRect(i).calcScaledHeight(Double.valueOf(val));
										}
										catch (IndexOutOfBoundsException e){
											graphsPanel.addRect(new ScaledRectangle(0, 50, 100));
											graphsPanel.getRect(i).calcScaledHeight(Double.valueOf(val));
										}
										
										i++;
									}
									
									
									graphsPanel.revalidate();
									graphsPanel.repaint();
								}
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
