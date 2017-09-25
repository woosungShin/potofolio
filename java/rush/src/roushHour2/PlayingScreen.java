package roushHour2;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Point;


//이 클래스는 플레이화면을 보여주는 클래스이다. 
// 이 클래스를 통해서 본 게임 화면이 나오게 한다.
// 자동차 정보에 관한것을 carBlockInfo를 통해서 들고 온다. => 객체를 만들어서 들고 온다.
// 움직인 횟수가 표시가 되어야 한다.


public class PlayingScreen extends JPanel implements CommonInfo{
	private CarClickMove carImg[]; //각 차량을 CarClickMove형식으로 저장 이유는 클릭한 자동차 객체에 대한 이벤트 처리때문.
	private CarClickMove myCar;
	private CarBlocksInfo cbi;
	private ControlCenter ctrl;
	private CarClickMove ccm;
	//public JPanel plJpnel;
	public JLabel playingImg;
	private JButton goTitleButton, goSelectButton, restartButton;
	private JLabel movesLabel,movesNum[]= new JLabel[3];
	//타이머 변수를 선언을 하여 주기
	
	public PlayingScreen(CarBlocksInfo cbi){
		
		this.cbi = cbi;
		setLayout(null);
		setBounds(0,0,800,455);
		insertSet();

	}
	
	public void insertSet(){
		cbi.readData();
		goTOTitle();
		goToSelect();
		goTORestart();
		insertCars();
		insertMyCar();
		insertMove();
		insertPlayField();
		//exchangeMoves();
	}
	
	//TiTle버튼 메소드
	private void goTOTitle(){
		ImageIcon titleIcn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/playtotitle.png"));
		goTitleButton = new JButton(titleIcn);
		add(goTitleButton);
		goTitleButton.setBounds(580, 100, titleIcn.getIconWidth(), titleIcn.getIconHeight());
		
		//이벤트가 발생이 되었을때 발동이 되는것.
		goTitleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				//알림창이 나오게 한다.
				int option = JOptionPane.showConfirmDialog(null, "타이틀 화면으로 가겠습니까?", "타이틀GO?", JOptionPane.YES_NO_OPTION); 
				if (option == JOptionPane.YES_OPTION){ 
					setVisible(false); //현재 판넬을 안보여주기
					ctrl.viewHome();//타이틀 판넬로 변경하기 호출
				}
			}
		});
	}
	

	/*select화면이 나오게 하는 버튼 설정*/
	private void goToSelect(){
		ImageIcon selectIcn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/playtoselect.png"));
		goSelectButton = new JButton(selectIcn);
		add(goSelectButton);
		goSelectButton.setBounds(580, 180, selectIcn.getIconWidth(), selectIcn.getIconHeight());
		goSelectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				int option = JOptionPane.showConfirmDialog(null, "스테이지 화면으로 돌아갈까요?", "스테이지화면 GO?", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION){
					setVisible(false);
					ctrl.viewSelect();
				}
			}
		});
	}

	/*리셋버튼 메소드*/
	private void goTORestart(){
		ImageIcon restartIcn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/playtorestart.png"));
		restartButton = new JButton(restartIcn);
		add(restartButton);
		restartButton.setBounds(580, 260, restartIcn.getIconWidth(), restartIcn.getIconHeight());
		restartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				int option = JOptionPane.showConfirmDialog(null, "다시 시작하겠습니까??", "다시시작", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION){
					setVisible(false);
					ctrl.viewPlay(cbi.getLevelNumber());
				}
			}
		});
	}

	private void insertPlayField(){
		ImageIcon icn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/field.png"));
		playingImg = new JLabel(icn);
		playingImg.setBounds(0, 0, icn.getIconWidth(), icn.getIconHeight());
		add(playingImg);
	}
	
	public void insertMove(){
		//움직이는 횟수가 위치할 라벨
			ImageIcon icn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/move.png"));
				movesLabel = new JLabel(icn);
				movesLabel.setBounds(20, 50, icn.getIconWidth(), icn.getIconHeight());

				//움직인 횟수 초기값 설정
				int x = 20 + movesLabel.getWidth();
				icn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/0.png"));
				for(int i = 0; i < 3; i++){
					movesNum[i] = new JLabel(icn);
					movesNum[i].setBounds(x, 50, icn.getIconWidth(), icn.getIconHeight());
					add(movesNum[i]);
					x += icn.getIconWidth();
				}
				add(movesLabel);
	}
	
	public void exchangeMoves(){
		int x = 20 + movesLabel.getWidth();
		int num;
		ImageIcon icn;

		for(int i = 0; i < 3; i++){
			switch(i){ 
				case 0://백의 자리
					num = (cbi.getMoves() / 100) % 10;
					break;
			
				case 1://십의 자리
					num = (cbi.getMoves() / 10) % 10;
					break;
				
				default: //일의자리
					num = cbi.getMoves() % 10;
					break;
			}
			icn = new ImageIcon(getClass().getResource("/img/playing/" + num + ".png"));
			movesNum[i].setIcon(icn);
			movesNum[i].setBounds(x, 50, icn.getIconWidth(), icn.getIconHeight());
			x += icn.getIconWidth();//다음 자리수의 x좌표값
		}
	}

	
	//자동차를 맵에 배치를 하는 메소드
	public void insertCars(){
		ImageIcon icn;
		carImg = new CarClickMove[cbi.getNumberOfCars()];
		for(int i = 0; i < cbi.getNumberOfCars()-1; i++){
			icn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/" + cbi.getCarName(i) + cbi.getCarSize(i) + cbi.getCarDirection(i) + ".gif"));
			carImg[i] = new CarClickMove(icn,i,cbi.getCarPositions(i).x,cbi.getCarPositions(i).y); //내 자동차를 CarClickMove 객체로 만들고 carImg에 저장	 //각 자동차의 이벤트 처리를 위해 CarClickMove 객체로 만들고 carImg에 저장
			add(carImg[i]);//그 객체를 판넬에 붙이
			carImg[i].setInfos(ctrl,cbi);
		}
	}
	
	public void insertMyCar(){
		//내차를 맵에 붙이기
		ImageIcon icn;
				icn = new ImageIcon(PlayingScreen.class.getResource("/img/playing/myCar.gif"));
				myCar = new CarClickMove(icn,cbi.getNumberOfCars()-1,cbi.getCarPositions(cbi.getNumberOfCars()-1).x,cbi.getCarPositions(cbi.getNumberOfCars()-1).y); //내 자동차를 CarClickMove 객체로 만들고 carImg에 저장	
				add(myCar);
				myCar.setInfos(ctrl,cbi);
				setLimits(); //자동차들의 움직임 제한 호출
				
	}
	
	public void setCtrl(ControlCenter ctrl){//콘트롤 센터의 관리를 하기 위해
		this.ctrl = ctrl;
	}
	
	//자동차별 움직임 제한 메소드
	public void setLimits(){
		for(int i = 0; i < cbi.getNumberOfCars()- 1; i++) {
				carImg[i].setMoveLimits();
			}
			
		myCar.setMoveLimits();
	}
	
	//클리어 조건 메소드
	public void checkClear(){
		if(cbi.getCarPositions(cbi.getNumberOfCars() - 1).x == 5){
			//여기에 조그만 판넬을 하나 띄워서 그 판넬에 걸린 시간 이동 횟수를 보여주는라벨을 붙이고
			//그 라벨의 바로 밑에 다음레벨레 가
			setVisible(false);
			ctrl.viewClear();
		}
	}
}
