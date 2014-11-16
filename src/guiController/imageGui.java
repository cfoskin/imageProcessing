package guiController;
import imageProcessing.ConnectedComponentImage;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTextField;
public class imageGui {

	private JFrame frame;
	private JSlider s1;
	ConnectedComponentImage c = new ConnectedComponentImage(fileChooser());
	private JTextField txtSetTresholdSlider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{

		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				try {
					imageGui window = new imageGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	/**
	 * Create the application.
	 */
	public imageGui() {
		initialize();
	}

	public String fileChooser()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG & GIF Images", "jpg", "gif", "bmp", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			chooser.getSelectedFile().getAbsolutePath();
		}
		String choice = chooser.getSelectedFile().getAbsolutePath();
		return choice;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		frame = new JFrame("Welcome to Colum's Image processing application");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 205));
		panel.setBackground(new Color(65, 105, 225));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnC = new JButton("Show Picture with new random colors");
		btnC.setBackground(new Color(255, 255, 255));
		btnC.setBounds(68, 151, 301, 30);
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.displayColourComponentImage();
			}
		});

		JButton btnNewButton_1 = new JButton("Show original picture");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(12, 235, 185, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.displayOriginal();
			}
		});
		panel.setLayout(null);
		panel.add(btnNewButton_1);
		panel.add(btnC);

		JButton btnNewButton_2 = new JButton("Count components");
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{

				JFrame frame = new JFrame("ConnectedComponentImage counting components in an image");
				JLabel label1 = new JLabel("Test");
				frame.getContentPane().add(label1, BorderLayout.CENTER);
				label1.setText("\n"+ "\n"+ "Number of components in the image is: " + "" +	c.countComponents());
				frame.pack();
				frame.setVisible(true);
			}

		});
		btnNewButton_2.setBounds(221, 235, 197, 25);
		panel.add(btnNewButton_2);

		JButton btnComponentsWithRed = new JButton("Show all Components in red boxes");
		btnComponentsWithRed.setBackground(new Color(255, 255, 255));
		btnComponentsWithRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.displayRedBoxedPic();
			}
		});
		btnComponentsWithRed.setBounds(68, 193, 301, 30);
		panel.add(btnComponentsWithRed);

		JButton btnNewButton_3 = new JButton("Show grayscaled picture");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.displayGrayscalePic();
			}
		});
		btnNewButton_3.setBounds(68, 67, 301, 30);
		panel.add(btnNewButton_3);

		JButton btnDisplayBinarizedImage = new JButton("Show Binarized picture");
		btnDisplayBinarizedImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value = s1.getValue();
				c.displayBinaryPic(value);
			}
		});
		btnDisplayBinarizedImage.setBounds(68, 109, 301, 30);
		panel.add(btnDisplayBinarizedImage);
		this.s1 = new JSlider(0, 255, 128);
		s1.setBounds(127, 12, 200, 25);
		panel.add(s1);
		s1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider js = (JSlider) e.getSource();
				txtSetTresholdSlider.setText("Treshold slider is at: " + js.getValue());
			}
		});

        txtSetTresholdSlider = new JTextField();
        txtSetTresholdSlider.setText("Treshold slider is at: " + s1.getValue());
        txtSetTresholdSlider.setBounds(137, 46, 160, 19);
        panel.add(txtSetTresholdSlider);
        txtSetTresholdSlider.setColumns(10);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new CardLayout(0, 0));
	}
}