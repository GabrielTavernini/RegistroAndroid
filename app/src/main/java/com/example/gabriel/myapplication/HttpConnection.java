package com.example.gabriel.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpConnection {

    static private String cookies;
    private HttpsURLConnection conn;

    static String seed;
    private final String USER_AGENT = "Mozilla/5.0";
    static String url = "https://www.lampschool.it/hosting_trentino_17_18/login/login.php?suffisso=scuola_";
    static String menu = "https://www.lampschool.it/hosting_trentino_17_18/login/ele_ges.php";
    static String urlvoti = "https://www.lampschool.it/hosting_trentino_17_18/valutazioni/visvaltut.php";

    public static String extractVoti(String user, String pass, int school) throws Exception {
        url += school;

        String page = Jsoup.connect(url).get().html();
        seed = page.split("seme='")[1].substring(0,32);
        String postParams = "utente=" + user + "&pass=&OK=Accedi&password=" + cryptPassword(pass);

        getCookies();
        System.out.print(cookies);

        String result = extractMarks(getMarksPage(postParams));
        return result;
    }

    public static String getMarksPage(String postParams) {
        URL site, marksSite;
        try {
            site = new URL(menu);
            marksSite = new URL(urlvoti);
            HttpsURLConnection conn = (HttpsURLConnection) site.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cookie", cookies);
            conn.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "it-IT,it;q=0.9");
            conn.setRequestProperty("Connection", "keep-alive");

            conn.setRequestProperty("Referer", url);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code : " + responseCode);
            System.out.println(conn.getURL());

            conn.disconnect();

            HttpsURLConnection marksConn = (HttpsURLConnection) marksSite.openConnection();
            marksConn.setDoOutput(true);
            marksConn.setDoInput(true);
            marksConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            marksConn.setRequestMethod("POST");
            marksConn.setRequestProperty("Cookie", cookies);
            marksConn.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

            BufferedReader in = new BufferedReader(new InputStreamReader(marksConn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;


    }

    public static String extractMarks(String html)
            throws Exception {

        System.out.println("Extracting marks data...");

        Document doc = Jsoup.parse(html);
        List<String> data = new ArrayList<>();
        Subject currentSubject = new Subject("", false);
        Grade currentGrade = new Grade("","","","", currentSubject, false);
        int Column = 0;
        for(int i = 2;; i++) {
            Element table = doc.select("body > div.contenuto > table > tbody > tr:nth-child(" + i + ")").first();

            if(table == null) break;

            Elements inputElements = table.getElementsByTag("td");
            for (Element inputElement : inputElements) {

                if("4".equals(inputElement.attr("colspan"))){
                    Column = 1;
                    currentSubject = new Subject(inputElement.text().toString().replace("[","").replace("]",""), true);
                }else if(Column == 1){
                    currentGrade = new Grade(inputElement.text().toString(),"","","", currentSubject, true);
                    currentSubject.addGrade(currentGrade);
                    data.add("Colonna 0");
                    Column++;
                }else if(Column == 2){
                    currentGrade.setType(inputElement.text().toString());
                    Column++;
                    data.add("Colonna 1");
                }else if(Column == 3){
                    currentGrade.setGrade(inputElement.text().toString());
                    Column++;
                    data.add("Colonna 2");
                }else if(Column == 4){
                    currentGrade.setDescription(inputElement.text().toString());
                    Column = 1;
                    data.add("Colonna 3");
                }

                data.add(inputElement.text().toString());
            }
        }
        return data.toString();
    }

    public static void getCookies() {
        try {
            URL site = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) site.openConnection();

            String[] temp = conn.getHeaderField("Set-Cookie").split(";");

            cookies = temp[0];
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static String cryptPassword(String pass){
        String password =  hex_md5(hex_md5(hex_md5(pass))+seed);
        return password;
    }

    public static final String hex_md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}