package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = createMap();
//
//        Сохранение созданного мира в файл
//
//        try {
//            createMapToFile(mapTileNum);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        getTileImage();
        loadMap("/maps/map_1.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] createMap() {
        int[][] map = new int[50][50];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == map[i].length - 1 || i == 0 || j == 0 || j == map[j].length - 1) {
                    map[i][j] = 1;
                } else {
                    map[i][j] = (Math.random()<0.67)?0:2;
                }
            }
        }
        return map;
    }

    public static void createMapToFile(int[][] map) throws IOException {
        int c = 1;
        try (FileWriter fileWriter = new FileWriter("map_" + c + ".txt", true)) {
            for (int[] ints : map) {
                for (int j = 0; j < map.length; j++) {
                    if (j != map.length - 1) {
                        fileWriter.append(String.valueOf(ints[j])).append(" ");
                    } else {
                        fileWriter.append(String.valueOf(ints[j])).append("\n");
                    }
                }
                fileWriter.flush();
            }
        }
    }

    public void loadMap(String filePath) {
        try {
            // Reading the map from a file
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            // Drawing tiles around the player
            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

                graphics2D.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
