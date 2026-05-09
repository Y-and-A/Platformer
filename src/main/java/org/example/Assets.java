package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Assets {
    public static Image playerLeft, playerRight, playerFront, playerUp;
    public static Image enemyLeft, enemyRight, enemyFront, enemyBack;
    public static Image bulletLeft, bulletRight, bulletUp, bulletDown;

    private static Image tileRightTop, tileMiddleTop, tileLeftTop;
    private static Image tileLeftMiddle, tileMiddleMiddle, tileRightMiddle;
    private static Image tileLeftBottom, tileMiddleBottom, tileRightBottom;
    private static Image tileFloatingLeft, tileFloatingMiddle, tileFloatingRight, tileFloatingSingle;
    private static Image tileOnly1, tileOnly2, tileOnly3, tileOnly4;
    private static Image tileSpecial1, tileSpecial2,tileSpecial3, tileFullGrassUp, tileFullGrassLeft,tileFullGrassThrough;
    public static Image jumpMushroom;

    public static void loadAll() {
        try {
            // Player
            playerLeft = ImageIO.read(new File("src/main/resources/player/player-facingLeft.png"));
            playerRight = ImageIO.read(new File("src/main/resources/player/player-facingRight.png"));
            playerFront = ImageIO.read(new File("src/main/resources/player/player-front.png"));
            playerUp = ImageIO.read(new File("src/main/resources/player/player-facingUp.png"));

            // Enemy
            enemyLeft = ImageIO.read(new File("src/main/resources/enemy/left.png"));
            enemyRight = ImageIO.read(new File("src/main/resources/enemy/right.png"));
            enemyFront = ImageIO.read(new File("src/main/resources/enemy/front.png"));
            enemyBack = ImageIO.read(new File("src/main/resources/enemy/back.png"));

            // Bullets
            bulletLeft = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallLeft.png"));
            bulletRight = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallRight.png"));
            bulletUp = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallUp.png"));
            bulletDown = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallDown.png"));

            // Tiles
            tileRightTop = ImageIO.read(new File("src/main/resources/tiles/rightTop.png"));
            tileMiddleTop = ImageIO.read(new File("src/main/resources/tiles/middleTop.png"));
            tileLeftTop = ImageIO.read(new File("src/main/resources/tiles/leftTop.png"));
            tileLeftMiddle = ImageIO.read(new File("src/main/resources/tiles/leftMiddle.png"));
            tileMiddleMiddle = ImageIO.read(new File("src/main/resources/tiles/middleMiddle.png"));
            tileRightMiddle = ImageIO.read(new File("src/main/resources/tiles/rightMiddle.png"));
            tileLeftBottom = ImageIO.read(new File("src/main/resources/tiles/leftBottom.png"));
            tileMiddleBottom = ImageIO.read(new File("src/main/resources/tiles/middleBottom.png"));
            tileRightBottom = ImageIO.read(new File("src/main/resources/tiles/rightBottom.png"));
            tileFloatingLeft = ImageIO.read(new File("src/main/resources/tiles/floatingLeft.png"));
            tileFloatingMiddle = ImageIO.read(new File("src/main/resources/tiles/floatingMiddle.png"));
            tileFloatingRight = ImageIO.read(new File("src/main/resources/tiles/floatingRight.png"));
            tileFloatingSingle = ImageIO.read(new File("src/main/resources/tiles/floatingSingle.png"));
            tileOnly1 = ImageIO.read(new File("src/main/resources/tiles/only1.png"));
            tileOnly2 = ImageIO.read(new File("src/main/resources/tiles/only2.png"));
            tileOnly3 = ImageIO.read(new File("src/main/resources/tiles/only3.png"));
            tileOnly4 = ImageIO.read(new File("src/main/resources/tiles/only4.png"));
            tileSpecial1 = ImageIO.read(new File("src/main/resources/tiles/special1.png"));
            tileSpecial2 = ImageIO.read(new File("src/main/resources/tiles/special2.png"));
            tileSpecial3 = ImageIO.read(new File("src/main/resources/tiles/special3.png"));
            tileFullGrassUp = ImageIO.read(new File("src/main/resources/tiles/fullGrassUp.png"));
            tileFullGrassLeft = ImageIO.read(new File("src/main/resources/tiles/fullGrassLeft.png"));
            tileFullGrassThrough = ImageIO.read(new File("src/main/resources/tiles/fullGrassThrough.png"));
            jumpMushroom =  ImageIO.read(new File("src/main/resources/jumpMushroom.png"));

        } catch (IOException e) {
            System.err.println("Failed to load assets");
        }
    }

    public static Image getTileImage(int tileId) {
        return switch (tileId) {
            case 11 -> tileLeftTop;
            case 12 -> tileMiddleTop;
            case 13 -> tileRightTop;
            case 14 -> tileLeftMiddle;
            case 15 -> tileMiddleMiddle;
            case 16 -> tileRightMiddle;
            case 17 -> tileLeftBottom;
            case 18 -> tileMiddleBottom;
            case 19 -> tileRightBottom;
            case 21 -> tileFloatingLeft;
            case 22 -> tileFloatingMiddle;
            case 23 -> tileFloatingRight;
            case 24 -> tileFloatingSingle;
            case 31 -> tileOnly1;
            case 32 -> tileOnly2;
            case 33 -> tileOnly3;
            case 34 -> tileOnly4;
            case 40 -> tileFullGrassUp;
            case 41 -> tileFullGrassLeft;
            case 42 -> tileFullGrassThrough;
            case 61 -> tileSpecial1;
            case 62 -> tileSpecial2;
            case 63 -> tileSpecial3;
            case 66 -> jumpMushroom;
            default -> null;
        };
    }
}
