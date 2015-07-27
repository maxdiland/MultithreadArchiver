package com.gmail.gorpenko;

import com.gmail.gorpenko.compressor.Compressor;
import com.gmail.gorpenko.compressor.MultiThreadCompressor;
import com.gmail.gorpenko.compressor.SimpleCompressor;
import com.gmail.gorpenko.decompressor.Decompressor;
import com.gmail.gorpenko.decompressor.MultiThreadDecompressor;
import com.gmail.gorpenko.decompressor.SimpleDecompressor;

public enum Mode {
    SIMPLE {
        @Override
        public Compressor getAssociatedCompressor() {
            return new SimpleCompressor();
        }

        @Override
        public Decompressor getAssociatedDecompressor() {
            return new SimpleDecompressor();
        }
    },

    MULTI {
        @Override
        public Compressor getAssociatedCompressor() {
            return new MultiThreadCompressor();
        }

        @Override
        public Decompressor getAssociatedDecompressor() {
            return new MultiThreadDecompressor();
        }
    };

    public abstract Compressor getAssociatedCompressor();
    public abstract Decompressor getAssociatedDecompressor();
}
