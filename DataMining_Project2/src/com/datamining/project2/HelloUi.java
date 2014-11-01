package com.datamining.project2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class HelloUi {

	private JFrame frmBullseyeDw;
	static Connection con = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloUi window = new HelloUi();
					window.frmBullseyeDw.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HelloUi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmBullseyeDw = new JFrame();
		frmBullseyeDw.setTitle("BullsEye Dataware House");
		frmBullseyeDw.setBounds(100, 100, 658, 436);
		frmBullseyeDw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmBullseyeDw.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 56, 21);
		panel.add(menuBar);

		JMenu mnQueries = new JMenu("Queries");

		menuBar.add(mnQueries);

		JMenuItem mnQuery1 = new JMenuItem("KMeans");
		mnQuery1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				KMeans qu1 = new KMeans();
				qu1.setVisible(true);

			}
		});

		mnQueries.add(mnQuery1);

		JLabel lblNewLabel = new JLabel("DATA WAREHOUSE");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(129, 112, 375, 42);
		panel.add(lblNewLabel);

	}
}
