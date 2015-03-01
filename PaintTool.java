import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

//筆透明度再現
//パターン描写
//点線描写

class PaintTool extends JFrame implements ClipboardOwner, ActionListener, ItemListener{
	private static final long serialVersionUID = 2L;
	
	public static int HistoryNUM = 200;
	public static int Heepsize = 170000000;
	
	public static int type = 0, selectframetype = 0, comand = 0, colornum = 0,
			sizex = 500, sizey = 500, spin = 0, ratiox = 100, ratioy = 100, moveindex;
	public static float shx = 0, shy = 0;
	public static double scale = 1;
	public static boolean rote = false, shtype;
	public static Color[] color = new Color[24];
	public static JScrollBar bx, by;
	static ToolPanel p1;
	static ColorPanel p2;
	static DrawPanel p3;
	static ComandPanel p5;
	static MoziFrame p4;
	static JPanel p6;
	static ChangeSize p7;
	static OriginalFrame p8;
	static SelectOptionFrame p9;
	static HistoryFrame p10;
	static ViewFrame p11;
	static ToolFrame p12;
	static ColorFrame p13;
	static RulerFrame p14;
	static StrokeFrame p15;
	static FillFrame p16;
	static public JScrollPane scrollpane;
	static public JPopupMenu popup;
	static  public JMenuItem copyMenuItem, pasteMenuItem;
	static JMenuItem savem, undom, redom, copym, pastem;
	static JRadioButtonMenuItem bmpm, jpgm, pngm, gifm;
	static  public JCheckBoxMenuItem historym, selectm, viewm, toolm, hsvm, rulerm, strokem, fillm;
	static JMenuBar menubar;

	/* ★★入出力系★★ */
	static void drawcation(boolean a) {
		p5.drawl3(a);
		p5.addNotify();
		p5.validate();
		p5.updateUI();
	}

	static void setColor(Color colors) {
		color[colornum] = colors;
	}

	static Color getColor() {
		return color[colornum];
	}

	static void setType(int a) {
		type = a;
	}

	static int getType() {
		return type;
	}

	static int getsize() {
		return p1.getsize();
	}

	static int getsize2() {
		return p1.getsize2();
	}

	static float getsize2f() {
		return p1.getsize2f();
	}

	static int getsize3() {
		return p1.getsize3();
	}

	static int getsize4() {
		return p1.getsize4();
	}

	static float getsize5() {
		return p1.getsize5();
	}
	
	static int getsize6() {
		return p1.getsize6();
	}

	static void Clear() {
		p7.t1.setText(String.valueOf(sizex));
		p7.t2.setText(String.valueOf(sizey));
		p7.setTitle("新しいキャンバス");
		p7.b2.setText("作成");
		p7.type = 1;
		p7.setLocation(PaintTool.scrollpane.getLocationOnScreen().x + PaintTool.scrollpane.getWidth()/2 - 230, PaintTool.scrollpane.getLocationOnScreen().y +PaintTool.scrollpane.getHeight()/2 - 100);
		p7.visible();
	}
	
	static void ChangeSize() {
		p7.t1.setText(String.valueOf(sizex));
		p7.t2.setText(String.valueOf(sizey));
		p7.setTitle("サイズ変更");
		p7.b2.setText("変更");
		p7.type = 0;
		p7.setLocation(PaintTool.scrollpane.getLocationOnScreen().x + PaintTool.scrollpane.getWidth()/2 - 230, PaintTool.scrollpane.getLocationOnScreen().y +PaintTool.scrollpane.getHeight()/2 - 100);
		p7.visible();
	}

	static void capture() {
		p3.capture();
	}

	static void henkan() {
		DrawPanel.capt = true;
		if(type == 15){
			DrawPanel.listname = "全体輝度";
		}else if(type == 25){
			DrawPanel.listname = "全体RGBフィルター";
		}else{
			DrawPanel.listname = "全体" + ToolPanel.cbf.getSelectedItem();
		}
		comand = 12;
		p3.regular = true;
		p3.rewrite();
	}

	static void sharp() {
		DrawPanel.capt = true;
		DrawPanel.listname = "全体シャープ";
		comand = 14;
		p3.regular = true;
		p3.rewrite();
	}
	
	
	static void changeShape() {
		if (!(p1.getsize3() == spin && ratiox == p1.getsize()
				&& ratioy == p1.getsize2() && ((shx == p1.getsize4f() && ToolPanel.rb2
				.isSelected() == false) || (shy == p1.getsize4f() && ToolPanel.rb2
				.isSelected() == true)))) {
			if (DrawPanel.dontfill == true && SelectOptionFrame.rb5.isSelected()) {
				DrawPanel.capt = true;
				DrawPanel.listname = "選択範囲白塗＆変形";
			}else{
				DrawPanel.capt = true;
				DrawPanel.listname = "選択範囲変形";
			}
			DrawPanel.debug = true;
			comand = 10;
			p3.rewrite();
		}
	}

	static void selectRectDraw() {
		if (DrawPanel.set == true) {
			p3.selectRectDraw();
		}
	}
	
	public static void AreaHanten() {
		if (DrawPanel.set == true) {
			p3.selectRectDraw();
			comand = 14;
			p3.rewrite();
		}
	}
	
	static void deside() {
		comand = 15;
		DrawPanel.capt = true;
		DrawPanel.listname = "曲線";
		p3.rewrite();
	}

	static void Undo() {
		comand = 2;
		p3.rewrite();
	}

	static void Redo() {
		comand = 3;
		p3.rewrite();
	}

	static void historyMove(int index) {
		moveindex = index - (DrawPanel.imgnum - DrawPanel.startimg + HistoryNUM)%HistoryNUM;
		if(moveindex != 0){
			comand = 20;
			p3.rewrite();
		}
	}

	static void Save() {
		comand = 4;
		p3.rewrite();
		p3.save();
	}

	static void Save2() {
		comand = 4;
		p3.rewrite();
		p3.save2();
	}

	static void open() {
		JFrame jFrame = new JFrame();
		FileDialog flDialog = new FileDialog(jFrame,"ファイルを開く");
		flDialog.setMode(FileDialog.LOAD);
		flDialog.setVisible(true);
		p3.file = new File(flDialog.getDirectory()+flDialog.getFile());
		if (flDialog.getFile() != null) {
			try {
				DrawPanel.filepass = flDialog.getDirectory()+flDialog.getFile();
				ComandPanel.b12.setEnabled(true);
				DrawPanel.capt = true;
				DrawPanel.listname = "開く";
				savem.setEnabled(true);
				comand = 8;
				main.setTitle("パッソペイント："+DrawPanel.filepass);
				p3.rewrite();
			} catch (Exception d) {
				p3.rewrite();
			}
		}else{
			p3.rewrite();
		}
		flDialog.dispose();
		jFrame.dispose();
	}

	static void triming() {
		if (DrawPanel.set == true) {
			Area are = new Area(p3.shap);
			AffineTransform tran = new AffineTransform();
			tran.shear(shx, shy);
			tran.scale((float) ratiox / (float) 100,
					(float) ratioy / (float) 100);
			tran.rotate(-spin * Math.PI / 180, 0, 0);
			are.transform(tran);
			sizex = are.getBounds().width;
			sizey = are.getBounds().height;
			p3.setPreferredSize(new Dimension((int) Math.round(scale * sizex),
					(int) Math.round(scale * sizey)));
			p3.revalidate();
			comand = 9;
			if (DrawPanel.dontfill == false) {
				DrawPanel.imgnum--;
			}
			DrawPanel.capt = true;
			DrawPanel.listname = "トリミング";
			DrawPanel.dontfill = false;
			p3.rewrite();
		}
	}

	static void rewrite() {
		p3.rewrite();
	}

	static void resetComand() {
		comand = 0;
	}

	static int getComand() {
		return comand;
	}

	static void moziVisible() {
		p4.visible();
	}

	static void moziInvisible() {
		p4.invisible();
	}

	static void originalVisible() {
		p8.visible();
	}

	static void originalInvisible() {
		p8.invisible();
	}

	static void optionVisible() {
		p9.visible();
	}

	static void optionInvisible() {
		p9.invisible();
	}
	
	static void viewVisible() {
		p11.repaint();
		p11.frame.setVisible(true);
	}
	
	static void viewInvisible() {
		p11.frame.setVisible(false);
	}
	
	static void toolVisible() {
		p12.visible();
	}

	static void toolInvisible() {
		p12.invisible();
	}
	
	static void hsvVisible() {
		p13.visible();
	}

	static void hsvInvisible() {
		p13.invisible();
	}
	
	static void rulerVisible() {
		p14.visible();
	}

	static void rulerInvisible() {
		p14.invisible();
	}
	
	static void strokeVisible() {
		p15.visible();
	}

	static void strokeInvisible() {
		p15.invisible();
	}
	
	static void fillVisible() {
		p16.visible();
	}

	static void fillInvisible() {
		p16.invisible();
	}

	static void historyVisible() {
		p10.visible();
	}

	static void historyInvisible() {
		p10.invisible();
	}

	static void historyrewrite() {
		p10.repaint();
	}

	static int getMozisize() {
		return p4.getsize();
	}

	static String getMozi() {
		return p4.getMozi();
	}

	static int getMoziStyle() {
		if (p4.rb1.isSelected()) {
			return 0;
		} else if (p4.rb2.isSelected()) {
			return 1;
		} else {
			return 2;
		}
	}

	static String getfont() {
		return (String) p4.cb.getSelectedItem();
	}

	public static void showPopup(MouseEvent e){
		copyMenuItem.setEnabled(DrawPanel.set);
		Clipboard clp = main.getToolkit().getSystemClipboard();
		Transferable data = clp.getContents(main);
		if (data == null || !data.isDataFlavorSupported(DataFlavor.imageFlavor)){
			pasteMenuItem.setEnabled(false);
		}else{
			pasteMenuItem.setEnabled(true);
		}
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
	
	static void openRect() {
		spin = 0;
		ratiox = 100;
		ratioy = 100;
		shx = 0;
		shy = 0;
		p3.openRect();
		DrawPanel.capt = true;
		DrawPanel.listname = "取込";
		rewrite();
	}

	static public void getclipboad() {
		if(type != 12){
			p1.changeSelectTool();
		}
		Clipboard clp = main.getToolkit().getSystemClipboard();
		Transferable data = clp.getContents(main);
		if (data != null && data.isDataFlavorSupported(DataFlavor.imageFlavor)){
			DrawPanel.capt = true;
			comand = 21;
			DrawPanel.listname = "貼付け";
			rewrite();
		}
	}

	static public void setclipboad() {
		BufferedImage outimg = new BufferedImage(DrawPanel.imgin.getWidth(), DrawPanel.imgin.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = outimg.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, outimg.getWidth(), outimg.getHeight());
		Area are = new Area(p3.shap);
		AffineTransform tran = new AffineTransform();
		tran.translate(-p3.shap.getBounds().getMinX(), -p3.shap.getBounds().getMinY());
		are.transform(tran);
		g.clip(are);
		g.drawImage(DrawPanel.imgin, 0, 0, main);
		Clipboard clp = main.getToolkit().getSystemClipboard();
		ImageSelection imgselection = new ImageSelection(outimg);
		clp.setContents(imgselection, null);
		pastem.setEnabled(true);
	}

	/* ★★コンストラクタ★★ */
	PaintTool() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("パッソペイント");
		setSize(1240, 650);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		sizex = 500;
		sizey = 500;
		scale = 1;
		
		p1 = new ToolPanel();
		p2 = new ColorPanel();
		p3 = new DrawPanel();
		p4 = new MoziFrame();
		p5 = new ComandPanel();
		p6 = new JPanel();
		p7 = new ChangeSize();
		p8 = new OriginalFrame();
		p9 = new SelectOptionFrame();
		scrollpane = new JScrollPane(p3,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bx = scrollpane.getHorizontalScrollBar();
		by = scrollpane.getVerticalScrollBar();
		bx.setUnitIncrement(10);
		by.setUnitIncrement(10);
		p10 = new HistoryFrame();
		p11 = new ViewFrame();
		p12 = new ToolFrame();
		p13 = new ColorFrame();
		p14 = new RulerFrame();
		p15 = new StrokeFrame();
		p16 = new FillFrame();
		p4.setAlwaysOnTop(true);
		p7.setAlwaysOnTop(true);
		p8.setAlwaysOnTop(true);
		
		c.setBackground(Color.white);
		p6.setLayout(new GridLayout(2, 1));
		p6.add(p5);
		p6.add(p1);

		setLayout(new BorderLayout());
		c.add(p6, BorderLayout.NORTH);
		c.add(p2, BorderLayout.WEST);
		c.add(scrollpane, BorderLayout.CENTER);

		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));

		color[0] = Color.black;
		color[1] = Color.gray;
		color[2] = Color.white;
		color[3] = Color.red;
		color[4] = Color.green;
		color[5] = Color.blue;
		color[6] = Color.cyan;
		color[7] = Color.yellow;
		color[8] = Color.magenta;
		color[9] = Color.orange;
		color[10] = Color.pink;
		color[11] = new Color(80,80,80);
		color[12] = new Color(0,128,128);
		color[13] = new Color(128,0,128);
		color[14] = new Color(128,128,0);
		color[15] = new Color(0,0,128);
		color[16] = new Color(0,128,0);
		color[17] = new Color(128,0,0);
		color[18] = Color.white;
		color[19] = Color.white;
		color[20] = Color.white;
		color[21] = Color.white;
		color[22] = Color.white;
		color[23] = Color.white;
		
		p3.addMouseListener(p3);
		p3.addMouseMotionListener(p3);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {//アプリケーション終了時
				File file;
				for (int i = 0; i < HistoryNUM; i++) {
					file = new File(DrawPanel.tmpname + i + ".bmp");
					file.delete();
					file = new File(DrawPanel.tmpname + "in" + i + ".bmp");
					file.delete();
				}
				System.exit(0);
			}
			
			public void windowActivated(WindowEvent e) {//アクティブ時
				Clipboard clp = main.getToolkit().getSystemClipboard();
				Transferable data = clp.getContents(main);
				if (data == null || !data.isDataFlavorSupported(DataFlavor.imageFlavor)){
					pastem.setEnabled(false);
				}else{
					pastem.setEnabled(true);
				}
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}
		});
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {//ウィンドウサイズ変更時
				p3.setPreferredSize(new Dimension((int)Math.round(scale * sizex), (int)Math.round(scale * sizey)));
				p3.revalidate();
				p3.repaint();
				scrollpane.repaint();
				repaint();
			}

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}
		});
		popup = new JPopupMenu();
		copyMenuItem = new JMenuItem(new MyAction( "コピー"));
		copyMenuItem.setBackground(Color.WHITE);
		copyMenuItem.setMnemonic(KeyEvent.VK_C);
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		pasteMenuItem = new JMenuItem(new MyAction( "貼り付け"));
		pasteMenuItem.setBackground(Color.WHITE);
		pasteMenuItem.setMnemonic(KeyEvent.VK_V);
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		popup.add(copyMenuItem);
		popup.add(pasteMenuItem);

		menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu Menu1 = new JMenu("ファイル");
		menubar.add(Menu1);
		JMenu Menu2 = new JMenu("保存形式");
		menubar.add(Menu2);
		JMenu Menu3 = new JMenu("編集");
		menubar.add(Menu3);
		JMenu Menu4 = new JMenu("ｳｨﾝﾄﾞｳ");
		menubar.add(Menu4);
		JMenu Menu5 = new JMenu("その他");
		menubar.add(Menu5);
		JMenuItem newm = new JMenuItem("新規作成");
		JMenuItem openm = new JMenuItem("開く");
		JMenuItem saveasm = new JMenuItem("名前をつけて保存");
		savem = new JMenuItem("上書き保存");
		JMenuItem closem = new JMenuItem("閉じる");
		savem.setEnabled(false);

		bmpm = new JRadioButtonMenuItem("bmp", true);
		jpgm = new JRadioButtonMenuItem("jpg", false);
		pngm = new JRadioButtonMenuItem("png", false);
		gifm = new JRadioButtonMenuItem("gif", false);
		ButtonGroup group = new ButtonGroup();
		group.add(bmpm);
		group.add(jpgm);
		group.add(pngm);
		group.add(gifm);

		undom = new JMenuItem("元に戻す");
		redom = new JMenuItem("やり直す");
		copym = new JMenuItem("コピー");
		pastem = new JMenuItem("貼り付け");
		undom.setEnabled(false);
		redom.setEnabled(false);
		copym.setEnabled(false);
		pastem.setEnabled(false);
		JMenuItem changem = new JMenuItem("サイズ変更");

		historym = new JCheckBoxMenuItem("履歴", false);
		viewm = new JCheckBoxMenuItem("ビュー", false);
		rulerm = new JCheckBoxMenuItem("ルーラー", false);
		selectm = new JCheckBoxMenuItem("選択ツール", false);
		toolm = new JCheckBoxMenuItem("ツール", false);
		hsvm = new JCheckBoxMenuItem("HSV色選択", false);
		strokem = new JCheckBoxMenuItem("ストローク", false);
		fillm = new JCheckBoxMenuItem("塗り潰し色", false);
		
		JMenuItem verm = new JMenuItem("バージョン情報");

		//メニューアイテムの追加
		Menu1.add(newm);
		Menu1.add(openm);
		Menu1.add(saveasm);
		Menu1.add(savem);
		Menu1.add(changem);
		Menu1.addSeparator();
		Menu1.add(closem);

		Menu2.add(bmpm);
		Menu2.add(jpgm);
		Menu2.add(pngm);
		Menu2.add(gifm);

		Menu3.add(undom);
		Menu3.add(redom);
		Menu3.addSeparator();
		Menu3.add(copym);
		Menu3.add(pastem);
		Menu3.addSeparator();
		Menu3.add(changem);

		Menu4.add(historym);
		Menu4.add(viewm);
		Menu4.add(rulerm);
		Menu4.add(selectm);
		Menu4.add(toolm);
		Menu4.add(hsvm);
		Menu4.add(strokem);
		Menu4.add(fillm);

		Menu5.add(verm);

		//イベントリスクの設定
		newm.addActionListener(this);
		openm.addActionListener(this);
		saveasm.addActionListener(this);
		savem.addActionListener(this);
		closem.addActionListener(this);
		newm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		openm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		saveasm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		savem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		closem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
		
		undom.addActionListener(this);
		redom.addActionListener(this);
		copym.addActionListener(this);
		pastem.addActionListener(this);
		changem.addActionListener(this);
		undom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		redom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		copym.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
		pastem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		changem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
		
		
		historym.addActionListener(this);
		selectm.addActionListener(this);
		viewm.addActionListener(this);
		toolm.addActionListener(this);
		hsvm.addActionListener(this);
		rulerm.addActionListener(this);
		strokem.addActionListener(this);
		fillm.addActionListener(this);
		historym.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		selectm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		viewm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));
		toolm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_DOWN_MASK));
		hsvm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		rulerm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		strokem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
		fillm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		
		
		verm.addActionListener(this);

		bmpm.addItemListener(this);
		jpgm.addItemListener(this);
		pngm.addItemListener(this);
		gifm.addItemListener(this);



		new DropTarget(this, new MyDropTargetAdapter());

		setVisible(true);
	}

	/* ★★メイン★★ */
	static PaintTool main;
	public static void main(String[] args) {
		main = new PaintTool();
		if(args.length == 1){
			DrawPanel.filepass = args[0];
			p3.file = new File(DrawPanel.filepass);
			try {
				main.setTitle("パッソペイント："+DrawPanel.filepass);
				ImageIO.read(p3.file).createGraphics();
				DrawPanel.img[0] = ImageIO.read(p3.file);
				ComandPanel.b12.setEnabled(true);
				savem.setEnabled(true);
				sizex = DrawPanel.img[0].getWidth();
				sizey = DrawPanel.img[0].getHeight();
				p3.setPreferredSize(new Dimension(sizex, sizey));
				p3.revalidate();
				rewrite();
			} catch (Exception d) {
				p3.rewrite();
			}
		}
	}

	/*★メニュー設定★*/
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "新規作成"){
			Clear();
		}else if (e.getActionCommand() == "開く"){
			open();
		}else if (e.getActionCommand() == "名前をつけて保存"){
			Save();
		}else if (e.getActionCommand() == "上書き保存"){
			Save2();
		}else if(e.getActionCommand() == "閉じる"){
			File file;
			for (int i = 0; i < HistoryNUM; i++) {
				file = new File(DrawPanel.tmpname + i + ".bmp");
				file.delete();
				file = new File(DrawPanel.tmpname + "in" + i + ".bmp");
				file.delete();
			}
			System.exit(0);
		}else if (e.getActionCommand() == "元に戻す"){
			Undo();
		}else if (e.getActionCommand() == "やり直す"){
			Redo();
		}else if(e.getActionCommand() == "コピー"){
			PaintTool.setclipboad();
		}else if(e.getActionCommand() == "貼り付け"){
			PaintTool.getclipboad();
		}else if (e.getActionCommand() == "サイズ変更"){
			ChangeSize();
		}else if (e.getActionCommand() == "履歴"){
			if(p10.isVisible()){
				historyInvisible();
				historym.setSelected(false);
			}else{
				historyVisible();
				historym.setSelected(true);
			}
		}else if (e.getActionCommand() == "選択ツール"){
			if(p9.isVisible()){
				optionInvisible();
				selectm.setSelected(false);
			}else{
				optionVisible();
				selectm.setSelected(true);
			}
		}else if (e.getActionCommand() == "ビュー"){
			if(p11.frame.isVisible()){
				viewInvisible();
				viewm.setSelected(false);
			}else{
				viewVisible();
				viewm.setSelected(true);
			}
		}else if (e.getActionCommand() == "ツール"){
			if(p12.isVisible()){
				toolInvisible();
				toolm.setSelected(false);
			}else{
				toolVisible();
				toolm.setSelected(true);
			}
		}else if (e.getActionCommand() == "HSV色選択"){
			if(p13.frame.isVisible()){
				hsvInvisible();
				hsvm.setSelected(false);
			}else{
				hsvVisible();
				hsvm.setSelected(true);
			}
		}else if (e.getActionCommand() == "ルーラー"){
			if(p14.isVisible()){
				rulerInvisible();
				rulerm.setSelected(false);
			}else{
				rulerVisible();
				rulerm.setSelected(true);
			}
		}else if (e.getActionCommand() == "ストローク"){
			if(p15.isVisible()){
				strokeInvisible();
				strokem.setSelected(false);
			}else{
				strokeVisible();
				strokem.setSelected(true);
			}
		}else if (e.getActionCommand() == "塗り潰し色"){
			if(p16.isVisible()){
				fillInvisible();
				fillm.setSelected(false);
			}else{
				fillVisible();
				fillm.setSelected(true);
			}
		}else if (e.getActionCommand() == "バージョン情報"){
			JOptionPane pane = new JOptionPane("Ver 26.3\nさすがにもうバグ無いよね・・・",JOptionPane.INFORMATION_MESSAGE);

			JDialog dialog = pane.createDialog(this, "バージョン情報");

			// モーダルの設定
			dialog.setVisible(true);
		}
	}

	/*★画像ドロップ動作★*/
	private class MyDropTargetAdapter extends DropTargetAdapter {
		@SuppressWarnings({ "rawtypes" })
		public void drop(DropTargetDropEvent dtde) {
			try {
				// 転送されたデータの取得
				Transferable tr=dtde.getTransferable();
				// 転送データがサポート可能なデータかどうか判定
				if(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					// ドロップ動作を受け入れる
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					// 転送データからファイルのリストを抽出
					java.util.List myList=(java.util.List)tr.getTransferData(DataFlavor.javaFileListFlavor);
					// リスト要素が単一かどうか判定
					// 複数ファイルのドロップを受け入れるのであれば，判定不要
					if(myList.size() == 1) {
						// リストからファイルの取り出し
						p3.file=(File)myList.get(0);
						try {
							ImageIO.read(p3.file).createGraphics();
							DrawPanel.img[DrawPanel.imgnum +1] = ImageIO.read(p3.file);
							DrawPanel.filepass = p3.file.getPath();
							main.setTitle("パッソペイント："+DrawPanel.filepass);
							ComandPanel.b12.setEnabled(true);
							DrawPanel.capt = true;
							DrawPanel.listname = "開く";
							savem.setEnabled(true);
							comand = 8;
							p3.rewrite();
							
						} catch (Exception d) {
							p3.rewrite();
						}
						// ファイルの絶対パス名を表示
						// 実際には，この１行を変更すると良い
						// ドロップ動作を正常終了
						dtde.getDropTargetContext().dropComplete(true);
					} else {
						// 「要素が多い」という警告を表示
						// ドロップ動作を異常終了
						dtde.getDropTargetContext().dropComplete(false);
					}
				} else {
					// ドロップ動作をはねつける
					dtde.rejectDrop();
				}
			} catch (IOException ioe) {
				// ドロップ動作をはねつける
				dtde.rejectDrop();
			} catch (UnsupportedFlavorException ufe) {
				// ドロップ動作をはねつける
				dtde.rejectDrop();
			}
		}
		public void dragEnter(DropTargetDragEvent dtde) {
			dtde.acceptDrag(DnDConstants.ACTION_COPY);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() == "クリア"){
			Clear();
		}
	}
}
