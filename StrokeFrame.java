import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StrokeFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2L;

	static public JCheckBox cb;
	private JLabel l1, l2, l3;
	static public JTextField t1, t2, t3, t4;
	static public JRadioButton r1, r2, r3, r4, r5;
	private ButtonGroup gr1, gr2;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}
	
	float[] getpattern() {
		try {
			if(cb.isSelected()){
				if(r3.isSelected()){
					float[] r = {Float.parseFloat(t1.getText())};
					return r;
				}else if(r4.isSelected()){
					float[] r = {Float.parseFloat(t1.getText()),Float.parseFloat(t2.getText())};
					return r;
				}else{
					float[] r = {Float.parseFloat(t1.getText()), Float.parseFloat(t2.getText()), Float.parseFloat(t3.getText()), Float.parseFloat(t4.getText())};
					return r;
				}
			}else{
				float[] r = {0};
				return r;
			}
		} catch (NumberFormatException e) {
			System.out.println("Error");
			float[] r = {0};
			return r;
		}
	}
	
	int getborder() {
		if(r1.isSelected()){
			return BasicStroke.CAP_ROUND;
		}else{
			return BasicStroke.CAP_SQUARE;
		}
	}
	
	/*★★コンストラクタ★★*/
	StrokeFrame() {

	    setTitle("破線");
	    setSize(200, 270);
	    Container c = getContentPane();
	    c.setLayout(new GridLayout(8, 1));
	    
	    
	    l1 = new JLabel("縁");
	    r1 = new JRadioButton("●", true);
	    r2 = new JRadioButton("■");
	    cb = new JCheckBox("破線" ,false);
	    r3 = new JRadioButton("1", true);
	    r4 = new JRadioButton("2");
	    r5 = new JRadioButton("4");
	    l2 = new JLabel("パターン列数");
	    l3 = new JLabel("パターン列");
	    t1 = new JTextField("5",3);
		t2 = new JTextField("10",3);
		t3 = new JTextField("20",3);
		t4 = new JTextField("20",3);
		
		gr1 = new ButtonGroup();
		gr2 = new ButtonGroup();
		gr1.add(r1);
		gr1.add(r2);
		gr2.add(r3);
		gr2.add(r4);
		gr2.add(r5);
		
		add(l1);
		JPanel p = new JPanel();
		p.add(r1);
		p.add(r2);
		add(p);
		
		add(cb);
		add(l2);
		JPanel p2 = new JPanel();
		p2.add(r3);
		p2.add(r4);
		p2.add(r5);
		add(p2);
		
		add(l3);
		JPanel p3 = new JPanel();
		p3.add(t1);
		p3.add(t2);
		p3.add(t3);
		p3.add(t4);
		add(p3);
		
		l2.setEnabled(false);
		r3.setEnabled(false);
		r4.setEnabled(false);
		r5.setEnabled(false);
		l3.setEnabled(false);
		t1.setEnabled(false);
		t2.setEnabled(false);
		t3.setEnabled(false);
		t4.setEnabled(false);
		setResizable(false);
		setVisible(false);
		
		cb.addActionListener(this);
		r3.addActionListener(this);
		r4.addActionListener(this);
		r5.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == cb) {
			if(cb.isSelected()){
				l2.setEnabled(true);
				r3.setEnabled(true);
				r4.setEnabled(true);
				r5.setEnabled(true);
				l3.setEnabled(true);
				t1.setEnabled(true);
				if(r4.isSelected()){
					t2.setEnabled(true);
					t3.setEnabled(false);
					t4.setEnabled(false);
				}else if(r5.isSelected()){
					t2.setEnabled(true);
					t3.setEnabled(true);
					t4.setEnabled(true);
				}
			}else{
				l2.setEnabled(false);
				r3.setEnabled(false);
				r4.setEnabled(false);
				r5.setEnabled(false);
				l3.setEnabled(false);
				t1.setEnabled(false);
				t2.setEnabled(false);
				t3.setEnabled(false);
				t4.setEnabled(false);
				
			}
		}else if (e.getSource() == r3) {
			t2.setEnabled(false);
			t3.setEnabled(false);
			t4.setEnabled(false);
		}else if (e.getSource() == r4) {
			t2.setEnabled(true);
			t3.setEnabled(false);
			t4.setEnabled(false);
		}else if (e.getSource() == r5) {
			t2.setEnabled(true);
			t3.setEnabled(true);
			t4.setEnabled(true);
		}
	}
}
