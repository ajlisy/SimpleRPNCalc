package com.example.simplecalc;

import java.text.NumberFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	StringBuilder input=new StringBuilder();
	Deque<Double> stack = new ArrayDeque<Double>();
	NumberFormat nf = NumberFormat.getInstance(Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void buttonPress(View view){
		Button b = (Button) findViewById(view.getId());
		if (b.getText().equals("Enter")){
			if (input.length()==0) return;
			stack.push(Double.parseDouble(input.toString()));
			input=new StringBuilder();
			if(stack.size()>=10){
				stack.removeLast();
			}
		} else if (b.getText().equals("+/-")){
			if (input.length()==0) return;
			if (input.charAt(0)=='-'){
				input.replace(0, 2, Character.toString(input.charAt(1)));
			} else {
				input.insert(0, "-");
			}
		} else if (b.getText().equals("C")){
			stack=new ArrayDeque<Double>();
			input=new StringBuilder();
		} else if (b.getText().equals("+") || b.getText().equals("-") ||
				b.getText().equals("*") || b.getText().equals("/")){
			doOperation((String) b.getText(),stack,input);
			input=new StringBuilder();
		} else {
			try{
				input.append(b.getText());
			} catch (Exception e) {};
		} 

		drawStack(stack,input.toString());

	}

	public void doOperation(String operation,Deque<Double> stack, StringBuilder input){
		if (stack.peek()==null) return;
		double x=0,y=0;
		try {
			x = stack.pop();
			y = Double.parseDouble(input.toString());
			switch (operation.charAt(0)){
			case '+':
				stack.push(x+y);
				break;
			case '-':
				stack.push(x-y);
				break;
			case '*':
				stack.push(x*y);
				break;
			case '/':
				stack.push(x/y);
				break;
			}
		} catch (NumberFormatException e) {
			stack.push(x);
		}
	}

	public void drawStack(Deque<Double> stack,String input){
		TextView tv = (TextView) findViewById(R.id.textView1);
		StringBuilder sb=new StringBuilder();
		Log.v("Stack",stack.toString());
		Iterator<Double> di = stack.descendingIterator();
		while(di.hasNext()){
			sb.append(nf.format(di.next()));
			sb.append("<br>");
		}
		String formattedInput;
		try{
			formattedInput=nf.format(Double.parseDouble(input));
		} catch (Exception e){
			formattedInput=input;
		}
		sb.append("<font color=blue>"+ formattedInput +"</font>");
		tv.setText(Html.fromHtml(sb.toString()));
	}

}
