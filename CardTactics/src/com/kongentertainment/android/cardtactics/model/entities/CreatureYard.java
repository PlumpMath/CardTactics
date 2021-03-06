package com.kongentertainment.android.cardtactics.model.entities;

import com.kongentertainment.android.cardtactics.model.exceptions.InvalidMoveException;

/**
 * Class to represent the area which a player's creatures live/move.
 */
public class CreatureYard {
	private static final int BACK_ROW = 0;
	private static final int FRONT_ROW = 1;
    
    private CreatureCard[][] mGrid;

    public CreatureYard(int x, int y) {
        mGrid = new CreatureCard[x][y];
    }

    /**
     * Get the creature at a position
     */
    public CreatureCard getCreature(int x, int y) {
        return mGrid[x][y];
    }
    /**
     * PERF: Consider caching this
     */
    public int getWidth() {
        return mGrid.length;
    }
    /**
     * PERF: Consider caching this
     */
    public int getHeight() {
        return mGrid[0].length;
    }

    /**
     * Put a creature into play, presumably from a player's hand
     * @throws InvalidMoveException 
     */
    public void addCreature(CreatureCard creature, int x, int y) throws InvalidMoveException {
        if (mGrid[x][y] != null) {
            throw new InvalidMoveException();
        } else {
            mGrid[x][y] = creature;
        }
    }

	/**
	 * Currently this class presumes two rows
	 * @throws InvalidMoveException 
	 */
	public void pushCreature(int column, boolean forward) throws InvalidMoveException {
		if (forward) {
			moveCreature(column, BACK_ROW, column, FRONT_ROW);
		} else {
			moveCreature(column, FRONT_ROW, column, BACK_ROW);
		}
	}

    public void moveCreature(int startX, int startY, int destX, int destY) throws InvalidMoveException {
        if (mGrid[startX][startY] == null ||
             mGrid[destX][destY] != null
        ) {
            throw new InvalidMoveException();
        } else {
             mGrid[destX][destY] = mGrid[startX][startY];
             mGrid[startX][startY] = null;
        }
    }

    public CreatureCard removeCreature(int x, int y) {
        CreatureCard creature = mGrid[x][y];
        mGrid[x][y] = null;
        return creature;
    }

    public boolean isEmpty(int x, int y) {
        if (mGrid[x][y] == null) {
            return true;
        } 
        return false;
    }

	public boolean isLegalColumn(int column) {
		if (column >= 0 && column < mGrid.length)
			return true;
		return false;
	}
}
