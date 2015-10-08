import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;


public class OriginalPanel extends JPanel implements MouseListener , MouseMotionListener {
	private static final long serialVersionUID = 2L;

	Graphics2D g3;
	public static int[] x, y, size, style;
	public static float[] compo;
	public static int cnt = 0, debug = 0, dx, dy;
	public static Color[] color;

	/*★★コンストラクタ★★*/
	OriginalPanel() {
		super();
		setBackground(new Color(255, 255, 255));
		x = new int[20];
		y = new int[20];
		size = new int[20];
		style = new int[20];
		compo = new float[20];
		color = new Color[20];
		for(int i = 0; i < 20 ; i++){
			x[i] = -10;
		}
		for(int i = 0; i < 20 ; i++){
			y[i] = -10;
		}
	}

	/* ★★描写★★ */
	public void paintComponent(Graphics g) {
		Graphics2D g3 = (Graphics2D)g;
		super.paintComponent(g);
		super.paintComponent(g3);
		g3.setColor(Color.black);
		g3.drawLine((PaintTool.p8.getWidth()-10)/2, (PaintTool.p8.getHeight() - 74)/2 - 25, (PaintTool.p8.getWidth()-10)/2, (PaintTool.p8.getHeight() - 74)/2 + 25);
		g3.drawLine((PaintTool.p8.getWidth()-10)/2 - 25, (PaintTool.p8.getHeight() - 74)/2, (PaintTool.p8.getWidth()-10)/2 + 25, (PaintTool.p8.getHeight() - 74)/2);
		for(int i = 0; i < cnt ; i++){
			g3.setColor(color[i]);
			g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, compo[i]));
			g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (style[i] == 0) {
				g3.fillRect(x[i]-size[i]/2, y[i]-size[i]/2,size[i], size[i]);
				g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g3.drawRect(x[i]-size[i]/2, y[i]-size[i]/2,size[i], size[i]);
			} else if (style[i] == 1) {
				g3.fillOval(x[i]-size[i]/2, y[i]-size[i]/2,size[i], size[i]);
				g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			}


		}
		if(debug == 1){
			g3.setColor(PaintTool.getColor());
			g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
			g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (OriginalFrame.rb1.isSelected()) {
				g3.fillRect(dx-PaintTool.getsize()/2, dy-PaintTool.getsize()/2,PaintTool.getsize(), PaintTool.getsize());
			} else if (OriginalFrame.rb2.isSelected()) {
				g3.fillOval(dx-PaintTool.getsize()/2, dy-PaintTool.getsize()/2,PaintTool.getsize(), PaintTool.getsize());
			}
			debug = 0;
		}
	}

	/* ★★動作系★★ */
	public void mousePressed(MouseEvent e) {
		x[cnt] = e.getX();
		y[cnt] = e.getY();
		compo[cnt] = PaintTool.getsize5();
		color[cnt] = PaintTool.getColor();
		size[cnt] = PaintTool.getsize();
		if (OriginalFrame.rb1.isSelected()) {
			style[cnt] = 0;
		} else if (OriginalFrame.rb2.isSelected()) {
			style[cnt] = 1;
		}
		if(cnt < 19)cnt++;
		repaint();
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		dx = e.getX();
		dy = e.getY();
		debug = 1;
		repaint();
	}
}
