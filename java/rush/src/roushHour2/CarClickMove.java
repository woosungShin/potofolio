package roushHour2;
//마우스 이벤트를 사용을 하여서 자동차가 움직이게 한다. ->ok
//자동차가 움직일때 제약을 건다.--> ok
//

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

//carClickMove여기에서 Jlabel을 이용을 하여서 자동차이미지를 맵에 집어넣기-->



public class CarClickMove extends JLabel implements CommonInfo{
	//마우스 클릭을 했을때의 x,y좌표
	//클릭한 자동차의 가로와 세로 길이를 비교를 하여서 세로로만 움직일지,가로로만 움직일지 정하기
	//마우스 드래그 이벤트를 사용을 하여서 드래그하는 하는동안 좌표
	
	private ControlCenter ctrl;
	private CarBlocksInfo cbi; //자동차의 
	private int carNumber;//넘어온 자동차 번호 저장 변수
	private int maxX,maxY,minX,minY; //X의 최대,최소 좌표 설정, Y의 최대,최소 자표설정 저장 변수
	
	//최대값 최소값 get메소드
	public int getMaxX(){return maxX;}
	public int getMinX(){return minX;}
	public int getMaxY(){return maxY;}
	public int getMinY(){return minY;}
	
	public void setInfos(ControlCenter ctrl,CarBlocksInfo cbi){
		this.cbi = cbi;
		this.ctrl = ctrl;
	}
	
	public CarClickMove(ImageIcon icn,int carNum,int x,int y){
		
		super(icn);//JLabel에 이미지 넣기
		//자동차 라벨의 x,y좌표는 그림의 맵부분의 좌표를 보고 정한다.
		setBounds(180+(x*50),80+(y*50), icn.getIconWidth(), icn.getIconHeight());
		this.carNumber = carNum;//자동차의 번호 저장
		ClickLitener carClickListener = new ClickLitener(this); //여기에서 this는 현재 생성이된 객체
		addMouseListener(carClickListener); //현재 생성이 된 JLabel의 마우스이벤트
		addMouseMotionListener(carClickListener);//현재 생성이 된 JLabel의 마우스이벤트
		
	}
	
	//ClickLitener클래스를 innerclass함 => 마우스가 때졌을때 정보를 변경을 하여야 하는것 때문에 inner로 변경.
	class ClickLitener extends MouseAdapter{
		int clickX,clickY;
		int cX,cY;
		private CarClickMove carDrag;
		
		
		public ClickLitener(CarClickMove carDrag){
			this.carDrag = carDrag;
		}
		
		public void mouseDragged(MouseEvent e){//마우스 드래그 이벤트 처리 메소드
			int newX = e.getXOnScreen() - cX;
			int newY = e.getYOnScreen() - cY;
			
			//새로운 지점 설정
			if(carDrag.getHeight() < carDrag.getWidth()){//가로가 세로보다 길면 옆으로만 움직이기
				newX = LIMIT_OF_LEFT + ((newX - 150) / BLOCK_WIDTH) * BLOCK_WIDTH;
				if(newX < carDrag.getMinX()) 
					newX = carDrag.getMinX();
				else if(carDrag.getMaxX() < newX) 
					newX = carDrag.getMaxX();
				
				newY = carDrag.getMaxY();
			}

			else if(carDrag.getWidth() < carDrag.getHeight()){//세로가 가로보다 길면 위,아래로 움직이기
				newX = carDrag.getMaxX();
				newY = LIMIT_OF_TOP + ((newY - 50) / BLOCK_HEIGHT) * BLOCK_HEIGHT; 
				if(newY < carDrag.getMinY()) 
					{newY = carDrag.getMinY(); }
				else if(newY > carDrag.getMaxY()) {
					newY = carDrag.getMaxY();}
			}
			
			carDrag.setLocation(newX, newY);
		}
		public void mousePressed(MouseEvent e){//마우스가 클릭이 되었을때 처리 메소드
			clickX = carDrag.getX(); //생성된 라벨의 마우스x값
			clickY = carDrag.getY(); //생성된 라벡의 마우스x값
		
			cX = e.getXOnScreen() - carDrag.getX();//라벨내의 x좌표값을 구하기
			cY = e.getYOnScreen() - carDrag.getY();//라벨내의 y좌표값을 구하기
			
		}
		public void mouseReleased(MouseEvent e){//마우스가 때졌을때 처리 메소드
			//if((clickX != carDrag.getX())||(clickY != carDrag.getY())){
				//처음 클릭을 한 x좌표와 나중의 x좌표가 다를경우 움직인 횟수를 카운트하는 메소드 실행
				//게임 클리어 확인 메소드 실행
				//마우스를 땐 좌표로 해당 자동차의 x,y좌표 수정 하는 메소드 실행
			ctrl.chageInfo(((carDrag.getX() - 180) / 50), ((carDrag.getY() - 80) / 50), carNumber);
				
			
		}
		
	} 
	
	
	
	public void setMoveLimits() {
		// TODO Auto-generated method stub
		int presentSize = cbi.getCarSize(carNumber);
		int presentDirection = cbi.getCarDirection(carNumber);
		Point presentPosition = cbi.getCarPositions(carNumber);

		if(presentDirection == 0){ //차가 가로일때
			maxX = LIMIT_OF_RIGHT - presentSize*BLOCK_WIDTH;         //오른쪽으로 최대 갈수 있는것은 오른쪽 최대좌표-현재차사이즈*블록의 너비이다. 
			minX = LIMIT_OF_LEFT;                       //왼쪽으로 최소 갈수 있는 값
			maxY = minY = LIMIT_OF_TOP + (presentPosition.y * BLOCK_HEIGHT); //위,아래로는 못움직이게 설정
			
			for(int i = 0; i < cbi.getNumberOfCars(); i++){ //차량의 숫자만큼 비교를 하여서
				if(i == carNumber)
					continue;        // 현재의 차량 번호하고 인덱스 번호 같으면 밑에꺼 무시
				
				//아래는 비교하기 위해서 사용
				//다른 차의 
				int otherCarSize =cbi.getCarSize(i);
				int otherCarDirection = cbi.getCarDirection(i);
				Point otherCarPosition = cbi.getCarPositions(i);
				if(otherCarDirection == 1){ //다른차가 세로일때 최대움직이는거 설정
					if(otherCarPosition.y <= presentPosition.y && presentPosition.y < (otherCarPosition.y + otherCarSize)){
						if(presentPosition.x < otherCarPosition.x){ 
							int newLimit = LIMIT_OF_LEFT + ((otherCarPosition.x - presentSize) * BLOCK_WIDTH);
							if( maxX >= newLimit)
								maxX=newLimit;
						}
						else{                                     
							int newLimit = LIMIT_OF_LEFT + ((otherCarPosition.x + 1) * BLOCK_WIDTH);
							if(minX <= newLimit)
								minX =  newLimit;
						}
					}
				} else{//다른차가 가로일때 최대 움직이는거 설정      
					if(presentPosition.y == otherCarPosition.y){ 
						if(presentPosition.x < otherCarPosition.x){ 
							int newLimit = LIMIT_OF_LEFT + ((otherCarPosition.x - presentSize) * BLOCK_WIDTH);
							if( maxX >= newLimit)
								maxX=newLimit;
						}
						else{                                     
							int newLimit  = LIMIT_OF_LEFT + ((otherCarPosition.x + otherCarSize) * BLOCK_WIDTH);
							if(minX <= newLimit)
								minX =  newLimit;
						}
					}
				}
			}
			
		} else{ 
			maxX = minX = LIMIT_OF_LEFT + (presentPosition.x * BLOCK_WIDTH); 
			maxY = LIMIT_OF_BOTTOM - getHeight();        
			minY = LIMIT_OF_TOP;                         
			for(int i = 0; i < cbi.getNumberOfCars(); i++){ 
				if(i == carNumber) continue;
				int otherCarSize = cbi.getCarSize(i);
				int otherCarDirection = cbi.getCarDirection(i);
				Point otherCarPosition = cbi.getCarPositions(i);
				if(otherCarDirection == 0){
					if(otherCarPosition.x <= presentPosition.x && presentPosition.x < (otherCarPosition.x + otherCarSize)){
						if(presentPosition.y < otherCarPosition.y){
							int newLimit = LIMIT_OF_TOP + ((otherCarPosition.y - presentSize) * BLOCK_HEIGHT);
							if(maxY >= newLimit)
								maxY =  newLimit;
						}
						else{
							int newLimit = LIMIT_OF_TOP + ((otherCarPosition.y + 1) * BLOCK_HEIGHT);
							if(minY <= newLimit)
								minY =  newLimit;
						}
					}
				} else{
					if(presentPosition.x == otherCarPosition.x){
						if(presentPosition.y < otherCarPosition.y){
							int newLimit = LIMIT_OF_TOP + ((otherCarPosition.y - presentSize) * BLOCK_HEIGHT);
							if(maxY >= newLimit)
								maxY =  newLimit;
						}
						else{
							int newLimit = LIMIT_OF_TOP + ((otherCarPosition.y + otherCarSize) * BLOCK_HEIGHT);
							if(minY <= newLimit)
								minY =  newLimit;
						}
					}
				}
			}
		}
		if(carNumber == (cbi.getNumberOfCars() - 1)){ //현재 차번호가 탈출해야할 차라면
			if(maxX == LIMIT_OF_RIGHT - (BLOCK_WIDTH*2))//최대가 380이면 골인지점의 좌표까지
			{
				maxX = LIMIT_OF_RIGHT;
			}
	
		}
	
	}

}
