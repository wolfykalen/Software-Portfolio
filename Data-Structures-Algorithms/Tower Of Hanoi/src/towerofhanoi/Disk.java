package towerofhanoi;

// Virginia Tech Honor Code Pledge:
// Project 3 Spring 2025
// As a Hokie, I will conduct myself with honor and integrity at all times.
//I will not lie, cheat, or steal, nor will I accept the actions of those who do
// -- Your name (kalendaco)

import cs2.Shape;
import java.awt.Color;
import student.TestableRandom;

// -------------------------------------------------------------------------
/**
 * Represents a Disk in the tower of hanoi each disk has a specific width
 * and is rectangular in shape the disks are used to complete the game by 
 * moving smaller disks onto larger ones 
 * 
 * @author kalendaco
 * @version Mar 25, 2025
 */
public class Disk
    extends Shape
    implements Comparable<Disk>
{
    private int width;

    // ----------------------------------------------------------
    /**
     * Create a new Disk object with a specific width & height &
     * given a random color 
     * 
     * @param width
     *            of the disk
     */
    public Disk(int width) 
    {
        super(0, 0, width, PuzzleWindow.DISK_HEIGHT);
        if (width <= 0) 
        {
            throw new IllegalArgumentException("Width must be positive.");
        }
        this.width = width;
        TestableRandom rand = new TestableRandom();
        Color randomColor = new Color(rand.nextInt(256),
            rand.nextInt(256), rand.nextInt(256));
        this.setBackgroundColor(randomColor);
    }

    /**
     * Compares this disk with another disk based on their widths.
     * 
     * @param 
     *      otherDisk the disk to compare against
     * @return 
     *      a negative integer, zero, or a positive integer         
     * @throws 
     *      IllegalArgumentException if otherDisk is null
     */
    @Override
    public int compareTo(Disk otherDisk) 
    {
        if (otherDisk == null) 
        {
            throw new IllegalArgumentException("Cannot compare to null.");
        }
        return Integer.compare(this.width, otherDisk.width);
    }

    /**
     * Checks if this disk is equal to another object.
     * 
     * @param 
     *      obj the object to compare
     * @return 
     *      true if the object is a Disk with the same width false otherwise
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) 
        {
            return false;
        }
        
        Disk disk = (Disk) obj;
        return width == disk.width;
    }

    /**
     * Returns a string representation of the disks width.
     * 
     * @return 
     *      the width of the disk as a string
     */
    @Override
    public String toString() 
    {
        return String.valueOf(this.getWidth());
    }

}
