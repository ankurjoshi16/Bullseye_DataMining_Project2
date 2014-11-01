package com.datamining.project2;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class KMeans extends JFrame {

	private JPanel contentPane;
	private JTable dataTable;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public KMeans() {
		this.setTitle("Query1");
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
		
		JLabel lblNewLabel = new JLabel("Run KMeans for: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(40, 21, 232, 30);
		panel.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(53, 107, 567, 220);
		panel.add(scrollPane);
		
		dataTable = new JTable();
		scrollPane.setViewportView(dataTable);
		
		final JComboBox dName = new JComboBox();
		dName.setBounds(308, 28, 76, 20);
		panel.add(dName);
		fillCombo(dName, "name");
		
		final JComboBox dType = new JComboBox();
		dType.setBounds(409, 28, 76, 20);
		panel.add(dType);
		fillCombo(dType, "type");
		
		final JComboBox dDescription  = new JComboBox();
		dDescription.setBounds(519, 28, 76, 20);
		panel.add(dDescription);
		fillCombo(dDescription, "description");
		
		JLabel lblqueryexec = new JLabel("Execution Time:");
		lblqueryexec.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblqueryexec.setVerticalAlignment(SwingConstants.BOTTOM);
		lblqueryexec.setBounds(52, 344, 112, 20);
		panel.add(lblqueryexec);
		
		final JLabel lblqExec = new JLabel("");
		lblqExec.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblqExec.setVerticalAlignment(SwingConstants.BOTTOM);
		lblqExec.setBounds(174, 344, 66, 20);
		panel.add(lblqExec);
		
		
		
		
		
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					long current=System.nanoTime();
					
					if(dName.getSelectedItem().toString().equals("") && dType.getSelectedItem().toString().equals("") && dDescription.getSelectedItem().toString().equals(""))
						JOptionPane.showMessageDialog(panel, "Select name, type or disease");
					
					
						
						
					lblqExec.setText(Double.toString((System.nanoTime()-current)/1000000000.0)+" Sec");	
					
				//	lblexecTime.setText(Double.toString((System.nanoTime()-current)/1000000000.0)+" Sec");
					
			
				
			}
				
				
				
			
		});
		btnExecute.setBounds(257, 75, 89, 23);
		panel.add(btnExecute);
		
		
		JLabel lblK = new JLabel("K");
		lblK.setHorizontalAlignment(SwingConstants.CENTER);
		lblK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblK.setBounds(308, 3, 71, 20);
		panel.add(lblK);
		
		JLabel lblC = new JLabel("Row Number");
		lblC.setHorizontalAlignment(SwingConstants.CENTER);
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblC.setBounds(409, 3, 71, 20);
		panel.add(lblC);
		
		JLabel lblS = new JLabel("SSE_threshold");
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		lblS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblS.setBounds(519, 3, 71, 20);
		panel.add(lblS);
		
	}

	public static void fillCombo(JComboBox temp, String variable) {

		temp.addItem("");

		

	}
}
