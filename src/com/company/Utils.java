package com.company;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Utils {
    public Mappa readMapFile(String path){
        if (path == null || path.equals("")) throw new IllegalArgumentException();

        try {//Controlla se esiste il file (path)
            FileChannel.open(Paths.get(path), StandardOpenOption.READ);
        } catch (IOException fe) {//Se non esiste crea il file (path), con dentro un json vuoto
            fe.printStackTrace();
        }
        Mappa map = new Mappa();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){ //creo l'out su dove scrivere i byte letti
            String line = reader.readLine();
            int i = 0; // utilizzato per capire quale info devo ricordare
            int k = 0; //utilizzato per capire da dove inzia le info della mappa
            while (line != null) {
                if ( i == 0) {
                    String[] elments = line.split(" ");
                    map.width = Integer.parseInt(elments[0]);
                    map.height = Integer.parseInt(elments[1]);
                    map.customerHQ = Integer.parseInt(elments[2]);
                    map.maxReplyOffices = Integer.parseInt(elments[3]);
                    map.infoMap = new char[map.height][map.width];
                } else if ( i <= map.customerHQ){
                    String[] coords = line.split(" ");
                    Coordinate coordinata = new Coordinate();
                    coordinata.x = Integer.parseInt(coords[0]);
                    coordinata.y = Integer.parseInt(coords[1]);
                    coordinata.rewardPoints = Integer.parseInt(coords[2]);
                    map.lsCoord.add(coordinata);
                }else {
                    char[] lineAsChar = line.toCharArray();
                    for(int j = 0; j < line.length(); j++){
                        map.infoMap[k][j] = lineAsChar[j];
                    }
                    k++;
                }
                line = reader.readLine();
                i++;
            }

            return map;
        } catch (Exception ec) {
            ec.printStackTrace();
        }

        return null;
    }
}
