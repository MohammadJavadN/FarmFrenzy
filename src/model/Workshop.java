package model;

import controller.Logger;
import model.User;

import java.util.Random;

public class Workshop implements Changeable {
    String name;
    ProductType input;
    ProductType output;
    int cost;
    int processTime;
    long processDate;
    boolean isWorking = false;
    Random random = new Random();
    int x, y;

    Workshop(String name, ProductType input, ProductType output, int cost, int processTime) {
        this.name = name;
        this.input = input;
        this.output = output;
        this.cost = cost;
        this.processTime = processTime;
        //processDate = LocalDate.getInstance().getCurrentTime() + this.processTime*100000000L;
        //Farm.getFarm().money.set(Farm.getFarm().money.get()-cost);
        Farm.getFarm().addMoney(-cost);
        Farm.getFarm().workshops.add(this);
    }

    public boolean Work() {
        if (Warehouse.getWarehouse().getProduct(input) != null && !isWorking) {
            processDate = LocalDate.getInstance().getCurrentTime() + this.processTime*100000000L;
            LocalDate.getInstance().event.put(processDate, this);
            isWorking = true;
            Warehouse.getWarehouse().removeProduct(Warehouse.getWarehouse().getProduct(input));
            return true;
        }
        return false;
    }

    private void setPosition() {
        boolean isExistWild = true;
        while (isExistWild) {
            x = random.nextInt(6);
            y = random.nextInt(6);
            isExistWild = false;
            for (Wild wild : Farm.getFarm().wilds) {
                if (wild.xPosition == x && wild.yPosition == y) {
                    isExistWild = true;
                    break;
                }
            }
        }
    }

    public boolean equal(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public String checkAfterChangeDate() {
        setPosition();
        new Product(output, x, y);
        isWorking = false;
        long produceDate = 0;
        for (Long aLong : LocalDate.getInstance().event.keySet()) {
            if (LocalDate.getInstance().event.get(aLong) == this)
                produceDate = aLong;
        }
        LocalDate.getInstance().event.remove(produceDate);
        Logger.getLogger(User.getCurrentUser()).log(output.name() + " in [" + (x + 1) + "," + (y + 1) + "]" + "from " + name + "is produced", Logger.LogType.Info);
        System.out.println(output.name() + " in [" + (x + 1) + "," + (y + 1) + "]" + "from " + name + "is produced");
        return "";
        //return output.name() + " in [" + (x+1) + "," + (y+1) + "]" + "from " + name + "is produced\n";
    }

    @Override
    public String toString() {
        return  name +
                " : input = " + input +
                ", output = " + output +
                ", processTime = " + processTime +
                ", isWorking = " + isWorking +
                '\n';
    }
}
