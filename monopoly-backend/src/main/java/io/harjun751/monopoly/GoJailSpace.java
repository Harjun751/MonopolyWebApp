package io.harjun751.monopoly;

public class GoJailSpace extends BoardSpace {
    public int position;

    public GoJailSpace(int Position) {
        super(Position, "Go To Jail Space");
        position = Position;
    }
}
