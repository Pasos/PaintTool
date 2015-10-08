import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class MoziFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2L;

	public JRadioButton rb1, rb2, rb3;
	public static JRadioButton rb4, rb5, rb6;
	public JComboBox<String> cb;
	public static JComboBox<String> cb2;
	private ButtonGroup gr, gr2;
	private JTextField t1, t2;
	private JLabel l1, l2, l3, l4;
	public static JCheckBox cb1;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}

	String getMozi(){
		return t2.getText();
	}

	int getsize(){
		try{
            return Integer.parseInt(t1.getText());
        }
        catch(NumberFormatException  e){
        	System.out.println("Error");
        	return 0;
        }
	}

	/*★★コンストラクタ★★*/
	MoziFrame() {

	    setTitle("文字");
	    setSize(330, 160);
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());

		rb1 = new JRadioButton("PLAIN",true);
		rb2 = new JRadioButton("ITARIC");
		rb3 = new JRadioButton("BOLD");
		t1 = new JTextField("30", 5);
		t2 = new JTextField("文字", 23);
		l1 = new JLabel("サイズ");
		l2 = new JLabel("文字");
		l3 = new JLabel("フォント");
		cb = new JComboBox<String>();
		cb.addItem("MSゴシック");
		cb.addItem("MS明朝");
		cb.addItem("HG行書体");
		cb.addItem("Arial");
		cb.addItem("Century");
		cb.addItem("Algerian");
		cb.addItem("Blackadder ITC");
		cb.addItem("Burnstown Dam");

		cb2 = new JComboBox<String>();
		cb2.addItem("左上");
		cb2.addItem("上");
		cb2.addItem("右上");
		cb2.addItem("右");
		cb2.addItem("右下");
		cb2.addItem("下");
		cb2.addItem("左下");
		cb2.addItem("左");
		cb2.setSelectedIndex(4);
		cb1 = new JCheckBox("影");
		l4 = new JLabel("影使用色");
		rb4 = new JRadioButton("パレット1",true);
		rb5 = new JRadioButton("パレット2");
		rb6 = new JRadioButton("パレット3");


		add(rb1);
		add(rb2);
		add(rb3);
		add(l1);
		add(t1);
		add(l2);
		add(t2);
		add(l3);
		add(cb);
		add(cb1);
		add(cb2);
		add(l4);
		add(rb4);
		add(rb5);
		add(rb6);
		cb2.setEnabled(false);
		l4.setEnabled(false);
		rb4.setEnabled(false);
		rb5.setEnabled(false);
		rb6.setEnabled(false);
		gr = new ButtonGroup();
		gr.add(rb1);
		gr.add(rb2);
		gr.add(rb3);
		gr2 = new ButtonGroup();
		gr2.add(rb4);
		gr2.add(rb5);
		gr2.add(rb6);
		cb1.addActionListener(this);

		setResizable(false);
		setVisible(false);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cb1) {
			cb2.setEnabled(cb1.isSelected());
			l4.setEnabled(cb1.isSelected());
			rb4.setEnabled(cb1.isSelected());
			rb5.setEnabled(cb1.isSelected());
			rb6.setEnabled(cb1.isSelected());
		}
	}
}
