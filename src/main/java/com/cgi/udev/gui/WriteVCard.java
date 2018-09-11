package com.cgi.udev.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class WriteVCard {

	public static void createVcard(VCard vcard, File file) throws IOException {
		if (!file.exists()) {
			throw new IOException("Le fichier n'existe pas");
		}
		try (Writer w = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
			w.write("BEGIN:VCARD\n");
			w.write("VERSION:4.0\n");
			w.write("N:" + vcard.nom + ";" + vcard.prenom + ";;" + vcard.civilite + ";\n");
			w.write("FN:" + vcard.prenom + " " + vcard.nom + "\n");
			w.write("TEL;TYPE=home,voice;VALUE=uri:tel:" + vcard.numtel + ";\n");
			w.write("ADR:;" + vcard.addresse + ";;" + vcard.ville + ";;" + vcard.cp + ";\n");
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String date = df.format(vcard.datenaissance);
			w.write("BDAY:" + date + "\n");
			w.write("END:VCARD\n");
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}
}
