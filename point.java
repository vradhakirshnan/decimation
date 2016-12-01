package data;

import java.io.*;
import math.*;
import java.util.ArrayList;
import java.lang.reflect.Array;


public class point{

	double x,y,z;

	public int[] faces;

	public point(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public point center( point p)
	{
		return new point((this.x + p.ret_x())/2,(this.y + p.ret_y())/2,(this.z + p.ret_z())/2);
	}

	public double ret_x(){return this.x;}
	public double ret_y(){return this.y;}
	public double ret_z(){return this.z;}

	public void set_x(double x){this.x = x;}
	public void set_y(double y){this.y = y;}
	public void set_z(double z){this.z = z;}


	public void print_point()
	{
	//	System.out.println(this.x +" "+ this.y +" "+ this.z + "\n");
		for(int i=0;i<faces.length ;i++)
		{
			System.out.print("pt  " + faces[i]);
		}
		System.out.println();

	}

	public boolean equal(point p)
	{
		boolean ret = false;
		if(this.x == p.ret_x() && this.y == p.ret_y() && this.z == p.ret_z()) ret = true;
		return ret;
	}

	public point translate(vector _v)
	{
		return new point(x+_v.GetI(),y+_v.GetJ(),z+_v.GetK());
	}

	public void plot_point(PrintStream pr)
	{
		pr.print("[" + this.x +" "+ this.y +" "+ this.z + "] ");
	}
	/**********************************************/

	public point transform(square tm)
	{
		if(tm.ret_rows()==3)
		{
			double _x = this.x *tm.retvalue(0,0)+
						this.y *tm.retvalue(1,0)+
						this.z *tm.retvalue(2,0);

			double _y = this.x *tm.retvalue(0,1)+
						this.y *tm.retvalue(1,1)+
						this.z *tm.retvalue(2,1);

			double _z = this.x *tm.retvalue(0,2)+
						this.y *tm.retvalue(1,2)+
						this.z *tm.retvalue(2,2);
			return new point(_x,_y,_z);

		}
		if(tm.ret_rows()==4)
		{
			double _x = this.x *tm.retvalue(0,0)+
						this.y *tm.retvalue(1,0)+
						this.z *tm.retvalue(2,0)+
						1 * tm.retvalue(3,0);

			double _y = this.x *tm.retvalue(0,1)+
						this.y *tm.retvalue(1,1)+
						this.z *tm.retvalue(2,1)+
						1 * tm.retvalue(3,1);;

			double _z = this.x *tm.retvalue(0,2)+
						this.y *tm.retvalue(1,2)+
						this.z *tm.retvalue(2,2)+
						1 * tm.retvalue(3,2);
			return new point(_x,_y,_z);

		}
		return null;
	}
	/**********************************************/
	void loadfaces(int[] faces)
	{
		this.faces=faces;
	}
	void loadfaces(point _p)
	{
		//obtained from code samples java...
		Object newfaces= Array.newInstance(this.faces.getClass().getComponentType(), Array.getLength(this.faces) +Array.getLength(_p.faces) );
		System.arraycopy(this.faces, 0, newfaces, 0, Array.getLength(this.faces));
		System.arraycopy(_p.faces, 0, newfaces, Array.getLength(this.faces), Array.getLength(_p.faces));
		this.faces= null;
		this.faces=(int[])newfaces;
	}

	/**********************************************/

	void removeface(int face)
	{
		boolean found = true;
		for(int i=0;i<faces.length && found;i++)
		{
			if(faces[i]==face){faces[i]=-1;found= false;}
		}
	}
	public static void main(String args[])
	{
		point p1 = new point((double)0,(double)0,(double)0);
		point p2 = new point((double)4,(double)0,(double)0);
		point p3 = new point((double)0,(double)0,(double)0);
		point p4 = new point((double)0,(double)4,(double)0);
		if(p1.equal(p2)){System.out.println("p2 -p1");p2.print_point();}
		if(p1.equal(p3)){System.out.println("p3-p1");p3.print_point();}
		if(p4.equal(p2)){System.out.println("p2-p4");p2.print_point();}


	}
}