import java.awt.*;
import java.awt.event.*;
import java.util.Stack;


class window extends WindowAdapter{
    
    /** 
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}

public class CalFrame extends Frame implements ActionListener{
    TextField t;
    Button clrAll,clr,div,mul,sub,sum,tot,mod,dot,b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    double exp=0,exp1=0;
    CalFrame(){
        super("Calculator");
        setBounds(600, 150, 250, 300);
        setResizable(false);
        addWindowListener(new window());

        //text field for input
        t=new TextField("");
        t.setColumns(5);
        t.setEditable(false);
        t.setFont(new Font("Times New Roman",1, 40));
        
        //panel for textfield
        Panel p1=new Panel();
        p1.add(t);


        clrAll=new Button("AC");
        clrAll.setBackground(Color.RED);
        clr=new Button("<-");
        clr.setBackground(Color.ORANGE);
        div=new Button("/");
        mul=new Button("*");
        b1 = new Button("1");
        b2 = new Button("2");
        b3=new Button("3");
        sub=new Button("-");
        b4=new Button("4");
        b5=new Button("5");
        b6=new Button("6");
        sum=new Button("+");
        b7=new Button("7");
        b8=new Button("8");
        b9=new Button("9");
        mod=new Button("mod");
        b0=new Button("0");
        dot=new Button(".");
        tot=new Button("=");
        tot.setBackground(Color.GREEN);
        setBackground(Color.GRAY);
        
        //panel for buttons
        Panel p=new Panel();
        GridLayout gl=new GridLayout(5,5,10,10); 
        p.setLayout(gl);
        p.setSize(150,1500);

        p.add(clrAll);
        p.add(clr);
        p.add(div);
        p.add(mul);
        p.add(b7);
        p.add(b8);
        p.add(b9);
        p.add(sub);
        p.add(b4);
        p.add(b5);
        p.add(b6);
        p.add(sum);
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(mod);
        p.add(dot);
        p.add(b0);
        p.add(tot);


        clrAll.addActionListener(this);
        clr.addActionListener(this);
        div.addActionListener(this);
        mul.addActionListener(this);
        sub.addActionListener(this);
        sum.addActionListener(this);
        mod.addActionListener(this);
        dot.addActionListener(this);
        tot.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b0.addActionListener(this);

        //main panel contain both panels
        Panel main=new Panel();
        main.add(p1);
        main.add(p);

        add(main);
    }


    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==clrAll){
            t.setText("");
            exp=0;
        }
        if(ae.getSource()==clr){
            String str=t.getText();
            String str1=str.substring(0, str.length()-1);
            t.setText(str1);
        }
        if(ae.getSource()==mul){
            t.setText(t.getText()+"*");;
        }
        if(ae.getSource()==div){
            t.setText(t.getText()+"/");;
        }
        if(ae.getSource()==sub){
            t.setText(t.getText()+"-");;
        }
        if(ae.getSource()==sum){
            t.setText(t.getText()+"+");;
        }
        if(ae.getSource()==mod){
            t.setText(t.getText()+"mod");;
        }
        if(ae.getSource()==dot){
            t.setText(t.getText()+".");;
        }
        if(ae.getSource()==b1){
            t.setText(t.getText()+"1");;
        }
        if(ae.getSource()==b2){
            t.setText(t.getText()+"2");;
        }
        if(ae.getSource()==b3){
            t.setText(t.getText()+"3");;
        }
        if(ae.getSource()==b4){
            t.setText(t.getText()+"4");;
        }
        if(ae.getSource()==b5){
            t.setText(t.getText()+"5");;
        }
        if(ae.getSource()==b6){
            t.setText(t.getText()+"6");;
        }
        if(ae.getSource()==b7){
            t.setText(t.getText()+"7");;
        }
        if(ae.getSource()==b8){
            t.setText(t.getText()+"8");;
        }
        if(ae.getSource()==b9){
            t.setText(t.getText()+"9");;
        }
        if(ae.getSource()==b0){
            t.setText(t.getText()+"0");;
        }

        
        if(ae.getSource()==tot){
            evaluate();
        }
    }
    
    void evaluate(){
        //stack for inputted symbols
        Stack<String> s=new Stack<>(); 

        //to convert mod to %
        String fieldText=t.getText();
        fieldText=fieldText.replace("mod", "%");

        //inputted string to char array
        char eval[]=fieldText.toCharArray();

        //to check for -ve values 
        int count=0;

        //postfix string
        String  strExp="";

        for(char c:eval){

            //if number is encountered or first symbol is '-' then returns true 
            if((c!='/' && c!='*' && c!='-' && c!='+' && c!='%')|| (count==0 && c=='-')){
                strExp=strExp+String.valueOf(c);
                count=1;
            }
            else{
                //to distinguish values   i.e. 56  as different value as 56)  else it can be evaluated as 5 and 6
                strExp=strExp+")";

                //to make another character as first symbol so that -ve value can be taken  i.e.    5*-3    then -3 will be taken to stack through if condition
                count=0;
                if(s.isEmpty()||precedence(s.peek() ,String.valueOf(c))){
                    s.push(String.valueOf(c));
                }
                else if(samepre(s.peek() ,String.valueOf(c))){
                    strExp=strExp+s.pop();
                    s.push(String.valueOf(c));
                }
                else{
                    strExp=strExp+s.pop();
                    strExp=strExp+s.pop();
                    s.push(String.valueOf(c));
                }
            }
        }
        strExp=strExp+")";
        while(!s.isEmpty()){
            strExp=strExp+s.pop();
        }

        //stack t resolve postfix exp
        Stack<String> Exp=new Stack<>();

        //char array of postfix exp
        char finalExp[]=strExp.toCharArray();

        String exp1="";

        //value 1 , value 2 and final result
        double e1=0,e2=0,fe=0;

        for(char x:finalExp){
            if(x!=')' && x!='+' && x!='-' && x!='/' && x!='*' && x!='%'){
                exp1+=x;
            }
            else if(x==')'){
                Exp.push(exp1);
                exp1="";
            }
            else{
                e1=Double.parseDouble(Exp.pop());
                e2=Double.parseDouble(Exp.pop());
                if(x=='+'){
                    fe=e1+e2;
                }
                if(x=='-'){
                    fe=e2-e1;
                }
                if(x=='/'){
                    fe=e2/e1;
                }
                if(x=='*'){
                    fe=e1*e2;
                }
                if(x=='%'){
                    fe=e2%e1;
                }
                exp1=String.valueOf(fe);
                Exp.push(exp1);
                exp1="";
            }

        }
    t.setText(Exp.peek());
    }

    //returns true if stack top and present symbol have same presedence
    boolean samepre(String a,String b){
        if((a.equals("+") && b.equals("-"))||(a.equals("-") && b.equals("+"))){
            return true;
        }
        if((a.equals("/") && (b.equals("*") || b.equals("%")))){
            return true;
        }
        if((a.equals("*") && (b.equals("/") || b.equals("%")))){
            return true;
        }
        if((a.equals("%") && (b.equals("/") || b.equals("*")))){
            return true;
        }
        if(a.equals(b)){
            return true;
        }
        return false;
    }

    //returns true if present symbol have higher precedence than stack top
    boolean precedence(String a,String b){

        if((a.equals("+") && (b.equals("/") || b.equals("%") || b.equals("*")))){
            return true;
        }
        if((a.equals("-") && (b.equals("/") || b.equals("%") || b.equals("*")))){
            return true;
        }
        return false;
    }
}
