import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FillFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 2L;

	static public JRadioButton r1, r2;
	private ButtonGroup gr1;
	private JButton b1, b2;
	public JPanel imgp;
	static public BufferedImage img;
	
	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}
	
	
	/*★★コンストラクタ★★*/
	FillFrame() {

	    setTitle("塗り潰し");
	    setSize(200, 200);
	    Container c = getContentPane();
	    c.setLayout(new GridLayout(4, 1));
	    
	    r1 = new JRadioButton("単色", true);
	    r2 = new JRadioButton("パターン色");
		b1 = new JButton("パターンインポート");
		b2 = new JButton("選択範囲をパターン化");
	    
		imgp = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 =(Graphics2D)g;
				if(img != null){
					double scal = Math.min(50/img.getHeight(), 200/img.getWidth());
					g2.scale(scal, scal);
					g2.drawImage(img, 0, 0, imgp);
				}
				
			}
		};
	    
		gr1 = new ButtonGroup();
		gr1.add(r1);
		gr1.add(r2);
		
		JPanel p = new JPanel();
		p.add(r1);
		p.add(r2);
		add(p);
		
		add(b1);
		add(b2);
		
		add(imgp);
		
		b1.setEnabled(false);
		b2.setEnabled(false);
		setResizable(false);
		setVisible(false);
		
		img = null;
		
		r1.addActionListener(this);
		r2.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == b1) {//パターンインポート
			JFrame jFrame = new JFrame();
			FileDialog flDialog = new FileDialog(jFrame,"パターンをインポートする");
			flDialog.setMode(FileDialog.LOAD);
			flDialog.setVisible(true);
			File file = new File(flDialog.getDirectory()+flDialog.getFile());
			if (flDialog.getFile() != null) {
				try {
					img = ImageIO.read(file);
				} catch (Exception d) {
				}
				double scal = Math.min(50/(double)img.getHeight(), 200/(double)img.getWidth());
				Graphics2D g = (Graphics2D)imgp.getGraphics();
				System.out.println(scal);
				g.scale(scal, scal);
				g.drawImage(img, 0, 0, imgp);
			}
		}else if (e.getSource() == b2) {//選択範囲をパターン化
			if(DrawPanel.set){
				img = DrawPanel.imgin;
				double scal = Math.min(50/(double)img.getHeight(), 200/(double)img.getWidth());
				Graphics2D g = (Graphics2D)imgp.getGraphics();
				g.scale(scal, scal);
				g.drawImage(img, 0, 0, imgp);
			}
		}else if (e.getSource() == r1) {
			b1.setEnabled(false);
			b2.setEnabled(false);
		}else if (e.getSource() == r2) {
			b1.setEnabled(true);
			b2.setEnabled(true);
		}
	}
}
