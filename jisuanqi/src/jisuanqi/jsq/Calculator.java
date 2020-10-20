package jisuanqi.jsq;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Calculator extends JFrame implements ActionListener
{
    private String[] KEYS={"7","8","9","*","4","5","6","-","1","2","3","+","0","e","��","��","c","%",".","=","(",")","sqr","x*x"};
    private JButton keys[]=new JButton[KEYS.length];
    private JTextField resultText = new JTextField("0.0");
    private String b="";
    public Calculator()
    {
        super("������");
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
        else if(label=="e"||label=="��")
        {
            if (label=="e")
            {
                String m=String.valueOf(2.71828);
                this.b=this.b+m;
                resultText.setText(this.b);
            }
            if (label=="��")
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
		String s="";//���ڳнӶ�λ�����ַ���
		char a[]=new char[100];//��̬��ջ
		String jieguo[]=new String[100];//��׺���ʽ�ַ������飬Ϊ�˽���λ���洢Ϊ�������ַ���
		int top=-1,j=0;//��ָ̬��top�����Ʊ���j
		for (int i=0;i<str.length();i++)//������׺���ʽ
		{
			if ("0123456789.".indexOf(str.charAt(i))>=0)//indexof�������Ϸ�ע�ͣ����������ַ������
			{
				s="";//��Ϊ�нӵ��ַ�����ÿ�ο�ʼ��Ҫ���
				for (;i<str.length()&&"0123456789.".indexOf(str.charAt(i))>=0;i++)//����λ���洢��һ���ַ�����
				{
					s=s+str.charAt(i);
				}
				i--;
				jieguo[j]=s;//�����ַ�ֱ�Ӽ����׺���ʽ
				j++;
			}
			else if ("(".indexOf(str.charAt(i))>=0)//����������
			{
				top++;
				a[top]=str.charAt(i);//��������ջ
			}
			else if (")".indexOf(str.charAt(i))>=0)//����������
			{
				for (;;)//ջ��Ԫ��ѭ����ջ��ֱ���ҵ�������Ϊֹ
				{
					if (a[top]!='(')//ջ��Ԫ�ز���������
					{
						jieguo[j]=a[top]+"";//ջ��Ԫ�س�ջ
						j++;
						top--;
					}
					else//�ҵ�ջ��Ԫ����������
					{
						top--;//ɾ��ջ����������
						break;//ѭ������
					}
				}
			}
			else if ("*%��".indexOf(str.charAt(i))>=0)//���������ȼ������
			{
				if (top==-1)//��ջΪ��ֱ����ջ
				{
					top++;
					a[top]=str.charAt(i);
				}
				else//ջ��Ϊ��
				{
					if ("*%��".indexOf(a[top])>=0)//ջ��Ԫ��ҲΪ�����ȼ������
					{
						jieguo[j]=a[top]+"";//ջ��Ԫ�س�ջ�����׺���ʽ
						j++;
						a[top]=str.charAt(i);//��ǰ�������ջ
					}
					else if ("(".indexOf(a[top])>=0)//ջ��Ԫ��Ϊ�����ţ���ǰ�������ջ
					{
						top++;
						a[top]=str.charAt(i);
					}
					else if ("+-".indexOf(a[top])>=0)//ջ��Ԫ��Ϊ�����ȼ����������ǰ�������ջ
					{
						top++;
						a[top]=str.charAt(i);
					}
				}
			}
			else if ("+-".indexOf(str.charAt(i))>=0)//���������ȼ������
			{
				if (top==-1)//ջΪ��ֱ����ջ
				{
					top++;
					a[top]=str.charAt(i);
				}
				else//ջ��Ϊ��
				{
					if ("%*��".indexOf(a[top])>=0)//ջ��Ԫ��Ϊ�����ȼ������
					{
						jieguo[j]=a[top]+"";//ջ��Ԫ�س�ջ�����׺���ʽ
						j++;
						a[top]=str.charAt(i);//��ǰ�������ջ
					}
					else if ("(".indexOf(a[top])>=0)//ջ��Ԫ��Ϊ������
					{
						top++;
						a[top]=str.charAt(i);//��ǰ�������ջ
					}
					else if ("+-".indexOf(a[top])>=0)//ջ��Ԫ��ҲΪ�����ȼ������
					{
						jieguo[j]=a[top]+"";//ջ��Ԫ�س�ջ�����׺���ʽ
						j++;
						a[top]=str.charAt(i);//��ǰԪ����ջ
					}
				}
			}
		}
		for (;top!=-1;)//����������ջ��ʣ��Ԫ�����γ�ջ�����׺���ʽ
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
        String Result[]=new String[100];//˳��洢��ջ����������Ϊ�ַ���
        int Top=-1;//��ָ̬��Top
        for (int i=0;str[i]!=null;i++)//������׺���ʽ
        {
            if ("+-*%��".indexOf(str[i])<0)//���������ַ���ջ
            {
                Top++;
                Result[Top]=str[i];
            }
            if ("+-*%��".indexOf(str[i])>=0)//���������
            {
                double x,y,n;
                x=Double.parseDouble(Result[Top]);//˳���ջ���������ַ�������ת��Ϊdouble���͵�����
                Top--;
                y=Double.parseDouble(Result[Top]);//˳���ջ���������ַ�������ת��Ϊdouble���͵�����
                Top--;
                if ("-".indexOf(str[i])>=0)//һ�²���������������������
                {
                    n=y-x;
                    Top++;
                    Result[Top]=String.valueOf(n);//��������������ջ
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
                if ("��".indexOf(str[i])>=0)
                {
                    if (x==0)//����������Ϊ0
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
                    if (x==0)//����������Ϊ0
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
        return Result[Top];//�������ս��
    }
    public static void main(String arg[])
    {
        Calculator a=new Calculator();
    }
}
 
