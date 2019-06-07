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

        f1.setUpdateFlag(true);//Q�� ������ �����ϴ� ��ɾ��̹Ƿ�
        	
        try {
            RunC.Cmd_D(f1, 1); // ����
			ie1.Do_Update(f1);	
			assertEquals(num-1, f1.NumLins()); // num�� �����ϱ� �� ���� ��, ���� �� ���μ��� �񱳽� �޶����. ������:true
			assertNotEquals("#this is moment!", f1.GetLine(1)); //���� ��� false.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Test
	public void testCmd_X() {
		
		int num = f.NumLins();
		
		tf = true;
        f.setUpdateFlag(false);//X�� �������� �ʰ� �����ϴ� ��ɾ��̹Ƿ�
        	
        try {
            RunC.Cmd_D(f, num); // ����
			ie.Do_Update(f);
			f = new File_Buffer();
			ie = new Init_Exit(input,f);
			assertEquals("goodd", f.GetLine(num));
			assertEquals(num, f.NumLins()); // num�� �����ϱ� �� ���� ��. ������:true
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testCmd_T() {
		
		RunC.Cmd_T(f);
		assertEquals(1, f.GetCLN()); // ���� ��� : true
	}

	@Test
	public void testCmd_E() {
		int num = f.NumLins();
		RunC.Cmd_E(f);
		assertEquals(num,f.GetCLN()); // ������ : true
	}

	@Test
	public void testCmd_N() {
		int CLNnum = f.GetCLN();
		int next = 1;
		
		RunC.Cmd_N(f,next); //Cmd_N ��ɾ��� ��� CLN ���� next��ŭ ���Ѱ��� CLN ���� �ȴ�.
		if(CLNnum + next > f.NumLins()) {
			CLNnum = f.NumLins();
		}
		else {
		 CLNnum = CLNnum+next;
		}
		
		assertEquals(CLNnum, f.GetCLN()); //���� ��� true
	}

	@Test
	public void testCmd_B() {
		int CLNnum = f.GetCLN();
		int next = 1;
		
		RunC.Cmd_B(f, next); //Cmd_B ��ɾ��� ���. CLN���� next��ŭ �� ���� CLN ���� �ȴ�.
		
		if(1 > CLNnum - next) {			
			CLNnum = 1;
		}
		else {
			CLNnum = CLNnum - next;
		}
		
		assertEquals(CLNnum, f.GetCLN()); //���� ��� : true
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
		//S ��ɾ�� CLN�� ��ȭ ���� �ʾƾ� �Ѵ�. 
		assertEquals(CLNnum , f.GetCLN()); //���� ��� : true
	}

	@Test
	public void testCmd_D() {
		int CLNnum = f.NumLins();
		int n = 2;
		RunC.Cmd_D(f,n);
		
		assertEquals(CLNnum - n, f.NumLins()); // ���� ��� : true
	}

	@Test
	public void testCmd_A() {
		int CLNnum = f.GetCLN();
		System.out.println(f.GetCLN());
		int tnum = f.NumLins();
		
		try {
			RunC.Cmd_A(f);
			assertEquals(CLNnum, f.GetCLN());//1���� add���� �ʾ����Ƿ�  CLN���� �״������ üũ ������:true.
			assertEquals(tnum, f.NumLins()); // �� ���� ���� �����ߴ°� üũ. ������ : true.
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
		assertEquals(CLNnum +2, f.GetCLN()); // KKIMUS�� 3�࿡ �����Ƿ�, CLN�� ���� 3���� �ٲ������ Ȯ��. ������ : true
	}

	@Test
	public void testCmd_R() {
		int CLNnum = f.GetCLN();
		int n = 7;
		String Right = "KKIMUS";
		String Left =  "KKIMSE";
		
		RunC.Cmd_R(f, n, Right, Left);
		
		assertEquals(CLNnum + 2, f.GetCLN()); // R�� ���� ��ȯ�� �����Ǿ��� �Ŷ�� ����ǹǷ�, CLN���� ��ȯ�� ������ �̵��ߴ� �� Ȯ��.
		assertEquals("KKIMSE",f.GetLine(3)); // KKIMUS�� 3��° �����Ƿ�, 3��° ���� �ٲ������ üũ
	}

	@Test
	public void testCmd_Y() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_Y(f,n);
		assertEquals(CLNnum+2, f.GetCLN()); //Y��ɾ ����Ǹ�, CLN���� ������ ������ �ٲ���� Ȯ��.
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
		assertEquals(CLNnum,f.GetCLN()); //2�� 1���� @�� �����Ƿ� CLN�� ���� �ȹ���.`
	}

	@Test
	public void testCmd_K() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_I(f);
		RunC.Cmd_K("#this is moment!");
		
		assertEquals(CLNnum, f.GetCLN()); // �ε����� ��������Ƿ� �׽�Ʈ �����ϸ�, CLN�� ������ ����� �Ѵ� ������:true.
	}

	@Test
	public void testCmd_O() {
		int CLNnum = f.GetCLN();
		int n = 3;
		
		RunC.Cmd_O(f,n);
		
		assertEquals(CLNnum+2,f.GetCLN()); // ������ �ȴٰ� �����ϸ�, CLN�� ������ ���ĵ� �� ��ȣ�� �����������Ƿ� ������ : true.
	}

	@Test
	public void testCmd_M() {
		 int M_left=3;
	      int M_right=5;
	      RunC.Cmd_M(M_left, M_right);
	   
	      RunC.Cmd_O(f, 8);
	      assertNotEquals(5, f.GetCLN()); // ���� ��� : false
	            
	      f.SetCLN(2);
	      
	      RunC.Cmd_F(f, 8,"test");
	      assertNotEquals(5, f.GetCLN()); // ���� ��� : false
	      
	      f.SetCLN(2);
	      
	      RunC.Cmd_R(f, 8, "ha", "ho");
	      assertNotEquals(5, f.GetCLN()); // ���� ��� : false
	}

}
