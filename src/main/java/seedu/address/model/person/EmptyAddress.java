package seedu.address.model.person;

public class EmptyAddress extends Address{
    public EmptyAddress() {
        super("-");
    }

    @Override
    public boolean isEmptyAddress() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyAddress;
    }
}
