package br.org.isac.portaltransparencia.portal.util;

import java.util.Random;

public class GeradorSenha {
	
	private static final int MIN = 10000000;
	private static final int MAX = 99999999;
	
	public static int aleatorio() {
        Random random = new Random();
        return random.nextInt((MAX - MIN) + 1) + MIN;
    }

}
