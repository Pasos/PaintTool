import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SelectOptionFrame extends JFrame implements ActionListener, ChangeListener{
	private static final long serialVersionUID = 2L;

	static public JRadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11;
	private ButtonGroup gr1, gr2, gr3;
	private JLabel l1, l2, l3, l4, l5, l6, l7;
	static public JTextField t1, t2, t3, t4;
	public static JSlider s, s2, s3;
	public static JButton b;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}

	static float gettrans() {
		try {
			return Float.parseFloat(t1.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}

	static int getpoly() {
		try {
			return Integer.parseInt(t2.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 3;
		}
	}

	/*★★コンストラクタ★★*/
	SelectOptionFrame() {

	    setTitle("選択ツールOption");
	    setSize(240, 320);
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());

		rb1 = new JRadioButton("■",true);
		rb2 = new JRadioButton("●");
		rb8 = new JRadioButton("線");
		rb9 = new JRadioButton("多角形");
		rb10= new JRadioButton("ﾏｼﾞｯｸﾜﾝﾄﾞ");
		rb3 = new JRadioButton("外  ",true);
		rb4 = new JRadioButton("中  ");
		rb11= new JRadioButton("無し");
		rb5 = new JRadioButton("移動",true);
		rb6 = new JRadioButton("複製");
		rb7 = new JRadioButton("枠のみ");
		l1 = new JLabel("●範囲枠形      ");
		l2 = new JLabel("●ﾏｽｸ範囲");
		l3 = new JLabel("  ●特性");
		l4 = new JLabel("●透明度                                 ");
		l5 = new JLabel("画数");
		l6 = new JLabel("しきい値");
		l7 = new JLabel("繰り抜き省略サイズ");
		t1 = new JTextField("1.0",5);
		t2 = new JTextField("3",2);
		t3 = new JTextField("0",5);
		t4 = new JTextField("省略無し",5);
		s = new JSlider(SwingConstants.HORIZONTAL, 0, 768, 1);
		s2 = new JSlider(SwingConstants.HORIZONTAL, 0, 1001, 0);
		s3 = new JSlider(SwingConstants.HORIZONTAL, 0, 10000, 10000);
		b = new JButton("選択範囲開放");
		b.setEnabled(false);
		add(l1);
		add(rb1);
		add(rb2);
		add(rb8);
		add(rb9);
		add(l5);
		add(t2);
		add(rb10);
		add(l6);
		add(t3);
		add(s);
		add(l7);
		add(t4);
		add(s2);
		add(l2);
		add(rb3);
		add(rb4);
		add(rb11);
		add(l3);
		add(rb5);
		add(rb6);
		add(rb7);
		add(l4);
		add(t1);
		add(s3);
		add(b);

		gr1 = new ButtonGroup();
		gr2 = new ButtonGroup();
		gr3 = new ButtonGroup();
		gr1.add(rb1);
		gr1.add(rb2);
		gr1.add(rb8);
		gr1.add(rb9);
		gr1.add(rb10);
		gr2.add(rb3);
		gr2.add(rb4);
		gr2.add(rb11);
		gr3.add(rb5);
		gr3.add(rb6);
		gr3.add(rb7);
		
		
		
		 addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					PaintTool.selectm.setSelected(false);
				}

				public void windowIconified(WindowEvent e) {
				}

				public void windowOpened(WindowEvent e) {
				}
			});
		
		l5.setEnabled(false);
		t2.setEnabled(false);
		l6.setEnabled(false);
		l7.setEnabled(false);
		t3.setEnabled(false);
		t4.setEnabled(false);
		s.setEnabled(false);
		s2.setEnabled(false);
		rb5.addActionListener(this);
		rb6.addActionListener(this);
		rb7.addActionListener(this);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb8.addActionListener(this);
		rb9.addActionListener(this);
		rb10.addActionListener(this);
		t3.addActionListener(this);
		t4.addActionListener(this);
		t1.addActionListener(this);
		b.addActionListener(this);
		s.addChangeListener(this);
		s2.addChangeListener(this);
		s3.addChangeListener(this);
		setResizable(false);
		setVisible(false);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rb5) {
			if(DrawPanel.set){
				if(PaintTool.selectframetype != 0){
					if(PaintTool.selectframetype != 2)PaintTool.selectRectDraw();
					DrawPanel.dontfill = true;
					PaintTool.capture();
					DrawPanel.capt = true;
					DrawPanel.listname = "選択タイプ変更";
					PaintTool.rewrite();
				}
			}
			PaintTool.selectframetype = 0;
		}else if (e.getSource() == rb6) {
			if(DrawPanel.set){
				if(PaintTool.selectframetype != 1){
					if(!DrawPanel.dontfill && PaintTool.selectframetype != 2)PaintTool.selectRectDraw();
					DrawPanel.dontfill = true;
					PaintTool.capture();
					DrawPanel.capt = true;
					DrawPanel.listname = "選択タイプ変更";
					PaintTool.rewrite();
				}
			}
			PaintTool.selectframetype = 1;
		}else if (e.getSource() == rb7) {
			if(DrawPanel.set){
				if(PaintTool.selectframetype != 2){
					PaintTool.selectRectDraw();
					DrawPanel.capt = true;
					DrawPanel.listname = "選択タイプ変更";
					PaintTool.rewrite();
				}
			}
			PaintTool.selectframetype = 2;
		}else if (e.getSource() == rb1) {
			l5.setEnabled(false);
			t2.setEnabled(false);
			l6.setEnabled(false);
			l7.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
			s.setEnabled(false);
			s2.setEnabled(false);
			if(PaintTool.getType() == 12){
				PaintTool.p1.remove(ToolPanel.b9);
				ToolPanel.b9.setEnabled(false);
				PaintTool.p1.repaint();
				DrawPanel.pcnt2 = 0;
			}
		}else if (e.getSource() == rb2) {
			l5.setEnabled(false);
			t2.setEnabled(false);
			l6.setEnabled(false);
			l7.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
			s.setEnabled(false);
			s2.setEnabled(false);
			if(PaintTool.getType() == 12){
				PaintTool.p1.remove(ToolPanel.b9);
				ToolPanel.b9.setEnabled(false);
				PaintTool.p1.repaint();
				DrawPanel.pcnt2 = 0;
			}
		}else if (e.getSource() == rb8) {
			l5.setEnabled(false);
			t2.setEnabled(false);
			l6.setEnabled(false);
			l7.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
			s.setEnabled(false);
			s2.setEnabled(false);
			if(PaintTool.getType() == 12){
				PaintTool.p1.remove(ToolPanel.b9);
				ToolPanel.b9.setEnabled(false);
				PaintTool.p1.repaint();
				DrawPanel.pcnt2 = 0;
			}
		}else if (e.getSource() == rb9) {
			l5.setEnabled(true);
			t2.setEnabled(true);
			l6.setEnabled(false);
			l7.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
			s.setEnabled(false);
			s2.setEnabled(false);
			if(PaintTool.getType() == 12){
				PaintTool.p1.add(ToolPanel.b9);
				ToolPanel.b9.setEnabled(false);
				PaintTool.p1.repaint();
				DrawPanel.pcnt2 = 0;
			}
		}else if (e.getSource() == rb10) {
			l5.setEnabled(false);
			t2.setEnabled(false);
			l6.setEnabled(true);
			l7.setEnabled(true);
			t3.setEnabled(true);
			t4.setEnabled(true);
			s.setEnabled(true);
			s2.setEnabled(true);
			if(PaintTool.getType() == 12){
				PaintTool.p1.remove(ToolPanel.b9);
				PaintTool.p1.repaint();
				DrawPanel.pcnt2 = 0;
				DrawPanel.pcnt = 0;
			}
		}else if (e.getSource() == t3) {
			if(Integer.parseInt(t3.getText()) >= 0 && Integer.parseInt(t3.getText()) <= 768){
				s.setValue(Integer.parseInt(t3.getText()));
			}else{
				t3.setText(String.valueOf(s.getValue()));
			}
		}else if (e.getSource() == t4) {
			if(t4.getText() == "省略無し"){
				s2.setValue(0);
			}else if(t4.getText() == "全て省略"){
				s2.setValue(1001);
			}else if(Integer.parseInt(t4.getText()) >= 0 && Integer.parseInt(t4.getText()) <= 768){
				s2.setValue(Integer.parseInt(t4.getText()));
			}else{
				t4.setText(String.valueOf(s2.getValue()));
			}
		}else if (e.getSource() == t1) {
			if(Float.parseFloat(t1.getText()) >= 0 && Float.parseFloat(t1.getText()) <= 1){
				s3.setValue(Math.round(10000*Float.parseFloat(t1.getText())));
			}else{
				t1.setText(String.valueOf(s3.getValue()));
			}
		}else if (e.getSource() == b) {
			if(DrawPanel.set){
				DrawPanel.capt = true;
				DrawPanel.listname = "選択範囲開放";
				PaintTool.comand = 13;
				PaintTool.rewrite();
			}
		}
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == s) {
			t3.setText(String.valueOf(s.getValue()));
		}else if (e.getSource() == s2) {
			if(s2.getValue() == 0){
				t4.setText("省略無し");
			}else if(s2.getValue() == 1001){
				t4.setText("全て省略");
			}else{
				t4.setText(String.valueOf(s2.getValue()));
			}
		}else if (e.getSource() == s3) {
			t1.setText(String.valueOf((float)s3.getValue()/10000));
		}
	}
}
