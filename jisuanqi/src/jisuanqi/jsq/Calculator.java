package jisuanqi.jsq;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Calculator extends JFrame implements ActionListener
{
    private String[] KEYS={"7","8","9","*","4","5","6","-","1","2","3","+","0","e","π","÷","c","%",".","=","(",")","sqr","x*x"};
    private JButton keys[]=new JButton[KEYS.length];
    private JTextField resultText = new JTextField("0.0");
    private String b="";
    public Calculator()
    {
        super("计算器");
        this.setLayout(null);
        resultText.setBounds(20, 5, 255, 40);
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setEditable(false);
        this.add(resultText);
        int x=20,y=55;
        for (int i=0;i<KEYS.length;i++)
        {
            keys[i] = new JButton();
            keys[i].setText(KEYS[i]);
            keys[i].setBounds(x, y, 60, 40);
        if(x<215)
            {
                x+=65;
            }
        else
            {
                x = 20;
                y+=45;
            }
            this.add(keys[i]);
        }
        for (int i = 0; i <KEYS.length; i++) 
        {
            keys[i].addActionListener(this);
        }
        this.setResizable(false);
        this.setBounds(500, 200, 300, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        String label = e.getActionCommand(); 
        if (label=="c"||label=="=")
        {
            if(label=="=")
            {
                String s[]=yunsuan(this.b);
                String result=Result(s);
                this.b=result+"";
                resultText.setText(this.b);
            }
            else
            {
                this.b="";
                resultText.setText("0");
            }
        }
        else if (label=="sqr")
        {
            String n=yunsuan2(this.b);
            resultText.setText(n);
            this.b=n;
        }
        else if(label=="x*x")
        {
            String m=yunsuan3(this.b);
            resultText.setText(m);
            this.b=m;
        }
        else if(label=="e"||label=="π")
        {
            if (label=="e")
            {
                String m=String.valueOf(2.71828);
                this.b=this.b+m;
                resultText.setText(this.b);
            }
            if (label=="π")
            {
                String m=String.valueOf(3.14159265);
                this.b=this.b+m;
                resultText.setText(this.b);
            }
        }
        else
        {
            this.b=this.b+label;
            resultText.setText(this.b);
        }
    }
    private String[] yunsuan(String str)
	{
		String s="";//用于承接多位数的字符串
		char a[]=new char[100];//静态的栈
		String jieguo[]=new String[100];//后缀表达式字符串数组，为了将多位数存储为独立的字符串
		int top=-1,j=0;//静态指针top，控制变量j
		for (int i=0;i<str.length();i++)//遍历中缀表达式
		{
			if ("0123456789.".indexOf(str.charAt(i))>=0)//indexof函数见上方注释，遇到数字字符的情况
			{
				s="";//作为承接的字符串，每次开始都要清空
				for (;i<str.length()&&"0123456789.".indexOf(str.charAt(i))>=0;i++)//将多位数存储在一个字符串中
				{
					s=s+str.charAt(i);
				}
				i--;
				jieguo[j]=s;//数字字符直接加入后缀表达式
				j++;
			}
			else if ("(".indexOf(str.charAt(i))>=0)//遇到左括号
			{
				top++;
				a[top]=str.charAt(i);//左括号入栈
			}
			else if (")".indexOf(str.charAt(i))>=0)//遇到右括号
			{
				for (;;)//栈顶元素循环出栈，直到找到左括号为止
				{
					if (a[top]!='(')//栈顶元素不是左括号
					{
						jieguo[j]=a[top]+"";//栈顶元素出栈
						j++;
						top--;
					}
					else//找到栈顶元素是左括号
					{
						top--;//删除栈顶的左括号
						break;//循环结束
					}
				}
			}
			else if ("*%÷".indexOf(str.charAt(i))>=0)//遇到高优先级运算符
			{
				if (top==-1)//若栈为空直接入栈
				{
					top++;
					a[top]=str.charAt(i);
				}
				else//栈不为空
				{
					if ("*%÷".indexOf(a[top])>=0)//栈顶元素也为高优先级运算符
					{
						jieguo[j]=a[top]+"";//栈顶元素出栈进入后缀表达式
						j++;
						a[top]=str.charAt(i);//当前运算符入栈
					}
					else if ("(".indexOf(a[top])>=0)//栈顶元素为左括号，当前运算符入栈
					{
						top++;
						a[top]=str.charAt(i);
					}
					else if ("+-".indexOf(a[top])>=0)//栈顶元素为低优先级运算符，当前运算符入栈
					{
						top++;
						a[top]=str.charAt(i);
					}
				}
			}
			else if ("+-".indexOf(str.charAt(i))>=0)//遇到低优先级运算符
			{
				if (top==-1)//栈为空直接入栈
				{
					top++;
					a[top]=str.charAt(i);
				}
				else//栈不为空
				{
					if ("%*÷".indexOf(a[top])>=0)//栈顶元素为高优先级运算符
					{
						jieguo[j]=a[top]+"";//栈顶元素出栈加入后缀表达式
						j++;
						a[top]=str.charAt(i);//当前运算符入栈
					}
					else if ("(".indexOf(a[top])>=0)//栈顶元素为左括号
					{
						top++;
						a[top]=str.charAt(i);//当前运算符入栈
					}
					else if ("+-".indexOf(a[top])>=0)//栈顶元素也为低优先级运算符
					{
						jieguo[j]=a[top]+"";//栈顶元素出栈进入后缀表达式
						j++;
						a[top]=str.charAt(i);//当前元素入栈
					}
				}
			}
		}
		for (;top!=-1;)//遍历结束后将栈中剩余元素依次出栈进入后缀表达式
		{
			jieguo[j]=a[top]+"";
			j++;
			top--;
		}
		return jieguo;
	}
    public String yunsuan2(String str)
    {
        String result="";
        double a=Double.parseDouble(str),b=0;
        b=Math.sqrt(a);
        result=String.valueOf(b);
        return result;
    }
    public String yunsuan3(String str)
    {
        String result="";
        double a=Double.parseDouble(str),b=0;
        b=Math.pow(a, 2);
        result=String.valueOf(b);
        return result;
    }
    public String Result(String str[])
    {
        String Result[]=new String[100];//顺序存储的栈，数据类型为字符串
        int Top=-1;//静态指针Top
        for (int i=0;str[i]!=null;i++)//遍历后缀表达式
        {
            if ("+-*%÷".indexOf(str[i])<0)//遇到数字字符进栈
            {
                Top++;
                Result[Top]=str[i];
            }
            if ("+-*%÷".indexOf(str[i])>=0)//遇到运算符
            {
                double x,y,n;
                x=Double.parseDouble(Result[Top]);//顺序出栈两个数字字符串，并转换为double类型的数字
                Top--;
                y=Double.parseDouble(Result[Top]);//顺序出栈两个数字字符串，并转换为double类型的数字
                Top--;
                if ("-".indexOf(str[i])>=0)//一下步骤根据运算符来进行运算
                {
                    n=y-x;
                    Top++;
                    Result[Top]=String.valueOf(n);//将运算结果重新入栈
                }
                if ("+".indexOf(str[i])>=0)
                {
                    n=y+x;
                    Top++;
                    Result[Top]=String.valueOf(n);
                }
                if ("*".indexOf(str[i])>=0)
                {
                    n=y*x;
                    Top++;
                    Result[Top]=String.valueOf(n);
                }
                if ("÷".indexOf(str[i])>=0)
                {
                    if (x==0)//不允许被除数为0
                    {
                        String s="ERROR";
                        return s;
                    }
                    else
                    {
                        n=y/x;
                        Top++;
                        Result[Top]=String.valueOf(n);
                    }
                }
                if ("%".indexOf(str[i])>=0)
                {
                    if (x==0)//不允许被除数为0
                    {
                        String s="ERROR";
                        return s;
                    }
                    else
                    {
                        n=y%x;
                        Top++;
                        Result[Top]=String.valueOf(n);
                    }
                }
            }
        }
        return Result[Top];//返回最终结果
    }
    public static void main(String arg[])
    {
        Calculator a=new Calculator();
    }
}
 
