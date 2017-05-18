package mands.facilities.inventory;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;

public class StandardInvItem implements InvItem {

    private String id;
    private Integer quantity;

    public StandardInvItem(String idIn, Integer quantityIn) throws InvalidDataException, IllegalParameterException
    {
        setId(idIn);
        setQuantity(quantityIn);
    }

    private void setId(String idIn) throws InvalidDataException {
        if (idIn == null || idIn.isEmpty()){
            throw new InvalidDataException(idIn == null ? "StandardInvItem.setId(string); string is null"
                                                        : "StandardInvItem.setId(string); string is empty");
        }
        id = idIn;
    }

    private void setQuantity(Integer quantityIn) throws IllegalParameterException {
        if (quantityIn < 0){
            throw new IllegalParameterException("Integers passed to InvItem.setQuantity() must be >= O; input: " + quantityIn);
        }
        quantity = quantityIn;
    }

    //Accessors

    public String getId() {return id;}

    public int getQuantity() {
        return quantity;
    }

    //Mutators

    public void increaseInvItemQuant(Integer numberIn) throws IllegalParameterException {
        if (numberIn <= 0){
            throw new IllegalParameterException("Integers passed to StandInvItem.increaseInvItemQuant() must be >0; input: " + numberIn);
        }
        quantity += numberIn;
    }

    public void decreaseInvItemQuant(Integer numberIn) throws IllegalParameterException {
        if (numberIn <= 0){
            throw new IllegalParameterException("Integers passed to StandInvItem.increaseInvItemQuant() must be >0; input: " + numberIn);
        }
        quantity -= numberIn;
    }



}
