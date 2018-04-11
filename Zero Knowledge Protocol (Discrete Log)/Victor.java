import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Victor
{
	public static void main(String ar[])throws IOException,UnknownHostException,ClassNotFoundException //UnknownHostException - unchecked exception
	{
		Socket s = new Socket("localhost",2001);
		Scanner sc = new Scanner(System.in);
		int turn = 0;
		long n=0,e=0,x0=0,x1=0;

		long values[] = new long[9];
		// 1-> y, 2 -> C
		String val[] = new String[9];
		val[0] = "p"; val[1] = "y"; val[2] = "C"; val[3] = "g";
		long req1 =0, req2 = 0;
		int b = 2;
		while(true)
		{
			
			if(turn<=3){
				//System.out.println("Enter a string");
				DataInputStream dis = new DataInputStream(System.in);
				String st = "Enter "+val[turn];
				PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				ps.println(st);									// request to server
				dis = new DataInputStream(s.getInputStream());			// new object to read
				st = dis.readLine();
				System.out.println("From Peggy : "+val[turn]+"  = "+st);
				values[turn] = Long.parseLong(st);
				turn++;
			}else if(turn==4){
				System.out.println("Choose 0 to request r or 1 to request (x+r)mod(p-1) or 2 to break");
				b = sc.nextInt();
				long v;
				if(b==2){
					break;
				}
				String st;// = dis.readLine();
				if(b==0){
					st = "Send r";
				}else{
					st = "Send (x+r) mod(p-1)";
				}
				PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				ps.println(st);									// request to server
				turn++;
			}else{
				DataInputStream dis = new DataInputStream(System.in);
				//PrintStream ps = new PrintStream(s.getOutputStream());  // where to write i.e. s, which is defined above.
				//ps.println(st);									// request to server
				dis = new DataInputStream(s.getInputStream());			// new object to read
				String st = dis.readLine();
				
				if(b==0){
					System.out.println("From Peggy : r = "+st);
					long r = Long.parseLong(st);
					long v = fun(values[3],r,values[0]);
					if(v==values[2]){
						System.out.println("Verified");
					}
					// We've received r;
				}else{
					System.out.println("From Peggy : (x+r) mod(p-1) = "+st);
					long r2 = Long.parseLong(st);
					long v = fun(values[3],r2,values[0]);
					long v2 = (values[2]*values[1])%values[0];
					if(v2==v){
						System.out.println("Verified");
					}
				}
				turn--;
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
