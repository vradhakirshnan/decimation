package math;

import java.lang.Math;
import java.io.*;
import math.*;
import data.*;


public class vector{

	double m_i,m_j,m_k;

	public vector(double m_i,double m_j, double m_k)
	{
		this.m_i=m_i;
		this.m_j=m_j;
		this.m_k=m_k;
	}

	public vector(point _p1, point _p2)
	{
		this.m_i=_p1.ret_x() - _p2.ret_x();
		this.m_j=_p1.ret_y() - _p2.ret_y();
		this.m_k=_p1.ret_z() - _p2.ret_z();
	}

	public double GetI(){return m_i;}

	public double GetJ(){return m_j;}

	public double GetK(){return m_k;}


	public double dot(vector _v)
	{
		return this.m_i * _v.GetI() +
			   this.m_j * _v.GetJ() +
			   this.m_k * _v.GetK();
	}

	public vector cross(vector _v)
	{
		double newi= this.m_j * _v.GetK() - this.m_k*_v.GetJ();
		double newj= this.m_k * _v.GetI() - this.m_i*_v.GetK();
		double newk= this.m_i * _v.GetJ() - this.m_j*_v.GetI();
		return new vector(newi,newj,newk);
	}

	public boolean IsNull()
	{
		if(this.m_i==(double)0)
			if(this.m_j==(double)0)
				if(this.m_k==(double)0) return true;

		return false;
	}

	public double magnitude()
	{
		return Math.sqrt(this.dot(this));
	}

	public void normalize()
	{
		double mag=this.magnitude();
		this.m_i=m_i/mag;
		this.m_j=m_j/mag;
		this.m_k=m_k/mag;
	}

	public void plot_normal(PrintStream pr)
	{
		pr.println("[" +m_i + " " + m_j+ " " + m_k+ "]" );
	}
	public vector transform(square tm)
	{
		if(tm.ret_rows()==3)
		{
			double _i = this.m_i *tm.retvalue(0,0)+
						this.m_j *tm.retvalue(1,0)+
						this.m_k *tm.retvalue(2,0);

			double _j = this.m_i *tm.retvalue(0,1)+
						this.m_j *tm.retvalue(1,1)+
						this.m_k *tm.retvalue(2,1);

			double _k = this.m_i *tm.retvalue(0,2)+
						this.m_j *tm.retvalue(1,2)+
						this.m_k *tm.retvalue(2,2);
			return new vector(_i,_j,_k);

		}
		return null;
	}
	public void print_vector()
	{
		System.out.println(this.m_i +" "+ this.m_j +" "+ this.m_k);
	}


}