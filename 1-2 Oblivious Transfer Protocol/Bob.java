import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Bob
{
	public static void main(String ar[])throws IOException,UnknownHostException,ClassNotFoundException //UnknownHostException - unchecked exception
	{
		Socket s = new Socket("localhost",2000);
		Scanner sc = new Scanner(System.in);
		int turn = 1;
		long n=0,e=0,x0=0,x1=0;

		long values[] = new long[9];
		// 1-> N, 2-> e, 3 -> x0, 4 -> x1, 5 -> v, 7 -> m0', 8 -> m1'
		String val[] = new String[9];
		val[1] = "N"; val[2] = "e"; val[3] = "x0"; val[4] = "x1"; val[5] = "v"; val[7] = "m0\'"; val[8] = "m1\'";
		int d = 0;
		int b=0;
		long k=0;
		while(true)
		{
			
			if(turn<5 || turn==7 || turn==8){
				//System.out.println("Enter a string");
				DataInputStream dis = new DataInputStream(System.in);
				String st = "Enter "+val[turn];
				PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				ps.println(st);									// request to server
				dis = new DataInputStream(s.getInputStream());			// new object to read
				st = dis.readLine();
				System.out.println("From Alice : "+val[turn]+"  = "+st);
				values[turn] = Long.parseLong(st);
				turn++;
			}else if(turn==5){
				System.out.println("Choose 0 or 1");
				b = sc.nextInt();
				long v;
				x0 = values[3];
				x1 = values[4];
				while(true){
					k = (int)(Math.random()*100	);
					
					if(b==0){
						v = x0 + fun(k,values[2],values[1]);
						if(v>values[1]) v = v%values[1];
					}else{
						v = x1 + fun(k,values[2],values[1]);
						if(v>values[1]) v = v%values[1];
					}
					//System.out.println("\tValue of V = "+v+" k = "+k);
					if(v>x0 && v>x1) break;
				}
				System.out.println("\tValue of V = "+v+" k = "+k);
				DataInputStream dis = new DataInputStream(System.in);
				System.out.println("Send V");
				String st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				ps.println(st);									// request to server
				turn+=2;
			}else if(turn==9){
				long ans;
				if(b==0){
					ans = values[7] - k;
				}else{
					ans = values[8] - k;
				}
				System.out.println("Ans => "+ans);
				turn++;
			}else
			{
				System.out.println("DOne ");
				break;
				/*
				DataInputStream dis = new DataInputStream(System.in);
				System.out.println("Enter a string");
				String st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				ps.println(st);									// request to server
				dis = new DataInputStream(s.getInputStream());			// new object to read
				st = dis.readLine();
				System.out.println("From Alice : n = "+st);
				turn++;
				*/
			}
		}
	}

	static long fun(long k, long e, long n){
		long v = 1;
		for(long i=0;i<e;i++){
			v = v*k;
			if(v>n) v = v%n;
		}
		return v;
	}
}
