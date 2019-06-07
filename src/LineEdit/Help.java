package LineEdit;

import java.io.*;

public class Help
/****************************************************************************
*  AUTH:  Truly, Yours                    DATE:  Oct. 1999                  *
*  DEPT:  Computer Science, CS-200        ORG.:  Colorado State University  *
*****************************************************************************
*                                                                           *
*  FILE:  Help.java                                                         *
*                                                                           *
*  DESC:  Contains the member functions for the Help Class.        .        *
*                                                                           *
****************************************************************************/
{

  //***************************************************************************
  public static void General()
  {
	 System.out.println("Genaral 호출");
  }

  //***************************************************************************
  public static void Command(char cmd)
  {
	 System.out.println(cmd+"information 호출");
  }

} // EndClass Help
