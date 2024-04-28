import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.reflect.*;
public class a120220808064 {
    public static void main(String[] args) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Emulator.INITIALIZE(args[0]);

    }
}
class Emulator{
    static Scanner scan;
    static File commands;

    static String file_name;
    static Scanner string_scanner;
    static int F;
    static int AC;
    static int PC;
    static int[] M;

    public static void START() throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        commands=new File(file_name);
        scan=new Scanner(commands);
        M = new int[256];
        int count=0;
        while(scan.hasNextLine()&& scan.hasNext()) {
            count++;
            scan.nextLine();
        }
        scan.close();
        scan=new Scanner(commands);
        String[] instructions=new String[count];
        PC=0;
        AC=0;
        F=0;
        int index=0;
        String current_instruction=null;
        while(scan.hasNextLine() && scan.hasNext()) {
            instructions[index++] = scan.nextLine();
        }
        for(int i = 0; i < instructions.length; i = PC) {
            String instruction_line = instructions[i];
            string_scanner = new Scanner(instruction_line);
            while (string_scanner.hasNext()) {
                string_scanner.next();
                current_instruction = string_scanner.next();
                if (current_instruction.equals("START")) {
                    PC++;
                    break;
                }
                if (current_instruction.equals("DISP")) {
                    PC++;
                    DISP();
                    continue;
                }
                if (current_instruction.equals("HALT")){
                    PC++;
                    break;
                    }
                int arg_val = string_scanner.nextInt();
                Method stringMethod = Emulator.class.getMethod(current_instruction, int.class);
                stringMethod.invoke(null, arg_val);
            }
        }
        }







//

    public static void LOAD(int X){
        CHECK_OVERFLOW(X);
        AC=X;
        PC++;
    }

    public static void LOADM(int X){
        AC=M[X];
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void STORE(int X){
        CHECK_OVERFLOW(X);
        M[X]=AC;
        CHECK_OVERFLOW(M[X]);
        PC++;
    }
    public static void CMPM(int X){
        if(AC>M[X])
            F=1;
        else if(AC<M[X])
            F=-1;
        else
            F=0;
        PC++;
    }
    public static void CJMP(int X){
        if(F>0)
            PC=X-1;
        PC++;
    }
    public static void JMP(int X){
        PC=X;


    }
    public static void ADD(int X){
        AC+=X;
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void ADDM(int X){
        AC+=M[X];
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void SUBM(int X){
        AC-=M[X];
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void SUB(int X){
        AC-=X;
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void MUL(int N){
        AC*=N;
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void MULM(int N){
        AC*=M[N];
        CHECK_OVERFLOW(AC);
        PC++;
    }
    public static void DISP(){
        System.out.println(AC);
        PC++;
    }
    public static void HALT(){
        System.out.println("Execution stopped");
    }


    public static void INITIALIZE(String val) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        file_name=val;
        START();

    }
    public static void CHECK_OVERFLOW(int val){
        if(!(val<256 && val>=0))
            throw new RuntimeException("Value must be in range [0,256)..!");
    }
}

