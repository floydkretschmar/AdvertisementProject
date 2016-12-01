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
import java.util.Vector;
import javax.imageio.ImageIO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SerializableRenderedImage implements RenderedImage, Externalizable {
    
    // --------------- Private constants ---------------
    
    /**
     * Stores the serialization UID.
     */
    private static final long serialVersionUID = 1L;
    
    // --------------- Private fields ---------------

    /**
     * Stores the buffered image that is being decorated.
     */
    @NonNull
    private transient RenderedImage image;
    
    // --------------- Public fields (Externalizable) ---------------


    /**
     * Restores the contents of a {@link RenderedImage} by calling the methods 
     * of DataInput and reading the image from a byte array into a 
     * {@link RenderedImage}.
     *
     * @param in the stream to read data from in order to restore the object
     * @exception IOException if I/O errors occur
     * @exception ClassNotFoundException If the class for an object being
     *              restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int pictureAsByteArrayLength = in.readInt();
        byte[] pictureAsByteArray = new byte[pictureAsByteArrayLength];
        
        in.readFully(pictureAsByteArray);
        
        try(ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(pictureAsByteArray)) {
            this.image = ImageIO.read(byteArrayIS);
        }
    }

    /**
     * Saves the contents of a {@link RenderedImage} by calling the methods of 
     * DataOutput and writing the image as a serialized byte array.
     *
     * @serialData  The lenght of the picture as an byte array followed by the
     *              byte array.
     *
     * @param out the stream to write the object to
     * @exception IOException Includes any I/O exceptions that may occur
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        try(ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream()) {
            ImageIO.write(this.image, "jpg", byteArrayOS);
            byte[] pictureAsByteArray = byteArrayOS.toByteArray();
            out.writeInt(pictureAsByteArray.length);
            out.write(pictureAsByteArray);
        }
    }
    
    // --------------- Public fields (RenderedImage) ---------------

    /**
     * {@inheritDoc}
     */
    @Override
    public WritableRaster copyData(WritableRaster raster) {
        return this.image.copyData(raster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColorModel getColorModel() {
        return this.image.getColorModel();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Raster getData() {
        return this.image.getData();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Raster getData(Rectangle rect) {
        return this.image.getData(rect);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.image.getHeight();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinX() {
        return this.image.getMinX();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinY() {
        return this.image.getMinY();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumXTiles() {
        return this.image.getNumXTiles();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumYTiles() {
        return this.image.getNumYTiles();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinTileX() {
        return this.image.getMinTileX();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinTileY() {
        return this.image.getMinTileY();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProperty(String name) {
        return this.image.getProperty(name);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getPropertyNames() {
        return this.image.getPropertyNames();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SampleModel getSampleModel() {
        return this.image.getSampleModel();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Vector<RenderedImage> getSources() {
        return this.image.getSources();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTileWidth() {
        return this.image.getTileWidth();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTileHeight() {
        return this.image.getTileHeight();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTileGridXOffset() {
        return this.image.getTileGridXOffset();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getTileGridYOffset() {
        return this.image.getTileGridYOffset();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Raster getTile(int tileX, int tileY) {
        return this.image.getTile(tileX, tileY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.image.getWidth();
    }
}
