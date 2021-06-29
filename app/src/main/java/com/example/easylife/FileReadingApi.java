package com.example.easylife;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileReadingApi {

        @RequiresApi(api = Build.VERSION_CODES.N)
        public static void main(String[] argv)
        {
            try {
                ArrayList<String> array = new ArrayList<>();
//                String path = "/var/www/html/Emplitrack/company/data/"+argv[0]+"/"+argv[1]+"/locations/";
                File myObj = new File("./");
                String[] pathnames = myObj.list();
                for (String pathname : pathnames) {
                        BufferedReader br = new BufferedReader(new FileReader(pathname));
                    String st = "";
//
//                     String line = reader.readLine();
//                    while (line != null) {
//                        System.out.println(line);
//                        // read next line
//                        line = reader.readLine();
//                    }
//                    reader.close();

                    while ( br.readLine() != null)
                    {
                        st = st+br.readLine();
                    }
                    array.add(st);
                }
                System.out.println(array.toString());
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

}
