package roushHour2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CarBlocksInfo {
//자동차에 관한 정보를 저장을 하는클래스
//자동차의 x,y좌표 자동차의 크기, 자동차의 방향(세로,가로),자동차의 이름
	private ControlCenter ctrl;
	
	private int levelnum =1;
	private int numberOfCars;//전체 자동차의 수 저장변수
	private ArrayList<Point> carPositions = new ArrayList<Point>(); //자동차의 위치 (x,y)좌표를 저장하는 ArrayList
	private ArrayList<Integer> carSize = new ArrayList<Integer>(); //자동차의 크기를 저장하는 ArrayList
	private ArrayList<Integer> carDirection = new ArrayList<Integer>(); //자동차의 방향(세로,가로) 저장하는 ArrayList
	private ArrayList<String> carName = new ArrayList<String>();//자동차의 이름 저장하는 ArrayList
	private Point[] setCarPosition; // x,y재설정을 위한 Point 배열
	private int moves;//움직인 횟수
	
	//생성자
	public CarBlocksInfo(){
		readData();
		
	}
	
	//get메소드
	public int getMoves(){
		return moves; //움직인 횟수 가져오기
	}

	public int getLevelNumber(){
		return levelnum;
	}
	public int getNumberOfCars(){
		return numberOfCars; //가지고 있는 객체의 자동차 이름을 반환
	}
	public Point getCarPositions(int i){
		return carPositions.get(i); //가지고 있는 객체의 자동차 이름을 반환
	}
	public int getCarSize(int i){
		return carSize.get(i); //가지고 있는 객체의 자동차 이름을 반환
	}
	public int getCarDirection(int i){
		return carDirection.get(i); //가지고 있는 객체의 자동차 이름을 반환
	}
	public String getCarName(int i){
		return carName.get(i); //가지고 있는 객체의 자동차 이름을 반환
	}
	
	//set 메서도
	public void setLevelNumber(int levelnum){
		this.levelnum = levelnum;
	}
	public void setCtrl(ControlCenter ctrl){//콘트롤 센터의 관리를 하기 위해
		this.ctrl = ctrl;
	}
	
	
	//리셋버튼을 눌렀을때 움직인 횟수 리셋
	public void resetData(){
		moves = 0;
		ctrl.changeMoveCount();
	}
	
	
	//드래그를 하였을때 x,y의 좌표를 재설정
	public void setCarPosition(int x, int y, int num){ 
		setCarPosition[num] = new Point(x,y); //x,y값을 저장
		carPositions.set(num,setCarPosition[num]); //변경된 x,y값 저장 
		}
	
	//자동차 정보 설정
	public void setCarInfos(String[] carInfos,int args){
			//carInfos에 저장이 되어 있는 좌표값과 크기,방향는 정수형으로 변환
			carPositions.add(new Point(Integer.parseInt(carInfos[0]), Integer.parseInt(carInfos[1])));//carInfos[0]은 x좌표,carInfos[1]은 y좌표
			carSize.add(Integer.parseInt(carInfos[2]));//
			carDirection.add(Integer.parseInt(carInfos[3]));// 자동차가 세로로 놓을것인지 가로로 놓을것인지를 저장을 한다.
			carName.add(carInfos[4]); //자동차의 종류
	}
	
	public void addMove(){
		moves++;
	}
	
	
	//외부에서 정의한 자동차 데이터 txt 파일 들고 오기
	public void readData(){
		try{
			System.out.println(levelnum);
			
			//자동차의 정보가 들어 있는 콜렉션 초기화
			carPositions.clear();
			carSize.clear();
			carDirection.clear();
			carName.clear();
			
			
			InputStream is = CarBlocksInfo.class.getResourceAsStream("/img/level/level" + levelnum + ".txt");//바이트단위로 들고 옴
			InputStreamReader isr = new InputStreamReader(is);//stream 바이트 단위의 입출력,reader캐릭터단위 입출력 .stage1.txt 파일의 내용이 숫자와 문자(영어)로 되어 있는데 이것들을 읽을려면 바이트로 읽는것이 아닌 문자로 읽는것으로 바꿔줘야함
			BufferedReader br = new BufferedReader(isr);//inputStreamReader는 하나씩 읽어 들이기에 이것을 한줄 단위로 미리 읽는 BufferedReader를 사용을 하여준다.
			numberOfCars = Integer.parseInt(br.readLine());   //readLine은 BufferedReader에 있는 메소드로 데이터를 한 줄씩 읽어 들임. 여기에서 numberOfCars 즉,자동차의 숫자를 저장하기 위해서 사용
			for(int i = 0; i < numberOfCars; i++){ //여기에서 stage1에서 자동차 숫자는 4개이기에 4번 반복문이 돌아간다.
				String carInfo = br.readLine();//여기에서는 자동차의 위치와 자동차의 이름에 관한 자동차의 정보에 관한 한 줄을 문자열로 저장함
				String carInfos[] = carInfo.split(" "); //string의 split메소드를 이용을 하여 문자열을 분해하여서 배열에 담을수 있는데 이때 split에는 지정한 분리자를 기준으로 문자열이 여러 토막으로 분해된다.
				setCarInfos(carInfos, i);
			}
			String myCarInfos[] = {"0", "2", "2", "0", "myCar"};
			setCarInfos(myCarInfos, numberOfCars);
			numberOfCars++;
			setCarPosition =  new Point[getNumberOfCars()];//setCarPosition 설정하기
			is.close();//InputStream 객체를 사용하지 않으니깐 삭제를 해준다. 이거는 inputStream을 사용을 하면 무조건 사용하여야 한다.
			isr.close();//InputStreamReader 객체를 사용하지 않으니깐 삭제
			br.close();//BufferedReader 객체를 사용하지 않으니깐 삭제
		} catch(IOException e){
		}
	}
	
}
