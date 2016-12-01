package math;

import math.*;
import java.io.PrintStream;

public interface matrix
{

	public int ret_rows();
	public int ret_collumns();
	double retvalue(int i, int j);


	public void print();
	public void print(PrintStream pr);


	public void add(matrix m);
	public matrix Add(matrix m);
	public void subtract(matrix m);

	public matrix transpose();
	public matrix mulitply(matrix m);
	//public matrix scalar(double const);




}
