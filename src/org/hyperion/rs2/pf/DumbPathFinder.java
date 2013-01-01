package org.hyperion.rs2.pf;
 
import org.hyperion.rs2.model.Location;
 
/**
 * An implementation of a <code>PathFinder</code> which is 'dumb' and only looks
 * at surrounding tiles for a path, suitable for an NPC.
 * 
 * @author Graham Edgecombe
 * 
 */
public class DumbPathFinder implements PathFinder {
 
    @Override
    public Path findPath(Location location, int radius, TileMap map, int srcX,
            int srcY, int dstX, int dstY) {
        int stepX = 0, stepY = 0;
        //WEST, should check western on this tile and eastern on dest
        if (srcX > dstX
                && map.getTile(srcX, srcY).isWesternTraversalPermitted()
                && map.getTile(dstX, dstY).isEasternTraversalPermitted()) {
            stepX = -1;
        //EAST, should check eastern on this tile and western on dest
        } else if (srcX < dstX
                && map.getTile(srcX, srcY).isEasternTraversalPermitted()
                && map.getTile(dstX, dstY).isWesternTraversalPermitted()) {
            stepX = 1;
        }
        //SOUTH, should check southern on this and northern on dest
        if (srcY > dstY
                && map.getTile(srcX, srcY).isSouthernTraversalPermitted()
                && map.getTile(dstX, dstY).isNorthernTraversalPermitted()) {
            stepY = -1;
        //NORTH, should check northern on this and southern on dest
        } else if (srcY < dstY
                && map.getTile(srcX, srcY).isNorthernTraversalPermitted()
                && map.getTile(dstX, dstY).isSouthernTraversalPermitted()) {
            stepY = 1;
        }
        if (stepX != 0 || stepY != 0) {
            Path p = new Path();
            p.addPoint(new Point(location.getX() + stepX, location.getY() + stepY));
            p.addPoint(new Point(srcX + location.getX() - radius, srcY + location.getY() - radius));
            return p;
        }
        return null;
    }
 
}