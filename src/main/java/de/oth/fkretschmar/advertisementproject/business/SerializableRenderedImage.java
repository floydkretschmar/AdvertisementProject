/*
 * Copyright (C) 2016 Floyd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.oth.fkretschmar.advertisementproject.business;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Vector;
import javax.imageio.ImageIO;

/**
 * This class decorates a {@link BufferedImage} with Serialization capabilities.
 *
 * @author Floyd Kretschmar 
 * 
 * NOTE: Implementation inspired by:
 * https://github.com/SebastianTroy/OpenSourceRPG/blob/master/TroysCode
 * /SerializableBufferedImage.java
 * and
 * http://stackoverflow.com/questions/9994129/what-is-the-best-way-to-serialize-
 * an-image-compatible-with-swing-from-java-to
 */
public class SerializableRenderedImage implements RenderedImage, Externalizable {
    
    /**
     * Stores the serialization UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the buffered image that is being decorated.
     */
    private transient RenderedImage image = null;

    /**
     * Creates a new instance of {@link Serializable}.
     */
    public SerializableRenderedImage() {
    }

    /**
     * Creates a new instance of {@link Serializable} using the specified 
     * rendered image.
     * 
     * @param   image   the image that is being decorated with serialization 
     *                  capability.
     */
    public SerializableRenderedImage(RenderedImage image) {
        this.image = image;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        try(ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream()) {
            ImageIO.write(this.image, "png", byteArrayOS);
            byte[] pictureAsByteArray = byteArrayOS.toByteArray();
            out.writeInt(pictureAsByteArray.length);
            out.write(pictureAsByteArray);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int pictureAsByteArrayLength = in.readInt();
        byte[] pictureAsByteArray = new byte[pictureAsByteArrayLength];
        
        in.readFully(pictureAsByteArray);
        
        try(ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(pictureAsByteArray)) {
            this.image = ImageIO.read(byteArrayIS);
        }
    }

    @Override
    public Vector<RenderedImage> getSources() {
        return this.image.getSources();
    }

    @Override
    public Object getProperty(String name) {
        return this.image.getProperty(name);
    }

    @Override
    public String[] getPropertyNames() {
        return this.image.getPropertyNames();
    }

    @Override
    public ColorModel getColorModel() {
        return this.image.getColorModel();
    }

    @Override
    public SampleModel getSampleModel() {
        return this.image.getSampleModel();
    }

    @Override
    public int getWidth() {
        return this.image.getWidth();
    }

    @Override
    public int getHeight() {
        return this.image.getHeight();
    }

    @Override
    public int getMinX() {
        return this.image.getMinX();
    }

    @Override
    public int getMinY() {
        return this.image.getMinY();
    }

    @Override
    public int getNumXTiles() {
        return this.image.getNumXTiles();
    }

    @Override
    public int getNumYTiles() {
        return this.image.getNumYTiles();
    }

    @Override
    public int getMinTileX() {
        return this.image.getMinTileX();
    }

    @Override
    public int getMinTileY() {
        return this.image.getMinTileY();
    }

    @Override
    public int getTileWidth() {
        return this.image.getTileWidth();
    }

    @Override
    public int getTileHeight() {
        return this.image.getTileHeight();
    }

    @Override
    public int getTileGridXOffset() {
        return this.image.getTileGridXOffset();
    }

    @Override
    public int getTileGridYOffset() {
        return this.image.getTileGridYOffset();
    }

    @Override
    public Raster getTile(int tileX, int tileY) {
        return this.image.getTile(tileX, tileY);
    }

    @Override
    public Raster getData() {
        return this.image.getData();
    }

    @Override
    public Raster getData(Rectangle rect) {
        return this.image.getData(rect);
    }

    @Override
    public WritableRaster copyData(WritableRaster raster) {
        return this.image.copyData(raster);
    }

}
