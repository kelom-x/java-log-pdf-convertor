

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Main {
	//private static BufferedImage Win_icon;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		try {
			
			
			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");		
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		 
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					new SubMain();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + e.getMessage());
					try {
						throw e;
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				
				
			}
		});
	}

}
