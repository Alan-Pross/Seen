package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import net.sf.json.JSONObject;

public class getToken {
	public String token="";
	JSONObject jsonObject = null;
	StringBuffer buffer = new StringBuffer();

	public getToken(String userId,String name) {
        String uri = "";

        long t = System.currentTimeMillis() / 1000;
        String time = String.valueOf(t);//����Ϊ��ȡʱ���

        String ak = "x4vkb1qpxfwxk";//app key
        String Nonce = "";//
        Random rand = new Random();
        Nonce = Integer.toString(rand.nextInt(10000));

        String Signature = shaEncrypt("3Lkmoq7hts8Q" + Nonce + time);//����У����
      
        try {
            URL url = new URL("http://api.cn.ronghub.com/user/getToken.json");
            // ��url �� open�������ص�urlConnection  ����ǿתΪHttpURLConnection����  (��ʶһ��url�����õ�Զ�̶�������)
            // ��ʱcnnectionֻ��Ϊһ�����Ӷ���,��������
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // �������������Ϊtrue,Ĭ��false (post �����������ķ�ʽ��ʽ�Ĵ��ݲ���)
            connection.setDoOutput(true);
            // ��������������Ϊtrue
            connection.setDoInput(true);
            // ��������ʽΪpost
            connection.setRequestMethod("POST");
            // post���󻺴���Ϊfalse
            connection.setUseCaches(false);
            // ���ø�HttpURLConnectionʵ���Ƿ��Զ�ִ���ض���
            connection.setInstanceFollowRedirects(true);
            // ��������ͷ����ĸ������� (����Ϊ�������ݵ�����,����Ϊ����urlEncoded�������from����)
            // application/x-javascript text/xml->xml���� application/x-javascript->json���� application/x-www-form-urlencoded->������
            // ;charset=utf-8 ����Ҫ
            //addRequestProperty�����ͬ��key���Ḳ�ǣ������ͬ�����ݻ���{name1,name2}
            //connection.addRequestProperty("from", "sfzh");  //��Դ�ĸ�ϵͳ
            //setRequestProperty�����ͬ��key�Ḳ��value��Ϣ
            //setRequestProperty���������key���ڣ��򸲸ǣ������ڣ�ֱ����ӡ�
            //addRequestProperty����������key���ڲ����ڣ�ֱ����ӡ�
            //connection.setRequestProperty("user", "user");  //���������û�
            //InetAddress address = InetAddress.getLocalHost();
            //String ip=address.getHostAddress();//��ñ���IP
            //connection.setRequestProperty("ip",ip);  //������ԴIP
            //connection.setRequestProperty("encry", "123456");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("APP-Key", ak);
            connection.setRequestProperty("Nonce", Nonce);
            connection.setRequestProperty("Timestamp", time);
            connection.setRequestProperty("Signature", Signature);

            // �������� (����δ��ʼ,ֱ��connection.getInputStream()��������ʱ�ŷ���,���ϸ��������������ڴ˷���֮ǰ����)
            connection.connect();
            // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            // ��ʽ parm = aaa=111&bbb=222&ccc=333&ddd=444
            String parm = "userId=" + userId + "&name=" + name + "&portraitUri=" + uri;
            System.out.println("���ݲ�����" + parm);
            // ���������������
            dataout.writeBytes(parm);
            // �����ɺ�ˢ�²��ر���
            dataout.flush();
            dataout.close(); // ��Ҫ���׺��Բ��� (�ر���,�м�!)
            //System.out.println(connection.getResponseCode());
            // ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // �ͷ���Դ
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            System.out.println("!!!�����!!!!!!!!!!!!!!!!!!"+jsonObject.get("token"));
         
           
            token =jsonObject.getString("token");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public String shaEncrypt(String strSrc) {//SHA-1�����㷨
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// ���˻���SHA-1��SHA-512��SHA-384�Ȳ���
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public String bytes2Hex(byte[] bts) {//ͬ����
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
