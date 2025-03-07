package gameboard;

//Class used to create a single cell of the grid
public class Cell {
    int Ox;
    int Oy;
    CellEntityType cellType;
    CellEntityType originalCellType;
    boolean visited;
    boolean set;

    public Cell(int ox, int oy,CellEntityType cellType,boolean visited,boolean set) {
        this.Ox = ox;
        this.Oy = oy;
        this.originalCellType = cellType;
        this.cellType = cellType;
        this.visited = visited;
        this.set = set;
    }

    public boolean isSet() {
        return this.set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    public int getOx() {
        return this.Ox;
    }

    public int getOy() {
        return this.Oy;
    }

    public void setOx(int ox) {
        this.Ox = ox;
    }

    public void setOy(int oy) {
        this.Oy = oy;
    }


    public CellEntityType getCellType() {
        return this.cellType;
    }

    public void setCellType(CellEntityType cellType) {
        this.cellType = cellType;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public CellEntityType getOriginalCellType() {
        return this.originalCellType;
    }

    public void setOriginalCellType(CellEntityType originalCellType) {
        this.originalCellType = originalCellType;
    }

    @Override
    public String toString() {
       String result = "";
        switch (this.cellType) {
            case PLAYER -> {
                 result = "P ";
            }

            case PORTAL -> {
                if (this.visited) result = "F ";
                else result = "N ";
            }

            case ENEMY -> {
                if (this.visited) result = "E ";
                else result = "N ";
            }

            case SANCTUARY -> {
                if (this.visited) result = "S ";
                else result = "N ";
            }

            case VOID -> {
                if (this.visited) result = "V ";
                else result = "N ";
            }
        }
        return result;
    }
}
