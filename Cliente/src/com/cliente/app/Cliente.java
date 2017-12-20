package com.cliente.app;

import java.awt.EventQueue;

import com.cliente.app.janelas.TelaPrincipalCliente;

public class Cliente {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					TelaPrincipalCliente frame = new TelaPrincipalCliente();
				
				} catch (Exception e) {
					
					e.printStackTrace();
				
				}
			}
		});
	}
}
