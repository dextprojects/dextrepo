package com.exim.master;
public class EncryptTest 
{ 
        public static void main(String[] args){ 
                if(args[0].equals("encrypt")) 
                        encrypt(args[1]); 
                if(args[0].equals("decrypt")) 
                        decrypt(args[1]); 
        } 

        public static StringBuffer decrypt(String xxx){ 
                String input = xxx; 
                char test = '0'; 
                StringBuffer st = new StringBuffer(); 
                for(int i=0;  i< input.length(); i++){ 
                        if((i+1)%2 != 0 ) 
                                test =(char)( input.charAt(i) - (i+1) ) ; 
                        if((i+1)%2 == 0) 
                                test =(char)( input.charAt(i) + (i+1) ) ; 

                        st.append(test); 
                        } 
                System.out.println("................."+st); 
				return(st);

        } 
        public static StringBuffer encrypt(String xxx) 
        { 
                String input = xxx; 
                char test = '0'; 
                StringBuffer st = new StringBuffer(); 
                for(int i=0;  i< input.length(); i++){ 
                        if((i+1)%2 != 0 ) 
                                test =(char)( input.charAt(i) + (i+1) ) ; 
                        if((i+1)%2 == 0) 
                                test =(char)( input.charAt(i) - (i+1) ) ; 

                        st.append(test); 
                } 
                
				return(st);
        } 
} 
