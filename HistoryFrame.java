import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HistoryFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 2L;
	public static JList<String> ls;
	static String[] listname;

	/*★★入出力系★★*/
	void visible(){
		setVisible(true);
	}
	void invisible(){
		setVisible(false);
	}

	static void removeBackLs(int num){
		int i;
		for(i = 0;(i + num < PaintTool.HistoryNUM)? listname[i + num] != " " : false;i++){
			listname[i] = listname[i + num];
		}
		for(;i < PaintTool.HistoryNUM;i++){
			listname[i] = " ";
		}
	}

	static void removeForeLs(int num){
		for(int i = 0;(i < PaintTool.HistoryNUM) ? listname[i] != " " : false;i++){
			if((i + num < PaintTool.HistoryNUM) ? listname[i + num] == " " :true){
				listname[i] = " ";
			}
		}
	}

	/*★★コンストラクタ★★*/
	HistoryFrame() {

		setTitle("履歴");
		setSize(230, 205);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		listname = new String[PaintTool.HistoryNUM];
		ls = new JList<String>(listname);
		JScrollPane sp = new JScrollPane();
		sp.getViewport().setView(ls);
		sp.setPreferredSize(new Dimension(200,160));
		JPanel p = new JPanel();
		p.add(sp);
		c.add(p,BorderLayout.CENTER);
		for(int i = 0;i < PaintTool.HistoryNUM;i++){
			listname[i] = " ";
		}
		ls.ensureIndexIsVisible(0);
		HistoryFrame.listname[0] = "最初";
		HistoryFrame.ls.ensureIndexIsVisible(0);
		HistoryFrame.ls.setSelectedIndex(0);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if(listname[(ls.locationToIndex(e.getPoint()))] != " "){
						PaintTool.historyMove(ls.locationToIndex(e.getPoint()));
					}
				}
			}
		 };

		 addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					PaintTool.historym.setSelected(false);
				}

				public void windowIconified(WindowEvent e) {
				}

				public void windowOpened(WindowEvent e) {
				}
			});

		 ls.addMouseListener(mouseListener);
		 setResizable(false);
		 setVisible(false);
	}

	/* ★★動作系★★ */
	public void actionPerformed(ActionEvent e) {
	}
}
