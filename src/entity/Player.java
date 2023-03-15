package entity;


import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 7;
        worldY = gamePanel.tileSize * 5;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true ||
                keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
            if (keyHandler.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyHandler.rightPressed) {
                direction = "right";
                worldX += speed;
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
