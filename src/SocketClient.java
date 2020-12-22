import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.lang.model.type.UnknownTypeException;

 
public class SocketClient {
    public static void main(String[] args) throws Exception {
        File input = new File(args[1]);
        File output=new File(args[2]);
        //DataOutputStream dos = new DataOutputStream(new FileOutputStream(args[2]));
        //String client="127.0.0.1";
        String client="121.196.149.101";
        //int port = 8999;
        int port=443;
        if(args[0].equals("--generate")){
            System.out.println(client);
            Socket socket = new Socket(client, port);
            //System.out.println("new Socket");
            FileInputStream fileInputStream=new FileInputStream(input);
            DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
            byte[] bytes=new byte[1024];
            int length=0;
            int n=0;
            while ((length=fileInputStream.read(bytes, 0, bytes.length))!=-1) {
                //System.out.println(n++);
                dataOutputStream.write(bytes, 0, length);
                //System.out.println(length);
                //System.out.println(bytes);
                dataOutputStream.flush();
                //System.out.println(bytes);
            }
            //System.out.println("out while");
            FileOutputStream fileOutputStream=new FileOutputStream(output);
            DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
            int test=0;
            try {
                while (true) {
                    test=dataInputStream.read();
                    if(test==-1){
                        break;
                    }
                    if(test==0x7A){
                        System.out.println("DELETE:"+test);
                        throw new UnknownTypeException(null, null);
                    }else{
                        fileOutputStream.write(test);
                    }
                }
            } catch(IOException q) {
                System.out.println("Finish");
            } catch (UnknownTypeException u){
                throw new Exception();
            }
            finally{
                fileInputStream.close();
                dataInputStream.close();
                dataOutputStream.close();
                fileOutputStream.close();
                socket.close();
                System.out.println("Close");
                //System.exit(-1);
            }
        }
    }
}
