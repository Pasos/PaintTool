import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
;

public class ChangeSize extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2L;

	public JButton b1, b2;
	public JTextField t1, t2;
	private JLabel l1, l2;
	public int type;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}

	/*★★コンストラクタ★★*/
	ChangeSize() {

	    setTitle("サイズ変更");
	    setSize(270, 100);
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());

		b1 = new JButton("キャンセル");
		b2 = new JButton("変更");
		t1 = new JTextField(String.valueOf(PaintTool.sizex), 6);
		t2 = new JTextField(String.valueOf(PaintTool.sizey), 6);
		l1 = new JLabel("x軸");
		l2 = new JLabel("y軸");

		add(l1);
		add(t1);
		add(l2);
		add(t2);
		add(b1);
		add(b2);

		setResizable(false);
		b1.addActionListener(this);
		b2.addActionListener(this);
		setVisible(false);


	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) { // キャンセル
			invisible();
		} else if (e.getSource() == b2) { // サイズ変更
			if(Integer.parseInt(t1.getText())*Integer.parseInt(t2.getText()) <= 4000*4000 || !(PaintTool.sizex == Integer.parseInt(t1.getText()) && PaintTool.sizey == Integer.parseInt(t2.getText()))){
				if(type == 0){
					PaintTool.sizex = Integer.parseInt(t1.getText());
					PaintTool.sizey = Integer.parseInt(t2.getText());
					PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
					PaintTool.p3.revalidate();
					DrawPanel.capt = true;
					PaintTool.comand = 6;
					DrawPanel.listname = "サイズ変更";
					PaintTool.rewrite();
				}else{
					PaintTool.sizex = Integer.parseInt(t1.getText());
					PaintTool.sizey = Integer.parseInt(t2.getText());
					PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
					PaintTool.p3.revalidate();
					DrawPanel.capt = true;
					PaintTool.comand = 1;
					DrawPanel.listname = "新しいキャンバス";
					PaintTool.rewrite();
				}
			}
			invisible();
		}
	}
}
