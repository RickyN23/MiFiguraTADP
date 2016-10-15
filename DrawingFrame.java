/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mifigura;

/**
 *
 * @author deadzj
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawingFrame implements ActionListener, ItemListener {
	private DrawingPanel dw;
	private JButton clearBtn;
	private JButton undoBtn;
	private JCheckBox filled;
	private JComboBox shapesList;
        private JComboBox Colores;
	private JFrame wndw;
	private Color color;
	
	public DrawingFrame () {
		JLabel stateLabel = new JLabel ();
		JPanel controlPanel = new JPanel ();
		controlPanel.setLayout (new GridLayout (2, 1, 5, 5));
		JPanel top = new JPanel ();
		JPanel bottom = new JPanel ();
		clearBtn = new JButton ("Clear");
		clearBtn.addActionListener (this);
		undoBtn = new JButton ("Undo");
		undoBtn.addActionListener (this);
                wndw = new JFrame ("Drawing app");
                String[] shapesNames = {"Circle", "Square", "Triangle", "Line"};
                String[] colores = {"Black", "Green", "Red","Yellow","Orange","Pink","Blue"};
		shapesList = new JComboBox (shapesNames);
		shapesList.setMaximumRowCount (4);
		shapesList.addItemListener (this);
                Colores = new JComboBox (colores);
		Colores.setMaximumRowCount (7);
		Colores.addItemListener (this);
		color = Color.BLACK;
		dw = new DrawingPanel (stateLabel);
		
		filled = new JCheckBox ("Filled");
		filled.addItemListener (this);

                top.add (undoBtn);
                top.add (clearBtn);
                top.add (shapesList);
                top.add (Colores);
                top.add (filled);
		
		
		controlPanel.add (top);
		controlPanel.add (bottom);
		wndw.add (controlPanel, BorderLayout.NORTH);
		wndw.add (dw);
		wndw.add (stateLabel, BorderLayout.SOUTH);
		wndw.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		wndw.setSize (800, 500);
		wndw.setVisible (true);
	}

	public static void main (String args[]) {
		new DrawingFrame ();
	}
	
	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource () == undoBtn)
			dw.undo ();
		else if (ae.getSource () == clearBtn){
			dw.clear ();	
                }
	}
	
	public void itemStateChanged (ItemEvent ie) {
		if (ie.getSource () == filled)
			dw.setFillable (filled.isSelected ());
		if (ie.getStateChange () == ItemEvent.SELECTED)
			dw.setTypeShape ((byte) shapesList.getSelectedIndex ());  
                    if(Colores.getSelectedItem().toString().equals("Black")){
                           color = Color.BLACK;
                        }else if(Colores.getSelectedItem().toString().equals("Green")){
                           color = Color.green;
                        }else if(Colores.getSelectedItem().toString().equals("Red")){
                            color = Color.RED;
                        }else if(Colores.getSelectedItem().toString().equals("Yellow")){
                            color = Color.YELLOW;
                        }else if(Colores.getSelectedItem().toString().equals("Orange")){
                            color = Color.ORANGE;
                        }else if(Colores.getSelectedItem().toString().equals("Pink")){
                             color = Color.PINK;
                        }else if(Colores.getSelectedItem().toString().equals("Blue")){
                             color = Color.BLUE;
                        }
                        dw.setCurrentPaint(color);
	}
}