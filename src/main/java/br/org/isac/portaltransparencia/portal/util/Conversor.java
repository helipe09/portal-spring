package br.org.isac.portaltransparencia.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.MaskFormatter;

public class Conversor {
	
	public static Date converteStringEmDate(String dataString) {

		if(dataString == null) return new Date();
		
		Date data = null;
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
			data = formato.parse(dataString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public static String converteDateEmString(Date data) {
		
		if(data == null) return "";
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		return formato.format(data);
	}
	
	public static String formataCNPJ(String cnpj) {
		int qtde = cnpj.length();
		int qtdeCompletarZero = 14 - qtde;
		
		for(int i=0; i<qtdeCompletarZero;i++) {
			cnpj = "0"+cnpj;
		}
		
		try {
			MaskFormatter mask=new MaskFormatter("##.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			//mask.setMask(cnpj);
			return mask.valueToString(cnpj);
		} catch (ParseException e) {
			return cnpj;
		}
	}
	
	public static String formataCPF(String cpf) {
		int qtde = cpf.length();
		int qtdeCompletarZero = 11 - qtde;
		
		for(int i=0; i<qtdeCompletarZero;i++) {
			cpf = "0"+cpf;
		}
		
		try {
			MaskFormatter mask=new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException e) {
			return cpf;
		}
	}
	
	public static String formataCEP(String cep) {
		int qtde = cep.length();
		int qtdeCompletarZero = 8 - qtde;
		
		for(int i=0; i<qtdeCompletarZero;i++) {
			cep = "0"+cep;
		}
		
		try {
			MaskFormatter mask=new MaskFormatter("##.###-###");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cep);
		} catch (ParseException e) {
			return cep;
		}
	}
	
	public static String periodicidadeTipoDocumento(Integer id) {
		if(id == 1) return "Mensal";
		if(id == 2) return "Bimestral";
		if(id == 3) return "Trimestral";
		if(id == 4) return "Semestral";
		if(id == 5) return "Anual";
		if(id == 6) return "Indeterminada";
		return id+"";
	}

}
