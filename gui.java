import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class gui extends JFrame implements ActionListener{

    public static void main(String[] args) {
        new gui();
    }

	JTextArea textArea;
	JScrollPane scrollPane;
	
	encdec ed = new encdec();
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;

	private boolean exists;
	private String password;
	private String fileName;

	public gui() {

		this.exists = false;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,600);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open file...");
		saveItem = new JMenuItem("Save as...");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);		
		saveItem.addActionListener(this);		
		exitItem.addActionListener(this);		
		
		fileMenu.add(openItem); 
		fileMenu.add(saveItem); 
		fileMenu.add(exitItem); 
		menuBar.add(fileMenu);	

		
		this.setJMenuBar(menuBar);
		
		this.add(scrollPane);
		
		this.setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openItem) {
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new File(".")); 
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt"); 
			chooser.setFileFilter(filter); 
			
			int response = chooser.showOpenDialog(null);
			String password = JOptionPane.showInputDialog(null, "geben Sie das Passwort ein");

			if (response == JFileChooser.APPROVE_OPTION) {
				File file = new File(chooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				StringBuilder strb = new StringBuilder("");
				try {
					fileIn = new Scanner(file);
					if (file.isFile()) {
						while (fileIn.hasNextLine()) {
							strb.append(fileIn.nextLine() + "\n");
						}
					}
				} catch (FileNotFoundException e1) {
					System.err.println(e1);
				}
				finally {
					fileIn.close();
				}
				StringBuilder fileName = new StringBuilder(file.getName());
				fileName.delete(fileName.indexOf("."), fileName.length());
				String res = ed.decrypt(strb.toString().trim(), fileName.toString(), password);
				textArea.append(res);
			}
		}
		
		if(e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser(); 
			fileChooser.setCurrentDirectory(new File(".")); 
			
			int response = fileChooser.showSaveDialog(null);  
			String password = JOptionPane.showInputDialog(null, "geben Sie das Passwort ein");
			if(response == JFileChooser.APPROVE_OPTION) { 
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				StringBuilder fileName = new StringBuilder(file.getName());
				fileName.delete(fileName.indexOf("."), fileName.length());			
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(ed.encrypt(textArea.getText(), fileName.toString(), password));
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				}
			}
		}

		if(e.getSource() == exitItem) {
			System.exit(0);
		}
	}

	
}
