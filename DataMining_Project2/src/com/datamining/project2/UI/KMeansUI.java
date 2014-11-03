package com.datamining.project2.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import com.datamining.project2.KMeansAlgorithm;
import com.datamining.project2.OutputObject;
import com.mathworks.toolbox.javabuilder.MWException;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;

public class KMeansUI extends JFrame {

	private JPanel contentPane;
	private JTextField rowNumbers;
	private JTextField thresholdSSE;
	private JTextField numOfIter;
	private JTextField numOfClusters;
	private JTextField fileName;
	private JTextField exeTime;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public KMeansUI() {
		this.setTitle("KMeans Algorithm");
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

		JLabel lblNewLabel = new JLabel("Run KMeans Algorithm , Row Nums:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(52, 18, 232, 30);
		panel.add(lblNewLabel);

		JLabel lblName = new JLabel("K:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(295, 59, 27, 20);
		panel.add(lblName);

		JLabel lblType = new JLabel("Threshold SSE");
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setBounds(52, 57, 89, 20);
		panel.add(lblType);

		JLabel lblDescription = new JLabel("N:");
		lblDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescription.setBounds(231, 59, 27, 20);
		panel.add(lblDescription);

		rowNumbers = new JTextField();
		rowNumbers.setText("1|76|148|250|382");
		rowNumbers.setBounds(275, 25, 314, 20);
		panel.add(rowNumbers);
		rowNumbers.setColumns(10);

		thresholdSSE = new JTextField();
		thresholdSSE.setText("0.002");
		thresholdSSE.setBounds(141, 59, 80, 20);
		panel.add(thresholdSSE);
		thresholdSSE.setColumns(10);

		numOfIter = new JTextField();
		numOfIter.setText("15");
		numOfIter.setBounds(248, 59, 38, 20);
		panel.add(numOfIter);
		numOfIter.setColumns(10);

		numOfClusters = new JTextField();
		numOfClusters.setText("5");
		numOfClusters.setBounds(315, 61, 50, 18);
		panel.add(numOfClusters);
		numOfClusters.setColumns(10);

		JLabel fileNameLabel = new JLabel("File:");
		fileNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fileNameLabel.setBounds(375, 59, 32, 23);
		panel.add(fileNameLabel);

		fileName = new JTextField();
		fileName.setText("cho.txt");
		fileName.setColumns(10);
		fileName.setBounds(402, 61, 187, 18);
		panel.add(fileName);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 124, 568, 229);
		panel.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);

		JLabel lblExecutionTime = new JLabel("Execution Time :");
		lblExecutionTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblExecutionTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecutionTime.setBounds(196, 356, 109, 20);
		panel.add(lblExecutionTime);

		exeTime = new JTextField();
		exeTime.setBounds(315, 358, 130, 20);
		panel.add(exeTime);
		exeTime.setColumns(10);

		JCheckBox checkBox = new JCheckBox("Normalize File");
		checkBox.setSelected(true);
		checkBox.setBounds(52, 84, 137, 23);
		panel.add(checkBox);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int k = Integer.parseInt(numOfClusters.getText());
				int N = Integer.parseInt(numOfIter.getText());
				double sseThreshold = Double.parseDouble(thresholdSSE.getText());

				try {
					long current = System.nanoTime();
					KMeansAlgorithm kmc = new KMeansAlgorithm(fileName
							.getText(), k, rowNumbers.getText(), sseThreshold,
							N, checkBox.isSelected());
					OutputObject oo = kmc.runKMeansAlgorithm();
					exeTime.setText(Double.toString(
							(System.nanoTime() - current) / 1000000000.0)
							.substring(0, 4)
							+ " Sec");
					textArea.setText(oo.outputStr);
					kmc.plotPca();
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
