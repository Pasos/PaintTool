import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

class MyAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	public MyAction(String s) {
		super(s);  //メニューに表示される名前
	}

	public void actionPerformed(ActionEvent e) {	//メニューが選択されると、このメソッドが呼ばれる
		if(e.getActionCommand() == "コピー"){
			PaintTool.setclipboad();
		}else if(e.getActionCommand() == "貼り付け"){
			PaintTool.getclipboad();
		}
	}
}