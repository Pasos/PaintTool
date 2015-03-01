import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 2L;
	
	public int oldx, oldy, tentenphase;
	public static int x1, x2, y1, y2, curvex, curvey, curvex2, curvey2, arcx1, arcy1, arcx2, arcy2, imgnum = 0, startimg = 0, endimg = 0, start2img = 0, end2img = 0,pcnt = 0, pcnt2 = 0, move = 0, setx1, setx2, sety1, sety2, ssetx1, ssety1, rotatex2, rotatey2, originalrotate = 0, copyx, copyy, distancecount, centerx, centery;
	public int[] x3, y3, x4 ,y4, curvesize, curveside;
	public float[] curvetrans;
	public float[][] curvepattern;
	public boolean off = false, regular = false, press = false, presstmp, clock = false, exit = false, clockswitch = false, reaverelease = false;
	public static boolean set = false, dontfill = false, capt = false, debug = false, rectcapt = false, stampset = false, pointset = false, readyarc = false;
	public static boolean[] sets, setchange, curveispattern;
	public static String listname = "最初";
	public Color[] curvecolor;
	private Font f;
	public static BufferedImage[] img;
	public static BufferedImage imgin, imgtemp, imgtemp2, stampimg;
	static Graphics2D g3;
	public Graphics2D g2;
	public Shape shap, stampshap;
	public Shape[] shaps;
	Area fillshap;
	Area aretmp;
	int[] dx = {-1,0,1,1,1,0,-1,-1}, dy = {-1,-1,-1,0,1,1,1,0};
	public File file;
	public static String filepass, tmpname;
	Timer timer1, timer2, timer3;

	/* ★★入出力系★★ */
	void rewrite() {
		repaint();
	}
	
	
	//ネガポジ変換
	public BufferedImage negaposi(BufferedImage img){
		for(int y = 0; y < img.getHeight(); y++){
			for(int x = 0; x < img.getWidth(); x++){
				int rgb = img.getRGB(x, y);
				rgb = -rgb +16777215; //r, g, bの色の値を反転 16777215(FFFFFF)
				img.setRGB(x, y, rgb);
			}
		} 
		return img;
	}
	
	//選択範囲開放処理
	void leaveArea(){
		set = false;
		sets[imgnum] = false;
		dontfill = false;
		move = 0;
		//setchange[(imgnum+(PaintTool.HistoryNUM - 1))%PaintTool.HistoryNUM] = true;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
		Area are1 = new Area(shap);
		AffineTransform tran1 = new AffineTransform();
		tran1.shear(PaintTool.shx, PaintTool.shy);
		tran1.scale((float)PaintTool.ratiox/(float)100, (float)PaintTool.ratioy/(float)100);
		tran1.rotate(-PaintTool.spin*Math.PI/180, rotatex2, rotatey2);
		tran1.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
		are1.transform(tran1);
		g2.setClip(are1);
		g2.shear(PaintTool.shx, PaintTool.shy);
		g2.scale((float)PaintTool.ratiox/(float)100, (float)PaintTool.ratioy/(float)100);
		g2.rotate(-PaintTool.spin*Math.PI/180, rotatex2, rotatey2);
		if(!(SelectOptionFrame.rb7.isSelected())){
			g2.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
		}
		g2.rotate(PaintTool.spin*Math.PI/180, rotatex2, rotatey2);
		g2.scale((float)100/(float)PaintTool.ratiox, (float)100/(float)PaintTool.ratioy);
		g2.shear(-PaintTool.shx, -PaintTool.shy);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2.setClip(null);
		PaintTool.spin = 0;
		PaintTool.ratiox = 100;
		PaintTool.ratioy = 100;
		PaintTool.shx = 0;
		PaintTool.shy = 0;
		if(SelectOptionFrame.rb9.isSelected() || SelectOptionFrame.rb10.isSelected()){
			g3.drawImage(img[imgnum], 0, 0, this);
		}
	}
	
	
	//名前をつけて保存
	void save() {
		JFrame jFrame = new JFrame();
		FileDialog flDialog = new FileDialog(jFrame,"ファイルを保存する");
		flDialog.setMode(FileDialog.SAVE);
		flDialog.setVisible(true);
		filepass = flDialog.getDirectory() + flDialog.getFile();
		if (flDialog.getFile() != null) {
			try {
				if(filepass.endsWith(".bmp")){
					file = new File(filepass);
					ImageIO.write(img[imgnum], "bmp", file);
				}else if(filepass.endsWith(".jpg")){
					file = new File(filepass);
					ImageIO.write(img[imgnum], "jpg", file);
				}else if(filepass.endsWith(".png")){
					file = new File(filepass);
					ImageIO.write(img[imgnum], "png", file);
				}else if(filepass.endsWith(".gif")){
					file = new File(filepass);
					ImageIO.write(img[imgnum], "gif", file);
				}else{
					if(PaintTool.bmpm.isSelected()){
						filepass = filepass + ".bmp";
						file = new File(filepass);
						ImageIO.write(img[imgnum], "bmp", file);
					}else if(PaintTool.jpgm.isSelected()){
						filepass = filepass + ".jpg";
						file = new File(filepass);
						ImageIO.write(img[imgnum], "jpg", file);
					}else if(PaintTool.pngm.isSelected()){
						filepass = filepass + ".png";
						file = new File(filepass);
						ImageIO.write(img[imgnum], "png", file);
					}else if(PaintTool.gifm.isSelected()){
						filepass = filepass + ".gif";
						file = new File(filepass);
						ImageIO.write(img[imgnum], "gif", file);
					}
				}
				
				PaintTool.savem.setEnabled(true);
				ComandPanel.b12.setEnabled(true);
			} catch (Exception d) {
			}
		}
		repaint();
		flDialog.dispose();
		jFrame.dispose();
	}
	
	//上書き保存
	void save2() {
		try {
			file = new File(filepass);
			if(filepass.endsWith(".bmp")){
				ImageIO.write(img[imgnum], "bmp", file);
			}else if(filepass.endsWith(".jpg")){
				ImageIO.write(img[imgnum], "jpg", file);
			}else if(filepass.endsWith(".png")){
				ImageIO.write(img[imgnum], "png", file);
			}else if(filepass.endsWith(".gif")){
				ImageIO.write(img[imgnum], "gif", file);
			}else{
				if(PaintTool.bmpm.isSelected()){
					ImageIO.write(img[imgnum], "bmp", file);
				}else if(PaintTool.jpgm.isSelected()){
					ImageIO.write(img[imgnum], "jpg", file);
				}else if(PaintTool.pngm.isSelected()){
					ImageIO.write(img[imgnum], "png", file);
				}else if(PaintTool.gifm.isSelected()){
					ImageIO.write(img[imgnum], "gif", file);
				}
			}
		} catch (Exception d) {
		}
		repaint();
	}

	void openRect() {
		JFrame jFrame = new JFrame();
		FileDialog flDialog = new FileDialog(jFrame,"画像をインポートする");
		flDialog.setMode(FileDialog.LOAD);
		flDialog.setVisible(true);
		file = new File(flDialog.getDirectory()+flDialog.getFile());
		if (flDialog.getFile() != null) {
			try {
				imgin = ImageIO.read(file);
			} catch (Exception d) {
			}
			if(SelectOptionFrame.rb2.isSelected()){
				if(set == false){
					shap = new Ellipse2D.Float(0, 0, imgin.getWidth(), imgin.getHeight());
				}
			}else{
				if(set == false){
					shap = new Rectangle(0, 0, imgin.getWidth(), imgin.getHeight());
				}
			}
			set = true;
			setx1 = 0;
			sety1 = 0;
			setx2 = imgin.getWidth();
			sety2 = imgin.getHeight();
		}
		flDialog.dispose();
		jFrame.dispose();
	}
	
	//選択範囲の画像をキャンパスに転写
	void selectRectDraw() {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
		Area are = new Area(shap);
		AffineTransform tran = new AffineTransform();
		tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
		are.transform(tran);
		g2.setClip(are);
		if((!(SelectOptionFrame.rb7.isSelected())) || PaintTool.selectframetype != 2){
			g2.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
			PaintTool.selectframetype = 2;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2.setClip(null);
	}

	
	public void capture() {
		Area are = new Area(shap);
		AffineTransform tran = new AffineTransform();
		tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
		are.transform(tran);
		tran = new AffineTransform();
		tran.shear(PaintTool.shx, PaintTool.shy);
		tran.scale((float)PaintTool.ratiox/(float)100, (float)PaintTool.ratioy/(float)100);
		tran.rotate(-PaintTool.spin*Math.PI/180, rotatex2, rotatey2);
		are.transform(tran);
		if(imgin != null){
			Graphics2D g4 = imgin.createGraphics();
			g4.rotate(PaintTool.spin*Math.PI/180, imgin.getWidth()/2, imgin.getHeight()/2);
			g4.scale((float)100/(float)PaintTool.ratiox, (float)100/(float)PaintTool.ratioy);
			g4.shear(-PaintTool.shx, -PaintTool.shy);
			if(!(SelectOptionFrame.rb7.isSelected())){
				g4.drawImage(img[imgnum], (int)-(are.getBounds2D().getWidth() - ((float)PaintTool.ratiox/(float)100*imgin.getWidth() + imgin.getHeight()*PaintTool.shx))/2, (int)-(are.getBounds2D().getHeight() - ((float)PaintTool.ratioy/(float)100*imgin.getHeight() + imgin.getWidth()*PaintTool.shy))/2, (int)(are.getBounds2D().getWidth()-(are.getBounds2D().getWidth() - ((float)PaintTool.ratiox/(float)100*imgin.getWidth() + imgin.getHeight()*PaintTool.shx))/2), (int)(are.getBounds2D().getHeight() - (are.getBounds2D().getHeight() - ((float)PaintTool.ratioy/(float)100*imgin.getHeight() + imgin.getWidth()*PaintTool.shy))/2),(int)are.getBounds2D().getMinX(),(int)are.getBounds2D().getMinY(),(int)are.getBounds2D().getMaxX(), (int)are.getBounds2D().getMaxY(), this);
			}
			g4.dispose();
		}
	}
	
	public void imgincopy(){
		Area are = new Area(shap);
		if(imgin != null){
			BufferedImage imgintmp = imgin;
			if(PaintTool.p1.cbh.getSelectedItem() == "左右反転"){
				imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g4 = imgin.createGraphics();
				AffineTransform at = AffineTransform.getScaleInstance(-1.0, 1.0);
				at.translate(-are.getBounds().getWidth(), 0);
				g4.drawImage(imgintmp, at, this);
				
				AffineTransform at2 = AffineTransform.getTranslateInstance(-shap.getBounds().getMinX(), -shap.getBounds().getMinY());
				are.transform(at2);
				are.transform(at);
				at2 = AffineTransform.getTranslateInstance(shap.getBounds().getMinX(), shap.getBounds().getMinY());
				are.transform(at2);
				shap = are;
				g4.dispose();
			}else if(PaintTool.p1.cbh.getSelectedItem() == "上下反転"){
				imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g4 = imgin.createGraphics();
				AffineTransform at = AffineTransform.getScaleInstance(1.0, -1.0);
				at.translate(0, -are.getBounds().getHeight());
				g4.drawImage(imgintmp, at, this);
				
				AffineTransform at2 = AffineTransform.getTranslateInstance(-shap.getBounds().getMinX(), -shap.getBounds().getMinY());
				are.transform(at2);
				are.transform(at);
				at2 = AffineTransform.getTranslateInstance(shap.getBounds().getMinX(), shap.getBounds().getMinY());
				are.transform(at2);
				shap = are;
				g4.dispose();
			}else{
				are = new Area(shap);
				AffineTransform tran = new AffineTransform();
				tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
				are.transform(tran);
				tran = new AffineTransform();
				tran.shear(PaintTool.shx, PaintTool.shy);
				tran.scale((float)PaintTool.ratiox/(float)100, (float)PaintTool.ratioy/(float)100);
				tran.rotate(-PaintTool.spin*Math.PI/180, rotatex2, rotatey2);
				are.transform(tran);
				Shape shaptmp = shap;
				shap = are;
				imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g4 = imgin.createGraphics();
				g4.shear(PaintTool.shx, PaintTool.shy);
				g4.scale((float)PaintTool.ratiox/(float)100,(float)PaintTool.ratioy/(float)100);
				g4.translate(-pointrx(shaptmp, -PaintTool.spin*Math.PI/180), -pointry(shaptmp, -PaintTool.spin*Math.PI/180));
				g4.rotate(-PaintTool.spin*Math.PI/180, 0, 0);
				g4.scale(1.04,1.04);
				g4.drawImage(imgintmp, (int)-Math.ceil((float)shaptmp.getBounds().getWidth()/50), (int)-Math.ceil((float)shaptmp.getBounds().getHeight()/50), this);
				g4.scale((float)1/1.04,(float)1/1.04);
				g4.drawImage(imgintmp, 0, 0, this);
				g4.dispose();
			}
		}
		PaintTool.spin = 0;
		PaintTool.ratiox = 100;
		PaintTool.ratioy = 100;
		PaintTool.shx = 0;
		PaintTool.shy = 0;
	}
	
	int pointrx(Shape sh,double rote){
		Area are = new Area(sh);
		AffineTransform tran = new AffineTransform();
		tran.translate(-sh.getBounds().getMinX(), -sh.getBounds().getMinY());
		are.transform(tran);
		tran = new AffineTransform();
		tran.rotate(rote, 0, 0);
		are.transform(tran);
		return are.getBounds().x;
	}
	
	int pointry(Shape sh,double rote){
		Area are = new Area(sh);
		AffineTransform tran = new AffineTransform();
		tran.translate(-sh.getBounds().getMinX(), -sh.getBounds().getMinY());
		are.transform(tran);
		tran = new AffineTransform();
		tran.rotate(rote, 0, 0);
		are.transform(tran);
		return are.getBounds().y;
	}

	int pointrotatex(int x,int y,double rote,int rotex, int rotey){
		return rotex+(int)Math.round((x-rotex)*Math.cos(rote) - (y-rotey)*Math.sin(rote));
	}

	int pointrotatey(int x,int y,double rote,int rotex, int rotey){
		return rotey+(int)Math.round((x-rotex)*Math.sin(rote) + (y-rotey)*Math.cos(rote));
	}
	
	void UndoLeave(){
		if(imgnum == start2img  && imgnum != startimg){//開放処理
			try {
				File file = new File(tmpname + (imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM + ".bmp");
				//★計算
				long heepsum;
				heepsum = 0;
				int i;
				for(i = 0;i < PaintTool.HistoryNUM ;i++){//すべてのimgサイズ計算
					if(i != (imgnum + 1) % PaintTool.HistoryNUM){
						if(img[i] != null){
							heepsum += img[i].getHeight()*img[i].getWidth();
						}
					}
				}
				heepsum += ImageIO.read(file).getWidth()*ImageIO.read(file).getHeight();//次のimg用
				heepsum += ImageIO.read(file).getWidth()*ImageIO.read(file).getHeight();//imgtemp用
				while(heepsum >= PaintTool.Heepsize){
					if(img[end2img] != null){
						heepsum -= img[end2img].getHeight()*img[start2img].getWidth();
						try {
							File file2 = new File(tmpname + end2img + ".bmp");
							ImageIO.write(img[end2img], "bmp", file2);
						} catch (Exception d) {
						}
						img[end2img] = null;//領域開放
					}
					end2img = (end2img + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM;
				}
				img[(imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM] = ImageIO.read(file);//bmpから読み込んでおく
				start2img = (start2img + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM;
			} catch (Exception d) {
			}
		}
	}
	
	void RedoLeave(){
		if(imgnum == end2img  && imgnum != endimg){//開放処理
			try {
				File file = new File(tmpname + (imgnum + 1) % PaintTool.HistoryNUM + ".bmp");
				//★計算
				long heepsum;
				heepsum = 0;
				int i;
				for(i = 0;i < PaintTool.HistoryNUM ;i++){//すべてのimgサイズ計算
					if(i != (imgnum + 1) % PaintTool.HistoryNUM){
						if(img[i] != null){
							heepsum += img[i].getHeight()*img[i].getWidth();
						}
					}
				}
				heepsum += ImageIO.read(file).getWidth()*ImageIO.read(file).getHeight();//次のimg用
				heepsum += ImageIO.read(file).getWidth()*ImageIO.read(file).getHeight();//imgtemp用

				while(heepsum >= PaintTool.Heepsize){
					if(img[start2img] != null){
						heepsum -= img[start2img].getHeight()*img[start2img].getWidth();
						try {
							File file2 = new File(tmpname + start2img + ".bmp");
							ImageIO.write(img[start2img], "bmp", file2);
						} catch (Exception d) {
						}
						img[start2img] = null;//img領域開放
					}
					start2img = ++start2img % PaintTool.HistoryNUM;
				}
				img[(imgnum + 1) % PaintTool.HistoryNUM] = ImageIO.read(file);//bmpから読み込んでおく
				end2img = ++end2img % PaintTool.HistoryNUM;
			} catch (Exception d) {
			}
		}
	}
	
	CycleMethod getGradientPattern(){
		if(ToolPanel.rb8.isSelected()){
			return MultipleGradientPaint.CycleMethod.NO_CYCLE;
		}else if(ToolPanel.rb9.isSelected()){
			return MultipleGradientPaint.CycleMethod.REFLECT;
		}else{
			return MultipleGradientPaint.CycleMethod.REPEAT;
		}
	}
	
	void setColor(Graphics2D g){
		if(FillFrame.r1.isSelected() || FillFrame.img == null){
			g.setColor(PaintTool.getColor());
		}else{
			 g.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
		}
	}
		
	void setStroke(Graphics2D g){
		float[] pattern = PaintTool.p15.getpattern();
		int size;
		switch(PaintTool.getType()){
		case 0:
			size = PaintTool.getsize();
			break;
		case 1:
			size = PaintTool.getsize4();
			break;
		case 3:
			size = PaintTool.getsize4();
			break;
		case 5:
			size = PaintTool.getsize();
			break;
		case 6:
			size = PaintTool.getsize4();
			break;
		case 10:
			size = PaintTool.getsize();
			break;
		case 13:
			size = PaintTool.getsize();
			break;
		case 18:
			size = PaintTool.getsize4();
			break;
		case 20:
			size = PaintTool.getsize4();
			break;
		case 23:
			size = PaintTool.getsize4();
			break;
		case 25:
			size = PaintTool.getsize();
			break;
		default:
			size = PaintTool.getsize();
			break;
		}
		
		if(pattern[0] == 0 && pattern.length == 1){
			g.setStroke(new BasicStroke(size, PaintTool.p15.getborder(), BasicStroke.JOIN_MITER));
		}else{
			g.setStroke(new BasicStroke(size, PaintTool.p15.getborder(), BasicStroke.JOIN_MITER, 1.0f, pattern, 0.0f));
		}
	}
	
	//選択範囲形作成
	void makeShap(){
		int r1, gr1, b1;
		b1 = img[imgnum].getRGB(x1, y1) & 0xff;
		gr1 = (img[imgnum].getRGB(x1, y1) / 256) - 1 & 0xff;
		if (b1 == 0)
			gr1 = (gr1 + 1) % 256;
		r1 = (img[imgnum].getRGB(x1, y1) / 256 / 256) - 1 & 0xff;
		if (b1 == 0 && gr1 == 0)
			r1 = (r1 + 1) % 256;
		boolean[][] map;
		map = new boolean[PaintTool.sizex][PaintTool.sizey];
		map[x1][y1] = true;
		LinkedList<int[]> array = new LinkedList<int[]>();
		array.offer(new int[]{x1, y1});
		int[] w = new int[2];
		while((w = array.poll()) != null){//map作成
			if(w[0]+1 < PaintTool.sizex){
				if(map[w[0]+1][w[1]] == false){
					int r, gr, b;
					b = img[imgnum].getRGB(w[0]+1, w[1]) & 0xff;
					gr = (img[imgnum].getRGB(w[0]+1, w[1]) / 256) - 1 & 0xff;
					if (b == 0) gr = (gr + 1) % 256;
					r = (img[imgnum].getRGB(w[0]+1, w[1]) / 256 / 256) - 1 & 0xff;
					if (b == 0 && gr == 0) r = (r + 1) % 256;
					if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= SelectOptionFrame.s.getValue()){
						map[w[0]+1][w[1]] = true;
						array.offer(new int[]{w[0]+1, w[1]});
					}
				}
			}
			if(w[0]-1 >= 0){
				if(map[w[0]-1][w[1]] == false){
					int r, gr, b;
					b = img[imgnum].getRGB(w[0]-1, w[1]) & 0xff;
					gr = (img[imgnum].getRGB(w[0]-1, w[1]) / 256) - 1 & 0xff;
					if (b == 0) gr = (gr + 1) % 256;
					r = (img[imgnum].getRGB(w[0]-1, w[1]) / 256 / 256) - 1 & 0xff;
					if (b == 0 && gr == 0) r = (r + 1) % 256;
					if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= SelectOptionFrame.s.getValue()){
						map[w[0]-1][w[1]] = true;
						array.offer(new int[]{w[0]-1, w[1]});
					}
				}
			}
			if(w[1]+1 < PaintTool.sizey){
				if(map[w[0]][w[1]+1] == false){
					int r, gr, b;
					b = img[imgnum].getRGB(w[0], w[1]+1) & 0xff;
					gr = (img[imgnum].getRGB(w[0], w[1]+1) / 256) - 1 & 0xff;
					if (b == 0) gr = (gr + 1) % 256;
					r = (img[imgnum].getRGB(w[0], w[1]+1) / 256 / 256) - 1 & 0xff;
					if (b == 0 && gr == 0) r = (r + 1) % 256;
					if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= SelectOptionFrame.s.getValue()){
						map[w[0]][w[1]+1] = true;
						array.offer(new int[]{w[0], w[1]+1});
					}
				}
			}
			if(w[1]-1 >= 0){
				if(map[w[0]][w[1]-1] == false){
					int r, gr, b;
					b = img[imgnum].getRGB(w[0], w[1]-1) & 0xff;
					gr = (img[imgnum].getRGB(w[0], w[1]-1) / 256) - 1 & 0xff;
					if (b == 0) gr = (gr + 1) % 256;
					r = (img[imgnum].getRGB(w[0], w[1]-1) / 256 / 256) - 1 & 0xff;
					if (b == 0 && gr == 0) r = (r + 1) % 256;
					if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= SelectOptionFrame.s.getValue()){
						map[w[0]][w[1]-1] = true;
						array.offer(new int[]{w[0], w[1]-1});
					}
				}
			}
		}
		int i = 0,j = 0;
		boolean breaks = false;
		for(i = 0;i < PaintTool.sizex;i++){//外枠Polygon初期位置検索
			for(j = 0;j < PaintTool.sizey; j++){
				if(map[i][j]){
					breaks = true;
					break;
				}
			}
			if(breaks == true)break;
		}
		pcnt2 = 0;
		pcnt = 0;
		oldx = i;
		oldy = j;
		int newx = i;
		int newy = j;
		x4[pcnt2] = i;
		y4[pcnt2++] = j;
		boolean[] around;
		int ins = 0;
		int[] instype = new int[100000];
		while(pcnt2 <= 2 || !(oldx == x4[0] && oldy == y4[0] && i == x4[1] && j == y4[1])){//外枠Polygon作成
			around = new boolean[8];
			for(int q = ins; q < ins;q = (q+1) % 8){
				if(!((i+dx[q] < 0 || i+dx[q] >= PaintTool.sizex || j+dy[q] < 0 || j+dy[q] >= PaintTool.sizey)?false:map[i+dx[q]][j+dy[q]])){
					around[q] = true;
				}else{
					if(q%2 == 1) break;
				}
			}
			for(int q = ins;q != (ins+1) % 8;q = (q+7) % 8){
				if(!((i+dx[q] < 0 || i+dx[q] >= PaintTool.sizex || j+dy[q] < 0 || j+dy[q] >= PaintTool.sizey)?false:map[i+dx[q]][j+dy[q]])){
					around[q] = true;
				}else{
					if(q%2 == 1) break;
				}
			}
			if(around[3]){
				if(around[5]){
					if(around[1]){
						instype[pcnt2-1] = 3;
						x4[pcnt2] = i;
						y4[pcnt2++] = j;
						instype[pcnt2-1] = 1;
					}else if(around[7]){
						instype[pcnt2-1] = 2;
						x4[pcnt2] = i;
						y4[pcnt2++] = j;
						instype[pcnt2-1] = 3;
					}else{
						instype[pcnt2-1] = 3;
					}
				}else{
					if(around[1] && around[7]){
						instype[pcnt2-1] = 1;
						x4[pcnt2] = i;
						y4[pcnt2++] = j;
						instype[pcnt2-1] = 0;
					}else{
						instype[pcnt2-1] = 1;
					}

				}
			}else{
				if(around[5]){
					if(around[1] && around[7]){
						instype[pcnt2-1] = 0;
						x4[pcnt2] = i;
						y4[pcnt2++] = j;
						instype[pcnt2-1] = 2;
					}else{
						instype[pcnt2-1] = 2;
					}
				}else{
					if(around[6] && !around[7]){
						instype[pcnt2-1] = 2;
					}else if(around[2] && !around[1]){
						instype[pcnt2-1] = 1;
					}else if(around[4]){
						instype[pcnt2-1] = 1;
					}else{
						instype[pcnt2-1] = 0;
					}
				}
			}
			if((( j-1 < 0 )? false : map[i][j-1]) &&(around[2] || around[3] || around[0] || around[7]) && !(oldx == i && oldy == j-1) && !(newx == i && newy == j-1) && (!(((i-1 < 0 || j-1-1 < 0) ? false : map[i-1][j-1-1]) && ((j-1-1 < 0) ? false : map[i][j-1-1]) && ((i+1 >= PaintTool.sizex || j-1-1 < 0) ? false : map[i+1][j-1-1]) &&((i-1 < 0 || j-1 < 0) ? false : map[i-1][j-1]) && ((i+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1][j-1]) && ((i-1 < 0) ? false : map[i-1][j-1+1]) && map[i][j-1+1] && ((i+1 >= PaintTool.sizex) ? false : map[i+1][j-1+1])) )){
				oldx = i;
				oldy = j;
				newx = i;
				newy = j;
				j = j-1;
				pcnt = pcnt2;
				x4[pcnt2] = i;
				y4[pcnt2++] = j;
				if(around[2]){
					ins = 3;
				}else if(around[3]){
					ins = 4;
				}else if(around[0]){
					ins = 7;
				}else if(around[7]){
					ins = 6;
				}
			}else if((( i+1 >= PaintTool.sizex )? false : map[i+1][j]) && (around[1] || around[2] || around[4] || around[5]) && !(oldx == i+1 && oldy == j) && !(newx == i+1 && newy == j) && (!(((j-1 < 0) ? false : map[i+1-1][j-1]) && ((i+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1][j-1]) && ((i+1+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1+1][j-1]) && map[i+1-1][j] && ((i+1+1 >= PaintTool.sizex) ? false : map[i+1+1][j]) && ((j+1 >= PaintTool.sizey) ? false : map[i+1-1][j+1]) && ((i+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1][j+1]) && ((i+1+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1+1][j+1])) )){
				oldx = i;
				oldy = j;
				newx = i;
				newy = j;
				i = i+1;
				pcnt = pcnt2;
				x4[pcnt2] = i;
				y4[pcnt2++] = j;
				if(around[1]){
					ins = 0;
				}else if(around[2]){
					ins = 1;
				}else if(around[4]){
					ins = 5;
				}else if(around[5]){
					ins = 6;
				}
			}else if((( j+1 >= PaintTool.sizey )? false : map[i][j+1]) && (around[3] || around[4] || around[6] || around[7]) && !(oldx == i && oldy == j+1) && !(newx == i && newy == j+1) && (!(((i-1 < 0) ? false : map[i-1][j+1-1]) && map[i][j+1-1] && ((i+1 >= PaintTool.sizex) ? false : map[i+1][j+1-1]) && ((i-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1][j+1]) && ((i+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1][j+1]) && ((i-1 < 0 || j+1+1 >= PaintTool.sizey) ? false : map[i-1][j+1+1]) && ((j+1+1 >= PaintTool.sizey) ? false : map[i][j+1+1]) && ((i+1 >= PaintTool.sizex || j+1+1 >= PaintTool.sizey) ? false : map[i+1][j+1+1])) )){
				oldx = i;
				oldy = j;
				newx = i;
				newy = j;
				j = j+1;
				pcnt = pcnt2;
				x4[pcnt2] = i;
				y4[pcnt2++] = j;
				if(around[3]){
					ins = 2;
				}else if(around[4]){
					ins = 3;
				}else if(around[6]){
					ins = 7;
				}else if(around[7]){
					ins = 0;
				}
			}else if((( i-1 < 0 )? false : map[i-1][j]) && (around[0] || around[1] || around[5] || around[6]) && !(oldx == i-1 && oldy == j) && !(newx == i-1 && newy == j) && (!(((i-1-1 < 0 || j-1 < 0) ? false : map[i-1-1][j-1]) && ((i-1 < 0 || j-1 < 0) ? false : map[i-1][j-1]) && ((j-1 < 0) ? false : map[i-1+1][j-1]) && ((i-1-1 < 0) ? false : map[i-1-1][j]) && map[i-1+1][j] && ((i-1-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1-1][j+1]) && ((i-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1][j+1]) && ((j+1 >= PaintTool.sizey) ? false : map[i-1+1][j+1])) )){
				oldx = i;
				oldy = j;
				newx = i;
				newy = j;
				i = i-1;
				pcnt = pcnt2;
				x4[pcnt2] = i;
				y4[pcnt2++] = j;
				if(around[0]){
					ins = 1;
				}else if(around[1]){
					ins = 2;
				}else if(around[5]){
					ins = 4;
				}else if(around[6]){
					ins = 5;
				}
			}else{
				if(pcnt == 0){
					x4[pcnt2] = i;
					y4[pcnt2++] = j;
					break;
				}
				pcnt--;
				if(x4[pcnt+1]-x4[pcnt] == -1){ //x+
					if(ins == 0 || ins == 1 || ins == 2){
						if(around[4]){
							ins = 5;
						}else if(around[5]){
							ins = 6;
						}else if(around[1]){
							ins = 0;
						}else if(around[2]){
							ins = 1;
						}
					}else{
						if(around[1]){
							ins = 0;
						}else if(around[2]){
							ins = 1;
						}else if(around[4]){
							ins = 5;
						}else if(around[5]){
							ins = 6;
						}
					}
				}else if(x4[pcnt+1]-x4[pcnt] == 1){ //x-
					if(ins == 0 || ins == 1 || ins == 2){
						if(around[5]){
							ins = 4;
						}else if(around[6]){
							ins = 5;
						}else if(around[0]){
							ins = 1;
						}else if(around[1]){
							ins = 2;
						}
					}else{
						if(around[0]){
							ins = 1;
						}else if(around[1]){
							ins = 2;
						}else if(around[5]){
							ins = 4;
						}else if(around[6]){
							ins = 5;
						}
					}
				}else if(y4[pcnt+1]-y4[pcnt] == -1){ //y+
					if(ins == 0 || ins == 6 || ins == 7){
						if(around[3]){
							ins = 2;
						}else if(around[4]){
							ins = 3;
						}else if(around[6]){
							ins = 7;
						}else if(around[7]){
							ins = 0;
						}
					}else{
						if(around[6]){
							ins = 7;
						}else if(around[7]){
							ins = 0;
						}else if(around[3]){
							ins = 2;
						}else if(around[4]){
							ins = 3;
						}
					}

				}else if(y4[pcnt+1]-y4[pcnt] == 1){ //y-
					if(ins == 0 || ins == 6 || ins == 7){
						if(around[2]){
							ins = 3;
						}else if(around[3]){
							ins = 4;
						}else if(around[0]){
							ins = 7;
						}else if(around[7]){
							ins = 6;
						}
					}else{
						if(around[0]){
							ins = 7;
						}else if(around[7]){
							ins = 6;
						}else if(around[2]){
							ins = 3;
						}else if(around[3]){
							ins = 4;
						}
					}
				}
				i = x4[pcnt];
				j = y4[pcnt];
				x4[pcnt2] = i;
				y4[pcnt2++] = j;
				if(pcnt-1>=0){
					newx = x4[pcnt-1];
					newy = y4[pcnt-1];
				}
				oldx = x4[pcnt+1];
				oldy = y4[pcnt+1];
			}
		}
		for(int t= 0;t<pcnt2;t++){
			if(instype[t] == 1){
				x4[t]++;
			}else if(instype[t] == 2){
				y4[t]++;
			}else if(instype[t] == 3){
				x4[t]++;
				y4[t]++;
			}
		}
		fillshap = new Area(new Polygon(x4,y4,pcnt2));
		if(SelectOptionFrame.s2.getValue() <= 1000){
			Area examfillshap = new Area(new Polygon(x4,y4,pcnt2));
			int boundminx, boundminy, boundmaxx, boundmaxy, examstartx, examstarty;
			boundminx = fillshap.getBounds().x+1;
			boundminy = fillshap.getBounds().y+1;
			boundmaxx = (int)fillshap.getBounds().getMaxX()-1;
			boundmaxy = (int)fillshap.getBounds().getMaxY()-1;
			examstartx = boundminx;
			examstarty = boundminy;
			boolean exitsw = false;
			System.out.println("boundminx;" + boundminx + "\nboundminy;"+ boundminy + "\nboundmaxx;" +boundmaxx + "\nboundmaxy;" +boundmaxy);
			if(boundminx < boundmaxx && boundminy < boundmaxy){
				breaks = false;
				int oldexamstartx = 0, oldexamstarty = 0;
				for(i = examstartx;i < boundmaxx;i++){//内枠Polygon初期位置検索
					for(j = boundminy + 1;j < boundmaxy; j++){
						if(!(map[i][j])){
							if(examfillshap.contains(i, j)){
								int t;
								for(t = j;t < boundmaxy;t++){
									if(map[i][t])break;
								}
								examstartx = i;
								if(t == boundmaxy){
									if(examstartx < boundmaxx - 1){
										examstartx++;
										for(t = boundminy + 1;t < boundmaxy;t++){
											if(map[i+1][t])break;
										}
									}
								}
								examstarty = t;
								System.out.println("検索/end" + " x:"+ i + " y:" + j);
								breaks = true;
								break;
							}else{
								int t;
								for(t = j;t < boundmaxy;t++){
									if(map[i][t]){
										j = t;
										break;
									}
								}
								if(t == boundmaxy && i < boundmaxx - 1){
									i++;
									j = boundminy;
								}
							}
						}
					}
					if(breaks == true)break;
				}
				breaks = false;
				long time = System.currentTimeMillis();
				while(!(i >= boundmaxx && j >= boundmaxy)){
					j--;
					pcnt2 = 0;
					pcnt = 0;
					oldx = i;
					oldy = j;
					newx = i;
					newy = j;
					x4[pcnt2] = i;
					y4[pcnt2++] = j;
					ins = 5;
					while(pcnt2 <= 2 || !(oldx == x4[0] && oldy == y4[0] && i == x4[1] && j == y4[1])){//外枠Polygon作成
						around = new boolean[8];
						for(int q = ins; q < ins;q = (q+1) % 8){
							if(!((i+dx[q] < 0 || i+dx[q] >= PaintTool.sizex || j+dy[q] < 0 || j+dy[q] >= PaintTool.sizey)?false:map[i+dx[q]][j+dy[q]])){
								around[q] = true;
							}else{
								if(q%2 == 1) break;
							}
						}
						for(int q = ins;q != (ins+1) % 8;q = (q+7) % 8){
							if(!((i+dx[q] < 0 || i+dx[q] >= PaintTool.sizex || j+dy[q] < 0 || j+dy[q] >= PaintTool.sizey)?false:map[i+dx[q]][j+dy[q]])){
								around[q] = true;
							}else{
								if(q%2 == 1) break;
							}
						}
						if(pcnt2 >= 1 && (pcnt2 % 10000) == 0){
							for(int t = 1;t < pcnt2 - 1 ;t++){
								for(int r = t + 1;r < pcnt2 ;r++){
									if(x4[t] == x4[r] && y4[t] == y4[r] && x4[t-1] == x4[r-1] && y4[t-1] == y4[r-1]){
										j++;
										i = x4[t];
										j = y4[t];
										examstartx = i;
										examstarty = j;
										System.out.println("loop error");
										breaks =true;
										break;
									}
								}
								if(breaks)break;
							}
							if(breaks)break;
						}
						if(around[3]){
							if(around[5]){
								if(around[1]){
									instype[pcnt2-1] = 3;
									x4[pcnt2] = i;
									y4[pcnt2++] = j;
									instype[pcnt2-1] = 1;
								}else if(around[7]){
									instype[pcnt2-1] = 2;
									x4[pcnt2] = i;
									y4[pcnt2++] = j;
									instype[pcnt2-1] = 3;
								}else{
									instype[pcnt2-1] = 3;
								}
							}else{
								if(around[1] && around[7]){
									instype[pcnt2-1] = 1;
									x4[pcnt2] = i;
									y4[pcnt2++] = j;
									instype[pcnt2-1] = 0;
								}else{
									instype[pcnt2-1] = 1;
								}
							}
						}else{
							if(around[5]){
								if(around[1] && around[7]){
									instype[pcnt2-1] = 0;
									x4[pcnt2] = i;
									y4[pcnt2++] = j;
									instype[pcnt2-1] = 2;
								}else{
									instype[pcnt2-1] = 2;
								}
							}else{
								if(around[6] && !around[7]){
									instype[pcnt2-1] = 2;
								}else if(around[2] && !around[1]){
									instype[pcnt2-1] = 1;
								}else if(around[4]){
									instype[pcnt2-1] = 1;
								}else{
									instype[pcnt2-1] = 0;
								}
							}
						}
						if((( j-1 < 0 )? false : map[i][j-1]) &&(around[2] || around[3] || around[0] || around[7]) && !(oldx == i && oldy == j-1) && !(newx == i && newy == j-1) && (!(((i-1 < 0 || j-1-1 < 0) ? false : map[i-1][j-1-1]) && ((j-1-1 < 0) ? false : map[i][j-1-1]) && ((i+1 >= PaintTool.sizex || j-1-1 < 0) ? false : map[i+1][j-1-1]) &&((i-1 < 0 || j-1 < 0) ? false : map[i-1][j-1]) && ((i+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1][j-1]) && ((i-1 < 0) ? false : map[i-1][j-1+1]) && map[i][j-1+1] && ((i+1 >= PaintTool.sizex) ? false : map[i+1][j-1+1])) )){
							oldx = i;
							oldy = j;
							newx = i;
							newy = j;
							j = j-1;
							pcnt = pcnt2;
							x4[pcnt2] = i;
							y4[pcnt2++] = j;
							if(around[2]){
								ins = 3;
							}else if(around[3]){
								ins = 4;
							}else if(around[0]){
								ins = 7;
							}else if(around[7]){
								ins = 6;
							}
						}else if((( i+1 >= PaintTool.sizex )? false : map[i+1][j]) && (around[1] || around[2] || around[4] || around[5]) && !(oldx == i+1 && oldy == j) && !(newx == i+1 && newy == j) && (!(((j-1 < 0) ? false : map[i+1-1][j-1]) && ((i+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1][j-1]) && ((i+1+1 >= PaintTool.sizex || j-1 < 0) ? false : map[i+1+1][j-1]) && map[i+1-1][j] && ((i+1+1 >= PaintTool.sizex) ? false : map[i+1+1][j]) && ((j+1 >= PaintTool.sizey) ? false : map[i+1-1][j+1]) && ((i+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1][j+1]) && ((i+1+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1+1][j+1])) )){
							oldx = i;
							oldy = j;
							newx = i;
							newy = j;
							i = i+1;
							pcnt = pcnt2;
							x4[pcnt2] = i;
							y4[pcnt2++] = j;
							if(around[1]){
								ins = 0;
							}else if(around[2]){
								ins = 1;
							}else if(around[4]){
								ins = 5;
							}else if(around[5]){
								ins = 6;
							}
						}else if((( j+1 >= PaintTool.sizey )? false : map[i][j+1]) && (around[3] || around[4] || around[6] || around[7]) && !(oldx == i && oldy == j+1) && !(newx == i && newy == j+1) && (!(((i-1 < 0) ? false : map[i-1][j+1-1]) && map[i][j+1-1] && ((i+1 >= PaintTool.sizex) ? false : map[i+1][j+1-1]) && ((i-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1][j+1]) && ((i+1 >= PaintTool.sizex || j+1 >= PaintTool.sizey) ? false : map[i+1][j+1]) && ((i-1 < 0 || j+1+1 >= PaintTool.sizey) ? false : map[i-1][j+1+1]) && ((j+1+1 >= PaintTool.sizey) ? false : map[i][j+1+1]) && ((i+1 >= PaintTool.sizex || j+1+1 >= PaintTool.sizey) ? false : map[i+1][j+1+1])) )){
							oldx = i;
							oldy = j;
							newx = i;
							newy = j;
							j = j+1;
							pcnt = pcnt2;
							x4[pcnt2] = i;
							y4[pcnt2++] = j;
							if(around[3]){
								ins = 2;
							}else if(around[4]){
								ins = 3;
							}else if(around[6]){
								ins = 7;
							}else if(around[7]){
								ins = 0;
							}
						}else if((( i-1 < 0 )? false : map[i-1][j]) && (around[0] || around[1] || around[5] || around[6]) && !(oldx == i-1 && oldy == j) && !(newx == i-1 && newy == j) && (!(((i-1-1 < 0 || j-1 < 0) ? false : map[i-1-1][j-1]) && ((i-1 < 0 || j-1 < 0) ? false : map[i-1][j-1]) && ((j-1 < 0) ? false : map[i-1+1][j-1]) && ((i-1-1 < 0) ? false : map[i-1-1][j]) && map[i-1+1][j] && ((i-1-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1-1][j+1]) && ((i-1 < 0 || j+1 >= PaintTool.sizey) ? false : map[i-1][j+1]) && ((j+1 >= PaintTool.sizey) ? false : map[i-1+1][j+1])) )){
							oldx = i;
							oldy = j;
							newx = i;
							newy = j;
							i = i-1;
							pcnt = pcnt2;
							x4[pcnt2] = i;
							y4[pcnt2++] = j;
							if(around[0]){
								ins = 1;
							}else if(around[1]){
								ins = 2;
							}else if(around[5]){
								ins = 4;
							}else if(around[6]){
								ins = 5;
							}
						}else{
							if(pcnt == 0){
								x4[pcnt2] = i;
								y4[pcnt2++] = j;
								break;
							}
							pcnt--;
							if(x4[pcnt+1]-x4[pcnt] == -1){ //x+
								if(ins == 0 || ins == 1 || ins == 2){
									if(around[4]){
										ins = 5;
									}else if(around[5]){
										ins = 6;
									}else if(around[1]){
										ins = 0;
									}else if(around[2]){
										ins = 1;
									}
								}else{
									if(around[1]){
										ins = 0;
									}else if(around[2]){
										ins = 1;
									}else if(around[4]){
										ins = 5;
									}else if(around[5]){
										ins = 6;
									}
								}
							}else if(x4[pcnt+1]-x4[pcnt] == 1){ //x-
								if(ins == 0 || ins == 1 || ins == 2){
									if(around[5]){
										ins = 4;
									}else if(around[6]){
										ins = 5;
									}else if(around[0]){
										ins = 1;
									}else if(around[1]){
										ins = 2;
									}
								}else{
									if(around[0]){
										ins = 1;
									}else if(around[1]){
										ins = 2;
									}else if(around[5]){
										ins = 4;
									}else if(around[6]){
										ins = 5;
									}
								}
							}else if(y4[pcnt+1]-y4[pcnt] == -1){ //y+
								if(ins == 0 || ins == 6 || ins == 7){
									if(around[3]){
										ins = 2;
									}else if(around[4]){
										ins = 3;
									}else if(around[6]){
										ins = 7;
									}else if(around[7]){
										ins = 0;
									}
								}else{
									if(around[6]){
										ins = 7;
									}else if(around[7]){
										ins = 0;
									}else if(around[3]){
										ins = 2;
									}else if(around[4]){
										ins = 3;
									}
								}
							}else if(y4[pcnt+1]-y4[pcnt] == 1){ //y-
								if(ins == 0 || ins == 6 || ins == 7){
									if(around[2]){
										ins = 3;
									}else if(around[3]){
										ins = 4;
									}else if(around[0]){
										ins = 7;
									}else if(around[7]){
										ins = 6;
									}
								}else{
									if(around[0]){
										ins = 7;
									}else if(around[7]){
										ins = 6;
									}else if(around[2]){
										ins = 3;
									}else if(around[3]){
										ins = 4;
									}
								}
							}
							i = x4[pcnt];
							j = y4[pcnt];
							x4[pcnt2] = i;
							y4[pcnt2++] = j;
							if(pcnt-1>=0){
								newx = x4[pcnt-1];
								newy = y4[pcnt-1];
							}
							oldx = x4[pcnt+1];
							oldy = y4[pcnt+1];
						}
					}
					if(!breaks){
						for(int t= 0;t<pcnt2;t++){//調整
							if(instype[t] == 1){
								x4[t]++;
							}else if(instype[t] == 2){
								y4[t]++;
							}else if(instype[t] == 3){
								x4[t]++;
								y4[t]++;
							}
						}
						if(SelectOptionFrame.s2.getValue() == 0){
							examfillshap.subtract(new Area(new Polygon(x4,y4,pcnt2)));
						}else{
							Area are3 = new Area(new Polygon(x4,y4,pcnt2));
							if(are3.getBounds().height > SelectOptionFrame.s2.getValue() || are3.getBounds().width > SelectOptionFrame.s2.getValue() ){
								fillshap.subtract(new Area(new Polygon(x4,y4,pcnt2)));
							}
							examfillshap.subtract(new Area(new Polygon(x4,y4,pcnt2)));
						}
					}
					if(System.currentTimeMillis() - time > 10000){
						exitsw = true; // 時間がかかると省略
						System.out.println("cost too many time");
					}
					//内枠Polygon初期位置検索
					if(!breaks && !exitsw){
						for(j = examstarty + 1;j < boundmaxy; j++){
							if(!(map[examstartx][j])){
								if(examfillshap.contains(examstartx, j)){
									if(oldexamstartx == i && oldexamstarty == j){
										System.out.println("don't move error");
										break;
									}else{
										int t;
										for(t = j;t < boundmaxy;t++){
											if(map[examstartx][t])break;
										}
										if(t == boundmaxy){
											if(examstartx < boundmaxx - 1){
												examstartx++;
												for(t = boundminy + 1;t < boundmaxy;t++){
													if(map[examstartx][t])break;
												}
											}else{
												exitsw = true;
											}
										}
										examstarty = t;
										oldexamstartx = i;
										oldexamstarty = j;
										System.out.println("!検索/end" + " x:"+ examstartx + " y:" + j);
										breaks = true;
										break;
									}
								}else{
									int t;
									for(t = j;t < boundmaxy;t++){
										if(map[examstartx][t]){
											j = t;
											break;
										}
									}
									if(t == boundmaxy){
										break;
									}
								}
							}
						}
					}
					if(!breaks && !exitsw){
						for(i = examstartx+1;i < boundmaxx;i++){
							for(j = boundminy;j < boundmaxy; j++){
								if(!(map[i][j])){
									if(examfillshap.contains(i, j)){
										if(oldexamstartx == i && oldexamstarty == j){
											System.out.println("don't move error");
											i++;
										}else{
											int t;
											for(t = j;t < boundmaxy;t++){
												if(map[i][t])break;
											}
											examstartx = i;
											if(t == boundmaxy){
												if(examstartx < boundmaxx - 1){
													examstartx++;
													for(t = boundminy + 1;t < boundmaxy;t++){
														if(map[examstartx][t])break;
													}
												} else{
													exitsw = true;
												}
											}
											examstarty = t;
											oldexamstartx = i;
											oldexamstarty = j;
											System.out.println("検索/end" + " x:"+ i + " y:" + j);
											breaks = true;
											break;
										}
									}else{
										int t;
										for(t = j;t < boundmaxy;t++){
											if(map[i][t]){
												j = t;
												break;
											}
										}
										if(t == boundmaxy){
											if(i < boundmaxx - 1){
												i++;
												j = boundminy;
											}else{
												exitsw = true;
												break;
											}
										}
									}
								}
							}
							if(breaks || exitsw)break;
						}
					}
					breaks = false;
					if(exitsw) break;
				}
			}
			if(SelectOptionFrame.s2.getValue() == 0){
				fillshap = examfillshap;
			}
		}
	}
	
	/* ★★コンストラクタ★★ */
	DrawPanel() {
		super();
		setBackground(new Color(238, 238, 238));
		x1 = -10;
		y1 = -10;
		x2 = -10;
		y2 = -10;
		x3 = new int[100];
		y3 = new int[100];
		x4 = new int[1000000];
		y4 = new int[1000000];
		curvetrans = new float[100];
		curvesize = new int[100];
		curveside = new int[100];
		curvecolor = new Color[100];
		curveispattern = new boolean[100];
		curvepattern = new float[100][4];
		centerx = -1;
		centery = -1;
		img = new BufferedImage[PaintTool.HistoryNUM];
		shaps = new Shape[PaintTool.HistoryNUM];
		setchange = new boolean[PaintTool.HistoryNUM];
		sets = new boolean[PaintTool.HistoryNUM];
		sets[0] = false;
		setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
		img[0] = new BufferedImage(PaintTool.sizex, PaintTool.sizey,
				BufferedImage.TYPE_INT_RGB);
		g2 = img[0].createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, PaintTool.sizex, PaintTool.sizey);
		timer1 = new Timer();
		timer2 = new Timer();
		timer3 = new Timer();
		Random rnd = new Random();
		tmpname = "paint_tmp" + rnd.nextInt(10000)+"_";
	}

	/* ★★描写★★ */
	public void paintComponent(Graphics g) {
		g3 = (Graphics2D) g;
		super.paintComponent(g);
		super.paintComponent(g3);
		g3.setColor(Color.black);
		g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
		g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
		g3.scale(PaintTool.scale, PaintTool.scale);
		if(PaintTool.getType() == 20){
			if (PaintTool.getComand() != 0 && PaintTool.getComand() != 15) {
				pcnt2 = 0;
				curvex = -1000;
				curvey = -1000;
				curvex2 = -1000;
				curvey2 = -1000;
				if(pcnt2 != 0){
					ToolPanel.rb2.setEnabled(true);
					ToolPanel.rb6.setEnabled(true);
				} else{
					ToolPanel.rb2.setEnabled(false);
					ToolPanel.rb6.setEnabled(false);
					ToolPanel.rb1.setSelected(true);
				}
				if(curvex != -1000){
					ToolPanel.rb7.setEnabled(true);
				} else{
					ToolPanel.rb7.setEnabled(false);
					ToolPanel.rb6.setSelected(true);
				}
			}
		}
		if (PaintTool.getComand() == 2) { // Undo
			pcnt = 0;
			pcnt2 = 0;
			UndoLeave();
			if (img[(imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM] != null && imgnum != startimg) {
				super.paintComponent(g);
				super.paintComponent(g3);
				g3.drawImage(img[(imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM], 0, 0, this);
				imgnum = (imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM;
				PaintTool.sizex = img[imgnum].getWidth();
				PaintTool.sizey = img[imgnum].getHeight();
				PaintTool.p3.setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
				g3.setColor(new Color(238, 238, 238));
				g3.fillRect(PaintTool.sizex, 0, 2000, 2000);
				g3.fillRect(0, PaintTool.sizey, 2000, 2000);
				g3.setColor(Color.black);
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
				if(sets[imgnum]){//SelectTool状態設定
					set = true;
					timer1 = new Timer();
					timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
						clockswitch = !clockswitch;
						long t2;
						t2 = System.currentTimeMillis();
						if(t2 - t1 < 850){
							if(!press){
								clock = true;
								PaintTool.p3.revalidate();
								rewrite();
							}
						}
						t1 = t2;
					}}, 1, 750);
					shap = new Area(shaps[imgnum]);
					if(HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗") || HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗＆変形")){
						dontfill = false;
					}else if(setchange[imgnum] == true){
						dontfill = true;
					}
					for(int i = imgnum ;i != startimg;i = (i + (PaintTool.HistoryNUM - 1)) %PaintTool.HistoryNUM){
						try {
							File file = new File(tmpname +"in" + i + ".bmp");
							imgin = ImageIO.read(file);
							break;
						} catch (Exception d) {
						}
					}
				} else{
					set = false;
					timer1.cancel();
					dontfill = false;
					PaintTool.spin = 0;
					PaintTool.ratiox = 100;
					PaintTool.ratioy = 100;
					PaintTool.shx = 0;
					PaintTool.shy = 0;
				}
				HistoryFrame.ls.setSelectedIndex((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
				HistoryFrame.ls.ensureIndexIsVisible((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
			} else {
				g3.drawImage(img[imgnum], 0, 0, this);
			}
			ComandPanel.b3.setEnabled(!(imgnum == startimg));
			PaintTool.undom.setEnabled(!(imgnum == startimg));
			ComandPanel.b4.setEnabled(true);
			PaintTool.redom.setEnabled(true);
			ToolPanel.b8.setEnabled(false);
			ToolPanel.b9.setEnabled(false);
			System.out.println("imgnum "+imgnum+"\t"+"startimg "+startimg+"\t"+"endimg "+endimg+"\n\t\t"+"start2img "+start2img+"\t"+"end2img "+end2img);
		} else if (PaintTool.getComand() == 3) { // ReDo
			pcnt = 0;
			pcnt2 = 0;
			RedoLeave();
			if (img[imgnum] != null && imgnum != endimg) {
				super.paintComponent(g);
				super.paintComponent(g3);
				g3.drawImage(img[(imgnum + 1) % PaintTool.HistoryNUM], 0, 0, this);
				imgnum = (imgnum + 1) % PaintTool.HistoryNUM;
				PaintTool.sizex = img[imgnum].getWidth();
				PaintTool.sizey = img[imgnum].getHeight();
				PaintTool.p3.setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
				if(sets[imgnum]){//SelectTool状態設定
					set = true;
					timer1 = new Timer();
					timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
						clockswitch = !clockswitch;
						long t2;
						t2 = System.currentTimeMillis();
						if(t2 - t1 < 850){
							if(!press){
								clock = true;
								PaintTool.p3.revalidate();
								rewrite();
							}
						}
						t1 = t2;
					}}, 1, 750);
					shap = new Area(shaps[imgnum]);
					if(HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗") || HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗＆変形")){
						dontfill = false;
					}else if(setchange[imgnum] == true){
						dontfill = true;
					}
					for(int i = imgnum ;i != startimg;i = (i + (PaintTool.HistoryNUM - 1)) %PaintTool.HistoryNUM){
						try {
							File file = new File(tmpname + "in" + i + ".bmp");
							imgin = ImageIO.read(file);
							break;
						} catch (Exception d) {
						}
					}
				} else{
					set = false;
					timer1.cancel();
					dontfill = false;
					PaintTool.spin = 0;
					PaintTool.ratiox = 100;
					PaintTool.ratioy = 100;
					PaintTool.shx = 0;
					PaintTool.shy = 0;
				}
				HistoryFrame.ls.setSelectedIndex((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
				HistoryFrame.ls.ensureIndexIsVisible((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
			} else {
				g3.drawImage(img[imgnum], 0, 0, this);
			}
			ComandPanel.b3.setEnabled(true);
			PaintTool.undom.setEnabled(true);
			ComandPanel.b4.setEnabled(!(imgnum == endimg));
			PaintTool.redom.setEnabled(!(imgnum == endimg));
			ToolPanel.b8.setEnabled(false);
			ToolPanel.b9.setEnabled(false);
			System.out.println("imgnum "+imgnum+"\t"+"startimg "+startimg+"\t"+"endimg "+endimg+"\n\t\t"+"start2img "+start2img+"\t"+"end2img "+end2img);
		}else if (PaintTool.getComand() == 20) { // HistoryMove
			pcnt = 0;
			pcnt2 = 0;
			if(PaintTool.moveindex > 0){//Redo
				for(;PaintTool.moveindex != 0;PaintTool.moveindex--){
					RedoLeave();
					if (img[imgnum] != null && imgnum != endimg) {
						imgnum = (imgnum + 1) % PaintTool.HistoryNUM;
						HistoryFrame.ls.setSelectedIndex((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
						HistoryFrame.ls.ensureIndexIsVisible((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
					}
				}
				super.paintComponent(g);
				super.paintComponent(g3);
				g3.drawImage(img[imgnum], 0, 0, this);
				PaintTool.sizex = img[imgnum].getWidth();
				PaintTool.sizey = img[imgnum].getHeight();
				PaintTool.p3.setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
				if(sets[imgnum]){//SelectTool状態設定
					set = true;
					timer1 = new Timer();
					timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
						clockswitch = !clockswitch;
						long t2;
						t2 = System.currentTimeMillis();
						if(t2 - t1 < 850){
							if(!press){
								clock = true;
								PaintTool.p3.revalidate();
								rewrite();
							}
						}
						t1 = t2;
					}}, 1, 750);
					shap = new Area(shaps[imgnum]);
					if(HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗") || HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗＆変形")){
						dontfill = false;
					}else if(setchange[imgnum] == true){
						dontfill = true;
					}
					for(int i = imgnum ;i != startimg;i = (i + (PaintTool.HistoryNUM - 1)) %PaintTool.HistoryNUM){
						try {
							File file = new File(tmpname + "in" + i + ".bmp");
							imgin = ImageIO.read(file);
							break;
						} catch (Exception d) {
						}
					}
				} else{
					set = false;
					timer1.cancel();
					dontfill = false;
					PaintTool.spin = 0;
					PaintTool.ratiox = 100;
					PaintTool.ratioy = 100;
					PaintTool.shx = 0;
					PaintTool.shy = 0;
				}
			}else{//Undo
				for(;PaintTool.moveindex != 0;PaintTool.moveindex++){
					UndoLeave();
					if (img[(imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM] != null && imgnum != startimg) {
						imgnum = (imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM;
						HistoryFrame.ls.setSelectedIndex((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
						HistoryFrame.ls.ensureIndexIsVisible((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
					}
				}
				super.paintComponent(g);
				super.paintComponent(g3);
				g3.drawImage(img[imgnum], 0, 0, this);
				PaintTool.sizex = img[imgnum].getWidth();
				PaintTool.sizey = img[imgnum].getHeight();
				PaintTool.p3.setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
				g3.setColor(new Color(238, 238, 238));
				g3.fillRect(PaintTool.sizex, 0, 2000, 2000);
				g3.fillRect(0, PaintTool.sizey, 2000, 2000);
				g3.setColor(Color.black);
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
				if(sets[imgnum]){//SelectTool状態設定
					set = true;
					timer1 = new Timer();
					timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
						clockswitch = !clockswitch;
						long t2;
						t2 = System.currentTimeMillis();
						if(t2 - t1 < 850){
							if(!press){
								clock = true;
								PaintTool.p3.revalidate();
								rewrite();
							}
						}
						t1 = t2;
					}}, 1, 750);
					shap = new Area(shaps[imgnum]);
					if(HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗") || HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM].equals("選択範囲白塗＆変形")){
						dontfill = false;
					}else if(setchange[imgnum] == true){
						dontfill = true;
					}
					for(int i = imgnum ;i != startimg;i = (i + (PaintTool.HistoryNUM - 1)) %PaintTool.HistoryNUM){
						try {
							File file = new File(tmpname + "in" + i + ".bmp");
							imgin = ImageIO.read(file);
							break;
						} catch (Exception d) {
						}
					}
				} else{
					set = false;
					timer1.cancel();
					dontfill = false;
					PaintTool.spin = 0;
					PaintTool.ratiox = 100;
					PaintTool.ratioy = 100;
					PaintTool.shx = 0;
					PaintTool.shy = 0;
				}
			}
			ComandPanel.b3.setEnabled(!(imgnum == startimg));
			PaintTool.undom.setEnabled(!(imgnum == startimg));
			ComandPanel.b4.setEnabled(!(imgnum == endimg));
			PaintTool.redom.setEnabled(!(imgnum == endimg));
			ToolPanel.b8.setEnabled(false);
			ToolPanel.b9.setEnabled(false);
			System.out.println("imgnum "+imgnum+"\t"+"startimg "+startimg+"\t"+"endimg "+endimg+"\n\t\t"+"start2img "+start2img+"\t"+"end2img "+end2img);
		} else if (PaintTool.getComand() == 4) { // Save
			PaintTool.resetComand();
			g3.drawImage(img[imgnum], 0, 0, this);
		} else if (PaintTool.getComand() == 5) { // Repaint img
			g3.drawImage(img[imgnum], 0, 0, this);
		} else {
			if (capt) { // img移行処理
				if(set && (PaintTool.getType() != 12 || PaintTool.getComand() == 13 || listname.equals("選択範囲白塗") || listname.equals("選択範囲白塗＆変形") || listname.equals("選択範囲変形"))){
					if(PaintTool.getType() != 12)capture();
					try {
						File file = new File(tmpname + "in" + imgnum + ".bmp");
						ImageIO.write(imgin, "bmp", file);
					} catch (Exception d) {
					}
				}
				int i;
				if(imgnum != end2img){//前設定掃除
					File file;
					for(i = (imgnum + 1)%PaintTool.HistoryNUM ; ((imgnum + 1)%PaintTool.HistoryNUM <= end2img && i <= end2img) || ((imgnum + 1)%PaintTool.HistoryNUM > end2img && (i <= end2img || i >=(imgnum+1)%PaintTool.HistoryNUM)); i = (i + 1)%PaintTool.HistoryNUM){
						if(img[i] != null){
							img[i] = null;
						}
						file = new File(tmpname + "in" + i + ".bmp");
						file.delete();
						sets[i] = false;
						setchange[i] = false;
						HistoryFrame.removeForeLs(1);
					}
				}
				long heepsum;
				heepsum = 0;
				for(i = 0;i < PaintTool.HistoryNUM ;i++){//すべてのimgサイズ計算
					if(i != (imgnum + 1) % PaintTool.HistoryNUM){
						if(img[i] != null){
							heepsum += img[i].getHeight()*img[i].getWidth();
						}
					}
				}
				heepsum += PaintTool.sizex*PaintTool.sizey;//次のimg用
				heepsum += PaintTool.sizex*PaintTool.sizey;//imgtemp用
				while(heepsum >= PaintTool.Heepsize){
					if(img[start2img] != null){
						heepsum -= img[start2img].getHeight()*img[start2img].getWidth();
						try {
							File file = new File(tmpname + start2img + ".bmp");
							ImageIO.write(img[start2img], "bmp", file);
						} catch (Exception d) {
						}
						img[start2img] = null;//領域開放
					}
					start2img = ++start2img % PaintTool.HistoryNUM;
				}
				if(PaintTool.getComand() != 8){
					img[(imgnum + 1) % PaintTool.HistoryNUM] = new BufferedImage(PaintTool.sizex,
							PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
					Graphics2D g4 = img[(imgnum + 1) % PaintTool.HistoryNUM].createGraphics();
					g4.drawImage(img[imgnum],0,0,this);
				}
				imgnum = ++imgnum % PaintTool.HistoryNUM;
				endimg = imgnum;
				end2img = imgnum;
				if (endimg == startimg) {
					if (startimg == start2img) {
						start2img = ++start2img % PaintTool.HistoryNUM;
					}
					startimg = ++startimg % PaintTool.HistoryNUM;
					HistoryFrame.removeBackLs(1);
				}
				HistoryFrame.listname[(imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM] = listname;
				HistoryFrame.ls.ensureIndexIsVisible((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
				HistoryFrame.ls.setSelectedIndex((imgnum - startimg + PaintTool.HistoryNUM)%PaintTool.HistoryNUM);
				PaintTool.historyrewrite();
				sets[imgnum] = set;
				setchange[imgnum] = false;
				File file = new File(tmpname + "in" + imgnum + ".bmp");
				file.delete();
				if(listname.equals("選択範囲作成") || listname.equals("選択範囲変更") || listname.equals("選択範囲反転")){
					setchange[imgnum] = true;
					listname = "";
					try {
						ImageIO.write(imgin, "bmp", file);
					} catch (Exception d) {
					}
				}
				ComandPanel.b3.setEnabled(true);
				PaintTool.undom.setEnabled(true);
				ComandPanel.b4.setEnabled(false);
				PaintTool.redom.setEnabled(false);
				System.out.println("imgnum "+imgnum +"\t"+"startimg "+startimg+"\t"+"endimg "+endimg+"\n\t\t"+"start2img "+start2img+"\t"+"end2img "+end2img);
			}
			capt = false;
			if(PaintTool.getComand() != 8)g2 = img[imgnum].createGraphics();
			Area maskare = new Area();
			Area maskare2= new Area();
			if(set == true){ //マスキング
				maskare = new Area(shap);
				AffineTransform tran = new AffineTransform();
				tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
				maskare.transform(tran);
				if((SelectOptionFrame.rb3.isSelected())){
					g2.clip(maskare);
				} else if((SelectOptionFrame.rb4.isSelected())){
					maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
					maskare2.subtract(maskare);
					g2.clip(maskare2);
				}
			}
			
			g2.setColor(PaintTool.getColor());// 色設定
			setColor(g2);
			if (debug || clock) {
				g.setColor(PaintTool.getColor());
				setColor(g3);
			}
			if (PaintTool.getComand() == 1) { // 新規作成
				g2.setClip(null);
				super.paintComponent(g2);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g2.setColor(Color.white);
				g2.fillRect(0, 0, PaintTool.sizex, PaintTool.sizey);
				set = false;
				timer1.cancel();
				dontfill = false;
				PaintTool.spin = 0;
				PaintTool.ratiox = 100;
				PaintTool.ratioy = 100;
				PaintTool.shx = 0;
				PaintTool.shy = 0;
				if(PaintTool.getType() == 20){
					pcnt2 = 0;
					curvex = -1000;
					curvey = -1000;
					curvex2 = -1000;
					curvey2 = -1000;
					if(pcnt2 != 0){
						ToolPanel.rb2.setEnabled(true);
						ToolPanel.rb6.setEnabled(true);
					} else{
						ToolPanel.rb2.setEnabled(false);
						ToolPanel.rb6.setEnabled(false);
						ToolPanel.rb1.setSelected(true);
					}
					if(curvex != -1000){
						ToolPanel.rb7.setEnabled(true);
					} else{
						ToolPanel.rb7.setEnabled(false);
						ToolPanel.rb6.setSelected(true);
					}
				}
			} else if (PaintTool.getComand() == 6) { // ChangeSize
				g2.setClip(null);
				g2.setColor(Color.white);
				g2.fillRect(0, 0, PaintTool.sizex, PaintTool.sizey);
				Graphics2D g4 = img[imgnum].createGraphics();
				g4.drawImage(img[(imgnum + (PaintTool.HistoryNUM - 1)) % PaintTool.HistoryNUM],0,0,this);
			} else if (PaintTool.getComand() == 7) { // Open前
			} else if (PaintTool.getComand() == 8) { // Open後
				set = false;
				timer1.cancel();
				dontfill = false;
				PaintTool.spin = 0;
				PaintTool.ratiox = 100;
				PaintTool.ratioy = 100;
				PaintTool.shx = 0;
				PaintTool.shy = 0;
				BufferedImage imgtmp = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
				try{
					imgtmp = ImageIO.read(file);
				}catch(Exception e){}
				super.paintComponent(g);
				super.paintComponent(g3);
				PaintTool.sizex = imgtmp.getWidth();
				PaintTool.sizey = imgtmp.getHeight();
				PaintTool.p3.setPreferredSize(new Dimension(PaintTool.sizex, PaintTool.sizey));
				PaintTool.p3.revalidate();
				img[imgnum] = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
				Graphics g4 = img[imgnum].createGraphics();
				g4.drawImage(imgtmp,0,0,this);
				g3.setColor(new Color(238, 238, 238));
				g3.fillRect(PaintTool.sizex, 0, 2000, 2000);
				g3.fillRect(0, PaintTool.sizey, 2000, 2000);
				g3.setColor(Color.black);
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
			} else if (PaintTool.getComand() == 9) { // トリミング
				Area are = new Area(shap);
				AffineTransform tran = new AffineTransform();
				tran.translate(-are.getBounds().getX(), -are.getBounds().getY());
				are.transform(tran);
				g2.setClip(null);
				g2.setColor(Color.white);
				g2.fillRect(0, 0, are.getBounds().width, are.getBounds().height);
				g2.setClip(are);
				g2.translate(-are.getBounds().getX(), -are.getBounds().getY());
				g2.drawImage(imgin,0, 0, this);
				g2.scale(1, 1);
				g2.translate(are.getBounds().getX(), are.getBounds().getY());
				g3.drawImage(img[imgnum], 0, 0, this);
				set = false;
				timer1.cancel();
				sets[imgnum] = false;
				dontfill = false;
				PaintTool.spin = 0;
				PaintTool.ratiox = 100;
				PaintTool.ratioy = 100;
				PaintTool.shx = 0;
				PaintTool.shy = 0;
			} else if (PaintTool.getComand() == 21) { //貼付け
				int tempx = x2, tempy = y2;
				PaintTool.spin = 0;
				PaintTool.ratiox = 100;
				PaintTool.ratioy = 100;
				PaintTool.shx = 0;
				PaintTool.shy = 0;
				BufferedImage strClip = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
				Clipboard clp = PaintTool.main.getToolkit().getSystemClipboard();
				Transferable data = clp.getContents(PaintTool.main);

				if (data != null && data.isDataFlavorSupported(DataFlavor.imageFlavor)){
					try {
						strClip = (BufferedImage)data.getTransferData(DataFlavor.imageFlavor);
					}
					catch(Exception e) {}
				}
				imgin = strClip;
				shap = new Rectangle(tempx, tempy, imgin.getWidth(), imgin.getHeight());
				set = true;
				timer1 = new Timer();
				timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
					clockswitch = !clockswitch;
					long t2;
					t2 = System.currentTimeMillis();
					if(t2 - t1 < 850){
						if(!press){
							clock = true;
							PaintTool.p3.revalidate();
							rewrite();
						}
					}
					t1 = t2;
				}}, 1, 750);
				sets[imgnum] = true;
				setchange[(imgnum+(PaintTool.HistoryNUM - 1))%PaintTool.HistoryNUM] = true;
				setx1 = tempx;
				sety1 = tempy;
				setx2 = imgin.getWidth();
				sety2 = imgin.getHeight();
				dontfill = false;
			} else if (PaintTool.getComand() == 13 && PaintTool.type != 12) { //選択範囲開放
				g2.setClip(null);
				g3.setClip(null);
				g3.drawImage(img[imgnum], 0, 0, this);
				g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
				leaveArea();
			} else if (PaintTool.type == 0) {// 直線
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					setStroke(g3);
					g3.translate(0.5, 0.5);
					g3.drawLine(x1, y1, x2, y2);
					g3.translate(-0.5, -0.5);
				} else {
					if(regular){
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						setStroke(g2);
						g2.drawLine(x1, y1, x2, y2);
					}
				}
			} else if (PaintTool.type == 1) {// 長方形
				if(!clock){
					int temp = x2;
					x2 = pointrotatex(x2,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
					y2 = pointrotatey(temp,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
				}
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					g3.translate(0.5, 0.5);
					g3.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					setStroke(g3);
					if(ToolPanel.rb1.isSelected()){
						g3.drawRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
								.abs(x1 - x2), Math.abs(y1 - y2));
						for (int i = 0; i <= PaintTool.getsize(); i++) {
							if(ToolPanel.cb2.isSelected()){
								if(PaintTool.colornum < 23){
									g3.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
								}
								
							}
							g3.drawRect((x1 < x2) ? x1 + Math.abs(x1 - x2) * i
									/ PaintTool.getsize2() / 2 : x2
									+ Math.abs(x1 - x2) * i / PaintTool.getsize2()
									/ 2, (y1 < y2) ? y1 + Math.abs(y1 - y2) * i
									/ PaintTool.getsize2() / 2 : y2
									+ Math.abs(y1 - y2) * i / PaintTool.getsize2()
									/ 2, Math.abs(x1 - x2)
									* (PaintTool.getsize2() - i)
									/ PaintTool.getsize2(), Math.abs(y1 - y2)
									* (PaintTool.getsize2() - i)
									/ PaintTool.getsize2());
						}
					}else{
						int x11, y11, w, h;
						w = 2*Math.abs(x1 -x2);
						h = 2*Math.abs(y1 -y2);
						x11 = x1 - w/2;
						y11 = y1 - h/2;
						g3.drawRect(x11, y11, w, h);
						for (int i = 0; i <= PaintTool.getsize(); i++) {
							if(ToolPanel.cb2.isSelected()){
								if(PaintTool.colornum < 23){
									g3.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
								}
							}
							g3.drawRect(x11 + w * i/ PaintTool.getsize2() / 2 
									, y11 + h * i/ PaintTool.getsize2() / 2
									, w * (PaintTool.getsize2() - i)/ PaintTool.getsize2()
									, h * (PaintTool.getsize2() - i)/ PaintTool.getsize2());
						}
					}
					g3.rotate( PaintTool.getsize3()*Math.PI/180, x1, y1);
					g3.translate(-0.5, -0.5);
				} else {
					if(regular){
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						setStroke(g2);
						g2.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
						if(ToolPanel.rb1.isSelected()){
							g2.drawRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
							for (int i = 0; i <= PaintTool.getsize(); i++) {
								if(ToolPanel.cb2.isSelected()){
									if(PaintTool.colornum < 23){
										g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
									}
								}g2.drawRect((x1 < x2) ? x1 + Math.abs(x1 - x2) * i
										/ PaintTool.getsize2() / 2 : x2
										+ Math.abs(x1 - x2) * i / PaintTool.getsize2()
										/ 2, (y1 < y2) ? y1 + Math.abs(y1 - y2) * i
										/ PaintTool.getsize2() / 2 : y2
										+ Math.abs(y1 - y2) * i / PaintTool.getsize2()
										/ 2, Math.abs(x1 - x2)
										* (PaintTool.getsize2() - i)
										/ PaintTool.getsize2(), Math.abs(y1 - y2)
										* (PaintTool.getsize2() - i)
										/ PaintTool.getsize2());
							}
						}else{
							int x11, y11, w, h;
							w = 2*Math.abs(x1 -x2);
							h = 2*Math.abs(y1 -y2);
							x11 = x1 - w/2;
							y11 = y1 - h/2;
							g2.drawRect(x11, y11, w, h);
							for (int i = 0; i <= PaintTool.getsize(); i++) {
								if(ToolPanel.cb2.isSelected()){
									if(PaintTool.colornum < 23){
										g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
									}
								}g2.drawRect(x11 + w * i/ PaintTool.getsize2() / 2 
										, y11 + h * i/ PaintTool.getsize2() / 2
										, w * (PaintTool.getsize2() - i)/ PaintTool.getsize2()
										, h * (PaintTool.getsize2() - i)/ PaintTool.getsize2());
							}
						}
						g2.rotate( PaintTool.getsize3()*Math.PI/180, x1, y1);
					}
				}
			} else if (PaintTool.type == 2) {// 長方形(中塗)
				if(!clock){
					int temp = x2;
					x2 = pointrotatex(x2,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
					y2 = pointrotatey(temp,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
				}
				int x11, y11, w, h;
				w = 2*Math.abs(x1 -x2);
				h = 2*Math.abs(y1 -y2);
				x11 = x1 - w/2;
				y11 = y1 - h/2;
				Area are;
				if(ToolPanel.rb1.isSelected()){
					are = new Area(new Rectangle.Float(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2
							, ((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2
							, (float)(Math.abs(x1 - x2) * PaintTool.getsize2f())
							, (float)(Math.abs(y1 - y2) * PaintTool.getsize2f())));
				}else{
					are = new Area(new Rectangle.Float(x11 + w * (1 - PaintTool.getsize2f()) / 2
							, y11 + h * (1 - PaintTool.getsize2f()) / 2
							, (float)(w * PaintTool.getsize2f())
							, (float)(h * PaintTool.getsize2f())));
				}
				AffineTransform tran = new AffineTransform();
				tran.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
				are.transform(tran);
				Area are1 = new Area();
				if(set){
					if((SelectOptionFrame.rb3.isSelected())){
						are1 = maskare;
					} else if((SelectOptionFrame.rb4.isSelected())){
						are1 = maskare2;
					}
				}else{
					are1 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
				}
				are1.subtract(are);
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					g3.clip(are1);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g3.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
					if(ToolPanel.rb1.isSelected()){
						g3.fillRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
								.abs(x1 - x2), Math.abs(y1 - y2));
					}else{
						g3.fillRect(x11, y11, w, h);
					}
					g3.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
					g3.setClip(null);
				} else {
					if(regular){
						g2.clip(are1);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
						if(ToolPanel.rb1.isSelected()){
							g2.fillRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
						}else{
							g2.fillRect(x11, y11, w, h);
						}
						
						g2.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
						g2.setClip(null);
					}
				}
			} else if (PaintTool.type == 3) {// 楕円
				if(!clock){
					int temp = x2;
					x2 = pointrotatex(x2,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
					y2 = pointrotatey(temp,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
				}
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					g3.translate(0.5, 0.5);
					//g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					setStroke(g3);
					g3.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
					if(ToolPanel.rb1.isSelected()){
						g3.drawOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
								.abs(x1 - x2), Math.abs(y1 - y2));
						for (int i = 0; i <= PaintTool.getsize(); i++) {
							if(ToolPanel.cb2.isSelected()){
								if(PaintTool.colornum < 23){
									g3.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
								}
							}
							g3.drawOval((x1 < x2) ? x1 + Math.abs(x1 - x2) * i
									/ PaintTool.getsize2() / 2 : x2
									+ Math.abs(x1 - x2) * i / PaintTool.getsize2()
									/ 2, (y1 < y2) ? y1 + Math.abs(y1 - y2) * i
									/ PaintTool.getsize2() / 2 : y2
									+ Math.abs(y1 - y2) * i / PaintTool.getsize2()
									/ 2, Math.abs(x1 - x2)
									* (PaintTool.getsize2() - i)
									/ PaintTool.getsize2(), Math.abs(y1 - y2)
									* (PaintTool.getsize2() - i)
									/ PaintTool.getsize2());
						}
					}else{
						int x11, y11, w, h;
						w = 2*Math.abs(x1 -x2);
						h = 2*Math.abs(y1 -y2);
						x11 = x1 - w/2;
						y11 = y1 - h/2;
						g3.drawOval(x11, y11, w, h);
						for (int i = 0; i <= PaintTool.getsize(); i++) {
							if(ToolPanel.cb2.isSelected()){
								if(PaintTool.colornum < 23){
									g3.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
								}
							}
							g3.drawOval(x11 + w * i/ PaintTool.getsize2() / 2 
									, y11 + h * i/ PaintTool.getsize2() / 2 
									, w* (PaintTool.getsize2() - i)/ PaintTool.getsize2(),
									h* (PaintTool.getsize2() - i)/ PaintTool.getsize2());
						}
					}
					g3.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
					g3.translate(-0.5, -0.5);
				} else {
					if(regular){
						//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						setStroke(g2);
						g2.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
						if(ToolPanel.rb1.isSelected()){
							g2.drawOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
							for (int i = 0; i <= PaintTool.getsize(); i++) {
								if(ToolPanel.cb2.isSelected()){
									if(PaintTool.colornum < 23){
										g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
									}
								}
								g2.drawOval((x1 < x2) ? x1 + Math.abs(x1 - x2) * i
										/ PaintTool.getsize2() / 2 : x2
										+ Math.abs(x1 - x2) * i / PaintTool.getsize2()
										/ 2, (y1 < y2) ? y1 + Math.abs(y1 - y2) * i
										/ PaintTool.getsize2() / 2 : y2
										+ Math.abs(y1 - y2) * i / PaintTool.getsize2()
										/ 2, Math.abs(x1 - x2)
										* (PaintTool.getsize2() - i)
										/ PaintTool.getsize2(), Math.abs(y1 - y2)
										* (PaintTool.getsize2() - i)
										/ PaintTool.getsize2());
							}
						}else{
							int x11, y11, w, h;
							w = 2*Math.abs(x1 -x2);
							h = 2*Math.abs(y1 -y2);
							x11 = x1 - w/2;
							y11 = y1 - h/2;
							g2.drawOval(x11, y11, w, h);
							for (int i = 0; i <= PaintTool.getsize(); i++) {
								if(ToolPanel.cb2.isSelected()){
									if(PaintTool.colornum < 23){
										g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue()+ Math.round(((float)i/PaintTool.getsize())*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
									}
								}
								g2.drawOval(x11 + w * i/ PaintTool.getsize2() / 2 
										, y11 + h * i/ PaintTool.getsize2() / 2 
										, w* (PaintTool.getsize2() - i)/ PaintTool.getsize2(),
										h* (PaintTool.getsize2() - i)/ PaintTool.getsize2());
							}
						}
						g2.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
					}
				}

			} else if (PaintTool.type == 4) {// 楕円(中塗)
				if(!clock){
					int temp = x2;
					x2 = pointrotatex(x2,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
					y2 = pointrotatey(temp,y2,PaintTool.getsize3()*Math.PI/180,x1,y1);
				}
				int x11, y11, w, h;
				w = 2*Math.abs(x1 -x2);
				h = 2*Math.abs(y1 -y2);
				x11 = x1 - w/2;
				y11 = y1 - h/2;
				Area are;
				if(ToolPanel.rb1.isSelected()){
					are = new Area(new Ellipse2D.Float(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2
							, ((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2
							, (float)(Math.abs(x1 - x2) * PaintTool.getsize2f())
							, (float)(Math.abs(y1 - y2) * PaintTool.getsize2f())));
				}else{
					are = new Area(new Ellipse2D.Float(x11 + w * (1 - PaintTool.getsize2f()) / 2
							, y11 + h * (1 - PaintTool.getsize2f()) / 2
							, (float)(w * PaintTool.getsize2f())
							, (float)(h * PaintTool.getsize2f())));
				}
				AffineTransform tran = new AffineTransform();
				tran.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
				are.transform(tran);
				Area are1 = new Area();
				if(set){
					if((SelectOptionFrame.rb3.isSelected())){
						are1 = maskare;
					} else if((SelectOptionFrame.rb4.isSelected())){
						are1 = maskare2;
					}
				}else{
					are1 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
				}
				are1.subtract(are);
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					imgtemp = new BufferedImage(img[imgnum].getWidth(), img[imgnum].getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g4 = imgtemp.createGraphics();
					g4.drawImage(img[imgnum], 0, 0, this);//一時imgin保存
					g4.clip(are1);
					setColor(g4);
					g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g4.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
					if(ToolPanel.rb1.isSelected()){
						g4.fillOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
								.abs(x1 - x2), Math.abs(y1 - y2));
						g4.setClip(null);
						if(Math.abs(x2-x1) > 2 && Math.abs(y2-y1) > 2){
							g4.drawOval((int)(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2)
									, (int)(((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2)
									, (int)(Math.abs(x1 - x2) * PaintTool.getsize2f())
									, (int)(Math.abs(y1 - y2) * PaintTool.getsize2f()));
							g4.drawOval((int)(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2)-1
									, (int)(((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2)-1
									, (int)(Math.abs(x1 - x2) * PaintTool.getsize2f())+2
									, (int)(Math.abs(y1 - y2) * PaintTool.getsize2f())+2);
						}
					}else{
						g4.fillOval(x11, y11, w, h);
						g4.setClip(null);
						for (int i = 0; i <= PaintTool.getsize(); i++) {
							g3.drawOval(x11 + w * i/ PaintTool.getsize2() / 2 
									, y11 + h * i/ PaintTool.getsize2() / 2 
									, w* (PaintTool.getsize2() - i)/ PaintTool.getsize2(),
									h* (PaintTool.getsize2() - i)/ PaintTool.getsize2());
						}
						if(w > 2 && h > 2){
							g4.drawOval((int)(x11 + w * (1 - PaintTool.getsize2f()) / 2)
									, (int)(y11 + h * (1 - PaintTool.getsize2f()) / 2)
									, (int)(w * PaintTool.getsize2f())
									, (int)(h * PaintTool.getsize2f()));
							g4.drawOval((int)(x11 + w * (1 - PaintTool.getsize2f()) / 2)-1
									, (int)(y11 + h * (1 - PaintTool.getsize2f()) / 2)-1
									, (int)(w * PaintTool.getsize2f())+2
									, (int)(h * PaintTool.getsize2f())+2);
						}
					}
					g4.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g3.drawImage(imgtemp, 0, 0, this);
					g4.dispose();
					imgtemp = null;
				} else {
					if(regular){
						imgtemp = new BufferedImage(img[imgnum].getWidth(), img[imgnum].getHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D g4 = imgtemp.createGraphics();
						g4.drawImage(img[imgnum], 0, 0, this);//一時imgin保存
						g4.clip(are1);
						setColor(g4);
						g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g4.rotate(-PaintTool.getsize3()*Math.PI/180, x1, y1);
						if(ToolPanel.rb1.isSelected()){
							g4.fillOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
							g4.setClip(null);
							if(Math.abs(x2-x1) > 2 && Math.abs(y2-y1) > 2){
								g4.drawOval((int)(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2)
										, (int)(((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2)
										, (int)(Math.abs(x1 - x2) * PaintTool.getsize2f())
										, (int)(Math.abs(y1 - y2) * PaintTool.getsize2f()));
								g4.drawOval((int)(((x1 < x2) ? x1 : x2) + Math.abs(x1 - x2) * (1 - PaintTool.getsize2f()) / 2)-1
										, (int)(((y1 < y2) ? y1: y2) + Math.abs(y1 - y2) * (1 - PaintTool.getsize2f()) / 2)-1
										, (int)(Math.abs(x1 - x2) * PaintTool.getsize2f())+2
										, (int)(Math.abs(y1 - y2) * PaintTool.getsize2f())+2);
							}
						}else{
							g4.fillOval(x11, y11, w, h);
							g4.setClip(null);
							for (int i = 0; i <= PaintTool.getsize(); i++) {
								g3.drawOval(x11 + w * i/ PaintTool.getsize2() / 2 
										, y11 + h * i/ PaintTool.getsize2() / 2 
										, w* (PaintTool.getsize2() - i)/ PaintTool.getsize2(),
										h* (PaintTool.getsize2() - i)/ PaintTool.getsize2());
							}
							if(w > 2 && h > 2){
								g4.drawOval((int)(x11 + w * (1 - PaintTool.getsize2f()) / 2)
										, (int)(y11 + h * (1 - PaintTool.getsize2f()) / 2)
										, (int)(w * PaintTool.getsize2f())
										, (int)(h * PaintTool.getsize2f()));
								g4.drawOval((int)(x11 + w * (1 - PaintTool.getsize2f()) / 2)-1
										, (int)(y11 + h * (1 - PaintTool.getsize2f()) / 2)-1
										, (int)(w * PaintTool.getsize2f())+2
										, (int)(h * PaintTool.getsize2f())+2);
							}
						}
						g4.rotate(PaintTool.getsize3()*Math.PI/180, x1, y1);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawImage(imgtemp, 0, 0, this);
						g4.dispose();
						imgtemp = null;
					}
				}

			} else if (PaintTool.type == 5) { // 線
				if(PaintTool.p15.getpattern()[0] == 0 && PaintTool.p15.getpattern().length == 1){//点線かどうか
					if(press && (regular || clock)){
						debug =true;
						g3.drawImage(img[imgnum], 0, 0, this);
						if(set){
							if((SelectOptionFrame.rb3.isSelected())){
								g3.clip(maskare);
							} else if((SelectOptionFrame.rb4.isSelected())){
								g3.clip(maskare2);
							}
						}
						if(imgtemp == null){
							imgtemp = new BufferedImage(img[imgnum].getWidth(), img[imgnum].getHeight(), BufferedImage.TYPE_INT_RGB);
							Graphics2D g4 = imgtemp.createGraphics();
							g4.drawImage(img[imgnum], 0, 0, this);//一時imgin保存
						}
						if(!clock){
							Graphics2D g4 = imgtemp.createGraphics();
							setColor(g4);
							g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							g4.setStroke(new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
							g4.drawLine(x1, y1, x1, y1);
							if (oldx >= -999) {
								g4.drawLine(x1, y1, oldx, oldy);
							}
							oldx = x1;
							oldy = y1;
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g3.drawImage(imgtemp, 0, 0, this);
					} else if(!press && regular && imgtemp != null){
						Graphics2D g4 = imgtemp.createGraphics();
						setColor(g4);
						g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						setStroke(g4);
						g4.drawLine(x1, y1, x1, y1);
						if (oldx >= -999) {
							g4.drawLine(x1, y1, oldx, oldy);
						}
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawImage(imgtemp, 0, 0, this);
						oldx = -1000;
						oldy = -1000;
						imgtemp = null;
					}
				}else{//点線
					if(press && (regular || clock)){
						debug =true;
						g3.drawImage(img[imgnum], 0, 0, this);
						if(set){
							if((SelectOptionFrame.rb3.isSelected())){
								g3.clip(maskare);
							} else if((SelectOptionFrame.rb4.isSelected())){
								g3.clip(maskare2);
							}
						}
						if(imgtemp == null){//書き始め
							imgtemp = new BufferedImage(img[imgnum].getWidth(), img[imgnum].getHeight(), BufferedImage.TYPE_INT_RGB);
							Graphics2D g4 = imgtemp.createGraphics();
							g4.drawImage(img[imgnum], 0, 0, this);//一時imgin保存
							tentenphase = 0;
						}
						if(!clock){
							Graphics2D g4 = imgtemp.createGraphics();
							setColor(g4);
							g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							if (oldx > -1000) {
								g4.setStroke(new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
								int tmp  = Math.round(PaintTool.getsize() * PaintTool.p15.getpattern()[tentenphase]) - (distancecount + (x1-oldx)*(x1-oldx) + (y1-oldy)*(y1-oldy));
								while(tmp < 0){
									tmp = Math.round(PaintTool.getsize() * PaintTool.p15.getpattern()[tentenphase]) - distancecount;
									x2 = oldx + (int)Math.round((x1-oldx)*(Math.sqrt(tmp)/Math.sqrt((x1-oldx)*(x1-oldx)+(y1-oldy)*(y1-oldy))));
									y2 = oldy + (int)Math.round((y1-oldy)*(Math.sqrt(tmp)/Math.sqrt((x1-oldx)*(x1-oldx)+(y1-oldy)*(y1-oldy))));
									if(!off)g4.drawLine(oldx, oldy, x2, y2);
									oldx = x2;
									oldy = y2;
									off = !off;
									if(PaintTool.p15.getpattern().length == 4){
										if(tentenphase < 3){
											tentenphase++;
										}else{
											tentenphase = 0;
										}
									}else if(PaintTool.p15.getpattern().length == 2){
										if(tentenphase == 0){
											tentenphase++;
										}else{
											tentenphase = 0;
										}
									}
									distancecount = 0;
									tmp  = Math.round(PaintTool.getsize() * PaintTool.p15.getpattern()[tentenphase]) - (distancecount + (x1-oldx)*(x1-oldx) + (y1-oldy)*(y1-oldy));
								}
								if(!off)g4.drawLine(oldx, oldy, x1, y1);
								g4.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
								distancecount += (x1-oldx)*(x1-oldx) + (y1-oldy)*(y1-oldy);
								oldx = x1;
								oldy = y1;
							}
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g3.drawImage(imgtemp, 0, 0, this);
					} else if(!press && regular && imgtemp != null){//線確定
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawImage(imgtemp, 0, 0, this);
						oldx = -1000;
						oldy = -1000;
						off = false;
						tentenphase = 0;
						imgtemp = null;
					}
				}
			} else if (PaintTool.type == 6) {// 多角形
				if(press){
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					setStroke(g3);
					if(pcnt+1>0)g3.drawPolygon(x3,y3,pcnt+1);
					for(int i = 0;i<pcnt+1;i++){
						g3.drawOval(x3[i] - 2, y3[i] - 2, 4, 4);
						if(i>0)g3.drawLine(x3[i-1], y3[i-1], x3[i], y3[i]);
					}
					g3.translate(-0.5, -0.5);
				}else if (debug == true || regular == false) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					setStroke(g3);
					if(pcnt>0)g3.drawPolygon(x3,y3,pcnt);
					for(int i = 0;i<pcnt;i++){
						g3.drawOval(x3[i] - 2, y3[i] - 2, 4, 4);
						if(i>0)g3.drawLine(x3[i-1], y3[i-1], x3[i], y3[i]);
					}
					g3.translate(-0.5, -0.5);
				} else {
					if (pcnt > 2) {
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						setStroke(g2);
						g2.drawPolygon(x3, y3, pcnt);
					}
					pcnt = 0;
					super.paintComponent(g);
				}
			} else if (PaintTool.type == 7) {// 多角形(中塗)
				if(press){
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					if(pcnt+1>0)g3.drawPolygon(x3,y3,pcnt+1);
					for(int i = 0;i<pcnt+1;i++){
						g3.drawOval(x3[i] - 2, y3[i] - 2, 4, 4);
						if(i>0)g3.drawLine(x3[i-1], y3[i-1], x3[i], y3[i]);
					}
					g3.translate(-0.5, -0.5);
				}else if (debug == true || regular == false) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					if(pcnt>0)g3.drawPolygon(x3,y3,pcnt);
					for(int i = 0;i<pcnt;i++){
						g3.drawOval(x3[i] - 2, y3[i] - 2, 4, 4);
					}
					g3.translate(-0.5, -0.5);
				} else {
					if (pcnt > 2) {
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.fillPolygon(x3, y3, pcnt);
					}
					pcnt = 0;
					super.paintComponent(g);
				}
			} else if (PaintTool.type == 23) {// 自由形
				if (debug == true || regular == false) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					setStroke(g3);
					if(pcnt>0)g3.drawPolygon(x4, y4, pcnt);
					g3.translate(-0.5, -0.5);
				} else {
					if (pcnt > 2) {
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						setStroke(g2);
						g2.drawPolygon(x4, y4, pcnt);
					}
					pcnt = 0;
					super.paintComponent(g);
				}
			} else if (PaintTool.type == 24) {// 自由形(中塗)
				if (debug == true || regular == false) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.translate(0.5, 0.5);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					if(pcnt>0)g3.fillPolygon(x4, y4, pcnt);
					g3.translate(-0.5, -0.5);
				} else {
					if (pcnt > 2) {
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.fillPolygon(x4, y4, pcnt);
					}
					pcnt = 0;
					super.paintComponent(g);
				}
			} else if (PaintTool.type == 8) {// スプレー
				if(regular == true){
					Random rnd = new Random();
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					for (int i = 0; i < PaintTool.getsize2(); i++) {
						if(ToolPanel.cb3.isSelected()){
							double ran3 = Math.random();
							g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + (int)Math.floor(ran3*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + (int)Math.floor(ran3*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue() + (int)Math.floor(ran3*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
						}else if(ToolPanel.cb4.isSelected()){
							g2.setColor(new Color(PaintTool.color[PaintTool.colornum].getRed() + (int)Math.floor(Math.random()*(PaintTool.color[PaintTool.colornum+1].getRed() - PaintTool.color[PaintTool.colornum].getRed())),PaintTool.color[PaintTool.colornum].getGreen() + (int)Math.floor(Math.random()*(PaintTool.color[PaintTool.colornum+1].getGreen() - PaintTool.color[PaintTool.colornum].getGreen())),PaintTool.color[PaintTool.colornum].getBlue() + (int)Math.floor(Math.random()*(PaintTool.color[PaintTool.colornum+1].getBlue() - PaintTool.color[PaintTool.colornum].getBlue()))));
						}else{
							int tmp = PaintTool.getsize6();
							if(PaintTool.colornum + tmp > 24)tmp = 24;
							if(PaintTool.colornum + tmp < 1)tmp = 1;
							g2.setColor(PaintTool.color[PaintTool.colornum + rnd.nextInt(tmp)]);
						}
						int ran1 = rnd.nextInt(PaintTool.getsize())
								- PaintTool.getsize() / 2; // 位置ランダムx
						int ran2 = rnd.nextInt(PaintTool.getsize())
								- PaintTool.getsize() / 2; // 位置ランダムy
						if (ran1 * ran1 + ran2 * ran2 < (PaintTool.getsize() / 2)
								* (PaintTool.getsize() / 2)) {
							int ran; // 粒大きさランダム
							if (PaintTool.getsize4() - PaintTool.getsize3() > 0) {
								ran = rnd.nextInt(PaintTool.getsize4()
										- (PaintTool.getsize3()))
										+ PaintTool.getsize3()+1;
							} else {// 粒大きさランダム無し
								ran = PaintTool.getsize4()+1;
							}
							int ran3 = (rnd.nextInt(ran / 2) - ran / 4 + ran * 1 / 2);// 粒歪ランダム
							int ran4 = (rnd.nextInt(ran / 2) - ran / 4 + ran * 1 / 2);

							double ran5 = rnd.nextDouble(); // 角度ランダム
							g2.rotate(ran5*Math.PI, x1, y1);

							g2.fillOval( x1 - PaintTool.getsize3() / 2 + ran1, y1
									- PaintTool.getsize3() / 2 + ran2, ran3, ran4);

							g2.rotate( -ran5*Math.PI, x1, y1);

						} else {
							i--;
						}
					}
				}
			} else if (PaintTool.type == 9) {// 文字
				if (debug == true || (clock && !exit)) {
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					debug = true;
					g3.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					if (PaintTool.getMoziStyle() == 0) {
						f = new Font(PaintTool.getfont(), Font.PLAIN, PaintTool
								.getMozisize());
					} else if (PaintTool.getMoziStyle() == 1) {
						f = new Font(PaintTool.getfont(), Font.ITALIC,
								PaintTool.getMozisize());
					} else {
						f = new Font(PaintTool.getfont(), Font.BOLD, PaintTool
								.getMozisize());
					}
					g3.setFont(f);
					FontMetrics fm = g3.getFontMetrics();
					Rectangle rectText = fm.getStringBounds(PaintTool.getMozi(), g3).getBounds();
					if(MoziFrame.cb1.isSelected()){
						if(MoziFrame.rb4.isSelected())g3.setColor(PaintTool.color[0]);
						if(MoziFrame.rb5.isSelected())g3.setColor(PaintTool.color[1]);
						if(MoziFrame.rb6.isSelected())g3.setColor(PaintTool.color[2]);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()/3));
						g3.drawString(PaintTool.getMozi(), x1 - rectText.width/2 + PaintTool.getMozisize()*1/25*dx[MoziFrame.cb2.getSelectedIndex()], 
								y1 - rectText.height/2+fm.getMaxAscent() + PaintTool.getMozisize()*1/25*dy[MoziFrame.cb2.getSelectedIndex()]);
					}
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g3.drawString(PaintTool.getMozi(), x1 - rectText.width/2, y1 - rectText.height/2+fm.getMaxAscent());
				} else {
					if(regular == true){
						g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
								RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
						if (PaintTool.getMoziStyle() == 0) {
							f = new Font(PaintTool.getfont(), Font.PLAIN, PaintTool
									.getMozisize());
						} else if (PaintTool.getMoziStyle() == 1) {
							f = new Font(PaintTool.getfont(), Font.ITALIC,
									PaintTool.getMozisize());
						} else {
							f = new Font(PaintTool.getfont(), Font.BOLD, PaintTool
									.getMozisize());
						}
						g2.setFont(f);
						FontMetrics fm = g2.getFontMetrics();
						Rectangle rectText = fm.getStringBounds(PaintTool.getMozi(), g2).getBounds();
						if(MoziFrame.cb1.isSelected()){
							if(MoziFrame.rb4.isSelected())g2.setColor(PaintTool.color[0]);
							if(MoziFrame.rb5.isSelected())g2.setColor(PaintTool.color[1]);
							if(MoziFrame.rb6.isSelected())g2.setColor(PaintTool.color[2]);
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()/3));
							g2.drawString(PaintTool.getMozi(), x1 - rectText.width/2 + PaintTool.getMozisize()*1/25*dx[MoziFrame.cb2.getSelectedIndex()], y1
								- rectText.height/2+fm.getMaxAscent() + PaintTool.getMozisize()*1/25*dy[MoziFrame.cb2.getSelectedIndex()]);
						}
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawString(PaintTool.getMozi(), x1 - rectText.width/2, y1
							- rectText.height/2+fm.getMaxAscent());
					}
				}
			} else if (PaintTool.type == 12) {// 選択ツール
				g2.setClip(null);
				g3.setClip(null);
				if(PaintTool.getComand() == 10){//変形ボタン時 サイズ情報変更
					if(PaintTool.p1.cbh.getSelectedItem() == "回転"){
						PaintTool.spin = PaintTool.p1.getsize3();
					}else if(PaintTool.p1.cbh.getSelectedItem() == "拡大縮小"){
						PaintTool.ratiox = PaintTool.p1.getsize();
						PaintTool.ratioy = PaintTool.p1.getsize2();
					}else if(PaintTool.p1.cbh.getSelectedItem() == "傾き"){
						PaintTool.shtype = ToolPanel.rb2.isSelected();
						if(PaintTool.shtype == false){
							PaintTool.shx = PaintTool.p1.getsize4f();
							PaintTool.shy = 0;
						} else if(PaintTool.shtype == true){
							PaintTool.shx = 0;
							PaintTool.shy = PaintTool.p1.getsize4f();
						}
					}
				}
				g3.drawImage(img[imgnum], 0, 0, this);
				g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
				if (debug || (clock && (press || (SelectOptionFrame.rb9.isSelected()&& pcnt2 > 0))) || (press && SelectOptionFrame.rb9.isSelected() && reaverelease == false)) {
					if(move == 1 || PaintTool.getComand() == 10){//Image移動
						if(SelectOptionFrame.rb5.isSelected() && (ToolPanel.rb3.isSelected() || PaintTool.getComand() == 10)){ //裏側白塗り
							if(dontfill == true){
								g2.setColor(Color.white);
								Area are = new Area(shap);
								g2.fill((Shape)are);
								dontfill = false;
							}
						}
						if(PaintTool.getComand() == 10){
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {public void run() {
								debug = false;
								PaintTool.rewrite();
							}}
							, 1);
							imgincopy();
							try {
								File file = new File(tmpname + "in" + imgnum + ".bmp");
								ImageIO.write(imgin, "bmp", file);
							} catch (Exception d) {
							}
						}
						
						Area are = new Area(shap);
						AffineTransform tran = new AffineTransform();
						tran.translate((shap.getBounds().x - (x1-x2) + (int)PaintTool.shx*(ssety1-sety1)+ (int)PaintTool.shx*(y1-y2))*100/PaintTool.ratiox - shap.getBounds().x ,  (shap.getBounds().y - (y1-y2) + (int)PaintTool.shy*(ssetx1-setx1)+ (int)PaintTool.shy*(x1-x2))*100/PaintTool.ratioy - shap.getBounds().y);
						are.transform(tran);
						g3.setClip(are);
						if(!(SelectOptionFrame.rb7.isSelected())){
							g3.drawImage(imgin,pointrotatex((shap.getBounds().x - (x1-x2) + (int)PaintTool.shx*(ssety1-sety1)+ (int)PaintTool.shx*(y1-y2))*100/PaintTool.ratiox, (shap.getBounds().y - (y1-y2) + (int)PaintTool.shy*(ssetx1-setx1)+ (int)PaintTool.shy*(x1-x2))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x - (x1-x2) + (int)PaintTool.shx*(ssety1-sety1)+ (int)PaintTool.shx*(y1-y2))*100/PaintTool.ratiox, (shap.getBounds().y - (y1-y2) + (int)PaintTool.shy*(ssetx1-setx1)+ (int)PaintTool.shy*(x1-x2))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
						}
						g3.setClip(null);
						g3.setColor(Color.red);
						if(clock && clockswitch){
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
						}else{
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						Area are1 = new Area(shap);
						AffineTransform af = new AffineTransform();
						af.translate((shap.getBounds().x - (x1-x2) + (int)PaintTool.shx*(ssety1-sety1)+ (int)PaintTool.shx*(y1-y2))*100/PaintTool.ratiox - shap.getBounds().x ,  (shap.getBounds().y - (y1-y2) + (int)PaintTool.shy*(ssetx1-setx1)+ (int)PaintTool.shy*(x1-x2))*100/PaintTool.ratioy - shap.getBounds().y);
						are1.transform(af);
						g3.draw((Shape)are1);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					} else if (move == 2){//移動release後処理
						rotatex2 = x2;
						rotatey2 = y2;
						Area are = new Area(shap);
						g3.setClip(are);
						if(!(SelectOptionFrame.rb7.isSelected())){
							g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
						}
						g3.setClip(null);
						g3.setColor(Color.red);
						if(clock && clockswitch){
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
						}else{
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						Area are1 = new Area(shap);
						g3.draw((Shape)are1);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						move = 0;
					}else if(press && SelectOptionFrame.rb9.isSelected()){
						if(set){
							Area are = new Area(shap);
							g3.setClip(are);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
							if(!(SelectOptionFrame.rb7.isSelected())){
								g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
							}
							g3.setClip(null);
							g3.setColor(Color.RED);
							if(clock && clockswitch){
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
							}else{
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
							}
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
							Area are1 = new Area(shap);
							g3.draw((Shape)are1);
						}
						g3.setColor(Color.black);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						if(pcnt2 != 0){
							g3.setColor(Color.RED);
							g3.drawPolygon(x4,y4,pcnt2+1);
							for(int i = 0;i<pcnt2+1;i++){
								g3.drawOval(x4[i] - 2, y4[i] - 2, 4, 4);
							}
						}
					}else{//枠作成前デバッグ
						if(set){
							Area are = new Area(shap);
							g3.setClip(are);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
							if(!(SelectOptionFrame.rb7.isSelected())){
								g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
							}
							g3.setClip(null);
							g3.setColor(Color.RED);
							if(clock && clockswitch){
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
							}else{
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
							}
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
							Area are1 = new Area(shap);
							g3.draw((Shape)are1);
						}
						g3.setColor(Color.black);
						float[] dash = { 5.0f, 5.0f};
						g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						if(SelectOptionFrame.rb1.isSelected()){
							g3.drawRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
						} else if(SelectOptionFrame.rb2.isSelected()){
							g3.drawOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
						} else {
							if(pcnt2 != 0){
								g3.setColor(Color.RED);
								g3.drawPolygon(x4,y4,pcnt2);
								if(SelectOptionFrame.rb9.isSelected()){
									for(int i = 0;i<pcnt2;i++){
										g3.drawOval(x4[i] - 2, y4[i] - 2, 4, 4);
									}
								}
							}
						}
					}
				} else if(rectcapt){//選択枠作成
					fillshap = new Area();
					if(SelectOptionFrame.rb10.isSelected()){//マジックハンド枠作成
						makeShap();
					}
					if(set == false){
						set = true;
						timer1 = new Timer();
						timer1.schedule(new TimerTask() {long t1 = System.currentTimeMillis(); public void run() {
							clockswitch = !clockswitch;
							long t2;
							t2 = System.currentTimeMillis();
							if(t2 - t1 < 850){
								if(!press){
									clock = true;
									PaintTool.p3.revalidate();
									rewrite();
								}
							}
							t1 = t2;
						}}, 1, 750);
						dontfill = true;
						setx1 = x1;
						setx2 = x2;
						sety1 = y1;
						sety2 = y2;
						ssetx1 = x1;
						ssety1 = y1;
						rotatex2 = x2;
						rotatey2 = y2;
						g3.setColor(Color.red);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						if(SelectOptionFrame.rb1.isSelected()){
							shap = new Rectangle((setx1 < setx2) ? setx1 : setx2, (sety1 < sety2) ? sety1 : sety2, Math
									.abs(setx1 - setx2), Math.abs(sety1 - sety2));
						} else if(SelectOptionFrame.rb2.isSelected()){
							shap = new Ellipse2D.Float((setx1 < setx2) ? setx1 : setx2, (sety1 < sety2) ? sety1 : sety2, Math
									.abs(setx1 - setx2), Math.abs(sety1 - sety2));
						} else if(SelectOptionFrame.rb10.isSelected()){
							shap = fillshap;
						} else{
							shap = new Polygon(x4,y4,pcnt2);
							pcnt2 = 0;
						}
						if(shap.getBounds().getHeight() == 0 || shap.getBounds().getWidth() == 0){
							set = false;
							timer1.cancel();
							dontfill = false;
						}else{
							if(clock && clockswitch){
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
							}else{
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
							}
							g3.draw(shap);
							imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
							imgin.setData(img[imgnum].getData(shap.getBounds()).createTranslatedChild(0, 0) );
							rectcapt = false;
						}
						listname = "選択範囲作成";
					} else{
						setchange[(imgnum+(PaintTool.HistoryNUM - 1))%PaintTool.HistoryNUM] = true;
						dontfill = true;
						g3.setColor(Color.red);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						Area are, are1;
						if(SelectOptionFrame.rb1.isSelected()){
							are = new Area(new Rectangle((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2)));
						} else if(SelectOptionFrame.rb2.isSelected()){
							are = new Area(new Ellipse2D.Float((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2)));
						} else if(SelectOptionFrame.rb10.isSelected()){
							are = fillshap;
						} else{
							are = new Area(new Polygon(x4,y4,pcnt2));
							pcnt2 = 0;
						}

						Area are2 = new Area(shap);
						int tempx = shap.getBounds().x;
						int tempy = shap.getBounds().y;
						are1 = new Area(shap);
						if(ToolPanel.rb4.isSelected()){ //選択範囲結合
							are1.add(are);
						}else if(ToolPanel.rb5.isSelected()){
							are1.subtract(are);
						}else if(ToolPanel.rb10.isSelected()){
							are1.intersect(are);
						}
						shap = (Shape)are1;
						if(shap.getBounds().height == 0 && shap.getBounds().width == 0){
							set = false;
							timer1.cancel();
							dontfill = false;
							ToolPanel.rb4.setEnabled(true);
							ToolPanel.rb5.setEnabled(true);
							PaintTool.spin = 0;
							PaintTool.ratiox = 100;
							PaintTool.ratioy = 100;
							PaintTool.shx = 0;
							PaintTool.shy = 0;
						}else{
							imgtemp = new BufferedImage(imgin.getWidth(), imgin.getHeight(), BufferedImage.TYPE_INT_RGB);
							imgtemp.setData(imgin.getData().createTranslatedChild(0, 0) );//一時imgin保存

							are1 = new Area(shap);
							imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
							Graphics2D g4 = imgin.createGraphics();
							g4.drawImage(img[imgnum], (int)-(are1.getBounds().getWidth() - ((float)PaintTool.ratiox/(float)100*imgin.getWidth() + imgin.getHeight()*PaintTool.shx))/2, (int)-(are1.getBounds().getHeight() - ((float)PaintTool.ratioy/(float)100*imgin.getHeight() + imgin.getWidth()*PaintTool.shy))/2, (int)(are1.getBounds().getWidth()-(are1.getBounds().getWidth() - ((float)PaintTool.ratiox/(float)100*imgin.getWidth() + imgin.getHeight()*PaintTool.shx))/2), (int)(are1.getBounds().getHeight() - (are1.getBounds().getHeight() - ((float)PaintTool.ratioy/(float)100*imgin.getHeight() + imgin.getWidth()*PaintTool.shy))/2),(int)are1.getBounds().getMinX(),(int)are1.getBounds().getMinY(),(int)are1.getBounds().getMaxX(), (int)are1.getBounds().getMaxY(), this);
							AffineTransform tran = new AffineTransform();
							tran = new AffineTransform();//前imgin→imgin以降用クリップ
							tran.translate(-shap.getBounds().x,-shap.getBounds().y);
							are2.transform(tran);
							g4.setClip(are2);
							g4.drawImage(imgtemp, tempx - shap.getBounds().x, tempy - shap.getBounds().y, this);
							g4.dispose();
							imgtemp = null;

							are1 = new Area(shap);
							tran = new AffineTransform();
							tran.translate(-(tempx- shap.getBounds().x)*(PaintTool.ratiox - 100)/100,-(tempx- shap.getBounds().y)*(PaintTool.ratioy - 100)/100);
							are1.transform(tran);
							shap = (Shape)are1;
							//これ以降表示処理
							are = new Area(shap);
							g3.setClip(are);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SelectOptionFrame.gettrans()));
							if(!(SelectOptionFrame.rb7.isSelected())){
								g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
							}
							g3.setClip(null);
							g3.setColor(Color.red);
							if(clock && clockswitch){
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
							}else{
								float[] dash = { 5.0f, 5.0f};
								g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
							}
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
							are1 = new Area(shap);
							AffineTransform af = new AffineTransform();
							af.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
							are1.transform(af);
							g3.draw((Shape)are1);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
							rectcapt = false;
						}
						listname = "選択範囲変更";
					}
					shaps[imgnum+1] = new Area(shap);
					capt = true;
				} else if(PaintTool.getComand() == 13){//選択範囲開放
					leaveArea();
				} else if(PaintTool.getComand() == 14){//選択範囲反転
					setchange[(imgnum+(PaintTool.HistoryNUM - 1))%PaintTool.HistoryNUM] = true;
					dontfill = true;
					g3.setColor(Color.red);
					if(clock && clockswitch){
						float[] dash = { 5.0f, 5.0f};
						g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
					}else{
						float[] dash = { 5.0f, 5.0f};
						g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
					}
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
					Area are = new Area(new Rectangle2D.Float(0, 0, PaintTool.sizex, PaintTool.sizey));
					are.subtract(new Area(shap));
					shap = (Shape)are;
					g3.draw(shap);
					imgin = new BufferedImage(shap.getBounds().width, shap.getBounds().height, BufferedImage.TYPE_INT_RGB);
					imgin.setData(img[imgnum].getData(shap.getBounds()).createTranslatedChild(0, 0) );
					rectcapt = false;
					listname = "選択範囲反転";
					shaps[imgnum+1] = new Area(shap);
					capt = true;
				}else {//それ以外の時
					if(set == true){
						Area are = new Area(shap);
						g3.setClip(are);
						if(!(SelectOptionFrame.rb7.isSelected())){
							g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
						}
						g3.setClip(null);
						g3.setColor(Color.RED);
						if(clock && clockswitch){
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
						}else{
							float[] dash = { 5.0f, 5.0f};
							g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						Area are1 = new Area(shap);
						AffineTransform af = new AffineTransform();
						af.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
						are1.transform(af);
						g3.draw((Shape)are1);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}
				}
			} else if (PaintTool.type == 13) { // オリジナル線
				if(press && (regular || clock)){
					debug =true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					if(imgtemp == null){
						imgtemp = new BufferedImage(img[imgnum].getWidth(), img[imgnum].getHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics2D g4 = imgtemp.createGraphics();
						g4.drawImage(img[imgnum], 0, 0, this);//一時imgin保存
					}
					if(!clock){
						Graphics2D g4 = imgtemp.createGraphics();
						float w;
						if(ToolPanel.rb1.isSelected()){
							w = 1;
						}else{
							w = -1;
						}
						if(PaintTool.rote){
							originalrotate = (originalrotate+PaintTool.getsize2())%360;
							g4.rotate(-w*(float)originalrotate*Math.PI/180, x1, y1);
						}else{
							originalrotate = 0;
						}
						for(int i = 0; i < OriginalPanel.cnt ; i++){
							g4.setColor(OriginalPanel.color[i]);
							g4.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, OriginalPanel.compo[i]));
							g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							setx1 = x1 + OriginalPanel.x[i]-(PaintTool.p8.getWidth()-10)/2;
							sety1 = y1 + OriginalPanel.y[i]-(PaintTool.p8.getHeight() - 74)/2;
							setx2 = oldx + OriginalPanel.x[i]-(PaintTool.p8.getWidth()-10)/2;
							sety2 = oldy + OriginalPanel.y[i]-(PaintTool.p8.getHeight() - 74)/2;
							g4.drawLine(setx1, sety1, setx1, sety1);
							if (OriginalPanel.style[i] == 0) {
								g4.fillRect(setx1 - OriginalPanel.size[i] / 2, sety1
										- OriginalPanel.size[i] / 2, OriginalPanel.size[i],
										OriginalPanel.size[i]);
								if (oldx >= -999) {
									if(PaintTool.rote) g4.rotate(w*(float)originalrotate*Math.PI/180, x1, y1);
									for (int t = 1; t < 256; t++) {
										if(PaintTool.rote) g4.rotate(-w*(float)(originalrotate-PaintTool.getsize2()*t/256)*Math.PI/180, x1 -(x1 - oldx) * t/ 256, y1 - (y1 - oldy) * t/ 256);
										g4.fillRect(setx1 - (setx1 - setx2) * t/ 256 - OriginalPanel.size[i] / 2,
												sety1 - (sety1 - sety2) * t/ 256 - OriginalPanel.size[i] / 2,
												OriginalPanel.size[i],
												OriginalPanel.size[i]);
										if(PaintTool.rote) g4.rotate(w*(float)(originalrotate-PaintTool.getsize2()*t/256)*Math.PI/180, x1 -(x1 - oldx) * t/ 256, y1 - (y1 - oldy) * t/ 256);
									}
									if(PaintTool.rote) g4.rotate(-w*(float)originalrotate*Math.PI/180, x1, y1);
								}
							}else if (OriginalPanel.style[i] == 1) {
								g4.fillOval(setx1 - OriginalPanel.size[i] / 2, sety1
										- OriginalPanel.size[i] / 2, OriginalPanel.size[i],
										OriginalPanel.size[i]);
								if (oldx >= -999) {
									if(PaintTool.rote) g4.rotate(w*(float)originalrotate*Math.PI/180, x1, y1);
									for (int t = 1; t < 256; t++) {
										if(PaintTool.rote) g4.rotate(-w*(float)(originalrotate-PaintTool.getsize2()*t/256)*Math.PI/180, x1 -(x1 - oldx) * t/ 256, y1 - (y1 - oldy) * t/ 256);
										g4.fillOval(setx1 - (setx1 - setx2) * t / 256
												- OriginalPanel.size[i] / 2, sety1 - (sety1 - sety2) * t
												/ 256 - OriginalPanel.size[i] / 2, OriginalPanel.size[i]
												, OriginalPanel.size[i]);
										if(PaintTool.rote) g4.rotate(w*(float)(originalrotate-PaintTool.getsize2()*t/256)*Math.PI/180, x1 -(x1 - oldx) * t/ 256, y1 - (y1 - oldy) * t/ 256);
									}
									if(PaintTool.rote) g4.rotate(-w*(float)originalrotate*Math.PI/180, x1, y1);
								}
							}
						}
						if(PaintTool.rote){
							g4.rotate(w*(float)originalrotate/180, x1, y1);
						}
						oldx = x1;
						oldy = y1;
					}
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g3.drawImage(imgtemp, 0, 0, this);
				} else if(!press && regular && imgtemp != null){//リリース
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					g2.drawImage(imgtemp, 0, 0, this);
					oldx = -1000;
					oldy = -1000;
					imgtemp = null;
				}else if(debug || clock){//
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(exit == false){
						g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						for(int i = 0; i < OriginalPanel.cnt ; i++){
							g3.setColor(OriginalPanel.color[i]);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, OriginalPanel.compo[i]));
							g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							setx1 = x1 + OriginalPanel.x[i]-(PaintTool.p8.getWidth()-10)/2;
							sety1 = y1 + OriginalPanel.y[i]-(PaintTool.p8.getHeight() - 74)/2;
							setx2 = oldx + OriginalPanel.x[i]-(PaintTool.p8.getWidth()-10)/2;
							sety2 = oldy + OriginalPanel.y[i]-(PaintTool.p8.getHeight() - 74)/2;
							g3.drawLine(setx1, sety1, setx1, sety1);
							if (OriginalPanel.style[i] == 0) {
								g3.fillRect(setx1 - OriginalPanel.size[i] / 2, sety1
										- OriginalPanel.size[i] / 2, OriginalPanel.size[i],
										OriginalPanel.size[i]);
							}else if (OriginalPanel.style[i] == 1) {
								g3.fillOval(setx1 - OriginalPanel.size[i] / 2, sety1
										- OriginalPanel.size[i] / 2, OriginalPanel.size[i],
										OriginalPanel.size[i]);
							}
						}
					}
				}
			} else if (PaintTool.type == 14) { // フィルター
				if(regular){
					if(PaintTool.getComand() == 12){//全体
						imgtemp = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);
						imgtemp2 = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);
						
						BufferedImage imgt = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);;
						Graphics2D g5 = (Graphics2D) imgt.getGraphics();
						g5.scale(1.04,1.04);
						g5.drawImage(img[imgnum], 2-(int)Math.ceil((float)img[imgnum].getWidth()/50), 2-(int)Math.ceil((float)img[imgnum].getHeight()/50), this);
						g5.scale((float)1/1.04,(float)1/1.04);
						g5.drawImage(img[imgnum], 2, 2, this);
						
						Area are = new Area(new Rectangle(0, 0, PaintTool.sizex ,PaintTool.sizey));
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						g2.clip(are);
						
						if(ToolPanel.cbf.getSelectedItem() == "ネガポジ"){
							imgtemp2 = negaposi(imgt);
						}else if(ToolPanel.cbf.getSelectedItem() == "モノクロ"){
							ColorConvertOp co = new ColorConvertOp(
								    ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
							Graphics2D g4 = (Graphics2D) imgtemp.getGraphics();
							g4.drawImage(imgt, 0, 0, this);
							co.filter(imgtemp, imgtemp2);
						}else{
							Kernel kernel;
							float f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10;
							if(ToolPanel.cbf.getSelectedItem() == "ぼかし"){
								f0 = 0f;
								f1 = 0f;
								f2 = 1f / 9f;
								f3 = 1f / 9f;
								f4 = 1f / 9f;
								f5 = 1f / 9f;
								f6 = 1f / 9f;
								f7 = 1f / 9f;
								f8 = 1f / 9f;
								f9 = 1f / 9f;
								f10 = 1f / 9f;
								
							}else if(ToolPanel.cbf.getSelectedItem() == "シャープtype1"){
								f0 = -0.1f;
								f1 = -0.1f;
								f2 = -0.3f;
								f3 = -0.3f;
								f4 = -0.3f;
								f5 = -0.3f;
								f6 = 5f;
								f7 = -0.3f;
								f8 = -0.3f;
								f9 = -0.3f;
								f10 = -0.3f;
								
								
							}else if(ToolPanel.cbf.getSelectedItem() == "シャープtype2"){
								f0 = 0f;
								f1 = 0f;
								f2 = 0f;
								f3 = -1f;
								f4 = 0f;
								f5 = -1f;
								f6 = 5f;
								f7 = -1f;
								f8 = 0f;
								f9 = -1f;
								f10 = 0f;
							}else if(ToolPanel.cbf.getSelectedItem() == "線図化"){
								f0 = 0f;
								f1 = 0f;
								f2 = 0f;
								f3 = -1f;
								f4 = 0f;
								f5 = -1f;
								f6 = 4f;
								f7 = -1f;
								f8 = 0f;
								f9 = -1f;
								f10 = 0f;
							}else if(ToolPanel.cbf.getSelectedItem() == "垂直方向エッジ検出(正)"){
								f0 = 0f;
								f1 = 0f;
								f2 = -1f;
								f3 = 0f;
								f4 = 1f;
								f5 = -2f;
								f6 = 0f;
								f7 = 2f;
								f8 = -1f;
								f9 = 0f;
								f10 = 1f;
							}else if(ToolPanel.cbf.getSelectedItem() == "垂直方向エッジ検出(負)"){
								f0 = 0f;
								f1 = 0f;
								f2 = 1f;
								f3 = 0f;
								f4 = -1f;
								f5 = 2f;
								f6 = 0f;
								f7 = -2f;
								f8 = 1f;
								f9 = 0f;
								f10 = -1f;
							}else if(ToolPanel.cbf.getSelectedItem() == "水平方向エッジ検出(正)"){
								f0 = 0f;
								f1 = 0f;
								f2 = -1f;
								f3 = -2f;
								f4 = -1f;
								f5 = 0f;
								f6 = 0f;
								f7 = 0f;
								f8 = 1f;
								f9 = 2f;
								f10 = 1f;
							}else if(ToolPanel.cbf.getSelectedItem() == "エンボス(左上)"){
								f0 = 0f;
								f1 = 0f;
								f2 = 7f;
								f3 = 0f;
								f4 = 0f;
								f5 = 0f;
								f6 = -3f;
								f7 = 0f;
								f8 = 0f;
								f9 = 0f;
								f10 = -3f;
							}else if(ToolPanel.cbf.getSelectedItem() == "エンボス(右下)"){
								f0 = 0f;
								f1 = 0f;
								f2 = -3f;
								f3 = 0f;
								f4 = 0f;
								f5 = 0f;
								f6 = -3f;
								f7 = 0f;
								f8 = 0f;
								f9 = 0f;
								f10 = 7f;
							}else if(ToolPanel.cbf.getSelectedItem() == "エンボス(右上)"){
								f0 = 0f;
								f1 = 0f;
								f2 = 0f;
								f3 = 0f;
								f4 = 7f;
								f5 = 0f;
								f6 = -3f;
								f7 = 0f;
								f8 = -3f;
								f9 = 0f;
								f10 = 0f;
							}else if(ToolPanel.cbf.getSelectedItem() == "エンボス(左下)"){
								f0 = 0f;
								f1 = 0f;
								f2 = 0f;
								f3 = 0f;
								f4 = -3f;
								f5 = 0f;
								f6 = -3f;
								f7 = 0f;
								f8 = 7f;
								f9 = 0f;
								f10 = 0f;
							}else{
								f0 = 0f;
								f1 = 0f;
								f2 = 1f;
								f3 = 2f;
								f4 = 1f;
								f5 = 0f;
								f6 = 0f;
								f7 = 0f;
								f8 = -1f;
								f9 = -2f;
								f10 = -1f;
							}
							float[] matrix = {
									f0,f0,f1,f0,f0,
									f0,f2,f3,f4,f0,
									f1,f5,f6,f7,f1,
									f0,f8,f9,f10,f0,
									f0,f0,f1,f0,f0};
							kernel = new Kernel(5,5,matrix);
							ConvolveOp co = new ConvolveOp(kernel);
							Graphics2D g4 = (Graphics2D) imgtemp.getGraphics();
							g4.drawImage(imgt, 0, 0, this);
							co.filter(imgtemp, imgtemp2);
							for(int i = 1;i<PaintTool.getsize2();i++){
								imgt = imgtemp2;
								g4.drawImage(imgt, 0, 0, this);
								co.filter(imgtemp, imgtemp2);
							}
							g4.dispose();
						}
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawImage(imgtemp2, -2, -2, this);
						
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
					} else{
						g3.drawImage(img[imgnum], 0, 0, this);
						Area are;
						if(x2 != -1000){
							BasicStroke bs = new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
							are = new Area(bs.createStrokedShape(new Line2D.Double(x1, y1, x2, y2)));
						}else{
							BasicStroke bs = new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
							are = new Area(bs.createStrokedShape(new Line2D.Double(x1, y1, x1, y1)));
						}
						Area are1 = new Area(new Rectangle(PaintTool.sizex,PaintTool.sizey));
						are1.subtract(new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey)));
						are.subtract(are1);
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						if(press && !presstmp){
							aretmp = new Area();
							imgtemp = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);
							imgtemp2 = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);
							
							BufferedImage imgt = new BufferedImage(PaintTool.sizex + 4, PaintTool.sizey + 4, BufferedImage.TYPE_INT_RGB);;
							Graphics2D g5 = (Graphics2D) imgt.getGraphics();
							g5.scale(1.04,1.04);
							g5.drawImage(img[imgnum], 2-(int)Math.ceil((float)img[imgnum].getWidth()/50), 2-(int)Math.ceil((float)img[imgnum].getHeight()/50), this);
							g5.scale((float)1/1.04,(float)1/1.04);
							g5.drawImage(img[imgnum], 2, 2, this);
							
							if(ToolPanel.cbf.getSelectedItem() == "ネガポジ"){
								imgtemp2 = negaposi(imgt);
							}else if(ToolPanel.cbf.getSelectedItem() == "モノクロ"){
								ColorConvertOp co = new ColorConvertOp(
									    ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
								Graphics2D g4 = (Graphics2D) imgtemp.getGraphics();
								g4.drawImage(imgt, -2, -2, this);
								co.filter(imgtemp, imgtemp2);
							}else{
								Kernel kernel;
								float f0, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10;
								if(ToolPanel.cbf.getSelectedItem() == "ぼかし"){
									f0 = 0f;
									f1 = 0f;
									f2 = 1f / 9f;
									f3 = 1f / 9f;
									f4 = 1f / 9f;
									f5 = 1f / 9f;
									f6 = 1f / 9f;
									f7 = 1f / 9f;
									f8 = 1f / 9f;
									f9 = 1f / 9f;
									f10 = 1f / 9f;
								}else if(ToolPanel.cbf.getSelectedItem() == "シャープtype1"){
									f0 = -0.1f;
									f1 = -0.1f;
									f2 = -0.3f;
									f3 = -0.3f;
									f4 = -0.3f;
									f5 = -0.3f;
									f6 = 5f;
									f7 = -0.3f;
									f8 = -0.3f;
									f9 = -0.3f;
									f10 = -0.3f;
								}else if(ToolPanel.cbf.getSelectedItem() == "シャープtype2"){
									f0 = 0f;
									f1 = 0f;
									f2 = 0f;
									f3 = -1f;
									f4 = 0f;
									f5 = -1f;
									f6 = 5f;
									f7 = -1f;
									f8 = 0f;
									f9 = -1f;
									f10 = 0f;
								}else if(ToolPanel.cbf.getSelectedItem() == "線図化"){
									f0 = 0f;
									f1 = 0f;
									f2 = 0f;
									f3 = -1f;
									f4 = 0f;
									f5 = -1f;
									f6 = 4f;
									f7 = -1f;
									f8 = 0f;
									f9 = -1f;
									f10 = 0f;
								}else if(ToolPanel.cbf.getSelectedItem() == "垂直方向エッジ検出(正)"){
									f0 = 0f;
									f1 = 0f;
									f2 = -1f;
									f3 = 0f;
									f4 = 1f;
									f5 = -2f;
									f6 = 0f;
									f7 = 2f;
									f8 = -1f;
									f9 = 0f;
									f10 = 1f;
								}else if(ToolPanel.cbf.getSelectedItem() == "垂直方向エッジ検出(負)"){
									f0 = 0f;
									f1 = 0f;
									f2 = 1f;
									f3 = 0f;
									f4 = -1f;
									f5 = 2f;
									f6 = 0f;
									f7 = -2f;
									f8 = 1f;
									f9 = 0f;
									f10 = -1f;
								}else if(ToolPanel.cbf.getSelectedItem() == "水平方向エッジ検出(正)"){
									f0 = 0f;
									f1 = 0f;
									f2 = -1f;
									f3 = -2f;
									f4 = -1f;
									f5 = 0f;
									f6 = 0f;
									f7 = 0f;
									f8 = 1f;
									f9 = 2f;
									f10 = 1f;
								}else if(ToolPanel.cbf.getSelectedItem() == "エンボス1"){
									f0 = 0f;
									f1 = 0f;
									f2 = 7f;
									f3 = 0f;
									f4 = 0f;
									f5 = 0f;
									f6 = -3f;
									f7 = 0f;
									f8 = 0f;
									f9 = 0f;
									f10 = -3f;
								}else if(ToolPanel.cbf.getSelectedItem() == "エンボス2"){
									f0 = 0f;
									f1 = 0f;
									f2 = -3f;
									f3 = 0f;
									f4 = 0f;
									f5 = 0f;
									f6 = -3f;
									f7 = 0f;
									f8 = 0f;
									f9 = 0f;
									f10 = 7f;
								}else if(ToolPanel.cbf.getSelectedItem() == "エンボス3"){
									f0 = 0f;
									f1 = 0f;
									f2 = 0f;
									f3 = 0f;
									f4 = 7f;
									f5 = 0f;
									f6 = -3f;
									f7 = 0f;
									f8 = -3f;
									f9 = 0f;
									f10 = 0f;
								}else if(ToolPanel.cbf.getSelectedItem() == "エンボス4"){
									f0 = 0f;
									f1 = 0f;
									f2 = 0f;
									f3 = 0f;
									f4 = -3f;
									f5 = 0f;
									f6 = -3f;
									f7 = 0f;
									f8 = 7f;
									f9 = 0f;
									f10 = 0f;
								}else{
									f0 = 0f;
									f1 = 0f;
									f2 = 1f;
									f3 = 2f;
									f4 = 1f;
									f5 = 0f;
									f6 = 0f;
									f7 = 0f;
									f8 = -1f;
									f9 = -2f;
									f10 = -1f;
								}
								float[] matrix = {
										f0,f0,f1,f0,f0,
										f0,f2,f3,f4,f0,
										f1,f5,f6,f7,f1,
										f0,f8,f9,f10,f0,
										f0,f0,f1,f0,f0};
								kernel = new Kernel(5,5,matrix);
								ConvolveOp co = new ConvolveOp(kernel);
								Graphics2D g4 = (Graphics2D) imgtemp.getGraphics();
								g4.drawImage(imgt, -2, -2, this);
								co.filter(imgtemp, imgtemp2);
								for(int i = 1;i<PaintTool.getsize2();i++){
									imgt = imgtemp2;
									g4.drawImage(imgt, 0, 0, this);
									co.filter(imgtemp, imgtemp2);
								}
								g4.dispose();
							}
						}
						aretmp.add(are);
						if(!press){
							g2.clip(aretmp);
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							g2.drawImage(imgtemp2, 0, 0, this);
							imgtemp = null;
							imgtemp2 = null;
							debug = false;
							x2 = -1000;
							y2 = -1000;
						}else{
							g3.clip(aretmp);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							g3.drawImage(imgtemp2, 0, 0, this);
							debug = true;
							x2 = x1;
							y2 = y1;
						}
						presstmp = press;
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
					}
				}
			} else if (PaintTool.type == 15) { // Bright
				if(regular){
					if(PaintTool.getComand() == 12){
						imgtemp = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
						imgtemp2 = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
						Graphics g4 = imgtemp.getGraphics();
						Area are = new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey));
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						g2.clip(are);
						g4.drawImage(img[imgnum], 0, 0, this);
						if(ToolPanel.rb2.isSelected()){
							int n = (int)Math.round(256 / (64/(PaintTool.getsize2f()*6.4)));
							if(n > 256) n = 256;
							byte[] table = new byte[256];
							for(int i = 0;i < 256;i++){
							  int val = (int)((i + (n / 2)) / n);
							  table[i] = (byte)(val * n > 255 ? 255 : val * n);
							}
							ByteLookupTable blt = new ByteLookupTable(0,table);
							LookupOp lt = new LookupOp(blt,null);
							lt.filter(imgtemp, imgtemp2);
						}else{
							RescaleOp op = new RescaleOp(PaintTool.getsize2f(),0,null);
							op.filter(imgtemp, imgtemp2);
						}
						g2.drawImage(imgtemp2, 0, 0, this);
						g4.dispose();
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
						imgtemp = null;
						imgtemp2 = null;
					}else{
						g3.drawImage(img[imgnum], 0, 0, this);
						Area are;
						if(x2 != -1000){
							BasicStroke bs = new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
							are = new Area(bs.createStrokedShape(new Line2D.Double(x1, y1, x2, y2)));
						}else{
							BasicStroke bs = new BasicStroke(PaintTool.getsize(),BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
							are = new Area(bs.createStrokedShape(new Line2D.Double(x1, y1, x1, y1)));
						}
						Area are1 = new Area(new Rectangle(PaintTool.sizex,PaintTool.sizey));
						are1.subtract(new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey)));
						are.subtract(are1);
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						if(press && !presstmp){
							aretmp = new Area();
							imgtemp = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
							imgtemp2 = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
							Graphics g4 = imgtemp.getGraphics();
							g4.drawImage(img[imgnum], 0, 0, this);
							if(ToolPanel.rb2.isSelected()){
								int n = (int)Math.round(256 / (64/(PaintTool.getsize2f()*6.4)));
								if(n > 256) n = 256;
								byte[] table = new byte[256];
								for(int i = 0;i < 256;i++){
								  int val = (int)((i + (n / 2)) / n);
								  table[i] = (byte)(val * n > 255 ? 255 : val * n);
								}
								ByteLookupTable blt = new ByteLookupTable(0,table);
								LookupOp lt = new LookupOp(blt,null);
								lt.filter(imgtemp, imgtemp2);
							}else{
								RescaleOp op = new RescaleOp(PaintTool.getsize2f(),0,null);
								op.filter(imgtemp, imgtemp2);
							}
							g4.dispose();
						}
						aretmp.add(are);
						if(!press){
							g2.clip(aretmp);
							g2.drawImage(imgtemp2, 0, 0, this);
							imgtemp = null;
							imgtemp2 = null;
							debug = false;
							x2 = -1000;
							y2 = -1000;
						}else{
							g3.clip(aretmp);
							g3.drawImage(imgtemp2, 0, 0, this);
							debug = true;
							x2 = x1;
							y2 = y1;
						}
						presstmp = press;
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
					}
				}
			} else if (PaintTool.type == 25) { // RGBフィルター
				if(regular){
					if(PaintTool.getComand() == 12){
						imgtemp = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
						imgtemp2 = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
						Graphics g4 = imgtemp.getGraphics();
						Area are = new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey));
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						g2.clip(are);
						g4.drawImage(img[imgnum], 0, 0, this);
						float[] f = {PaintTool.p1.getsize2f(),PaintTool.p1.getsize3f(),PaintTool.p1.getsize4f()}; // 係数
					    float[] d = {PaintTool.p1.getsize6f(),PaintTool.p1.getsize7f(),PaintTool.p1.getsize8f()}; // オフセット
					    RescaleOp op = new RescaleOp(f,d,null);
						op.filter(imgtemp, imgtemp2);
						g2.drawImage(imgtemp2, 0, 0, this);
						g4.dispose();
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
						imgtemp = null;
						imgtemp2 = null;
					}else{
						g3.drawImage(img[imgnum], 0, 0, this);
						Area are = new Area((Shape)new Ellipse2D.Float(x1 - PaintTool.getsize()/2, y1 - PaintTool.getsize()/2, PaintTool.getsize(), PaintTool.getsize()));
						Area are1 = new Area(new Rectangle(PaintTool.sizex,PaintTool.sizey));
						are1.subtract(new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey)));
						are.subtract(are1);
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						if(press && !presstmp){
							aretmp = new Area();
							imgtemp = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
							imgtemp2 = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
							Graphics g4 = imgtemp.getGraphics();
							g4.drawImage(img[imgnum], 0, 0, this);
							float[] f = {PaintTool.p1.getsize2f(),PaintTool.p1.getsize3f(),PaintTool.p1.getsize4f()}; // 係数
						    float[] d = {PaintTool.p1.getsize6f(),PaintTool.p1.getsize7f(),PaintTool.p1.getsize8f()}; // オフセット
						    RescaleOp op = new RescaleOp(f,d,null);
							op.filter(imgtemp, imgtemp2);
							g4.dispose();
						}
						aretmp.add(are);
						if(!press){
							g2.clip(aretmp);
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							g2.drawImage(imgtemp2, 0, 0, this);
							imgtemp = null;
							imgtemp2 = null;
							debug = false;
						}else{
							g3.clip(aretmp);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							g3.drawImage(imgtemp2, 0, 0, this);
							debug = true;
						}
						presstmp = press;
						if((SelectOptionFrame.rb3.isSelected())){
							g2.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							maskare2 = new Area((Shape)new Rectangle(0,0,PaintTool.sizex,PaintTool.sizey));
							maskare2.subtract(maskare);
							g2.clip(maskare2);
						}
					}
				}
			}else if (PaintTool.type == 17) {// Gradaietion
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					if(ToolPanel.rb1.isSelected()){//描く
						if(ToolPanel.rb3.isSelected()){
							float[] dist = {0.0f, 1.0f};
							Color[] colors = {PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
							LinearGradientPaint gradient;
							gradient = new LinearGradientPaint(x1, y1, x2, y2, dist, colors, getGradientPattern());
							g3.setPaint(gradient);
							g3.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
						}else if(ToolPanel.rb4.isSelected()){
							float[] dist = {0.0f, 0.5f, 1.0f};
							Color[] colors = {PaintTool.color[(PaintTool.colornum+1)%24], PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
							LinearGradientPaint gradient;
							gradient = new LinearGradientPaint(x1, y1, x2, y2, dist, colors, getGradientPattern());
							g3.setPaint(gradient);
							g3.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
						}else{//円グラデーション
							float[] dist = {0.0f, 1.0f};
							Color[] colors = {PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
							RadialGradientPaint gradient;
							if(centerx == -1 && centery == -1){
								gradient = new RadialGradientPaint(x1, y1, (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)), dist, colors, getGradientPattern());
							}else{
								gradient = new RadialGradientPaint(x1, y1, (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)), centerx, centery, dist, colors, getGradientPattern());
							}
							g3.setPaint(gradient);
							g3.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
						}
					}else{//始点設定
						g3.drawOval(centerx-1, centery-1, 2, 2);
					}
				} else {
					if(regular){
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						if(ToolPanel.rb1.isSelected()){//描く
							if(ToolPanel.rb3.isSelected()){
								float[] dist = {0.0f, 1.0f};
								Color[] colors = {PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
								LinearGradientPaint gradient;
								gradient = new LinearGradientPaint(x1, y1, x2, y2, dist, colors, getGradientPattern());
								g2.setPaint(gradient);
								g2.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
							}else if(ToolPanel.rb4.isSelected()){
								float[] dist = {0.0f, 0.5f, 1.0f};
								Color[] colors = {PaintTool.color[(PaintTool.colornum+1)%24], PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
								LinearGradientPaint gradient;
								gradient = new LinearGradientPaint(x1, y1, x2, y2, dist, colors, getGradientPattern());
								g2.setPaint(gradient);
								g2.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
							}else{
								float[] dist = {0.0f, 1.0f};
								Color[] colors = {PaintTool.color[PaintTool.colornum], PaintTool.color[(PaintTool.colornum+1)%24]};
								RadialGradientPaint gradient;
								if(centerx == -1 && centery == -1){
									gradient = new RadialGradientPaint(x1, y1, (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)), dist, colors, getGradientPattern());
								}else{
									gradient = new RadialGradientPaint(x1, y1, (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)), centerx, centery, dist, colors, getGradientPattern());
								}
							g2.setPaint(gradient);
								g2.fill(new Rectangle2D.Double(0, 0, PaintTool.sizex, PaintTool.sizey));
							}
						}else{//始点設定
							g3.drawOval(centerx-1, centery-1, 2, 2);
						}
					}
				}
			}else if (PaintTool.type == 18) {// HorizonLine
				double gx1 = x1, gy1 = y1, gx2 = x1, gy2 = y1, gx3 = x2, gy3 = y2, gx4 = x2, gy4 = y2;
				double k;
				if(x1 == x2){
					gx1 = PaintTool.sizex;
					gy1 = y1;
					gx2 = 0;
					gy2 = y1;
					gx3 = PaintTool.sizex;
					gy3 = y2;
					gx4 = 0;
					gy4 = y2;
					k = 0;
				}else if(y1 == y2){
					gx1 = x1;
					gy1 = 0;
					gx2 = x1;
					gy2 = PaintTool.sizey;
					gx3 = x2;
					gy3 = 0;
					gx4 = x2;
					gy4 = PaintTool.sizey;
					k = 0;
				}else{
					k = -(double)1/((double)(y2 - y1)/(double)(x2 - x1));
					if(gx1 <= PaintTool.sizex){
						while(gx1 <= PaintTool.sizex){
							gx1++;
							gy1 += k;
						}
					}else{
						while(gx1 >= PaintTool.sizex){
							gx1--;
							gy1 -= k;
						}
						gx1++;
						gy1 += k;
					}
					if(gx2 >= 0){
						while(gx2 >= 0){
							gx2--;
							gy2 -= k;
						}
					}else{
						while(gx2 <= 0){
							gx2++;
							gy2 += k;
						}
						gx2--;
						gy2 -= k;
					}
					if(gx3 <= PaintTool.sizex){
						while(gx3 <= PaintTool.sizex){
							gx3++;
							gy3 += k;
						}
					}else{
						while(gx3 >= PaintTool.sizex){
							gx3--;
							gy3 -= k;
						}
						gx3++;
						gy3 += k;
					}
					if(gx4 >= 0){
						while(gx4 >= 0){
							gx4--;
							gy4 -= k;
						}
					}else{
						while(gx4 <= 0){
							gx4++;
							gy4 += k;
						}
						gx4--;
						gy4 -= k;
					}
				}
				if(ToolPanel.cb1.isSelected()){
					if(ToolPanel.rb1.isSelected()){
						if(x1<=x2 && y1<=y2){
							x3[0] = 0;
							y3[0] = PaintTool.sizey;
							x3[1] = 0;
							y3[1] = 0;
							x3[2] = PaintTool.sizex;
							y3[2] = 0;
						}else if(x1<=x2 && y1>=y2){
							x3[0] = 0;
							y3[0] = 0;
							x3[1] = 0;
							y3[1] = PaintTool.sizey;
							x3[2] = PaintTool.sizex;
							y3[2] = PaintTool.sizey;
						}else if(x1>=x2 && y1>=y2){
							x3[0] = 0;
							y3[0] = PaintTool.sizey;
							x3[1] = PaintTool.sizex;
							y3[1] = PaintTool.sizey;
							x3[2] = PaintTool.sizex;
							y3[2] = 0;
						}else if(x1>=x2 && y1<=y2){
							x3[0] = 0;
							y3[0] = 0;
							x3[1] = PaintTool.sizex;
							y3[1] = 0;
							x3[2] = PaintTool.sizex;
							y3[2] = PaintTool.sizey;
						}
						x3[3] = (int)Math.round(gx1);
						y3[3] = (int)Math.round(gy1);
						x3[4] = (int)Math.round(gx2);
						y3[4] = (int)Math.round(gy2);
					}else{
						x3[0] = (int)Math.round(gx1);
						y3[0] = (int)Math.round(gy1);
						x3[1] = (int)Math.round(gx2);
						y3[1] = (int)Math.round(gy2);
						x3[3] = (int)Math.round(gx4);
						y3[3] = (int)Math.round(gy4);
						x3[4] = (int)Math.round(gx3);
						y3[4] = (int)Math.round(gy3);
						if(k >= 0){
							x3[2] = 0;
							y3[2] = PaintTool.sizey;
							x3[5] = PaintTool.sizex;
							y3[5] = 0;
						}else{
							x3[2] = 0;
							y3[2] = 0;
							x3[5] = PaintTool.sizex;
							y3[5] = PaintTool.sizey;
						}
					}
				}
				if (debug || press) {
					debug = true;
					g3.drawImage(img[imgnum], 0, 0, this);
					if(set){
						if((SelectOptionFrame.rb3.isSelected())){
							g3.clip(maskare);
						} else if((SelectOptionFrame.rb4.isSelected())){
							g3.clip(maskare2);
						}
					}
					g3.translate(0.5, 0.5);
					g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					if(!ToolPanel.cb1.isSelected()){
						g3.setStroke(new BasicStroke(PaintTool.getsize4()));
					}
					if(ToolPanel.cb1.isSelected()){
						if(ToolPanel.rb1.isSelected()){
							g3.fillPolygon(x3, y3, 5);
						}else{
							g3.fillPolygon(x3, y3, 6);
						}
					}else{
						g3.drawLine((int)Math.round(gx1), (int)Math.round(gy1), (int)Math.round(gx2), (int)Math.round(gy2));
					}
					if(PaintTool.colornum < 23){
						g3.setColor(PaintTool.color[PaintTool.colornum+1]);
					}else{
						g3.setColor(PaintTool.color[PaintTool.colornum]);
					}
					if(ToolPanel.cb1.isSelected()){
						if(ToolPanel.rb1.isSelected()){
							if(x2<x1 && y2<y1){
								x3[0] = 0;
								y3[0] = PaintTool.sizey;
								x3[1] = 0;
								y3[1] = 0;
								x3[2] = PaintTool.sizex;
								y3[2] = 0;
							}else if(x2<x1 && y2>y1){
								x3[0] = 0;
								y3[0] = 0;
								x3[1] = 0;
								y3[1] = PaintTool.sizey;
								x3[2] = PaintTool.sizex;
								y3[2] = PaintTool.sizey;
							}else if(x2>x1 && y2>y1){
								x3[0] = 0;
								y3[0] = PaintTool.sizey;
								x3[1] = PaintTool.sizex;
								y3[1] = PaintTool.sizey;
								x3[2] = PaintTool.sizex;
								y3[2] = 0;
							}else if(x2>x1 && y2<y1){
								x3[0] = 0;
								y3[0] = 0;
								x3[1] = PaintTool.sizex;
								y3[1] = 0;
								x3[2] = PaintTool.sizex;
								y3[2] = PaintTool.sizey;
							}
							x3[3] = (int)Math.round(gx3);
							y3[3] = (int)Math.round(gy3);
							x3[4] = (int)Math.round(gx4);
							y3[4] = (int)Math.round(gy4);
							g3.fillPolygon(x3, y3, 5);
						}
					}else{
						g3.drawLine((int)Math.round(gx3), (int)Math.round(gy3), (int)Math.round(gx4), (int)Math.round(gy4));
					}
					g3.translate(-0.5, -0.5);
				} else {
					if(regular == true){
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						if(!ToolPanel.cb1.isSelected()){
							g2.setStroke(new BasicStroke(PaintTool.getsize4()));
						}
						if(ToolPanel.cb1.isSelected()){
							if(ToolPanel.rb1.isSelected()){
								g2.fillPolygon(x3, y3, 5);
							}else{
								g2.fillPolygon(x3, y3, 6);
							}
						}else{
							g2.drawLine((int)Math.round(gx1), (int)Math.round(gy1), (int)Math.round(gx2), (int)Math.round(gy2));
						}
						if(PaintTool.colornum < 23){
							g2.setColor(PaintTool.color[PaintTool.colornum+1]);
						}else{
							g2.setColor(PaintTool.color[PaintTool.colornum]);
						}
						if(ToolPanel.cb1.isSelected()){
							if(ToolPanel.rb1.isSelected()){
								if(x2<x1 && y2<y1){
									x3[0] = 0;
									y3[0] = PaintTool.sizey;
									x3[1] = 0;
									y3[1] = 0;
									x3[2] = PaintTool.sizex;
									y3[2] = 0;
								}else if(x2<x1 && y2>y1){
									x3[0] = 0;
									y3[0] = 0;
									x3[1] = 0;
									y3[1] = PaintTool.sizey;
									x3[2] = PaintTool.sizex;
									y3[2] = PaintTool.sizey;
								}else if(x2>x1 && y2>y1){
									x3[0] = 0;
									y3[0] = PaintTool.sizey;
									x3[1] = PaintTool.sizex;
									y3[1] = PaintTool.sizey;
									x3[2] = PaintTool.sizex;
									y3[2] = 0;
								}else if(x2>x1 && y2<y1){
									x3[0] = 0;
									y3[0] = 0;
									x3[1] = PaintTool.sizex;
									y3[1] = 0;
									x3[2] = PaintTool.sizex;
									y3[2] = PaintTool.sizey;
								}
								x3[3] = (int)Math.round(gx3);
								y3[3] = (int)Math.round(gy3);
								x3[4] = (int)Math.round(gx4);
								y3[4] = (int)Math.round(gy4);
								g2.fillPolygon(x3, y3, 5);
							}
						}else{
							g2.drawLine((int)Math.round(gx3), (int)Math.round(gy3), (int)Math.round(gx4), (int)Math.round(gy4));
						}
					}
				}
			}else if (PaintTool.type == 19) {// CopyStamp
				if (debug || (clock && !exit)) {
					if(stampset == true){//Draw前デバッグ
						g3.drawImage(img[imgnum], 0, 0, this);
						debug = true;
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						Area are = new Area(stampshap);
						AffineTransform tran = new AffineTransform();
						tran.translate(x1 - are.getBounds().width/2,y1 - are.getBounds().height/2);
						are.transform(tran);
						Area are2 = new Area(are);
						if(set){
							if((SelectOptionFrame.rb3.isSelected())){
								are2.intersect(maskare);
							} else if((SelectOptionFrame.rb4.isSelected())){
								are2.intersect(maskare2);
							}
						}
						g3.setClip(are2);
						g3.drawImage(stampimg,x1 - are.getBounds().width/2,y1 - are.getBounds().height/2, this);
						g3.setClip(null);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}else if(regular || (clock && press)){//枠作成前デバッグ
						g3.drawImage(img[imgnum], 0, 0, this);
						debug = true;
						g3.setColor(Color.black);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						if(ToolPanel.rb3.isSelected()){
							g3.drawOval((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
						} else if(ToolPanel.rb4.isSelected()){
							g3.drawRect((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
									.abs(x1 - x2), Math.abs(y1 - y2));
						} else if(ToolPanel.rb5.isSelected()){
							g3.drawPolygon(x4,y4,pcnt2);
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}
				} else if(rectcapt){//選択枠作成
					stampset = true;
					g3.setColor(Color.red);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
					if(ToolPanel.rb3.isSelected()){
						stampshap = new Ellipse2D.Float((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
							.abs(x1 - x2), Math.abs(y1 - y2));
						Area are = new Area(new Rectangle(0, 0, PaintTool.sizex, PaintTool.sizey));
						are.intersect(new Area(stampshap));
						stampshap = are;
					} else if(ToolPanel.rb4.isSelected()){
						stampshap = new Rectangle((x1 < x2) ? x1 : x2, (y1 < y2) ? y1 : y2, Math
							.abs(x1 - x2), Math.abs(y1 - y2));
					} else if(ToolPanel.rb5.isSelected()){
						stampshap = new Polygon(x4,y4,pcnt2);
						pcnt2 = 0;
					}
					stampimg = new BufferedImage(stampshap.getBounds().width, stampshap.getBounds().height, BufferedImage.TYPE_INT_RGB);
					stampimg.setData(img[imgnum].getData(stampshap.getBounds()).createTranslatedChild(0, 0) );
					Area are = new Area(stampshap);
					AffineTransform tran = new AffineTransform();
					tran.translate(-are.getBounds().x, -are.getBounds().y);
					are.transform(tran);
					stampshap = are;
					rectcapt = false;
					ToolPanel.b7.setEnabled(true);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				} else {//それ以外の時
					if(regular){
						Area are = new Area(stampshap);
						AffineTransform tran = new AffineTransform();
						tran.translate(x1 - are.getBounds().width/2,y1 - are.getBounds().height/2);
						are.transform(tran);
						if((SelectOptionFrame.rb3.isSelected())){
							are.subtract(maskare2);
						} else if((SelectOptionFrame.rb4.isSelected())){
							are.subtract(maskare);
						}
						g2.clip(are);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
						g2.drawImage(stampimg,x1 - are.getBounds().width/2,y1 - are.getBounds().height/2, this);
						g2.setClip(null);
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}
				}
			}else if (PaintTool.type == 22) {// コピーブラシ
				if (debug || (clock && !exit)) {
					if(stampset){
						if(pointset){//描写中心点決定前デバッグ
							g3.drawImage(img[imgnum], 0, 0, this);
							debug = true;
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							Area are = new Area(new Ellipse2D.Float(x1 - PaintTool.getsize()/2,y1 - PaintTool.getsize()/2,PaintTool.getsize(), PaintTool.getsize()));
							g3.setClip(are);
							g3.drawImage(stampimg, x1 - copyx, y1 - copyy, this);
							g3.setClip(null);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						}else{//Draw前デバッグ
							g3.drawImage(img[imgnum], 0, 0, this);
							debug = true;
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							Area are = new Area(new Ellipse2D.Float(x1 - PaintTool.getsize()/2,y1 - PaintTool.getsize()/2,PaintTool.getsize(), PaintTool.getsize()));
							g3.setClip(are);
							g3.drawImage(stampimg, copyx, copyy, this);
							g3.setClip(null);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
						}
					} else{//中心点決定前デバッグ
						g3.drawImage(img[imgnum], 0, 0, this);
						debug = true;
						g3.setColor(Color.black);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						g3.drawLine(x1 - 10, y1, x1 + 10, y1);
						g3.drawLine(x1, y1 - 10, x1, y1 + 10);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}
				} else if(rectcapt){//中心点決定
					stampset = true;
					pointset = true;
					stampimg = new BufferedImage(PaintTool.sizex, PaintTool.sizey, BufferedImage.TYPE_INT_RGB);
					stampimg.setData(img[imgnum].getData().createTranslatedChild(0, 0) );
					copyx = x1;
					copyy = y1;
					rectcapt = false;
					ToolPanel.b7.setEnabled(true);
				} else if(regular){
					if(pointset){//描写中心点決定
						pointset = false;
						copyx = x1 - copyx;
						copyy = y1 - copyy;
					}
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					Area are = new Area(new Ellipse2D.Float(x1 - PaintTool.getsize()/2,y1 - PaintTool.getsize()/2,PaintTool.getsize(), PaintTool.getsize()));
					g2.setClip(are);
					g2.drawImage(stampimg, copyx, copyy, this);
					g2.setClip(null);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				}
			}else if (PaintTool.type == 20) {//曲線
				if (debug || (clock && press) ) {
					g3.drawImage(img[imgnum], 0, 0, this);
					debug = true;
					g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					if(ToolPanel.rb1.isSelected()){
						if(regular == true){//線引き
							curvetrans[pcnt] = PaintTool.getsize5();
							curvecolor[pcnt] = PaintTool.getColor();
							curveispattern[pcnt] = FillFrame.r2.isSelected();
							curvesize[pcnt] = PaintTool.getsize4();
							curveside[pcnt] = PaintTool.p15.getborder();
							curvepattern[pcnt] = PaintTool.p15.getpattern();
							x3[pcnt] = x1;
							y3[pcnt] = y1;
							x4[pcnt] = x2;
							y4[pcnt] = y2;
							if(pcnt < 99){
								pcnt++;
								ToolPanel.b8.setEnabled(true);
								ToolPanel.b9.setEnabled(true);
							}
							if(curvex == -1000){
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.drawLine(x3[i], y3[i], x4[i], y4[i]);
								}
							}else if(curvex2 == -1000){
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.draw(new QuadCurve2D.Float(x3[i], y3[i], curvex, curvey, x4[i], y4[i]));
								}
								g3.setStroke(new BasicStroke(1));
								g3.setColor(Color.black);
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
								g3.drawOval(curvex - 2,curvey - 2, 4, 4);
							}else{
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.draw(new CubicCurve2D.Float(x3[i], y3[i], curvex, curvey, curvex2, curvey2, x4[i], y4[i]));
								}
								g3.setStroke(new BasicStroke(1));
								g3.setColor(Color.black);
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
								g3.drawOval(curvex - 2,curvey - 2, 4, 4);
								g3.drawOval(curvex2 - 2,curvey2 - 2, 4, 4);
							}
						}else{ //線引きデバッグ
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
							setStroke(g3);
							if(curvex == -1000){
								if(move == 1)g3.drawLine(x1, y1, x2, y2);
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.drawLine(x3[i], y3[i], x4[i], y4[i]);
								}
							}else if(curvex2 == -1000){
								if(move == 1)g3.draw(new QuadCurve2D.Float(x1, y1, curvex, curvey, x2, y2));
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.draw(new QuadCurve2D.Float(x3[i], y3[i], curvex, curvey, x4[i], y4[i]));
								}
								g3.setStroke(new BasicStroke(1));
								g3.setColor(Color.black);
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
								g3.drawOval(curvex - 2,curvey - 2, 4, 4);
							}else{
								if(move == 1)g3.draw(new CubicCurve2D.Float(x1, y1, curvex, curvey, curvex2, curvey2, x2, y2));
								for(int i = 0 ; i < pcnt; i++){
									g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
									if(curvepattern[i][0] == 0){
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
									}else{
										g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
									}
									if(curveispattern[i]){
										g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
									}else{
										g3.setColor(curvecolor[i]);
									}
									g3.draw(new CubicCurve2D.Float(x3[i], y3[i], curvex, curvey, curvex2, curvey2, x4[i], y4[i]));
								}
								g3.setStroke(new BasicStroke(1));
								g3.setColor(Color.black);
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
								g3.drawOval(curvex - 2,curvey - 2, 4, 4);
								g3.drawOval(curvex2 - 2,curvey2 - 2, 4, 4);
							}
						}
					}else{
						if(ToolPanel.rb6.isSelected()){
							curvex = x1;
							curvey = y1;
						}else{
							curvex2 = x1;
							curvey2 = y1;
						}
						if(curvex == -1000){
							for(int i = 0 ; i < pcnt; i++){
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
								if(curvepattern[i][0] == 0){
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
								}else{
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
								}
								if(curveispattern[i]){
									g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
								}else{
									g3.setColor(curvecolor[i]);
								}
								g3.drawLine(x3[i], y3[i], x4[i], y4[i]);
							}
						}else if(curvex2 == -1000){
							for(int i = 0 ; i < pcnt; i++){
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
								if(curvepattern[i][0] == 0){
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
								}else{
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
								}
								if(curveispattern[i]){
									g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
								}else{
									g3.setColor(curvecolor[i]);
								}
								g3.draw(new QuadCurve2D.Float(x3[i], y3[i], curvex, curvey, x4[i], y4[i]));
							}
							g3.setStroke(new BasicStroke(1));
							g3.setColor(Color.black);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
							g3.drawOval(curvex - 2,curvey - 2, 4, 4);
						}else{
							for(int i = 0 ; i < pcnt; i++){
								g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
								if(curvepattern[i][0] == 0){
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
								}else{
									g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
								}
								if(curveispattern[i]){
									g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
								}else{
									g3.setColor(curvecolor[i]);
								}
								g3.draw(new CubicCurve2D.Float(x3[i], y3[i], curvex, curvey, curvex2, curvey2, x4[i], y4[i]));
							}
							g3.setStroke(new BasicStroke(1));
							g3.setColor(Color.black);
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
							g3.drawOval(curvex - 2,curvey - 2, 4, 4);
							g3.drawOval(curvex2 - 2,curvey2 - 2, 4, 4);
						}
					}
				}else if(PaintTool.getComand() == 15){
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					if(curvex == -1000){
						for(int i = 0 ; i < pcnt; i++){
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g2.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g2.setColor(curvecolor[i]);
							}
							g2.drawLine(x3[i], y3[i], x4[i], y4[i]);
						}
					}else if(curvex2 == -1000){
						for(int i = 0 ; i < pcnt; i++){
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g2.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g2.setColor(curvecolor[i]);
							}
							g2.draw(new QuadCurve2D.Float(x3[i], y3[i], curvex, curvey, x4[i], y4[i]));
						}
					}else{
						for(int i = 0 ; i < pcnt; i++){
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g2.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g2.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g2.setColor(curvecolor[i]);
							}
							g2.draw(new CubicCurve2D.Float(x3[i], y3[i], curvex, curvey, curvex2, curvey2, x4[i], y4[i]));
						}
					}
					pcnt = 0;
					curvex = -1000;
					curvey = -1000;
					curvex2 = -1000;
					curvey2 = -1000;
				}else{
					g3.drawImage(img[imgnum], 0, 0, this);
					debug = true;
					g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					if(curvex == -1000){
						for(int i = 0 ; i < pcnt; i++){
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g3.setColor(curvecolor[i]);
							}
							g3.drawLine(x3[i], y3[i], x4[i], y4[i]);
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}else if(curvex2 == -1000){
						for(int i = 0 ; i < pcnt; i++){
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g3.setColor(curvecolor[i]);
							}
							g3.draw(new QuadCurve2D.Float(x3[i], y3[i], curvex, curvey, x4[i], y4[i]));
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						g3.setStroke(new BasicStroke(1));
						g3.setColor(Color.black);
						g3.drawOval(curvex - 2,curvey - 2, 4, 4);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}else{
						for(int i = 0 ; i < pcnt; i++){
							g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, curvetrans[i]));
							if(curvepattern[i][0] == 0){
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER));
							}else{
								g3.setStroke(new BasicStroke(curvesize[i], curveside[i], BasicStroke.JOIN_MITER, 1.0f, curvepattern[i], 0.0f));
							}
							if(curveispattern[i]){
								g3.setPaint(new TexturePaint(FillFrame.img, new Rectangle2D.Double(x1, y1, FillFrame.img.getWidth(), FillFrame.img.getHeight())));
							}else{
								g3.setColor(curvecolor[i]);
							}
							g3.draw(new CubicCurve2D.Float(x3[i], y3[i], curvex, curvey, curvex2, curvey2, x4[i], y4[i]));
						}
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
						g3.setStroke(new BasicStroke(1));
						g3.setColor(Color.black);
						g3.drawOval(curvex - 2,curvey - 2, 4, 4);
						g3.drawOval(curvex2 - 2,curvey2 - 2, 4, 4);
						g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
					}
				}
				if(pcnt != 0){
					ToolPanel.rb2.setEnabled(true);
					ToolPanel.rb6.setEnabled(true);
				} else{
					ToolPanel.rb2.setEnabled(false);
					ToolPanel.rb6.setEnabled(false);
					ToolPanel.rb1.setSelected(true);
				}
				if(curvex != -1000){
					ToolPanel.rb7.setEnabled(true);
				} else{
					ToolPanel.rb7.setEnabled(false);
					ToolPanel.rb6.setSelected(true);
				}
			}else if (PaintTool.type == 21) {// 塗り潰し
				if(regular){
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, PaintTool.getsize5()));
					int r1, gr1, b1;
					b1 = img[imgnum].getRGB(x1, y1) & 0xff;
					gr1 = (img[imgnum].getRGB(x1, y1) / 256) - 1 & 0xff;
					if (b1 == 0)
						gr1 = (gr1 + 1) % 256;
					r1 = (img[imgnum].getRGB(x1, y1) / 256 / 256) - 1 & 0xff;
					if (b1 == 0 && gr1 == 0)
						r1 = (r1 + 1) % 256;
					boolean[][] map;
					map = new boolean[PaintTool.sizex][PaintTool.sizey];
					map[x1][y1] = true;
					g2.drawLine(x1, y1, x1, y1);
					LinkedList<int[]> array = new LinkedList<int[]>();
					array.offer(new int[]{x1, y1});
					int[] w = new int[2];
					while((w = array.poll()) != null){//map作成
						if(w[0]+1 < PaintTool.sizex){
							if(map[w[0]+1][w[1]] == false){
								int r, gr, b;
								b = img[imgnum].getRGB(w[0]+1, w[1]) & 0xff;
								gr = (img[imgnum].getRGB(w[0]+1, w[1]) / 256) - 1 & 0xff;
								if (b == 0) gr = (gr + 1) % 256;
								r = (img[imgnum].getRGB(w[0]+1, w[1]) / 256 / 256) - 1 & 0xff;
								if (b == 0 && gr == 0) r = (r + 1) % 256;
								if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= PaintTool.getsize2f()){
									map[w[0]+1][w[1]] = true;
									array.offer(new int[]{w[0]+1, w[1]});
									g2.drawLine(w[0]+1, w[1], w[0]+1, w[1]);
								}
							}
						}
						if(w[0]-1 >= 0){
							if(map[w[0]-1][w[1]] == false){
								int r, gr, b;
								b = img[imgnum].getRGB(w[0]-1, w[1]) & 0xff;
								gr = (img[imgnum].getRGB(w[0]-1, w[1]) / 256) - 1 & 0xff;
								if (b == 0) gr = (gr + 1) % 256;
								r = (img[imgnum].getRGB(w[0]-1, w[1]) / 256 / 256) - 1 & 0xff;
								if (b == 0 && gr == 0) r = (r + 1) % 256;
								if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= PaintTool.getsize2f()){
									map[w[0]-1][w[1]] = true;
									array.offer(new int[]{w[0]-1, w[1]});
									g2.drawLine(w[0]-1, w[1], w[0]-1, w[1]);
								}
							}
						}
						if(w[1]+1 < PaintTool.sizey){
							if(map[w[0]][w[1]+1] == false){
								int r, gr, b;
								b = img[imgnum].getRGB(w[0], w[1]+1) & 0xff;
								gr = (img[imgnum].getRGB(w[0], w[1]+1) / 256) - 1 & 0xff;
								if (b == 0) gr = (gr + 1) % 256;
								r = (img[imgnum].getRGB(w[0], w[1]+1) / 256 / 256) - 1 & 0xff;
								if (b == 0 && gr == 0) r = (r + 1) % 256;
								if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= PaintTool.getsize2f()){
									map[w[0]][w[1]+1] = true;
									array.offer(new int[]{w[0], w[1]+1});
									g2.drawLine(w[0], w[1]+1, w[0], w[1]+1);
								}
							}
						}
						if(w[1]-1 >= 0){
							if(map[w[0]][w[1]-1] == false){
								int r, gr, b;
								b = img[imgnum].getRGB(w[0], w[1]-1) & 0xff;
								gr = (img[imgnum].getRGB(w[0], w[1]-1) / 256) - 1 & 0xff;
								if (b == 0) gr = (gr + 1) % 256;
								r = (img[imgnum].getRGB(w[0], w[1]-1) / 256 / 256) - 1 & 0xff;
								if (b == 0 && gr == 0) r = (r + 1) % 256;
								if((Math.abs(r1-r) + Math.abs(gr1-gr) + Math.abs(b1-b)) <= PaintTool.getsize2f()){
									map[w[0]][w[1]-1] = true;
									array.offer(new int[]{w[0], w[1]-1});
									g2.drawLine(w[0], w[1]-1, w[0], w[1]-1);
								}
							}
						}
					}
				}
			}
			if ((PaintTool.type >= 0 && PaintTool.type <= 4 || PaintTool.type == 17 || PaintTool.type == 18) && debug == true || (PaintTool.type == 6 || PaintTool.type == 7) && debug == false) {
				g3.setColor(new Color(238, 238, 238));
				g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g3.fillRect(PaintTool.sizex, 0, 2000, 2000);
				g3.fillRect(0, PaintTool.sizey, 2000, 2000);
				g3.setColor(Color.black);
				g3.setStroke(new BasicStroke(1));
				g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
				g3.drawLine((int)Math.round(PaintTool.sizex*PaintTool.scale), 0, (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.drawLine(0, (int)Math.round(PaintTool.sizey*PaintTool.scale), (int)Math.round(PaintTool.sizex*PaintTool.scale), (int)Math.round(PaintTool.sizey*PaintTool.scale));
				g3.scale(PaintTool.scale, PaintTool.scale);
			}
			if (debug == false && PaintTool.type != 12 || PaintTool.getComand() == 1 ||PaintTool.getComand() == 7 || PaintTool.getComand() == 8) {
				g3.drawImage(img[imgnum], 0, 0, this);
			}else if(PaintTool.type == 12 && (PaintTool.getComand() == 4 || PaintTool.getComand() == 6 || PaintTool.getComand() == 21)) {//それ以外の時
				g3.drawImage(img[imgnum], 0, 0, this);
				if(set){
					Area are = new Area(shap);
					AffineTransform tran = new AffineTransform();
					tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
					are.transform(tran);
					g3.setClip(are);
					g3.scale((float)PaintTool.ratiox/(float)100, (float)PaintTool.ratioy/(float)100);
					if(!(SelectOptionFrame.rb7.isSelected())){
						g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
					}
					g3.setClip(null);
					g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
					g3.setColor(Color.red);
					if(clock && clockswitch){
						float[] dash = { 5.0f, 5.0f};
						g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
					}else{
						float[] dash = { 5.0f, 5.0f};
						g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
					}
					Area are1 = new Area(shap);
					AffineTransform af = new AffineTransform();
					af.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
					are1.transform(af);
					g3.draw((Shape)are1);
					g3.scale(1, 1);
				}
			}

		}
		if(set &&( PaintTool.type != 12|| (PaintTool.type == 12 && (PaintTool.getComand() == 2 || PaintTool.getComand() == 3 || PaintTool.getComand() == 5)))){
			Area are = new Area(shap);
			AffineTransform tran = new AffineTransform();
			tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
			are.transform(tran);
			g3.setClip(are);
			if(PaintTool.type == 12){
				if(!(SelectOptionFrame.rb7.isSelected())){
					g3.drawImage(imgin,pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2), this);
				}
			}
			g3.setClip(null);
			g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
			g3.setColor(Color.red);
			if(clock && clockswitch){
				float[] dash = { 5.0f, 5.0f};
				g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 5.0f));
			}else{
				float[] dash = { 5.0f, 5.0f};
				g3.setStroke(new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f, dash, 0.0f));
			}
			Area are1 = new Area(shap);
			AffineTransform af = new AffineTransform();
			af.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
			are1.transform(af);
			g3.draw((Shape)are1);
			g3.scale(1, 1);
		}
		
		if(listname.equals("選択範囲作成") || listname.equals("選択範囲変更") || listname.equals("選択範囲反転")){
			timer2.schedule(new TimerTask() {; public void run() {
				repaint();
			}}, 5);
		}else if(shap != null){
			shaps[imgnum] = new Area(shap);
		}
		if(ComandPanel.tmpx != 0){
			PaintTool.bx.setValue((int)Math.round(PaintTool.bx.getMaximum()*(Double.parseDouble(PaintTool.p5.t1.getText())/PaintTool.scale) * ComandPanel.tmpx - PaintTool.bx.getVisibleAmount()/2));
			PaintTool.by.setValue((int)Math.round(PaintTool.by.getMaximum()*(Double.parseDouble(PaintTool.p5.t1.getText())/PaintTool.scale) * ComandPanel.tmpy - PaintTool.by.getVisibleAmount()/2));
			ComandPanel.tmpx = 0;
			ComandPanel.tmpy = 0;
		}
		
		if(RulerFrame.cb.isSelected()){//グリッド線表示
			g3.setColor(Color.black);
			g3.setStroke(new BasicStroke(1.0f));
			g3.scale(1/PaintTool.scale, 1/PaintTool.scale);
			for(float i = (float) (PaintTool.p14.getsize()*PaintTool.p14.getsize3()/100*PaintTool.scale);i < PaintTool.sizex*PaintTool.scale;i += PaintTool.p14.getsize() * PaintTool.scale){
				g3.drawLine(Math.round(i), 0, Math.round(i), (int)Math.round(PaintTool.sizey*PaintTool.scale));
			}
			for(float i = (float) (PaintTool.p14.getsize2()*PaintTool.p14.getsize4()/100*PaintTool.scale);i < PaintTool.sizey*PaintTool.scale;i += PaintTool.p14.getsize2() * PaintTool.scale){
				g3.drawLine(0, Math.round(i), (int)Math.round(PaintTool.sizex*PaintTool.scale), Math.round(i));
			}
			g3.scale(PaintTool.scale, PaintTool.scale);
			
		}
		PaintTool.copym.setEnabled(set);
		ToolPanel.b1.setEnabled(set);
		ToolPanel.b2.setEnabled(set);
		SelectOptionFrame.b.setEnabled(set);
		PaintTool.resetComand();
		PaintTool.drawcation(false);
		PaintTool.p11.repaint();
		PaintTool.p3.setPreferredSize(new Dimension((int)Math.round(PaintTool.scale*PaintTool.sizex), (int)Math.round(PaintTool.scale*PaintTool.sizey)));
		PaintTool.menubar.repaint();
		//PaintTool.scrollpane.repaint();
		PaintTool.bx.repaint();
		PaintTool.by.repaint();
		debug = false;
		regular = false;
		clock = false;
	}

	/* ★★動作系★★ */
	public void mousePressed(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左
			press = true;
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			
			if (PaintTool.type <= 4 || PaintTool.type == 17 || PaintTool.type == 18) {
				PaintTool.p5.l8.setText("w");
				PaintTool.p5.l9.setText("0");
				PaintTool.p5.l10.setText("h");
				PaintTool.p5.l11.setText("0");
			}else if (PaintTool.type == 6 || PaintTool.type == 7) {
				x3[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y3[pcnt] = (int)(((double)e.getY())/PaintTool.scale);
				repaint();
			} else if (PaintTool.type == 9) {
				capt = true;
				listname = "文字";
				regular = true;
				repaint();
			} else if (PaintTool.type == 5 || PaintTool.type == 8 || PaintTool.type == 13 || PaintTool.type == 14 || PaintTool.type == 15 || PaintTool.type == 25) {
				capt = true;
				if (PaintTool.type == 5){
					listname = "線";
					oldx = x1;
					oldy = y1;
				}else if (PaintTool.type == 8){
					listname = "スプレー";
					timer3 = new Timer();
					timer3.schedule(new TimerTask() {; public void run() {
						regular = true;
						repaint();
					}}, 1, 50);
				}else if (PaintTool.type == 13){
					listname = "オリジナルライン";
				}else if (PaintTool.type == 14){
					listname = (String)ToolPanel.cbf.getSelectedItem();
				}else if (PaintTool.type == 15){
					listname = "輝度";
				}else if (PaintTool.type == 25){
					listname = "RGBフィルター";
				}
				originalrotate = 0;
				regular = true;
				repaint();
			} else if (PaintTool.type == 11) {
				if (PaintTool.sizex > x1 && PaintTool.sizey > y1) {
					int r, g, b;
					b = img[imgnum].getRGB(x1, y1) & 0xff;
					g = (img[imgnum].getRGB(x1, y1) / 256) - 1 & 0xff;
					if (b == 0)
						g = (g + 1) % 256;
					r = (img[imgnum].getRGB(x1, y1) / 256 / 256) - 1 & 0xff;
					if (b == 0 && g == 0)
						r = (r + 1) % 256;
					PaintTool.setColor(new Color(r, g, b));
					ColorPanel.t1.setText(String.valueOf(PaintTool.getColor()
							.getRed()));
					ColorPanel.t2.setText(String.valueOf(PaintTool.getColor()
							.getGreen()));
					ColorPanel.t3.setText(String.valueOf(PaintTool.getColor()
							.getBlue()));
					ColorPanel.s1.setValue(PaintTool.getColor().getRed());
					ColorPanel.s2.setValue(PaintTool.getColor().getGreen());
					ColorPanel.s3.setValue(PaintTool.getColor().getBlue());
					ColorPanel.cb[PaintTool.colornum].setBackground(PaintTool
							.getColor());
				}
			} else if (PaintTool.type == 12) {
				if(set == true && ToolPanel.rb3.isSelected()){
					Area are = new Area(shap);
					AffineTransform tran = new AffineTransform();
					tran.translate(pointrotatex((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().x, pointrotatey((shap.getBounds().x + (int)PaintTool.shx*(ssety1-sety1))*100/PaintTool.ratiox, (shap.getBounds().y + (int)PaintTool.shy*(ssetx1-setx1))*100/PaintTool.ratioy,PaintTool.spin*Math.PI/180, rotatex2, rotatey2) - shap.getBounds().y);
					are.transform(tran);
					if(are.contains(x1, y1)){//枠範囲内の時
						move = 1;
						if(dontfill == true && SelectOptionFrame.rb5.isSelected()){
							capt = true;
							listname = "選択範囲白塗";
						}
					} else{//枠範囲外の時
						//場所確定
						capt = true;
						listname = "選択範囲開放";
						PaintTool.comand = 13;
						reaverelease = true;
						rewrite();
					}
				}else{
					if(SelectOptionFrame.rb8.isSelected()){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
					}else if(SelectOptionFrame.rb9.isSelected()){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2] >= PaintTool.sizex){
							x4[pcnt2] = PaintTool.sizex;
						}
						if(x4[pcnt2] < 0){
							x4[pcnt2] = 0;
						}
						if(y4[pcnt2] >= PaintTool.sizey){
							y4[pcnt2] = PaintTool.sizey;
						}
						if(y4[pcnt2] < 0){
							y4[pcnt2] = 0;
						}
						repaint();
					}else if(SelectOptionFrame.rb10.isSelected()){
						if( (((double)e.getX())/PaintTool.scale) < PaintTool.sizex && (((double)e.getY())/PaintTool.scale) < PaintTool.sizey){
							PaintTool.drawcation(true);
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {public void run() {
								debug = false;
								rectcapt = true;
								PaintTool.rewrite();
							}}
							, 1);
						}
					}else{
						PaintTool.p5.l8.setText("w");
						PaintTool.p5.l9.setText("0");
						PaintTool.p5.l10.setText("h");
						PaintTool.p5.l11.setText("0");
					}
				}
			}else if (PaintTool.type == 23 || PaintTool.type == 24) {
				x4[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y4[pcnt++] = (int)(((double)e.getY())/PaintTool.scale);
				if(x4[pcnt-1] >= PaintTool.sizex){
					x4[pcnt-1] = PaintTool.sizex;
				}
				if(x4[pcnt-1] < 0){
					x4[pcnt-1] = 0;
				}
				if(y4[pcnt-1] >= PaintTool.sizey){
					y4[pcnt-1] = PaintTool.sizey;
				}
				if(y4[pcnt-1] < 0){
					y4[pcnt-1] = 0;
				}
				debug = true;
				repaint();
			}else if (PaintTool.type == 19) {
				if(stampset == true){
					regular = true;
					capt = true;
					listname = "コピースタンプ";
					repaint();
				}else{
					if(ToolPanel.rb5.isSelected()){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
					}
				}
			} else if (PaintTool.type == 21) {
				if( (((double)e.getX())/PaintTool.scale) < PaintTool.sizex && (((double)e.getY())/PaintTool.scale) < PaintTool.sizey){
					PaintTool.drawcation(true);
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {public void run() {
						capt = true;
						listname = "塗り潰し";
						regular = true;
						PaintTool.rewrite();
					}}
					, 1);
				}
			} else if (PaintTool.type == 22) {
				if(stampset){
					regular = true;
					capt = true;
					listname = "コピーブラシ";
					repaint();
				}else{
					rectcapt = true;
					repaint();
				}
			}
		}else if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0 && PaintTool.type != 12 && PaintTool.type != 14 && PaintTool.type != 15 && PaintTool.type != 16 && PaintTool.type != 19 && PaintTool.type != 22) {
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			if (PaintTool.sizex > x1 && PaintTool.sizey > y1) {
				int r, g, b;
				b = img[imgnum].getRGB(x1, y1) & 0xff;
				g = (img[imgnum].getRGB(x1, y1) / 256) - 1 & 0xff;
				if (b == 0)
					g = (g + 1) % 256;
				r = (img[imgnum].getRGB(x1, y1) / 256 / 256) - 1 & 0xff;
				if (b == 0 && g == 0)
					r = (r + 1) % 256;
				PaintTool.setColor(new Color(r, g, b));
				ColorPanel.t1.setText(String.valueOf(PaintTool.getColor()
						.getRed()));
				ColorPanel.t2.setText(String.valueOf(PaintTool.getColor()
						.getGreen()));
				ColorPanel.t3.setText(String.valueOf(PaintTool.getColor()
						.getBlue()));
				ColorPanel.s1.setValue(PaintTool.getColor().getRed());
				ColorPanel.s2.setValue(PaintTool.getColor().getGreen());
				ColorPanel.s3.setValue(PaintTool.getColor().getBlue());
				ColorPanel.cb[PaintTool.colornum].setBackground(PaintTool
						.getColor());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左
			press = false;
			x2 = (int)(((double)e.getX())/PaintTool.scale);
			y2 = (int)(((double)e.getY())/PaintTool.scale);
			if(shap != null){
				shaps[imgnum] = new Area(shap);
			}
			if (PaintTool.type == 6 || PaintTool.type == 7) {
				x3[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y3[pcnt++] = (int)(((double)e.getY())/PaintTool.scale);
				if (pcnt >= PaintTool.getsize() || pcnt == 100) {
					capt = true;
					if (PaintTool.type == 6){
						listname = "多角形";
					}else{
						listname = "多角形(中塗)";
					}
					ToolPanel.b9.setEnabled(false);
					regular = true;
					repaint();
				} else {
					ToolPanel.b9.setEnabled(true);
					debug = true;
					repaint();
				}
			} else if ((PaintTool.type >= 0 && PaintTool.type <= 4) || PaintTool.type == 18) {
					capt = true;
					if(PaintTool.type == 0){
						listname = "直線";
					}else if(PaintTool.type == 1){
						listname = "長方形";
					}else if(PaintTool.type == 2){
						listname = "長方形(中塗)";
					}else if(PaintTool.type == 3){
						listname = "楕円";
					}else if(PaintTool.type == 4){
						listname = "楕円(中塗)";
					}
					debug = false;
					regular = true;
					PaintTool.p5.l8.setText("");
					PaintTool.p5.l9.setText("");
					PaintTool.p5.l10.setText("");
					PaintTool.p5.l11.setText("");
					repaint();
			}else if(PaintTool.type == 17){
				if(ToolPanel.rb1.isSelected()){
					PaintTool.drawcation(true);
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {public void run() {
						capt = true;
						listname = "グラデーション";
						debug = false;
						regular = true;
						PaintTool.p5.l8.setText("");
						PaintTool.p5.l9.setText("");
						PaintTool.p5.l10.setText("");
						PaintTool.p5.l11.setText("");
						PaintTool.rewrite();
					}}
					, 1);
				}else{
					centerx = x2;
					centery = y2;
					debug = true;
					PaintTool.rewrite();
				}
			}else if (PaintTool.type == 20) {
				move = 0;
				if(ToolPanel.rb2.isSelected()){
					x1 = (int)(((double)e.getX())/PaintTool.scale);
					y1 = (int)(((double)e.getY())/PaintTool.scale);
				}
				debug = true;
				regular = true;
				repaint();
			} else if (PaintTool.type == 5 || PaintTool.type == 13 || PaintTool.type == 14 || PaintTool.type == 15 || PaintTool.type == 25) {
				x1 = (int)(((double)e.getX())/PaintTool.scale);
				y1 = (int)(((double)e.getY())/PaintTool.scale);
				if(PaintTool.type == 5)distancecount = 0;
				regular = true;
				repaint();
			} else if (PaintTool.type == 8){
				x1 = (int)(((double)e.getX())/PaintTool.scale);
				y1 = (int)(((double)e.getY())/PaintTool.scale);
				regular = true;
				timer3.cancel();
				repaint();
			} else if (PaintTool.type == 12) {
				if((set == false || !(ToolPanel.rb3.isSelected()) ) && !(SelectOptionFrame.rb9.isSelected()) && !(SelectOptionFrame.rb10.isSelected())){//枠作成
					if(SelectOptionFrame.rb8.isSelected()){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
					}
					if(x2 >= PaintTool.sizex){//はみ出た枠調節
						x2 = PaintTool.sizex;
					}
					if(x2 < 0){
						x2 = 0;
					}
					if(x1 >= PaintTool.sizex){
						x1 = PaintTool.sizex;
					}
					if(x1 < 0){
						x1 = 0;
					}
					if(y2 >= PaintTool.sizey){
						y2 = PaintTool.sizey;
					}
					if(y2 < 0){
						y2 = 0;
					}
					if(y1 >= PaintTool.sizey){
						y1 = PaintTool.sizey;
					}
					if(y1 < 0){
						y1 = 0;
					}
					if(x2 != x1 && y2 != y1){
						debug = false;
						rectcapt = true;
						//capt = true;
						//listname = "選択領域作成";
						repaint();
					} else{//無効な枠
						pcnt2 = 0;
						PaintTool.comand = 5;
						repaint();
					}
					PaintTool.p5.l8.setText("");
					PaintTool.p5.l9.setText("");
					PaintTool.p5.l10.setText("");
					PaintTool.p5.l11.setText("");
				} else if(  (SelectOptionFrame.rb9.isSelected() || SelectOptionFrame.rb10.isSelected()) && move == 0){
					if(SelectOptionFrame.rb9.isSelected() && reaverelease == false){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2] = (int)(((double)e.getY())/PaintTool.scale);
						if(pcnt2 < 99)pcnt2++;
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
						if(pcnt2 >= SelectOptionFrame.getpoly()){
							debug = false;
							rectcapt = true;
							ToolPanel.b9.setEnabled(false);
						} else{
							debug = true;
							ToolPanel.b9.setEnabled(true);
						}
						repaint();
					}
				} else{
					setx1 += (x2-x1);
					setx2 += (x2-x1);
					sety1 += (y2-y1);
					sety2 += (y2-y1);
					Area are = new Area(shap);
					AffineTransform af = new AffineTransform();
					af.translate(x2-x1, y2-y1);
					are.transform(af);
					shap = (Shape)are;
					move = 2;
				}
				reaverelease = false;
			}else if (PaintTool.type == 23 || PaintTool.type == 24) {
				x4[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y4[pcnt++] = (int)(((double)e.getY())/PaintTool.scale);
				if(x4[pcnt-1] >= PaintTool.sizex){
					x4[pcnt-1] = PaintTool.sizex;
				}
				if(x4[pcnt-1] < 0){
					x4[pcnt-1] = 0;
				}
				if(y4[pcnt-1] >= PaintTool.sizey){
					y4[pcnt-1] = PaintTool.sizey;
				}
				if(y4[pcnt-1] < 0){
					y4[pcnt-1] = 0;
				}
				debug = false;
				capt = true;
				regular = true;
				repaint();
			}else if (PaintTool.type == 19) {
				if(stampset == false){//枠作成
					if(ToolPanel.rb5.isSelected()){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
					}
					if(!ToolPanel.rb3.isSelected()){
						if(x2 >= PaintTool.sizex){//はみ出た枠調節
							x2 = PaintTool.sizex;
						}
						if(x2 < 0){
							x2 = 0;
						}
						if(x1 >= PaintTool.sizex){
							x1 = PaintTool.sizex;
						}
						if(x1 < 0){
							x1 = 0;
						}
						if(y2 >= PaintTool.sizey){
							y2 = PaintTool.sizey;
						}
						if(y2 < 0){
							y2 = 0;
						}
						if(y1 >= PaintTool.sizey){
							y1 = PaintTool.sizey;
						}
						if(y1 < 0){
							y1 = 0;
						}
					}
					if(x2 != x1 && y2 != y1){
						debug = false;
						rectcapt = true;
						repaint();
					} else{//無効な枠
						pcnt2 = 0;
						PaintTool.comand = 5;
						repaint();
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			if(PaintTool.type == 12){
				x2 = (int)(((double)e.getX())/PaintTool.scale);
				y2 = (int)(((double)e.getY())/PaintTool.scale);
				PaintTool.showPopup(e);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		exit = false;
	}

	public void mouseExited(MouseEvent e) {
		if (PaintTool.type == 9 || PaintTool.type == 13 || PaintTool.type == 19 || PaintTool.type == 22) {
			exit = true;
			repaint();
		}
	}

	public void mouseDragged(MouseEvent e) {
		PaintTool.p5.l5.setText(String.valueOf((int)(((double)e.getX())/PaintTool.scale)));
		PaintTool.p5.l7.setText(String.valueOf((int)(((double)e.getY())/PaintTool.scale)));
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			//左
			if (PaintTool.type == 6 || PaintTool.type == 7) {
				x3[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y3[pcnt] = (int)(((double)e.getY())/PaintTool.scale);
				repaint();
			}else if (PaintTool.type == 5 || PaintTool.type == 13 || PaintTool.type == 14 || PaintTool.type == 15 || PaintTool.type == 25) {
				x1 = (int)(((double)e.getX())/PaintTool.scale);
				y1 = (int)(((double)e.getY())/PaintTool.scale);
				regular = true;
				repaint();
			}else if(PaintTool.type == 8){
				x1 = (int)(((double)e.getX())/PaintTool.scale);
				y1 = (int)(((double)e.getY())/PaintTool.scale);
				regular = true;
				repaint();
			}else if (PaintTool.type >= 0 && PaintTool.type <= 4 || PaintTool.type == 18) {
				x2 = (int)(((double)e.getX())/PaintTool.scale);
				y2 = (int)(((double)e.getY())/PaintTool.scale);
				debug = true;
				PaintTool.p5.l9.setText(String.valueOf((int)(((double)e.getX())/PaintTool.scale) - x1));
				PaintTool.p5.l11.setText(String.valueOf((int)(((double)e.getY())/PaintTool.scale) - y1));
				repaint();
			}else if (PaintTool.type == 17) {
				if(ToolPanel.rb1.isSelected()){
					x2 = (int)(((double)e.getX())/PaintTool.scale);
					y2 = (int)(((double)e.getY())/PaintTool.scale);
					
				}else{
					centerx = (int)(((double)e.getX())/PaintTool.scale);
					centery = (int)(((double)e.getY())/PaintTool.scale);
				}
				debug = true;
				PaintTool.p5.l9.setText(String.valueOf((int)(((double)e.getX())/PaintTool.scale) - x1));
				PaintTool.p5.l11.setText(String.valueOf((int)(((double)e.getY())/PaintTool.scale) - y1));
				repaint();
			}else if (PaintTool.type == 20) {
				move = 1;
				x2 = (int)(((double)e.getX())/PaintTool.scale);
				y2 = (int)(((double)e.getY())/PaintTool.scale);
				if(ToolPanel.rb2.isSelected()){
					x1 = (int)(((double)e.getX())/PaintTool.scale);
					y1 = (int)(((double)e.getY())/PaintTool.scale);
				}
				debug = true;
				repaint();
			}else if (PaintTool.type == 12) {
				x2 = (int)(((double)e.getX())/PaintTool.scale);
				y2 = (int)(((double)e.getY())/PaintTool.scale);
				if(SelectOptionFrame.rb8.isSelected()){
					if(set == false || !(ToolPanel.rb3.isSelected())){
						x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
						y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
						if(x4[pcnt2-1] >= PaintTool.sizex){
							x4[pcnt2-1] = PaintTool.sizex;
						}
						if(x4[pcnt2-1] < 0){
							x4[pcnt2-1] = 0;
						}
						if(y4[pcnt2-1] >= PaintTool.sizey){
							y4[pcnt2-1] = PaintTool.sizey;
						}
						if(y4[pcnt2-1] < 0){
							y4[pcnt2-1] = 0;
						}
					}
				}else if(SelectOptionFrame.rb9.isSelected() && move == 0){
					x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
					y4[pcnt2] = (int)(((double)e.getY())/PaintTool.scale);
					if(x4[pcnt2] >= PaintTool.sizex){
						x4[pcnt2] = PaintTool.sizex;
					}
					if(x4[pcnt2] < 0){
						x4[pcnt2] = 0;
					}
					if(y4[pcnt2] >= PaintTool.sizey){
						y4[pcnt2] = PaintTool.sizey;
					}
					if(y4[pcnt2] < 0){
						y4[pcnt2] = 0;
					}
					repaint();
				}
				if(SelectOptionFrame.rb1.isSelected() || SelectOptionFrame.rb2.isSelected()){
					PaintTool.p5.l9.setText(String.valueOf((int)(((double)e.getX())/PaintTool.scale) - x1));
					PaintTool.p5.l11.setText(String.valueOf((int)(((double)e.getY())/PaintTool.scale) - y1));
				}
				if(!(SelectOptionFrame.rb9.isSelected() || SelectOptionFrame.rb10.isSelected()) || move == 1){
					debug = true;
					repaint();
				}
			}else if (PaintTool.type == 23 || PaintTool.type == 24) {
				x4[pcnt] = (int)(((double)e.getX())/PaintTool.scale);
				y4[pcnt++] = (int)(((double)e.getY())/PaintTool.scale);
				if(x4[pcnt-1] >= PaintTool.sizex){
					x4[pcnt-1] = PaintTool.sizex;
				}
				if(x4[pcnt-1] < 0){
					x4[pcnt-1] = 0;
				}
				if(y4[pcnt-1] >= PaintTool.sizey){
					y4[pcnt-1] = PaintTool.sizey;
				}
				if(y4[pcnt-1] < 0){
					y4[pcnt-1] = 0;
				}
				debug = true;
				repaint();
			}else if (PaintTool.type == 22) {
				if(stampset = true){
					x1 = (int)(((double)e.getX())/PaintTool.scale);
					y1 = (int)(((double)e.getY())/PaintTool.scale);
					regular = true;
					repaint();
				}
			}else if (PaintTool.type == 19) {
				x2 = (int)(((double)e.getX())/PaintTool.scale);
				y2 = (int)(((double)e.getY())/PaintTool.scale);
				if(stampset == false){
					if(ToolPanel.rb5.isSelected()){
						if(stampset == false){
							x4[pcnt2] = (int)(((double)e.getX())/PaintTool.scale);
							y4[pcnt2++] = (int)(((double)e.getY())/PaintTool.scale);
							if(x4[pcnt2-1] >= PaintTool.sizex){
								x4[pcnt2-1] = PaintTool.sizex;
							}
							if(x4[pcnt2-1] < 0){
								x4[pcnt2-1] = 0;
							}
							if(y4[pcnt2-1] >= PaintTool.sizey){
								y4[pcnt2-1] = PaintTool.sizey;
							}
							if(y4[pcnt2-1] < 0){
								y4[pcnt2-1] = 0;
							}
						}
					}
					debug = true;
					regular = true;
					repaint();
				}else{
					x1 = (int)(((double)e.getX())/PaintTool.scale);
					y1 = (int)(((double)e.getY())/PaintTool.scale);
					regular = true;
					repaint();
				}
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		PaintTool.p5.l5.setText(String.valueOf((int)(((double)e.getX())/PaintTool.scale)));
		PaintTool.p5.l7.setText(String.valueOf((int)(((double)e.getY())/PaintTool.scale)));
		if (PaintTool.type == 5 || PaintTool.type == 10 || PaintTool.type == 13) {
			oldx = -1000;
			oldy = -1000;
		}
		if (PaintTool.type == 9) {
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			debug = true;
			repaint();
		}else if (PaintTool.type == 13) {
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			debug = true;
			repaint();
		}else if (PaintTool.type == 19) {
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			if(stampset == true){
				debug = true;
				repaint();
			}
		}else if (PaintTool.type == 22) {
			x1 = (int)(((double)e.getX())/PaintTool.scale);
			y1 = (int)(((double)e.getY())/PaintTool.scale);
			debug = true;
			repaint();
		}
	}
}
