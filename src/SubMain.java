

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SubMain extends JFrame {
	public static JButton browse_button = new JButton("Browse for oracle log    ");
	public static JButton convert_button = new JButton("Convert to pdf    ");
	public static JButton view_convert_button = new JButton("View Converted file    ");
	public static JLabel filename_label = new JLabel("Selected file name: ");
	public static JLabel Conv_FileName_label = new JLabel("Converted file name: ");
	public static JLabel Status_label = new JLabel("Status: ");
	public static JLabel filename_text = new JLabel();
	public static JLabel Status_Text_label = new JLabel();
	//public static JLabel Conv_Text_label = new JLabel();
	public static JLabel Conv_FileName = new JLabel();
	JFileChooser chooser = new JFileChooser();
	int conv_state=0;
	int des_name_exist=0;
	String name;
	String Dest_name;
	String File_exist_name;
	File file;
	Timer timer;
	int counter=0;
	int open_pdf=0;
	private static BufferedImage Win_icon;
	
	
	public SubMain() throws IOException {
		 URL url1 = SubMain.class.getResource("/logos/elom_logo.png");
		Win_icon = ImageIO.read(url1);
	//	Win_icon = ImageIO.read(new File(url1));
		setIconImage(Win_icon);
		
		setSize(650, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		
		browse_button.setBounds(30, 30, 200, 30);		
		add(browse_button);
		
		convert_button.setBounds(260, 30, 150, 30);		
		add(convert_button);
		
		view_convert_button.setBounds(430, 30, 200, 30);	
		view_convert_button.setEnabled(false);
		add(view_convert_button);
		
		filename_label.setBounds(60, 80, 150, 30);
		add(filename_label);
		
		filename_text.setBounds(200, 80, 150, 30);
		add(filename_text);
		
		Status_label.setBounds(60, 120, 70, 30);
		add(Status_label);

		Status_Text_label.setBounds(200, 120, 200, 30);
		Status_Text_label.setBackground(Color.gray);
		Status_Text_label.setOpaque(true);
		add(Status_Text_label);
		
		Conv_FileName_label.setBounds(60, 160, 150, 30);
		add(Conv_FileName_label);
		
		Conv_FileName.setBounds(200, 160, 300, 30);
		add(Conv_FileName);
		
		
		
	
		 URL url = SubMain.class.getResource("/logos/open.png");
		// Icon browse_icon = new ImageIcon("C:\\Users\\user\\workspace\\logfile_conv\\logos\\open.png"); 
		 Icon browse_icon = new ImageIcon(url); 
		 browse_button.setIcon(browse_icon);
		 browse_button.setHorizontalTextPosition(SwingConstants.LEFT);
		 
		 
		 
		 URL url2 = SubMain.class.getResource("/logos/convert.png");
		// Icon convert_icon = new ImageIcon("C:\\Users\\user\\workspace\\logfile_conv\\logos\\convert.png"); 
		 Icon convert_icon = new ImageIcon(url2); 
		 convert_button.setIcon(convert_icon);
		 convert_button.setHorizontalTextPosition(SwingConstants.LEFT);
		 
		 
		 
		 URL url3 = SubMain.class.getResource("/logos/view_i.png");
		 Icon view_icon = new ImageIcon(url3); 
		 //Icon view_icon = new ImageIcon("C:\\Users\\user\\workspace\\logfile_conv\\logos\\view_i.png"); 
		 view_convert_button.setIcon(view_icon);
		 view_convert_button.setHorizontalTextPosition(SwingConstants.LEFT);

		
				browse_button.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						
						if (conv_state==9) {
						
							JOptionPane.showMessageDialog(null, "Conversion in progress, Please wait.");
						} else {
							Browse_for_file();
						}
					}
				});
		
		
		
				convert_button.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if (conv_state==1) {
							
							 timer = new Timer(1000, new ActionListener() {					
								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									counter++;
									changeBg();
									
								}
							    });
							
							timer.start();
							  
							 MyTask process = new MyTask();
							process.execute();
						
		
							
						} else if(conv_state==0) {
							JOptionPane.showMessageDialog(null, "Please select a log file");
							Browse_for_file();
						} else {
							JOptionPane.showMessageDialog(null, "Conversion in progress, Please wait.");
						}
						
					}
				});
				
				
				
				view_convert_button.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {	
						if (open_pdf==1) {
							view_pdf();
						}
						
						
					}
				});
				
				
		
	}
			   
			
			
public void changeBg() {
		
			if(counter % 2 == 0) {
				Status_Text_label.setBackground(Color.YELLOW);
				Status_Text_label.setOpaque(true);
				Status_Text_label.setText("   Converting file........");
			
				Status_Text_label.repaint();
			} else {
				//Status_Text_label.setBackground(Color.GRAY);
			//	Status_Text_label.setOpaque(true);
				Status_Text_label.setText("   Please wait.........");
				Status_Text_label.repaint();
			}
			
		
	}
	

public void Browse_for_file() {
	FileNameExtensionFilter filter = new FileNameExtensionFilter("log", "log", "log");
	chooser.setFileFilter(filter);
	int returnVal = chooser.showOpenDialog(chooser);					
	if(returnVal == JFileChooser.APPROVE_OPTION) {
				
				 file = chooser.getSelectedFile();
			
			   	name = file.getName();
				int pos = name.lastIndexOf(".");
				if (pos > 0) {
				    name = name.substring(0, pos);
				    
				    File_exist_name = file.getParent() + "\\" + name;
				    Dest_name = file.getParent() + "\\" + name + ".pdf";
				}
				
				conv_state=1;	
				filename_text.setForeground(Color.BLUE);
				filename_text.setText(chooser.getSelectedFile().getName());
			}
}


public void changeBg2() {
	//timer.stop();
	 view_convert_button.setEnabled(true);
     Status_Text_label.setBackground(Color.GREEN);
		Status_Text_label.setText("     File converted successfully.");
		//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
}



public void view_pdf() {
	
	 try {

			if ((new File(Dest_name)).exists()) {

				Process p = Runtime
				   .getRuntime()
				   .exec("rundll32 url.dll,FileProtocolHandler "+Dest_name);
				p.waitFor();

			} else {

				JOptionPane.showMessageDialog(null, "File doesnt exist!");

			}


	  	  } catch (Exception ex) {
			ex.printStackTrace();
		  }
	
}





class MyTask extends SwingWorker {
				protected Object doInBackground() throws Exception {
					   open_pdf=0;
					   conv_state=9;
					   view_convert_button.setEnabled(false);
			        Document document = new Document();
			        
			        
				        while((new File(Dest_name)).exists()) {
				        	des_name_exist++;
				        	Dest_name = File_exist_name +"(" + des_name_exist + ")" + ".pdf";
				        	
				        }
				        
				        
				        PdfWriter.getInstance(document, new FileOutputStream(Dest_name));
				        document.open();
				        BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile().getAbsolutePath()));
				        String line;
				        Paragraph p;
				        Font normal = new Font(FontFamily.TIMES_ROMAN, 12);
				        Font bold = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
				        boolean title = true;
				        
					    while ((line = br.readLine()) != null) {
					        p = new Paragraph(line, title ? bold : normal);
					        p.setAlignment(Element.ALIGN_JUSTIFIED);
					        title = line.isEmpty();
					        document.add(p);
					    }
					    document.close();
					    timer.stop();
					    conv_state=0;
						changeBg2();
						open_pdf=1;
						JOptionPane.showMessageDialog(null, "File converted successfully!");
						String name =Dest_name;
						int pos_dot = name.lastIndexOf(".");
						int pos_slash = name.lastIndexOf("\\");
						
						
						if (pos_dot > 0) {
						    name = name.substring(pos_slash+1, pos_dot)+".pdf";
						}
						Conv_FileName.setForeground(Color.BLUE);
						Conv_FileName.setText(name + "   (Same location as the selected log file.)");
					return true;
					
				}
				
}



			
}