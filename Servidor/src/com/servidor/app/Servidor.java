package com.servidor.app;

import java.awt.EventQueue;

import com.servidor.app.janelas.TelaPrincipalServidor;

public class Servidor {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipalServidor window = new TelaPrincipalServidor();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
