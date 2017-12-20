package com.servidor.app.janelas;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.servidor.app.serviços.ServiçosServidor;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipalServidor {

	private JFrame frame;
	private JTextField textField;
	private ServiçosServidor servidor;
	
	/**
	 * Create the application.
	 */
	public TelaPrincipalServidor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setText("5555");
		textField.setBounds(10, 29, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		lblPorta.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblPorta);
		
		JButton btnIniciarServidor = new JButton("Iniciar Servidor");
		btnIniciarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(Integer.parseInt(textField.getText()));
				servidor = new ServiçosServidor(Integer.parseInt(textField.getText()));
				btnIniciarServidor.setEnabled(false);
			}
		});
		btnIniciarServidor.setBounds(157, 28, 122, 23);
		frame.getContentPane().add(btnIniciarServidor);
		frame.setVisible(true);
	}

}
