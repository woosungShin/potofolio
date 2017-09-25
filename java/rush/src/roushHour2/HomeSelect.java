package roushHour2;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class HomeSelect extends JPanel{
	private ControlCenter ctrl;
	
	public HomeSelect(){
		
		setLayout(null);
		setBounds(0, 0, 800, 455);

		//스테이지 선택 아이콘에 관한 버튼 이벤트와 이벤트 처리 부분
		ImageIcon stage_select_icon = new ImageIcon(getClass().getResource("/img/home/stage_select_btn.png"));
		JButton goSelect = new JButton(stage_select_icon);
		add(goSelect);
		goSelect.setBounds(261, 266, 267, 53);
		goSelect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				setVisible(false); //스테이지 버튼을 누르면 현재 home이 보이지 않게 설정
				ctrl.viewSelect();//level선택 판넬 호출
			}
		});
		
		
		ImageIcon titleBackIcon = new ImageIcon(getClass().getResource("/img/home/titleBack.png"));
		JLabel titleBackLabel = new JLabel(titleBackIcon);
		add(titleBackLabel);
		titleBackLabel.setBounds(0, 0, titleBackIcon.getIconWidth(), titleBackIcon.getIconHeight());
	
	}
	
	
	public void setCtrl(ControlCenter ctrl){//콘트롤 센터의 관리를 하기 위해
		this.ctrl = ctrl;
	}
	
}
