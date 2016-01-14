package main.java.com.tristanwiley.code;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by Tristan on 1/13/2016.
 */
@WebServlet(name = "Problem1", urlPatterns = {"/problem1"})
public class DayOne extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        long seed = stringToSeed(getHash(username));

        String one = problemOne(seed);

        request.setAttribute("input", one);

        PrintWriter out = response.getWriter();
//        out.println("<h1>Your input is</h1><br>" + "<h2>" + one + "</h2>");
        this.getServletContext().getRequestDispatcher("/WEB-INF/calc.jsp").forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static String getHash(String username) {
        String hash;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(username.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            hash = sb.toString();
        } catch (Exception e) {
            hash = "EXCEPTION OCCURED";
        }

        return hash;
    }

    static long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L * hash + c;
        }
        return hash;
    }

    public static String problemOne(long seed) {
        String input = "";
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random r = new Random(seed);
        int range = 100 + r.nextInt((400 - 100) + 1);

        for (int i = 0; i < range; i++) {
            int randomNum = 0 + r.nextInt((range - 0) + 1);
            char letter = abc.charAt(r.nextInt(26));
            input += String.valueOf(randomNum);
            input += letter;
        }
        return input;
    }

    public static boolean oneCorrect(String input, String answer, boolean part1) {
        int correctAnswer = 0;
        List<String> characters = new ArrayList<>(Arrays.asList(answer.split("")));

        if (!StringUtils.isNumeric(answer)) {
            return false;
        }
        if (part1 = true) {
            for (String s : input.split("")) {
                if (StringUtils.isNumeric(s)) {
                    correctAnswer += Integer.parseInt(s);
                }
            }
        } else {
            for (int i = 0; i < characters.size(); i++) {
                if (StringUtils.isNumeric(characters.get(i))) {
                    correctAnswer += Integer.parseInt(characters.get(i));
                } else if (characters.get(i).equals("E")) {
                    correctAnswer -= Integer.parseInt(characters.get(i - 1));
                }
            }
        }

        return correctAnswer == Integer.parseInt(answer);
    }

    public static int solveOne(String input) {
        int correctAnswer = 0;

        for (String s : input.split("")) {
            if (StringUtils.isNumeric(s)) {
                correctAnswer += Integer.parseInt(s);
            }
        }

        return correctAnswer;
    }
}
