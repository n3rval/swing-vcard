package com.cgi.udev.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

public class vcard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6039434517728554927L;
	
private JComboBox<String> civilite;
private JTextField prenom;
private JTextField nom;
private JDatePicker datenaissance;
private JTextField email;
private JTextField numtel;
private JTextArea addresse;
private JTextField cp;
private JTextField ville;


	@Override
	protected void frameInit() {
		super.frameInit();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Exemple grid bag layout");
		this.getContentPane().setLayout(new GridBagLayout());

		int rowIndex = 0;
		civilite = new JComboBox<String>(new String[] { null, "Madame", "Monsieur", "Docteur", "Professeur" });
		prenom = new JTextField();
		nom = new JTextField();
		datenaissance = new JDatePicker(new UtilDateModel());
		email = new JTextField();
		numtel = new JTextField();
		addresse = new JTextArea(10, 20);
		cp = new JTextField();
		ville = new JTextField();
		addRow(rowIndex++, "Civilité", civilite);
		addRow(rowIndex++, "Prénom", prenom);
		addRow(rowIndex++, "Nom", nom);
		addRow(rowIndex++, "Date de naissance", datenaissance);
		addRow(rowIndex++, "email", email);
		addRow(rowIndex++, "numéro de téléphone", numtel);
		addRow(rowIndex++, "Addresse", addresse);
		addRow(rowIndex++, "Code Postal", cp);
		addRow(rowIndex++, "Ville", ville);
		JButton okButton = new JButton("Sauver");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSave();
			}
		});
		JButton cancelButton = new JButton("Annuler");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});
		addButtons(rowIndex++, okButton, cancelButton);
		this.pack();
		this.setResizable(false);
	}
	
	  private void onSave() {
		  try(Writer w= new OutputStreamWriter(new FileOutputStream("test.vcf"), "UTF-8")) {
			  w.write("BEGIN:VCARD\n");
			  w.write("VERSION:4.0\n");
			  w.write("N:" + nom.getText() + ";" + prenom.getText() + ";;" + civilite.getSelectedItem().toString() + ";\n");
			  w.write("FN:"+ prenom.getText() + " " + nom.getText() + "\n");
			  w.write("TEL;TYPE=home,voice;VALUE=uri:tel:" + numtel.getText() + ";\n");
			  w.write("ADR:;" + addresse.getText() + ";;" + ville.getText() + ";;" + cp.getText() + ";\n");
			  Date datepicker = (Date) datenaissance.getModel().getValue();
			  DateFormat df = new SimpleDateFormat("yyyyMMdd");
			  String date = df.format(datepicker);
			  w.write("BDAY:" + date + "\n");
			  w.write("END:VCARD\n");
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }

	private void onCancel() {
		// on cache la fenêtre
		this.setVisible(false);
		// on supprime la fenêtre
		this.dispose();
	}

	private void addRow(int rowIndex, String titre, JComponent component) {
		// création des contraintes de positionnement
		GridBagConstraints cst = new GridBagConstraints();
		// le composant doit occuper tout l'espace horizontal de sa case
		cst.fill = GridBagConstraints.HORIZONTAL;
		// le composant doit être aligné sur le haut de la case
		cst.anchor = GridBagConstraints.NORTH;
		// on définit la marge en pixels pour le haut, la gauche, le bas et la droite
		cst.insets = new Insets(5, 20, 5, 20);
		// on définit la position verticale
		cst.gridy = rowIndex;
		// on définit la position horizontale
		cst.gridx = 0;
		// poids relatif à l'horizontal
		cst.weightx = .3;

		JLabel label = new JLabel(titre);
		label.setLabelFor(component);
		this.add(label, cst);

		// on définit la position horizontale
		cst.gridx = 1;
		// poids relatif à l'horizontal
		cst.weightx = .7;
		this.add(component, cst);
	}

	private void addButtons(int rowIndex, JButton... buttons) {
		JPanel panel = new JPanel();
		for (JButton button : buttons) {
			panel.add(button);
		}
		// création des contraintes de positionnement
		GridBagConstraints cst = new GridBagConstraints();
		// on définit la marge en pixels pour le haut, la gauche, le bas et la droite
		cst.insets = new Insets(5, 10, 0, 0);
		// le composant doit occuper tout l'espace horizontal de sa case
		cst.fill = GridBagConstraints.HORIZONTAL;
		// on définit la position verticale
		cst.gridy = rowIndex;
		// on définit la position horizontale
		cst.gridx = 0;
		// le composant s'étend à l'horizontal sur deux cases de la grille
		cst.gridwidth = 2;
		this.add(panel, cst);
	}

	public static void main(String[] args) {
		JFrame window = new vcard();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}