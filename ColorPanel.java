import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorPanel extends JPanel implements ActionListener, ChangeListener {

	private static final long serialVersionUID = 2L;
	private JLabel l1, l3, l4, l5;
	static public JTextField t1, t2, t3;
    static public JButton[] cb = new JButton[24];
    static public JButton cw;
    static public JSlider s1, s2, s3;
    static public JPanel p1, p2, p3, p4;


	/*★★コンストラクタ★★*/
	ColorPanel() {
		setLayout(new FlowLayout());
		
		p4 = new JPanel();
		p4.setLayout(new GridLayout(1, 1));
		l1 = new JLabel("　　　　 色");
		p4.add(l1);
		add(p4);
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(10, 3));
		
		cb[0] = new JButton("o");
		cb[0].setBackground(Color.black);
		cb[0].setForeground(Color.white);
		p1.add(cb[0]);
		cb[1] = new JButton(" ");
		cb[1].setBackground(Color.gray);
		p1.add(cb[1]);
		cb[2] = new JButton(" ");
		cb[2].setBackground(Color.white);
		p1.add(cb[2]);
		cb[3] = new JButton(" ");
		cb[3].setBackground(Color.red);
		p1.add(cb[3]);
		cb[4] = new JButton(" ");
		cb[4].setBackground(Color.green);
		p1.add(cb[4]);
		cb[5] = new JButton(" ");
		cb[5].setBackground(Color.blue);
		p1.add(cb[5]);
		cb[6] = new JButton(" ");
		cb[6].setBackground(Color.cyan);
		p1.add(cb[6]);
		cb[7] = new JButton(" ");
		cb[7].setBackground(Color.yellow);
		p1.add(cb[7]);
		cb[8] = new JButton(" ");
		cb[8].setBackground(Color.magenta);
		p1.add(cb[8]);
		cb[9] = new JButton(" ");
		cb[9].setBackground(Color.orange);
		p1.add(cb[9]);
		cb[10] = new JButton(" ");
		cb[10].setBackground(Color.pink);
		p1.add(cb[10]);
		cb[11] = new JButton(" ");
		cb[11].setBackground(new Color(80,80,80));
		p1.add(cb[11]);
		cb[12] = new JButton(" ");
		cb[12].setBackground(new Color(0,128,128));
		p1.add(cb[12]);
		cb[13] = new JButton(" ");
		cb[13].setBackground(new Color(128,0,128));
		p1.add(cb[13]);
		cb[14] = new JButton(" ");
		cb[14].setBackground(new Color(128,128,0));
		p1.add(cb[14]);
		cb[15] = new JButton(" ");
		cb[15].setBackground(new Color(0,0,128));
		p1.add(cb[15]);
		cb[16] = new JButton(" ");
		cb[16].setBackground(new Color(0,128,0));
		p1.add(cb[16]);
		cb[17] = new JButton(" ");
		cb[17].setBackground(new Color(128,0,0));
		p1.add(cb[17]);
		cb[18] = new JButton(" ");
		cb[18].setBackground(new Color(255,255,255));
		p1.add(cb[18]);
		cb[19] = new JButton(" ");
		cb[19].setBackground(new Color(255,255,255));
		p1.add(cb[19]);
		cb[20] = new JButton(" ");
		cb[20].setBackground(new Color(255,255,255));
		p1.add(cb[20]);
		cb[21] = new JButton(" ");
		cb[21].setBackground(new Color(255,255,255));
		p1.add(cb[21]);
		cb[22] = new JButton(" ");
		cb[22].setBackground(new Color(255,255,255));
		p1.add(cb[22]);
		cb[23] = new JButton(" ");
		cb[23].setBackground(new Color(255,255,255));
		p1.add(cb[23]);
		l3 = new JLabel("赤");
		l4 = new JLabel("緑");
		l5 = new JLabel("青");
		p1.add(l3);
		p1.add(l4);
		p1.add(l5);
		t1 = new JTextField("0");
		t2 = new JTextField("0");
		t3 = new JTextField("0");
		p1.add(t1);
		p1.add(t2);
		p1.add(t3);
		add(p1);

		p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 3));
		s1 = new JSlider(SwingConstants.VERTICAL, 0, 255, 0);
		s2 = new JSlider(SwingConstants.VERTICAL, 0, 255, 0);
		s3 = new JSlider(SwingConstants.VERTICAL, 0, 255, 0);
		p2.add(s1);
		p2.add(s2);
		p2.add(s3);
		add(p2);
		p3 = new JPanel();
		p3.setLayout(new GridLayout(1, 1));
		cw = new JButton("HSV選択");
		p3.add(cw);
		add(p3);
		for(int i = 0;i < 24;i++){
			cb[i].addActionListener(this);
		}
		cw.addActionListener(this);
		t1.addActionListener(this);
		t2.addActionListener(this);
		t3.addActionListener(this);
		s1.addChangeListener(this);
		s2.addChangeListener(this);
		s3.addChangeListener(this);


	}

	/*★★動作系★★*/

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == s1) {
			PaintTool.setColor(new Color(s1.getValue(),PaintTool.getColor().getGreen(),PaintTool.getColor().getBlue()));
		}else if (e.getSource() == s2) {
			PaintTool.setColor(new Color(PaintTool.getColor().getRed(),s2.getValue(),PaintTool.getColor().getBlue()));
		}else if (e.getSource() == s3) {
			PaintTool.setColor(new Color(PaintTool.getColor().getRed(),PaintTool.getColor().getGreen(),s3.getValue()));
		}
		if(PaintTool.getColor().getRed() + PaintTool.getColor().getGreen() + PaintTool.getColor().getBlue() <255){
			cb[PaintTool.colornum].setForeground(Color.white);
		}else{
			cb[0].setForeground(Color.black);
		}
		int max = Math.max(PaintTool.getColor().getRed(), Math.max(PaintTool.getColor().getGreen(), PaintTool.getColor().getBlue()));
		int min = Math.min(PaintTool.getColor().getRed(), Math.min(PaintTool.getColor().getGreen(), PaintTool.getColor().getBlue()));
		if(max == min){
			ColorFrame.z = 0;
		}else if(max == PaintTool.getColor().getRed()){
			ColorFrame.z = Math.round(60 * (PaintTool.getColor().getGreen() - PaintTool.getColor().getBlue()) / (float)(max - min) + 360) % 360;
		}else if(max == PaintTool.getColor().getGreen()){
			ColorFrame.z = Math.round(60 * (PaintTool.getColor().getBlue() - PaintTool.getColor().getRed()) / (float)(max - min)) + 120;
			
		}else if(max == PaintTool.getColor().getBlue()){
			ColorFrame.z = Math.round(60 * (PaintTool.getColor().getRed() - PaintTool.getColor().getGreen()) / (float)(max - min)) + 240;
		}
		if(max == 0){
			ColorFrame.y = 0;
		}else{
			ColorFrame.y = Math.round(255 * ((max - min) / (float)max));
		}
		ColorFrame.x = max;
		ColorFrame.s.setValue(ColorFrame.z);
		PaintTool.p13.repaint();
		t1.setText(String.valueOf(PaintTool.getColor().getRed()));
		t2.setText(String.valueOf(PaintTool.getColor().getGreen()));
		t3.setText(String.valueOf(PaintTool.getColor().getBlue()));
		cb[PaintTool.colornum].setBackground(PaintTool.getColor());
	}
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == cw) { // ビュー
			 if(PaintTool.p13.frame.isVisible()){
				 PaintTool.hsvInvisible();
				 PaintTool.hsvm.setSelected(false);
			 }else{
				 PaintTool.hsvVisible();
				 PaintTool.hsvm.setSelected(true);
			 }
		 }else{
			 for(int i = 0 ;i < 24;i++){
					if (e.getSource() == cb[i]) {
						PaintTool.colornum = i;
						cb[i].setText("o");
					}else{
						cb[i].setText("");
					}
				}
				if (e.getSource() == t1 || e.getSource() == t2 || e.getSource() == t3) {
					PaintTool.setColor(new Color(Integer.parseInt(t1.getText()),Integer.parseInt(t2.getText()),Integer.parseInt(t3.getText())));
					if(PaintTool.getColor().getRed() + PaintTool.getColor().getGreen() + PaintTool.getColor().getBlue() <255){
						cb[0].setForeground(Color.white);
					}else{
						cb[0].setForeground(Color.black);
					}
				}
				int max = Math.max(PaintTool.getColor().getRed(), Math.max(PaintTool.getColor().getGreen(), PaintTool.getColor().getBlue()));
				int min = Math.min(PaintTool.getColor().getRed(), Math.min(PaintTool.getColor().getGreen(), PaintTool.getColor().getBlue()));
				if(max == min){
					ColorFrame.z = 0;
				}else if(max == PaintTool.getColor().getRed()){
					ColorFrame.z = Math.round(60 * (PaintTool.getColor().getGreen() - PaintTool.getColor().getBlue()) / (float)(max - min) + 360) % 360;
				}else if(max == PaintTool.getColor().getGreen()){
					ColorFrame.z = Math.round(60 * (PaintTool.getColor().getBlue() - PaintTool.getColor().getRed()) / (float)(max - min)) + 120;
					
				}else if(max == PaintTool.getColor().getBlue()){
					ColorFrame.z = Math.round(60 * (PaintTool.getColor().getRed() - PaintTool.getColor().getGreen()) / (float)(max - min)) + 240;
				}
				if(max == 0){
					ColorFrame.y = 0;
				}else{
					ColorFrame.y = Math.round(255 * ((max - min) / (float)max));
				}
				ColorFrame.x = max;
				ColorFrame.s.setValue(ColorFrame.z);
				PaintTool.p13.repaint();
				t1.setText(String.valueOf(PaintTool.getColor().getRed()));
				t2.setText(String.valueOf(PaintTool.getColor().getGreen()));
				t3.setText(String.valueOf(PaintTool.getColor().getBlue()));
				s1.setValue(PaintTool.getColor().getRed());
				s2.setValue(PaintTool.getColor().getGreen());
				s3.setValue(PaintTool.getColor().getBlue());
				cb[PaintTool.colornum].setBackground(PaintTool.getColor());
		 }
	}
}