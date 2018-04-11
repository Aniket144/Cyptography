import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Peggy
{
	public static void main(String ar[])throws IOException
	{
		Scanner sc = new Scanner(System.in);
		
		long p = 30011; // Prime.
		long g = 47;	// Generator.
		System.out.println("\tPrime p = "+p+" Generator g = "+g);
		System.out.print("Enter X ");
		long x = sc.nextLong();
		long y = fun(g,x,p);
		System.out.println("\ty = "+y+" x = "+x);

		ServerSocket ss = new ServerSocket(2001);
		System.out.println("Waiting For Victor");
		Socket s = ss.accept();	// waiting for client
		System.out.println("Victor is connected\n");
		long r,C,r2;
		int turn = 1;
		while(true)
		{
			if(turn<=4){
				DataInputStream dis = new DataInputStream(s.getInputStream());	//from where to read 
				String st = dis.readLine();				// reading
				if(turn==3){
					r = (int)(Math.random()*100);
					C = fun(g,r,p);
					r2 = (x+r)%(p-1);
					System.out.println("\tr = "+r+" C = "+C+" (x+r)mod(p-1)="+r2);
				}
				System.out.println("From Victor: "+st);
				System.out.println("Enter what is asked");
				dis = new DataInputStream(System.in);
				st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(st);							// reply to client
				turn++;
			}else{
				DataInputStream dis = new DataInputStream(s.getInputStream());	//from where to read 
				String st = dis.readLine();				// reading
				System.out.println("From Bob: "+st);
				System.out.println("Enter what is asked");
				dis = new DataInputStream(System.in);
				st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(st);							// reply to client
				turn++;
			}
		}
	}

	static long fun(long vx, long d, long n){
		long k = 1;
		for(long i=0;i<d;i++){
			k = k*vx;
			if(k>n) k = k%n;
		}
		return k;
	}
}