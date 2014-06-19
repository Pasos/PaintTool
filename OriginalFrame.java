import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class OriginalFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2L;

	public static JRadioButton rb1, rb2;
	private ButtonGroup gr;
	public  JButton b1, b2;
	public JPanel p1;
	public OriginalPanel p2;
	private int sizex, sizey;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}

	/*★★コンストラクタ★★*/
	OriginalFrame() {

	    setTitle("ペン先");
	    setSize(280, 310);
	    sizex = 280;
	    sizey = 310;
	    Container c = getContentPane();
	    c.setLayout(new BorderLayout());
	    p1 = new JPanel();
	    rb1 = new JRadioButton("■",true);
		rb2 = new JRadioButton("●");
		b1 = new JButton("１つ消す");
		b2 = new JButton("Clear");
		p1.add(rb1);
		p1.add(rb2);
		p1.add(b1);
		p1.add(b2);
		gr = new ButtonGroup();
		gr.add(rb1);
		gr.add(rb2);
	    p2 = new OriginalPanel();
	    c.add(p1, BorderLayout.NORTH);
		c.add(p2, BorderLayout.CENTER);
		c.setBackground(Color.white);

		b1.addActionListener(this);
		b2.addActionListener(this);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		p2.addMouseListener(p2);
		p2.addMouseMotionListener(p2);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int changex, changey;
				changex = (PaintTool.p8.getWidth() - sizex)/2;
				changey = (PaintTool.p8.getHeight() - sizey)/2;
				for(int i = 0;i < OriginalPanel.cnt ;i++){
					OriginalPanel.x[i] += changex;
					OriginalPanel.y[i] += changey;
				}
				PaintTool.p8.p2.repaint();
				sizex = PaintTool.p8.getWidth();
				sizey = PaintTool.p8.getHeight();
			}

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}
		});

		setVisible(false);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rb1) {//■
			p2.repaint();
		} else if (e.getSource() == rb2) {//●
			p2.repaint();
		} else if (e.getSource() == b1) {//１つ消す
			if(OriginalPanel.cnt > 0){
				OriginalPanel.cnt--;
				p2.repaint();
			}
		} else if (e.getSource() == b2) {//clear
			OriginalPanel.cnt = 0;
			p2.repaint();
		}
	}
}
