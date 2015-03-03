import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ToolPanel extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 2L;

	public static JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
	public JTextField t1, t2, t3, t4, t5, t6, t7, t8;
	public static JButton b1, b2, b4, b5, b6,b7, b8, b9;
	ImageIcon i1, i2, i3;
	public static JLabel il1, il2, il3;
	public JComboBox cb, cbh;
	public static JComboBox cbf;
	public static JRadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11;
	private ButtonGroup gr1, gr2, gr3,gr4;
	public static JCheckBox cb1,cb2, cb3, cb4;
	public static JSlider s, s2, s3;
	public static int size, size2 = 10, spraysize1 = 15, spraysize2 = 5, spraysize3 = 10, spraysize4 = 10,sikiisize = 10;

	/* ★★入出力系★★ */

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

	float getsize2f() {
		try {
			return Float.parseFloat(t2.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
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
	
	float getsize3f() {
		try {
			return Float.parseFloat(t3.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
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

	float getsize4f() {
		try {
			return Float.parseFloat(t4.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}

	float getsize5() {
		try {
			if(Float.parseFloat(t5.getText())< 0){
				return 0;
			} else if(Float.parseFloat(t5.getText()) > 1){
				return 1;
			} else{
				return Float.parseFloat(t5.getText());
			}

		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}
	
	int getsize6() {
		try {
			return Integer.parseInt(t6.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return 1;
		}
	}
	
	float getsize6f() {
		try {
			return Float.parseFloat(t6.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}
	
	float getsize7f() {
		try {
			return Float.parseFloat(t7.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}
	
	float getsize8f() {
		try {
			return Float.parseFloat(t8.getText());
		} catch (NumberFormatException e) {
			System.out.println("Error");
			return (float)1;
		}
	}
	

	void setsize(String a) {
		t1.setText(a);
	}

	public void setB9(boolean a){
		if(a){
			add(b9);
		}else{
			remove(b9);
		}
	}
	/* ★★コンストラクタ★★ */
	ToolPanel() {
		cb = new JComboBox();
		cb.setMaximumRowCount(10);
		cb.addItem("直線");
		cb.addItem("線");
		cb.addItem("曲線");
		cb.addItem("オリジナル線");
		cb.addItem("長方形");
		cb.addItem("長方形(中塗)");
		cb.addItem("楕円");
		cb.addItem("楕円(中塗)");
		cb.addItem("多角形");
		cb.addItem("多角形(中塗)");
		cb.addItem("自由形");
		cb.addItem("自由形(中塗)");
		cb.addItem("スプレー");
		cb.addItem("文字");
		cb.addItem("グラデーション");
		cb.addItem("水平線");
		cb.addItem("ｺﾋﾟｰｽﾀﾝﾌﾟ");
		cb.addItem("ｺﾋﾟｰﾌﾞﾗｼ");
		cb.addItem("色取込");
		cb.addItem("塗り潰し");
		cb.addItem("フィルター");
		cb.addItem("RGBフィルター");
		cb.addItem("輝度");
		cb.addItem("選択ツール");
		
		cbh = new JComboBox();
		cbh.addItem("左右反転");
		cbh.addItem("上下反転");
		cbh.addItem("回転");
		cbh.addItem("拡大縮小");
		cbh.addItem("傾き");
		
		cbf = new JComboBox();
		cbf.addItem("ぼかし");
		cbf.addItem("シャープtype1");
		cbf.addItem("シャープtype2");
		cbf.addItem("モノクロ");
		cbf.addItem("線図化");
		cbf.addItem("垂直方向エッジ検出(正)");
		cbf.addItem("垂直方向エッジ検出(負)");
		cbf.addItem("水平方向エッジ検出(正)");
		cbf.addItem("水平方向エッジ検出(負)");
		cbf.addItem("エンボス(左上)");
		cbf.addItem("エンボス(右下)");
		cbf.addItem("エンボス(右上)");
		cbf.addItem("エンボス(左下)");
		cbf.addItem("ネガポジ");
		
		t1 = new JTextField("2",4);
		t2 = new JTextField("",4);
		t3 = new JTextField("",4);
		t4 = new JTextField("",4);
		t5 = new JTextField("1",4);
		t6 = new JTextField("1",4);
		t7 = new JTextField("0",4);
		t8 = new JTextField("0",4);
		
		
		b1 = new JButton("トリミング");
		b2 = new JButton("変形");
		b4 = new JButton("絵取込");

		b5 = new JButton("全体変換");

		b6 = new JButton("範囲反転");
		
		b7 = new JButton("クリア");

		b8 = new JButton("確定");

		b9 = new JButton("頂点を１つ消す");

		l1 = new JLabel("ツール");
		l2 = new JLabel("サイズ");
		l3 = new JLabel("");
		l4 = new JLabel("");
		l5 = new JLabel("");
		l6 = new JLabel("透明度");
		l7 = new JLabel("");
		l8 = new JLabel("縁");
		l9 = new JLabel("");
		
		i1 = new ImageIcon(getClass().getResource("ノマグラ.png"));
		il1 = new JLabel(i1);
		i2 = new ImageIcon(getClass().getResource("線グラ.png"));
		il2 = new JLabel(i2);
		i3 = new ImageIcon(getClass().getResource("丸グラ.png"));
		il3 = new JLabel(i3);

		rb1 = new JRadioButton("",true);
		rb2 = new JRadioButton("");
		
		rb3 = new JRadioButton("move",true);
		rb4 = new JRadioButton("add");
		rb5 = new JRadioButton("remove");
		rb10= new JRadioButton("subtract");
		
		rb6 = new JRadioButton("点1",true);
		rb7 = new JRadioButton("点2");
		
		rb8 = new JRadioButton("Normal",true);
		rb9 = new JRadioButton("REPEAT");
		rb11 = new JRadioButton("REFLECT");
		
		cb1 = new JCheckBox("回転");
		s = new JSlider(SwingConstants.HORIZONTAL, 1, 10000, 10000);
		s2 = new JSlider(SwingConstants.HORIZONTAL, 1, 10000, 10000);
		s3 = new JSlider(SwingConstants.HORIZONTAL, 100, 25500, 200);
		
		
		cb2 = new JCheckBox("グラデーション");
		cb3 = new JCheckBox("ｸﾞﾗﾃﾞｰｼｮﾝ塗り");
		cb4 = new JCheckBox("ﾏｰﾌﾞﾙ塗り");
		
		gr1 = new ButtonGroup();
		gr1.add(rb1);
		gr1.add(rb2);

		gr2 = new ButtonGroup();
		gr2.add(rb3);
		gr2.add(rb4);
		gr2.add(rb5);
		gr2.add(rb10);

		gr3 = new ButtonGroup();
		gr3.add(rb6);
		gr3.add(rb7);
		
		gr4 = new ButtonGroup();
		gr4.add(rb8);
		gr4.add(rb9);
		gr4.add(rb11);
		

		add(l1);
		add(cb);
		add(l6);
		add(s);
		s.setValue(10000);
		add(t5);
		add(l2);
		add(t1);
		cb.addActionListener(this);
		cbh.addActionListener(this);
		t2.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		cb1.addActionListener(this);
		cb3.addActionListener(this);
		cb4.addActionListener(this);
		s.addChangeListener(this);
		s2.addChangeListener(this);
		s3.addChangeListener(this);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb3.addActionListener(this);
		rb4.addActionListener(this);
		rb5.addActionListener(this);
		rb6.addActionListener(this);
		rb7.addActionListener(this);
		t5.addActionListener(this);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) { // トリミング
			PaintTool.triming();
		} else if (e.getSource() == b2) { // 変形
			PaintTool.changeShape();
		} else if (e.getSource() == b4) { // 絵取込
			PaintTool.openRect();
		} else if (e.getSource() == b5) { // 全体ぼかし&全体Bright
			PaintTool.henkan();
		} else if (e.getSource() == b6) { // エリア反転
			PaintTool.AreaHanten();
		} else if (e.getSource() == b7) { // クリア(Stamp)
			DrawPanel.stampset = false;
			DrawPanel.pointset = false;
			b7.setEnabled(false);
		} else if (e.getSource() == b8) { // 確定(CurveLine)
			PaintTool.deside();
			b8.setEnabled(false);
			b9.setEnabled(false);
		}else if (e.getSource() == b9) { // １つ消す(Polygon)
			if(PaintTool.getType() == 12 && SelectOptionFrame.rb9.isSelected() && DrawPanel.pcnt2 > 0){
				DrawPanel.pcnt2--;
				if(DrawPanel.pcnt2 == 0)b9.setEnabled(false);
				PaintTool.rewrite();
			}
			if(DrawPanel.pcnt > 0){
				DrawPanel.pcnt--;
				if(DrawPanel.pcnt == 0){
					b8.setEnabled(false);
					b9.setEnabled(false);
				}
				PaintTool.rewrite();
			}
		}else if (e.getSource() == t2) {//バー調整
			if(PaintTool.getType() == 15){
				if(rb6.isSelected()){
					s3.setValue(Math.round(100*Float.parseFloat(t2.getText())));
					s3.revalidate();
				}else{
					s3.setValue(Math.round(-100*Float.parseFloat(t2.getText())));
					s3.revalidate();
				}
			}else if(PaintTool.getType() == 21){
				s2.setValue(Math.round(14*Integer.parseInt(t2.getText())));
				s2.revalidate();
			}
		}else if (e.getSource() == rb1) {//type1
			if(PaintTool.getType() == 15){
				if(rb6.isSelected()){
					s3.setMaximum(1500);
					s3.setMinimum(100);
					s3.setValue(200);
					t2.setText(String.valueOf((float)s3.getValue()/100.0));
					
				}else{
					s3.setMaximum(0);
					s3.setMinimum(-100);
					s3.setValue(-80);
					t2.setText(String.valueOf((float)-s3.getValue()/100.0));
				}
			}
		}else if (e.getSource() == rb2) {//type2
			if(PaintTool.getType() == 15){
				if(rb6.isSelected()){
					s3.setMaximum(990);
					s3.setMinimum(0);
					s3.setValue(200);
					t2.setText(String.valueOf((float)s3.getValue()/100.0));
				}else{
					s3.setMaximum(666);
					s3.setMinimum(0);
					s3.setValue(200);
					t2.setText(String.valueOf((float)-s3.getValue()/100.0));
				}
			}
		}else if (e.getSource() == rb3) {//グラデ1
			if(PaintTool.getType() == 17){
				rb2.setEnabled(false);
				rb1.setSelected(true);
				DrawPanel.centerx = -1;
				DrawPanel.centery = -1;
			}
		}else if (e.getSource() == rb4) {//グラデ2
			if(PaintTool.getType() == 17){
				rb2.setEnabled(false);
				rb1.setSelected(true);
				DrawPanel.centerx = -1;
				DrawPanel.centery = -1;
			}
		}else if (e.getSource() == rb5) {//グラデ3
			if(PaintTool.getType() == 17){
				rb2.setEnabled(true);
			}
		}else if (e.getSource() == rb6) {//明るく
			if(PaintTool.getType() == 15){
				if(rb1.isSelected()){
					s3.setMaximum(1500);
					s3.setMinimum(100);
					s3.setValue(200);
				}else{
					s3.setMaximum(990);
					s3.setMinimum(0);
					s3.setValue(200);
				}
				t2.setText(String.valueOf((float)s3.getValue()/100.0));
			}
		}else if (e.getSource() == rb7) {//暗く
			if(PaintTool.getType() == 15){
				if(rb1.isSelected()){
					s3.setMaximum(0);
					s3.setMinimum(-100);
					s3.setValue(-80);
				}else{
					s3.setMaximum(666);
					s3.setMinimum(0);
					s3.setValue(200);
				}
				t2.setText(String.valueOf((float)-s3.getValue()/100.0));
			}
		} else if (e.getSource() == cb1) { // 回転
			PaintTool.rote = cb1.isSelected();
			t2.setEnabled(PaintTool.rote);
			rb1.setEnabled(PaintTool.rote);
			rb2.setEnabled(PaintTool.rote);
			l3.setEnabled(PaintTool.rote);
		} else if (e.getSource() == cb3) { // ｸﾞﾗﾃﾞｰｼｮﾝ塗り
			if(cb3.isSelected()){
				cb4.setSelected(false);
				l7.setEnabled(false);
				t6.setEnabled(false);
			}else{
				l7.setEnabled(true);
				t6.setEnabled(true);
			}
		} else if (e.getSource() == cb4) { // ﾏｰﾌﾞﾙ塗り
			if(cb4.isSelected()){
				cb3.setSelected(false);
				l7.setEnabled(false);
				t6.setEnabled(false);
			}else{
				l7.setEnabled(true);
				t6.setEnabled(true);
			}
		} else if (e.getSource() == t5) {
			if(Float.parseFloat(t5.getText()) >= 0 && Float.parseFloat(t5.getText()) <= 1){
				s.setValue(Math.round(10000*Float.parseFloat(t5.getText())));
			}else{
				t5.setText(String.valueOf(s.getValue()));
			}
		}else{
			switch(PaintTool.getType()){
			case 0:
				size = Integer.parseInt(t1.getText());
				break;
			case 1:
				size = Integer.parseInt(t4.getText());
				break;
			case 3:
				size = Integer.parseInt(t4.getText());
				break;
			case 5:
				size = Integer.parseInt(t1.getText());
				break;
			case 6:
				size = Integer.parseInt(t4.getText());
				break;
			case 8:
				spraysize1 = Integer.parseInt(t1.getText());
				spraysize2 = Integer.parseInt(t2.getText());
				spraysize3 = Integer.parseInt(t3.getText());
				spraysize4 = Integer.parseInt(t4.getText());
				break;
			case 13:
				size = Integer.parseInt(t1.getText());
				break;
			case 14:
				size2 = Integer.parseInt(t1.getText());
				break;
			case 15:
				size2 = Integer.parseInt(t1.getText());
				break;
			case 18:
				size = Integer.parseInt(t4.getText());
				break;
			case 20:
				size = Integer.parseInt(t4.getText());
				break;
			case 21:
				sikiisize = Integer.parseInt(t2.getText());
				break;
			case 23:
				size = Integer.parseInt(t4.getText());
				break;
			case 22:
				size2 = Integer.parseInt(t1.getText());
				break;
			case 25:
				size = Integer.parseInt(t1.getText());
				break;
			}
			PaintTool.moziInvisible();
			PaintTool.originalInvisible();
			remove(l2);
			remove(l3);
			remove(l4);
			remove(l5);
			remove(l6);
			remove(l7);
			remove(l8);
			remove(l9);
			remove(il1);
			remove(il2);
			remove(il3);
			remove(t1);
			remove(t2);
			remove(t3);
			remove(t4);
			remove(t5);
			remove(t6);
			remove(t7);
			remove(t8);
			remove(b1);
			remove(b2);
			remove(b4);
			remove(b5);
			remove(b6);
			remove(b7);
			remove(b8);
			remove(b9);
			remove(cb1);
			remove(cb2);
			remove(cb3);
			remove(cb4);
			remove(cbh);
			remove(cbf);
			remove(rb1);
			remove(rb2);
			remove(rb3);
			remove(rb4);
			remove(rb5);
			remove(rb10);
			remove(rb6);
			remove(rb7);
			remove(rb8);
			remove(rb9);
			remove(rb11);
			remove(s);
			remove(s2);
			remove(s3);
			l7.setEnabled(true);
			t6.setEnabled(true);
			b9.setEnabled(true);
			t2.setEnabled(true);
			rb1.setEnabled(true);
			rb2.setEnabled(true);
			l3.setEnabled(true);
			b7.setEnabled(true);
			repaint();
			PaintTool.comand = 5;
			PaintTool.rewrite();
			l3.setText("");
			if(e.getSource() == cbh){//変形ボックス変更
				if (cbh.getSelectedItem() == "左右反転") {
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					add(b6);
					add(cbh);
					add(b2);
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
				} else if (cbh.getSelectedItem() == "上下反転") {
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					add(b6);
					add(cbh);
					add(b2);
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
				} else if (cbh.getSelectedItem() == "回転") {
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					add(b6);
					add(cbh);
					add(b2);
					add(t3);
					add(l5);
					t3.setText(String.valueOf(PaintTool.spin));
					l5.setText("°");
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
				} else if (cbh.getSelectedItem() == "拡大縮小") {
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					add(b6);
					add(cbh);
					add(b2);
					add(l2);
					add(t1);
					add(l3);
					add(t2);
					add(l6);
					l2.setText("x軸");
					t1.setText(String.valueOf(PaintTool.ratiox));
					l3.setText("% y軸");
					t2.setText(String.valueOf(PaintTool.ratioy));
					l6.setText("%");
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
				} else if (cbh.getSelectedItem() == "傾き") {
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					rb3.setSelected(true);
					add(b6);
					add(cbh);
					add(b2);
					
					add(t4);
					add(rb1);
					add(rb2);
					rb1.setSelected(true);
					t4.setText(String.valueOf(PaintTool.shx));
					rb1.setText("x軸");
					rb2.setText("y軸");
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
				}
			}else{
				DrawPanel.move = 0;
				DrawPanel.dontfill = false;
				DrawPanel.pointset = false;
				if (PaintTool.type == 12) {
					PaintTool.selectRectDraw();
				}
				if (!(cb.getSelectedItem() == "ｺﾋﾟｰｽﾀﾝﾌﾟ")) {
					DrawPanel.stampimg = null;
					DrawPanel.stampset = false;
				}
				if (!(cb.getSelectedItem() == "ｺﾋﾟｰﾌﾞﾗｼ")) {
					DrawPanel.stampimg = null;
					DrawPanel.stampset = false;
				}
				DrawPanel.x1 = -10;
				DrawPanel.x2 = -10;
				DrawPanel.y1 = -10;
				DrawPanel.y2 = -10;
				DrawPanel.readyarc = false;
				if (cb.getSelectedItem() == "直線") { // 直線
					PaintTool.setType(0);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size));
				} else if (cb.getSelectedItem() == "長方形") { // 長方形
					PaintTool.setType(1);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l9);
					l9.setText("太さ");
					add(t4);
					t4.setText(Integer.toString(size));
					add(l4);
					add(t3);
					add(l5);
					l4.setText("回転率");
					t3.setText("0");
					l5.setText("°");
					add(l7);
					l7.setText(" ポイントタイプ");
					add(rb1);
					add(rb2);
					rb1.setText("枠内");
					rb2.setText("中心から");
					add(l2);
					add(t1);
					add(l3);
					add(t2);
					l2.setText("複数描写");
					t1.setText("0");
					l3.setText("/");
					t2.setText("10");
					add(cb2);
				} else if (cb.getSelectedItem() == "長方形(中塗)") { // 長方形(中塗)
					PaintTool.setType(2);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l4);
					add(t3);
					add(l5);
					l4.setText("回転率");
					t3.setText("0");
					l5.setText("°　中抜き割合");
					add(t2);
					add(s2);
					s2.revalidate();
					s2.setValue(1);
					t2.setText("0.0");
					add(l7);
					l7.setText(" ポイントタイプ");
					add(rb1);
					add(rb2);
					rb1.setText("枠内");
					rb2.setText("中心から");
				} else if (cb.getSelectedItem() == "楕円") { // 楕円
					PaintTool.setType(3);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l9);
					l9.setText("太さ");
					add(t4);
					t4.setText(Integer.toString(size));
					add(l4);
					add(t3);
					add(l5);
					l4.setText("回転率");
					t3.setText("0");
					l5.setText("°");
					add(l7);
					l7.setText(" ポイントタイプ");
					add(rb1);
					add(rb2);
					rb1.setText("枠内");
					rb2.setText("中心から");
					add(l2);
					add(t1);
					add(l3);
					add(t2);
					l2.setText("複数描写");
					t1.setText("0");
					l3.setText("/");
					t2.setText("10");
					add(cb2);
				} else if (cb.getSelectedItem() == "楕円(中塗)") { // 楕円(中塗)
					PaintTool.setType(4);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					add(l4);
					add(t3);
					add(l5);
					l6.setText("透明度");
					l4.setText("回転率");
					t3.setText("0");
					l5.setText("°　中抜き割合");
					add(t2);
					add(s2);
					s2.revalidate();
					s2.setValue(1);
					t2.setText("0.0");
					add(l7);
					l7.setText(" ポイントタイプ");
					add(rb1);
					add(rb2);
					rb1.setText("枠内");
					rb2.setText("中心から");
				} else if (cb.getSelectedItem() == "線") { // 線
					PaintTool.setType(5);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size));
					DrawPanel.imgtemp = null;
				} else if (cb.getSelectedItem() == "多角形") { // 多角形
					PaintTool.setType(6);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l9);
					l9.setText("太さ");
					add(t4);
					t4.setText(Integer.toString(size));
					DrawPanel.pcnt = 0;
					add(l2);
					add(t1);
					l2.setText("画数");
					t1.setText("3");
					add(b9);
					b9.setText("頂点を１つ消す");
					b9.setEnabled(false);
				} else if (cb.getSelectedItem() == "多角形(中塗)") { // 多角形(中塗)
					PaintTool.setType(7);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					DrawPanel.pcnt = 0;
					add(l2);
					add(t1);
					l2.setText("画数");
					t1.setText("3");
					add(b9);
					b9.setText("頂点を１つ消す");
					b9.setEnabled(false);
				} else if(cb.getSelectedItem() == "自由形"){
					PaintTool.setType(23);
					DrawPanel.pcnt = 0;
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l9);
					l9.setText("太さ");
					add(t4);
					t4.setText(Integer.toString(size));
				} else if(cb.getSelectedItem() == "自由形(中塗)"){
					PaintTool.setType(24);
					DrawPanel.pcnt = 0;
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
				} else if (cb.getSelectedItem() == "スプレー") { // スプレー
					PaintTool.setType(8);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l2);
					add(t1);
					add(l3);
					add(t2);
					add(l4);
					add(t3);
					add(l5);
					add(t4);
					l2.setText("描写領域");
					t1.setText(Integer.toString(spraysize1));
					l3.setText("粒数");
					t2.setText(Integer.toString(spraysize2));
					l4.setText("大きさ最小");
					t3.setText(Integer.toString(spraysize3));
					l5.setText("最大");
					t4.setText(Integer.toString(spraysize4));
					add(l7);
					l7.setText("色数");
					add(t6);
					t6.setText("1");
					add(cb3);
					add(cb4);
				} else if (cb.getSelectedItem() == "文字") { // 文字
					PaintTool.setType(9);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					PaintTool.moziVisible();
				}else if (cb.getSelectedItem() == "色取込") { // GrabColor
					PaintTool.setType(11);
				} else if (cb.getSelectedItem() == "選択ツール") { // SelectTool
					PaintTool.setType(12);
					add(b1);
					add(b4);
					add(rb3);
					add(rb4);
					add(rb5);
					add(rb10);
					rb3.setText("move");
					rb4.setText("add");
					rb5.setText("remove");
					rb3.setSelected(true);
					add(b6);
					add(cbh);
					add(b2);
					if (cbh.getSelectedItem() == "回転") {
						add(t3);
						add(l5);
						t3.setText(String.valueOf(PaintTool.spin));
						l5.setText("°");
					} else if (cbh.getSelectedItem() == "拡大縮小") {
						add(l2);
						add(t1);
						add(l3);
						add(t2);
						add(l6);
						l2.setText("x軸");
						t1.setText(String.valueOf(PaintTool.ratiox));
						l3.setText("% y軸");
						t2.setText(String.valueOf(PaintTool.ratioy));
						l6.setText("%");
					} else if (cbh.getSelectedItem() == "傾き") {
						add(t4);
						add(rb1);
						add(rb2);
						rb1.setSelected(true);
						t4.setText(String.valueOf(PaintTool.shx));
						rb1.setText("x軸");
						rb2.setText("y軸");
					}
					if(SelectOptionFrame.rb9.isSelected()){
						add(b9);
					}
					b9.setText("頂点を１つ消す");
					if(DrawPanel.set == true){
						DrawPanel.dontfill = true;
						PaintTool.capture();
					}
				} else if (cb.getSelectedItem() == "オリジナル線") { // OriginalLine
					PaintTool.setType(13);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size));
					add(cb1);
					add(rb1);
					add(rb2);
					add(l3);
					add(t2);
					l3.setText("回転スピード");
					t2.setText("6");
					cb1.setText("回転");
					cb1.setSelected(false);
					PaintTool.rote = false;
					rb1.setText("左回転");
					rb2.setText("右回転");
					rb1.setSelected(true);
					t2.setEnabled(PaintTool.rote);
					rb1.setEnabled(PaintTool.rote);
					rb2.setEnabled(PaintTool.rote);
					l3.setEnabled(PaintTool.rote);
					PaintTool.originalVisible();
				}else if (cb.getSelectedItem() == "フィルター") { // フィルター
					PaintTool.setType(14);
					add(l7);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l7.setText("透明度");
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size2));
					add(l6);
					add(t2);
					l6.setText("重ねがけ回数");
					t2.setText("1");
					add(cbf);
					add(b5);
					b5.setText("全体変換");
					DrawPanel.x2 = -1000;
					DrawPanel.y2 = -1000;
				}else if (cb.getSelectedItem() == "輝度") { // Bright
					PaintTool.setType(15);
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size2));
					add(rb6);
					add(rb7);
					rb6.setText("明るく");
					rb7.setText("暗く");
					rb6.setSelected(true);
					rb6.setEnabled(true);
					rb7.setEnabled(true);
					s3.setMaximum(1500);
					s3.setMinimum(100);
					s3.setValue(200);
					add(l6);
					add(s3);
					add(t2);
					l6.setText("つよさ");
					t2.setText("2.0");
					add(rb1);
					add(rb2);
					rb1.setText("type1");
					rb2.setText("type2");
					add(b5);
					b5.setText("全体変換");
					DrawPanel.x2 = -1000;
					DrawPanel.y2 = -1000;
				}else if (cb.getSelectedItem() == "RGBフィルター") { // RGBフィルター
					PaintTool.setType(25);
					add(l9);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l9.setText("透明度");
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size2));
					add(l3);
					add(t2);
					add(l4);
					add(t3);
					add(l5);
					add(t4);
					l3.setText("乗算・R");
					l4.setText("G");
					l5.setText("B");
					t2.setText("1.0");
					t3.setText("1.0");
					t4.setText("1.0");
					add(l6);
					add(t6);
					add(l7);
					add(t7);
					add(l8);
					add(t8);
					l6.setText("加算・R");
					l7.setText("G");
					l8.setText("B");
					t6.setText("0");
					t7.setText("0");
					t8.setText("0");
					
					add(b5);
					b5.setText("全体変換");
				}else if (cb.getSelectedItem() == "グラデーション") { // Gradation
					PaintTool.setType(17);
					add(l6);
					add(s);
					s.revalidate();
					t5.setText("1");
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(rb3);
					add(il1);
					add(rb4);
					add(il2);
					add(rb5);
					add(il3);
					rb3.setText("");
					rb4.setText("");
					rb5.setText("");
					rb3.setSelected(true);
					add(rb8);
					add(rb9);
					add(rb11);
					rb8.setText("NORMAL");
					rb9.setText("REFLECT");
					rb11.setText("REPEAT");
					rb8.setSelected(true);
					add(rb1);
					add(rb2);
					rb1.setText("描く");
					rb2.setText("始点設定");
					rb1.setSelected(true);
					rb2.setEnabled(false);
					DrawPanel.centerx = -1;
					DrawPanel.centery = -1;
				}else if (cb.getSelectedItem() == "水平線") { // HorizonLine
					PaintTool.setType(18);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(l9);
					add(t4);
					l9.setText("サイズ");
					t4.setText(Integer.toString(size));
					add(cb1);
					cb1.setText("塗り潰し");
					cb1.setSelected(false);
					rb1.setEnabled(false);
					rb2.setEnabled(false);
					l3.setEnabled(false);
					add(l3);
					add(rb1);
					add(rb2);
					l3.setText("範囲");
					rb1.setText("外");
					rb2.setText("中");
				} else if (cb.getSelectedItem() == "ｺﾋﾟｰｽﾀﾝﾌﾟ") { // CopyStamp
					PaintTool.setType(19);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					l6.setText("透明度");
					add(rb3);
					add(rb4);
					add(rb5);
					rb3.setText("●");
					rb4.setText("■");
					rb5.setText("線");
					rb3.setSelected(true);
					add(b7);
					b7.setEnabled(false);
				} else if (cb.getSelectedItem() == "ｺﾋﾟｰﾌﾞﾗｼ") { // CopyBrashi
					PaintTool.setType(22);
					add(l6);
					l6.setText("透明度");
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					add(t5);
					add(l2);
					add(t1);
					l2.setText("サイズ");
					t1.setText(Integer.toString(size2));
					add(b7);
					b7.setEnabled(false);
				} else if (cb.getSelectedItem() == "曲線") { // CurveLine
					PaintTool.setType(20);
					add(l6);
					add(s);
					s.revalidate();
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					DrawPanel.pcnt = 0;
					add(t5);
					l6.setText("透明度");
					add(l9);
					l9.setText("サイズ");
					add(t4);
					t4.setText(Integer.toString(size));
					add(l3);
					l3.setText("|");
					add(rb1);
					add(rb2);
					rb1.setText("描く");
					rb2.setText("引っ張る");
					rb1.setSelected(true);
					rb2.setEnabled(false);
					add(l4);
					l4.setText("|");
					add(rb6);
					add(rb7);
					rb6.setText("点1");
					rb7.setText("点2");
					rb6.setSelected(true);
					rb6.setEnabled(false);
					rb7.setEnabled(false);
					add(b8);
					b8.setEnabled(false);
					DrawPanel.curvex = -1000;
					DrawPanel.curvey = -1000;
					DrawPanel.curvex2 = -1000;
					DrawPanel.curvey2 = -1000;
					DrawPanel.pcnt2 = 0;
					add(b9);
					b9.setEnabled(false);
					b9.setText("線を１つ消す");
				}else if (cb.getSelectedItem() == "塗り潰し") { // Fill
					PaintTool.setType(21);
					add(l6);
					add(s);
					try{
						s.setValue(Math.round(Float.parseFloat(t5.getText())*10000));
					}catch (NumberFormatException t) {
						s.setValue(10000);
					}
					s.revalidate();
					add(t5);
					l6.setText("透明度");
					add(l2);
					add(s2);
					s2.setValue(sikiisize*14);
					s2.revalidate();
					add(t2);
					l2.setText("しきい値");
					t2.setText(String.valueOf(sikiisize));
				}
			}
		}
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == s) {
			if(s.getValue() != 1){
				t5.setText(String.valueOf((float)s.getValue()/10000.0));
			}else{
				t5.setText("0");
			}
		}else if (e.getSource() == s2) {
			if(s2.getValue() != 1){
				if(PaintTool.getType() == 21){
					t2.setText(String.valueOf(s2.getValue()/14));
				}else{
					t2.setText(String.valueOf((float)s2.getValue()/10000.0));
				}
			}else{
				t2.setText("0");
			}
		}else if (e.getSource() == s3) {
			if(rb6.isSelected()){
				t2.setText(String.valueOf((float)s3.getValue()/100.0));
			}else{
				t2.setText(String.valueOf((float)-s3.getValue()/100.0));
			}
		}
	}
	
	public void changeSelectTool(){
		switch(PaintTool.getType()){
		case 0:
			size = Integer.parseInt(t1.getText());
			break;
		case 1:
			size = Integer.parseInt(t4.getText());
			break;
		case 3:
			size = Integer.parseInt(t4.getText());
			break;
		case 5:
			size = Integer.parseInt(t1.getText());
			break;
		case 6:
			size = Integer.parseInt(t4.getText());
			break;
		case 8:
			spraysize1 = Integer.parseInt(t1.getText());
			spraysize2 = Integer.parseInt(t2.getText());
			spraysize3 = Integer.parseInt(t3.getText());
			spraysize4 = Integer.parseInt(t4.getText());
			break;
		case 10:
			size = Integer.parseInt(t1.getText());
			break;
		case 13:
			size = Integer.parseInt(t1.getText());
			break;
		case 14:
			size2 = Integer.parseInt(t1.getText());
			break;
		case 15:
			size2 = Integer.parseInt(t1.getText());
			break;
		case 18:
			size = Integer.parseInt(t4.getText());
			break;
		case 20:
			size = Integer.parseInt(t4.getText());
			break;
		case 21:
			sikiisize = Integer.parseInt(t2.getText());
			break;
		case 23:
			size = Integer.parseInt(t4.getText());
			break;
		case 22:
			size2 = Integer.parseInt(t1.getText());
			break;
		case 25:
			size = Integer.parseInt(t1.getText());
			break;
		}
		PaintTool.moziInvisible();
		PaintTool.originalInvisible();
		remove(l2);
		remove(l3);
		remove(l4);
		remove(l5);
		remove(l6);
		remove(l7);
		remove(l8);
		remove(l9);
		remove(il1);
		remove(il2);
		remove(il3);
		remove(t1);
		remove(t2);
		remove(t3);
		remove(t4);
		remove(t5);
		remove(t6);
		remove(t7);
		remove(t8);
		remove(b1);
		remove(b2);
		remove(b4);
		remove(b5);
		remove(b6);
		remove(b7);
		remove(b8);
		remove(b9);
		remove(cb1);
		remove(cb2);
		remove(cb3);
		remove(cb4);
		remove(cbh);
		remove(cbf);
		remove(rb1);
		remove(rb2);
		remove(rb3);
		remove(rb4);
		remove(rb5);
		remove(rb10);
		remove(rb6);
		remove(rb7);
		remove(rb8);
		remove(rb9);
		remove(s);
		remove(s2);
		remove(s3);
		l7.setEnabled(true);
		t6.setEnabled(true);
		b9.setEnabled(true);
		t2.setEnabled(true);
		rb1.setEnabled(true);
		rb2.setEnabled(true);
		l3.setEnabled(true);
		b7.setEnabled(true);
		repaint();
		PaintTool.comand = 5;
		PaintTool.rewrite();
		l3.setText("");
		
		cb.setSelectedItem("選択ツール");
		PaintTool.setType(12);
		add(b1);
		add(b4);
		add(rb3);
		add(rb4);
		add(rb5);
		add(rb10);
		rb3.setText("move");
		rb4.setText("add");
		rb5.setText("remove");
		rb3.setSelected(true);
		add(b6);
		add(cbh);
		add(b2);
		if (cbh.getSelectedItem() == "回転") {
			add(t3);
			add(l5);
			t3.setText(String.valueOf(PaintTool.spin));
			l5.setText("°");
		} else if (cbh.getSelectedItem() == "拡大縮小") {
			add(l2);
			add(t1);
			add(l3);
			add(t2);
			add(l6);
			l2.setText("x軸");
			t1.setText(String.valueOf(PaintTool.ratiox));
			l3.setText("% y軸");
			t2.setText(String.valueOf(PaintTool.ratioy));
			l6.setText("%");
		} else if (cbh.getSelectedItem() == "傾き") {
			add(t4);
			add(rb1);
			add(rb2);
			rb1.setSelected(true);
			t4.setText(String.valueOf(PaintTool.shx));
			rb1.setText("x軸");
			rb2.setText("y軸");
		}
		if(SelectOptionFrame.rb9.isSelected()){
			add(b9);
		}
		b9.setText("頂点を１つ消す");
		if(DrawPanel.set == true){
			DrawPanel.dontfill = true;
			PaintTool.capture();
		}
	}
	
}



