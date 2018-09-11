package com.cgi.udev.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

public class VCardPrompt extends JFrame {

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
		prenom = new JTextField();
		civilite = new JComboBox<String>(new String[] { null, "Madame", "Monsieur", "Docteur", "Professeur" });
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
				try {
					onSave();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Champs requis vide" , JOptionPane.WARNING_MESSAGE);
				}
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

	private void onSave() throws IOException {
		VCard vcard = new VCard();
		if (prenom.getText().isEmpty() | nom.getText().isEmpty() | civilite.getSelectedIndex() <= 0) {
			throw new IOException("Les champs Prenom ,Nom et civilité son obligatoires !");
		}
		vcard.civilite = civilite.getSelectedItem().toString();
		vcard.prenom = prenom.getText();
		vcard.nom = nom.getText();
		vcard.datenaissance = (Date) datenaissance.getModel().getValue();
		vcard.email = email.getText();
		vcard.numtel = numtel.getText();
		vcard.addresse = addresse.getText();
		vcard.cp = cp.getText();
		vcard.ville = ville.getText();
		WriteVCard.createVcard(vcard, vCardChooser());
	}

	private File vCardChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers Vcard (vcf)", "vcf"));
		int choix = fileChooser.showOpenDialog(this);
		if (choix == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
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
		JFrame window = new VCardPrompt();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}