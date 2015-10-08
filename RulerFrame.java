import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RulerFrame extends JFrame  implements ActionListener, ChangeListener{
	private static final long serialVersionUID = 2L;

	static public JCheckBox cb;
	private JLabel l1, l2, l3, l4, l5, l6, l7, l8;
	static public JTextField t1, t2, t3, t4;
	public static JSlider s, s2;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}
	
	int getsize() {
		try {
			return Integer.parseInt(t1.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 1;
		}
	}
	
	int getsize2() {
		try {
			return Integer.parseInt(t2.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 1;
		}
	}
	
	int getsize3() {
		try {
			return Integer.parseInt(t3.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 1;
		}
	}
	
	int getsize4() {
		try {
			return Integer.parseInt(t4.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 1;
		}
	}
	
	/*★★コンストラクタ★★*/
	RulerFrame() {

	    setTitle("ルーラー");
	    setSize(200, 270);
	    Container c = getContentPane();
	    c.setLayout(new GridLayout(8, 1));
	    
	    cb = new JCheckBox("グリッド線を表示" ,false);
		l1 = new JLabel("メモリ幅");
		l2 = new JLabel("x軸");
		l3 = new JLabel("y軸");
		l4 = new JLabel("初期位置");
		l5 = new JLabel("x軸");
		l6 = new JLabel("y軸");
		l7 = new JLabel("%");
		l8 = new JLabel("%");
		t1 = new JTextField("100",5);
		t2 = new JTextField("100",5);
		t3 = new JTextField("0",5);
		t4 = new JTextField("0",5);
		s =  new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		s2 = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		
		add(cb);
		
		add(l1);
		JPanel p = new JPanel();
		p.add(l2);
		p.add(t1);
		add(p);
		p.add(l3);
		p.add(t2);
		
		add(l4);
		JPanel p2 = new JPanel();
		p2.add(l5);
		p2.add(t3);
		p2.add(l7);
		add(p2);
		add(s);
		
		JPanel p3 = new JPanel();
		p3.add(l6);
		p3.add(t4);
		p3.add(l8);
		add(p3);
		add(s2);
		
		
		cb.addActionListener(this);
		t1.addActionListener(this);
		t2.addActionListener(this);
		t3.addActionListener(this);
		t4.addActionListener(this);
		s.addChangeListener(this);
		s2.addChangeListener(this);
		setResizable(false);
		setVisible(false);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t3) {
			if(Integer.parseInt(t3.getText()) >= 0 && Integer.parseInt(t3.getText()) <= 100){
				s.setValue(Integer.parseInt(t3.getText()));
			}else{
				t3.setText(String.valueOf(s.getValue()));
			}
		}else if (e.getSource() == t4) {
			if(Integer.parseInt(t3.getText()) >= 0 && Integer.parseInt(t3.getText()) <= 100){
				s.setValue(Integer.parseInt(t3.getText()));
			}else{
				t3.setText(String.valueOf(s.getValue()));
			}
		}
		PaintTool.rewrite();
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == s) {
			t3.setText(String.valueOf(s.getValue()));
		}else if (e.getSource() == s2) {
			t4.setText(String.valueOf(s2.getValue()));
		}
		PaintTool.rewrite();
	}
}
