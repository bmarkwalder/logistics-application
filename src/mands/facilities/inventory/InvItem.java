package mands.facilities.inventory;

import mands.exceptions.IllegalParameterException;

public interface InvItem {

    //String returnItem();

    String getId();

    int getQuantity();

    void increaseInvItemQuant(Integer number) throws IllegalParameterException;

    void decreaseInvItemQuant(Integer numberIn) throws IllegalParameterException;

}
