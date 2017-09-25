package roushHour2;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LevelSelect extends JPanel implements ActionListener{
	private ControlCenter ctrl;
	private JButton stageButton[] = new JButton[6];

	/*생성자*/
	public LevelSelect(){
		
		setLayout(null);
		setBounds(0, 0, 800, 455);
		
		
		//레벨 번호 버튼 넣기
		for(int i = 0; i < 3; i++){
			ImageIcon icon = new ImageIcon(getClass().getResource("/img/levelScreen/no" + (i+1) +  ".png")); //?궧?깇?꺖?궦?겦?뒢?깭?궭?꺍
			stageButton[i] = new JButton(icon);
			add(stageButton[i]);//1~3까지 스테이지 번호 이미지 판넬에 붙이기 
			int x = 110 + 230 * (i % 3);
			int y = 200 + 150 * (i / 3);
			stageButton[i].setBounds(x, y, 120, 120);//각 스테이지 위치 설정
			
			//버튼을 눌렀을때 
			stageButton[i].setActionCommand(String.valueOf(i+1)); //
			stageButton[i].addActionListener(this);//이벤트가 발생하였을때 현재 객체의 이벤트 발생
		}
		
		ImageIcon LevelBrg = new ImageIcon(getClass().getResource("/img/levelScreen/stageSelectBack.png"));
		JLabel LevelSelectLbl = new JLabel(LevelBrg);
		add(LevelSelectLbl);
		LevelSelectLbl.setBounds(0, 0, LevelBrg.getIconWidth(), LevelBrg.getIconHeight()); //가져온 이미지의 너비와 높이로 설정을 한다
		
	
	}
	
	public void setCtrl(ControlCenter ctrl){
		this.ctrl = ctrl;
	}
	
	public void actionPerformed(ActionEvent ev){
		int num = Integer.parseInt(ev.getActionCommand());
		setVisible(false); //stageSelectPanel를 안보여주기
		switch(num){
		case 1:
			ctrl.viewPlay(num); //playpanel로 변경하여 보여주기 위한 메소드
			break;
		case 2:
			ctrl.viewPlay(num); //playpanel로 변경하여 보여주기 위한 메소드
			break;
		default:
			ctrl.viewPlay(num); //playpanel로 변경하여 보여주기 위한 메소드
			break;		
		}
		
		
	}


	
}
