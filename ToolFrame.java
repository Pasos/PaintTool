import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class ToolFrame  extends JFrame  implements ActionListener{

	ImageIcon i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i21,i22,i23,i24,i25,i26;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b21,b22,b23,b24,b25,b26;
	
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}
	
	/*★★コンストラクタ★★*/
	ToolFrame() {
		
		
		setTitle("ツール");
		setSize(150, 288);
		Container c = getContentPane();
		c.setLayout(new GridLayout(7, 4));
		i1 = new ImageIcon(getClass().getResource("直線.png"));
		i2 = new ImageIcon(getClass().getResource("線.png"));
		i3 = new ImageIcon(getClass().getResource("曲線.png"));
		i4 = new ImageIcon(getClass().getResource("点線.png"));
		i5 = new ImageIcon(getClass().getResource("オリジナル線.png"));
		i6 = new ImageIcon(getClass().getResource("水平線.png"));
		i7 = new ImageIcon(getClass().getResource("長方形.png"));
		i8 = new ImageIcon(getClass().getResource("長方形中塗り.png"));
		i9 = new ImageIcon(getClass().getResource("楕円.png"));
		i10 = new ImageIcon(getClass().getResource("楕円中塗り.png"));
		i11 = new ImageIcon(getClass().getResource("多角形.png"));
		i12 = new ImageIcon(getClass().getResource("多角形中塗り.png"));
		i13 = new ImageIcon(getClass().getResource("スプレー.png"));
		i14 = new ImageIcon(getClass().getResource("文字.png"));
		i15 = new ImageIcon(getClass().getResource("グラデーション.png"));
		i16 = new ImageIcon(getClass().getResource("スタンプ.png"));
		i17 = new ImageIcon(getClass().getResource("コピーブラシ.png"));
		i18 = new ImageIcon(getClass().getResource("ぼかし.png"));
		i19 = new ImageIcon(getClass().getResource("輝度.png"));
		i21 = new ImageIcon(getClass().getResource("塗りつぶし.png"));
		i22 = new ImageIcon(getClass().getResource("取り込み.png"));
		i23 = new ImageIcon(getClass().getResource("選択範囲.png"));
		i24 = new ImageIcon(getClass().getResource("自由形.png"));
		i25 = new ImageIcon(getClass().getResource("自由形中塗り.png"));
		i26 = new ImageIcon(getClass().getResource("RGBフィルター.png"));
		b1 = new JButton(i1);
		b1.setToolTipText("直線");
		b2 = new JButton(i2);
		b2.setToolTipText("線");
		b3 = new JButton(i3);
		b3.setToolTipText("曲線");
		b4 = new JButton(i4);
		b4.setToolTipText("点線");
		b5 = new JButton(i5);
		b5.setToolTipText("オリジナル線");
		b6 = new JButton(i6);
		b6.setToolTipText("水平線");
		b7 = new JButton(i7);
		b7.setToolTipText("長方形");
		b8 = new JButton(i8);
		b8.setToolTipText("長方形(中塗)");
		b9 = new JButton(i9);
		b9.setToolTipText("楕円");
		b10 = new JButton(i10);
		b10.setToolTipText("楕円(中塗)");
		b11 = new JButton(i11);
		b11.setToolTipText("多角形");
		b12 = new JButton(i12);
		b12.setToolTipText("多角形(中塗)");
		b13 = new JButton(i13);
		b13.setToolTipText("スプレー");
		b14 = new JButton(i14);
		b14.setToolTipText("文字");
		b15 = new JButton(i15);
		b15.setToolTipText("グラデーション");
		b16 = new JButton(i16);
		b16.setToolTipText("コピースタンプ");
		b17 = new JButton(i17);
		b17.setToolTipText("コピーブラシ");
		b18 = new JButton(i18);
		b18.setToolTipText("フィルター");
		b19 = new JButton(i19);
		b19.setToolTipText("輝度");
		b21 = new JButton(i21);
		b21.setToolTipText("塗り潰し");
		b22 = new JButton(i22);
		b22.setToolTipText("色取り込み");
		b23 = new JButton(i23);
		b23.setToolTipText("選択ツール");
		b24 = new JButton(i24);
		b24.setToolTipText("自由形");
		b25 = new JButton(i25);
		b25.setToolTipText("自由形(中塗)");
		b26 = new JButton(i26);
		b26.setToolTipText("RGBフィルター");
		b1.setMargin(new Insets(0,0,0,0));
		b2.setMargin(new Insets(0,0,0,0));
		b3.setMargin(new Insets(0,0,0,0));
		b4.setMargin(new Insets(0,0,0,0));
		b5.setMargin(new Insets(0,0,0,0));
		b6.setMargin(new Insets(0,0,0,0));
		b7.setMargin(new Insets(0,0,0,0));
		b8.setMargin(new Insets(0,0,0,0));
		b9.setMargin(new Insets(0,0,0,0));
		b10.setMargin(new Insets(0,0,0,0));
		b11.setMargin(new Insets(0,0,0,0));
		b12.setMargin(new Insets(0,0,0,0));
		b13.setMargin(new Insets(0,0,0,0));
		b14.setMargin(new Insets(0,0,0,0));
		b15.setMargin(new Insets(0,0,0,0));
		b16.setMargin(new Insets(0,0,0,0));
		b17.setMargin(new Insets(0,0,0,0));
		b18.setMargin(new Insets(0,0,0,0));
		b19.setMargin(new Insets(0,0,0,0));
		b21.setMargin(new Insets(0,0,0,0));
		b22.setMargin(new Insets(0,0,0,0));
		b23.setMargin(new Insets(0,0,0,0));
		b24.setMargin(new Insets(0,0,0,0));
		b25.setMargin(new Insets(0,0,0,0));
		b26.setMargin(new Insets(0,0,0,0));
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		add(b7);
		add(b8);
		add(b9);
		add(b10);
		add(b11);
		add(b12);
		add(b24);
		add(b25);
		add(b13);
		add(b14);
		add(b15);
		add(b16);
		add(b17);
		add(b18);
		add(b26);
		add(b19);
		add(b21);
		add(b22);
		add(b23);
		
		
		b1.addActionListener(this);
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
		b18.addActionListener(this);
		b19.addActionListener(this);
		b21.addActionListener(this);
		b22.addActionListener(this);
		b23.addActionListener(this);
		b24.addActionListener(this);
		b25.addActionListener(this);
		b26.addActionListener(this);
		
		setResizable(false);
		setVisible(false);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		switch(PaintTool.getType()){
		case 0:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 1:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 3:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 5:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 6:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 8:
			ToolPanel.spraysize1 = Integer.parseInt(PaintTool.p1.t1.getText());
			ToolPanel.spraysize2 = Integer.parseInt(PaintTool.p1.t2.getText());
			ToolPanel.spraysize3 = Integer.parseInt(PaintTool.p1.t3.getText());
			ToolPanel.spraysize4 = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 10:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 13:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 14:
			ToolPanel.size2 = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 15:
			ToolPanel.size2 = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 18:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 20:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 22:
			ToolPanel.size2 = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		case 23:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 24:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t4.getText());
			break;
		case 25:
			ToolPanel.size = Integer.parseInt(PaintTool.p1.t1.getText());
			break;
		}
		PaintTool.moziInvisible();
		PaintTool.originalInvisible();
		PaintTool.p1.remove(ToolPanel.l2);
		PaintTool.p1.remove(ToolPanel.l3);
		PaintTool.p1.remove(ToolPanel.l4);
		PaintTool.p1.remove(ToolPanel.l5);
		PaintTool.p1.remove(ToolPanel.l6);
		PaintTool.p1.remove(ToolPanel.l7);
		PaintTool.p1.remove(ToolPanel.l8);
		PaintTool.p1.remove(ToolPanel.l9);
		PaintTool.p1.remove(ToolPanel.il1);
		PaintTool.p1.remove(ToolPanel.il2);
		PaintTool.p1.remove(ToolPanel.il3);
		PaintTool.p1.remove(PaintTool.p1.t1);
		PaintTool.p1.remove(PaintTool.p1.t2);
		PaintTool.p1.remove(PaintTool.p1.t3);
		PaintTool.p1.remove(PaintTool.p1.t4);
		PaintTool.p1.remove(PaintTool.p1.t5);
		PaintTool.p1.remove(PaintTool.p1.t6);
		PaintTool.p1.remove(PaintTool.p1.t7);
		PaintTool.p1.remove(PaintTool.p1.t8);
		PaintTool.p1.remove(ToolPanel.b1);
		PaintTool.p1.remove(ToolPanel.b2);
		PaintTool.p1.remove(ToolPanel.b4);
		PaintTool.p1.remove(ToolPanel.b5);
		PaintTool.p1.remove(ToolPanel.b6);
		PaintTool.p1.remove(ToolPanel.b7);
		PaintTool.p1.remove(ToolPanel.b8);
		PaintTool.p1.remove(ToolPanel.b9);
		PaintTool.p1.remove(ToolPanel.cb1);
		PaintTool.p1.remove(ToolPanel.cb2);
		PaintTool.p1.remove(ToolPanel.cb3);
		PaintTool.p1.remove(ToolPanel.cb4);
		PaintTool.p1.remove(PaintTool.p1.cbh);
		PaintTool.p1.remove(ToolPanel.cbf);
		PaintTool.p1.remove(ToolPanel.rb1);
		PaintTool.p1.remove(ToolPanel.rb2);
		PaintTool.p1.remove(ToolPanel.rb3);
		PaintTool.p1.remove(ToolPanel.rb4);
		PaintTool.p1.remove(ToolPanel.rb5);
		PaintTool.p1.remove(ToolPanel.rb10);
		PaintTool.p1.remove(ToolPanel.rb6);
		PaintTool.p1.remove(ToolPanel.rb7);
		PaintTool.p1.remove(ToolPanel.rb8);
		PaintTool.p1.remove(ToolPanel.rb9);
		PaintTool.p1.remove(ToolPanel.s);
		PaintTool.p1.remove(ToolPanel.s2);
		ToolPanel.l7.setEnabled(true);
		PaintTool.p1.t6.setEnabled(true);
		ToolPanel.b9.setEnabled(true);
		if (PaintTool.type == 12) {
			PaintTool.selectRectDraw();
		}
		PaintTool.p1.t2.setEnabled(true);
		ToolPanel.rb1.setEnabled(true);
		ToolPanel.rb2.setEnabled(true);
		ToolPanel.l3.setEnabled(true);
		ToolPanel.b7.setEnabled(true);
		DrawPanel.move = 0;
		DrawPanel.dontfill = false;
		DrawPanel.pointset = false;
		PaintTool.p1.repaint();
		if (!(e.getSource() == b16)) {
			DrawPanel.stampimg = null;
			DrawPanel.stampset = false;
		}
		if (!(e.getSource() == b17)) {
			DrawPanel.stampimg = null;
			DrawPanel.stampset = false;
		}
		DrawPanel.x1 = -10;
		DrawPanel.x2 = -10;
		DrawPanel.y1 = -10;
		DrawPanel.y2 = -10;
		DrawPanel.readyarc = false;
		PaintTool.comand = 5;
		PaintTool.rewrite();
		ToolPanel.l3.setText("");
		if (e.getSource() == b1) { // 直線
			PaintTool.p1.cb.setSelectedItem("直線");
			PaintTool.setType(0);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.l8);
			ToolPanel.l8.setText("縁");
			PaintTool.p1.add(ToolPanel.rb8);
			PaintTool.p1.add(ToolPanel.rb9);
			ToolPanel.rb8.setText("丸");
			ToolPanel.rb9.setText("四角");
		} else if (e.getSource() == b7) { // 長方形
			PaintTool.p1.cb.setSelectedItem("長方形");
			PaintTool.setType(1);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			ToolPanel.l9.setText("太さ");
			PaintTool.p1.add(PaintTool.p1.t4);
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			ToolPanel.l4.setText("回転率");
			PaintTool.p1.t3.setText("0");
			ToolPanel.l5.setText("°");
			PaintTool.p1.add(ToolPanel.l7);
			ToolPanel.l7.setText(" ポイントタイプ");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("(枠内)");
			ToolPanel.rb2.setText("(中心から)");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l2.setText("複数描写");
			PaintTool.p1.t1.setText("0");
			ToolPanel.l3.setText("/");
			PaintTool.p1.t2.setText("10");
			PaintTool.p1.add(ToolPanel.cb2);
		} else if (e.getSource() == b8) { // 長方形(中塗)
			PaintTool.p1.cb.setSelectedItem("長方形(中塗)");
			PaintTool.setType(2);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			ToolPanel.l4.setText("回転率");
			PaintTool.p1.t3.setText("0");
			ToolPanel.l5.setText("°　中抜き割合");
			PaintTool.p1.add(PaintTool.p1.t2);
			PaintTool.p1.add(ToolPanel.s2);
			ToolPanel.s2.revalidate();
			ToolPanel.s2.setValue(1);
			PaintTool.p1.t2.setText("0.0");
			PaintTool.p1.add(ToolPanel.l7);
			ToolPanel.l7.setText(" ポイントタイプ");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("(枠内)");
			ToolPanel.rb2.setText("(中心から)");
		} else if (e.getSource() == b9) { // 楕円
			PaintTool.p1.cb.setSelectedItem("楕円");
			PaintTool.setType(3);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			ToolPanel.l9.setText("太さ");
			PaintTool.p1.add(PaintTool.p1.t4);
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
			
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			ToolPanel.l4.setText("回転率");
			PaintTool.p1.t3.setText("0");
			ToolPanel.l5.setText("°");
			PaintTool.p1.add(ToolPanel.l7);
			ToolPanel.l7.setText(" ポイントタイプ");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("(枠内)");
			ToolPanel.rb2.setText("(中心から)");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l2.setText("複数描写");
			PaintTool.p1.t1.setText("0");
			ToolPanel.l3.setText("/");
			PaintTool.p1.t2.setText("10");
			PaintTool.p1.add(ToolPanel.cb2);
		} else if (e.getSource() == b10) { // 楕円(中塗)
			PaintTool.p1.cb.setSelectedItem("楕円(中塗)");
			PaintTool.setType(4);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			ToolPanel.l6.setText("透明度");
			ToolPanel.l4.setText("回転率");
			PaintTool.p1.t3.setText("0");
			ToolPanel.l5.setText("°　中抜き割合");
			PaintTool.p1.add(PaintTool.p1.t2);
			PaintTool.p1.add(ToolPanel.s2);
			ToolPanel.s2.revalidate();
			ToolPanel.s2.setValue(1);
			PaintTool.p1.t2.setText("0.0");
			PaintTool.p1.add(ToolPanel.l7);
			ToolPanel.l7.setText(" ポイントタイプ");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("(枠内)");
			ToolPanel.rb2.setText("(中心から)");
		} else if (e.getSource() == b2) { // 線
			PaintTool.p1.cb.setSelectedItem("線");
			PaintTool.setType(5);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size));
			DrawPanel.imgtemp = null;
		} else if (e.getSource() == b11) { // 多角形
			PaintTool.p1.cb.setSelectedItem("多角形");
			PaintTool.setType(6);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			ToolPanel.l9.setText("太さ");
			PaintTool.p1.add(PaintTool.p1.t4);
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
			DrawPanel.pcnt = 0;
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("画数");
			PaintTool.p1.t1.setText("3");
			add(ToolPanel.b9);
			ToolPanel.b9.setText("頂点を１つ消す");
			ToolPanel.b9.setEnabled(false);
		} else if (e.getSource() == b12) { // 多角形(中塗)
			PaintTool.p1.cb.setSelectedItem("多角形(中塗)");
			PaintTool.setType(7);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			DrawPanel.pcnt = 0;
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("画数");
			PaintTool.p1.t1.setText("3");
			PaintTool.p1.add(ToolPanel.b9);
			ToolPanel.b9.setText("頂点を１つ消す");
			ToolPanel.b9.setEnabled(false);
		} else if (e.getSource() == b24){//自由形
			PaintTool.setType(23);
			DrawPanel.pcnt = 0;
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
		} else if (e.getSource() == b25){//自由形(中塗)
			PaintTool.setType(24);
			DrawPanel.pcnt = 0;
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			ToolPanel.l9.setText("太さ");
			PaintTool.p1.add(PaintTool.p1.t4);
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
		} else if (e.getSource() == b13) { // スプレー
			PaintTool.p1.cb.setSelectedItem("スプレー");
			PaintTool.setType(8);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			PaintTool.p1.add(PaintTool.p1.t4);
			ToolPanel.l2.setText("描写領域");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.spraysize1));
			ToolPanel.l3.setText("粒数");
			PaintTool.p1.t2.setText(Integer.toString(ToolPanel.spraysize2));
			ToolPanel.l4.setText("大きさ最小");
			PaintTool.p1.t3.setText(Integer.toString(ToolPanel.spraysize3));
			ToolPanel.l5.setText("最大");
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.spraysize4));
			PaintTool.p1.add(ToolPanel.l7);
			ToolPanel.l7.setText("色数");
			PaintTool.p1.add(PaintTool.p1.t6);
			PaintTool.p1.add(ToolPanel.cb3);
			PaintTool.p1.add(ToolPanel.cb4);
		} else if (e.getSource() == b14) { // 文字
			PaintTool.p1.cb.setSelectedItem("文字");
			PaintTool.setType(9);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.moziVisible();
		} else if (e.getSource() == b4) { // 点線
			PaintTool.p1.cb.setSelectedItem("点線");
			PaintTool.setType(10);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			DrawPanel.distancecount = 0;
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l3.setText("細かさ");
			PaintTool.p1.t2.setText("3");
			PaintTool.p1.add(ToolPanel.l5);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l4);
			ToolPanel.l5.setText("実線割合");
			PaintTool.p1.t3.setText("50");
			ToolPanel.l4.setText("%");
		} else if (e.getSource() == b22) { // 色取り込み
			PaintTool.p1.cb.setSelectedItem("色取込");
			PaintTool.setType(11);
		} else if (e.getSource() == b23) { // 選択ツール
			PaintTool.p1.cb.setSelectedItem("選択ツール");
			PaintTool.setType(12);
			PaintTool.p1.add(ToolPanel.b1);
			PaintTool.p1.add(ToolPanel.b4);
			PaintTool.p1.add(ToolPanel.rb3);
			PaintTool.p1.add(ToolPanel.rb4);
			PaintTool.p1.add(ToolPanel.rb5);
			ToolPanel.rb3.setText("move");
			ToolPanel.rb4.setText("add");
			ToolPanel.rb5.setText("remove");
			ToolPanel.rb3.setSelected(true);
			PaintTool.p1.add(ToolPanel.b6);
			PaintTool.p1.add(PaintTool.p1.cbh);
			PaintTool.p1.add(ToolPanel.b2);
			if (PaintTool.p1.cbh.getSelectedItem() == "回転") {
				PaintTool.p1.add(PaintTool.p1.t3);
				PaintTool.p1.add(ToolPanel.l5);
				PaintTool.p1.t3.setText(String.valueOf(PaintTool.spin));
				ToolPanel.l5.setText("°");
			} else if (PaintTool.p1.cbh.getSelectedItem() == "拡大縮小") {
				PaintTool.p1.add(ToolPanel.l2);
				PaintTool.p1.add(PaintTool.p1.t1);
				PaintTool.p1.add(ToolPanel.l3);
				PaintTool.p1.add(PaintTool.p1.t2);
				PaintTool.p1.add(ToolPanel.l6);
				ToolPanel.l2.setText("x軸");
				PaintTool.p1.t1.setText(String.valueOf(PaintTool.ratiox));
				ToolPanel.l3.setText("% y軸");
				PaintTool.p1.t2.setText(String.valueOf(PaintTool.ratioy));
				ToolPanel.l6.setText("%");
			} else if (PaintTool.p1.cbh.getSelectedItem() == "傾き") {
				PaintTool.p1.add(PaintTool.p1.t4);
				PaintTool.p1.add(ToolPanel.rb1);
				PaintTool.p1.add(ToolPanel.rb2);
				ToolPanel.rb1.setSelected(true);
				PaintTool.p1.t4.setText(String.valueOf(PaintTool.shx));
				ToolPanel.rb1.setText("x軸");
				ToolPanel.rb2.setText("y軸");
			}
			if(SelectOptionFrame.rb9.isSelected()){
				PaintTool.p1.add(ToolPanel.b9);
			}
			ToolPanel.b9.setText("頂点を１つ消す");
			if(DrawPanel.set == true){
				DrawPanel.dontfill = true;
				PaintTool.capture();
			}
		}else if (e.getSource() == b5) { // オリジナル線
			PaintTool.p1.cb.setSelectedItem("オリジナル線");
			PaintTool.setType(13);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.cb1);
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l3.setText("回転スピード");
			PaintTool.p1.t2.setText("6");
			ToolPanel.cb1.setText("回転");
			ToolPanel.cb1.setSelected(false);
			PaintTool.rote = false;
			ToolPanel.rb1.setText("左回転");
			ToolPanel.rb2.setText("右回転");
			ToolPanel.rb1.setSelected(true);
			PaintTool.p1.t2.setEnabled(PaintTool.rote);
			ToolPanel.rb1.setEnabled(PaintTool.rote);
			ToolPanel.rb2.setEnabled(PaintTool.rote);
			ToolPanel.l3.setEnabled(PaintTool.rote);
			PaintTool.originalVisible();
		}else if (e.getSource() == b18) { // フィルター
			PaintTool.p1.cb.setSelectedItem("フィルター");
			PaintTool.setType(14);
			PaintTool.p1.add(ToolPanel.l7);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l7.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size2));
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l6.setText("重ねがけ回数");
			PaintTool.p1.t2.setText("1");
			PaintTool.p1.add(ToolPanel.cbf);
			PaintTool.p1.add(ToolPanel.b5);
			ToolPanel.b5.setText("全体変換");
			DrawPanel.x2 = -1000;
			DrawPanel.y2 = -1000;
		}else if (e.getSource() == b19) { // 輝度
			PaintTool.p1.cb.setSelectedItem("輝度");
			PaintTool.setType(15);
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size2));
			PaintTool.p1.add(ToolPanel.rb6);
			PaintTool.p1.add(ToolPanel.rb7);
			ToolPanel.rb6.setText("明るく");
			ToolPanel.rb7.setText("暗く");
			ToolPanel.rb6.setSelected(true);
			ToolPanel.rb6.setEnabled(true);
			ToolPanel.rb7.setEnabled(true);
			ToolPanel.s3.setMaximum(1500);
			ToolPanel.s3.setMinimum(100);
			ToolPanel.s3.setValue(200);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s3);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l6.setText("つよさ");
			PaintTool.p1.t2.setText("2.0");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("type1");
			ToolPanel.rb2.setText("type2");
			PaintTool.p1.add(ToolPanel.b5);
			ToolPanel.b5.setText("全体変換");
			DrawPanel.x2 = -1000;
			DrawPanel.y2 = -1000;
		}else if (e.getSource() == b26) { // RGBフィルター
			PaintTool.setType(25);
			PaintTool.p1.add(ToolPanel.l9);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l9.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size2));
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(PaintTool.p1.t2);
			PaintTool.p1.add(ToolPanel.l4);
			PaintTool.p1.add(PaintTool.p1.t3);
			PaintTool.p1.add(ToolPanel.l5);
			PaintTool.p1.add(PaintTool.p1.t4);
			ToolPanel.l3.setText("乗算・R");
			ToolPanel.l4.setText("G");
			ToolPanel.l5.setText("B");
			PaintTool.p1.t2.setText("1.0");
			PaintTool.p1.t3.setText("1.0");
			PaintTool.p1.t4.setText("1.0");
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(PaintTool.p1.t6);
			PaintTool.p1.add(ToolPanel.l7);
			PaintTool.p1.add(PaintTool.p1.t7);
			PaintTool.p1.add(ToolPanel.l8);
			PaintTool.p1.add(PaintTool.p1.t8);
			ToolPanel.l6.setText("加算・R");
			ToolPanel.l7.setText("G");
			ToolPanel.l8.setText("B");
			PaintTool.p1.t6.setText("0");
			PaintTool.p1.t7.setText("0");
			PaintTool.p1.t8.setText("0");
			PaintTool.p1.add(ToolPanel.b5);
			ToolPanel.b5.setText("全体変換");
		}else if (e.getSource() == b15) { // グラデーション
			PaintTool.p1.cb.setSelectedItem("グラデーション");
			PaintTool.setType(17);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			PaintTool.p1.t5.setText("1");
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			PaintTool.p1.add(ToolPanel.l5);
			PaintTool.p1.add(ToolPanel.s2);
			ToolPanel.s2.revalidate();
			PaintTool.p1.t2.setText("1");
			try{
				ToolPanel.s2.setValue(Math.round(Float.parseFloat(PaintTool.p1.t2.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s2.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l6.setText("透明度1");
			ToolPanel.l5.setText("透明度2");
			PaintTool.p1.add(ToolPanel.rb3);
			PaintTool.p1.add(ToolPanel.il1);
			PaintTool.p1.add(ToolPanel.rb4);
			PaintTool.p1.add(ToolPanel.il2);
			PaintTool.p1.add(ToolPanel.rb5);
			PaintTool.p1.add(ToolPanel.il3);
			ToolPanel.rb3.setText("");
			ToolPanel.rb4.setText("");
			ToolPanel.rb5.setText("");
			ToolPanel.rb3.setSelected(true);
		}else if (e.getSource() == b6) { // 水平線
			PaintTool.p1.cb.setSelectedItem("水平線");
			PaintTool.setType(18);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			PaintTool.p1.add(PaintTool.p1.t4);
			ToolPanel.l9.setText("サイズ");
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.cb1);
			ToolPanel.cb1.setText("塗り潰し");
			ToolPanel.cb1.setSelected(false);
			ToolPanel.rb1.setEnabled(false);
			ToolPanel.rb2.setEnabled(false);
			ToolPanel.l3.setEnabled(false);
			PaintTool.p1.add(ToolPanel.l3);
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.l3.setText("範囲");
			ToolPanel.rb1.setText("外");
			ToolPanel.rb2.setText("中");
		} else if (e.getSource() == b16) { // コピースタンプ
			PaintTool.p1.cb.setSelectedItem("ｺﾋﾟｰｽﾀﾝﾌﾟ");
			PaintTool.setType(19);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.rb3);
			PaintTool.p1.add(ToolPanel.rb4);
			PaintTool.p1.add(ToolPanel.rb5);
			ToolPanel.rb3.setText("●");
			ToolPanel.rb4.setText("■");
			ToolPanel.rb5.setText("線");
			ToolPanel.rb3.setSelected(true);
			PaintTool.p1.add(ToolPanel.b7);
			ToolPanel.b7.setEnabled(false);
		} else if (e.getSource() == b17) { // コピーブラシ
			PaintTool.p1.cb.setSelectedItem("ｺﾋﾟｰﾌﾞﾗｼ");
			PaintTool.setType(22);
			PaintTool.p1.add(ToolPanel.l6);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(PaintTool.p1.t1);
			ToolPanel.l2.setText("サイズ");
			PaintTool.p1.t1.setText(Integer.toString(ToolPanel.size2));
			PaintTool.p1.add(ToolPanel.b7);
			ToolPanel.b7.setEnabled(false);
		} else if (e.getSource() == b3) { // 曲線
			PaintTool.p1.cb.setSelectedItem("曲線");
			PaintTool.setType(20);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			DrawPanel.pcnt = 0;
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l9);
			ToolPanel.l9.setText("サイズ");
			PaintTool.p1.add(PaintTool.p1.t4);
			PaintTool.p1.t4.setText(Integer.toString(ToolPanel.size));
			PaintTool.p1.add(ToolPanel.l8);
			ToolPanel.l8.setText("縁");
			PaintTool.p1.add(ToolPanel.rb8);
			PaintTool.p1.add(ToolPanel.rb9);
			ToolPanel.rb8.setText("丸");
			ToolPanel.rb9.setText("四角");
			PaintTool.p1.add(ToolPanel.l3);
			ToolPanel.l3.setText("|");
			PaintTool.p1.add(ToolPanel.rb1);
			PaintTool.p1.add(ToolPanel.rb2);
			ToolPanel.rb1.setText("描く");
			ToolPanel.rb2.setText("引っ張る");
			ToolPanel.rb1.setSelected(true);
			ToolPanel.rb2.setEnabled(false);
			PaintTool.p1.add(ToolPanel.l4);
			ToolPanel.l4.setText("|");
			PaintTool.p1.add(ToolPanel.rb6);
			PaintTool.p1.add(ToolPanel.rb7);
			ToolPanel.rb6.setText("点1");
			ToolPanel.rb7.setText("点2");
			ToolPanel.rb6.setSelected(true);
			ToolPanel.rb6.setEnabled(false);
			ToolPanel.rb7.setEnabled(false);
			PaintTool.p1.add(ToolPanel.b8);
			ToolPanel.b8.setEnabled(false);
			DrawPanel.curvex = -1000;
			DrawPanel.curvey = -1000;
			DrawPanel.curvex2 = -1000;
			DrawPanel.curvey2 = -1000;
			DrawPanel.pcnt2 = 0;
			PaintTool.p1.add(ToolPanel.b9);
			ToolPanel.b9.setEnabled(false);
			ToolPanel.b9.setText("線を１つ消す");
		}else if (e.getSource() == b21) { // 塗りつぶし
			PaintTool.p1.cb.setSelectedItem("塗り潰し");
			PaintTool.setType(21);
			PaintTool.p1.add(ToolPanel.l6);
			PaintTool.p1.add(ToolPanel.s);
			ToolPanel.s.revalidate();
			try{
				ToolPanel.s.setValue(Math.round(Float.parseFloat(PaintTool.p1.t5.getText())*10000));
			}catch (NumberFormatException t) {
				ToolPanel.s.setValue(10000);
			}
			PaintTool.p1.add(PaintTool.p1.t5);
			ToolPanel.l6.setText("透明度");
			PaintTool.p1.add(ToolPanel.l2);
			PaintTool.p1.add(ToolPanel.s2);
			ToolPanel.s2.revalidate();
			ToolPanel.s2.setValue(1);
			PaintTool.p1.add(PaintTool.p1.t2);
			ToolPanel.l2.setText("しきい値");
			PaintTool.p1.t2.setText("5");
		}
	}

}
