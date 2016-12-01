package math;

import math.*;
import java.io.PrintStream;
import java.lang.Math;


public class twoD implements matrix
{
	double m_matrix[][];
	public twoD(){}
	public twoD(int rows, int collumns)
	{
		 this.m_matrix = new double[rows][collumns];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<collumns;j++)
			{
				this.m_matrix[i][j]=0.0;
			}
		}

	}

	public twoD(double[][] values)
	{
		this.m_matrix= new double[values.length][values[0].length];
		for(int i=0;i<values.length;i++)
		{
			for(int j=0;j<values[0].length;j++)
			{
				this.m_matrix[i][j]=values[i][j];
			}
		}
	}
	/**********************************************************/
	public double retvalue(int i, int j){ return this.m_matrix[i][j];}
	public int ret_rows(){return m_matrix.length;}
	public int ret_collumns(){return m_matrix[0].length;}
	/**********************************************************/
	public matrix get_cofactor(int row,int collumn)
	{
		double[][] temp =  new double[this.ret_rows()-1][this.ret_collumns()-1];
		int nrow=0;
		int ncollumn=0;
		for(int i=0;i<this.ret_rows();i++)
		{
			if(i!=row)
			{

				for(int j=0;j<this.ret_collumns();j++)
				{
					if(nrow<this.ret_rows()-1)
					{
						if(j!=collumn)
						{
							temp[nrow][ncollumn]=retvalue(i,j);
							ncollumn++;
							if(ncollumn==this.ret_collumns()-1)
							{
								ncollumn=0;
								nrow++;
							}


						}
					}
				}
			}
		}
		matrix ret = new twoD(temp);
		return ret;
	}
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
	public void  add(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			for(int i=0;i<this.ret_rows();i++)
			{
				for(int j=0;j<ret_collumns();j++)
				{
					m_matrix[i][j] =m_matrix[i][j] + m.retvalue(i,j);
				}
			}
		}
	}
	/**********************************************************/
	public matrix Add(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			double _values[][] = new double [ret_rows()][ret_collumns()];
			for(int i=0;i<this.ret_rows();i++)
			{
				for(int j=0;j<ret_collumns();j++)
				{
					_values[i][j] =m_matrix[i][j] + m.retvalue(i,j);
				}
			}
			matrix ret = new twoD(_values);
			return ret;
		}
		return null;
	}
	/**********************************************************/
	public void  subtract(matrix m)
	{
		if(this.ret_rows()==m.ret_rows() && this.ret_collumns()==m.ret_collumns())
		{
			for(int i=0;i<this.ret_rows();i++)
			{
				for(int j=0;j<this.ret_rows();j++)
				{
					m_matrix[i][j] =m_matrix[i][j] - m.retvalue(i,j);
				}
			}
		}
	}
	/**********************************************************/
	public void print()
	{
		int rows=this.ret_rows();
		int collumns=this.ret_collumns();
		for(int i=0;i<rows;i++)
		{
			System.out.println();
			for(int j=0;j<collumns;j++)System.out.print(this.m_matrix[i][j] + "    ");
		}

	}
	/**********************************************************/
	public void print(PrintStream pr)
	{
		int rows=this.ret_rows();
		int collumns=this.ret_collumns();
		for(int i=0;i<rows;i++)
		{
			pr.println();
			for(int j=0;j<collumns;j++)pr.print(this.m_matrix[i][j] + "    ");
		}
		pr.println();

	}
	/**********************************************************/
	public matrix transpose()
	{
		double newvalues[][]=new double[ret_collumns()][ret_rows()];
		for(int i=0;i<ret_rows();i++)
		{
			for(int j=0;j<ret_collumns();j++)
			{
				newvalues[j][i]=this.m_matrix[i][j];
			}
		}
		matrix m = new twoD(newvalues);
		return m;
	}
	/**********************************************************/

}