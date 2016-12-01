package data;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.reflect.Array;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;


import math.*;
import data.*;

class error
{
	public int v1;
	public int v2;
	public double m_error;
	public point _v;
	public error(int v1, int v2)
	{
		this.v1=v1;
		this.v2=v2;
	}
	public void print(PrintStream pr)
	{
		pr.println(v1  + " \t" + v2);
	//	_v.plot_point(pr);
	//	pr.println("error\t"+ m_error);
	}

	public void print()
	{
		System.out.println("\n"+ v1  + " \t" + v2);
		_v.print_point();
		System.out.println("error\t"+ m_error);
	}


	public boolean Compare(error _e)
	{
		return(m_error<_e.m_error);
	}
	public boolean Contains(int _v)
	{
		if(this.v1==_v)return true;

		if(this.v2==_v)return true;

		return false;
	}
	public void Replace(int _v,int _r)
	{
		if(this.v1==_v)this.v1=_r;
		if(this.v2==_v)this.v2=_r;
	}


}

class HeapErrorList
{
	public error []errors;
	int length;
	public HeapErrorList()
	{
		errors = new error[1000];
		length=0;
	}


	public void AddErrorObject(error _e)
	{
		//errors.add(_e);
		//arrange heap..
		if(errors.length -length <2)expand();
		if(length==0)
		{
			errors[length]=_e;
			length++;
			return;
		}
		int hole = length++;
		while(true)
		{
			int parent = (int)((hole-1)/2);
			if(hole==0)//put
			{
				errors[hole]=_e;
				return;
			}

			if(errors[parent].Compare(_e))//put
			{
				errors[hole]=_e;
				return;
			}

			else//swap stuff
			{
				errors[hole]=errors[parent];
				errors[parent]= null;
				hole= parent;
			}
		}

	}
	public error GetLeastError()
	{
		//remove element
		int hole=0;
		error least =errors[hole];
		length--;
		error last =errors[length];
		//errors[length]= null;

		while(true)
		{
			int left_child = 2*hole+1;
			int right_child = 2*hole+2;
			int child;
			if(left_child>length)//no children
			{
				errors[hole]=last;
				return least;
			}
			else if(right_child>length) child = left_child;
			else child=(errors[left_child].Compare(errors[right_child]) ? left_child:right_child);
			if(last.Compare(errors[child]))
			{
				errors[hole]=last;
				return least;
			}
			else
			{
				errors[hole]=errors[child];
				hole=child;
			}

		}

	}
	//change the eroor value of the given error object and update the heap
	public void UpdateError(int position)
	{
		//find position of error and update if necessary...
		int hole = position;
		//check above...
		boolean found = true;
		while(found)
		{
			int parent =(int)((hole-1)/2);
			if(hole==0)return;
			if(errors[parent].Compare(errors[hole])) found=false;
			else
			{
				//swap parent and child
				error temp =errors[parent];
				errors[parent]=errors[hole];
				errors[hole]=temp;
				//set child to parent..
				hole=parent;
			}
		}
		//or
		//check below...
		while(true)
		{
			int left_child=2*hole+1;
			int right_child=2*hole+2;
			int child;
		/*	System.out.println("length :" + length);
			System.out.println("hole:" + hole);
			System.out.println("left_child :" + left_child);
			System.out.println("right_child:" + right_child);*/

			if(left_child>=length)return;//no children
			if(right_child>=length)child=left_child;
			else child=(errors[left_child].Compare(errors[right_child]) ? left_child:right_child);
			if(errors[hole].Compare(errors[child])) return;
			else
			{
				//swap parent and child
				error temp =errors[child];
				errors[child]=errors[hole];
				errors[hole]=temp;
				//set child to parent..
				hole=child;

			}

		}
	}
	void RemoveError(int _v1, int _v2)
	{
		//remove element
		int hole=0;
		boolean found = false;
		for(int i=0;i<length && !found ;i++)
		{
			if(errors[i].Contains(_v1) && errors[i].Contains(_v2))
			{
				found=true;
				hole=i;
			}
		}


		if(found)
		{
		//	System.out.print("  removed  " + _v1 + " " + _v2);
			error least =errors[hole];
			length--;
			error last =errors[length];
	//		errors[length]= null;

			while(true)
			{
				int left_child = 2*hole+1;
				int right_child = 2*hole+2;
				int child;
				if(left_child>length)//no children
				{
					errors[hole]=last;
					return;
				}
				else if(right_child>length) child = left_child;
				else child=(errors[left_child].Compare(errors[right_child]) ? left_child:right_child);
				if(last.Compare(errors[child]))
				{
					errors[hole]=last;
					return;
				}
				else
				{
					errors[hole]=errors[child];
					hole=child;
				}

			}
		}


	}
	public void print(PrintStream pr)
	{
		for(int i=0;i<length;i++)
		{
			errors[i].print(pr);
		}
	}

	public void print()
	{
		for(int i=0;i<length;i++)
		{
			errors[i].print();
		}
	}
	void expand()
	{
		//obtained from code samples java...
		Object newerrors= Array.newInstance(errors.getClass().getComponentType(), Array.getLength(errors)*2);
		System.arraycopy(errors, 0, newerrors, 0, Array.getLength(errors));
		errors= null;
		errors=(error[])newerrors;
	}



}

public class object{
	int num_of_points,num_of_faces, num_of_edges;
	ArrayList points;
	int faces[][];

	boolean formed;
	double max_x=(double)0, max_y=(double)0;
	double min_x=(double)0, min_y=(double)0;



	matrix q_error[];
	HeapErrorList h;

	public int ret_num_faces(){return this.num_of_faces;}
	public int ret_num_points(){return this.num_of_points;}
	public int ret_num_edges(){return this.num_of_edges;}
	public ArrayList ret_points(){return this.points;}
	public int[][] ret_faces(){return this.faces;}


	public double ret_max_x(){return this.max_x;}
	public double ret_max_y(){return this.max_y;}
	public double ret_min_x(){return this.min_x;}
	public double ret_min_y(){return this.min_y;}


	double get_x(int i)
	{
		double x = ((point)this.points.get(i)).ret_x();
		double z = ((point)this.points.get(i)).ret_z();
		return conv(x,z);

	}

	double get_y(int i)
	{
		double y = -((point)this.points.get(i)).ret_y();
		double z = -((point)this.points.get(i)).ret_z();
		return conv(y,z);
	}
/**********************************************/




/**********************************************/
	public object(int num_of_points,int num_of_faces,int num_of_edges, ArrayList points, int[][] faces )
	{
		this.num_of_points=num_of_points;
		this.num_of_faces=num_of_faces;
		this.num_of_edges=num_of_edges;
		this.points = points;
		this.faces = faces;
		this.formed =false;
		//normals= new vector[num_of_faces];
		triangulate();
		q_error= new square[num_of_points];
		for(int i=0;i<this.num_of_points;i++)ComputeErrors(i);
		h = new HeapErrorList();
		edgenumber();
		bounding_box();

	}
/**********************************************/
	public object(String Filename)
	{
		try{
				FileInputStream fis = new FileInputStream(Filename);
				InputStreamReader in = new InputStreamReader((InputStream)fis);
				BufferedReader br = new BufferedReader(in);
				String off =(br.readLine()).trim();
				if(off.equals("OFF"))
				{
					String ln=(br.readLine()).trim();
					ArrayList a = split_string(ln);
					this.num_of_points = Integer.parseInt((String)a.get(0));
					this.num_of_faces = Integer.parseInt((String)a.get(1));
					this.num_of_edges = Integer.parseInt((String)a.get(2));
					this.points =  new ArrayList();
					this.faces = new int[this.num_of_faces][];
					for(int i=0;i<this.num_of_points;i++)
					{
						String pts = (br.readLine()).trim();
						ArrayList ps = split_string(pts);
						float x = Float.parseFloat((String)ps.get(0));
						float y = Float.parseFloat((String)ps.get(1));
						float z = Float.parseFloat((String)ps.get(2));
						point p = new point(x,y,z);
						//p.print_point();
						this.points.add(p);
					}
					for(int i=0;i<this.num_of_faces;i++)
					{
						String fcs= (br.readLine()).trim();
						ArrayList fs = split_string(fcs);
						int np =  Integer.parseInt((String)fs.get(0));
						this.faces[i]= new int[np+1];
						this.faces[i][0]=np;
						//System.out.println(this.faces[i][0]);
						for(int j=1;j<=np;j++)
						{
						 	this.faces[i][j]=Integer.parseInt((String)fs.get(j));
						// 	System.out.print(this.faces[i][j]+ " ");

						}
						//System.out.println();
					}
					this.formed =true;
					//subdivide();
					//this.normals= new vector[this.num_of_faces];
					triangulate();
					q_error= new square[num_of_points];
					loadfaces();
					for(int i=0;i<this.num_of_points;i++)ComputeErrors(i);
					h = new HeapErrorList();
					edgenumber();
					//TEST("aaaa.txt");
					bounding_box();

				}
				else
				{
					System.out.println("not an off file..");
					this.formed =false;
				}

			}
		catch(Exception e){	e.printStackTrace();}
	}
/**********************************************/
public void loadfaces()
{
	for(int i=0;i<this.num_of_points;i++)
	{
		int count=0;
		int[] temp= new int[this.num_of_faces];
		for(int j=0;j<this.num_of_faces;j++)
		{
			if(faces[j][1]==i){temp[count]=j;count++;}
			if(faces[j][2]==i){temp[count]=j;count++;}
			if(faces[j][3]==i){temp[count]=j;count++;}
		}
		int[] mfaces = new int[count];
		for(int j=0;j<count;j++)
		{
			mfaces[j]=temp[j];
		}
		((point)points.get(i)).loadfaces(mfaces);
	}
}


/**********************************************/

public double conv(double x1,double z1)
{
	double pi = 3.1415926535;
	double  rad  = (float)(pi/(float)4);
	return (double )((double )x1+((double )z1*(double )Math.cos(rad)));

}
/**********************************************/


void bounding_box()
{
	for(int i=0;i< this.num_of_points;i++)
	{
		double tempx =  conv(((point)this.points.get(i)).ret_x(),((point)this.points.get(i)).ret_z());
		double tempy =  conv(((point)this.points.get(i)).ret_y(),((point)this.points.get(i)).ret_z());
		if(tempx >max_x) max_x =  tempx;
		if(tempy >max_y) max_y =  tempy;
		if(tempx <min_x) min_x =  tempx;
		if(tempy <min_y) min_y =  tempy;
	}
	//System.out.println("\n" + max_x + "\t" + max_y + "\t" + min_x + "\t" + min_y + "\n");

}

/**********************************************/
	public void triangulate()
	{
		//number of faces to b removed,,,
		//number of triangles to b addes....
		int fcs=0;
		int cnt=0;
		for (int i=0;i<this.num_of_faces;i++)
		{
			if(this.faces[i][0]>3)
			{
				fcs =fcs+1;
				cnt = cnt + this.faces[i][0];
			}
		}
		int new_faces = this.num_of_faces +cnt - fcs;
	//	System.out.println(new_faces);
		int newfaces[][] =  new int[new_faces][4];

		cnt=0;
		for (int i=0;i<this.num_of_faces;i++)
		{

			if(this.faces[i][0]>3)
			{
				point newpoint = ((face)form_face(i)).calc_centroid();
				this.points.add(newpoint);
				int index= this.num_of_points;
				this.num_of_points ++;
				for(int j =1;j<this.faces[i][0];j++)
				{
					newfaces[cnt][0]=3;
					newfaces[cnt][1] =index;
					newfaces[cnt][2] =this.faces[i][j];
					newfaces[cnt][3] =this.faces[i][j+1];
					cnt++;
				}
				newfaces[cnt][0]=3;
				newfaces[cnt][1] =index;
				newfaces[cnt][2] =this.faces[i][this.faces[i][0]];
				newfaces[cnt][3] =this.faces[i][1];
				cnt++;

			}
			else
			{
				newfaces[cnt] = this.faces[i];
				cnt++;
			}
		}
		this.num_of_faces=cnt;
		this.faces=newfaces;
	//	System.out.println(cnt);

	}
/**********************************************/
public void ComputeErrors(int _v)
{
	//for(int i=0;i<this.num_of_points;i++)
	//{
	if(_v<this.num_of_points &&_v>=0)
	{
		point _p = (point)points.get(_v);
		q_error[_v] = null;
		q_error[_v] = new square(4);
		for(int j=0;j<_p.faces.length;j++)
		{
			if(_p.faces[j]>=0)
			{
				triangle t =form_triangle(_p.faces[j]);
				q_error[_v].add(t.formQmatrix());
			}
		}

	}
}

/**********************************************/

/**********************************************/
	private ArrayList split_string(String s)
	{
		ArrayList sub = new ArrayList();
		//System.out.println(s);
		int start_pos =s.indexOf(" ");
		//System.out.println(s);
		//System.out.println(start_pos);
		while(start_pos !=-1)
		{

			String str = s.substring(0,start_pos).trim();
			if(sub.add(str));//System.out.println(str);
			s = (s.substring(start_pos)).trim();
			start_pos =s.indexOf(" ");
			//System.out.println(s);
			//System.out.println(start_pos);
		}
		if(sub.add(s));//System.out.println(s);
		return  sub;
	}


/**********************************************/

	public void edgenumber()
	{
		int num=0;
		int arr[][]= new int[this.num_of_points][this.num_of_points];
		for(int i=0;i<this.num_of_points;i++)
		for(int j=0;j<this.num_of_points;j++)arr[i][j]=0;

		for(int i=0;i<this.num_of_faces;i++)
		{

			for(int j=1;j<this.faces[i][0];j++)
			{

				int pos1=this.faces[i][j];
				int pos2=this.faces[i][j+1];

				arr[pos1][pos2]=1;
				if(arr[pos2][pos1]==0)
				{
				//	System.out.println(pos1+"  "+pos2);
					error e = new error(pos1,pos2);
					point _p =(point)points.get(pos1);
					point _q =(point)points.get(pos2);
					e._v =_p.center(_q);
					double values[]= new double[4];
					values[0]=e._v.ret_x();
					values[1]=e._v.ret_y();
					values[2]=e._v.ret_z();
					values[3]=1.0;
					//v
					matrix c = new collumn(values);
					//v_transpose
					matrix r = new row(values);
					//sume of q1 and q2
					matrix q=q_error[pos1].Add(q_error[pos1]);
					matrix s =(scalar)r.mulitply(q.mulitply(c));
					e.m_error=s.retvalue(0,0);
					h.AddErrorObject(e);
					num++;
				}

			}

			int pos2=faces[i][1];
			int pos1=faces[i][this.faces[i][0]];
			//System.out.println(pos1+"  "+pos2);
			arr[pos1][pos2]=1;
			if(arr[pos2][pos1]==0)
			{
			//	System.out.println(pos1+"  "+pos2);
				error e = new error(pos1,pos2);
				point _p =(point)points.get(pos1);
				point _q =(point)points.get(pos2);
				e._v =_p.center(_q);
				double values[]= new double[4];
				values[0]=e._v.ret_x();
				values[1]=e._v.ret_y();
				values[2]=e._v.ret_z();
				values[3]=1.0;
				//v
				matrix c = new collumn(values);
				//v_transpose
				matrix r = new row(values);
				//sume of q1 and q2
				matrix q=q_error[pos1].Add(q_error[pos1]);
				matrix s =(scalar)r.mulitply(q.mulitply(c));
				e.m_error=s.retvalue(0,0);
				h.AddErrorObject(e);
				num++;
			}

		}
		this.num_of_edges =  num;
	//	System.out.println("edges: "+num);




	}
/**********************************************/
	int GetFace(int _v1,int _v2)
	{


		for(int i=0;i<this.num_of_faces;i++)
		{
			//System.out.print("\n" + i);
			for(int j=1;j<this.faces[i][0];j++)
			{
				//System.out.println(faces[i][0]);
				if(faces[i][j]==_v1 && faces[i][j+1]==_v2)return i;
			}
			if(faces[i][3]==_v1 && faces[i][1]==_v2)return i;
		}

		return -1;
	}
/**********************************************/
	int  get_valence(int p)
	{
		int ret =0;
		for(int i=0;i<this.num_of_faces;i++)
		{
			for(int j=1;j<=4;j++)
			{
				if(this.faces[i][j]==p )ret++;
			}
		}
		return ret;
	}
/**********************************************/
	face form_face(int pos)
	{
		face f = new face();
//		System.out.println(pos);
		for(int i=1;i<=this.faces[pos][0];i++)
		{
			f.add_point((point)this.points.get(this.faces[pos][i]));
		}
		f.add_point((point)this.points.get(this.faces[pos][1]));
		return f;
	}
/**********************************************/
public triangle	form_triangle(int pos)
	{
		return new triangle((point)points.get(faces[pos][1]),
							(point)points.get(faces[pos][2]),
							(point)points.get(faces[pos][3]));
	}
/**********************************************/
	public void save(String Filename)
	{
		//save into off file....
		//if(this.formed)System.out.println("already saved...");
		//else
		//{
			try{
				FileOutputStream fos = new FileOutputStream (Filename);

				PrintStream pr =new PrintStream((OutputStream)fos);
		//		edgenumber();
				pr.println("OFF");
				pr.print(this.num_of_points +" " + this.num_of_faces + " " + this.num_of_edges);
				pr.println();
				for(int i=0;i<this.num_of_points;i++)
				{
					double x = ((point)this.points.get(i)).ret_x();
					double y = ((point)this.points.get(i)).ret_y();
					double z = ((point)this.points.get(i)).ret_z();
					pr.println(x +" " + y + " " + z);
				}
				for(int i=0;i<this.num_of_faces;i++)
				{

					for(int j=0;j<=this.faces[i][0];j++)
					{
						pr.print( this.faces[i][j] +" ");
					}
					pr.println();
				}
				pr.close();
				this.formed =true;
			}

			catch(Exception e){e.printStackTrace();}
		//}

	}
	/**********************************************/
	public void lineto(PrintStream pr,point p)
	{
		pr.println(p.ret_x() +"\t" + p.ret_y() + "\t" +p.ret_z() + "\tlineto3d");
	}

	/**********************************************/
	public void moveto(PrintStream pr,point p)
	{
		pr.println(p.ret_x() +"\t" + p.ret_y() + "\t" +p.ret_z() + "\tmoveto3d");
	}

	/**********************************************/


	/**********************************************/
	public void plot_object(String Filename)
	{
		try{
				FileOutputStream fos = new FileOutputStream (Filename + ".ps");

				PrintStream pr =new PrintStream((OutputStream)fos);


				double min;
				double trans  = (double)300;
				pr.println("\n"+ "%!"+ " \n");
				pr.println("\n (matrix.inc) run \n");
				pr.println("\n (ps3d.inc) run\n");

				if((max_x -min_x)>(max_y -min_y)) min =(max_x -min_x);
				else min =(max_y -min_y);
				double scale= 200/min;

				//define object in postsript..
				//filename.i= face
				pr.println("/"+ Filename+ "[\n");
				for(int i=0;i<this.num_of_faces;i++)
				{
					face f= form_face(i);
					f.plot_face(pr);
				}
				pr.println("]def");

				//define normals for each face... in postsript..
				//normal.i = normal for face.i in filename
				pr.println("/centroids[\n");
				for(int i=0;i<this.num_of_faces;i++)
				{
					//normals[i].plot_normal(pr);
					face f= form_face(i);
					point centroid = f.calc_centroid();
					//centroid.print_point();
					centroid.plot_point(pr);
					pr.println();
				}
				pr.println("]def");




				pr.println("\n newpath \n");
				pr.println("\n [.5 0 .5 0] set-eye\n");
				pr.println("250 350 0 translate3d \n");

				pr.println( scale + "\t " + scale + "\t " + scale + "\t scale3d \n");
				pr.println("0.05 setlinewidth\n");

				pr.println("/E get-eye ctm3d 1 get transform3d def");
				pr.println("/light-source [0 1 0 0]normalized def");
				pr.println("/L light-source ctm3d 1 get transform3d def");
				pr.println("/count 0 def");
				pr.println(Filename+"{");
				pr.println("aload pop");
				pr.println("/f exch def");
				pr.println("/p exch def");
				pr.println("f E dot-product 0 ge{");
				pr.println("\tnewpath");
				pr.println("p p length 1 sub get aload pop moveto3d");
				pr.println("p{");
				pr.println("\taload pop");
				pr.println("\tlineto3d");
				pr.println("}forall");
				pr.println("gsave");
				pr.println("0.8 setgray");
				pr.println("fill");
				pr.println("grestore");
				pr.println("\tp p length 1 sub get aload pop moveto3d");
				pr.println("\tp{");
				pr.println("\taload pop");
				pr.println("\tlineto3d");
				pr.println("}forall");
				pr.println("stroke");
				//normals
			//	pr.println("centroids count get aload pop moveto3d");
				//pr.println("normals   count get aload pop lineto3d");
			//	pr.println("stroke");
				pr.println("}if");
				pr.println("/count count 1 add def");

				pr.println("}forall");
				pr.println();
				pr.println();



				pr.println("\n stroke \n");
				pr.println("\n showpage \n");
				pr.close();
//				System.out.println(Filename);

			}

			catch(Exception e){e.printStackTrace();}


	}

/**********************************************/
	public void plot_object(Graphics2D g2)
	{
		//boundingbox();
		double min;
		double trans  = (double)300;
		if((max_x -min_x)>(max_y -min_y)) min =(max_x -min_x);
		else min =(max_y -min_y);
		g2.setPaint(Color.black);
		g2.translate(300,300);
		//g2.rotate(3.1415926535);
		double scale = (double)200/min;
		System.out.println(min);
		for(int i=0;i<this.num_of_faces;i++)
		{
			for(int j=1;j<this.faces[i][0];j++)
			{
				//double x1 = trans + get_x(this.faces[i][j])* scale;
				//double y1 = get_y(this.faces[i][j])* scale + trans;
				//double x2 = trans + get_x(this.faces[i][j+1])* scale;
				//double y2 = get_y(this.faces[i][j+1])* scale + trans;
				double x1 = get_x(this.faces[i][j])* scale;
				double y1 = get_y(this.faces[i][j])* scale;
				double x2 = get_x(this.faces[i][j+1])* scale;
				double y2 = get_y(this.faces[i][j+1])* scale;
				Line2D l =  new Line2D.Double(x1,y1,x2,y2);
				g2.draw(l);
			}
			double x1 = get_x(this.faces[i][this.faces[i][0]]) * scale;
			double y1 = get_y(this.faces[i][this.faces[i][0]])* scale ;
			double x2 = get_x(this.faces[i][1])* scale;
			double y2 = get_y(this.faces[i][1])* scale;
			Line2D l =  new Line2D.Double(x1,y1,x2,y2);
			g2.draw(l);

		}

	}
	/**********************************************/
	public object transform(square tm)
	{
		ArrayList newpoints= new ArrayList();
		for(int i=0;i<this.num_of_points;i++)
		{
			newpoints.add(((point)this.points.get(i)).transform(tm));
		}
		object newobject =new object(this.num_of_points,this.num_of_faces,this.num_of_edges, newpoints, this.faces);
		return newobject;
	}

	/**********************************************/

	void print_object()
	{
		for(int i=0;i<this.num_of_points;i++)
		{
			((point)points.get(i)).print_point();
		}

		for(int i=0;i<this.num_of_faces;i++)
		{
			System.out.println(faces[i][0] + "  " +  faces[i][1]  + "  " + faces[i][2] +"  " +  faces[i][3]  );
		}
	}
	/**********************************************/
	public void RefineMesh(int level)
	{
		//100 iterations...
	//	h.print();
		System.out.println("faces length" +faces.length);
		int count=0;
		for(int it=0;it<level;it++)
		{
			error _e = h.GetLeastError();
			int vertex=((_e.v1<_e.v2)? _e.v1:_e.v2);
			int v=((_e.v1>_e.v2)? _e.v1:_e.v2);
			int other_point;
			point other;
			//System.out.print("\nvertex " + vertex + " v " + v);
			point _p=(point)points.get(vertex);
			point _q=(point)points.get(v);

			int face1=GetFace(vertex,v);
			_p.removeface(face1);
			_q.removeface(face1);

			//System.out.print(" f1 " +face1);
			if(faces[face1][1]==vertex)other_point=faces[face1][3];
			else if(faces[face1][2]==vertex)other_point=faces[face1][1];
			else other_point=faces[face1][2];


			other =(point)points.get(other_point);
			other.removeface(face1);

			h.RemoveError(v,other_point);

			int face2=GetFace(v,vertex);
			_p.removeface(face2);
			_q.removeface(face2);



		//	System.out.println(" f2 "+face2);
			if(faces[face2][1]==v)other_point=faces[face2][3];
			else if(faces[face2][2]==v)other_point=faces[face2][1];
			else other_point=faces[face2][2];

			other =(point)points.get(other_point);
			other.removeface(face2);

			h.RemoveError(v,other_point);

			//_p.print_point();
			ComputeErrors(vertex);

			//replace vertex ny the one that comes first...
			for(int i=0;i<this.num_of_faces;i++)
			{
				if(faces[i][1]==v)faces[i][1]=vertex;
				if(faces[i][2]==v)faces[i][2]=vertex;
				if(faces[i][3]==v)faces[i][3]=vertex;
			}

			if(face1>=0)
			{
				faces[face1][0]=-1;
				faces[face1][1]=-1;
				faces[face1][2]=-1;
				faces[face1][3]=-1;
				count++;
			}
			if(face2>=0)
			{
				faces[face2][0]=-1;
				faces[face2][1]=-1;
				faces[face2][2]=-1;
				faces[face2][3]=-1;
				count++;
			}
			_p.loadfaces(_q);
			_p.set_x(_e._v.ret_x());
			_p.set_y(_e._v.ret_y());
			_p.set_z(_e._v.ret_z());
		//	TEST("h"+it+".txt");
			for(int i=0;i<h.length;i++)
			{
				boolean found = false;
				if(h.errors[i].Contains(v))
				{
					//System.out.print("  "+ h.errors[i].v1 + "  " + h.errors[i].v2);
					h.errors[i].Replace(v,vertex);
					found = true;
					//System.out.println("  "+ h.errors[i].v1 + "  " + h.errors[i].v2);
				}
				if(h.errors[i].Contains(vertex)) found = true;
				if(found)
				{
					point _v1=(point)points.get(h.errors[i].v1);
					point _v2=(point)points.get(h.errors[i].v2);
					h.errors[i]._v=_v1.center(_v2);
					double values[]= new double[4];
					values[0]=h.errors[i]._v.ret_x();
					values[1]=h.errors[i]._v.ret_y();
					values[2]=h.errors[i]._v.ret_z();
					values[3]=1.0;
					matrix c = new collumn(values);
					//v_transpose
					matrix r = new row(values);
					//sume of q1 and q2
					matrix q=q_error[h.errors[i].v1].Add(q_error[h.errors[i].v2]);
					matrix s =(scalar)r.mulitply(q.mulitply(c));
					h.errors[i].m_error=s.retvalue(0,0);
				}

			}
			for(int i=0;i<h.length;i++)
			{
				//System.out.print(" "+ i);
				h.UpdateError(i);
			}





		}
		this.num_of_faces= this.num_of_faces-count;
		this.num_of_edges=this.num_of_edges-100;
		//System.out.println("after   "+this.num_of_points);
	}


	void RemoveUnnecessaryPoints()
	{
		int []pointscount= new int[this.num_of_points];
		ArrayList newpoints = new ArrayList();
		int count=0;
		for(int i=0;i<this.num_of_points;i++)pointscount[i]=0;
		for(int i=0;i<this.num_of_points;i++)
		{
			boolean notfound=true;
			for(int j=0;j<this.num_of_faces && notfound;j++)
			{
				if(faces[j][1]==i)notfound=false;
				if(faces[j][2]==i)notfound=false;
				if(faces[j][3]==i)notfound=false;
			}
			if(notfound)
			{
	//			System.out.println(i);
				count++;
				pointscount[i]=-1;
				for(int j=i+1;j<this.num_of_points;j++)
				{
					pointscount[j]=pointscount[j]+1;
				}
			}
			else
			{
				point _p=(point)points.get(i);
				newpoints.add(_p);
			}
		}
		this.num_of_points=this.num_of_points-count;
		if(this.num_of_points!=newpoints.size())System.out.println("errors-size");
		points.clear();
		points = newpoints;
		for(int i=0;i<this.num_of_faces;i++)
		{
		//	System.out.println("face\t" + i);
			if(pointscount[faces[i][1]]==-1)System.out.println("error" + faces[i][1]);
			if(pointscount[faces[i][1]]>0)
			{
				faces[i][1]=faces[i][1]-pointscount[faces[i][1]];
			}

			if(pointscount[faces[i][3]]==-1)System.out.println("error" + faces[i][2]);
			if(pointscount[faces[i][2]]>0)
			{
				faces[i][2]=faces[i][2]-pointscount[faces[i][2]];
			}

			if(pointscount[faces[i][3]]==-1)System.out.println("error" + faces[i][3]);
			if(pointscount[faces[i][3]]>0)
			{
				faces[i][3]=faces[i][3]-pointscount[faces[i][3]];
			}
		}

	}
	void RemoveUnnecessaryFaces()
	{
		int[][]newfaces= new int[this.num_of_faces][4];
		int count =0;
		for(int i=0;i<faces.length;i++)
		{
			if(faces[i][0]>0)
			{
				newfaces[count][0]=faces[i][0];
				newfaces[count][1]=faces[i][1];
				newfaces[count][2]=faces[i][2];
				newfaces[count][3]=faces[i][3];
				count++;
			}

		}
		faces=null;
		faces=newfaces;
	}



	public static void main(String args[]) throws Exception
	{

		object o = new object(args[0] + ".off");
		o.plot_object(args[0]);
		//o.print_object();
		o.RefineMesh(Integer.parseInt(args[1]));
		o.RemoveUnnecessaryFaces();
		o.RemoveUnnecessaryPoints();
//		o.print_object();
		o.save(args[0]+"_mod" +args[1] + ".off");
		o.plot_object(args[0]+"_mod" + args[1]);





	}
	/**********************************************/
}
