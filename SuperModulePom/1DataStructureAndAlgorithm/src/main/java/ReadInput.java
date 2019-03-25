import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 读取输入.
 *
 * Created by chenjingshuai on 19-3-13.
 */
public class ReadInput {

    /**
     * UTF-8编码汉字一般用3个字节表示, 数字和英文字母都分别占一个字节,
     *
     * e.g.
     *


     系统编码格式:UTF-8
     InputStream读字节:
     哈哈哈罸a
     229 147 136 229 147 136 229 147 136 231 189 184 97
     InputStreamReader读字:
     哈哈哈罸a
     21704 21704 21704 32632 97
     BufferedReader读行:
     哈哈哈罸a
     [哈哈哈罸a]
     BufferedReader读字:
     哈哈哈罸a
     21704 21704 21704 32632 97


     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("系统编码格式:   " + Charset.defaultCharset());
        System.out.println("'\\n'的ASCII码:   " + ((int)'\n'));
        InputStream inputStream = System.in;
        int byteValue;
        System.out.println("InputStream读字节: ");
        while ((byteValue = inputStream.read()) != "\n".getBytes()[0]) {
            System.out.print(byteValue + " ");
        }
        System.out.println();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        System.out.println("InputStreamReader读字: ");
        while ((byteValue = inputStreamReader.read()) != '\n') {
            System.out.print(byteValue + " ");
        }
        System.out.println();

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println("BufferedReader读行: ");
        String firstLine = bufferedReader.readLine();
        String[] arrInFirstLine = firstLine.split(" ");
        System.out.println(Arrays.toString(arrInFirstLine));

        System.out.println("BufferedReader读字: ");
        while ((byteValue = bufferedReader.read()) != "\n".getBytes()[0]) {
            System.out.print(byteValue + " ");
        }
    }
}
