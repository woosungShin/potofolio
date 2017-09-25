package roushHour2;

import javax.swing.JFrame;

//이게임의 main화면이 있는 클래스


public class RushHourMain extends JFrame{
	public ControlCenter ctc;
	public CarBlocksInfo cbi;
	public PlayingScreen ps;
	public LevelSelect ls;
	public HomeSelect hs;
	public ClearScreen cs;
	
	public RushHourMain(){
		//자동차 정보 객체 생성
		cbi = new CarBlocksInfo();//자동차블럭정보 객체 생성
		
		//각 화면 판넬 생성
		ps = new PlayingScreen(cbi);//게임판넬
		ls = new LevelSelect();//레벨판넬
		hs = new HomeSelect();//홈판넬
		cs = new ClearScreen(cbi);//게임 클리어 판넬
		
		//컨트롤 센터를 통해서 관리
		ctc = new ControlCenter(cbi,hs,ls,ps,cs);
		
		//각 클래스에서 콘트롤센터에 접근할수 있게 사용 
		hs.setCtrl(ctc);
		ls.setCtrl(ctc);
		ps.setCtrl(ctc);
		cs.setCtrl(ctc);
		cbi.setCtrl(ctc);
		
		//판넬들을 추가를 한다.
		add(ps);//play화면 삽입
		add(ls);//레벨 화면 삽임
		add(hs);//홈 화면 삽입
		add(cs);//클리어 화면 삽입
		
		cs.setVisible(false);
		ps.setVisible(false);
		ls.setVisible(false);
		
		
		setTitle("러시아워~~퍼즐!!");
		setLayout(null);
		setBounds(250,120,800,455);
		setResizable(false);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args []){
		RushHourMain rhm = new RushHourMain();
	}
}
