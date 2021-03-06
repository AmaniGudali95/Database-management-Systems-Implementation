import bigt.*;
import diskmgr.PCounter;
import global.SystemDefs;

/**
 * This is a command line program which implements the RowJoin operator
 * Compile using "javac RowJoin.java"
 * Execute using "java RowJoin big_table_1_name big_table_2_name output_big_table_name column_name NUMBUF"
 * NOTE: output_big_table_name should contain the word "join"
 */
public class RowJoin {
    public static void main(String[] args) throws Exception {
        RowJoin rowJoin = new RowJoin();
        rowJoin.execute(args[0], args[1], args[2], args[3], args[4]);
    }

    public void execute(String bigTableName1, String bigTableName2, String outBigTableName,
                        String columnName, String numBuf) throws Exception {

        System.out.println("Performing RowJoin");
        PCounter.getInstance().setReadCount(0);
        PCounter.getInstance().setWriteCount(0);

        Minibase.getInstance().init(bigTableName1, Integer.parseInt(numBuf));

        Stream stream = Minibase.getInstance().getBigTable().openStream(1, "*",
                "*", "*");

        BTRowJoin btRowJoin = new BTRowJoin(stream, bigTableName2, columnName, outBigTableName);
        btRowJoin.close();
        BigTableCatalog.addBigTInfo(new BigTableInfo(outBigTableName));
        System.out.println("Total number of reads " + PCounter.getInstance().getReadCount());
        System.out.println("Total number of writes " + PCounter.getInstance().getWriteCount());
        SystemDefs.JavabaseBM.setNumBuffers(0);
    }
}
