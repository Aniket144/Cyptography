import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Alice
{
	public static void main(String ar[])throws IOException
	{
		Scanner sc = new Scanner(System.in);

		// Variables for RSA 
		long n = 187, e = 23, d = 7;
		System.out.println("\tRSA Variables : (public)n="+n+" (public)e="+e+" (secret)d="+d);
		System.out.print("\tMessage (secret)m0 = ");
		long m0 = sc.nextLong();
		System.out.print("\tMessage (secret)m1 = ");
		long m1 = sc.nextLong();
		
		long x0=(long)(Math.random()*100);
		long x1=(long)(Math.random()*100);

		System.out.println("\tx0 = "+x0+" x1 = "+x1);
		ServerSocket ss = new ServerSocket(2000);
		System.out.println("Waiting For Bob");
		Socket s = ss.accept();	// waiting for client
		System.out.println("Bob is connected\n");

		//ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		//Message message = new Message(new Integer(15), new Integer(32));
		//os.writeObject(message);
		int turn = 1;
		long v, k0, k1;
		long m0i, m1i;
		while(true)
		{
			if(turn<5){
				DataInputStream dis = new DataInputStream(s.getInputStream());	//from where to read 
				String st = dis.readLine();				// reading
				System.out.println("From Bob: "+st);
				System.out.println("Enter what is asked");
				dis = new DataInputStream(System.in);
				st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(st);							// reply to client
				turn++;
			}else if(turn==5){
				DataInputStream dis = new DataInputStream(s.getInputStream());	//from where to read 
				String st = dis.readLine();		
				v = Long.parseLong(st);
				k0 = fun(v-x0,d,n);
				k1 = fun(v-x1,d,n);
				m0i = m0 + k0;
				m1i = m1 + k1;
				System.out.println(" m0\' = "+m0i+"  m1\' = "+m1i);
				turn++;
			}else if(turn==6|| turn==7){
				DataInputStream dis = new DataInputStream(s.getInputStream());	//from where to read 
				String st = dis.readLine();				// reading
				System.out.println("From Bob: "+st);
				System.out.println("Enter what is asked");
				dis = new DataInputStream(System.in);
				st = dis.readLine();
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(st);							// reply to client
				turn++;
			}else{
				break;
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