package com.datamining.project2.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.datamining.project2.DBScanAlgorithm;
import com.datamining.project2.KMeansAlgorithm;
import com.datamining.project2.OutputObject;
import com.mathworks.toolbox.javabuilder.MWException;

public class DBScanUI extends JFrame {

	private JPanel contentPane;
	private JTextField epsilon;
	private JTextField minPnts;
	private JTextField fileName;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public DBScanUI() {
		this.setTitle("DBScan Algorithm");
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Run DBScan Algorithm with below parameters");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(52, 18, 470, 30);
		panel.add(lblNewLabel);
		
		
		
		
		JLabel lblName = new JLabel("Min Points");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(215, 57, 74, 20);
		panel.add(lblName);
		
		JLabel lblType = new JLabel("Epsilon");
		lblType.setHorizontalAlignment(SwingConstants.LEFT);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setBounds(52, 57, 59, 20);
		panel.add(lblType);
		
		epsilon = new JTextField();
		epsilon.setText("0.257");
		epsilon.setBounds(109, 59, 96, 20);
		panel.add(epsilon);
		epsilon.setColumns(10);
		
		minPnts = new JTextField();
		minPnts.setText("5");
		minPnts.setBounds(287, 60, 53, 18);
		panel.add(minPnts);
		minPnts.setColumns(10);
		
		JLabel fileNameLabel = new JLabel("File:");
		fileNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fileNameLabel.setBounds(350, 56, 32, 23);
		panel.add(fileNameLabel);
		
		fileName = new JTextField();
		fileName.setText("cho.txt");
		fileName.setColumns(10);
		fileName.setBounds(378, 59, 187, 18);
		panel.add(fileName);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 124, 568, 229);
		panel.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int minP = Integer.parseInt(minPnts.getText());
				double epsilonV = Double.parseDouble(epsilon.getText());
				

				try {
					DBScanAlgorithm dbscan =new DBScanAlgorithm(fileName.getText(),epsilonV,minP);
					OutputObject oo =dbscan.runDBScanAlgorithm();
					textArea.setText(oo.outputStr);
					dbscan.plotPca();
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MWException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
				
				
			
		});
		
		btnExecute.setBounds(273, 90, 89, 23);
		panel.add(btnExecute);
		
		
		
		
	
		
		
		
		
		
		
		
		
	}

	public static void fillCombo(JComboBox temp, String variable) {

		

	}

}
