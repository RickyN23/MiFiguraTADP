/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mifigura;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	public static final byte CIRCLE = 0;
	public static final byte SQUARE = 1;
	public static final byte TRIANGLE = 2;
	public static final byte LINE = 3;

	private Shape[] shapes;
	private Shape currentShape;
	private Stroke currentStroke;
	private short numberOfShapes;
	private byte typeOfShape;
	private Paint currentPaint;
	private boolean filled = false;
	private JLabel stateLabel;
	
	public DrawingPanel (JLabel state) {
		stateLabel = state;
		shapes = new Shape[100];
		numberOfShapes = 0;
		typeOfShape = CIRCLE;
		currentPaint = Color.BLACK;
		setBackground (Color.WHITE);
		MouseHandler mh = new MouseHandler ();
		addMouseListener (mh);
		addMouseMotionListener (mh);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent (g);
		Graphics2D g2d = (Graphics2D) g;

		if (currentShape != null)
			currentShape.draw (g2d);

		for (byte j = 0; shapes[j] != null && j < shapes.length; ++j)
			shapes[j].draw (g2d);
	}
	
	public void setTypeShape (byte newTypeShape) {
		typeOfShape = newTypeShape >= 0 && newTypeShape < 4 ? newTypeShape : 0;
	}
	
	public void setCurrentPaint (Paint newPaint) {
		currentPaint = newPaint;
	}
	
	public void setCurrentStroke (Stroke newStroke) {
		currentStroke = newStroke;
	}
	
	public void setFillable (boolean filledValue) {
		filled = filledValue;
	}
	
	public void undo () {
		if (--numberOfShapes < 0)
			numberOfShapes = 0;
		
		shapes[numberOfShapes] = null;
		
		repaint ();
	}
	
	public void clear () {
		numberOfShapes = 0;
		currentShape = null;
		
		for (short j = 0; shapes[j] != null && j < shapes.length; ++j)
			shapes[j] = null;
		
		repaint ();
	}
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener {

		public void mousePressed (MouseEvent me) {
			switch (typeOfShape) {
				case CIRCLE:
					currentShape = new Circle (me.getX (), me.getY (), filled, currentPaint, currentStroke);
					break;
					
				case SQUARE:
					currentShape = new Square (me.getX (), me.getY (), filled, currentPaint, currentStroke);
					break;
					
				case TRIANGLE:
					currentShape = new Triangle (me.getX(), me.getY(), filled, currentPaint, currentStroke);
					break;
				
				case LINE:
					currentShape = new Line (me.getX(), me.getY(), currentPaint, currentStroke);
			}
		}
		
		public void mouseReleased (MouseEvent me) {
			refreshCurrentShape (me.getX (), me.getY ());
			shapes[numberOfShapes++] = currentShape;
			currentShape = null;
			repaint ();
		}
		
		public void mouseMoved (MouseEvent me) {
			refreshStateLabel (me.getX (), me.getY ());
		}
		
		public void mouseDragged (MouseEvent me) {
			refreshStateLabel (me.getX (), me.getY ());
			refreshCurrentShape (me.getX (), me.getY ());
			repaint ();
		}
		
		private void refreshStateLabel (int x, int y) {
			stateLabel.setText (String.format ("(%d, %d)", x, y));
		}
		
		private void refreshCurrentShape (int x, int y) {
			currentShape.setFinalPoint (x, y);
		}
	}
}
