//-----------------------------------------------------------------------------
//	Test4.java
//  Authors: Chad Dugie, David Trinh
//-----------------------------------------------------------------------------
//	Description:
//-----------------------------------------------------------------------------
import java.util.*;

public class Test4 extends Thread {
    private final int iterations = 100;
    private static final int BSIZE = 512;
    private byte[] writeBuff = new byte[BSIZE];
    private byte[] readBuff = new byte[BSIZE];

    //Used to hold the user arguments
    private int caseNum;
    private String diskCase;

    // Helps determine if disk cache will be used
    private final String useDisk = "enabled";
    private final String noDisk = "disabled";
    private Random randomNum = new Random();

    private long time; // Time


    public Test4(String[] args)
    {
        if(args == null || args.length != 2)
            throw new RuntimeException("Please enter two valid arguments. " +
                    "Argument 1: [enabled | disabled]. Argument 2: [1-4] ");
        // Holds arguments
        diskCase = args[0].toLowerCase();
        caseNum = Integer.parseInt(args[1]);
        // Argument checking
        if(!(diskCase.equals(useDisk) || diskCase.equals(noDisk)))
            throw new RuntimeException("Please enter either \"enabled\" or \"disabled\" for the first argument");
        if(caseNum < 1 || caseNum > 4)
            throw new RuntimeException("Only integer from 1-4 are allowed for the second argument");
    }

    public void run()
    {
        SysLib.flush(); // Clear
        time = new Date().getTime(); // Move down to each method later

        SysLib.cout("\nIn Run \n"); // Remove later
        switch(caseNum)
        {
            case 1:
                randomAccess();
                break;
            case 2:
                localAccess();
                break;
            case 3:
                mixedAccess();
                break;
            case 4:
                adversaryAccess();
                break;
            default: // not really necessary, could remove
                break;
        }
        SysLib.exit();
    }

    //======================= randomAccess() ===================================
    //  Reads and writes many blocks randomly across the disk.
    //
    //  I'll add more to this. Getting late though so I'll be doing it when I wake
    //  To Do: Check performance on the method with time and validity.
    public void randomAccess()
    {
        SysLib.cout("\nIn Random \n"); // Will remove later
        int[] arr = new int[iterations];

        // Fill array
        for(int i = 0; i < iterations; i++) {

            arr[i] = Math.abs((randomNum.nextInt() % BSIZE));
        }
        for(int i = 0; i < iterations; i++)
        {
            for(int a = 0; a < BSIZE; a++)
            {
                byte b = (byte) a;
                writeBuff[a] = b;
            }
            write(arr[i], writeBuff);
        }
        for(int i = 0; i < iterations; i++)
        {
            read(arr[i], readBuff);
        }

    }

    //======================= localAccess() ====================================
    //  Reads and writes a small selection of blocks many times to get a high
    //  ratio of cache and hits
    //
    public void localAccess()
    {

    }
    public void mixedAccess()
    {

    }
    public void adversaryAccess()
    {

    }

    //======================= read(int, byte[]) ================================
    //  Helper method that either uses cread or rawread.
    //
    private void read(int num, byte[] arr)
    {
        if(diskCase.equals(useDisk))
            SysLib.cread(num, arr);
        else
            SysLib.rawread(num, arr);

    }

    //======================= write(int, byte[]) ===============================
    //  Helper method that either uses cwrite or rawwrite.
    //
    private void write(int num, byte[] arr)
    {
        if(diskCase.equals(useDisk))
            SysLib.cwrite(num, arr);
        else
            SysLib.rawwrite(num, arr);
    }
    private void showPerformance()
    {

    }
    private void check()
    {
        if(!(Arrays.equals(readBuff, writeBuff)))
        {
            SysLib.cerr(" Data does not match!");
        }
    }
}

