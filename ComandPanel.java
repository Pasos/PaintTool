import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ComandPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2L;
	
	public JTextField t1;
	private JLabel l1, l2, l3;
	public JLabel l4, l5, l6, l7, l8, l9, l10, l11;
	public static JButton b2, b3, b4, b5, b6, b7, b8, b9, b10, b11,b12,b13,b14,b15,b16,b17;
	ImageIcon i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13,i14,i15,i16,i17;
	public static float tmpx = 0, tmpy = 0;
	JPanel p;
    
    public void drawl3(boolean a){
    	if(a){
    		add(l3);
    	}else{
    		remove(l3);
    	}
    }

	/*★★コンストラクタ★★*/
	ComandPanel() {
		this.setLayout(new FlowLayout());
		i2 = new ImageIcon(getClass().getResource("新規.png"));
		b2 = new JButton(i2);
		b2.setToolTipText("新しいキャンバス");
		b2.setMargin(new Insets(0,0,0,0));
		b2.setBackground(Color.white);
		i3 = new ImageIcon(getClass().getResource("戻る.png"));
		b3 = new JButton(i3);
		b3.setToolTipText("元に戻す");
		b3.setMargin(new Insets(0,0,0,0));
		b3.setBackground(Color.white);
		i4 = new ImageIcon(getClass().getResource("進む.png"));
		b4 = new JButton(i4);
		b4.setToolTipText("やり直す");
		b4.setMargin(new Insets(0,0,0,0));
		b4.setBackground(Color.white);
		i5 = new ImageIcon(getClass().getResource("名前付け保存.png"));
		b5 = new JButton(i5);
		b5.setToolTipText("名前をつけて保存");
		b5.setMargin(new Insets(0,0,0,0));
		b5.setBackground(Color.white);
		i6 = new ImageIcon(getClass().getResource("サイズ変更.png"));
		b6 = new JButton(i6);
		b6.setToolTipText("サイズ変更");
		b6.setMargin(new Insets(0,0,0,0));
		b6.setBackground(Color.white);
		i7 = new ImageIcon(getClass().getResource("開く.png"));
		b7 = new JButton(i7);
		b7.setToolTipText("開く");
		b7.setMargin(new Insets(0,0,0,0));
		b7.setBackground(Color.white);
		i8 = new ImageIcon(getClass().getResource("上矢印.png"));
		b8 = new JButton(i8);
		b8.setMargin(new Insets(0,0,0,0));
		i9 = new ImageIcon(getClass().getResource("下矢印.png"));
		b9 = new JButton(i9);
		b9.setMargin(new Insets(0,0,0,0));
		i10 = new ImageIcon(getClass().getResource("選択ツール.png"));
		b10 = new JButton(i10);
		b10.setToolTipText("選択ツールオプション設定");
		b10.setMargin(new Insets(0,0,0,0));
		b10.setBackground(Color.white);
		i11 = new ImageIcon(getClass().getResource("履歴.png"));
		b11 = new JButton(i11);
		b11.setToolTipText("履歴ウィンドウ");
		b11.setMargin(new Insets(0,0,0,0));
		b11.setBackground(Color.white);
		i12 = new ImageIcon(getClass().getResource("保存.png"));
		b12 = new JButton(i12);
		b12.setToolTipText("上書き保存");
		b12.setMargin(new Insets(0,0,0,0));
		b12.setBackground(Color.white);
		i13 = new ImageIcon(getClass().getResource("ビュー.png"));
		b13 = new JButton(i13);
		b13.setToolTipText("ビューウィンドウ");
		b13.setMargin(new Insets(0,0,0,0));
		b13.setBackground(Color.white);
		i14 = new ImageIcon(getClass().getResource("ツール.png"));
		b14 = new JButton(i14);
		b14.setToolTipText("ツールウィンドウ");
		b14.setMargin(new Insets(0,0,0,0));
		b14.setBackground(Color.white);
		i15 = new ImageIcon(getClass().getResource("ルーラー.png"));
		b15 = new JButton(i15);
		b15.setToolTipText("ルーラー設定");
		b15.setMargin(new Insets(0,0,0,0));
		b15.setBackground(Color.white);
		i16 = new ImageIcon(getClass().getResource("ストローク.png"));
		b16 = new JButton(i16);
		b16.setToolTipText("ストローク");
		b16.setMargin(new Insets(0,0,0,0));
		b16.setBackground(Color.white);
		i17 = new ImageIcon(getClass().getResource("塗り潰し色.png"));
		b17 = new JButton(i17);
		b17.setToolTipText("塗り潰し色");
		b17.setMargin(new Insets(0,0,0,0));
		b17.setBackground(Color.white);
		l1 = new JLabel("ｽｹｰﾙ");
		t1 = new JTextField("1", 5);
		l2 = new JLabel("倍");
		l3 = new JLabel("処理中");
		l3.setForeground(Color.red);
		l3.setBackground(Color.black);
		l3.setOpaque(true);
		b3.setEnabled(false);
		b4.setEnabled(false);
		b12.setEnabled(false);
		l4 = new JLabel("x");
		l5 = new JLabel("0");
		l6 = new JLabel("y");
		l7 = new JLabel("0");
		l8 = new JLabel("");
		l9 = new JLabel("");
		l10 = new JLabel("");
		l11 = new JLabel("");
		
		
		add(b2);
		add(b7);
		add(b12);
		add(b5);
		add(b6);
		add(b3);
		add(b4);
		add(l1);
		p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(b8);
		p.add(b9);
		add(p);
		add(t1);
		add(l2);
		add(b13);
		add(b15);
		add(b11);
		add(b10);
		add(b14);
		add(b16);
		add(b17);
		add(l4);
		add(l5);
		add(l6);
		add(l7);
		add(l8);
		add(l9);
		add(l10);
		add(l11);
		

		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		b10.addActionListener(this);
		b11.addActionListener(this);
		b12.addActionListener(this);
		b13.addActionListener(this);
		b14.addActionListener(this);
		b15.addActionListener(this);
		b16.addActionListener(this);
		b17.addActionListener(this);
		t1.addActionListener(this);
	}

	/*★★動作系★★*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b2) { // 新規作成
			PaintTool.Clear();
		} else if (e.getSource() == b3) { // UnDo
			PaintTool.Undo();
		} else if (e.getSource() == b4) { // ReDo
			PaintTool.Redo();
		} else if (e.getSource() == b5) { // 名前をつけて保存
			PaintTool.Save();
		} else if (e.getSource() == b12) { // 上書き保存
			PaintTool.Save2();
		} else if (e.getSource() == b6) { // サイズ変更
			PaintTool.ChangeSize();
		} else if (e.getSource() == b7) { // 開く
			PaintTool.open();
		} else if (e.getSource() == b8) { // +
			tmpx = (float)(PaintTool.bx.getValue() + PaintTool.bx.getVisibleAmount()/2) / PaintTool.bx.getMaximum();
			tmpy = (float)(PaintTool.by.getValue() + PaintTool.by.getVisibleAmount()/2) / PaintTool.by.getMaximum();
			PaintTool.scale = (float)Math.round(1000*PaintTool.scale*1.5)/1000.0;
			t1.setText(String.valueOf(PaintTool.scale));
			PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
			PaintTool.p3.revalidate();
			PaintTool.rewrite();
		} else if (e.getSource() == b9) { // -
			tmpx = (float)(PaintTool.bx.getValue() + PaintTool.bx.getVisibleAmount()/2) / PaintTool.bx.getMaximum();
			tmpy = (float)(PaintTool.by.getValue() + PaintTool.by.getVisibleAmount()/2) / PaintTool.by.getMaximum();
			PaintTool.scale = (float)Math.round(1000*PaintTool.scale/1.5)/1000.0;
			t1.setText(String.valueOf(PaintTool.scale));
			PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
			PaintTool.p3.revalidate();
			PaintTool.rewrite();
		} else if (e.getSource() == t1) { // スケール変更
			try{
				tmpx = (float)(PaintTool.bx.getValue() + PaintTool.bx.getVisibleAmount()/2) / PaintTool.bx.getMaximum();
				tmpy = (float)(PaintTool.by.getValue() + PaintTool.by.getVisibleAmount()/2) / PaintTool.by.getMaximum();
				PaintTool.scale = Double.parseDouble(t1.getText());
				PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
				PaintTool.p3.revalidate();
			}
			catch(NumberFormatException  d){
				System.out.println("Error");
			}
			PaintTool.rewrite();
		} else if (e.getSource() == b10) { // SelectToolOption
			if(PaintTool.p9.isVisible()){
				PaintTool.optionInvisible();
				PaintTool.selectm.setSelected(false);
			}else{
				PaintTool.optionVisible();
				PaintTool.selectm.setSelected(true);
			}
		} else if (e.getSource() == b11) { // 履歴
			if(PaintTool.p10.isVisible()){
				PaintTool.historyInvisible();
				PaintTool.historym.setSelected(false);
			}else{
				PaintTool.historyVisible();
				PaintTool.historym.setSelected(true);
			}
		} else if (e.getSource() == b13) { // ビュー
			if(PaintTool.p11.frame.isVisible()){
				PaintTool.viewInvisible();
				PaintTool.viewm.setSelected(false);
			}else{
				PaintTool.viewVisible();
				PaintTool.viewm.setSelected(true);
			}
		} else if (e.getSource() == b14) { // ツール
			if(PaintTool.p12.isVisible()){
				PaintTool.toolInvisible();
				PaintTool.toolm.setSelected(false);
			}else{
				PaintTool.toolVisible();
				PaintTool.toolm.setSelected(true);
			}
		}else if (e.getSource() == b15) { // ルーラー
			if(PaintTool.p14.isVisible()){
				PaintTool.rulerInvisible();
				PaintTool.rulerm.setSelected(false);
			}else{
				PaintTool.rulerVisible();
				PaintTool.rulerm.setSelected(true);
			}
		}else if (e.getSource() == b16) { // ストローク
			if(PaintTool.p15.isVisible()){
				PaintTool.strokeInvisible();
				PaintTool.strokem.setSelected(false);
			}else{
				PaintTool.strokeVisible();
				PaintTool.strokem.setSelected(true);
			}
		}else if (e.getSource() == b17) { // 塗り潰し色
			if(PaintTool.p16.isVisible()){
				PaintTool.fillInvisible();
				PaintTool.fillm.setSelected(false);
			}else{
				PaintTool.fillVisible();
				PaintTool.fillm.setSelected(true);
			}
		}
	}
}
