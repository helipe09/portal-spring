package br.org.isac.portaltransparencia.portal.util;

import org.apache.commons.codec.binary.Base64;

public class Criptografia {
	
	public static String encrypt(String chaveSenha)  {
		 return new Base64().encodeToString(chaveSenha.getBytes());
	}

	public static String decrypt(String senhaEncriptada){
		 return new String(new Base64().decode(senhaEncriptada));
	}
}
