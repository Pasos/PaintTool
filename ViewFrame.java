import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewFrame extends JPanel  implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 2L;
	JFrame frame;
	
	int x ,y;
	Graphics2D g2;
	public static JButton b;

	/*★★入出力系★★*/

	/*★★コンストラクタ★★*/
	ViewFrame() 
	{
		super();
		frame = new JFrame();
		frame.setBackground(new Color(238, 238, 238));
		frame.setTitle("ビュー");
		frame.setSize(125+6, 125+28);
		frame.add(this);
		
		
		 frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					PaintTool.viewm.setSelected(false);
				}

				public void windowIconified(WindowEvent e) {
				}

				public void windowOpened(WindowEvent e) {
				}
			});
		
		addMouseListener(this);
		addMouseMotionListener(this);
		frame.setResizable(false);
		frame.setVisible(false);
	}
	
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		super.paintComponents(g);
		super.paintComponents(g2);
		frame.setSize(PaintTool.sizex/4+6, PaintTool.sizey/4+28);
		g2.scale(0.25, 0.25);
		g2.drawImage(DrawPanel.img[DrawPanel.imgnum], 0, 0, this);
		g2.setColor(Color.RED);
		g2.drawRect( (int)Math.round(PaintTool.bx.getValue()/PaintTool.scale), (int)Math.round(PaintTool.by.getValue()/PaintTool.scale), (int)Math.round(PaintTool.scrollpane.getViewport().getBounds().width/PaintTool.scale), (int)Math.round(PaintTool.scrollpane.getViewport().getBounds().height/PaintTool.scale));
	}
	
	/* ★★動作系★★ */
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		PaintTool.bx.setValue(Math.round(PaintTool.scrollpane.getHorizontalScrollBar().getMaximum()*((float)(4*x - PaintTool.scrollpane.getViewport().getBounds().width/2/PaintTool.scale) /PaintTool.sizex)));
		PaintTool.by.setValue(Math.round(PaintTool.scrollpane.getVerticalScrollBar().getMaximum()*((float)(4*y - PaintTool.scrollpane.getViewport().getBounds().height/2/PaintTool.scale)/PaintTool.sizey)));
		repaint();
		PaintTool.rewrite();
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		PaintTool.bx.setValue(Math.round(PaintTool.bx.getMaximum()*((float)(4*x - PaintTool.scrollpane.getViewport().getBounds().width/2/PaintTool.scale) /PaintTool.sizex)));
		PaintTool.by.setValue(Math.round(PaintTool.by.getMaximum()*((float)(4*y - PaintTool.scrollpane.getViewport().getBounds().height/2/PaintTool.scale)/PaintTool.sizey)));
		repaint();
		PaintTool.rewrite();
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
}
