package math;

import java.lang.Math;
import java.io.PrintStream;
import math.*;


public class scalar implements matrix
{

	double m_matrix;

	public scalar(double _scalar)
	{
		this.m_matrix = _scalar;
	}
	public scalar()
	{
		this.m_matrix = 0.0;
	}

	public double retvalue(int i, int j)
	{
		return this.m_matrix;
	}

	public int ret_rows(){return 1;}
	public int ret_collumns(){return 1;}



	public matrix mulitply(matrix m){//return m.scalar(this.m_matrix);
										return null;}

	public void print(){System.out.println(m_matrix);}
	public void print(PrintStream pr){pr.println("\n" + m_matrix+ "\n");}

	public void  add(matrix m){}
	public matrix  Add(matrix m){return m;}
	public void  subtract(matrix m){}

	public matrix transpose()
	{
		return this;
	}
}