package math;

import math.*;
import java.lang.Math;


public class square extends twoD
{
	public square(int size)
	{
		super(size,size);
	}


	public square(double[][] values)
	{
		if(values.length==values[0].length)
		{
			this.m_matrix= new double[values.length][values.length];
			for(int i=0;i<values.length;i++)
			{
				for(int j=0;j<values.length;j++)
				{
					this.m_matrix[i][j]=values[i][j];
				}
			}

		}

	}
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
		matrix ret = new square(temp);
		return ret;
	}
	/**********************************************************/
	public double determinant()
	{
	//if(this.rows==this.collumns)
	//{
		double det=(double)0;
		if(this.ret_rows()==2)
		{
			det=this.m_matrix[0][0] * this.m_matrix[1][1] - this.m_matrix[0][1] * this.m_matrix[1][0];
		}
		else
		{
			for(int i=0;i<this.ret_rows();i++)
			{
				det=det + this.m_matrix[0][i]* java.lang.Math.pow((double)-1,(double)i)* ((square)this.get_cofactor(0,i)).determinant();
			}
		}
		return det;
		//}

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
		matrix m = new square(newvalues);
		return m;
	}
	/**********************************************************/
	public square inverse()
	{
		if(this.determinant()==(double)0)return null;
		int rows=this.ret_rows();
		int collumns= this.ret_collumns();
		double[][] temp= new double[rows][collumns];
		for(int i=0;i<rows;i++)
			for(int j=0;j<rows;j++)
			{
				temp[i][j]= Math.pow((double)-1,(double)i)*
							Math.pow((double)-1,(double)j)*
							((square)this.get_cofactor(i,j)).determinant();
			}
		for(int i=0;i<rows;i++)
		for(int j=0;j<rows;j++)temp[i][j]=temp[i][j]/this.determinant();
		square m = new square(temp);
		m=(square)m.transpose();
		return m;
	}
	/**********************************************************/
	public static void main(String args[])
	{
		double values[][]= new double[3][3];
		double _values[]= new double[3];
		values[0][0]=1;
		values[0][1]=0;
		values[0][2]=1;

		values[1][0]=1;
		values[1][1]=1;
		values[1][2]=0;

		values[2][0]=3;
		values[2][1]=0;
		values[2][2]=1;

		_values[0]=2;
		_values[1]=-1;
		_values[2]=3;

		/*_values[1][0]=0;
		_values[1][1]=-1;
		_values[1][2]=2;

		_values[2][0]=-2;
		_values[2][1]=4;
		_values[2][2]=1;*/

		matrix sq1 = new square(values);
		matrix sq2 = new collumn(_values);
		//System.out.println(sq.ret_rows()+ "  " + sq.ret_collumns() + " " + sq.determinant());
		//sq.print();
		//System.out.println("/**************************/");
		//matrix _transpose=sq.transpose();
		//square _inverse=sq.inverse();
		//_inverse.print();
		//System.out.println("/**************************/");
		//(((square)_transpose).inverse()).print();
		//System.out.println("/**************************/");
		//(_inverse.transpose()).print();
		(sq1.mulitply(sq2)).print();

	}
	/**********************************************************/


}