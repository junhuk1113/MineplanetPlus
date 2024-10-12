package net.pmkjun.mineplanetplus.fishhelper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNum {
    private static char[] signs = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    private static int[] values = {1, 5, 10, 50, 100, 500, 1000}; 

	public static int toInt(String s){
		
		int iret = -1;
		if( isRomaNum(s) ){
		    int pre = 0;
			int sum = 0;
			for(char ch : s.toCharArray()) {
				int v = getValue(ch);
				
				if(pre != 0) {
					if(pre < v) {
						sum -= pre * 2;
					}
				}
				sum += v;
				pre = v;
			}
			iret = sum;
		}
		return iret;
	}
	 
	private static int getValue(char sign) {
		for(int i = 0; i < signs.length; i++) {
			if(signs[i] == sign) {
				return values[i];
			}
		}
		return 0;
	}
    public static boolean isRomaNum(String s){
		String regex ="^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
		String rtn ="";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(s); 
		while( m.find() ){ 
		    rtn =m.group(0);
		} 
		return !rtn.equals(""); 
	}
}
