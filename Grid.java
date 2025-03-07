package gameboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//Class used to create the game grid
public class Grid extends ArrayList<ArrayList<Cell>> {
    int width;
    int length;
    Character currentCharacter;
    Cell currentCell;

    private Grid(int with, int length,Cell currentCell) {
        this.width = with;
        this.length = length;
        this.currentCell = currentCell;
    }

    public Cell getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public int getWith() {
        return this.width;
    }

    public int getLength() {
        return this.length;
    }


    public void setWith(int with) {
        this.width = with;
    }

    public void setLength(int length) {
        this.length = length;
    }

    //Method used to generate the grid
    public static Grid generateGrid(int length, int width, Cell currentCell) {
       // System.out.println("Generating Grid with length " + length + " and width " + width);
        if (length >= 10 || width >= 10) throw new IllegalArgumentException("Length must not exceed 10X10");
        Grid grid = new Grid(length, width, currentCell);
        //Grid grid2 = new Grid(length, width, currentCell);
        //Add all cells to an array list to more quickly sort through them when
        //setting the special tiles
        ArrayList<Cell> unsetCells = new ArrayList<>();
        //Initialy, create all cells as void
        for (int i = 0; i < width; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                Cell cell = new Cell(i, j, CellEntityType.VOID, false, false);
                row.add(cell);
                unsetCells.add(cell);
            }
            grid.add(row);
        }

        //set the type of each cell
        grid.setStartingCell();
        unsetCells.remove(grid.getStartingCell());

        grid.setPortal(unsetCells);
        for (int i = 0; i < 2; i++) grid.setSanctuary(unsetCells);
        for (int i = 0; i < 4; i++) grid.setEnemy(unsetCells);

        Random random = new Random();
        while (!unsetCells.isEmpty()) {
            Cell cell = unsetCells.remove(random.nextInt(unsetCells.size()));
            switch (random.nextInt(3)) {
                case 0 -> cell.setCellType(CellEntityType.ENEMY);
                case 1 -> cell.setCellType(CellEntityType.SANCTUARY);
                case 2 -> cell.setCellType(CellEntityType.VOID);
            }
            cell.setSet(true);
        }

        return grid;
    }

    //Method to ensure the creation of the desired grid for the test
    public static Grid generateTestGrid() {
        Grid grid = new Grid(5, 5, new Cell(0,0,CellEntityType.VOID,false,false));
        for (int i = 0; i < 5; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Cell cell = new Cell(i, j, CellEntityType.VOID,false,false);
                row.add(cell);
            }
            grid.add(row);
        }

        //Hard-Code the positions
        grid.setCurrentCell(grid.getCell(0,0));
        grid.getCurrentCell().setCellType(CellEntityType.PLAYER);
        grid.getCurrentCell().setOriginalCellType(CellEntityType.VOID);
        grid.getCell(0,3).setCellType(CellEntityType.SANCTUARY);
        grid.getCell(0,3).setOriginalCellType(CellEntityType.SANCTUARY);
        grid.getCell(0,3).setSet(true);
        grid.getCell(0,3).setVisited(false);
        grid.getCell(1,3).setCellType(CellEntityType.SANCTUARY);
        grid.getCell(1,3).setOriginalCellType(CellEntityType.SANCTUARY);
        grid.getCell(1,3).setSet(true);
        grid.getCell(1,3).setVisited(false);
        grid.getCell(2,0).setCellType(CellEntityType.SANCTUARY);
        grid.getCell(2,0).setOriginalCellType(CellEntityType.SANCTUARY);
        grid.getCell(2,0).setSet(true);
        grid.getCell(2,0).setVisited(false);
        grid.getCell(3,4).setCellType(CellEntityType.ENEMY);
        grid.getCell(3,4).setOriginalCellType(CellEntityType.ENEMY);
        grid.getCell(3,4).setSet(true);
        grid.getCell(3,4).setVisited(false);
        grid.getCell(4,4).setCellType(CellEntityType.PORTAL);
        grid.getCell(4,4).setOriginalCellType(CellEntityType.PORTAL);
        grid.getCell(4,4).setSet(true);
        grid.getCell(4,4).setVisited(false);
        grid.getCell(4,3).setCellType(CellEntityType.SANCTUARY);
        grid.getCell(4,3).setOriginalCellType(CellEntityType.SANCTUARY);
        grid.getCell(4,3).setSet(true);
        grid.getCell(4,3).setVisited(false);
        return grid;
    }

    public Cell setVoid() {
        Cell cell = generateCoords();
        cell.cellType = CellEntityType.VOID;
        cell.set = true;
        cell.visited = false;
        return cell;
    }

    //Method used to set the starting position in a random place
    public void setStartingCell() {
        currentCell.cellType = CellEntityType.PLAYER;

        Random random = new Random();

        currentCell.setOx(random.nextInt(length));
        currentCell.setOy(random.nextInt(width));
        currentCell.visited = true;
        currentCell.set = true;
        Cell starting_cell = getCell(currentCell.Ox, currentCell.Oy);
        starting_cell.cellType = CellEntityType.PLAYER;
        starting_cell.originalCellType = CellEntityType.PLAYER;
        starting_cell.visited = true;
        starting_cell.set = true;

        this.currentCell = starting_cell;
        //return currentCell;
    }

    public Cell getStartingCell() {
        return this.currentCell;
    }

    //Method used to set the Portal in a random place
    public void setPortal(ArrayList<Cell> unsetCells) {
        Random random = new Random();
        Cell cell = unsetCells.remove(random.nextInt(unsetCells.size()));
        cell.cellType = CellEntityType.PORTAL;
        cell.originalCellType = CellEntityType.PORTAL;
        cell.set = true;
        cell.visited = false;
    }

    //Method used to randomly set sanctuaries
    public void setSanctuary(ArrayList<Cell> unsetCells) {
        Random random = new Random();
        Cell cell = unsetCells.remove(random.nextInt(unsetCells.size()));
        cell.cellType = CellEntityType.SANCTUARY;
        cell.originalCellType = CellEntityType.SANCTUARY;
        cell.set = true;
        cell.visited = false;
    }

    //Method used to randomly set enemies
    public void setEnemy(ArrayList<Cell> unsetCells) {
        Random random = new Random();
        Cell cell = unsetCells.remove(random.nextInt(unsetCells.size()));
        cell.cellType = CellEntityType.ENEMY;
        cell.originalCellType = CellEntityType.ENEMY;
        cell.set = true;
        cell.visited = false;
    }


    public Cell generateCoords() {
        Random random = new Random();
        int ox, oy;
        Cell cell;

        do {
            ox = random.nextInt(length);
            oy = random.nextInt(width);
            cell = getCell(ox, oy);
        } while (cell.isSet());

        return cell;
    }


    public Cell getCell(int ox, int oy) {
        return this.get(ox).get(oy);
    }


    //Methodes used to move the characters in all 4 directions
    public void goNorth() throws ImpossibleMove{
        if(currentCell.getOx() - 1 < 0) throw new ImpossibleMove("Cannot go North");
            else {
                Cell nextCell = getCell(currentCell.getOx() - 1,currentCell.getOy());
                nextCell.cellType = CellEntityType.PLAYER;
                if(currentCell.originalCellType == CellEntityType.PLAYER) {
                    currentCell.originalCellType = CellEntityType.VOID;
                }
                    currentCell.cellType = currentCell.originalCellType;
                    currentCell.visited = true;
                    this.currentCell = nextCell;
                }
            }

        public void goSouth() throws ImpossibleMove{
        if(currentCell.getOx() + 1 > length - 1) throw new ImpossibleMove("Cannot go South");
        else {
            Cell nextCell = getCell(currentCell.getOx() + 1,currentCell.getOy());
            nextCell.cellType = CellEntityType.PLAYER;
            if(currentCell.originalCellType == CellEntityType.PLAYER) {
                currentCell.originalCellType = CellEntityType.VOID;
            }
            currentCell.cellType = currentCell.originalCellType;
            currentCell.visited = true;

            this.currentCell = nextCell;
            }
        }

        public void goWest() throws ImpossibleMove{
            if(currentCell.getOy() - 1 < 0) throw new ImpossibleMove("Cannot go West");
            else {
                Cell nextCell = getCell(currentCell.getOx() ,currentCell.getOy() - 1);
                nextCell.cellType = CellEntityType.PLAYER;
                if(currentCell.originalCellType == CellEntityType.PLAYER) {
                    currentCell.originalCellType = CellEntityType.VOID;
                }
                currentCell.cellType = currentCell.originalCellType;
                currentCell.visited = true;

                this.currentCell = nextCell;
            }
        }

        public void goEast() throws ImpossibleMove{
        if(currentCell.getOy() + 1 > width - 1) throw new ImpossibleMove("Cannot go East");
        else {
            Cell nextCell = getCell(currentCell.getOx() ,currentCell.getOy() + 1);
            nextCell.cellType = CellEntityType.PLAYER;
            if(currentCell.originalCellType == CellEntityType.PLAYER) {
                currentCell.originalCellType = CellEntityType.VOID;
            }
            currentCell.cellType = currentCell.originalCellType;
            currentCell.visited = true;

            this.currentCell = nextCell;
        }
        }

        @Override
        public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                sb.append(getCell(i, j).toString());
            }
            sb.append("\n");
        }
        return sb.toString();
        }

        public JPanel gridPanel() {

            final BufferedImage pImg;
            final BufferedImage vImg;
            final BufferedImage nImg;
            final BufferedImage sImg;
            final BufferedImage eImg;
            final BufferedImage fImg;

            try {
                pImg = ImageIO.read(new File("player-icon.jpg"));
                vImg = ImageIO.read(new File("void-visited.jpg"));
                nImg = ImageIO.read(new File("not-visited.jpg"));
                sImg = ImageIO.read(new File("sanctuary-visited.png"));
                eImg = ImageIO.read(new File("enemy-visited.jpg"));
                fImg = ImageIO.read(new File("portal-visited.jpg"));
               } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(length, width,0,0));
        panel.setPreferredSize(new Dimension(900,900));

        int labelWidth = 900/ width;
        int labelLength = 900 / length;

        for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                JLabel label = new JLabel();
                if (!getCell(i,j).isVisited()){
                    label.setIcon(new ImageIcon(nImg.getScaledInstance(labelWidth,labelWidth,Image.SCALE_SMOOTH)));
                }
                else {
                    switch (getCell(i,j).getCellType()){
                        case PLAYER:
                            label.setIcon(new ImageIcon(pImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                            break;
                        case VOID:
                            label.setIcon(new ImageIcon(vImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                            break;
                        case SANCTUARY:
                            label.setIcon(new ImageIcon(sImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                            break;
                        case ENEMY:
                            label.setIcon(new ImageIcon(eImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                            break;
                        case PORTAL:
                            label.setIcon(new ImageIcon(fImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                    }
                }
                label.setBackground(Color.getHSBColor(281f/360f, 0.42f, 0.48f));
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
                panel.add(label);
            }
        }
        panel.setBackground(Color.getHSBColor(281f/360f, 0.42f, 0.48f));
        return panel;
        }

        public void updateGridPanel(JPanel panel) {
            panel.removeAll();
            final BufferedImage pImg;
            final BufferedImage vImg;
            final BufferedImage nImg;
            final BufferedImage sImg;
            final BufferedImage eImg;
            final BufferedImage fImg;

            try {
                pImg = ImageIO.read(new File("player-icon.jpg"));
                vImg = ImageIO.read(new File("void-visited.jpg"));
                nImg = ImageIO.read(new File("not-visited.jpg"));
                sImg = ImageIO.read(new File("sanctuary-visited.png"));
                eImg = ImageIO.read(new File("enemy-visited.jpg"));
                fImg = ImageIO.read(new File("portal-visited.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            int labelWidth = 900/ width;
            int labelLength = 900 / length;

        for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    JLabel label = new JLabel();
                    if(getCell(i,j).getCellType()==CellEntityType.PLAYER) {
                        label.setIcon(new ImageIcon(pImg.getScaledInstance(labelWidth,labelWidth,Image.SCALE_SMOOTH)));
                    }
                    else if (!getCell(i,j).isVisited()){
                        label.setIcon(new ImageIcon(nImg.getScaledInstance(labelWidth,labelWidth,Image.SCALE_SMOOTH)));
                    }
                    else {
                        switch (getCell(i,j).getCellType()){
                            case VOID:
                                label.setIcon(new ImageIcon(vImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                                break;
                            case SANCTUARY:
                                label.setIcon(new ImageIcon(sImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                                break;
                            case ENEMY:
                                label.setIcon(new ImageIcon(eImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                                break;
                            case PORTAL:
                                label.setIcon(new ImageIcon(fImg.getScaledInstance(labelWidth,labelLength,Image.SCALE_SMOOTH)));
                        }
                    }
                    label.setBackground(Color.getHSBColor(281f/360f, 0.42f, 0.48f));
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
                    panel.add(label);
                }
            }
            panel.setBackground(Color.getHSBColor(281f/360f, 0.42f, 0.48f));
        }
}

