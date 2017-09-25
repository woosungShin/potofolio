package roushHour2;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

//여기에서 사용될 쓰레드는 클리어 판넬이 나오면 그 판넬에서 점수 보여주는 숫자들이 실시간으로 나오게 하기 위해서 사용

public class ClearScreen extends JPanel implements ActionListener{
	public ControlCenter ctrl;
	public CarBlocksInfo cbi; //카운트 값과 이벤트 사용을 위해서 들고옴
	public JPanel clearpnl;

	public JButton clearGoTitleButton, clearGoSelectButton, clearRestartButton, clearGoNextStageButton;
	public JLabel scoreNum[] = new JLabel[3];
	public JLabel clearBackLabel;

	
	public ClearScreen(CarBlocksInfo cbi){
		this.cbi = cbi;
		setLayout(null);
		setBounds(0, 0, 800, 455);
		//insertClearSet();
		//insertSocre();
	}
	
	public void setCtrl(ControlCenter ctrl){//콘트롤 센터의 관리를 하기 위해
		this.ctrl = ctrl;
	}
	

	/*클리어할때 나오는 버튼및 배경 설정*/
	public void insertClearSet(){
		
		//home으로 돌아가기 버튼
		ImageIcon clearedTitleIcn = new ImageIcon(ClearScreen.class.getResource("/img/clear/clearedTitle.png"));
		clearGoTitleButton = new JButton(clearedTitleIcn);
		clearGoTitleButton.setBounds(147,250, 170, 61);
		add(clearGoTitleButton);
		clearGoTitleButton.setActionCommand("0");
		clearGoTitleButton.addActionListener(this);

		//현재 레벨 다시 시작 버튼
		ImageIcon clearedRetryIcn = new ImageIcon(ClearScreen.class.getResource("/img/clear/clearedRetry.png"));
		clearRestartButton = new JButton(clearedRetryIcn);
		clearRestartButton.setBounds(484, 250, 170, 61);
		add(clearRestartButton);
		clearRestartButton.setActionCommand("1");
		clearRestartButton.addActionListener(this);

		//다음 레벨 넘어가기 버튼
		ImageIcon clearedNextIcn = new ImageIcon(ClearScreen.class.getResource("/img/clear/clearedNext.png"));
		clearGoNextStageButton = new JButton(clearedNextIcn);
		clearGoNextStageButton.setBounds(147, 301+35, 170, 61);
		add(clearGoNextStageButton);
		clearGoNextStageButton.setActionCommand("2");
		clearGoNextStageButton.addActionListener(this);

		//레벨 선택 화면을버튼
		ImageIcon clearedSelectIcn = new ImageIcon(ClearScreen.class.getResource("/img/clear/clearedSelect.png"));
		clearGoSelectButton = new JButton(clearedSelectIcn);
		clearGoSelectButton.setBounds(484, 301+35, 170, 61);
		add(clearGoSelectButton);
		clearGoSelectButton.setActionCommand("3");
		clearGoSelectButton.addActionListener(this);
		
		ImageIcon clearBackIcon = new ImageIcon(ClearScreen.class.getResource("/img/clear/clearBack.png"));
		clearBackLabel = new JLabel(clearBackIcon);
		clearBackLabel.setBounds(0, 0, 800, 455);
		//add(clearBackLabel);
		
		int x=350;
		//초기 점수 관련 라벨 배열을 선언을 한다.
				for(int i = 0; i < 3; i++){
					ImageIcon icn = new ImageIcon(ClearScreen.class.getResource("/img/clear/scoreNumbers/0.png"));
					scoreNum[i] = new JLabel(icn);
					scoreNum[i].setBounds(x, 155, icn.getIconWidth(), icn.getIconHeight());
					add(scoreNum[i]);
					x+= icn.getIconWidth();
				}
				add(clearBackLabel);
		
		
		
				
		
		
	}
	

	public void actionPerformed(ActionEvent e){
		setVisible(false);
		int command = Integer.parseInt(e.getActionCommand()); 
		//버튼 클릭시 위에서 해당 버튼에 값을 설정을 해준것이 넘어옴

		if(command == 0)
			ctrl.viewHome();
		
		else if(command == 1)
			ctrl.viewPlay(cbi.getLevelNumber());
		
		else if(command == 2){ //다음 스테이지
			
			if(cbi.getLevelNumber() < 6) //최대 6단계이기에 6보다 적은지 비교
				ctrl.viewPlay(cbi.getLevelNumber()+1);
			
			else 
				ctrl.viewSelect();
		}
	
		else if(command == 3) 
			ctrl.viewSelect();
		
		
	}
	public void changeNum(){
		ChangeNum num = new ChangeNum();
		num.start();

		
	}

	class ChangeNum extends Thread{
		public void run() {
			// TODO Auto-generated method stub
			
			int score = 500;
			System.out.println(cbi.getMoves());
			switch(cbi.getLevelNumber()){//각 레벨별 최소 클리어 조건 설정
			
			case 1:
				//System.out.println(cbi.getMoves());
				if(cbi.getMoves() > 5){
					score = score - (3 * cbi.getMoves());  //점수 설정
				}
				break;
			case 2:
				if(cbi.getMoves() > 8){
					score = score - (3 * cbi.getMoves());  //점수 설정
				}
				break;
			case 3:
				if(cbi.getMoves() > 12){
					score = score - (3 * cbi.getMoves());  //점수 설정
				}
				break;
			}
			
			if(score < 0)//점수가 음사가 나오면 무조건 0으로 설정 
				score = 0;
			
			ImageIcon chageIcn0,chageIcn1,chageIcn2;
			
			int num;
			int x;
			int imgNum = 0;
			int count = 0;
			int indexNum = 3;
			boolean flag = true;
			
			
			
			while(flag){
				try{
					
					x = 350;
					
					if(imgNum < 10){
							if(indexNum <=0){
								num = score/100;
								chageIcn0 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/" + num + ".png"));
							}else{
								chageIcn0 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/"+imgNum+".png"));
							}
							scoreNum[0].setIcon(chageIcn0);
							scoreNum[0].setBounds(x, 155, chageIcn0.getIconWidth(), chageIcn0.getIconHeight());
							
						//	add(scoreNum[0]);
							
							x+=70;
							if(indexNum <=1){
								num = (score%100)/10;
								chageIcn1 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/" + num + ".png"));
							}else{
								chageIcn1 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/"+imgNum+".png"));
							}
							scoreNum[1].setIcon(chageIcn1);
							scoreNum[1].setBounds(x, 155, chageIcn1.getIconWidth(), chageIcn1.getIconHeight());
						//	add(scoreNum[1]);
							
							x+=70;
							if(indexNum <=2){
								num = score%10;
								chageIcn2 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/" + num + ".png"));
							}else{
								chageIcn2 = new ImageIcon(getClass().getResource("/img/clear/scoreNumbers/"+imgNum+".png"));
							}
							scoreNum[2].setIcon(chageIcn2);
							scoreNum[2].setBounds(x, 155, chageIcn2.getIconWidth(), chageIcn2.getIconHeight());
						//	add(scoreNum[2]);
							
							if(imgNum == 9){
								imgNum=0;
							}
							
					}
					
					
					Thread.sleep(10);
					imgNum++;
					count++;
					
					if((count%30) == 0){
						indexNum--;
						if(indexNum<0){
							flag = false;
						}
					}
				}catch(Exception e){
					
				}
				
			}
			//add(clearBackLabel);
			
			System.out.println("쓰레드 종료가 되었다.");
		}
	}
	
	
}
