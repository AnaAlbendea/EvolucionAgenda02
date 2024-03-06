package agenda.vista.swing;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;



@SuppressWarnings("serial")
public class VentanaOpciones extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMemoria;
	private JRadioButton rdbtnMysql;
	
	private ManejaEventos manejador;
	private JPanel panelMemoria;
	private JCheckBox chckGraba;

	private JPanel panelMySQL;
	private final ButtonGroup bgMySQL = new ButtonGroup();
	private JRadioButton rdbtnJdbc;
	private JRadioButton rdbtnJpa;
	
	private JButton btnGrabar;
	private JButton btnCancelar;
	private VInicial padre;
	private boolean edicion;
	private boolean cambioOrigenDatos;


	public VentanaOpciones(VInicial padre) {
		setResizable(false);
		setTitle("Opciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.padre = padre;
		manejador = new ManejaEventos();

		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.LIGHT_GRAY);
		panelBotones.setBounds(15, 279, 338, 55);
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);
		
		btnGrabar = new JButton("Grabar");
		btnGrabar.setBounds(48, 13, 97, 30);
		btnGrabar.addActionListener(manejador);
		panelBotones.add(btnGrabar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(193, 13, 97, 30);
		btnCancelar.addActionListener(manejador);
		panelBotones.add(btnCancelar);
		
		rdbtnMemoria = new JRadioButton("Memoria");
		rdbtnMemoria.setSelected(true);
		buttonGroup.add(rdbtnMemoria);
		rdbtnMemoria.setBounds(32, 20, 127, 25);
		contentPane.add(rdbtnMemoria);
		rdbtnMemoria.addItemListener(manejador);
		
		rdbtnMysql = new JRadioButton("MySQL");
		buttonGroup.add(rdbtnMysql);
		rdbtnMysql.setBounds(32, 150, 127, 25);
		contentPane.add(rdbtnMysql);
		rdbtnMysql.addItemListener(manejador);
		
		panelMemoria = new JPanel();
		panelMemoria.setBackground(SystemColor.activeCaption);
		panelMemoria.setBounds(42, 54, 266, 64);
		contentPane.add(panelMemoria);
		panelMemoria.setLayout(null);
		
		chckGraba = new JCheckBox("Guardar Datos (Serializados)");
		chckGraba.setSelected(true);
		chckGraba.setForeground(Color.WHITE);
		chckGraba.setBackground(SystemColor.activeCaption);
		chckGraba.setBounds(29, 19, 208, 25);
		chckGraba.addActionListener(manejador);
		panelMemoria.add(chckGraba);
		
		panelMySQL = new JPanel();
		panelMySQL.setBackground(SystemColor.activeCaption);
		panelMySQL.setBounds(42, 180, 266, 64);
		contentPane.add(panelMySQL);
		panelMySQL.setLayout(null);
		
		rdbtnJdbc = new JRadioButton("JDBC");
		rdbtnJdbc.setBackground(SystemColor.activeCaption);
		rdbtnJdbc.setForeground(Color.WHITE);
		rdbtnJdbc.setBounds(69, 4, 127, 25);
		rdbtnJdbc.setSelected(true);
		rdbtnJdbc.addActionListener(manejador);
		panelMySQL.add(rdbtnJdbc);
		
		rdbtnJpa = new JRadioButton("JPA");
		rdbtnJpa.setBackground(SystemColor.activeCaption);
		rdbtnJpa.setForeground(Color.WHITE);
		rdbtnJpa.setBounds(69, 33, 127, 25);
		rdbtnJpa.addActionListener(manejador);
		panelMySQL.add(rdbtnJpa);
		
		bgMySQL.add(rdbtnJdbc);
		bgMySQL.add(rdbtnJpa);
		
		
		setVisible(true);
		
		leerConfig();
		btnGrabar.setEnabled(false);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				btnCancelar.doClick();;
			}
		});

		edicion = false;
	}
	
	private void leerConfig(){
//		chckGraba.setSelected(padre.control.isMemGraba());
//		switch (padre.control.getNuevoOrigenDatos()) {
//		case Controler.MEMORIA:
//			rdbtnMemoria.setSelected(true);
//			rdbtnJdbc.setEnabled(false);
//			rdbtnJpa.setEnabled(false);
//			break;
//		case Controler.JDBC:
//			rdbtnMysql.setSelected(true);
//			rdbtnJdbc.setSelected(true);
//			break;
//		case Controler.JPA:
//			rdbtnMysql.setSelected(true);
//			rdbtnJpa.setSelected(true);
//			break;
//		}
	}
	
	private void grabarConfig(){
//		padre.control.setMemGraba(chckGraba.isSelected());
//		
//		if (rdbtnMemoria.isSelected()) {
//			padre.control.setNuevoOrigenDatos(Controler.MEMORIA);
//		}
//		else {
//			if (rdbtnJdbc.isSelected()) {
//				padre.control.setNuevoOrigenDatos(Controler.JDBC);			
//			} else {
//				padre.control.setNuevoOrigenDatos(Controler.JPA);			
//			}
//		}
	}

	private void salir() {
		padre.setEnabled(true);
		padre.setFocusableWindowState(true);
		padre.tabla.requestFocus();
		dispose();
	}

	
	private class ManejaEventos implements ItemListener, ActionListener{

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == btnGrabar) {
				grabarConfig();
				btnGrabar.setEnabled(false);
				edicion = false;
				if (cambioOrigenDatos) {
					JOptionPane.showMessageDialog(null, "Los cambios tendrán efecto la próxima vez que ejecute Agenda\n Debe reiniciar la aplicación");
				}
				else {
					JOptionPane.showMessageDialog(null, "Actualizacion correcta");
				}
				salir();
			}
			if (ev.getSource() == chckGraba) {
				btnGrabar.setEnabled(true);
				edicion = true;
			}
			if (ev.getSource() == btnCancelar) {
				if (!edicion || JOptionPane.showConfirmDialog(null,
										"Desea abandonar la ventana\n Se perderán los cambios realizados",
										"Salir de Contactos", 2) == 0) {
					salir();
				}
			}
			if (ev.getSource() == rdbtnJdbc || ev.getSource() == rdbtnJpa){
				btnGrabar.setEnabled(true);
				edicion = true;
			}
		}

		public void itemStateChanged(ItemEvent ev) {
			btnGrabar.setEnabled(true);
			edicion = true;
			cambioOrigenDatos = true;

			chckGraba.setEnabled(rdbtnMemoria.isSelected());
			rdbtnJdbc.setEnabled(rdbtnMysql.isSelected());
			rdbtnJpa.setEnabled(rdbtnMysql.isSelected());
			
			if (rdbtnMemoria.isSelected()) {
				rdbtnMemoria.requestFocus();
			}else{
				rdbtnMysql.requestFocus();
			}
		}
	}
}
