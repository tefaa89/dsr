package com.dsr.gui;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class MainPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setLayout(null);

		table = new JTable();
		table.setBounds(10, 50, 920, 273);
		add(table);

		JLabel lblDocumentsView = new JLabel("Documents View");
		lblDocumentsView.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblDocumentsView.setBounds(20, 11, 126, 28);
		add(lblDocumentsView);

		JLabel lblTracer = new JLabel("Tracer");
		lblTracer.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblTracer.setBounds(20, 334, 57, 20);
		add(lblTracer);

		JButton btnNewButton = new JButton("Add (Classify)");
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(788, 586, 142, 62);
		add(btnNewButton);

		JButton btnTrain = new JButton("Train");
		btnTrain.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTrain.setBounds(636, 586, 142, 62);
		add(btnTrain);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnExit.setBounds(10, 586, 142, 62);
		add(btnExit);

		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 365, 920, 205);
		add(editorPane);

	}
}
