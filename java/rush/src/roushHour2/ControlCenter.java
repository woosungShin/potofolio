package roushHour2;

//이 클래스는 모든 게임에서 핵심의 요소로 게임을 관리를 하는 클래스이다.
//이미지의 위치가 변경이 되었다고 한다면 다시 모든 이미지의 최대 움직이는 거 설정
public class ControlCenter{
	private CarBlocksInfo cbi;
	private HomeSelect hs;
	private LevelSelect ls;
	private PlayingScreen ps;
	private ClearScreen cs;
	
	//생성자
	public ControlCenter(CarBlocksInfo cbi, HomeSelect hs,LevelSelect ls,PlayingScreen ps,ClearScreen cs){
		this.cbi = cbi;
		this.hs = hs;
		this.ls = ls;
		this.ps = ps;
		this.cs = cs;
	}
	public void viewHome(){
		hs.setVisible(true);
	}
	
	public void viewSelect(){
		ls.setVisible(true);
	}

	//플레이 판넬을 보여주고 플레이판넬 객체 를 실행
	public void viewPlay(int levelNum){
		cbi.setLevelNumber(levelNum);
		ps.setVisible(true);
		ps.removeAll(); //전 레벨에 있던 컴퍼넌트 제거 : 다시 배치위해
		ps.repaint(); //Jpanel의 메소드
		ps.insertSet();//removeall로 다지운 컴퍼넌트 새롭게 배치
		cbi.resetData();//움직인 횟수 초기화
	}

	public void viewClear(){
		
		cs.setVisible(true);
		cs.removeAll();
		cs.repaint();
		cs.insertClearSet();
		cs.changeNum();//점수 설정
		
		
	}
	
	
	public void changeMoveCount(){
		ps.exchangeMoves();
	}
	
	public void chageInfo(int x,int y,int carNum){
		cbi.addMove();            //움직인 횟수 추가
		cbi.setCarPosition(x, y, carNum); //마우스 놓은곳으로 자동차 위치설정
		ps.setLimits();
		ps.exchangeMoves();
		ps.checkClear();          
	}
}
