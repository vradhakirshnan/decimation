package math;

import java.lang.Math;
import java.io.PrintStream;
import math.*;


public class collumn implements matrix
{

	double m_matrix[];

/**********************************************************/
	public collumn(int size)
	{
		this.m_matrix = new double[size];
		for(int i=0;i<size;i++)
		{
				this.m_matrix[i]=0.0;
		}
	}
/**********************************************************/
	public collumn(double[] values)
	{
		this.m_matrix = new double[values.length];
		for(int i=0;i<values.length;i++)this.m_matrix[i]=values[i];
	}
/**********************************************************/
	public collumn(int[] values)
	{
		this.m_matrix = new double[values.length];
		for(int i=0;i<values.length;i++)this.m_matrix[i]=(double)values[i];
	}

/**********************************************************/
	public double retvalue(int i, int j)
	{
		return this.m_matrix[i];
	}
/**********************************************************/
	public int ret_rows(){return m_matrix.length;}
/**********************************************************/
	public int ret_collumns(){return 1;}

/**********************************************************/

	public matrix mulitply(matrix m)
	{
		int newcols;
		int newrows;
		matrix ret;
		if(this.ret_collumns()!=m.ret_rows())return null;
		newrows=this.ret_rows();
		newcols=m.ret_collumns();
		if(newcols==1 && newrows==1)
		{
			double _scalar = 0.0;
			for(int i=0;i<this.ret_collumns();i++)
			{
				_scalar=_scalar + this.retvalue(1,i)*m.retvalue(i,1);
			}
			ret= new scalar(_scalar);
			return ret;
		}
		if(newcols==1)
		{

			double[] _collumn = new double[newrows];
			for(int i = 0;i<this.ret_rows();i++)
			{
				_collumn[i]=0.0;
				for(int j=0;j<m.ret_rows();j++)
				{
					_collumn[i]=_collumn[i]+this.retvalue(i,j)*m.retvalue(j,1);
				}
			}

			ret= new collumn(_collumn);
			return ret;
		}
		if(newrows==1)
		{
			double[] _row = new double[newrows];
			for(int i = 0;i<this.ret_collumns();i++)
			{
				double _scalar = 0.0;
				for(int j=0;j<m.ret_collumns();j++)
				{
					_row[i]=_row[i]+this.retvalue(1,j)*m.retvalue(j,i);
				}
			}

			ret= new row(_row);
			return ret;
		}
		double [][]_values=new double[newrows][newcols];
		for(int i=0;i<newrows;i++)
		{
			for(int j=0;j<newcols;j++)
			{
				_values[i][j]=0.0;
				for(int k =0;k<this.ret_collumns();k++)
				{
					_values[i][j]=_values[i][j] + this.retvalue(i,k)*m.retvalue(k,j);
				}
			}
		}

		if(newrows==newcols)ret = new square(_values);
		else ret = new twoD(_values);
		return ret;


	}
/**********************************************************/
	public void print()
	{
		System.out.println();
		for(int i=0;i<m_matrix.length;i++)
		{
			System.out.println(m_matrix[i]);
		}
		System.out.println();
	}
/**********************************************************/
	public void print(PrintStream pr)
	{
		pr.println();
		for(int i=0;i<m_matrix.length;i++)
		{
			pr.println(m_matrix[i]);
		}
		pr.println();
	}
/**********************************************************/
	public void  add(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			for(int j=0;j<this.ret_rows();j++)
			{
				m_matrix[j] =m_matrix[j] + m.retvalue(j,1);
			}
		}
	}
/**********************************************************/
	public matrix Add(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			double _values[] = new double[this.ret_rows()];
			for(int j=0;j<this.ret_rows();j++)
			{
				_values[j] =m_matrix[j] + m.retvalue(j,1);
			}
			matrix ret = new collumn(_values);
			return ret;
		}
		return null;

	}
/**********************************************************/
	public void  subtract(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			for(int j=0;j<this.ret_rows();j++)
			{
				m_matrix[j] =m_matrix[j] - m.retvalue(j,1);
			}
		}
	}
/**********************************************************/

	public matrix transpose()
	{
		matrix m = new row(this.m_matrix);
		return m;
	}
/**********************************************************/
	public static void main(String args[])
	{
	}
}