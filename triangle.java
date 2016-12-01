package data;

import java.io.*;
import math.*;
import java.util.ArrayList;

import data.point;

public class triangle{

	// list ot store the points
	point p1,p2,p3;
	point centroid;

	//contructor to intialize the object
	public triangle(point p1,point p2,point p3)
	{
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
	}

	//returns the number  of points
	public int ret_num(){return 3;}

	//returns the  point of current index..
	public point  ret_point(int index)
	{
		if(index==1)
		{
			return p1;
		}
		if(index==2)
		{
			return p2;
		}
		if(index==3)
		{
			return p3;
		}
		return null;

	}

	//
	/**********************************************/


	//
	public void calc_centroid()
	{
		double x=(p1.ret_x()+ p2.ret_x() + p3.ret_x())/(double)3;
		double y=(p1.ret_y()+ p2.ret_y() + p3.ret_y())/(double)3;
		double z=(p1.ret_z()+ p2.ret_z() + p3.ret_z())/(double)3;
		this.centroid = new point(x,y,z);

	}
	/**********************************************/
	public point ret_centroid()
	{
		if (this.centroid==null)calc_centroid();
		return this.centroid;
	}
	/**********************************************/

	public void print_face()
	{
		p1.print_point();
		p2.print_point();
		p3.print_point();
	}

	/**********************************************/
	public point edje_center(int index)
	{

		if(index==1)
		{
			return new point( (p1.ret_x() +p2.ret_x())/(double)2,
							  (p1.ret_y() +p2.ret_y())/(double)2,
							  (p1.ret_z() +p2.ret_z())/(double)2);
		}
		if(index==2)
		{
			return new point( (p2.ret_x() +p3.ret_x())/(double)2,
							  (p2.ret_y() +p3.ret_y())/(double)2,
							  (p2.ret_z() +p3.ret_z())/(double)2);
		}
		if(index==3)
		{
			return new point( (p3.ret_x() +p1.ret_x())/(double)2,
							  (p3.ret_y() +p1.ret_y())/(double)2,
							  (p3.ret_z() +p1.ret_z())/(double)2);
		}
		return null;
	}
	/**********************************************/
	public vector GetNormal()
	{
		vector _v1 = new vector(p2,p1);
		vector _v2 = new vector(p3,p2);
		return _v1.cross(_v2);
	}

	/**********************************************/
	public void plot_face(PrintStream pr)
	{
		pr.print("[[");
		p1.plot_point(pr);
		p2.plot_point(pr);
		p3.plot_point(pr);
		pr.print("] dup normal-function]\n");
	}
	/**********************************************/
	public matrix GetPlane()
	{

		double plane[] = new double[4];
		//solve for plane...
		double[][] values= new double[3][3];
		values[0][0]=1.0;
		values[0][1]=1.0;
		values[0][2]=1.0;
		values[1][0]=p1.ret_y();
		values[1][1]=p2.ret_y();
		values[1][2]=p3.ret_y();
		values[2][0]=p1.ret_z();
		values[2][1]=p2.ret_z();
		values[2][2]=p3.ret_z();
		matrix m1 = new square(values);
		//m1.print();
		//System.out.println();
		plane[0]=((square)m1).determinant();

		values[1][0]=p1.ret_z();
		values[1][1]=p2.ret_z();
		values[1][2]=p3.ret_z();
		values[2][0]=p1.ret_x();
		values[2][1]=p2.ret_x();
		values[2][2]=p3.ret_x();
		matrix m2 = new square(values);
		//m2.print();
		//System.out.println();
		plane[1]=((square)m2).determinant();

		values[1][0]=p1.ret_x();
		values[1][1]=p2.ret_x();
		values[1][2]=p3.ret_x();
		values[2][0]=p1.ret_y();
		values[2][1]=p2.ret_y();
		values[2][2]=p3.ret_y();
		matrix m3 = new square(values);
		//m3.print();
		//System.out.println();
		plane[2]=((square)m3).determinant();

		values[0][0]=p1.ret_x();
		values[0][1]=p2.ret_x();
		values[0][2]=p3.ret_x();
		values[1][0]=p1.ret_y();
		values[1][1]=p2.ret_y();
		values[1][2]=p3.ret_y();
		values[2][0]=p1.ret_z();
		values[2][1]=p2.ret_z();
		values[2][2]=p3.ret_z();
		matrix m4 = new square(values);
		//m4.print();
		//System.out.println();
		plane[3]=-1*((square)m4).determinant();

		matrix ret =  new row(plane);
		return ret;
	}

	public matrix formQmatrix()
	{
		row plane = (row)GetPlane();
		double [][] _values = new double [4][4];
		for(int i=0;i<4;i++)
		{
			double temp1=plane.retvalue(1,i);
			for(int j=0;j<4;j++)
			{
				double temp2=plane.retvalue(1,j);
				_values[i][j]=temp1*temp2;
			}
		}
		matrix ret = new square(_values);
		return ret;
	}

	public static void main(String args[])
	{
		point p1 =  new point(3.0,4.0,1.0);
		point p2 =  new point(-2.0,1.0,0.0);
		point p3 =  new point(0.0,0.0,1.0);
		triangle t = new triangle(p1,p2,p3);
		row r = (row)t.GetPlane();
		r.print();
		System.out.println("\n\n\n");
		square sq = (square)t.formQmatrix();
		sq.print();
	}


}
