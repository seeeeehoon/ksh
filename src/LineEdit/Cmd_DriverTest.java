package LineEdit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class Cmd_DriverTest extends TestCase{

	File_Buffer f, f1;
    Init_Exit ie, ie1;
    Cmd_Driver RunC;
    UserCmd commandLine;
    boolean tf;
    String[] input = {"D:\\LineEdit\\src\\test.txt"};
	String[] input1 = {"D:\\LineEdit\\src\\test1.txt"};
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;
 
    @Before
	protected void setUp() throws Exception {
	 	 
    	f1 = new File_Buffer();
		 f = new File_Buffer();
	     ie = new Init_Exit(input, f);
	     ie1 = new Init_Exit(input1, f1);
	     RunC = new Cmd_Driver();
	     testOut = new ByteArrayOutputStream();
	     System.setOut(new PrintStream(testOut));
	     super.setUp();
	}
    
    @After
    public void restoreStreams()
    {
    	System.setOut(systemOut);
    }
    
	@Test
	public void testCmd_Q() {
		
		int num = f1.NumLins();

        f1.setUpdateFlag(true);//Q는 저장후 종료하는 명령어이므로
        	
        try {
            RunC.Cmd_D(f1, 1); // 삭제
			ie1.Do_Update(f1);	
			assertEquals(num-1, f1.NumLins()); // num은 삭제하기 전 라인 수, 삭제 후 라인수와 비교시 달라야함. 예상결과:true
			assertNotEquals("#this is moment!", f1.GetLine(1)); //예상 결과 false.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Test
	public void testCmd_X() {
		
		int num = f.NumLins();
		
		tf = true;
        f.setUpdateFlag(false);//X는 저장하지 않고 종료하는 명령어이므로
        	
        try {
            RunC.Cmd_D(f, num); // 삭제
			ie.Do_Update(f);
			f = new File_Buffer();
			ie = new Init_Exit(input,f);
			assertEquals("goodd", f.GetLine(num));
			assertEquals(num, f.NumLins()); // num은 삭제하기 전 라인 수. 예상결과:true
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testCmd_T() {
		
		RunC.Cmd_T(f);
		assertEquals(1, f.GetCLN()); // 예상 결과 : true
	}

	@Test
	public void testCmd_E() {
		int num = f.NumLins();
		RunC.Cmd_E(f);
		assertEquals(num,f.GetCLN()); // 예상결과 : true
	}

	@Test
	public void testCmd_N() {
		int CLNnum = f.GetCLN();
		int next = 1;
		
		RunC.Cmd_N(f,next); //Cmd_N 명령어의 경우 CLN 값에 next만큼 더한값이 CLN 값이 된다.
		if(CLNnum + next > f.NumLins()) {
			CLNnum = f.NumLins();
		}
		else {
		 CLNnum = CLNnum+next;
		}
		
		assertEquals(CLNnum, f.GetCLN()); //예상 결과 true
	}

	@Test
	public void testCmd_B() {
		int CLNnum = f.GetCLN();
		int next = 1;
		
		RunC.Cmd_B(f, next); //Cmd_B 명령어의 경우. CLN값에 next만큼 뺀 값이 CLN 값이 된다.
		
		if(1 > CLNnum - next) {			
			CLNnum = 1;
		}
		else {
			CLNnum = CLNnum - next;
		}
		
		assertEquals(CLNnum, f.GetCLN()); //예상 결과 : true
	}
	@Test
	public void testCmd_W() {
		RunC.Cmd_W(f);
		assertEquals("At Edit File Line "+f.GetCLN()+System.getProperty("line.separator"),testOut.toString() );
	}
	@Test
	public void testCmd_C() {
		RunC.Cmd_C(f);
		assertEquals("Total Edit File Lines: "+f.NumLins()+System.getProperty("line.separator"),testOut.toString() );
	}
	
	@Test
	public void testCmd_L() {
		  int CLNnum =f.GetCLN();
	      int nLines=2;
	      String str = "";
	      
	      for(int i=CLNnum;i<=nLines;i++) {
	         str += f.GetLine(i)+System.getProperty("line.separator");
	      }
	      
	      RunC.Cmd_L(f,nLines);
	      assertEquals(str,testOut.toString());
	}

	@Test
	public void testCmd_S() {
		int CLNnum = f.GetCLN();
		int nLines = 2;
		
		String str = "";
	      
	    for(int i=CLNnum;i<=nLines;i++) {
	       str += f.GetLine(i)+System.getProperty("line.separator");
	    }
	      
	    RunC.Cmd_S(f,nLines);
	    assertEquals(str,testOut.toString());
		//S 명령어는 CLN이 변화 되지 않아야 한다. 
		assertEquals(CLNnum , f.GetCLN()); //예상 결과 : true
	}

	@Test
	public void testCmd_D() {
		int CLNnum = f.NumLins();
		int n = 2;
		RunC.Cmd_D(f,n);
		
		assertEquals(CLNnum - n, f.NumLins()); // 예상 결과 : true
	}

	@Test
	public void testCmd_A() {
		int CLNnum = f.GetCLN();
		System.out.println(f.GetCLN());
		int tnum = f.NumLins();
		
		try {
			RunC.Cmd_A(f);
			assertEquals(CLNnum, f.GetCLN());//1개도 add하지 않았으므로  CLN값이 그대로인지 체크 예상결과:true.
			assertEquals(tnum, f.NumLins()); // 총 행의 수가 증가했는가 체크. 예상결과 : true.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCmd_F() {
		int CLNnum = f.GetCLN();
		int nLines = 5;
		String str = "KKIMUS";
		
		RunC.Cmd_F(f,nLines,str);
		assertEquals(CLNnum +2, f.GetCLN()); // KKIMUS가 3행에 있으므로, CLN의 값이 3으로 바뀌었는지 확인. 예상결과 : true
	}

	@Test
	public void testCmd_R() {
		int CLNnum = f.GetCLN();
		int n = 7;
		String Right = "KKIMUS";
		String Left =  "KKIMSE";
		
		RunC.Cmd_R(f, n, Right, Left);
		
		assertEquals(CLNnum + 2, f.GetCLN()); // R을 통해 변환이 성공되었을 거라고 예상되므로, CLN값이 변환된 행으로 이동했는 지 확인.
		assertEquals("KKIMSE",f.GetLine(3)); // KKIMUS가 3번째 있으므로, 3번째 값이 바뀌었는지 체크
	}

	@Test
	public void testCmd_Y() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_Y(f,n);
		assertEquals(CLNnum+2, f.GetCLN()); //Y명령어가 실행되면, CLN값이 마지막 행으로 바뀌는지 확인.
	}

	@Test
	public void testCmd_Z() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_Z(f,n);
		assertEquals(CLNnum, f.GetCLN());
	}

	@Test
	public void testCmd_P() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_Y(f,n);		
		RunC.Cmd_P(f);
		assertEquals((CLNnum+(n*2-1)), f.GetCLN());
		
	}

	@Test
	public void testCmd_I() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_I(f);
		assertEquals(CLNnum,f.GetCLN()); //2행 1열에 @가 있으므로 CLN은 영향 안받음.`
	}

	@Test
	public void testCmd_K() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_I(f);
		RunC.Cmd_K("#this is moment!");
		
		assertEquals(CLNnum, f.GetCLN()); // 인덱스를 만들었으므로 테스트 가능하며, CLN에 영향은 없어야 한다 예상결과:true.
	}

	@Test
	public void testCmd_O() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_O(f,n);
		
		assertEquals(CLNnum+2,f.GetCLN()); // 정렬이 된다고 가정하면, CLN은 마지막 정렬된 줄 번호를 가지고있으므로 예상결과 : true.
	}

	@Test
	public void testCmd_M() {
		 int M_left=3;
	      int M_right=5;
	      RunC.Cmd_M(M_left, M_right);
	   
	      RunC.Cmd_O(f, 8);
	      assertNotEquals(5, f.GetCLN()); // 예상 결과 : false
	            
	      f.SetCLN(2);
	      
	      RunC.Cmd_F(f, 8,"test");
	      assertNotEquals(5, f.GetCLN()); // 예상 결과 : false
	      
	      f.SetCLN(2);
	      
	      RunC.Cmd_R(f, 8, "ha", "ho");
	      assertNotEquals(5, f.GetCLN()); // 예상 결과 : false
	}

}
