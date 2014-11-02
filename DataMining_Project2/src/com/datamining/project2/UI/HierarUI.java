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

import com.datamining.project2.HirAggSingleLinkAlgorithm;
import com.datamining.project2.KMeansAlgorithm;
import com.datamining.project2.OutputObject;
import com.mathworks.toolbox.javabuilder.MWException;

public class HierarUI extends JFrame {

	private JPanel contentPane;
	private JTextField cutLevel;
	private JTextField fileName;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public HierarUI() {
		this.setTitle("Hierarchical Clustering");
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
		
		JLabel lblNewLabel = new JLabel("Run Hierarchical Algorithm for below props :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(52, 18, 446, 30);
		panel.add(lblNewLabel);
		
		JLabel lblType = new JLabel("Cut Level");
		lblType.setHorizontalAlignment(SwingConstants.LEFT);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setBounds(52, 57, 89, 20);
		panel.add(lblType);
		
		cutLevel = new JTextField();
		cutLevel.setText("5");
		cutLevel.setBounds(141, 59, 80, 20);
		panel.add(cutLevel);
		cutLevel.setColumns(10);
		
		JLabel fileNameLabel = new JLabel("File:");
		fileNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fileNameLabel.setBounds(273, 59, 32, 23);
		panel.add(fileNameLabel);
		
		fileName = new JTextField();
		fileName.setText("cho.txt");
		fileName.setColumns(10);
		fileName.setBounds(315, 60, 187, 18);
		panel.add(fileName);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 124, 568, 229);
		panel.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int cutL = Integer.parseInt(cutLevel.getText());
				

				try {
					HirAggSingleLinkAlgorithm hcl = new HirAggSingleLinkAlgorithm(fileName.getText(), cutL);
					OutputObject oo = hcl.runHeirarchical();
					textArea.setText(oo.outputStr);
					hcl.plotPca();
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
