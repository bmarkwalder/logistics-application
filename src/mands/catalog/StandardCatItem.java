package mands.catalog;

import mands.exceptions.IllegalParameterException;
import mands.exceptions.InvalidDataException;

import java.text.NumberFormat;
import java.util.Locale;

public class StandardCatItem implements CatalogItem {

    private String id;
    private int cost;

    public StandardCatItem(String idIn, int costIn) throws IllegalParameterException, InvalidDataException {
        setId(idIn);
        setCost(costIn);
    }

    private void setId(String idIn) throws InvalidDataException {
        if (idIn == null || idIn.isEmpty()){
            throw new InvalidDataException(idIn == null ? "StandardCatItem.setId(string); string is null"
                                            : "StandardCatItem.setId(string); string is empty");
        }
        id = idIn;
    }

    private void setCost(int costIn) throws IllegalParameterException {
        if (costIn <= 0){
            throw new IllegalParameterException("StandardCatItem.setCost(int); int must be > 0; input: " + costIn);
        }
        cost = costIn;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String returnItem(){
        NumberFormat n = NumberFormat.getNumberInstance(Locale.US);
        return String.format("%-8s : $%s", getId(), n.format(getCost()));
    }
}
