import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorFrame extends JPanel  implements MouseListener, MouseMotionListener,  ChangeListener  {
	private static final long serialVersionUID = 2L;
	JFrame frame;
	JLabel l;
	public static JSlider s;
	public static int x  = 0,y = 0, z = 0;
	Graphics2D g2;
	public static JButton b;

	/*★★入出力系★★*/
	
	void visible(){
		frame.setVisible(true);
	}
	void invisible(){
		frame.setVisible(false);
	}
	/*★★コンストラクタ★★*/
	ColorFrame() 
	{
		super();
		setPreferredSize(new Dimension(256,256));
		frame = new JFrame();
		frame.setBackground(new Color(238, 238, 238));
		frame.setTitle("色HSV選択");
		frame.setSize(282+6, 315+28);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,1));
		frame.setLayout(new BorderLayout());
		s = new JSlider(SwingConstants.HORIZONTAL, 0, 360, 0);
		s.setPreferredSize(new Dimension(22,10));
		ImageIcon icon = new ImageIcon(getClass().getResource("H.png"));
		l = new JLabel(icon);
		p.add(s);
		p.add(l);
		frame.add("North", p);
		JPanel p2 = new JPanel();
		p2.add(this);
		frame.add("Center", p2);
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
		s.addChangeListener(this);
		frame.setResizable(false);
		frame.setVisible(false);
	}
	
	public void paintComponent(Graphics g) {
		frame.repaint();
		g2 = (Graphics2D) g;
		super.paintComponents(g);
		super.paintComponents(g2);
		g2.drawRect(0, 0, 255, 255);
		int hi , p, q , t;
		float f;
		hi = (int) (Math.floor(z / 60.0) % 6);
		f  = (float) ((z / 60.0f) - Math.floor(z / 60.0f));
		for(int xt = 0;xt < 256;xt++){
			for(int yt = 0;yt < 256;yt++){
				p  = Math.round(xt * (1.0f - (yt / 255.0f)));
				q  = Math.round(xt * (1.0f - (yt / 255.0f) * f));
				t  = Math.round(xt * (1.0f - (yt / 255.0f) * (1.0f - f)));
				switch(hi){
				case 0:
					g2.setColor(new Color(xt, t, p));
					break;
				case 1:
					g2.setColor(new Color(q, xt, p));
					break;
				case 2:
					g2.setColor(new Color(p, xt, t));
					break;
				case 3:
					g2.setColor(new Color(p, q, xt));
					break;
				case 4:
					g2.setColor(new Color(t, p, xt));
					break;
				case 5:
					g2.setColor(new Color(xt, p, q));
					break;
				}
				g2.drawLine(xt, yt, xt, yt);
			}
		}
		g2.setColor(Color.BLACK);
		g2.drawOval(x-2, y-2, 4, 4);
		s.revalidate();
		s.repaint();
	}
	
	/* ★★動作系★★ */
	
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(x > 255)x = 255;
		if(x < 0)x = 0;
		if(y > 255)y = 255;
		if(y < 0)y = 0;
		int hi , p, q , t;
		float f;
		hi = (int) (Math.floor(z / 60.0) % 6);
		f  = (float) ((z / 60.0f) - Math.floor(z / 60.0f));
		p  = Math.round(x * (1.0f - (y / 255.0f)));
		q  = Math.round(x * (1.0f - (y / 255.0f) * f));
		t  = Math.round(x * (1.0f - (y / 255.0f) * (1.0f - f)));
		switch(hi){
		case 0:
			PaintTool.setColor(new Color(x, t, p));
			break;
		case 1:
			PaintTool.setColor(new Color(q, x, p));
			break;
		case 2:
			PaintTool.setColor(new Color(p, x, t));
			break;
		case 3:
			PaintTool.setColor(new Color(p, q, x));
			break;
		case 4:
			PaintTool.setColor(new Color(t, p, x));
			break;
		case 5:
			PaintTool.setColor(new Color(x, p, q));
			break;
		}
		ColorPanel.t1.setText(String.valueOf(PaintTool.getColor().getRed()));
		ColorPanel.t2.setText(String.valueOf(PaintTool.getColor().getGreen()));
		ColorPanel.t3.setText(String.valueOf(PaintTool.getColor().getBlue()));
		ColorPanel.s1.setValue(PaintTool.getColor().getRed());
		ColorPanel.s2.setValue(PaintTool.getColor().getGreen());
		ColorPanel.s3.setValue(PaintTool.getColor().getBlue());
		ColorPanel.cb[PaintTool.colornum].setBackground(PaintTool.getColor());
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
		if(x > 255)x = 255;
		if(x < 0)x = 0;
		if(y > 255)y = 255;
		if(y < 0)y = 0;
		int hi , p, q , t;
		float f;
		hi = (int) (Math.floor(z / 60.0) % 6);
		f  = (float) ((z / 60.0f) - Math.floor(z / 60.0f));
		p  = Math.round(x * (1.0f - (y / 255.0f)));
		q  = Math.round(x * (1.0f - (y / 255.0f) * f));
		t  = Math.round(x * (1.0f - (y / 255.0f) * (1.0f - f)));
		switch(hi){
		case 0:
			PaintTool.setColor(new Color(x, t, p));
			break;
		case 1:
			PaintTool.setColor(new Color(q, x, p));
			break;
		case 2:
			PaintTool.setColor(new Color(p, x, t));
			break;
		case 3:
			PaintTool.setColor(new Color(p, q, x));
			break;
		case 4:
			PaintTool.setColor(new Color(t, p, x));
			break;
		case 5:
			PaintTool.setColor(new Color(x, p, q));
			break;
		}
		ColorPanel.t1.setText(String.valueOf(PaintTool.getColor().getRed()));
		ColorPanel.t2.setText(String.valueOf(PaintTool.getColor().getGreen()));
		ColorPanel.t3.setText(String.valueOf(PaintTool.getColor().getBlue()));
		ColorPanel.s1.setValue(PaintTool.getColor().getRed());
		ColorPanel.s2.setValue(PaintTool.getColor().getGreen());
		ColorPanel.s3.setValue(PaintTool.getColor().getBlue());
		ColorPanel.cb[PaintTool.colornum].setBackground(PaintTool.getColor());
		repaint();
		PaintTool.rewrite();
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == s) {
			z = s.getValue();
			int hi , p, q , t;
			float f;
			hi = (int) (Math.floor(z / 60.0) % 6);
			f  = (float) ((z / 60.0f) - Math.floor(z / 60.0f));
			p  = Math.round(x * (1.0f - (y / 255.0f)));
			q  = Math.round(x * (1.0f - (y / 255.0f) * f));
			t  = Math.round(x * (1.0f - (y / 255.0f) * (1.0f - f)));
			switch(hi){
			case 0:
				PaintTool.setColor(new Color(x, t, p));
				break;
			case 1:
				PaintTool.setColor(new Color(q, x, p));
				break;
			case 2:
				PaintTool.setColor(new Color(p, x, t));
				break;
			case 3:
				PaintTool.setColor(new Color(p, q, x));
				break;
			case 4:
				PaintTool.setColor(new Color(t, p, x));
				break;
			case 5:
				PaintTool.setColor(new Color(x, p, q));
				break;
			}
			ColorPanel.t1.setText(String.valueOf(PaintTool.getColor().getRed()));
			ColorPanel.t2.setText(String.valueOf(PaintTool.getColor().getGreen()));
			ColorPanel.t3.setText(String.valueOf(PaintTool.getColor().getBlue()));
			ColorPanel.s1.setValue(PaintTool.getColor().getRed());
			ColorPanel.s2.setValue(PaintTool.getColor().getGreen());
			ColorPanel.s3.setValue(PaintTool.getColor().getBlue());
			ColorPanel.cb[PaintTool.colornum].setBackground(PaintTool.getColor());
			repaint();
		}
	}
}
